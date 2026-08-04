package com.tigerbrokers.stock.openapi.client.quote;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.*;
import com.tigerbrokers.stock.openapi.client.https.request.future.*;
import com.tigerbrokers.stock.openapi.client.https.request.financial.*;
import com.tigerbrokers.stock.openapi.client.https.request.fund.*;
import com.tigerbrokers.stock.openapi.client.https.request.option.*;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.*;
import com.tigerbrokers.stock.openapi.client.testsupport.IntegTestConfig;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

/** Integration tests for all quote/market data APIs. */
public class QuoteIntegrationTest {

  private static TigerHttpClient client;

  @BeforeClass
  public static void setUpClass() {
    Assume.assumeTrue("enable with -Dtest.integ=true", Boolean.getBoolean("test.integ"));
    client = IntegTestConfig.createClient();
  }

  private void assertSuccess(TigerResponse resp, String api) {
    Assert.assertNotNull(api + " returned null", resp);
    Assert.assertTrue(api + " failed: " + resp.getMessage(), resp.isSuccess());
  }

  @Test
  public void testMarketState() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.MARKET_STATE);
    request.setBizContent("{\"market\":\"US\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testMarketState");
  }

  @Test
  public void testAllSymbols() {
    TigerResponse response = client.execute(QuoteSymbolRequest.newRequest(Market.US));
    assertSuccess(response, "testAllSymbols");
  }

  @Test
  public void testAllSymbolNames() {
    TigerResponse response = client.execute(QuoteSymbolNameRequest.newRequest(Market.US));
    assertSuccess(response, "testAllSymbolNames");
  }

  @Test
  public void testBrief() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.BRIEF);
    request.setBizContent("{\"symbols\":[\"AAPL\"]}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testBrief");
  }

  @Test
  public void testStockDetail() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.STOCK_DETAIL);
    request.setBizContent("{\"symbols\":[\"AAPL\"]}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testStockDetail");
  }

  @Test
  public void testTimeline() {
    TigerResponse response = client.execute(QuoteTimelineRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testTimeline");
  }

  @Test
  public void testHistoryTimeline() {
    TigerResponse response = client.execute(QuoteHistoryTimelineRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testHistoryTimeline");
  }

  @Test
  public void testKline() {
    TigerResponse response = client.execute(QuoteKlineRequest.newRequest(java.util.Arrays.asList("AAPL"), KType.day));
    assertSuccess(response, "testKline");
  }

  @Test
  public void testTradeTick() {
    TigerResponse response = client.execute(QuoteTradeTickRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testTradeTick");
  }

  @Test
  public void testQuoteContract() {
    TigerResponse response = client.execute(QuoteContractRequest.newRequest("AAPL"));
    assertSuccess(response, "testQuoteContract");
  }

  @Test
  public void testQuoteRealTime() {
    TigerResponse response = client.execute(QuoteRealTimeQuoteRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testQuoteRealTime");
  }

  @Test
  public void testQuoteShortableStocks() {
    TigerResponse response = client.execute(QuoteShortableStockRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testQuoteShortableStocks");
  }

  @Test
  public void testQuoteStockTrade() {
    TigerResponse response = client.execute(QuoteStockTradeRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testQuoteStockTrade");
  }

  @Test
  public void testQuoteDepth() {
    TigerResponse response = client.execute(QuoteDepthRequest.newRequest(java.util.Arrays.asList("AAPL"), "US"));
    assertSuccess(response, "testQuoteDepth");
  }

  @Test
  public void testQuoteDelay() {
    TigerResponse response = client.execute(QuoteDelayRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testQuoteDelay");
  }

  @Test
  public void testQuoteOvernight() {
    TigerResponse response = client.execute(QuoteOvernightRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testQuoteOvernight");
  }

  @Test
  public void testTradingCalendar() {
    TigerResponse response = client.execute(QuoteTradeCalendarRequest.newRequest(Market.US));
    assertSuccess(response, "testTradingCalendar");
  }

  @Test
  public void testStockBroker() {
    TigerResponse response = client.execute(QuoteStockBrokerRequest.newRequest("00700"));
    assertSuccess(response, "testStockBroker");
  }

  @Test
  public void testCapitalDistribution() {
    TigerResponse response = client.execute(QuoteCapitalDistributionRequest.newRequest("AAPL", Market.US));
    assertSuccess(response, "testCapitalDistribution");
  }

  @Test
  public void testCapitalFlow() {
    TigerResponse response = client.execute(QuoteCapitalFlowRequest.newRequest("AAPL", Market.US, CapitalPeriod.intraday));
    assertSuccess(response, "testCapitalFlow");
  }

  @Test
  public void testTradeRank() {
    TigerResponse response = client.execute(QuoteTradeRankRequest.newRequest(Market.US));
    assertSuccess(response, "testTradeRank");
  }

  @Test
  public void testOptionExpiration() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.OPTION_EXPIRATION);
    request.setBizContent("{\"symbols\":[\"AAPL\"]}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testOptionExpiration");
  }

  @Test
  public void testOptionChain() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.OPTION_CHAIN);
    request.setBizContent("{\"symbol\":\"AAPL\",\"expiry\":\"2027-01-15\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testOptionChain");
  }

  @Test
  public void testOptionBrief() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.OPTION_BRIEF);
    request.setBizContent("{\"identifiers\":[\"AAPL 270115C00200000\"]}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testOptionBrief");
  }

  @Test
  public void testWarrantFilter() {
    TigerResponse response = client.execute(WarrantFilterRequest.newRequest("00700"));
    assertSuccess(response, "testWarrantFilter");
  }

  @Test
  public void testWarrantRealTimeQuote() {
    TigerResponse response = client.execute(WarrantQuoteRequest.newRequest(java.util.Arrays.asList("15792")));
    assertSuccess(response, "testWarrantRealTimeQuote");
  }

  @Test
  public void testFutureExchange() {
    TigerResponse response = client.execute(FutureExchangeRequest.newRequest("FUT"));
    assertSuccess(response, "testFutureExchange");
  }

  @Test
  public void testFutureContractByCode() {
    TigerResponse response = client.execute(FutureContractByConCodeRequest.newRequest("CL2702"));
    assertSuccess(response, "testFutureContractByCode");
  }

  @Test
  public void testFutureContractByExchange() {
    TigerResponse response = client.execute(FutureContractByExchCodeRequest.newRequest("CME"));
    assertSuccess(response, "testFutureContractByExchange");
  }

  @Test
  public void testFutureContinuousContracts() {
    TigerResponse response = client.execute(FutureContinuousContractRequest.newRequest("ES"));
    assertSuccess(response, "testFutureContinuousContracts");
  }

  @Test
  public void testFutureCurrentContract() {
    TigerResponse response = client.execute(FutureCurrentContractRequest.newRequest("ES"));
    assertSuccess(response, "testFutureCurrentContract");
  }

  @Test
  public void testFutureContracts() {
    TigerResponse response = client.execute(FutureContractsRequest.newRequest("ES"));
    assertSuccess(response, "testFutureContracts");
  }

  @Test
  public void testFutureKline() {
    TigerResponse response = client.execute(FutureKlineRequest.newRequest(java.util.Arrays.asList("CL2702")));
    assertSuccess(response, "testFutureKline");
  }

  @Test
  public void testFutureRealTimeQuote() {
    TigerResponse response = client.execute(FutureRealTimeQuoteRequest.newRequest(java.util.Arrays.asList("CL2702")));
    assertSuccess(response, "testFutureRealTimeQuote");
  }

  @Test
  public void testFutureTradingDate() {
    TigerResponse response = client.execute(FutureTradingDateRequest.newRequest("CL2702"));
    assertSuccess(response, "testFutureTradingDate");
  }

  @Test
  public void testFutureDepth() {
    TigerResponse response = client.execute(FutureDepthRequest.newRequest(java.util.Arrays.asList("CL2702")));
    assertSuccess(response, "testFutureDepth");
  }

  @Test
  public void testFinancialCurrency() {
    TigerResponse response = client.execute(FinancialCurrencyRequest.newRequest(java.util.Arrays.asList("AAPL"), Market.US));
    assertSuccess(response, "testFinancialCurrency");
  }

  @Test
  public void testFinancialExchangeRate() {
    TigerResponse response = client.execute(FinancialExchangeRateRequest.newRequest(java.util.Arrays.asList("USD"), "2026-01-01", "2026-01-31"));
    assertSuccess(response, "testFinancialExchangeRate");
  }

  @Test
  public void testStockFundamental() {
    TigerResponse response = client.execute(QuoteStockFundamentalRequest.newRequest(java.util.Arrays.asList("AAPL"), "US"));
    assertSuccess(response, "testStockFundamental");
  }

  @Test
  public void testFundAllSymbols() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.FUND_ALL_SYMBOLS);
    request.setBizContent("{\"market\":\"US\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testFundAllSymbols");
  }

  @Test
  public void testFundContracts() {
    TigerResponse response = client.execute(FundContractsRequest.newRequest(java.util.Arrays.asList("SPY")));
    assertSuccess(response, "testFundContracts");
  }

  @Test
  public void testFundQuote() {
    TigerResponse response = client.execute(FundQuoteRequest.newRequest(java.util.Arrays.asList("SPY")));
    assertSuccess(response, "testFundQuote");
  }

  @Test
  public void testGrabQuotePermission() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.GRAB_QUOTE_PERMISSION);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testGrabQuotePermission");
  }

  @Test
  public void testGetQuotePermission() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.GET_QUOTE_PERMISSION);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testGetQuotePermission");
  }

  @Test
  public void testIndustryList() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.INDUSTRY_LIST);
    request.setBizContent("{\"market\":\"US\",\"level\":\"GSECTOR\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testIndustryList");
  }

  @Test
  public void testIndustryStocks() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.INDUSTRY_STOCKS);
    request.setBizContent("{\"industry_id\":1,\"market\":\"US\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testIndustryStocks");
  }

  @Test
  public void testStockIndustry() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.STOCK_INDUSTRY);
    request.setBizContent("{\"symbol\":\"AAPL\",\"market\":\"US\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testStockIndustry");
  }

}