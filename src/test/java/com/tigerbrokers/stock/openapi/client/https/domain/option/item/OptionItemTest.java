package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.testsupport.PojoTester;
import org.junit.Assert;
import org.junit.Test;

public class OptionItemTest {
  private static final String GTH_JSON = "{\"identifier\":\"SPXW  260723P07380000\","
      + "\"cumVolume\":83322,\"cumAmount\":158877.00128555298,"
      + "\"hourTrading\":{\"tag\":\"盘前\",\"session\":\"GTH\",\"latestPrice\":1.5,"
      + "\"latestTimestamp\":1784796380425,\"volume\":83322,"
      + "\"amount\":158877.00128555298,\"preClose\":1.1}}";

  @Test
  public void pojoSurface() {
    PojoTester.testPackage(getClass().getPackage().getName());
  }

  @Test
  public void deserializeGthFields() {
    OptionBriefItem brief = JSON.parseObject(GTH_JSON, OptionBriefItem.class);
    Assert.assertEquals(Integer.valueOf(83322), brief.getCumVolume());
    Assert.assertEquals(Double.valueOf(158877.00128555298), brief.getCumAmount());
    Assert.assertNotNull(brief.getHourTrading());
    Assert.assertEquals("GTH", brief.getHourTrading().getSession());
    Assert.assertEquals("盘前", brief.getHourTrading().getTag());
    Assert.assertEquals(Double.valueOf(1.5), brief.getHourTrading().getLatestPrice());
    Assert.assertEquals(Long.valueOf(1784796380425L), brief.getHourTrading().getLatestTimestamp());
    Assert.assertEquals(Integer.valueOf(83322), brief.getHourTrading().getVolume());
    Assert.assertEquals(Double.valueOf(1.1), brief.getHourTrading().getPreClose());

    OptionRealTimeQuote quote = JSON.parseObject(GTH_JSON, OptionRealTimeQuote.class);
    Assert.assertEquals(Integer.valueOf(83322), quote.getCumVolume());
    Assert.assertEquals(Double.valueOf(158877.00128555298), quote.getCumAmount());
    Assert.assertEquals("CURB", JSON.parseObject(
        "{\"hourTrading\":{\"session\":\"CURB\"}}", OptionRealTimeQuote.class)
        .getHourTrading().getSession());
  }
}
