package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.DepositWithdrawModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.EstimateTradableQuantityModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.ForexTradeOrderModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.OptionExerciseCheckModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.OptionExerciseRecordModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PrimeAnalyticsAssetModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PositionTransferModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.SegmentFundModel;
import com.tigerbrokers.stock.openapi.client.https.response.trade.AggregateAssetResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.BatchOrderResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.DepositWithdrawResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.EstimateTradableQuantityResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.ForexTradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.FundDetailsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseCancelResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseCheckResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExercisePositionResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseRecordResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseSubmitResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferDetailResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferExternalRecordsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferRecordsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAnalyticsAssetResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAssetResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundAvailableResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SingleOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionExerciseType;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public class TradeRequestTest {
  private static final String ACCOUNT = "testAccount";

  @Test public void testAggregateAssetRequest() {
    Assert.assertEquals(MethodName.AGGREGATE_ASSETS, new AggregateAssetRequest().getApiMethodName());
    Assert.assertEquals(AggregateAssetResponse.class, new AggregateAssetRequest().getResponseClass());
    AggregateAssetRequest req = AggregateAssetRequest.buildAggregateAssetRequest(ACCOUNT, "ALL");
    Assert.assertNotNull(req.getApiModel());
    Assert.assertEquals(MethodName.AGGREGATE_ASSETS, req.getApiMethodName());
    Assert.assertEquals(AggregateAssetResponse.class, req.getResponseClass());
    Assert.assertNotNull(AggregateAssetRequest.buildAggregateAssetRequest(ACCOUNT, "ALL", "secret").getApiModel());
    Assert.assertNotNull(AggregateAssetRequest.buildAggregateAssetRequest(ACCOUNT, "ALL", "secret", Currency.USD).getApiModel());
  }
  @Test public void testDepositWithdrawRequest() {
    DepositWithdrawRequest req = DepositWithdrawRequest.newRequest();
    Assert.assertEquals(MethodName.TRANSFER_FUND, req.getApiMethodName());
    Assert.assertEquals(DepositWithdrawResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
    Assert.assertNotNull(DepositWithdrawRequest.newRequest(new DepositWithdrawModel()).getApiModel());
  }
  @Test public void testEstimateTradableQuantityRequest() {
    EstimateTradableQuantityRequest req = EstimateTradableQuantityRequest.buildRequest(SecType.STK, "AAPL", ActionType.BUY, OrderType.LMT, 150.0, null);
    Assert.assertEquals(MethodName.ESTIMATE_TRADABLE_QUANTITY, req.getApiMethodName());
    Assert.assertEquals(EstimateTradableQuantityResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
    Assert.assertNotNull(EstimateTradableQuantityRequest.newRequest(new EstimateTradableQuantityModel()).getApiModel());
    Assert.assertNotNull(EstimateTradableQuantityRequest.buildRequest(ACCOUNT, SecType.STK, "AAPL", ActionType.BUY, OrderType.LMT, 150.0, null).getApiModel());
  }
  @Test public void testForexTradeOrderRequest() {
    ForexTradeOrderRequest req = ForexTradeOrderRequest.buildRequest(SegmentType.SEC, Currency.USD, 1000.0, Currency.HKD);
    Assert.assertEquals(MethodName.PLACE_FOREX_ORDER, req.getApiMethodName());
    Assert.assertEquals(ForexTradeOrderResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
    Assert.assertNotNull(ForexTradeOrderRequest.newRequest(new ForexTradeOrderModel()).getApiModel());
    Assert.assertNotNull(ForexTradeOrderRequest.buildRequest(ACCOUNT, SegmentType.SEC, Currency.USD, 1000.0, Currency.HKD).getApiModel());
  }
  @Test public void testFundDetailsRequest() {
    Assert.assertEquals(MethodName.FUND_DETAILS, new FundDetailsRequest().getApiMethodName());
    Assert.assertEquals(FundDetailsResponse.class, new FundDetailsRequest().getResponseClass());
    Assert.assertNotNull(FundDetailsRequest.buildFundDetailsRequest(ACCOUNT, Arrays.asList("ALL")).getApiModel());
    Assert.assertNotNull(FundDetailsRequest.buildFundDetailsRequest(ACCOUNT, Arrays.asList("ALL"), "secret").getApiModel());
    Assert.assertNotNull(FundDetailsRequest.buildFundDetailsRequest(ACCOUNT, Arrays.asList("ALL"), "fundType", "secret").getApiModel());
    Assert.assertNotNull(FundDetailsRequest.buildFundDetailsRequest(ACCOUNT, Arrays.asList("ALL"), 0L, 10L).getApiModel());
    Assert.assertNotNull(FundDetailsRequest.buildFundDetailsRequest(ACCOUNT, Arrays.asList("ALL"), 0L, 10L, "secret").getApiModel());
  }
  @Test public void testOptionExerciseCancelRequest() {
    OptionExerciseCancelRequest req = OptionExerciseCancelRequest.buildRequest(ACCOUNT, 1L);
    Assert.assertEquals(MethodName.OPTION_EXERCISE_CANCEL, req.getApiMethodName());
    Assert.assertEquals(OptionExerciseCancelResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
  }
  @Test public void testOptionExerciseCheckRequest() {
    OptionExerciseCheckRequest req = OptionExerciseCheckRequest.buildRequest(ACCOUNT, 1L, OptionExerciseType.Exercise);
    Assert.assertEquals(MethodName.OPTION_EXERCISE_CHECK, req.getApiMethodName());
    Assert.assertEquals(OptionExerciseCheckResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
  }
  @Test public void testOptionExercisePositionRequest() {
    OptionExercisePositionRequest req = OptionExercisePositionRequest.buildRequest(ACCOUNT, OptionExerciseType.Exercise);
    Assert.assertEquals(MethodName.OPTION_EXERCISE_POSITION, req.getApiMethodName());
    Assert.assertEquals(OptionExercisePositionResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
  }
  @Test public void testOptionExerciseRecordRequest() {
    OptionExerciseRecordRequest req = OptionExerciseRecordRequest.buildRequest(ACCOUNT, 1, 10);
    Assert.assertEquals(MethodName.OPTION_EXERCISE_RECORD, req.getApiMethodName());
    Assert.assertEquals(OptionExerciseRecordResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
  }
  @Test public void testOptionExerciseSubmitRequest() {
    OptionExerciseSubmitRequest exerciseReq = OptionExerciseSubmitRequest.buildExerciseRequest(ACCOUNT, 1L, 1.0, "2024-01-19", false);
    Assert.assertEquals(MethodName.OPTION_EXERCISE_SUBMIT, exerciseReq.getApiMethodName());
    Assert.assertEquals(OptionExerciseSubmitResponse.class, exerciseReq.getResponseClass());
    Assert.assertNotNull(exerciseReq.getApiModel());
    OptionExerciseSubmitRequest expireReq = OptionExerciseSubmitRequest.buildExpireRequest(ACCOUNT, 1L, 1.0, 50);
    Assert.assertEquals(MethodName.OPTION_EXERCISE_SUBMIT, expireReq.getApiMethodName());
    Assert.assertNotNull(expireReq.getApiModel());
  }
  @Test public void testPositionsRequest() {
    Assert.assertEquals(MethodName.POSITIONS, new PositionsRequest().getApiMethodName());
    Assert.assertEquals(PositionsResponse.class, new PositionsRequest().getResponseClass());
  }
  @Test(expected = UnsupportedOperationException.class) public void testPositionsRequestSetApiModelThrows() {
    new PositionsRequest().setApiModel(null);
  }
  @Test public void testPositionTransferDetailRequest() {
    PositionTransferDetailRequest req = PositionTransferDetailRequest.buildRequest(1L, ACCOUNT);
    Assert.assertEquals(MethodName.POSITION_TRANSFER_DETAIL, req.getApiMethodName());
    Assert.assertEquals(PositionTransferDetailResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
  }
  @Test public void testPositionTransferExternalRecordsRequest() {
    PositionTransferExternalRecordsRequest req = PositionTransferExternalRecordsRequest.buildRequest(ACCOUNT, "2024-01-01", "2024-06-30");
    Assert.assertEquals(MethodName.POSITION_TRANSFER_EXTERNAL_RECORDS, req.getApiMethodName());
    Assert.assertEquals(PositionTransferExternalRecordsResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
    Assert.assertNotNull(PositionTransferExternalRecordsRequest.buildRequest(ACCOUNT, "2024-01-01", "2024-06-30", "done", "US", "AAPL").getApiModel());
  }
  @Test public void testPositionTransferRecordsRequest() {
    PositionTransferRecordsRequest req = PositionTransferRecordsRequest.buildRequest(ACCOUNT, "2024-01-01", "2024-06-30");
    Assert.assertEquals(MethodName.POSITION_TRANSFER_RECORDS, req.getApiMethodName());
    Assert.assertEquals(PositionTransferRecordsResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
    Assert.assertNotNull(PositionTransferRecordsRequest.buildRequest(ACCOUNT, "2024-01-01", "2024-06-30", "done", "US", "AAPL").getApiModel());
  }
  @Test public void testPositionTransferRequest() {
    PositionTransferModel.Transfer transfer = new PositionTransferModel.Transfer("AAPL", 100L);
    PositionTransferRequest req = PositionTransferRequest.buildRequest(ACCOUNT, "targetAcct", "US", Arrays.asList(transfer));
    Assert.assertEquals(MethodName.POSITION_TRANSFER, req.getApiMethodName());
    Assert.assertEquals(PositionTransferResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
  }
  @Test public void testPositionTransferRequestEmptyTransfers() {
    Assert.assertNotNull(PositionTransferRequest.buildRequest(ACCOUNT, "targetAcct", "US", Collections.emptyList()).getApiModel());
  }
  @Test public void testPrimeAnalyticsAssetRequest() {
    Assert.assertEquals(MethodName.ANALYTICS_ASSET, new PrimeAnalyticsAssetRequest().getApiMethodName());
    Assert.assertEquals(PrimeAnalyticsAssetResponse.class, new PrimeAnalyticsAssetRequest().getResponseClass());
    Assert.assertNotNull(PrimeAnalyticsAssetRequest.buildPrimeAnalyticsAssetRequest().getApiModel());
    Assert.assertNotNull(PrimeAnalyticsAssetRequest.buildPrimeAnalyticsAssetRequest(ACCOUNT).getApiModel());
    Assert.assertNotNull(PrimeAnalyticsAssetRequest.buildPrimeAnalyticsAssetRequest(ACCOUNT, "secret").getApiModel());
  }
  @Test public void testPrimeAssetRequest() {
    Assert.assertEquals(MethodName.PRIME_ASSETS, new PrimeAssetRequest().getApiMethodName());
    Assert.assertEquals(PrimeAssetResponse.class, new PrimeAssetRequest().getResponseClass());
    Assert.assertNotNull(PrimeAssetRequest.buildPrimeAssetRequest(ACCOUNT).getApiModel());
    Assert.assertNotNull(PrimeAssetRequest.buildPrimeAssetRequest(ACCOUNT, "secret").getApiModel());
    Assert.assertNotNull(PrimeAssetRequest.buildPrimeAssetRequest(ACCOUNT, Currency.USD).getApiModel());
    Assert.assertNotNull(PrimeAssetRequest.buildPrimeAssetRequest(ACCOUNT, Currency.USD, "secret").getApiModel());
  }
  @Test public void testQueryOrderRequest() {
    Assert.assertEquals(MethodName.ORDERS, new QueryOrderRequest().getApiMethodName());
    Assert.assertEquals(BatchOrderResponse.class, new QueryOrderRequest().getResponseClass());
    QueryOrderRequest customMethod = new QueryOrderRequest(MethodName.FILLED_ORDERS);
    Assert.assertEquals(MethodName.FILLED_ORDERS, customMethod.getApiMethodName());
  }
  @Test(expected = UnsupportedOperationException.class) public void testQueryOrderRequestSetApiModelThrows() {
    new QueryOrderRequest().setApiModel(null);
  }
  @Test public void testQuerySingleOrderRequest() {
    Assert.assertEquals(MethodName.ORDERS, new QuerySingleOrderRequest().getApiMethodName());
    Assert.assertEquals(SingleOrderResponse.class, new QuerySingleOrderRequest().getResponseClass());
  }
  @Test(expected = UnsupportedOperationException.class) public void testQuerySingleOrderRequestSetApiModelThrows() {
    new QuerySingleOrderRequest().setApiModel(null);
  }
  @Test public void testSegmentFundAvailableRequest() {
    SegmentFundAvailableRequest req = SegmentFundAvailableRequest.buildRequest(SegmentType.SEC, Currency.USD);
    Assert.assertEquals(MethodName.SEGMENT_FUND_AVAILABLE, req.getApiMethodName());
    Assert.assertEquals(SegmentFundAvailableResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
    Assert.assertNotNull(SegmentFundAvailableRequest.newRequest(new SegmentFundModel()).getApiModel());
    Assert.assertNotNull(SegmentFundAvailableRequest.buildRequest(ACCOUNT, SegmentType.SEC, Currency.USD).getApiModel());
  }
  @Test public void testSegmentFundCancelRequest() {
    SegmentFundCancelRequest req = SegmentFundCancelRequest.buildRequest(1L);
    Assert.assertEquals(MethodName.CANCEL_SEGMENT_FUND, req.getApiMethodName());
    Assert.assertEquals(SegmentFundResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
    Assert.assertNotNull(SegmentFundCancelRequest.newRequest(new SegmentFundModel()).getApiModel());
    Assert.assertNotNull(SegmentFundCancelRequest.buildRequest(ACCOUNT, 1L).getApiModel());
  }
  @Test public void testSegmentFundHistoryRequest() {
    SegmentFundHistoryRequest req = SegmentFundHistoryRequest.buildRequest(10);
    Assert.assertEquals(MethodName.SEGMENT_FUND_HISTORY, req.getApiMethodName());
    Assert.assertEquals(SegmentFundsResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
    Assert.assertNotNull(SegmentFundHistoryRequest.newRequest(new SegmentFundModel()).getApiModel());
    Assert.assertNotNull(SegmentFundHistoryRequest.buildRequest(ACCOUNT, 10).getApiModel());
  }
  @Test public void testSegmentFundTransferRequest() {
    SegmentFundTransferRequest req = SegmentFundTransferRequest.buildRequest(SegmentType.SEC, SegmentType.FUT, Currency.USD, 100.0);
    Assert.assertEquals(MethodName.TRANSFER_SEGMENT_FUND, req.getApiMethodName());
    Assert.assertEquals(SegmentFundResponse.class, req.getResponseClass());
    Assert.assertNotNull(req.getApiModel());
    Assert.assertNotNull(SegmentFundTransferRequest.newRequest(new SegmentFundModel()).getApiModel());
    Assert.assertNotNull(SegmentFundTransferRequest.buildRequest(ACCOUNT, SegmentType.SEC, SegmentType.FUT, Currency.USD, 100.0).getApiModel());
  }
  @Test public void testTransferModelNoArg() {
    PositionTransferModel.Transfer t = new PositionTransferModel.Transfer();
    Assert.assertNull(t.getSymbol());
    PositionTransferModel.Transfer t2 = new PositionTransferModel.Transfer("AAPL", 50L);
    Assert.assertEquals("AAPL", t2.getSymbol());
    Assert.assertEquals(Long.valueOf(50L), t2.getQuantity());
  }

  @Test public void testEstimateTradableQuantityRequest_builders() {
    EstimateTradableQuantityRequest req = EstimateTradableQuantityRequest.buildRequest(ACCOUNT,
        SecType.STK, "AAPL", ActionType.BUY, OrderType.LMT, 150.0, null);
    Assert.assertNotNull(req.getApiModel());
    EstimateTradableQuantityRequest chained = EstimateTradableQuantityRequest.buildRequest(
        SecType.STK, "AAPL", ActionType.BUY, OrderType.LMT, 150.0, null)
        .account(ACCOUNT).secretKey("sk").segType(SegmentType.SEC).secType(SecType.OPT)
        .symbol("AAPL").expiry("20240119").strike("160").right("CALL")
        .action(ActionType.SELL).orderType(OrderType.STP).limitPrice(155.0).stopPrice(150.0)
        .lang(com.tigerbrokers.stock.openapi.client.struct.enums.Language.zh_CN);
    Assert.assertNotNull(chained.getApiModel());
    Assert.assertEquals("sk", ((EstimateTradableQuantityModel)chained.getApiModel()).getSecretKey());
  }

  @Test public void testForexTradeOrderRequest_builders() {
    ForexTradeOrderRequest req = ForexTradeOrderRequest.buildRequest(ACCOUNT,
        SegmentType.SEC, Currency.USD, 1000.0, Currency.HKD);
    Assert.assertNotNull(req.getApiModel());
    // test ALL/null branch handling
    ForexTradeOrderRequest allReq = ForexTradeOrderRequest.buildRequest(
        SegmentType.ALL, Currency.ALL, 500.0, Currency.ALL);
    Assert.assertNotNull(allReq.getApiModel());
    ForexTradeOrderRequest nullReq = ForexTradeOrderRequest.buildRequest(
        null, null, 500.0, null);
    Assert.assertNotNull(nullReq.getApiModel());
    // chain builders - ALL/null clears values
    ForexTradeOrderRequest chained = ForexTradeOrderRequest.buildRequest(ACCOUNT,
        SegmentType.SEC, Currency.USD, 1000.0, Currency.HKD)
        .segType(SegmentType.FUT).sourceCurrency(Currency.HKD).targetCurrency(Currency.USD)
        .sourceAmount(2000.0).timeInForce(com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce.GTC)
        .externalId("ext1").account("acct2").secretKey("sk")
        .lang(com.tigerbrokers.stock.openapi.client.struct.enums.Language.zh_CN);
    Assert.assertNotNull(chained.getApiModel());
    // ALL branches clear
    ForexTradeOrderRequest cleared = ForexTradeOrderRequest.buildRequest(ACCOUNT,
        SegmentType.SEC, Currency.USD, 1000.0, Currency.HKD)
        .segType(SegmentType.ALL).sourceCurrency(Currency.ALL).targetCurrency(Currency.ALL);
    ForexTradeOrderModel m = (ForexTradeOrderModel) cleared.getApiModel();
    Assert.assertNull(m.getSegType());
    Assert.assertNull(m.getSourceCurrency());
    Assert.assertNull(m.getTargetCurrency());
  }

  @Test public void testSegmentFundTransferRequest_builders() {
    SegmentFundTransferRequest req = SegmentFundTransferRequest.buildRequest(ACCOUNT,
        SegmentType.SEC, SegmentType.FUT, Currency.USD, 100.0);
    Assert.assertNotNull(req.getApiModel());
    // ALL branch handling
    SegmentFundTransferRequest allReq = SegmentFundTransferRequest.buildRequest(
        SegmentType.ALL, SegmentType.ALL, Currency.USD, 100.0);
    SegmentFundModel allModel = (SegmentFundModel) allReq.getApiModel();
    Assert.assertNull(allModel.getFromSegment());
    Assert.assertNull(allModel.getToSegment());
    // null currency
    SegmentFundTransferRequest nullCurReq = SegmentFundTransferRequest.buildRequest(
        SegmentType.SEC, SegmentType.FUT, null, 100.0);
    Assert.assertNotNull(nullCurReq.getApiModel());
    // chain builders
    SegmentFundTransferRequest chained = SegmentFundTransferRequest.buildRequest(
        SegmentType.SEC, SegmentType.FUT, Currency.USD, 100.0)
        .fromSegmentType(SegmentType.FUT).toSegmentType(SegmentType.SEC)
        .currency(Currency.HKD).amount(200.0).account("acct2").secretKey("sk")
        .lang(com.tigerbrokers.stock.openapi.client.struct.enums.Language.zh_CN);
    SegmentFundModel m = (SegmentFundModel) chained.getApiModel();
    Assert.assertEquals("FUT", m.getFromSegment());
    Assert.assertEquals("SEC", m.getToSegment());
    Assert.assertEquals(Double.valueOf(200.0), m.getAmount());
    // ALL chain clears
    chained.fromSegmentType(SegmentType.ALL).toSegmentType(SegmentType.ALL).currency(null);
    Assert.assertNull(m.getFromSegment());
    Assert.assertNull(m.getToSegment());
    Assert.assertNull(m.getCurrency());
  }

  @Test public void testFundDetailsRequest_builders() {
    FundDetailsRequest req = FundDetailsRequest.buildFundDetailsRequest(ACCOUNT, Arrays.asList("ALL"));
    req.setSegTypes(Arrays.asList("SEC")).setStart(0L).setLimit(10L).setCurrency("USD")
       .setStartDate("2024-01-01").setEndDate("2024-06-30").setFundType("cash");
    Assert.assertNotNull(req.getApiModel());
    Assert.assertEquals("cash", req.getApiModel().getFundType());
    Assert.assertEquals("USD", req.getApiModel().getCurrency());
    // getApiModel lazy init when null
    FundDetailsRequest lazyReq = new FundDetailsRequest();
    Assert.assertNotNull(lazyReq.getApiModel());
  }

  @Test public void testOptionExerciseCheckRequest_builders() {
    OptionExerciseCheckRequest req = OptionExerciseCheckRequest.buildRequest(ACCOUNT, 1L, OptionExerciseType.Exercise);
    req.setQuantity(1.0).setExecutingDate("2024-01-19").setIsForce(false).setItmRate(80).setSecretKey("sk");
    Assert.assertNotNull(req.getApiModel());
    Assert.assertEquals(Double.valueOf(1.0), ((OptionExerciseCheckModel)req.getApiModel()).getQuantity());
    Assert.assertEquals("sk", ((OptionExerciseCheckModel)req.getApiModel()).getSecretKey());
  }

  @Test public void testOptionExerciseRecordRequest_builders() {
    OptionExerciseRecordRequest req = OptionExerciseRecordRequest.buildRequest(ACCOUNT, 1, 10);
    req.setStatus("done").setType("exercise").setSymbol("AAPL").setOrderBy("create_time").setSecretKey("sk");
    Assert.assertNotNull(req.getApiModel());
    Assert.assertEquals("done", ((OptionExerciseRecordModel)req.getApiModel()).getStatus());
    Assert.assertEquals("sk", ((OptionExerciseRecordModel)req.getApiModel()).getSecretKey());
  }

  @Test public void testPrimeAnalyticsAssetRequest_builders() {
    PrimeAnalyticsAssetRequest req = PrimeAnalyticsAssetRequest.buildPrimeAnalyticsAssetRequest(ACCOUNT, "sk");
    req.startDate("2024-01-01").endDate("2024-06-30")
       .segType(SegmentType.SEC).currency(Currency.USD).subAccount("sub1");
    Assert.assertNotNull(req.getApiModel());
    // long-based startDate/endDate with timezone
    PrimeAnalyticsAssetRequest req2 = PrimeAnalyticsAssetRequest.buildPrimeAnalyticsAssetRequest(ACCOUNT, "sk");
    req2.startDate(1704067200000L).endDate(1718668800000L);
    Assert.assertNotNull(((PrimeAnalyticsAssetModel)req2.getApiModel()).getStartDate());
    Assert.assertNotNull(((PrimeAnalyticsAssetModel)req2.getApiModel()).getEndDate());
    // explicit timezone + null handling
    PrimeAnalyticsAssetRequest req3 = PrimeAnalyticsAssetRequest.buildPrimeAnalyticsAssetRequest(ACCOUNT, "sk");
    req3.startDate(1704067200000L, TimeZoneId.NewYork).endDate(1718668800000L, TimeZoneId.Shanghai);
    Assert.assertNotNull(((PrimeAnalyticsAssetModel)req3.getApiModel()).getStartDate());
    // null long date should not set
    PrimeAnalyticsAssetRequest req4 = PrimeAnalyticsAssetRequest.buildPrimeAnalyticsAssetRequest(ACCOUNT, "sk");
    req4.startDate((Long) null).endDate((Long) null);
    Assert.assertNull(((PrimeAnalyticsAssetModel)req4.getApiModel()).getStartDate());
    Assert.assertNull(((PrimeAnalyticsAssetModel)req4.getApiModel()).getEndDate());
  }
}
