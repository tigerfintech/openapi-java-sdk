package com.tigerbrokers.stock.openapi.client.https.request;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.testsupport.ClasspathScanner;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Assert;
import org.junit.Test;

/**
 * 本仓自查测试：wire method 注册表完整性。
 *
 * <p>不测任何具体接口，测的是「有没有漏」。全部基于反射，不发请求、不依赖凭据、
 * 不依赖用例执行顺序。
 *
 * <p>各语言 SDK 各自持有自己的测试资产，没有跨仓共享的接口清单，所以「某个接口在本仓
 * 漏实现或漏注册」必须由本仓的测试自己发现。跨语言之间的完整性（某接口只在一个语言里
 * 实现了）不在这里，由 review-sdk skill 负责，那需要人判断是否有意不支持。
 */
public class ApiRegistryTest {

  private static final String REQUEST_PACKAGE =
      "com.tigerbrokers.stock.openapi.client.https.request";

  /**
   * 棘轮清单：只能通过泛用 {@link TigerHttpRequest} 调用、没有类型化 Request 类的 MethodName。
   *
   * <p>{@code TigerHttpRequest(MethodName)} 让调用方在构造时传方法名，返回的是未定型的
   * {@link com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse}，
   * 请求参数和响应字段都没有编译期约束 —— 这是早期 API 风格，新接口不应再走这条路。
   *
   * <p>清单只允许变短：给某个方法补上类型化 Request 类后从这里删一行，忘了删也会红。
   * 不要为了消红往里加条目，那是在往后拨棘轮。
   */
  private static final Set<String> METHOD_NAMES_WITHOUT_REQUEST = new HashSet<>(Arrays.asList(
      "ACCOUNTS",
      "ACTIVE_ORDERS",
      "ASSETS",
      "BATCH_PLACE_ORDER",
      "BRIEF",
      "CANCEL_ORDER",
      "FILLED_ORDERS",
      "GET_QUOTE_PERMISSION",
      "GRAB_QUOTE_PERMISSION",
      "HOUR_TRADING_TIMELINE",
      "INACTIVE_ORDERS",
      "INDUSTRY_LIST",
      "INDUSTRY_STOCKS",
      "MODIFY_ORDER",
      "ORDER_NO",
      "ORDER_TRANSACTIONS",
      "STOCK_DETAIL",
      "STOCK_INDUSTRY"
  ));

  /** 没有无参构造的 Request 类，无法反射实例化，跳过注册表检查。 */
  private static final Set<String> REQUESTS_WITHOUT_DEFAULT_CONSTRUCTOR =
      new HashSet<>(Arrays.asList(
          // 泛用请求，方法名由调用方在构造时传入，本身不绑定某个 MethodName
          "TigerHttpRequest"
      ));

  private static List<Class<?>> requestClasses() {
    List<Class<?>> classes =
        ClasspathScanner.concreteClasses(REQUEST_PACKAGE, TigerRequest.class, "Request");
    Assert.assertFalse("没扫到任何 TigerRequest 实现，扫描逻辑坏了", classes.isEmpty());
    return classes;
  }

  /** 用无参构造反射创建 Request，拿不到就返回 null。 */
  private static TigerRequest<?> instantiate(Class<?> type) {
    try {
      Constructor<?> constructor = type.getDeclaredConstructor();
      constructor.setAccessible(true);
      return (TigerRequest<?>) constructor.newInstance();
    } catch (Throwable th) {
      return null;
    }
  }

  @Test
  public void everyRequestDeclaresMethodName() {
    List<String> violations = new ArrayList<>();
    List<String> notInstantiable = new ArrayList<>();

    for (Class<?> type : requestClasses()) {
      String name = type.getSimpleName();
      TigerRequest<?> request = instantiate(type);
      if (request == null) {
        notInstantiable.add(name);
        continue;
      }
      if (request.getApiMethodName() == null) {
        violations.add(name + " 的构造函数没有 setApiMethodName，请求发出时不带 method 参数");
      }
    }

    List<String> unexpected = new ArrayList<>(notInstantiable);
    unexpected.removeAll(REQUESTS_WITHOUT_DEFAULT_CONSTRUCTOR);
    Assert.assertTrue("这些 Request 类无法用无参构造实例化：" + unexpected
        + "。如果确实没有无参构造，加进 REQUESTS_WITHOUT_DEFAULT_CONSTRUCTOR。",
        unexpected.isEmpty());

    List<String> stale = new ArrayList<>(REQUESTS_WITHOUT_DEFAULT_CONSTRUCTOR);
    stale.removeAll(notInstantiable);
    Assert.assertTrue("REQUESTS_WITHOUT_DEFAULT_CONSTRUCTOR 里这些条目已经不需要了："
        + stale + "，请移除。", stale.isEmpty());

    assertNoViolations("Request 必须声明 MethodName", violations);
  }

  @Test
  public void everyRequestResolvesToResponseClass() {
    List<String> violations = new ArrayList<>();

    for (Class<?> type : requestClasses()) {
      TigerRequest<?> request = instantiate(type);
      if (request == null) {
        continue;
      }
      Class<?> responseClass;
      try {
        responseClass = request.getResponseClass();
      } catch (Throwable th) {
        violations.add(type.getSimpleName() + ".getResponseClass() 抛异常：" + th);
        continue;
      }
      if (responseClass == null) {
        violations.add(type.getSimpleName() + ".getResponseClass() 返回 null，响应无法反序列化");
      } else if (!TigerResponse.class.isAssignableFrom(responseClass)) {
        violations.add(type.getSimpleName() + ".getResponseClass() 返回 "
            + responseClass.getSimpleName() + "，不是 TigerResponse 子类");
      }
    }
    assertNoViolations("Request 与 Response 类型闭环", violations);
  }

  @Test
  public void everyMethodNameHasRequestClass() {
    Set<MethodName> used = new LinkedHashSet<>();
    for (Class<?> type : requestClasses()) {
      TigerRequest<?> request = instantiate(type);
      if (request != null && request.getApiMethodName() != null) {
        used.add(request.getApiMethodName());
      }
    }
    Assert.assertFalse("没收集到任何 MethodName，实例化逻辑坏了", used.isEmpty());

    Set<String> unused = new TreeSet<>();
    for (MethodName methodName : MethodName.values()) {
      if (!used.contains(methodName)) {
        unused.add(methodName.name());
      }
    }

    Set<String> newlyUnused = new TreeSet<>(unused);
    newlyUnused.removeAll(METHOD_NAMES_WITHOUT_REQUEST);
    Assert.assertTrue("这些 MethodName 没有对应的 Request 类：" + newlyUnused
        + "。新增枚举值必须同时加 Request 类，不要往 METHOD_NAMES_WITHOUT_REQUEST 里加条目。",
        newlyUnused.isEmpty());

    Set<String> stale = new TreeSet<>(METHOD_NAMES_WITHOUT_REQUEST);
    stale.removeAll(unused);
    Assert.assertTrue("这些 MethodName 已经有 Request 类了：" + stale
        + "，请从 METHOD_NAMES_WITHOUT_REQUEST 里删掉，让棘轮往前走一格。", stale.isEmpty());
  }

  /**
   * 已废弃的 ApiServiceType 常量接口不得领先 MethodName。
   *
   * <p>同一份 wire 契约在本仓存在两份声明，ApiServiceType 已标 {@code @Deprecated}，
   * 新接口只加 MethodName。这里保证它不会反向漂移出 MethodName 没有的方法名。
   */
  @Test
  public void deprecatedApiServiceTypeDoesNotDriftAheadOfMethodName() throws Exception {
    Set<String> methodNameValues = new HashSet<>();
    for (MethodName methodName : MethodName.values()) {
      methodNameValues.add(methodName.getValue());
    }

    Set<String> orphans = new TreeSet<>();
    int scanned = 0;
    for (Field field : ApiServiceType.class.getDeclaredFields()) {
      if (!Modifier.isStatic(field.getModifiers()) || field.getType() != String.class) {
        continue;
      }
      scanned++;
      String value = (String) field.get(null);
      if (!methodNameValues.contains(value)) {
        orphans.add(field.getName() + " = \"" + value + "\"");
      }
    }

    Assert.assertTrue("没扫到 ApiServiceType 常量，反射逻辑坏了", scanned > 0);
    Assert.assertTrue("ApiServiceType 里这些常量在 MethodName 中不存在：" + orphans
        + "。两份注册表必须对齐，新接口只加 MethodName。", orphans.isEmpty());
  }

  private static void assertNoViolations(String rule, List<String> violations) {
    if (violations.isEmpty()) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(rule).append("：发现 ").append(violations.size()).append(" 处违规：\n");
    for (String violation : violations) {
      sb.append("  - ").append(violation).append('\n');
    }
    Assert.fail(sb.toString());
  }
}
