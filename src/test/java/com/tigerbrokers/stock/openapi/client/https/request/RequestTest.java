package com.tigerbrokers.stock.openapi.client.https.request;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractsModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.CorporateActionModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialCurrencyModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialDailyModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialExchangeRateModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialReportModel;
import com.tigerbrokers.stock.openapi.client.https.domain.fund.model.FundQuoteHistoryModel;
import com.tigerbrokers.stock.openapi.client.https.domain.fund.model.FundSymbolModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureKlineModel;
import com.tigerbrokers.stock.openapi.client.https.request.contract.ContractRequest;
import com.tigerbrokers.stock.openapi.client.https.request.contract.ContractsRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.*;
import com.tigerbrokers.stock.openapi.client.https.request.fund.*;
import com.tigerbrokers.stock.openapi.client.https.request.future.*;
import com.tigerbrokers.stock.openapi.client.https.request.user.*;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.*;
import com.tigerbrokers.stock.openapi.client.https.response.financial.*;
import com.tigerbrokers.stock.openapi.client.https.response.fund.*;
import com.tigerbrokers.stock.openapi.client.https.response.future.*;
import com.tigerbrokers.stock.openapi.client.https.response.quote.AddonEntitlementResponse;
import com.tigerbrokers.stock.openapi.client.https.response.user.*;
import com.tigerbrokers.stock.openapi.client.struct.enums.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class RequestTest {
  private static final String ACCOUNT = "testAccount";
  private static final List<String> SYMBOLS = Arrays.asList("AAPL", "GOOGL");

  @Test public void testTigerHttpRequest() {
    TigerHttpRequest req = new TigerHttpRequest(MethodName.OPTION_BRIEF);
    Assert.assertEquals(MethodName.OPTION_BRIEF, req.getApiMethodName());
    Assert.assertNotNull(req.getTimestamp());
    Assert.assertEquals(TigerHttpResponse.class, req.getResponseClass());
    Assert.assertEquals(MethodName.OPTION_BRIEF, new TigerHttpRequest("option_brief").getApiMethodName());
  }
  @Test public void testTigerHttpRequestGettersSetters() {
    TigerHttpRequest req = new TigerHttpRequest(MethodName.ORDERS);
    req.setApiVersion("2.0"); Assert.assertEquals("2.0", req.getApiVersion());
    req.setTigerId("tiger"); Assert.assertEquals("tiger", req.getTigerId());
    req.setSignType("RSA"); Assert.assertEquals("RSA", req.getSignType());
    req.setBizContent("{}"); Assert.assertEquals("{}", req.getBizContent());
    req.setCharset("UTF-8"); Assert.assertEquals("UTF-8", req.getCharset());
    req.setTimestamp("ts"); Assert.assertEquals("ts", req.getTimestamp());
    req.setSign("s"); Assert.assertEquals("s", req.getSign());
  }
  @Test(expected = UnsupportedOperationException.class) public void testTigerHttpRequestSetApiModelThrows() { new TigerHttpRequest(MethodName.ORDERS).setApiModel(null); }
  @Test public void testTigerHttpRequestGetApiModelNull() { Assert.assertNull(new TigerHttpRequest(MethodName.ORDERS).getApiModel()); }
  @Test public void testTigerCommonRequest() {
    TigerCommonRequest req = new TigerCommonRequest();
    Assert.assertNotNull(req.getTimestamp());
    req.setApiVersion("3.0"); Assert.assertEquals("3.0", req.getApiVersion());
    req.setTigerId("id"); Assert.assertEquals("id", req.getTigerId());
    req.setApiMethodName(MethodName.ORDERS); Assert.assertEquals(MethodName.ORDERS, req.getApiMethodName());
    req.setBizContent("biz"); Assert.assertEquals("biz", req.getBizContent());
    req.setTimestamp("ts2"); Assert.assertEquals("ts2", req.getTimestamp());
    ContractModel m = new ContractModel(); req.setApiModel(m); Assert.assertSame(m, req.getApiModel());
    req.setSign("sign"); Assert.assertEquals("sign", req.getSign());
    Assert.assertEquals("2.0", TigerCommonRequest.V2_0);
    Assert.assertEquals("3.0", TigerCommonRequest.V3_0);
  }
  @Test public void testContractRequest() {
    Assert.assertEquals(MethodName.CONTRACT, new ContractRequest().getApiMethodName());
    Assert.assertEquals("3.0", new ContractRequest().getApiVersion());
    Assert.assertEquals(ContractResponse.class, new ContractRequest().getResponseClass());
    ContractModel model = new ContractModel();
    ContractRequest req1 = ContractRequest.newRequest(model); Assert.assertNotNull(req1.getApiModel());
    ContractRequest.newRequest(model, ACCOUNT); Assert.assertEquals(ACCOUNT, model.getAccount());
  }
  @Test public void testContractsRequest() {
    Assert.assertEquals(MethodName.CONTRACTS, new ContractsRequest().getApiMethodName());
    Assert.assertEquals(ContractsResponse.class, new ContractsRequest().getResponseClass());
    ContractsModel model = new ContractsModel();
    ContractsRequest req2 = ContractsRequest.newRequest(model); Assert.assertNotNull(req2.getApiModel());
    ContractsRequest.newRequest(model, ACCOUNT); Assert.assertEquals(ACCOUNT, model.getAccount());
  }
  @Test public void testUserRequests() {
    Assert.assertEquals(MethodName.USER_LOGIN, UserLoginRequest.newRequest("u","p").getApiMethodName());
    Assert.assertEquals(UserLoginResponse.class, UserLoginRequest.newRequest("u","p").getResponseClass());
    Assert.assertNotNull(UserLoginRequest.newRequest("u","p", GrantType.phone).getApiModel());
    Assert.assertEquals(MethodName.USER_TRADE_PASSWORD_VERIFY, UserTradePasswordVerifyRequest.newRequest("id").getApiMethodName());
    Assert.assertEquals(UserTradePasswordVerifyResponse.class, UserTradePasswordVerifyRequest.newRequest("id").getResponseClass());
    Assert.assertEquals(MethodName.USER_TRADE_PASSWORD_RESET, UserTradePasswordResetRequest.newRequest("id","p","c").getApiMethodName());
    Assert.assertEquals(UserTradePasswordResetResponse.class, UserTradePasswordResetRequest.newRequest("id","p","c").getResponseClass());
    Assert.assertEquals(MethodName.USER_TRADE_TOKEN, UserTradeTokenRequest.newRequest("p").getApiMethodName());
    Assert.assertEquals(UserTradeTokenResponse.class, UserTradeTokenRequest.newRequest("p").getResponseClass());
    Assert.assertEquals(MethodName.ADDON_ENTITLEMENTS, AddonEntitlementRequest.newRequest().getApiMethodName());
    Assert.assertEquals(AddonEntitlementResponse.class, AddonEntitlementRequest.newRequest().getResponseClass());
    UserLicenseRequest licReq = UserLicenseRequest.newRequest();
    Assert.assertEquals(MethodName.USER_LICENSE, licReq.getApiMethodName());
    Assert.assertEquals(UserLicenseResponse.class, licReq.getResponseClass());
    Assert.assertEquals("{}", licReq.getBizContent());
    Assert.assertEquals(MethodName.USER_TOKEN_REFRESH, UserTokenRefreshRequest.newRequest().getApiMethodName());
    Assert.assertEquals(UserTokenResponse.class, UserTokenRefreshRequest.newRequest().getResponseClass());
  }
  @Test public void testCorporateActionRequests() {
    Date d = new Date();
    Assert.assertEquals(CorporateActionType.DIVIDEND, ((CorporateActionModel)CorporateDividendRequest.newRequest(SYMBOLS, Market.US, d, d).getApiModel()).getActionType());
    Assert.assertEquals(CorporateActionType.IPO, ((CorporateActionModel)CorporateIpoRequest.newRequest(SYMBOLS, Market.HK, d, d).getApiModel()).getActionType());
    Assert.assertEquals(CorporateActionType.SPLIT, ((CorporateActionModel)CorporateSplitRequest.newRequest(SYMBOLS, Market.US, d, d).getApiModel()).getActionType());
    Assert.assertEquals(CorporateActionType.DELISTING, ((CorporateActionModel)CorporateDelistingRequest.newRequest(SYMBOLS, Market.US, d, d).getApiModel()).getActionType());
    Assert.assertEquals(CorporateActionType.SYMBOL_CHANGE, ((CorporateActionModel)CorporateSymbolChangeRequest.newRequest(SYMBOLS, Market.US, d, d).getApiModel()).getActionType());
    Assert.assertEquals(CorporateActionType.EARNING, ((CorporateActionModel)CorporateEarningRequest.newRequest(Market.US, d, d).getApiModel()).getActionType());
  }
  @Test public void testFinancialReportRequest() {
    FinancialReportRequest req = FinancialReportRequest.newRequest(SYMBOLS, Arrays.asList("f1"));
    Assert.assertEquals(MethodName.FINANCIAL_REPORT, req.getApiMethodName());
    Assert.assertEquals("2.0", req.getApiVersion());
    Assert.assertEquals(FinancialReportResponse.class, req.getResponseClass());
    Assert.assertEquals(Market.US, ((FinancialReportModel)req.getApiModel()).getMarket());
    Assert.assertEquals(FinancialPeriodType.Quarterly, ((FinancialReportModel)req.getApiModel()).getPeriodType());
    Assert.assertNotNull(FinancialReportRequest.newRequest(SYMBOLS, Arrays.asList("f"), FinancialPeriodType.Annual).getApiModel());
    Assert.assertNotNull(FinancialReportRequest.newRequest(SYMBOLS, Market.HK, Arrays.asList("f"), FinancialPeriodType.LTM).getApiModel());
    Assert.assertNotNull(FinancialReportRequest.newRequest(SYMBOLS, Market.US, Arrays.asList("f"), FinancialPeriodType.Quarterly, "2024-01-01", "2024-06-30").getApiModel());
  }
  @Test public void testFinancialDailyRequest() {
    Date d = new Date();
    FinancialDailyRequest req = FinancialDailyRequest.newRequest(SYMBOLS, Arrays.asList("f"), d, d);
    Assert.assertEquals(MethodName.FINANCIAL_DAILY, req.getApiMethodName());
    Assert.assertEquals("2.0", req.getApiVersion());
    Assert.assertEquals(FinancialDailyResponse.class, req.getResponseClass());
    Assert.assertNotNull(FinancialDailyRequest.newRequest(SYMBOLS, Arrays.asList("f"), "2024-01-01","2024-06-30").getApiModel());
    Assert.assertNotNull(FinancialDailyRequest.newRequest(SYMBOLS, Arrays.asList("f"), "2024-01-01","2024-06-30", TimeZoneId.NewYork).getApiModel());
  }
  @Test public void testFinancialCurrencyRequest() {
    Assert.assertEquals(MethodName.FINANCIAL_CURRENCY, FinancialCurrencyRequest.newRequest(SYMBOLS, Market.US).getApiMethodName());
    Assert.assertEquals(FinancialCurrencyResponse.class, FinancialCurrencyRequest.newRequest(SYMBOLS, Market.US).getResponseClass());
    FinancialCurrencyRequest req = new FinancialCurrencyRequest();
    req.symbols(SYMBOLS).market(Market.HK);
    Assert.assertEquals(Market.HK, req.getApiModel().getMarket());
  }
  @Test public void testFinancialExchangeRateRequest() {
    Date d = new Date();
    Assert.assertEquals(MethodName.FINANCIAL_EXCHANGE_RATE, FinancialExchangeRateRequest.newRequest(Arrays.asList("USD"), d, d).getApiMethodName());
    Assert.assertNotNull(FinancialExchangeRateRequest.newRequest(Arrays.asList("USD"), "2024-01-01","2024-06-30").getApiModel());
    FinancialExchangeRateRequest req = new FinancialExchangeRateRequest();
    req.currencyList(Arrays.asList("USD","HKD")).beginDate(d).endDate(d);
    req.beginDate("2024-01-01", TimeZoneId.Shanghai).endDate("2024-06-30", TimeZoneId.Shanghai);
    Assert.assertNotNull(req.getApiModel());
  }
  @Test public void testFundRequests() {
    Assert.assertEquals(MethodName.FUND_ALL_SYMBOLS, FundSymbolRequest.newRequest().getApiMethodName());
    Assert.assertEquals(FundSymbolResponse.class, FundSymbolRequest.newRequest().getResponseClass());
    Assert.assertEquals(SYMBOLS, ((FundSymbolModel)FundContractsRequest.newRequest(SYMBOLS).getApiModel()).getSymbols());
    Assert.assertEquals(Language.zh_CN, ((FundSymbolModel)FundContractsRequest.newRequest(SYMBOLS, Language.zh_CN).getApiModel()).getLang());
    Assert.assertEquals(MethodName.FUND_QUOTE, FundQuoteRequest.newRequest(SYMBOLS).getApiMethodName());
    Assert.assertEquals(FundQuoteResponse.class, FundQuoteRequest.newRequest(SYMBOLS).getResponseClass());
    Assert.assertEquals(MethodName.FUND_HISTORY_QUOTE, FundHistoryQuoteRequest.newRequest(SYMBOLS).getApiMethodName());
    Assert.assertEquals(FundHistoryQuoteResponse.class, FundHistoryQuoteRequest.newRequest(SYMBOLS).getResponseClass());
    Assert.assertEquals(Long.valueOf(1000L), FundHistoryQuoteRequest.newRequest(SYMBOLS, 1000L, 2000L).getApiModel().getBeginTime());
  }
  @Test public void testFutureRequests() {
    List<String> codes = Arrays.asList("CL2401");
    Assert.assertEquals(MethodName.FUTURE_DEPTH, FutureDepthRequest.newRequest(codes).getApiMethodName());
    Assert.assertEquals(FutureDepthResponse.class, FutureDepthRequest.newRequest(codes).getResponseClass());
    Assert.assertNotNull(FutureDepthRequest.newRequest(codes, Language.zh_CN).getApiModel());
    Assert.assertEquals(MethodName.FUTURE_CONTRACTS, FutureContractsRequest.newRequest("CL").getApiMethodName());
    Assert.assertEquals(MethodName.FUTURE_CONTRACT_BY_EXCHANGE_CODE, FutureContractByExchCodeRequest.newRequest("CME").getApiMethodName());
    Assert.assertEquals(MethodName.FUTURE_CURRENT_CONTRACT, FutureCurrentContractRequest.newRequest("CL").getApiMethodName());
    Assert.assertEquals(MethodName.FUTURE_CONTINUOUS_CONTRACTS, FutureContinuousContractRequest.newRequest("CL").getApiMethodName());
    Assert.assertEquals(MethodName.FUTURE_TRADING_DATE, FutureTradingDateRequest.newRequest("CL2401").getApiMethodName());
    Assert.assertEquals(MethodName.FUTURE_HISTORY_MAIN_CONTRACT, FutureHistoryMainContractRequest.newRequest(codes, 1L, 2L).getApiMethodName());
    FutureKlineRequest fkr = FutureKlineRequest.newRequest(codes);
    Assert.assertEquals(MethodName.FUTURE_KLINE, fkr.getApiMethodName());
    Assert.assertEquals(FutureKlineResponse.class, fkr.getResponseClass());
    fkr.withPageToken("tk"); Assert.assertEquals("tk", ((FutureKlineModel)fkr.getApiModel()).getPageToken());
    Assert.assertEquals(MethodName.FUTURE_TICK, FutureTickRequest.newRequest("CL2401").getApiMethodName());
    Assert.assertEquals("3.0", FutureTickRequest.newRequest("CL2401").getApiVersion());
    Assert.assertEquals(MethodName.FUTURE_CONTRACT_BY_CONTRACT_CODE, FutureContractByConCodeRequest.newRequest("CL2401").getApiMethodName());
    Assert.assertEquals(MethodName.FUTURE_EXCHANGE, FutureExchangeRequest.newRequest("FUT").getApiMethodName());
    Assert.assertEquals(MethodName.FUTURE_REAL_TIME_QUOTE, FutureRealTimeQuoteRequest.newRequest(codes).getApiMethodName());
  }

  @Test public void testFundHistoryQuoteRequest_builders() {
    FundHistoryQuoteRequest req = FundHistoryQuoteRequest.newRequest(SYMBOLS);
    req.symbols(SYMBOLS).beginTime(1000L).endTime(2000L).limit(100).lang(Language.zh_CN);
    Assert.assertEquals(Long.valueOf(1000L), req.getApiModel().getBeginTime());
    Assert.assertEquals(Integer.valueOf(100), req.getApiModel().getLimit());
    // String begin/end + timezone overload
    FundHistoryQuoteRequest req2 = FundHistoryQuoteRequest.newRequest(SYMBOLS, "2024-01-01", "2024-06-30", TimeZoneId.NewYork);
    Assert.assertNotNull(req2.getApiModel());
    // lazy getApiModel when null
    FundHistoryQuoteRequest req3 = new FundHistoryQuoteRequest();
    Assert.assertNotNull(req3.getApiModel());
  }
  @Test public void testFundContractsRequest_builders() {
    FundContractsRequest req = FundContractsRequest.newRequest(SYMBOLS);
    req.symbols(SYMBOLS).lang(Language.zh_CN);
    Assert.assertEquals(Language.zh_CN, req.getApiModel().getLang());
    FundContractsRequest req2 = FundContractsRequest.newRequest(SYMBOLS, Language.zh_CN);
    Assert.assertNotNull(req2.getApiModel());
    // lazy getApiModel
    Assert.assertNotNull(new FundContractsRequest().getApiModel());
  }
  @Test public void testFundQuoteRequest_builders() {
    FundQuoteRequest req = FundQuoteRequest.newRequest(SYMBOLS);
    req.symbols(SYMBOLS).lang(Language.zh_CN);
    Assert.assertEquals(SYMBOLS, req.getApiModel().getSymbols());
    Assert.assertNotNull(FundQuoteRequest.newRequest(SYMBOLS, Language.zh_CN).getApiModel());
    // lazy getApiModel
    Assert.assertNotNull(new FundQuoteRequest().getApiModel());
  }
}
