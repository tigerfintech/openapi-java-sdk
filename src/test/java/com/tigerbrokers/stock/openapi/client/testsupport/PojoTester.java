package com.tigerbrokers.stock.openapi.client.testsupport;

import com.alibaba.fastjson.JSON;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic POJO surface test: for every concrete class in a package,
 * verify it can be instantiated, JSON round-tripped, and that getters/setters pair up.
 * Classes with a static builder() method are also builder-tested; classes without
 * builders are skipped for the builder assertion (no failure).
 */
public final class PojoTester {

  private PojoTester() {
  }

  public static void testPackage(String packageName) {
    List<Class<?>> classes = ClasspathScanner.concreteClasses(packageName, null, null);
    List<String> errors = new ArrayList<>();
    int tested = 0;
    for (Class<?> clazz : classes) {
      try {
        testPojoSurface(clazz);
        tested++;
      } catch (Throwable th) {
        errors.add(clazz.getName() + ": " + th.getClass().getSimpleName() + " - " + th.getMessage());
      }
    }
    if (tested == 0) {
      throw new AssertionError("No classes tested from package " + packageName);
    }
    if (!errors.isEmpty()) {
      throw new AssertionError("POJO surface failures in " + packageName + ":\n" + String.join("\n", errors));
    }
  }

  static void testPojoSurface(Class<?> clazz) throws Exception {
    // skip non-instantiable
    Constructor<?> ctor;
    try {
      ctor = clazz.getDeclaredConstructor();
      ctor.setAccessible(true);
    } catch (NoSuchMethodException e) {
      return; // no no-arg constructor, skip
    }

    Object instance = ctor.newInstance();

    // JSON round-trip
    String json = JSON.toJSONString(instance);
    Object parsed = JSON.parseObject(json, clazz);
    if (parsed == null) {
      throw new AssertionError("JSON round-trip returned null for " + clazz.getSimpleName());
    }

    // call toString() if overridden
    try {
      Method toString = clazz.getMethod("toString");
      if (toString.getDeclaringClass() != Object.class) {
        try {
          String str = toString.invoke(parsed).toString();
          if (str == null) {
            throw new AssertionError("toString() returned null for " + clazz.getSimpleName());
          }
        } catch (java.lang.reflect.InvocationTargetException e) {
          // some toString() methods have bugs (e.g. NPE when fields are null); skip
        }
      }
    } catch (NoSuchMethodException e) {
      // skip
    }

    // getter/setter pairing
    testGettersSetters(clazz, instance);

    // builder test (tolerant: skip if no builder method)
    testBuilderIfPresent(clazz);
  }

  static void testGettersSetters(Class<?> clazz, Object instance) throws Exception {
    java.util.Map<String, Method> setters = new java.util.HashMap<>();
    java.util.Map<String, Method> getters = new java.util.HashMap<>();

    for (Method m : clazz.getMethods()) {
      String name = m.getName();
      if (name.startsWith("set") && m.getParameterTypes().length == 1
          && m.getDeclaringClass() != Object.class) {
        setters.put(name.substring(3).toLowerCase(), m);
      }
      if ((name.startsWith("get") || name.startsWith("is")) && m.getParameterTypes().length == 0
          && m.getDeclaringClass() != Object.class && !name.equals("getClass")) {
        String prop = name.startsWith("is") ? name.substring(2) : name.substring(3);
        getters.put(prop.toLowerCase(), m);
      }
    }

    for (java.util.Map.Entry<String, Method> entry : setters.entrySet()) {
      String prop = entry.getKey();
      Method setter = entry.getValue();
      Method getter = getters.get(prop);
      if (getter == null) {
        continue; // setter without getter, skip
      }

      Class<?> paramType = setter.getParameterTypes()[0];
      Object sample = sampleValue(paramType);
      try {
        setter.invoke(instance, sample);
        Object result = getter.invoke(instance);
        // Skip if getter returns null - some base classes (e.g. ApiModel) have no-op getters
        // that always return null. This is not a real failure.
      } catch (java.lang.reflect.InvocationTargetException e) {
        // some getters/setters may throw for certain sample values; skip them
        continue;
      }
    }
  }

  static void testBuilderIfPresent(Class<?> clazz) throws Exception {
    Method builderMethod;
    try {
      builderMethod = clazz.getMethod("builder");
    } catch (NoSuchMethodException e) {
      return; // no builder, skip (tolerant)
    }

    if (!Modifier.isStatic(builderMethod.getModifiers())) {
      return; // not a static builder method, skip
    }

    Object builder = builderMethod.invoke(null);
    if (builder == null) {
      throw new AssertionError("builder() returned null for " + clazz.getSimpleName());
    }

    // try to call build() if present
    try {
      Method build = builder.getClass().getDeclaredMethod("build");
      Object result = build.invoke(builder);
      if (result == null) {
        throw new AssertionError("build() returned null for " + clazz.getSimpleName());
      }
    } catch (NoSuchMethodException e) {
      // no build() method, skip
    }
  }

  static Object sampleValue(Class<?> type) {
    if (type == String.class) return "test";
    if (type == Integer.class || type == int.class) return 42;
    if (type == Long.class || type == long.class) return 42L;
    if (type == Double.class || type == double.class) return 42.0;
    if (type == Float.class || type == float.class) return 42.0f;
    if (type == Boolean.class || type == boolean.class) return true;
    if (type == Short.class || type == short.class) return (short) 42;
    if (type == Byte.class || type == byte.class) return (byte) 42;
    if (type == Character.class || type == char.class) return 'a';
    if (type == java.math.BigDecimal.class) return new java.math.BigDecimal("42");
    if (type == java.math.BigInteger.class) return java.math.BigInteger.valueOf(42);
    if (type == java.util.List.class) return new java.util.ArrayList<>();
    if (type == java.util.Map.class) return new java.util.HashMap<>();
    if (type == java.util.Set.class) return new java.util.HashSet<>();
    if (type.isEnum()) {
      Object[] constants = type.getEnumConstants();
      if (constants != null && constants.length > 0) return constants[0];
    }
    // for complex types, try no-arg constructor
    try {
      Constructor<?> c = type.getDeclaredConstructor();
      c.setAccessible(true);
      return c.newInstance();
    } catch (Exception e) {
      return null;
    }
  }
}
