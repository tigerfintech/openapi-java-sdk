package com.tigerbrokers.stock.openapi.client.util;

import java.lang.reflect.Method;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link ReflectionUtil}.
 */
public class ReflectionUtilTest {

  public static class TestBean {
    private String name;
    private Integer age;

    public TestBean() {
    }

    public TestBean(String name, Integer age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Integer getAge() {
      return age;
    }

    public void setAge(Integer age) {
      this.age = age;
    }
  }

  @Test
  public void testGetMethodExists() {
    Method method = ReflectionUtil.getMethod(TestBean.class, "setName", String.class);
    Assert.assertNotNull(method);
    Assert.assertEquals("setName", method.getName());
  }

  @Test
  public void testGetMethodNotExists() {
    Method method = ReflectionUtil.getMethod(TestBean.class, "nonExistentMethod", String.class);
    Assert.assertNull(method);
  }

  @Test
  public void testCheckAndSetDefaultValueViaSetter() {
    TestBean bean = new TestBean();
    Assert.assertNull(bean.getName());
    ReflectionUtil.checkAndSetDefaultValue(bean, "name", "setName", "defaultValue");
    Assert.assertEquals("defaultValue", bean.getName());
  }

  @Test
  public void testCheckAndSetDefaultValueViaField() {
    TestBean bean = new TestBean();
    Assert.assertNull(bean.getAge());
    // Use a non-existent method name so it falls back to field access
    ReflectionUtil.checkAndSetDefaultValue(bean, "age", "setAgeNonExistent", 42);
    Assert.assertEquals(Integer.valueOf(42), bean.getAge());
  }

  @Test
  public void testCheckAndSetDefaultValueDoesNotOverwrite() {
    TestBean bean = new TestBean("existing", null);
    // name is already set, should not overwrite via field access path
    ReflectionUtil.checkAndSetDefaultValue(bean, "name", "nonExistentSetter", "shouldNotApply");
    // The setter doesn't exist, so it falls back to field access which only sets if null
    Assert.assertEquals("existing", bean.getName());
  }

  @Test
  public void testCheckAndSetDefaultValueOverwritesViaSetter() {
    TestBean bean = new TestBean("existing", null);
    // Setter exists, so it always sets the value regardless of current value
    ReflectionUtil.checkAndSetDefaultValue(bean, "name", "setName", "newValue");
    Assert.assertEquals("newValue", bean.getName());
  }
}
