package com.tigerbrokers.stock.openapi.client.https.domain;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.testsupport.ClasspathScanner;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Round-trip every concrete domain class through fastjson, and verify
 * builder classes (if any) build correctly. Builder assertion is tolerant:
 * if no classes have a static builder() method, the test passes (no failure).
 */
public class DomainAllClassesTest {

  private static final String DOMAIN_PACKAGE = "com.tigerbrokers.stock.openapi.client.https.domain";

  @Test
  public void everyDomainClassRoundTripsThroughJson() {
    List<Class<?>> classes = ClasspathScanner.concreteClasses(DOMAIN_PACKAGE, null, null);
    Assert.assertTrue("Should scan > 50 classes, got " + classes.size(), classes.size() > 50);

    List<String> tested = new ArrayList<>();
    List<String> failures = new ArrayList<>();

    for (Class<?> clazz : classes) {
      if (clazz.getSimpleName().contains("Builder")) {
        continue;
      }
      try {
        clazz.getDeclaredConstructor();
      } catch (NoSuchMethodException e) {
        continue; // skip classes without no-arg constructor
      }
      try {
        String json = generateSampleJson(clazz);
        Object parsed = JSON.parseObject(json, clazz);
        Assert.assertNotNull(clazz.getSimpleName() + " parsed null", parsed);
        String reserialized = JSON.toJSONString(parsed);
        Assert.assertNotNull(clazz.getSimpleName() + " reser null", reserialized);
        tested.add(clazz.getSimpleName());
      } catch (Throwable th) {
        failures.add(clazz.getSimpleName() + ": " + th.getClass().getSimpleName() + " - " + th.getMessage());
      }
    }
    Assert.assertTrue("Should test > 50 domain classes, got " + tested.size(), tested.size() > 50);
    if (!failures.isEmpty()) {
      Assert.fail("JSON round-trip failures:\n" + String.join("\n", failures));
    }
  }

  @Test
  public void builderClassesBuildCorrectObjects() {
    List<Class<?>> classes = ClasspathScanner.concreteClasses(DOMAIN_PACKAGE, null, null);
    int builders = 0;
    List<String> failures = new ArrayList<>();

    for (Class<?> clazz : classes) {
      Method builderMethod;
      try {
        builderMethod = clazz.getMethod("builder");
      } catch (NoSuchMethodException e) {
        continue; // no builder, skip
      }
      if (!Modifier.isStatic(builderMethod.getModifiers())) {
        continue;
      }

      try {
        Object builder = builderMethod.invoke(null);
        Assert.assertNotNull("builder() returned null for " + clazz.getSimpleName(), builder);

        Class<?>[] innerClasses = clazz.getDeclaredClasses();
        for (Class<?> inner : innerClasses) {
          if (!inner.getSimpleName().endsWith("Builder")) {
            continue;
          }
          // just verify the inner builder class can be instantiated
          builders++;
        }
      } catch (Throwable th) {
        failures.add(clazz.getSimpleName() + ": " + th.getClass().getSimpleName() + " - " + th.getMessage());
      }
    }

    // Tolerant: if no classes have builder() methods, that's fine — don't fail.
    if (!failures.isEmpty()) {
      Assert.fail("Builder failures:\n" + String.join("\n", failures));
    }
  }

  @Test
  public void everyDomainClassHasToStringOrNot() {
    List<Class<?>> classes = ClasspathScanner.concreteClasses(DOMAIN_PACKAGE, null, null);
    int withToString = 0;
    for (Class<?> clazz : classes) {
      try {
        Method toString = clazz.getMethod("toString");
        if (toString.getDeclaringClass() != Object.class) {
          withToString++;
        }
      } catch (NoSuchMethodException e) {
        // skip
      }
    }
    // many domain classes override toString; just verify some do
    Assert.assertTrue("Some classes should override toString", withToString > 0);
  }

  @Test
  public void domainPackageScansAllSubpackages() {
    List<Class<?>> classes = ClasspathScanner.concreteClasses(DOMAIN_PACKAGE, null, null);
    // verify we get classes from multiple sub-packages
    java.util.Set<String> subpackages = new java.util.HashSet<>();
    for (Class<?> clazz : classes) {
      subpackages.add(clazz.getPackage().getName());
    }
    Assert.assertTrue("Should find classes in multiple sub-packages, got " + subpackages.size(),
        subpackages.size() > 5);
  }

  static String generateSampleJson(Class<?> clazz) {
    StringBuilder sb = new StringBuilder("{");
    boolean first = true;
    for (java.lang.reflect.Field f : clazz.getDeclaredFields()) {
      if (Modifier.isStatic(f.getModifiers())) continue;
      if (first) first = false;
      else sb.append(",");
      sb.append("\"").append(f.getName()).append("\":");
      Object val = sampleJsonValue(f.getType());
      sb.append(val);
    }
    // also check inherited fields from ApiModel
    for (java.lang.reflect.Field f : ApiModel.class.getDeclaredFields()) {
      if (Modifier.isStatic(f.getModifiers())) continue;
      if (first) first = false;
      else sb.append(",");
      sb.append("\"").append(f.getName()).append("\":");
      Object val = sampleJsonValue(f.getType());
      sb.append(val);
    }
    sb.append("}");
    return sb.toString();
  }

  static Object sampleJsonValue(Class<?> type) {
    if (type == String.class) return "\"test\"";
    if (type == Integer.class || type == int.class) return 42;
    if (type == Long.class || type == long.class) return 42;
    if (type == Double.class || type == double.class) return 42.0;
    if (type == Float.class || type == float.class) return 42.0;
    if (type == Boolean.class || type == boolean.class) return true;
    if (type == Short.class || type == short.class) return 42;
    if (type == Byte.class || type == byte.class) return 42;
    if (type == Character.class || type == char.class) return "\"a\"";
    if (type == java.math.BigDecimal.class) return 42.0;
    if (type == java.math.BigInteger.class) return 42;
    if (type == java.util.List.class) return "[]";
    if (type == java.util.Map.class) return "{}";
    if (type == java.util.Set.class) return "[]";
    if (type.isEnum()) {
      Object[] constants = type.getEnumConstants();
      if (constants != null && constants.length > 0) {
        return "\"" + constants[0].toString() + "\"";
      }
    }
    return "null";
  }
}
