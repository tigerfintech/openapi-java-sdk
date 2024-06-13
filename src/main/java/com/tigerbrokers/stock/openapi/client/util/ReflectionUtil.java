package com.tigerbrokers.stock.openapi.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author liutongping
 * @date 2024/6/4 11:05 AM
 */
public class ReflectionUtil {

  @SuppressWarnings("unchecked")
  public static void checkAndSetDefaultValue(Object bean, String fieldName, String setMethodName, Object defaultValue) {
    try {
      Class<?> clazz = bean.getClass();
      Method setNameMethod = getMethod(clazz, setMethodName, defaultValue.getClass());
      if (setNameMethod != null) {
        setNameMethod.invoke(bean, defaultValue);
        return;
      }
      Field field = clazz.getDeclaredField(fieldName);
      if (field == null) {
        return;
      }
      field.setAccessible(true);
      Object value = field.get(bean);
      if (value == null) {
        field.set(bean, defaultValue);
      }
    } catch (InvocationTargetException | NoSuchFieldException | IllegalAccessException e) {
      ApiLogger.error("checkAndSetDefaultValue failed:{}", e.getMessage(), e);
    }
  }

  public static Method getMethod(Class<?> clazz, String methodName, Class<?> parameterType) {
    try {
      return clazz.getMethod(methodName, parameterType);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }
}
