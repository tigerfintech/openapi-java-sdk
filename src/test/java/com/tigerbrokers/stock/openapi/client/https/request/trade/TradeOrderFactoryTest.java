package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import org.junit.Assert;
import org.junit.Test;

/**
 * TradeOrderRequest / TradeOrderPreviewRequest 工厂方法覆盖。
 * 这两个文件合计 1500+ 行，全是 buildXxxOrder 静态方法。
 */
public class TradeOrderFactoryTest {

  private static final String ACCOUNT = "00000000000000000";
  private static final ContractItem STOCK = ContractItem.buildStockContract("AAPL", "USD");

  private void assertValid(TradeOrderRequest req, OrderType expectedType) {
    Assert.assertNotNull(req);
    Assert.assertNotNull(req.getApiMethodName());
    TradeOrderModel model = (TradeOrderModel) req.getApiModel();
    Assert.assertNotNull(model);
    Assert.assertEquals(expectedType, model.getOrderType());
    String json = JSON.toJSONString(model, SerializerFeature.WriteEnumUsingToString);
    Assert.assertTrue(json, json.startsWith("{"));
  }

  // --- Market Order ---
  @Test public void testMarketOrder_basic() {
    assertValid(TradeOrderRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.BUY, 100), OrderType.MKT);
  }
  @Test public void testMarketOrder_withScale() {
    assertValid(TradeOrderRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.SELL, 50L, 0), OrderType.MKT);
  }

  // --- Limit Order ---
  @Test public void testLimitOrder_basic() {
    assertValid(TradeOrderRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.5), OrderType.LMT);
  }
  @Test public void testLimitOrder_withAdjust() {
    assertValid(TradeOrderRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.5, 0.5), OrderType.LMT);
  }
  @Test public void testLimitOrder_withScale() {
    assertValid(TradeOrderRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100L, 0, 150.5), OrderType.LMT);
  }

  // --- Stop Order ---
  @Test public void testStopOrder_basic() {
    assertValid(TradeOrderRequest.buildStopOrder(ACCOUNT, STOCK, ActionType.SELL, 50, 140.0), OrderType.STP);
  }
  @Test public void testStopOrder_withAdjust() {
    assertValid(TradeOrderRequest.buildStopOrder(ACCOUNT, STOCK, ActionType.SELL, 50, 140.0, 1.0), OrderType.STP);
  }

  // --- Stop Limit Order ---
  @Test public void testStopLimitOrder_basic() {
    assertValid(TradeOrderRequest.buildStopLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 155.0, 150.0), OrderType.STP_LMT);
  }
  @Test public void testStopLimitOrder_withAdjust() {
    assertValid(TradeOrderRequest.buildStopLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 155.0, 150.0, 0.5), OrderType.STP_LMT);
  }

  // --- Trail Order ---
  @Test public void testTrailOrder_amount() {
    assertValid(TradeOrderRequest.buildTrailOrder(ACCOUNT, STOCK, ActionType.SELL, 50, 5.0, null), OrderType.TRAIL);
  }
  @Test public void testTrailOrder_withLimit() {
    assertValid(TradeOrderRequest.buildTrailOrder(ACCOUNT, STOCK, ActionType.SELL, 50, 3.0, 145.0), OrderType.TRAIL);
  }

  // --- Amount Order ---
  @Test public void testAmountOrder() {
    assertValid(TradeOrderRequest.buildAmountOrder(ACCOUNT, STOCK, ActionType.BUY, 1000.0), OrderType.MKT);
  }

  // --- TWAP Order ---
  @Test public void testTWAPOrder() {
    long now = System.currentTimeMillis();
    assertValid(TradeOrderRequest.buildTWAPOrder(ACCOUNT, "AAPL", ActionType.BUY, 1000, now, now + 3600_000, 155.0), OrderType.TWAP);
  }

  // --- Preview ---
  @Test public void testPreview_market() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.BUY, 100);
    Assert.assertNotNull(req);
    Assert.assertNotNull(req.getApiModel());
  }
  @Test public void testPreview_limit() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.5);
    Assert.assertNotNull(req);
    Assert.assertNotNull(req.getApiModel());
  }
  @Test public void testPreview_stop() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildStopOrder(ACCOUNT, STOCK, ActionType.SELL, 50, 140.0);
    Assert.assertNotNull(req);
    Assert.assertNotNull(req.getApiModel());
  }
}
