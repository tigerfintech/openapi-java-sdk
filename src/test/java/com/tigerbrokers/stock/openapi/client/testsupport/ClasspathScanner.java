package com.tigerbrokers.stock.openapi.client.testsupport;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 走 CodeSource 定位再遍历目录就够了，少一个依赖少一处版本冲突。 */
public final class ClasspathScanner {

  private ClasspathScanner() {
  }

  /** 扫描指定包下可实例化的具体类。 */
  public static List<Class<?>> concreteClasses(String packageName, Class<?> superType,
      String simpleNameSuffix) {
    File packageDir = new File(classesRoot(), packageName.replace('.', '/'));
    if (!packageDir.isDirectory()) {
      throw new IllegalStateException("找不到包目录：" + packageDir);
    }

    List<String> classNames = new ArrayList<>();
    collectClassNames(packageDir, packageName, classNames);
    Collections.sort(classNames);

    List<Class<?>> result = new ArrayList<>();
    for (String className : classNames) {
      Class<?> type;
      try {
        type = Class.forName(className, false, ClasspathScanner.class.getClassLoader());
      } catch (Throwable th) {
        // 依赖缺失导致加载不了的类直接跳过，不让扫描器成为编译期依赖的传递点
        continue;
      }
      if (type.isInterface() || type.isEnum() || type.isAnonymousClass()
          || Modifier.isAbstract(type.getModifiers())) {
        continue;
      }
      if (superType != null && !superType.isAssignableFrom(type)) {
        continue;
      }
      if (simpleNameSuffix != null && !type.getSimpleName().endsWith(simpleNameSuffix)) {
        continue;
      }
      result.add(type);
    }
    return result;
  }

  /** target/classes 目录。test-classes 与 classes 是兄弟目录。 */
  private static File classesRoot() {
    File testClasses;
    try {
      testClasses = new File(ClasspathScanner.class.getProtectionDomain()
          .getCodeSource().getLocation().toURI());
    } catch (URISyntaxException e) {
      throw new IllegalStateException("定位 test-classes 目录失败", e);
    }
    File classesDir = new File(testClasses.getParentFile(), "classes");
    if (!classesDir.isDirectory()) {
      throw new IllegalStateException("找不到 target/classes：" + classesDir);
    }
    return classesDir;
  }

  private static void collectClassNames(File dir, String packageName, List<String> out) {
    File[] children = dir.listFiles();
    if (children == null) {
      return;
    }
    for (File child : children) {
      if (child.isDirectory()) {
        collectClassNames(child, packageName + "." + child.getName(), out);
      } else if (child.getName().endsWith(".class") && child.getName().indexOf('$') < 0) {
        out.add(packageName + "." + child.getName().substring(0, child.getName().length() - 6));
      }
    }
  }
}
