package com.tigerbrokers.stock.openapi.client.util;

import java.lang.reflect.Field;

/**
 * @author liutongping
 * @date 2024/6/4 11:05 AM
 */
public class ReflectionUtil {

  @SuppressWarnings("unchecked")
  public static void checkAndSetDefaultValue(Object bean, String fieldName, Object defaultValue) {
    try {
      Class<?> clazz = bean.getClass();
      Field field = clazz.getDeclaredField(fieldName);
      if (field == null) {
        return;
      }
      field.setAccessible(true);
      Object value = field.get(bean);
      if (value == null) {
        field.set(bean, defaultValue);
      }
    } catch (NoSuchFieldException | IllegalAccessException e) {
      ApiLogger.error("checkAndSetDefaultValue failed:{}", e.getMessage(), e);
    }
  }
}
