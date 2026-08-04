package com.tigerbrokers.stock.openapi.client.https;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.testsupport.ClasspathScanner;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/** 只是某个字段静默为 null。这类问题只有序列化契约测试能强制拦住，而它不需要网络。 */
public class SerializationContractTest {

  private static final String BASE_PACKAGE = "com.tigerbrokers.stock.openapi.client.https";

  /** S2 例外：服务端接收的 wire 名不是该字段的标准 snake_case 形式。 */
  private static final Map<String, String> S2_WIRE_NAME_OVERRIDES;

  static {
    Map<String, String> overrides = new HashMap<>();
    // 服务端该参数名就叫 period，不是 k_type
    overrides.put("QuoteKlineModel.kType", "period");
    // 附加订单的 orderId 后缀在 wire 上保留驼峰，Go SDK model/order.go 有同样的注释说明
    overrides.put("TradeOrderModel.profitTakerOrderId", "profit_taker_orderId");
    overrides.put("TradeOrderModel.stopLossOrderId", "stop_loss_orderId");
    S2_WIRE_NAME_OVERRIDES = Collections.unmodifiableMap(overrides);
  }

  /** S3 例外：Item 上确实需要 snake_case 的字段（服务端该字段返回的就是 snake_case）。 */
  private static final Set<String> S3_EXEMPT_FIELDS = new HashSet<>(Arrays.asList(
      // 占位：目前无例外
  ));

  /** S5 例外：Response 声明的字段不是 data 载荷（如客户端本地补充的字段）。 */
  private static final Set<String> S5_EXEMPT_FIELDS = new HashSet<>(Arrays.asList(
      // 占位：目前无例外
  ));

  /** 协议信封基类，声明的是 code / message / timestamp / sign，不是 data 载荷。 */
  private static final Set<String> RESPONSE_ENVELOPE_CLASSES = new HashSet<>(Arrays.asList(
      "TigerResponse", "TigerHttpResponse"
  ));

  @Test
  public void modelCamelCaseFieldMustDeclareSnakeCaseJsonField() {
    List<String> violations = new ArrayList<>();
    // 按类名后缀过滤，不能只按 ApiModel 父类过滤 —— 有 64 个 *Item 类也 extends ApiModel，
    // 它们是响应体，适用 S3 的 camelCase 规则，混进来会产生大量假违规
    List<Class<?>> models = classesUnder(BASE_PACKAGE + ".domain", ApiModel.class, "Model");
    Assert.assertFalse("没扫到任何 Model 类，扫描逻辑坏了", models.isEmpty());

    for (Class<?> type : models) {
      for (Field field : serializableFields(type)) {
        String name = field.getName();
        if (!hasUpperCase(name)) {
          // 单词字段（account / currency / strike）的 Java 名就是 wire 名，无需注解
          continue;
        }
        String key = type.getSimpleName() + "." + name;
        JSONField annotation = field.getAnnotation(JSONField.class);
        String expected = S2_WIRE_NAME_OVERRIDES.containsKey(key)
            ? S2_WIRE_NAME_OVERRIDES.get(key) : toSnakeCase(name);
        if (annotation == null || annotation.name().isEmpty()) {
          violations.add(key + " 缺 @JSONField(name = \"" + expected + "\")");
        } else if (!expected.equals(annotation.name())) {
          violations.add(key + " 的 @JSONField 是 \"" + annotation.name()
              + "\"，期望 \"" + expected + "\"");
        }
      }
    }
    assertNoViolations("S2 请求参数序列化", models.size(), violations);
  }

  @Test
  public void itemFieldMustNotDeclareSnakeCaseJsonField() {
    List<String> violations = new ArrayList<>();
    List<Class<?>> items = classesUnder(BASE_PACKAGE + ".domain", null, "Item");
    Assert.assertFalse("没扫到任何 Item 类，扫描逻辑坏了", items.isEmpty());

    for (Class<?> type : items) {
      for (Field field : serializableFields(type)) {
        JSONField annotation = field.getAnnotation(JSONField.class);
        if (annotation == null || annotation.name().isEmpty()) {
          continue;
        }
        String key = type.getSimpleName() + "." + field.getName();
        if (S3_EXEMPT_FIELDS.contains(key)) {
          continue;
        }
        if (annotation.name().indexOf('_') >= 0) {
          violations.add(key + " 的 @JSONField 是 snake_case \"" + annotation.name()
              + "\"，服务端返回 camelCase，该字段会反序列化为 null");
        }
      }
    }
    assertNoViolations("S3 响应字段反序列化", items.size(), violations);
  }

  @Test
  public void responseDataFieldMustBeBound() {
    List<String> violations = new ArrayList<>();
    List<Class<?>> responses = classesUnder(BASE_PACKAGE + ".response", TigerResponse.class);
    Assert.assertFalse("没扫到任何 TigerResponse 子类，扫描逻辑坏了", responses.isEmpty());

    int withPayload = 0;
    for (Class<?> type : responses) {
      if (RESPONSE_ENVELOPE_CLASSES.contains(type.getSimpleName())) {
        continue;
      }
      List<Field> fields = serializableFields(type);
      if (fields.isEmpty()) {
        // 空 body 响应（submit / cancel 类接口），没有 data 载荷
        continue;
      }
      withPayload++;
      for (Field field : fields) {
        String key = type.getSimpleName() + "." + field.getName();
        if (S5_EXEMPT_FIELDS.contains(key)) {
          continue;
        }
        JSONField annotation = field.getAnnotation(JSONField.class);
        if (annotation == null || annotation.name().isEmpty()) {
          violations.add(key + " 缺 @JSONField(name = \"data\")，整个 data 无法反序列化");
        }
      }
    }
    Assert.assertTrue("没扫到任何带 data 载荷的 Response，扫描逻辑坏了", withPayload > 0);
    assertNoViolations("S5 Response data 字段绑定", withPayload, violations);
  }

  // ---------- helpers ----------

  private static void assertNoViolations(String rule, int scanned, List<String> violations) {
    if (violations.isEmpty()) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(rule).append("：扫描 ").append(scanned).append(" 个类，发现 ")
        .append(violations.size()).append(" 处违规：\n");
    for (String violation : violations) {
      sb.append("  - ").append(violation).append('\n');
    }
    Assert.fail(sb.toString());
  }

  /** 参与 JSON 序列化的实例字段。static / transient / 编译器合成字段都不算。 */
  private static List<Field> serializableFields(Class<?> type) {
    List<Field> result = new ArrayList<>();
    for (Field field : type.getDeclaredFields()) {
      int modifiers = field.getModifiers();
      if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers) || field.isSynthetic()) {
        continue;
      }
      result.add(field);
    }
    return result;
  }

  private static boolean hasUpperCase(String value) {
    for (int i = 0; i < value.length(); i++) {
      if (Character.isUpperCase(value.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  /** camelCase 转 snake_case，连续大写按缩写处理。 */
  static String toSnakeCase(String camelCase) {
    StringBuilder sb = new StringBuilder(camelCase.length() + 4);
    for (int i = 0; i < camelCase.length(); i++) {
      char c = camelCase.charAt(i);
      if (!Character.isUpperCase(c)) {
        sb.append(c);
        continue;
      }
      boolean prevIsLowerOrDigit = i > 0 && !Character.isUpperCase(camelCase.charAt(i - 1));
      boolean nextIsLower = i + 1 < camelCase.length()
          && Character.isLowerCase(camelCase.charAt(i + 1));
      if (i > 0 && (prevIsLowerOrDigit || nextIsLower)) {
        sb.append('_');
      }
      sb.append(Character.toLowerCase(c));
    }
    return sb.toString();
  }

  @Test
  public void toSnakeCaseHandlesAcronyms() {
    Assert.assertEquals("sec_type", toSnakeCase("secType"));
    Assert.assertEquals("contract_id", toSnakeCase("contractId"));
    Assert.assertEquals("include_otc", toSnakeCase("includeOTC"));
    Assert.assertEquals("expire_ym", toSnakeCase("expireYM"));
    Assert.assertEquals("otc_only", toSnakeCase("OTCOnly"));
    Assert.assertEquals("account", toSnakeCase("account"));
  }

  private static List<Class<?>> classesUnder(String packageName, Class<?> superType) {
    return classesUnder(packageName, superType, null);
  }

  private static List<Class<?>> classesUnder(String packageName, Class<?> superType, String suffix) {
    return ClasspathScanner.concreteClasses(packageName, superType, suffix);
  }
}
