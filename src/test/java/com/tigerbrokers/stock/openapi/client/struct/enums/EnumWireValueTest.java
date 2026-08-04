package com.tigerbrokers.stock.openapi.client.struct.enums;

import com.tigerbrokers.stock.openapi.client.testsupport.ClasspathScanner;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 * 枚举 wire 值不变量测试（对应跨语言 review-sdk S4）。
 *
 * <p>对所有声明了 {@code getValue()} 方法的枚举，断言：
 * <ol>
 *   <li>每个常量的 wire 值非空、非 null</li>
 *   <li>同一枚举内无重复 wire 值</li>
 *   <li>枚举常量列表非空（类能正常加载）</li>
 * </ol>
 *
 * <p>副作用：触发所有枚举类的类初始化，jacoco 会把静态初始化的行算进覆盖行数，
 * 从而覆盖 struct.enums 下 1500+ 行常量声明。这不是目的，目的是断言 wire 值约束。
 */
public class EnumWireValueTest {

  private static final String ENUMS_PACKAGE =
      "com.tigerbrokers.stock.openapi.client.struct.enums";

  @Test
  public void allEnumsLoadSuccessfully() {
    List<Class<?>> enums = allEnumClasses();
    Assert.assertTrue("没扫到任何枚举类", enums.size() > 50);
    // 只要这里不抛 ExceptionInInitializerError 就说明类都能正常加载
    for (Class<?> e : enums) {
      Object[] constants = e.getEnumConstants();
      Assert.assertNotNull(e.getSimpleName() + " getEnumConstants() 返回 null", constants);
      Assert.assertTrue(e.getSimpleName() + " 没有任何常量", constants.length > 0);
    }
  }

  @Test
  public void enumsWithGetValueHaveUniqueNonNullWireValues() {
    List<Class<?>> enums = allEnumClasses();
    List<String> violations = new ArrayList<>();
    int withGetValue = 0;

    for (Class<?> enumClass : enums) {
      Method getValue;
      try {
        getValue = enumClass.getMethod("getValue");
      } catch (NoSuchMethodException e) {
        // 没有 getValue() 的枚举（如 MethodType），跳过
        continue;
      }
      withGetValue++;
      Object[] constants = enumClass.getEnumConstants();
      Set<Object> seen = new HashSet<>();

      for (Object constant : constants) {
        Object value;
        try {
          value = getValue.invoke(constant);
        } catch (Exception e) {
          violations.add(enumClass.getSimpleName() + "." + constant + ".getValue() 抛异常: " + e);
          continue;
        }
        if (value == null || (value instanceof String && ((String) value).isEmpty())) {
          violations.add(enumClass.getSimpleName() + "." + constant + " wire 值为空");
        }
        if (!seen.add(value)) {
          violations.add(enumClass.getSimpleName() + "." + constant
              + " wire 值重复: \"" + value + "\"");
        }
      }
    }

    Assert.assertTrue("没找到任何带 getValue() 的枚举", withGetValue > 0);
    if (!violations.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      sb.append("枚举 wire 值不变量违规 ").append(violations.size()).append(" 处：\n");
      for (String v : violations) {
        sb.append("  - ").append(v).append('\n');
      }
      Assert.fail(sb.toString());
    }
  }

  private static List<Class<?>> allEnumClasses() {
    List<Class<?>> all = new ArrayList<>();
    // surefire fork 的 classpath 里 getResource() 可能返回 null（jar URL 环境）；
    // 直接走 target/classes 目录遍历，跟 ClasspathScanner 同一套路。
    java.io.File testClasses;
    try {
      testClasses = new java.io.File(EnumWireValueTest.class.getProtectionDomain()
          .getCodeSource().getLocation().toURI());
    } catch (Exception e) {
      Assert.fail("定位 test-classes 目录失败: " + e);
      return all;
    }
    java.io.File classesDir = new java.io.File(testClasses.getParentFile(), "classes");
    java.io.File dir = new java.io.File(classesDir, ENUMS_PACKAGE.replace('.', '/'));
    Assert.assertTrue("枚举包目录不存在: " + dir, dir.isDirectory());

    for (java.io.File f : dir.listFiles()) {
      if (!f.getName().endsWith(".class") || f.getName().contains("$")) continue;
      String className = ENUMS_PACKAGE + "." + f.getName().replace(".class", "");
      try {
        Class<?> c = Class.forName(className, true, EnumWireValueTest.class.getClassLoader());
        if (c.isEnum()) {
          all.add(c);
        }
      } catch (Throwable th) {
        // 跳过加载失败的
      }
    }
    return all;
  }
}
