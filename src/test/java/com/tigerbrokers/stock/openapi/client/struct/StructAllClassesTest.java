package com.tigerbrokers.stock.openapi.client.struct;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.testsupport.ClasspathScanner;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class StructAllClassesTest {

  private static final String STRUCT_PACKAGE = "com.tigerbrokers.stock.openapi.client.struct";

  @Test
  public void everyStructClassRoundTripsThroughJson() {
    List<Class<?>> classes = ClasspathScanner.concreteClasses(STRUCT_PACKAGE, null, null);
    List<String> tested = new ArrayList<>();
    List<String> failures = new ArrayList<>();

    for (Class<?> clazz : classes) {
      try {
        Constructor<?> ctor;
        try {
          ctor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
          continue; // skip classes without no-arg constructor
        }
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();

        // JSON round-trip
        String json = JSON.toJSONString(instance);
        Object parsed = JSON.parseObject(json, clazz);
        Assert.assertNotNull(clazz.getSimpleName() + " parsed null", parsed);

        // toString
        String str = parsed.toString();
        Assert.assertNotNull(clazz.getSimpleName() + " toString null", str);

        tested.add(clazz.getSimpleName());
      } catch (Throwable th) {
        failures.add(clazz.getSimpleName() + ": " + th.getClass().getSimpleName() + " - " + th.getMessage());
      }
    }
    Assert.assertTrue("Should test > 0 struct classes", tested.size() > 3);
    if (!failures.isEmpty()) {
      Assert.fail("Struct round-trip failures:\n" + String.join("\n", failures));
    }
  }

  @Test
  public void optionSymbol_gettersSetters() {
    OptionSymbol s = new OptionSymbol();
    s.setSymbol("AAPL");
    s.setExpiry("20240119");
    s.setStrike("150");
    s.setRight("CALL");
    Assert.assertEquals("AAPL", s.getSymbol());
    Assert.assertEquals("20240119", s.getExpiry());
    Assert.assertEquals("150", s.getStrike());
    Assert.assertEquals("CALL", s.getRight());
  }

  @Test
  public void subscribedSymbol_gettersSetters() {
    SubscribedSymbol s = new SubscribedSymbol();
    s.setLimit(100);
    s.setUsed(50);
    s.setAskBidLimit(200);
    s.setAskBidUsed(100);
    s.setTradeTickLimit(300);
    s.setTradeTickUsed(150);
    s.setKlineLimit(400);
    s.setKlineUsed(200);

    Set<String> syms = new HashSet<>();
    syms.add("AAPL");
    s.setSubscribedSymbols(syms);
    s.setSubscribedAskBidSymbols(syms);
    s.setSubscribedTradeTickSymbols(syms);
    s.setSubscribedKlineSymbols(syms);
    s.setSubscribedMarketQuote(syms);

    Assert.assertEquals(100, s.getLimit());
    Assert.assertEquals(50, s.getUsed());
    Assert.assertEquals(200, s.getAskBidLimit());
    Assert.assertEquals(100, s.getAskBidUsed());
    Assert.assertEquals(300, s.getTradeTickLimit());
    Assert.assertEquals(150, s.getTradeTickUsed());
    Assert.assertEquals(400, s.getKlineLimit());
    Assert.assertEquals(200, s.getKlineUsed());
    Assert.assertTrue(s.getSubscribedSymbols().contains("AAPL"));
  }

  @Test
  public void tagValue_gettersSetters() {
    TagValue tv = new TagValue("tag1", "val1");
    Assert.assertEquals("tag1", tv.tag);
    Assert.assertEquals("val1", tv.value);
    tv.tag = "tag2";
    tv.value = "val2";
    Assert.assertEquals("tag2", tv.tag);
    Assert.assertEquals("val2", tv.value);
    Assert.assertEquals(tv, new TagValue("tag2", "val2"));
    Assert.assertEquals(tv.hashCode(), new TagValue("tag2", "val2").hashCode());
    Assert.assertNotEquals(tv, new TagValue("other", "val2"));
    Assert.assertNotEquals(tv, null);
    Assert.assertNotEquals(tv, "notATagValue");
    Assert.assertEquals(tv, tv);
    Assert.assertNull(TagValue.buildTagValue(null, "val"));
    Assert.assertNull(TagValue.buildTagValue("tag", null));
    Assert.assertNotNull(TagValue.buildTagValue("tag", 42));
  }

  @Test
  public void range_gettersSetters() {
    Range<String> r = new Range<>("A", "Z");
    Assert.assertEquals("A", r.getMin());
    Assert.assertEquals("Z", r.getMax());
    r.setMin("a");
    r.setMax("z");
    Assert.assertEquals("a", r.getMin());
    Assert.assertEquals("z", r.getMax());
  }

  @Test
  public void signItem_gettersSetters() {
    SignItem item = new SignItem("source", "sign123");
    Assert.assertEquals("source", item.getSignSource());
    Assert.assertEquals("sign123", item.getSign());
    item.setSignSource("new_source");
    item.setSign("new_sign");
    Assert.assertEquals("new_source", item.getSignSource());
    Assert.assertEquals("new_sign", item.getSign());
  }

  @Test
  public void indicator_staticGetValues() {
    Set<Indicator> indicators = new HashSet<>();
    indicators.add(() -> "AAPL");
    indicators.add(() -> "GOOG");
    Set<String> values = Indicator.getValues(indicators);
    Assert.assertTrue(values.contains("AAPL"));
    Assert.assertTrue(values.contains("GOOG"));
    Assert.assertTrue(Indicator.getValues(null).isEmpty());
  }

  @Test
  public void marketIndicatorValue_gettersSetters() {
    MarketIndicatorValue miv = new MarketIndicatorValue(1, "test", 42.0);
    Assert.assertEquals(Integer.valueOf(1), miv.getIndex());
    Assert.assertEquals("test", miv.getName());
    Assert.assertEquals(42.0, miv.getValue());
    Assert.assertEquals(Double.valueOf(42.0), miv.doubleValue());
    miv.setIndex(2);
    miv.setName("name2");
    miv.setValue(99.0);
    Assert.assertEquals(Integer.valueOf(2), miv.getIndex());
    Assert.assertEquals("name2", miv.getName());
    Assert.assertTrue(miv.toString().contains("name2"));
  }

  @Test
  public void marketIndicatorValue_nullValue() {
    MarketIndicatorValue miv = new MarketIndicatorValue();
    Assert.assertNull(miv.doubleValue());
  }

  @Test
  public void clientHeartBeatData_gettersSetters() {
    ClientHeartBeatData data = new ClientHeartBeatData(30, 60);
    Assert.assertEquals(30, data.getSendInterval());
    Assert.assertEquals(60, data.getReceiveInterval());
    data.setSendInterval(15);
    data.setReceiveInterval(45);
    Assert.assertEquals(15, data.getSendInterval());
    Assert.assertEquals(45, data.getReceiveInterval());
  }

  @Test
  public void clientHeartBeatData_defaultConstructor() {
    ClientHeartBeatData data = new ClientHeartBeatData();
    Assert.assertEquals(0, data.getSendInterval());
    Assert.assertEquals(0, data.getReceiveInterval());
  }

  @Test
  public void optionFundamentals_gettersSetters() {
    OptionFundamentals of = new OptionFundamentals();
    of.setDelta(0.5);
    of.setGamma(0.1);
    of.setTheta(-0.2);
    of.setVega(0.3);
    of.setRho(0.01);
    of.setPredictedValue(1.0);
    of.setTimeValue(2.0);
    of.setPremiumRate(0.05);
    of.setProfitRate(0.1);
    of.setVolatility(0.3);
    of.setLeverage(10.0);
    of.setInsideValue(5.0);
    of.setHistoryVolatility(0.25);
    of.setOpenInterest(1000);
    of.setMetricParam("test");

    Assert.assertEquals(0.5, of.getDelta(), 0.001);
    Assert.assertEquals(0.1, of.getGamma(), 0.001);
    Assert.assertEquals(-0.2, of.getTheta(), 0.001);
    Assert.assertEquals(0.3, of.getVega(), 0.001);
    Assert.assertEquals(0.01, of.getRho(), 0.001);
    Assert.assertEquals(1.0, of.getPredictedValue(), 0.001);
    Assert.assertEquals(2.0, of.getTimeValue(), 0.001);
    Assert.assertEquals(0.05, of.getPremiumRate(), 0.001);
    Assert.assertEquals(0.1, of.getProfitRate(), 0.001);
    Assert.assertEquals(0.3, of.getVolatility(), 0.001);
    Assert.assertEquals(10.0, of.getLeverage(), 0.001);
    Assert.assertEquals(5.0, of.getInsideValue(), 0.001);
    Assert.assertEquals(0.25, of.getHistoryVolatility(), 0.001);
    Assert.assertEquals(1000, of.getOpenInterest(), 0.001);
    Assert.assertEquals("test", of.getMetricParam());
  }

  @Test
  public void optionMetrics_gettersSetters() {
    OptionMetrics om = new OptionMetrics(0.5, 0.1, -0.2, 0.3, 0.01);
    Assert.assertEquals(0.5, om.getDelta(), 0.001);
    Assert.assertEquals(0.1, om.getGamma(), 0.001);
    Assert.assertEquals(-0.2, om.getTheta(), 0.001);
    Assert.assertEquals(0.3, om.getVega(), 0.001);
    Assert.assertEquals(0.01, om.getRho(), 0.001);
    om.setDelta(0.6);
    Assert.assertEquals(0.6, om.getDelta(), 0.001);
  }
}
