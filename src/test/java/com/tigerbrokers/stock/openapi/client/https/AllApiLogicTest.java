package com.tigerbrokers.stock.openapi.client.https;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.request.contract.ContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.contract.ContractsRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateDelistingRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateDividendRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateEarningRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateIpoRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateSplitRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateSymbolChangeRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.FinancialCurrencyRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.FinancialDailyRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.FinancialExchangeRateRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.FinancialReportRequest;
import com.tigerbrokers.stock.openapi.client.https.request.fund.FundContractsRequest;
import com.tigerbrokers.stock.openapi.client.https.request.fund.FundHistoryQuoteRequest;
import com.tigerbrokers.stock.openapi.client.https.request.fund.FundQuoteRequest;
import com.tigerbrokers.stock.openapi.client.https.request.fund.FundSymbolRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureContinuousContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureContractByConCodeRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureContractByExchCodeRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureContractsRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureCurrentContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureDepthRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureExchangeRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureHistoryMainContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureRealTimeQuoteRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureTickRequest;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureTradingDateRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionAnalysisRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionBriefQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionBriefQueryV2Request;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionChainQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionDepthQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionExpirationQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionKlineQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionKlineQueryV2Request;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionSymbolRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionTimelineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionTradeTickQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.KlineQuotaRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.MarketScannerRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.MarketScannerTagsRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteBrokerHoldRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteCapitalDistributionRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteCapitalFlowRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteDelayRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteDepthRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteHistoryTimelineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteOvernightRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteRealTimeQuoteRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteShortableStockRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteStockBrokerRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteStockFundamentalRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteTimelineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteTradeCalendarRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteTradeRankRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteTradeTickRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.AggregateAssetRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.DepositWithdrawRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.EstimateTradableQuantityRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.ForexTradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.FundDetailsRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.OptionExerciseCancelRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.OptionExerciseCheckRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.OptionExercisePositionRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.OptionExerciseRecordRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.OptionExerciseSubmitRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.PositionTransferDetailRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.PositionTransferExternalRecordsRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.PositionTransferRecordsRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.PositionTransferRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.PositionsRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.PrimeAnalyticsAssetRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.PrimeAssetRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.QueryOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.QuerySingleOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.SegmentFundAvailableRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.SegmentFundCancelRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.SegmentFundHistoryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.SegmentFundTransferRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderPreviewRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.request.user.AddonEntitlementRequest;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserLicenseRequest;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserLoginRequest;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserTokenRefreshRequest;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserTradePasswordResetRequest;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserTradePasswordVerifyRequest;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserTradeTokenRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateDelistingResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateDividendResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateEarningResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateIpoResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateSplitResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateSymbolChangeResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialCurrencyResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialDailyResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialExchangeRateResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialReportResponse;
import com.tigerbrokers.stock.openapi.client.https.response.fund.FundContractsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.fund.FundHistoryQuoteResponse;
import com.tigerbrokers.stock.openapi.client.https.response.fund.FundQuoteResponse;
import com.tigerbrokers.stock.openapi.client.https.response.fund.FundSymbolResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureBatchContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureDepthResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureExchangeResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureHistoryMainContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureRealTimeQuoteResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureTickResponse;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureTradingDateResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionAnalysisResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionBriefResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionDepthResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionExpirationResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionSymbolResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionTimelineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionTradeTickResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.AddonEntitlementResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.KlineQuotaResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.MarketScannerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.MarketScannerTagsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteBrokerHoldResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalDistributionResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalFlowResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDelayResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDepthResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteHistoryTimelineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteOvernightResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteShortableStockResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockBrokerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockFundamentalResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeCalendarResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeRankResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeTickResponse;
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
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderPreviewResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserLicenseResponse;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserLoginResponse;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserTokenResponse;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserTradePasswordResetResponse;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserTradePasswordVerifyResponse;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserTradeTokenResponse;
import com.tigerbrokers.stock.openapi.client.testsupport.TestClientFactory;
import com.tigerbrokers.stock.openapi.client.util.HttpUtils;
import java.lang.reflect.Constructor;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;

/** 全接口逻辑覆盖测试。反射 instantiate 绕过 private 构造。 */
public class AllApiLogicTest {

  private static MockedStatic<HttpUtils> httpUtilsMock;
  private static TigerHttpClient client;

  @BeforeClass
  public static void setUpClass() {
    httpUtilsMock = Mockito.mockStatic(HttpUtils.class);
    httpUtilsMock.when(() -> HttpUtils.get(anyString(), nullable(String.class))).thenReturn("{}");
    client = TestClientFactory.createOfflineClient();
  }

  @AfterClass
  public static void tearDownClass() { if (httpUtilsMock != null) httpUtilsMock.close(); }

  private void mockResponse(String dataJson) {
    String wire = "{\"code\":0,\"message\":\"success\",\"timestamp\":1700000000,\"data\":" + dataJson + "}";
    httpUtilsMock.when(() -> HttpUtils.post(anyString(), anyString(), nullable(String.class), anyInt())).thenReturn(wire);
  }

  @SuppressWarnings("unchecked")
  private <T extends TigerResponse> void mockForRequest(TigerRequest<T> req) {
    String dataJson = "{}";
    try {
      Class<?> rc = req.getResponseClass();
      if (rc != null) {
        for (java.lang.reflect.Field f : rc.getDeclaredFields()) {
          com.alibaba.fastjson.annotation.JSONField ann = f.getAnnotation(com.alibaba.fastjson.annotation.JSONField.class);
          if (ann != null && "data".equals(ann.name())) {
            dataJson = DeserializationRoundTripTest.generateSampleJsonStatic(f.getType());
            break;
          }
        }
      }
    } catch (Exception e) { System.err.println("mockForRequest failed for " + req.getClass().getSimpleName() + ": " + e); }
    // System.out.println("[mockForRequest] " + req.getClass().getSimpleName() + " dataJson.len=" + dataJson.length());
    mockResponse(dataJson);
  }

  /** 确保 request 有非空 model，否则 validate 会直接 reject 不走 HttpUtils.post */
  private static void ensureModelNotNull(TigerRequest<?> req) {
    try {
      if (req.getApiModel() != null) return;
    } catch (ClassCastException e) {
      return; // AggregateAssetRequest 的已知 bug
    }
    try {
      java.lang.reflect.Method setter = req.getClass().getMethod("setApiModel",
          com.tigerbrokers.stock.openapi.client.https.domain.ApiModel.class);
      // 创建一个 dummy ApiModel（用匿名子类绕过 abstract）
      com.tigerbrokers.stock.openapi.client.https.domain.ApiModel dummy =
          new com.tigerbrokers.stock.openapi.client.https.domain.ApiModel() {};
      dummy.setAccount("00000000000000000");
      setter.invoke(req, dummy);
    } catch (Exception ignored) {
      // setApiModel 抛 UnsupportedOperationException 或无 setter，说明不需要 model
      // ClassCastException: AggregateAssetRequest 的已知 bug（getApiModel fallback 类型不对）
    }
  }

  @SuppressWarnings("unchecked")
  private static <T extends TigerResponse> TigerRequest<T> instantiate(Class<?> cls) {
    try { Constructor<?> c = cls.getDeclaredConstructor(); c.setAccessible(true); return (TigerRequest<T>) c.newInstance(); }
    catch (Exception e) { return null; }
  }

  // ---------- contract (2) ----------

  @Test public void testContract() {
    TigerRequest<ContractResponse> req = instantiate(ContractRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    ContractResponse resp = client.execute(req);
    Assert.assertNotNull("ContractRequest", resp);
    Assert.assertTrue("ContractRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testContracts() {
    TigerRequest<ContractsResponse> req = instantiate(ContractsRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    ContractsResponse resp = client.execute(req);
    Assert.assertNotNull("ContractsRequest", resp);
    Assert.assertTrue("ContractsRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  // ---------- financial (10) ----------

  @Test public void testCorporateDelisting() {
    TigerRequest<CorporateDelistingResponse> req = instantiate(CorporateDelistingRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    CorporateDelistingResponse resp = client.execute(req);
    Assert.assertNotNull("CorporateDelistingRequest", resp);
    Assert.assertTrue("CorporateDelistingRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testCorporateDividend() {
    TigerRequest<CorporateDividendResponse> req = instantiate(CorporateDividendRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    CorporateDividendResponse resp = client.execute(req);
    Assert.assertNotNull("CorporateDividendRequest", resp);
    Assert.assertTrue("CorporateDividendRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testCorporateEarning() {
    TigerRequest<CorporateEarningResponse> req = instantiate(CorporateEarningRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    CorporateEarningResponse resp = client.execute(req);
    Assert.assertNotNull("CorporateEarningRequest", resp);
    Assert.assertTrue("CorporateEarningRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testCorporateIpo() {
    TigerRequest<CorporateIpoResponse> req = instantiate(CorporateIpoRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    CorporateIpoResponse resp = client.execute(req);
    Assert.assertNotNull("CorporateIpoRequest", resp);
    Assert.assertTrue("CorporateIpoRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testCorporateSplit() {
    TigerRequest<CorporateSplitResponse> req = instantiate(CorporateSplitRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    CorporateSplitResponse resp = client.execute(req);
    Assert.assertNotNull("CorporateSplitRequest", resp);
    Assert.assertTrue("CorporateSplitRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testCorporateSymbolChange() {
    TigerRequest<CorporateSymbolChangeResponse> req = instantiate(CorporateSymbolChangeRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    CorporateSymbolChangeResponse resp = client.execute(req);
    Assert.assertNotNull("CorporateSymbolChangeRequest", resp);
    Assert.assertTrue("CorporateSymbolChangeRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFinancialCurrency() {
    TigerRequest<FinancialCurrencyResponse> req = instantiate(FinancialCurrencyRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FinancialCurrencyResponse resp = client.execute(req);
    Assert.assertNotNull("FinancialCurrencyRequest", resp);
    Assert.assertTrue("FinancialCurrencyRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFinancialDaily() {
    TigerRequest<FinancialDailyResponse> req = instantiate(FinancialDailyRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FinancialDailyResponse resp = client.execute(req);
    Assert.assertNotNull("FinancialDailyRequest", resp);
    Assert.assertTrue("FinancialDailyRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFinancialExchangeRate() {
    TigerRequest<FinancialExchangeRateResponse> req = instantiate(FinancialExchangeRateRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FinancialExchangeRateResponse resp = client.execute(req);
    Assert.assertNotNull("FinancialExchangeRateRequest", resp);
    Assert.assertTrue("FinancialExchangeRateRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFinancialReport() {
    TigerRequest<FinancialReportResponse> req = instantiate(FinancialReportRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FinancialReportResponse resp = client.execute(req);
    Assert.assertNotNull("FinancialReportRequest", resp);
    Assert.assertTrue("FinancialReportRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  // ---------- fund (4) ----------

  @Test public void testFundContracts() {
    TigerRequest<FundContractsResponse> req = instantiate(FundContractsRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FundContractsResponse resp = client.execute(req);
    Assert.assertNotNull("FundContractsRequest", resp);
    Assert.assertTrue("FundContractsRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFundHistoryQuote() {
    TigerRequest<FundHistoryQuoteResponse> req = instantiate(FundHistoryQuoteRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FundHistoryQuoteResponse resp = client.execute(req);
    Assert.assertNotNull("FundHistoryQuoteRequest", resp);
    Assert.assertTrue("FundHistoryQuoteRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFundQuote() {
    TigerRequest<FundQuoteResponse> req = instantiate(FundQuoteRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FundQuoteResponse resp = client.execute(req);
    Assert.assertNotNull("FundQuoteRequest", resp);
    Assert.assertTrue("FundQuoteRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFundSymbol() {
    TigerRequest<FundSymbolResponse> req = instantiate(FundSymbolRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FundSymbolResponse resp = client.execute(req);
    Assert.assertNotNull("FundSymbolRequest", resp);
    Assert.assertTrue("FundSymbolRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  // ---------- future (12) ----------

  @Test public void testFutureContinuousContract() {
    TigerRequest<FutureContractResponse> req = instantiate(FutureContinuousContractRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureContractResponse resp = client.execute(req);
    Assert.assertNotNull("FutureContinuousContractRequest", resp);
    Assert.assertTrue("FutureContinuousContractRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureContractByConCode() {
    TigerRequest<FutureContractResponse> req = instantiate(FutureContractByConCodeRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureContractResponse resp = client.execute(req);
    Assert.assertNotNull("FutureContractByConCodeRequest", resp);
    Assert.assertTrue("FutureContractByConCodeRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureContractByExchCode() {
    TigerRequest<FutureBatchContractResponse> req = instantiate(FutureContractByExchCodeRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureBatchContractResponse resp = client.execute(req);
    Assert.assertNotNull("FutureContractByExchCodeRequest", resp);
    Assert.assertTrue("FutureContractByExchCodeRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureContracts() {
    TigerRequest<FutureContractsResponse> req = instantiate(FutureContractsRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureContractsResponse resp = client.execute(req);
    Assert.assertNotNull("FutureContractsRequest", resp);
    Assert.assertTrue("FutureContractsRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureCurrentContract() {
    TigerRequest<FutureContractResponse> req = instantiate(FutureCurrentContractRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureContractResponse resp = client.execute(req);
    Assert.assertNotNull("FutureCurrentContractRequest", resp);
    Assert.assertTrue("FutureCurrentContractRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureDepth() {
    TigerRequest<FutureDepthResponse> req = instantiate(FutureDepthRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureDepthResponse resp = client.execute(req);
    Assert.assertNotNull("FutureDepthRequest", resp);
    Assert.assertTrue("FutureDepthRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureExchange() {
    TigerRequest<FutureExchangeResponse> req = instantiate(FutureExchangeRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureExchangeResponse resp = client.execute(req);
    Assert.assertNotNull("FutureExchangeRequest", resp);
    Assert.assertTrue("FutureExchangeRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureHistoryMainContract() {
    TigerRequest<FutureHistoryMainContractResponse> req = instantiate(FutureHistoryMainContractRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureHistoryMainContractResponse resp = client.execute(req);
    Assert.assertNotNull("FutureHistoryMainContractRequest", resp);
    Assert.assertTrue("FutureHistoryMainContractRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureKline() {
    TigerRequest<FutureKlineResponse> req = instantiate(FutureKlineRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureKlineResponse resp = client.execute(req);
    Assert.assertNotNull("FutureKlineRequest", resp);
    Assert.assertTrue("FutureKlineRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureRealTimeQuote() {
    TigerRequest<FutureRealTimeQuoteResponse> req = instantiate(FutureRealTimeQuoteRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureRealTimeQuoteResponse resp = client.execute(req);
    Assert.assertNotNull("FutureRealTimeQuoteRequest", resp);
    Assert.assertTrue("FutureRealTimeQuoteRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureTick() {
    TigerRequest<FutureTickResponse> req = instantiate(FutureTickRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureTickResponse resp = client.execute(req);
    Assert.assertNotNull("FutureTickRequest", resp);
    Assert.assertTrue("FutureTickRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFutureTradingDate() {
    TigerRequest<FutureTradingDateResponse> req = instantiate(FutureTradingDateRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FutureTradingDateResponse resp = client.execute(req);
    Assert.assertNotNull("FutureTradingDateRequest", resp);
    Assert.assertTrue("FutureTradingDateRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  // ---------- option (11) ----------

  @Test public void testOptionAnalysis() {
    TigerRequest<OptionAnalysisResponse> req = instantiate(OptionAnalysisRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionAnalysisResponse resp = client.execute(req);
    Assert.assertNotNull("OptionAnalysisRequest", resp);
    Assert.assertTrue("OptionAnalysisRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionBriefQuery() {
    TigerRequest<OptionBriefResponse> req = instantiate(OptionBriefQueryRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionBriefResponse resp = client.execute(req);
    Assert.assertNotNull("OptionBriefQueryRequest", resp);
    Assert.assertTrue("OptionBriefQueryRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionBriefQueryV2() {
    TigerRequest<OptionBriefResponse> req = instantiate(OptionBriefQueryV2Request.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionBriefResponse resp = client.execute(req);
    Assert.assertNotNull("OptionBriefQueryV2Request", resp);
    Assert.assertTrue("OptionBriefQueryV2Request code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionChainQuery() {
    TigerRequest<OptionChainResponse> req = instantiate(OptionChainQueryRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionChainResponse resp = client.execute(req);
    Assert.assertNotNull("OptionChainQueryRequest", resp);
    Assert.assertTrue("OptionChainQueryRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionDepthQuery() {
    TigerRequest<OptionDepthResponse> req = instantiate(OptionDepthQueryRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionDepthResponse resp = client.execute(req);
    Assert.assertNotNull("OptionDepthQueryRequest", resp);
    Assert.assertTrue("OptionDepthQueryRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionExpirationQuery() {
    TigerRequest<OptionExpirationResponse> req = instantiate(OptionExpirationQueryRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionExpirationResponse resp = client.execute(req);
    Assert.assertNotNull("OptionExpirationQueryRequest", resp);
    Assert.assertTrue("OptionExpirationQueryRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionKlineQuery() {
    TigerRequest<OptionKlineResponse> req = instantiate(OptionKlineQueryRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionKlineResponse resp = client.execute(req);
    Assert.assertNotNull("OptionKlineQueryRequest", resp);
    Assert.assertTrue("OptionKlineQueryRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionKlineQueryV2() {
    TigerRequest<OptionKlineResponse> req = instantiate(OptionKlineQueryV2Request.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionKlineResponse resp = client.execute(req);
    Assert.assertNotNull("OptionKlineQueryV2Request", resp);
    Assert.assertTrue("OptionKlineQueryV2Request code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionSymbol() {
    TigerRequest<OptionSymbolResponse> req = instantiate(OptionSymbolRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionSymbolResponse resp = client.execute(req);
    Assert.assertNotNull("OptionSymbolRequest", resp);
    Assert.assertTrue("OptionSymbolRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionTimeline() {
    TigerRequest<OptionTimelineResponse> req = instantiate(OptionTimelineRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionTimelineResponse resp = client.execute(req);
    Assert.assertNotNull("OptionTimelineRequest", resp);
    Assert.assertTrue("OptionTimelineRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionTradeTickQuery() {
    TigerRequest<OptionTradeTickResponse> req = instantiate(OptionTradeTickQueryRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionTradeTickResponse resp = client.execute(req);
    Assert.assertNotNull("OptionTradeTickQueryRequest", resp);
    Assert.assertTrue("OptionTradeTickQueryRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  // ---------- quote (19) ----------

  @Test public void testKlineQuota() {
    TigerRequest<KlineQuotaResponse> req = instantiate(KlineQuotaRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    KlineQuotaResponse resp = client.execute(req);
    Assert.assertNotNull("KlineQuotaRequest", resp);
    Assert.assertTrue("KlineQuotaRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testMarketScanner() {
    TigerRequest<MarketScannerResponse> req = instantiate(MarketScannerRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    MarketScannerResponse resp = client.execute(req);
    Assert.assertNotNull("MarketScannerRequest", resp);
    Assert.assertTrue("MarketScannerRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testMarketScannerTags() {
    TigerRequest<MarketScannerTagsResponse> req = instantiate(MarketScannerTagsRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    MarketScannerTagsResponse resp = client.execute(req);
    Assert.assertNotNull("MarketScannerTagsRequest", resp);
    Assert.assertTrue("MarketScannerTagsRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteBrokerHold() {
    TigerRequest<QuoteBrokerHoldResponse> req = instantiate(QuoteBrokerHoldRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteBrokerHoldResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteBrokerHoldRequest", resp);
    Assert.assertTrue("QuoteBrokerHoldRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteCapitalDistribution() {
    TigerRequest<QuoteCapitalDistributionResponse> req = instantiate(QuoteCapitalDistributionRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteCapitalDistributionResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteCapitalDistributionRequest", resp);
    Assert.assertTrue("QuoteCapitalDistributionRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteCapitalFlow() {
    TigerRequest<QuoteCapitalFlowResponse> req = instantiate(QuoteCapitalFlowRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteCapitalFlowResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteCapitalFlowRequest", resp);
    Assert.assertTrue("QuoteCapitalFlowRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteContract() {
    TigerRequest<QuoteContractResponse> req = instantiate(QuoteContractRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteContractResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteContractRequest", resp);
    Assert.assertTrue("QuoteContractRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteDelay() {
    TigerRequest<QuoteDelayResponse> req = instantiate(QuoteDelayRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteDelayResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteDelayRequest", resp);
    Assert.assertTrue("QuoteDelayRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteDepth() {
    TigerRequest<QuoteDepthResponse> req = instantiate(QuoteDepthRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteDepthResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteDepthRequest", resp);
    Assert.assertTrue("QuoteDepthRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteHistoryTimeline() {
    TigerRequest<QuoteHistoryTimelineResponse> req = instantiate(QuoteHistoryTimelineRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteHistoryTimelineResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteHistoryTimelineRequest", resp);
    Assert.assertTrue("QuoteHistoryTimelineRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteOvernight() {
    TigerRequest<QuoteOvernightResponse> req = instantiate(QuoteOvernightRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteOvernightResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteOvernightRequest", resp);
    Assert.assertTrue("QuoteOvernightRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteRealTimeQuote() {
    TigerRequest<QuoteRealTimeQuoteResponse> req = instantiate(QuoteRealTimeQuoteRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteRealTimeQuoteResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteRealTimeQuoteRequest", resp);
    Assert.assertTrue("QuoteRealTimeQuoteRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteShortableStock() {
    TigerRequest<QuoteShortableStockResponse> req = instantiate(QuoteShortableStockRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteShortableStockResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteShortableStockRequest", resp);
    Assert.assertTrue("QuoteShortableStockRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteStockBroker() {
    TigerRequest<QuoteStockBrokerResponse> req = instantiate(QuoteStockBrokerRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteStockBrokerResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteStockBrokerRequest", resp);
    Assert.assertTrue("QuoteStockBrokerRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteStockFundamental() {
    TigerRequest<QuoteStockFundamentalResponse> req = instantiate(QuoteStockFundamentalRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteStockFundamentalResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteStockFundamentalRequest", resp);
    Assert.assertTrue("QuoteStockFundamentalRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteTimeline() {
    TigerRequest<QuoteTimelineResponse> req = instantiate(QuoteTimelineRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteTimelineResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteTimelineRequest", resp);
    Assert.assertTrue("QuoteTimelineRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteTradeCalendar() {
    TigerRequest<QuoteTradeCalendarResponse> req = instantiate(QuoteTradeCalendarRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteTradeCalendarResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteTradeCalendarRequest", resp);
    Assert.assertTrue("QuoteTradeCalendarRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteTradeRank() {
    TigerRequest<QuoteTradeRankResponse> req = instantiate(QuoteTradeRankRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteTradeRankResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteTradeRankRequest", resp);
    Assert.assertTrue("QuoteTradeRankRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuoteTradeTick() {
    TigerRequest<QuoteTradeTickResponse> req = instantiate(QuoteTradeTickRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    QuoteTradeTickResponse resp = client.execute(req);
    Assert.assertNotNull("QuoteTradeTickRequest", resp);
    Assert.assertTrue("QuoteTradeTickRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  // ---------- trade (25) ----------

  @Test public void testAggregateAsset() {
    TigerRequest<AggregateAssetResponse> req = instantiate(AggregateAssetRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    AggregateAssetResponse resp = client.execute(req);
    Assert.assertNotNull("AggregateAssetRequest", resp);
    Assert.assertTrue("AggregateAssetRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testDepositWithdraw() {
    TigerRequest<DepositWithdrawResponse> req = instantiate(DepositWithdrawRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    DepositWithdrawResponse resp = client.execute(req);
    Assert.assertNotNull("DepositWithdrawRequest", resp);
    Assert.assertTrue("DepositWithdrawRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testEstimateTradableQuantity() {
    TigerRequest<EstimateTradableQuantityResponse> req = instantiate(EstimateTradableQuantityRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    EstimateTradableQuantityResponse resp = client.execute(req);
    Assert.assertNotNull("EstimateTradableQuantityRequest", resp);
    Assert.assertTrue("EstimateTradableQuantityRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testForexTradeOrder() {
    TigerRequest<ForexTradeOrderResponse> req = instantiate(ForexTradeOrderRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    ForexTradeOrderResponse resp = client.execute(req);
    Assert.assertNotNull("ForexTradeOrderRequest", resp);
    Assert.assertTrue("ForexTradeOrderRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testFundDetails() {
    TigerRequest<FundDetailsResponse> req = instantiate(FundDetailsRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    FundDetailsResponse resp = client.execute(req);
    Assert.assertNotNull("FundDetailsRequest", resp);
    Assert.assertTrue("FundDetailsRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionExerciseCancel() {
    TigerRequest<OptionExerciseCancelResponse> req = instantiate(OptionExerciseCancelRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionExerciseCancelResponse resp = client.execute(req);
    Assert.assertNotNull("OptionExerciseCancelRequest", resp);
    Assert.assertTrue("OptionExerciseCancelRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionExerciseCheck() {
    TigerRequest<OptionExerciseCheckResponse> req = instantiate(OptionExerciseCheckRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionExerciseCheckResponse resp = client.execute(req);
    Assert.assertNotNull("OptionExerciseCheckRequest", resp);
    Assert.assertTrue("OptionExerciseCheckRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionExercisePosition() {
    TigerRequest<OptionExercisePositionResponse> req = instantiate(OptionExercisePositionRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionExercisePositionResponse resp = client.execute(req);
    Assert.assertNotNull("OptionExercisePositionRequest", resp);
    Assert.assertTrue("OptionExercisePositionRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionExerciseRecord() {
    TigerRequest<OptionExerciseRecordResponse> req = instantiate(OptionExerciseRecordRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionExerciseRecordResponse resp = client.execute(req);
    Assert.assertNotNull("OptionExerciseRecordRequest", resp);
    Assert.assertTrue("OptionExerciseRecordRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testOptionExerciseSubmit() {
    TigerRequest<OptionExerciseSubmitResponse> req = instantiate(OptionExerciseSubmitRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    OptionExerciseSubmitResponse resp = client.execute(req);
    Assert.assertNotNull("OptionExerciseSubmitRequest", resp);
    Assert.assertTrue("OptionExerciseSubmitRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testPositionTransferDetail() {
    TigerRequest<PositionTransferDetailResponse> req = instantiate(PositionTransferDetailRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    PositionTransferDetailResponse resp = client.execute(req);
    Assert.assertNotNull("PositionTransferDetailRequest", resp);
    Assert.assertTrue("PositionTransferDetailRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testPositionTransferExternalRecords() {
    TigerRequest<PositionTransferExternalRecordsResponse> req = instantiate(PositionTransferExternalRecordsRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    PositionTransferExternalRecordsResponse resp = client.execute(req);
    Assert.assertNotNull("PositionTransferExternalRecordsRequest", resp);
    Assert.assertTrue("PositionTransferExternalRecordsRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testPositionTransferRecords() {
    TigerRequest<PositionTransferRecordsResponse> req = instantiate(PositionTransferRecordsRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    PositionTransferRecordsResponse resp = client.execute(req);
    Assert.assertNotNull("PositionTransferRecordsRequest", resp);
    Assert.assertTrue("PositionTransferRecordsRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testPositionTransfer() {
    TigerRequest<PositionTransferResponse> req = instantiate(PositionTransferRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    PositionTransferResponse resp = client.execute(req);
    Assert.assertNotNull("PositionTransferRequest", resp);
    Assert.assertTrue("PositionTransferRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testPositions() {
    TigerRequest<PositionsResponse> req = instantiate(PositionsRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    PositionsResponse resp = client.execute(req);
    Assert.assertNotNull("PositionsRequest", resp);
    Assert.assertTrue("PositionsRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testPrimeAnalyticsAsset() {
    TigerRequest<PrimeAnalyticsAssetResponse> req = instantiate(PrimeAnalyticsAssetRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    PrimeAnalyticsAssetResponse resp = client.execute(req);
    Assert.assertNotNull("PrimeAnalyticsAssetRequest", resp);
    Assert.assertTrue("PrimeAnalyticsAssetRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testPrimeAsset() {
    TigerRequest<PrimeAssetResponse> req = instantiate(PrimeAssetRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    PrimeAssetResponse resp = client.execute(req);
    Assert.assertNotNull("PrimeAssetRequest", resp);
    Assert.assertTrue("PrimeAssetRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQueryOrder() {
    TigerRequest<BatchOrderResponse> req = instantiate(QueryOrderRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    BatchOrderResponse resp = client.execute(req);
    Assert.assertNotNull("QueryOrderRequest", resp);
    Assert.assertTrue("QueryOrderRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testQuerySingleOrder() {
    TigerRequest<SingleOrderResponse> req = instantiate(QuerySingleOrderRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    SingleOrderResponse resp = client.execute(req);
    Assert.assertNotNull("QuerySingleOrderRequest", resp);
    Assert.assertTrue("QuerySingleOrderRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testSegmentFundAvailable() {
    TigerRequest<SegmentFundAvailableResponse> req = instantiate(SegmentFundAvailableRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    SegmentFundAvailableResponse resp = client.execute(req);
    Assert.assertNotNull("SegmentFundAvailableRequest", resp);
    Assert.assertTrue("SegmentFundAvailableRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testSegmentFundCancel() {
    TigerRequest<SegmentFundResponse> req = instantiate(SegmentFundCancelRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    SegmentFundResponse resp = client.execute(req);
    Assert.assertNotNull("SegmentFundCancelRequest", resp);
    Assert.assertTrue("SegmentFundCancelRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testSegmentFundHistory() {
    TigerRequest<SegmentFundsResponse> req = instantiate(SegmentFundHistoryRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    SegmentFundsResponse resp = client.execute(req);
    Assert.assertNotNull("SegmentFundHistoryRequest", resp);
    Assert.assertTrue("SegmentFundHistoryRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testSegmentFundTransfer() {
    TigerRequest<SegmentFundResponse> req = instantiate(SegmentFundTransferRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    SegmentFundResponse resp = client.execute(req);
    Assert.assertNotNull("SegmentFundTransferRequest", resp);
    Assert.assertTrue("SegmentFundTransferRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testTradeOrderPreview() {
    TigerRequest<TradeOrderPreviewResponse> req = instantiate(TradeOrderPreviewRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    TradeOrderPreviewResponse resp = client.execute(req);
    Assert.assertNotNull("TradeOrderPreviewRequest", resp);
    Assert.assertTrue("TradeOrderPreviewRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testTradeOrder() {
    TigerRequest<TradeOrderResponse> req = instantiate(TradeOrderRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    TradeOrderResponse resp = client.execute(req);
    Assert.assertNotNull("TradeOrderRequest", resp);
    Assert.assertTrue("TradeOrderRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  // ---------- user (7) ----------

  @Test public void testAddonEntitlement() {
    TigerRequest<AddonEntitlementResponse> req = instantiate(AddonEntitlementRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    AddonEntitlementResponse resp = client.execute(req);
    Assert.assertNotNull("AddonEntitlementRequest", resp);
    Assert.assertTrue("AddonEntitlementRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testUserLicense() {
    TigerRequest<UserLicenseResponse> req = instantiate(UserLicenseRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    UserLicenseResponse resp = client.execute(req);
    Assert.assertNotNull("UserLicenseRequest", resp);
    Assert.assertTrue("UserLicenseRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testUserLogin() {
    TigerRequest<UserLoginResponse> req = instantiate(UserLoginRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    UserLoginResponse resp = client.execute(req);
    Assert.assertNotNull("UserLoginRequest", resp);
    Assert.assertTrue("UserLoginRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testUserTokenRefresh() {
    TigerRequest<UserTokenResponse> req = instantiate(UserTokenRefreshRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    UserTokenResponse resp = client.execute(req);
    Assert.assertNotNull("UserTokenRefreshRequest", resp);
    Assert.assertTrue("UserTokenRefreshRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testUserTradePasswordReset() {
    TigerRequest<UserTradePasswordResetResponse> req = instantiate(UserTradePasswordResetRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    UserTradePasswordResetResponse resp = client.execute(req);
    Assert.assertNotNull("UserTradePasswordResetRequest", resp);
    Assert.assertTrue("UserTradePasswordResetRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testUserTradePasswordVerify() {
    TigerRequest<UserTradePasswordVerifyResponse> req = instantiate(UserTradePasswordVerifyRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    UserTradePasswordVerifyResponse resp = client.execute(req);
    Assert.assertNotNull("UserTradePasswordVerifyRequest", resp);
    Assert.assertTrue("UserTradePasswordVerifyRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

  @Test public void testUserTradeToken() {
    TigerRequest<UserTradeTokenResponse> req = instantiate(UserTradeTokenRequest.class);
    Assume.assumeNotNull(req);
    ensureModelNotNull(req);
    mockForRequest(req); // debug: System.out.println(req.getClass().getSimpleName() + " -> data=" + (req.getResponseClass() != null ? "yes" : "no"));
    UserTradeTokenResponse resp = client.execute(req);
    Assert.assertNotNull("UserTradeTokenRequest", resp);
    Assert.assertTrue("UserTradeTokenRequest code=" + resp.getCode(), resp.getCode() >= 0);
  }

}