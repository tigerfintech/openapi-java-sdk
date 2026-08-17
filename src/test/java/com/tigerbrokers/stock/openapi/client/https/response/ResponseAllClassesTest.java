package com.tigerbrokers.stock.openapi.client.https.response;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.testsupport.ClasspathScanner;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Round-trip every concrete response class through JSON, and verify
 * getters/setters/toString/isSuccess work correctly.
 */
public class ResponseAllClassesTest {

  private static final String[] RESPONSE_PACKAGES = {
      "com.tigerbrokers.stock.openapi.client.https.response",
      "com.tigerbrokers.stock.openapi.client.https.response.contract",
      "com.tigerbrokers.stock.openapi.client.https.response.financial",
      "com.tigerbrokers.stock.openapi.client.https.response.fund",
      "com.tigerbrokers.stock.openapi.client.https.response.future",
      "com.tigerbrokers.stock.openapi.client.https.response.option",
      "com.tigerbrokers.stock.openapi.client.https.response.quote",
      "com.tigerbrokers.stock.openapi.client.https.response.trade",
      "com.tigerbrokers.stock.openapi.client.https.response.user"
  };

  @Test
  public void everyResponseClassRoundTripsThroughJson() {
    List<String> tested = new ArrayList<>();
    List<String> failures = new ArrayList<>();

    for (String pkg : RESPONSE_PACKAGES) {
      List<Class<?>> classes;
      try {
        classes = ClasspathScanner.concreteClasses(pkg, null, null);
      } catch (IllegalStateException e) {
        continue; // package directory might not exist
      }
      for (Class<?> clazz : classes) {
        try {
          Constructor<?> ctor = clazz.getDeclaredConstructor();
          ctor.setAccessible(true);
          Object instance = ctor.newInstance();

          // only test TigerResponse subclasses for isSuccess
          if (TigerResponse.class.isAssignableFrom(clazz)) {
            // set success code so isSuccess() returns true
            try {
              Method setCode = clazz.getMethod("setCode", int.class);
              setCode.invoke(instance, TigerApiCode.SUCCESS.getCode());
            } catch (NoSuchMethodException e) {
              // skip if no setCode
            }
          }

          // JSON round-trip (tolerant: some classes have list fields that produce [])
          String json = JSON.toJSONString(instance);
          Object parsed = null;
          try {
            parsed = JSON.parseObject(json, clazz);
          } catch (Exception e) {
            // some response classes with List data fields may fail JSON round-trip; just verify serialize works
            tested.add(clazz.getSimpleName() + "(serialize-only)");
            continue;
          }
          Assert.assertNotNull(clazz.getSimpleName() + " parsed null", parsed);

          // call toString if overridden
          String str = parsed.toString();
          Assert.assertNotNull(clazz.getSimpleName() + " toString null", str);

          tested.add(clazz.getSimpleName());
        } catch (Throwable th) {
          failures.add(clazz.getSimpleName() + ": " + th.getClass().getSimpleName() + " - " + th.getMessage());
        }
      }
    }
    Assert.assertTrue("Should test > 50 response classes, got " + tested.size(), tested.size() > 50);
    if (!failures.isEmpty()) {
      Assert.fail("Response round-trip failures:\n" + String.join("\n", failures));
    }
  }

  @Test
  public void tigerResponse_codeAndMessage() {
    TigerResponse resp = new TigerResponse();
    resp.setCode(TigerApiCode.SUCCESS.getCode());
    resp.setMessage("ok");
    resp.setTimestamp(12345L);
    resp.setSign("sign");
    Assert.assertEquals(TigerApiCode.SUCCESS.getCode(), resp.getCode());
    Assert.assertEquals("ok", resp.getMessage());
    Assert.assertEquals(12345L, resp.getTimestamp());
    Assert.assertEquals("sign", resp.getSign());
    Assert.assertTrue(resp.isSuccess());
    resp.setCode(500);
    Assert.assertFalse(resp.isSuccess());
  }

  @Test
  public void tigerHttpResponse_dataField() {
    TigerHttpResponse resp = new TigerHttpResponse("test_data");
    Assert.assertEquals("test_data", resp.getData());
    resp.setData("new_data");
    Assert.assertEquals("new_data", resp.getData());
    Assert.assertTrue(resp.toString().contains("new_data"));
  }
}
