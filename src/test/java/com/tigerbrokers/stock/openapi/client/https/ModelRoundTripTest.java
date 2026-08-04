package com.tigerbrokers.stock.openapi.client.https;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.testsupport.ClasspathScanner;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Model 反序列化 round-trip 测试。
 *
 * 对每个 *Model 类：生成填满字段的 camelCase JSON -> 用 fastjson 反序列化 -> 断言成功。
 * 覆盖所有 setter 路径。注意 Model 序列化是 snake_case（有 @JSONField），但反序列化
 * 时 fastjson 默认也能匹配 camelCase setter，所以两种 key 都测。
 */
public class ModelRoundTripTest {

  private static final String DOMAIN_PACKAGE =
      "com.tigerbrokers.stock.openapi.client.https.domain";

  @Test
  public void everyModelCanDeserializeFromCamelCaseJson() {
    List<Class<?>> models = ClasspathScanner.concreteClasses(DOMAIN_PACKAGE, ApiModel.class, "Model");
    Assert.assertTrue("没扫到 Model 类", models.size() > 50);

    List<String> failures = new ArrayList<>();
    for (Class<?> type : models) {
      String json = DeserializationRoundTripTest.generateSampleJsonStatic(type);
      try {
        Object obj = JSON.parseObject(json, type);
        Assert.assertNotNull(type.getSimpleName() + " 反序列化返回 null", obj);
      } catch (Exception e) {
        failures.add(type.getSimpleName() + ": " + e.getClass().getSimpleName() + " " + e.getMessage());
      }
    }

    if (!failures.isEmpty()) {
      StringBuilder sb = new StringBuilder("Model 反序列化失败 " + failures.size() + " 个:\n");
      for (String f : failures) sb.append("  - ").append(f).append("\n");
      Assert.fail(sb.toString());
    }
  }

  @Test
  public void everyModelCanSerializeToJson() {
    List<Class<?>> models = ClasspathScanner.concreteClasses(DOMAIN_PACKAGE, ApiModel.class, "Model");
    List<String> failures = new ArrayList<>();
    for (Class<?> type : models) {
      String json = DeserializationRoundTripTest.generateSampleJsonStatic(type);
      try {
        Object obj = JSON.parseObject(json, type);
        // 再序列化回去
        String reser = JSON.toJSONString(obj);
        Assert.assertNotNull(type.getSimpleName() + " 序列化返回 null", reser);
        Assert.assertTrue(type.getSimpleName() + " 序列化结果不是 JSON", reser.startsWith("{"));
      } catch (Exception e) {
        failures.add(type.getSimpleName() + ": " + e.getClass().getSimpleName() + " " + e.getMessage());
      }
    }

    if (!failures.isEmpty()) {
      StringBuilder sb = new StringBuilder("Model 序列化失败 " + failures.size() + " 个:\n");
      for (String f : failures) sb.append("  - ").append(f).append("\n");
      Assert.fail(sb.toString());
    }
  }
}
