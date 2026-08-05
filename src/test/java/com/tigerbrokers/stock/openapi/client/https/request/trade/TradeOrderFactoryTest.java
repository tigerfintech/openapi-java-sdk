package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.ContractLeg;
import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.ComboType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.PriceType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradeSession;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradingSessionType;
import java.util.Arrays;
import java.util.Collections;
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

  private static final ContractLeg LEG = new ContractLeg(SecType.OPT, "AAPL", "160", "20240119",
      com.tigerbrokers.stock.openapi.client.struct.enums.Right.CALL, ActionType.BUY, 1);

  private void assertPreviewValid(TradeOrderPreviewRequest req, OrderType expectedType) {
    Assert.assertNotNull(req);
    Assert.assertNotNull(req.getApiMethodName());
    TradeOrderModel model = (TradeOrderModel) req.getApiModel();
    Assert.assertNotNull(model);
    Assert.assertEquals(expectedType, model.getOrderType());
  }

  // --- TradeOrderRequest: Iceberg ---
  @Test public void testIcebergOrder_defaultAccount() {
    assertValid(TradeOrderRequest.buildIcebergOrder(STOCK, ActionType.BUY, 100, 150.0, 10), OrderType.ICEBERG);
  }
  @Test public void testIcebergOrder_withAccount() {
    assertValid(TradeOrderRequest.buildIcebergOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0, 10), OrderType.ICEBERG);
  }
  @Test public void testIcebergOrder_fullOverload() {
    TradeOrderRequest req = TradeOrderRequest.buildIcebergOrder(ACCOUNT, STOCK, ActionType.SELL, 200, 155.0,
        20, 5, 60, PriceType.LATEST_PRICE, 1000L, 2000L);
    assertValid(req, OrderType.ICEBERG);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(Integer.valueOf(5), m.getMinDisplaySize());
    Assert.assertEquals(Integer.valueOf(60), m.getCheckIntervals());
    Assert.assertEquals(PriceType.LATEST_PRICE.getValue(), m.getPriceType());
    Assert.assertEquals(Long.valueOf(1000L), m.getStartTime());
  }
  @Test public void testIcebergOrder_nullPriceType_defaultsToLimit() {
    TradeOrderRequest req = TradeOrderRequest.buildIcebergOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0,
        10, null, null, null, null, null);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(PriceType.LIMIT_PRICE.getValue(), m.getPriceType());
    Assert.assertEquals(Integer.valueOf(10), m.getMinDisplaySize());
  }

  // --- TradeOrderRequest: MultiLeg ---
  @Test public void testMultiLegOrder_integerQuantity() {
    TradeOrderRequest req = TradeOrderRequest.buildMultiLegOrder(ACCOUNT, Arrays.asList(LEG),
        ComboType.VERTICAL, ActionType.BUY, 10, OrderType.LMT, 150.0, null, null);
    Assert.assertNotNull(req);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(SecType.MLEG, m.getSecType());
    Assert.assertEquals("VERTICAL", m.getComboType());
    Assert.assertEquals(TimeInForce.DAY, m.getTimeInForce());
    Assert.assertEquals(Long.valueOf(10L), m.getTotalQuantity());
  }
  @Test public void testMultiLegOrder_longQuantity() {
    TradeOrderRequest req = TradeOrderRequest.buildMultiLegOrder(ACCOUNT, Arrays.asList(LEG),
        ComboType.STRADDLE, ActionType.SELL, 10L, 0, OrderType.MKT, null, null, 5.0);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(OrderType.MKT, m.getOrderType());
    Assert.assertEquals(Double.valueOf(5.0), m.getTrailingPercent());
  }
  @Test(expected = IllegalArgumentException.class) public void testMultiLegOrder_nullLegs_throws() {
    TradeOrderRequest.buildMultiLegOrder(ACCOUNT, null, ComboType.CUSTOM, ActionType.BUY, 10,
        OrderType.LMT, 1.0, null, null);
  }
  @Test(expected = IllegalArgumentException.class) public void testMultiLegOrder_nullOrderType_throws() {
    TradeOrderRequest.buildMultiLegOrder(ACCOUNT, Arrays.asList(LEG), ComboType.CUSTOM, ActionType.BUY, 10,
        null, 1.0, null, null);
  }
  @Test public void testMultiLegOrder_emptyAccount_usesDefault() {
    TradeOrderRequest req = TradeOrderRequest.buildMultiLegOrder("", Arrays.asList(LEG),
        ComboType.COVERED, ActionType.BUY, 10, OrderType.LMT, 1.0, null, null);
    Assert.assertNotNull(req);
  }

  // --- TradeOrderRequest: VWAP / WAP ---
  @Test public void testVWAPOrder() {
    long now = System.currentTimeMillis();
    TradeOrderRequest req = TradeOrderRequest.buildVWAPOrder(ACCOUNT, "AAPL", ActionType.BUY, 1000,
        now, now + 3600_000, 0.1, 155.0);
    assertValid(req, OrderType.VWAP);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals("VWAP", m.getAlgoStrategy());
    Assert.assertEquals(3, m.getAlgoParams().size());
  }
  @Test public void testWAPOrder_integerQuantity() {
    long now = System.currentTimeMillis();
    TradeOrderRequest req = TradeOrderRequest.buildWAPOrder(ACCOUNT, "AAPL", ActionType.BUY, 1000,
        OrderType.TWAP, now, now + 3600_000, null, 155.0);
    assertValid(req, OrderType.TWAP);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(SecType.STK, m.getSecType());
    Assert.assertEquals(Boolean.FALSE, m.getOutsideRth());
    Assert.assertEquals(2, m.getAlgoParams().size());
  }
  @Test public void testWAPOrder_longQuantity() {
    long now = System.currentTimeMillis();
    TradeOrderRequest req = TradeOrderRequest.buildWAPOrder(ACCOUNT, "AAPL", ActionType.SELL, 1000L, 0,
        OrderType.VWAP, now, now + 3600_000, 0.2, 160.0);
    assertValid(req, OrderType.VWAP);
  }
  @Test(expected = IllegalArgumentException.class) public void testWAPOrder_invalidOrderType_throws() {
    TradeOrderRequest.buildWAPOrder(ACCOUNT, "AAPL", ActionType.BUY, 1000, OrderType.LMT,
        1L, 2L, null, 1.0);
  }
  @Test public void testWAPOrder_emptyAccount_usesDefault() {
    long now = System.currentTimeMillis();
    Assert.assertNotNull(TradeOrderRequest.buildWAPOrder("", "AAPL", ActionType.BUY, 100,
        OrderType.TWAP, now, now + 1000, null, 1.0).getApiModel());
  }

  // --- TradeOrderRequest: OCA Brackets ---
  @Test public void testOCABracketsOrder_defaultAccount() {
    TradeOrderRequest req = TradeOrderRequest.buildOCABracketsOrder(STOCK, ActionType.BUY, 100,
        160.0, TimeInForce.DAY, true, 140.0, null, TimeInForce.DAY, false);
    Assert.assertNotNull(req);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertNotNull(m.getOcaOrders());
    Assert.assertEquals(2, m.getOcaOrders().size());
    Assert.assertEquals(OrderType.LMT, m.getOcaOrders().get(0).getOrderType());
    Assert.assertEquals(OrderType.STP, m.getOcaOrders().get(1).getOrderType());
  }
  @Test public void testOCABracketsOrder_withStopLimit() {
    TradeOrderRequest req = TradeOrderRequest.buildOCABracketsOrder(ACCOUNT, STOCK, ActionType.SELL, 100L, 0,
        160.0, TimeInForce.GTC, false, 140.0, 138.0, TimeInForce.DAY, true);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(OrderType.STP_LMT, m.getOcaOrders().get(1).getOrderType());
    Assert.assertEquals(Double.valueOf(138.0), m.getOcaOrders().get(1).getLimitPrice());
  }

  // --- TradeOrderRequest: attach order helpers ---
  @Test public void testAddProfitTakerOrder() {
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderRequest.addProfitTakerOrder(req, 160.0, TimeInForce.DAY, true);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(com.tigerbrokers.stock.openapi.client.struct.enums.AttachType.PROFIT, m.getAttachType());
    Assert.assertEquals(Double.valueOf(160.0), m.getProfitTakerPrice());
  }
  @Test public void testAddStopLossOrder() {
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderRequest.addStopLossOrder(req, 140.0, TimeInForce.DAY);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(OrderType.STP, m.getStopLossOrderType());
  }
  @Test public void testAddStopLossLimitOrder() {
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderRequest.addStopLossLimitOrder(req, 140.0, 138.0, TimeInForce.GTC);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(OrderType.STP_LMT, m.getStopLossOrderType());
    Assert.assertEquals(Double.valueOf(138.0), m.getStopLossLimitPrice());
  }
  @Test public void testAddStopLossTrailOrder() {
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderRequest.addStopLossTrailOrder(req, 5.0, 2.0, TimeInForce.DAY);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(OrderType.TRAIL, m.getStopLossOrderType());
    Assert.assertEquals(Double.valueOf(5.0), m.getStopLossTrailingPercent());
  }
  @Test public void testAddBracketsOrder_twoArgs() {
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderRequest.addBracketsOrder(req, 160.0, TimeInForce.DAY, true, 140.0, TimeInForce.DAY);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(com.tigerbrokers.stock.openapi.client.struct.enums.AttachType.BRACKETS, m.getAttachType());
  }
  @Test public void testAddBracketsOrder_threeArgs() {
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderRequest.addBracketsOrder(req, 160.0, TimeInForce.DAY, true, 140.0, 138.0, TimeInForce.DAY);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(Double.valueOf(138.0), m.getStopLossLimitPrice());
  }

  // --- TradeOrderRequest: instance setters chain ---
  @Test public void testInstanceSetters() {
    TradeOrderRequest req = TradeOrderRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.BUY, 100);
    req.setOrderId(1).setAccount("acct").setSecretKey("sk").setSymbol("GOOG")
       .setSecType(SecType.STK).setAction(ActionType.SELL).setCurrency(Currency.USD)
       .setTotalQuantity(200L).setTotalQuantityScale(0).setCashAmount(1000.0)
       .setOrderType(OrderType.LMT).setLimitPrice(150.0).setAdjustLimit(0.5)
       .setAuxPrice(140.0).setTrailingPercent(5.0).setOutsideRth(true)
       .setMarket("us").setExchange("SMART").setExpiry("20240119").setStrike("160")
       .setRight("CALL").setMultiplier(100f).setLocalSymbol("AAPL")
       .setAllocAccounts(Arrays.asList("a1")).setAllocShares(Arrays.asList(1.0))
       .setAlgoStrategy("TWAP").setAlgoParams(Arrays.asList(TagValue.buildTagValue("k","v")))
       .setUserMark("mark").withUserMark("mark2").setTimeInForce(TimeInForce.GTC)
       .setExpireTime(123L).setTradingSessionType(TradeSession.OverNight)
       .setTradingSessionType(TradingSessionType.OVERNIGHT).setLang(Language.zh_CN)
       .setDisplaySize(10).setMinDisplaySize(5).setCheckIntervals(60)
       .setPriceType("LIMIT_PRICE").setStartTime(1L).setEndTime(2L);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(Integer.valueOf(1), m.getOrderId());
    Assert.assertEquals("mark2", m.getUserMark());
    Assert.assertEquals(TradingSessionType.OVERNIGHT, m.getTradingSessionType());
    Assert.assertEquals(Language.zh_CN, m.getLang());
  }
  @Test public void testSetAuctionOrder() {
    TradeOrderRequest req = TradeOrderRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.BUY, 100);
    req.setAuctionOrder(OrderType.AM, TimeInForce.OPG);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(OrderType.AM, m.getOrderType());
    Assert.assertEquals(TimeInForce.OPG, m.getTimeInForce());
    // non-auction order type should be ignored
    req.setAuctionOrder(OrderType.LMT, TimeInForce.OPG);
    Assert.assertEquals(OrderType.AM, m.getOrderType());
  }
  @Test public void testSetTradingSessionTypeDeprecated_overNight() {
    TradeOrderRequest req = TradeOrderRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.BUY, 100);
    req.setTradingSessionType(TradeSession.OverNight);
    Assert.assertEquals(TradingSessionType.OVERNIGHT, ((TradeOrderModel)req.getApiModel()).getTradingSessionType());
  }
  @Test public void testSetTradingSessionTypeDeprecated_nonOverNight_noOp() {
    TradeOrderRequest req = TradeOrderRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.BUY, 100);
    req.setTradingSessionType(TradeSession.Regular);
    Assert.assertNull(((TradeOrderModel)req.getApiModel()).getTradingSessionType());
  }
  @Test public void testTradeOrderRequestGetResponseClass() {
    Assert.assertEquals(com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse.class,
        new TradeOrderRequest().getResponseClass());
  }

  // --- TradeOrderPreviewRequest: factory methods ---
  @Test public void testPreview_marketDefaultAccount() {
    assertPreviewValid(TradeOrderPreviewRequest.buildMarketOrder(STOCK, ActionType.BUY, 100), OrderType.MKT);
  }
  @Test public void testPreview_marketWithScale() {
    assertPreviewValid(TradeOrderPreviewRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.SELL, 50L, 0), OrderType.MKT);
  }
  @Test public void testPreview_marketWithScaleDefaultAccount() {
    assertPreviewValid(TradeOrderPreviewRequest.buildMarketOrder(STOCK, ActionType.BUY, 50L, 0), OrderType.MKT);
  }
  @Test public void testPreview_limitDefaultAccount() {
    assertPreviewValid(TradeOrderPreviewRequest.buildLimitOrder(STOCK, ActionType.BUY, 100, 150.0), OrderType.LMT);
  }
  @Test public void testPreview_limitWithAdjust() {
    assertPreviewValid(TradeOrderPreviewRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0, 0.5), OrderType.LMT);
  }
  @Test public void testPreview_limitWithScale() {
    assertPreviewValid(TradeOrderPreviewRequest.buildLimitOrder(STOCK, ActionType.BUY, 100L, 0, 150.0), OrderType.LMT);
  }
  @Test public void testPreview_limitWithScaleAdjust() {
    assertPreviewValid(TradeOrderPreviewRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100L, 0, 150.0, 0.5), OrderType.LMT);
  }
  @Test public void testPreview_stopDefaultAccount() {
    assertPreviewValid(TradeOrderPreviewRequest.buildStopOrder(STOCK, ActionType.SELL, 50, 140.0), OrderType.STP);
  }
  @Test public void testPreview_stopWithAdjust() {
    assertPreviewValid(TradeOrderPreviewRequest.buildStopOrder(ACCOUNT, STOCK, ActionType.SELL, 50, 140.0, 1.0), OrderType.STP);
  }
  @Test public void testPreview_stopWithScaleDefaultAccount() {
    assertPreviewValid(TradeOrderPreviewRequest.buildStopOrder(STOCK, ActionType.SELL, 50L, 0, 140.0), OrderType.STP);
  }
  @Test public void testPreview_stopWithScaleAdjust() {
    assertPreviewValid(TradeOrderPreviewRequest.buildStopOrder(ACCOUNT, STOCK, ActionType.SELL, 50L, 0, 140.0, 1.0), OrderType.STP);
  }
  @Test public void testPreview_stopLimitDefaultAccount() {
    assertPreviewValid(TradeOrderPreviewRequest.buildStopLimitOrder(STOCK, ActionType.BUY, 100, 155.0, 150.0), OrderType.STP_LMT);
  }
  @Test public void testPreview_stopLimitWithAdjust() {
    assertPreviewValid(TradeOrderPreviewRequest.buildStopLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 155.0, 150.0, 0.5), OrderType.STP_LMT);
  }
  @Test public void testPreview_stopLimitWithScaleDefaultAccount() {
    assertPreviewValid(TradeOrderPreviewRequest.buildStopLimitOrder(STOCK, ActionType.BUY, 100L, 0, 155.0, 150.0), OrderType.STP_LMT);
  }
  @Test public void testPreview_stopLimitWithScaleAdjust() {
    assertPreviewValid(TradeOrderPreviewRequest.buildStopLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100L, 0, 155.0, 150.0, 0.5), OrderType.STP_LMT);
  }
  @Test public void testPreview_trailDefaultAccount() {
    assertPreviewValid(TradeOrderPreviewRequest.buildTrailOrder(STOCK, ActionType.SELL, 50, 5.0, null), OrderType.TRAIL);
  }
  @Test public void testPreview_trailWithScaleDefaultAccount() {
    assertPreviewValid(TradeOrderPreviewRequest.buildTrailOrder(STOCK, ActionType.SELL, 50L, 0, 3.0, 145.0), OrderType.TRAIL);
  }
  @Test public void testPreview_trailWithScale() {
    assertPreviewValid(TradeOrderPreviewRequest.buildTrailOrder(ACCOUNT, STOCK, ActionType.SELL, 50L, 0, 3.0, 145.0), OrderType.TRAIL);
  }
  @Test public void testPreview_amountOrder() {
    assertPreviewValid(TradeOrderPreviewRequest.buildAmountOrder(ACCOUNT, STOCK, ActionType.BUY, 1000.0), OrderType.MKT);
  }
  @Test public void testPreview_multiLegInteger() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildMultiLegOrder(ACCOUNT, Arrays.asList(LEG),
        ComboType.VERTICAL, ActionType.BUY, 10, OrderType.LMT, 150.0, null, null);
    assertPreviewValid(req, OrderType.LMT);
    Assert.assertEquals(SecType.MLEG, ((TradeOrderModel)req.getApiModel()).getSecType());
  }
  @Test public void testPreview_multiLegLong() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildMultiLegOrder(ACCOUNT, Arrays.asList(LEG),
        ComboType.STRADDLE, ActionType.SELL, 10L, 0, OrderType.MKT, null, null, 5.0);
    assertPreviewValid(req, OrderType.MKT);
  }
  @Test(expected = IllegalArgumentException.class) public void testPreview_multiLegNullLegs_throws() {
    TradeOrderPreviewRequest.buildMultiLegOrder(ACCOUNT, null, ComboType.CUSTOM, ActionType.BUY, 10,
        OrderType.LMT, 1.0, null, null);
  }
  @Test(expected = IllegalArgumentException.class) public void testPreview_multiLegNullOrderType_throws() {
    TradeOrderPreviewRequest.buildMultiLegOrder(ACCOUNT, Arrays.asList(LEG), ComboType.CUSTOM, ActionType.BUY, 10,
        null, 1.0, null, null);
  }
  @Test public void testPreview_TWAPOrder() {
    long now = System.currentTimeMillis();
    assertPreviewValid(TradeOrderPreviewRequest.buildTWAPOrder(ACCOUNT, "AAPL", ActionType.BUY, 1000,
        now, now + 3600_000, 155.0), OrderType.TWAP);
  }
  @Test public void testPreview_VWAPOrder() {
    long now = System.currentTimeMillis();
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildVWAPOrder(ACCOUNT, "AAPL", ActionType.BUY, 1000,
        now, now + 3600_000, 0.1, 155.0);
    assertPreviewValid(req, OrderType.VWAP);
  }
  @Test public void testPreview_WAPOrderInteger() {
    long now = System.currentTimeMillis();
    assertPreviewValid(TradeOrderPreviewRequest.buildWAPOrder(ACCOUNT, "AAPL", ActionType.BUY, 1000,
        OrderType.TWAP, now, now + 3600_000, null, 155.0), OrderType.TWAP);
  }
  @Test public void testPreview_WAPOrderLong() {
    long now = System.currentTimeMillis();
    assertPreviewValid(TradeOrderPreviewRequest.buildWAPOrder(ACCOUNT, "AAPL", ActionType.SELL, 1000L, 0,
        OrderType.VWAP, now, now + 3600_000, 0.2, 160.0), OrderType.VWAP);
  }
  @Test(expected = IllegalArgumentException.class) public void testPreview_WAPOrderInvalidType_throws() {
    TradeOrderPreviewRequest.buildWAPOrder(ACCOUNT, "AAPL", ActionType.BUY, 1000, OrderType.LMT,
        1L, 2L, null, 1.0);
  }
  @Test public void testPreview_OCABracketsDefaultAccount() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildOCABracketsOrder(STOCK, ActionType.BUY, 100,
        160.0, TimeInForce.DAY, true, 140.0, null, TimeInForce.DAY, false);
    Assert.assertNotNull(req);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(2, m.getOcaOrders().size());
    Assert.assertEquals(OrderType.STP, m.getOcaOrders().get(1).getOrderType());
  }
  @Test public void testPreview_OCABracketsWithStopLimit() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildOCABracketsOrder(ACCOUNT, STOCK, ActionType.SELL, 100L, 0,
        160.0, TimeInForce.GTC, false, 140.0, 138.0, TimeInForce.DAY, true);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals(OrderType.STP_LMT, m.getOcaOrders().get(1).getOrderType());
  }

  // --- TradeOrderPreviewRequest: attach helpers + setters ---
  @Test public void testPreview_addProfitTaker() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderPreviewRequest.addProfitTakerOrder(req, 160.0, TimeInForce.DAY, true);
    Assert.assertEquals(Double.valueOf(160.0), ((TradeOrderModel)req.getApiModel()).getProfitTakerPrice());
  }
  @Test public void testPreview_addStopLoss() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderPreviewRequest.addStopLossOrder(req, 140.0, TimeInForce.DAY);
    Assert.assertEquals(OrderType.STP, ((TradeOrderModel)req.getApiModel()).getStopLossOrderType());
  }
  @Test public void testPreview_addStopLossLimit() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderPreviewRequest.addStopLossLimitOrder(req, 140.0, 138.0, TimeInForce.GTC);
    Assert.assertEquals(OrderType.STP_LMT, ((TradeOrderModel)req.getApiModel()).getStopLossOrderType());
  }
  @Test public void testPreview_addStopLossTrail() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderPreviewRequest.addStopLossTrailOrder(req, 5.0, 2.0, TimeInForce.DAY);
    Assert.assertEquals(OrderType.TRAIL, ((TradeOrderModel)req.getApiModel()).getStopLossOrderType());
  }
  @Test public void testPreview_addBracketsTwoArgs() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderPreviewRequest.addBracketsOrder(req, 160.0, TimeInForce.DAY, true, 140.0, TimeInForce.DAY);
    Assert.assertEquals(com.tigerbrokers.stock.openapi.client.struct.enums.AttachType.BRACKETS,
        ((TradeOrderModel)req.getApiModel()).getAttachType());
  }
  @Test public void testPreview_addBracketsThreeArgs() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100, 150.0);
    TradeOrderPreviewRequest.addBracketsOrder(req, 160.0, TimeInForce.DAY, true, 140.0, 138.0, TimeInForce.DAY);
    Assert.assertEquals(Double.valueOf(138.0), ((TradeOrderModel)req.getApiModel()).getStopLossLimitPrice());
  }
  @Test public void testPreview_instanceSetters() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.BUY, 100);
    req.setOrderId(1).setAccount("acct").setSecretKey("sk").setSymbol("GOOG")
       .setSecType(SecType.STK).setAction(ActionType.SELL).setCurrency(Currency.USD)
       .setTotalQuantity(200L).setTotalQuantityScale(0).setCashAmount(1000.0)
       .setOrderType(OrderType.LMT).setLimitPrice(150.0).setAdjustLimit(0.5)
       .setAuxPrice(140.0).setTrailingPercent(5.0).setOutsideRth(true)
       .setMarket("us").setExchange("SMART").setExpiry("20240119").setStrike("160")
       .setRight("CALL").setMultiplier(100f).setLocalSymbol("AAPL")
       .setAllocAccounts(Arrays.asList("a1")).setAllocShares(Arrays.asList(1.0))
       .setAlgoStrategy("TWAP").setAlgoParams(Collections.<TagValue>emptyList())
       .setAttachType(com.tigerbrokers.stock.openapi.client.struct.enums.AttachType.PROFIT)
       .setProfitTakerOrderId(2).setProfitTakerPrice(160.0).setProfitTakerTif(TimeInForce.DAY).setProfitTakerRth(true)
       .setStopLossOrderType(OrderType.STP).setStopLossOrderId(3).setStopLossPrice(140.0)
       .setStopLossLimitPrice(138.0).setStopLossTif(TimeInForce.GTC)
       .setStopLossTrailingPercent(5.0).setStopLossTrailingAmount(2.0)
       .setUserMark("mark").withUserMark("mark2").setTimeInForce(TimeInForce.GTC)
       .setExpireTime(123L).setTradingSessionType(TradeSession.OverNight)
       .setTradingSessionType(TradingSessionType.OVERNIGHT).setLang(Language.zh_CN)
       .setAuctionOrder(OrderType.AL, TimeInForce.OPG);
    TradeOrderModel m = (TradeOrderModel) req.getApiModel();
    Assert.assertEquals("mark2", m.getUserMark());
    Assert.assertEquals(OrderType.AL, m.getOrderType());
  }
  @Test public void testPreview_setTradingSessionTypeDeprecated_overNight() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.BUY, 100);
    req.setTradingSessionType(TradeSession.OverNight);
    Assert.assertEquals(TradingSessionType.OVERNIGHT, ((TradeOrderModel)req.getApiModel()).getTradingSessionType());
  }
  @Test public void testPreview_auctionOrderNonAuctionIgnored() {
    TradeOrderPreviewRequest req = TradeOrderPreviewRequest.buildMarketOrder(ACCOUNT, STOCK, ActionType.BUY, 100);
    req.setAuctionOrder(OrderType.LMT, TimeInForce.OPG);
    Assert.assertEquals(OrderType.MKT, ((TradeOrderModel)req.getApiModel()).getOrderType());
  }
  @Test public void testPreview_getResponseClass() {
    Assert.assertEquals(com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderPreviewResponse.class,
        new TradeOrderPreviewRequest().getResponseClass());
  }

  // --- TradeOrderRequest: default-account convenience overloads ---
  @Test public void testMarketOrder_defaultAccount() {
    assertValid(TradeOrderRequest.buildMarketOrder(STOCK, ActionType.BUY, 100), OrderType.MKT);
  }
  @Test public void testMarketOrder_defaultAccountWithScale() {
    assertValid(TradeOrderRequest.buildMarketOrder(STOCK, ActionType.SELL, 50L, 0), OrderType.MKT);
  }
  @Test public void testLimitOrder_defaultAccount() {
    assertValid(TradeOrderRequest.buildLimitOrder(STOCK, ActionType.BUY, 100, 150.5), OrderType.LMT);
  }
  @Test public void testLimitOrder_defaultAccountWithScale() {
    assertValid(TradeOrderRequest.buildLimitOrder(STOCK, ActionType.BUY, 100L, 0, 150.5), OrderType.LMT);
  }
  @Test public void testStopOrder_defaultAccount() {
    assertValid(TradeOrderRequest.buildStopOrder(STOCK, ActionType.SELL, 50, 140.0), OrderType.STP);
  }
  @Test public void testStopOrder_defaultAccountWithScale() {
    assertValid(TradeOrderRequest.buildStopOrder(STOCK, ActionType.SELL, 50L, 0, 140.0), OrderType.STP);
  }
  @Test public void testStopOrder_accountWithScaleDefaultAdjust() {
    assertValid(TradeOrderRequest.buildStopOrder(ACCOUNT, STOCK, ActionType.SELL, 50L, 0, 140.0), OrderType.STP);
  }
  @Test public void testStopOrder_accountWithScaleAdjust() {
    assertValid(TradeOrderRequest.buildStopOrder(ACCOUNT, STOCK, ActionType.SELL, 50L, 0, 140.0, 1.0), OrderType.STP);
  }
  @Test public void testStopLimitOrder_defaultAccount() {
    assertValid(TradeOrderRequest.buildStopLimitOrder(STOCK, ActionType.BUY, 100, 155.0, 150.0), OrderType.STP_LMT);
  }
  @Test public void testStopLimitOrder_defaultAccountWithScale() {
    assertValid(TradeOrderRequest.buildStopLimitOrder(STOCK, ActionType.BUY, 100L, 0, 155.0, 150.0), OrderType.STP_LMT);
  }
  @Test public void testStopLimitOrder_accountWithScaleDefaultAdjust() {
    assertValid(TradeOrderRequest.buildStopLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100L, 0, 155.0, 150.0), OrderType.STP_LMT);
  }
  @Test public void testStopLimitOrder_accountWithScaleAdjust() {
    assertValid(TradeOrderRequest.buildStopLimitOrder(ACCOUNT, STOCK, ActionType.BUY, 100L, 0, 155.0, 150.0, 0.5), OrderType.STP_LMT);
  }
  @Test public void testTrailOrder_defaultAccount() {
    assertValid(TradeOrderRequest.buildTrailOrder(STOCK, ActionType.SELL, 50, 5.0, null), OrderType.TRAIL);
  }
  @Test public void testTrailOrder_defaultAccountWithScale() {
    assertValid(TradeOrderRequest.buildTrailOrder(STOCK, ActionType.SELL, 50L, 0, 3.0, 145.0), OrderType.TRAIL);
  }
  @Test public void testTrailOrder_accountWithScale() {
    assertValid(TradeOrderRequest.buildTrailOrder(ACCOUNT, STOCK, ActionType.SELL, 50L, 0, 3.0, 145.0), OrderType.TRAIL);
  }
}
