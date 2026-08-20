package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainFilterModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainFilterModel.Greeks;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PrimeAssetItem.Segment;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PositionTransferExternalRecordItem.TransferPropertyInfo;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PositionTransferDetailItem.TransferDetail;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.AddonEntitlementItem.Entitlement;
import org.junit.Assert;
import org.junit.Test;

public class InnerClassCoverageTest {

  @Test
  public void testSegment_jsonRoundTrip() {
    Segment s = new Segment();
    s.setCapability("cap1");
    s.setCategory("cat1");
    s.setCurrency("USD");
    s.setCashBalance(1000.0);
    s.setCashAvailableForTrade(500.0);
    s.setGrossPositionValue(2000.0);
    s.setEquityWithLoan(1500.0);
    s.setNetLiquidation(1200.0);
    s.setInitMargin(100.0);
    s.setMaintainMargin(80.0);
    s.setOvernightMargin(90.0);
    s.setUnrealizedPL(50.0);
    s.setUnrealizedPLByCostOfCarry(40.0);
    s.setRealizedPL(30.0);
    s.setTotalTodayPL(60.0);
    s.setExcessLiquidation(110.0);
    s.setOvernightLiquidation(95.0);
    s.setBuyingPower(200.0);
    s.setLeverage(3.0);

    String json = JSON.toJSONString(s);
    Segment parsed = JSON.parseObject(json, Segment.class);
    Assert.assertNotNull(parsed);
    Assert.assertEquals("cap1", parsed.getCapability());
    Assert.assertEquals("USD", parsed.getCurrency());
    Assert.assertEquals(1000.0, parsed.getCashBalance(), 0.001);
    Assert.assertEquals(500.0, parsed.getCashAvailableForTrade(), 0.001);
    Assert.assertEquals(2000.0, parsed.getGrossPositionValue(), 0.001);
    Assert.assertEquals(1500.0, parsed.getEquityWithLoan(), 0.001);
    Assert.assertEquals(1200.0, parsed.getNetLiquidation(), 0.001);
    Assert.assertEquals(100.0, parsed.getInitMargin(), 0.001);
    Assert.assertEquals(80.0, parsed.getMaintainMargin(), 0.001);
    Assert.assertEquals(90.0, parsed.getOvernightMargin(), 0.001);
    Assert.assertEquals(50.0, parsed.getUnrealizedPL(), 0.001);
    Assert.assertEquals(30.0, parsed.getRealizedPL(), 0.001);
    Assert.assertEquals(60.0, parsed.getTotalTodayPL(), 0.001);
    Assert.assertEquals(110.0, parsed.getExcessLiquidation(), 0.001);
    Assert.assertEquals(200.0, parsed.getBuyingPower(), 0.001);
    Assert.assertEquals(3.0, parsed.getLeverage(), 0.001);
  }

  @Test
  public void testGreeks_builderPattern() {
    Greeks g = new Greeks();
    g.delta(0.3, 0.7);
    g.gamma(0.01, 0.05);
    g.vega(0.1, 0.3);
    g.theta(-0.5, -0.1);
    g.rho(0.001, 0.01);
    Assert.assertNotNull(g.getDelta());
    String json = JSON.toJSONString(g);
    Greeks parsed = JSON.parseObject(json, Greeks.class);
    Assert.assertNotNull(parsed);
  }

  @Test
  public void testOptionChainFilterModel_withGreeks() {
    OptionChainFilterModel m = new OptionChainFilterModel();
    Greeks g = new Greeks().delta(0.3, 0.7);
    m.setGreeks(g);
    Assert.assertNotNull(m.getGreeks());
  }

  @Test
  public void testTransferPropertyInfo_jsonRoundTrip() {
    TransferPropertyInfo info = new TransferPropertyInfo();
    info.setSymbol("AAPL");
    info.setMarket("US");
    info.setSecType("STK");
    info.setQuantity(100.0);
    info.setStatus("FINISHED");
    String json = JSON.toJSONString(info);
    TransferPropertyInfo parsed = JSON.parseObject(json, TransferPropertyInfo.class);
    Assert.assertNotNull(parsed);
    Assert.assertEquals("AAPL", parsed.getSymbol());
    Assert.assertEquals("US", parsed.getMarket());
    Assert.assertEquals(100.0, parsed.getQuantity(), 0.001);
    Assert.assertEquals("FINISHED", parsed.getStatus());
  }

  @Test
  public void testTransferDetail_jsonRoundTrip() {
    TransferDetail detail = new TransferDetail();
    detail.setSymbol("TSLA");
    detail.setDirection("IN");
    detail.setMarket("US");
    detail.setQuantity(50.0);
    String json = JSON.toJSONString(detail);
    TransferDetail parsed = JSON.parseObject(json, TransferDetail.class);
    Assert.assertNotNull(parsed);
    Assert.assertEquals("TSLA", parsed.getSymbol());
    Assert.assertEquals("IN", parsed.getDirection());
    Assert.assertEquals(50.0, parsed.getQuantity(), 0.001);
  }

  @Test
  public void testEntitlement_jsonRoundTrip() {
    Entitlement e = new Entitlement();
    e.setHistoryStockLimit(100);
    e.setHistoryStockRemaining(80);
    e.setSubscribeLimit(50);
    e.setSubscribeRemaining(45);
    String json = JSON.toJSONString(e);
    Entitlement parsed = JSON.parseObject(json, Entitlement.class);
    Assert.assertNotNull(parsed);
    Assert.assertEquals(Integer.valueOf(100), parsed.getHistoryStockLimit());
    Assert.assertEquals(Integer.valueOf(80), parsed.getHistoryStockRemaining());
    Assert.assertEquals(Integer.valueOf(50), parsed.getSubscribeLimit());
  }
}
