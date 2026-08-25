package com.tigerbrokers.stock.openapi.client.https.request.quote;

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
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteMarketResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteOvernightResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteShortableStockResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockBrokerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockFundamentalResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockTradeResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolNameResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeCalendarResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeRankResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeTickResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.CapitalPeriod;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.MultiTagField;
import com.tigerbrokers.stock.openapi.client.struct.enums.PackageName;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeLineType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradeSession;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteBrokerHoldModel;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class QuoteRequestTest {
  private static final List<String> SYMBOLS = Arrays.asList("AAPL", "GOOGL");
  @Test public void testKlineQuotaRequest() {
    Assert.assertEquals(MethodName.KLINE_QUOTA, new KlineQuotaRequest().getApiMethodName());
    Assert.assertEquals(KlineQuotaResponse.class, new KlineQuotaRequest().getResponseClass());
    Assert.assertNotNull(KlineQuotaRequest.newRequest().getApiModel());
    Assert.assertNotNull(KlineQuotaRequest.newRequest(true).getApiModel());
  }
  @Test public void testMarketScannerRequest() {
    Assert.assertEquals(MethodName.MARKET_SCANNER, new MarketScannerRequest().getApiMethodName());
    Assert.assertEquals(MarketScannerResponse.class, new MarketScannerRequest().getResponseClass());
    Assert.assertNotNull(MarketScannerRequest.newRequest(Market.US, null, null, null, null, null, 1, 10).getApiModel());
    Assert.assertNotNull(MarketScannerRequest.newRequest(Market.US, null, null, null, null, null, 1, 10, "cursor123").getApiModel());
  }
  @Test public void testMarketScannerTagsRequest() {
    Assert.assertEquals(MethodName.MARKET_SCANNER_TAGS, new MarketScannerTagsRequest().getApiMethodName());
    Assert.assertEquals(MarketScannerTagsResponse.class, new MarketScannerTagsRequest().getResponseClass());
    Assert.assertNotNull(MarketScannerTagsRequest.newRequest(Market.US, Arrays.asList(MultiTagField.MultiTagField_Industry)).getApiModel());
  }
  @Test public void testQuoteBrokerHoldRequest() {
    Assert.assertEquals(MethodName.BROKER_HOLD, new QuoteBrokerHoldRequest().getApiMethodName());
    Assert.assertEquals(QuoteBrokerHoldResponse.class, new QuoteBrokerHoldRequest().getResponseClass());
    Assert.assertNotNull(QuoteBrokerHoldRequest.newRequest(Market.US, 10).getApiModel());
    Assert.assertNotNull(QuoteBrokerHoldRequest.newRequest(Market.US, 10, 1).getApiModel());
    Assert.assertNotNull(QuoteBrokerHoldRequest.newRequest(Market.US, 10, 1, "volume", "asc").getApiModel());
  }
  @Test public void testQuoteCapitalDistributionRequest() {
    Assert.assertEquals(MethodName.CAPITAL_DISTRIBUTION, new QuoteCapitalDistributionRequest().getApiMethodName());
    Assert.assertEquals(QuoteCapitalDistributionResponse.class, new QuoteCapitalDistributionRequest().getResponseClass());
    Assert.assertNotNull(QuoteCapitalDistributionRequest.newRequest("AAPL", Market.US).getApiModel());
    Assert.assertNotNull(QuoteCapitalDistributionRequest.newRequest("AAPL", Market.US, Language.zh_CN).getApiModel());
  }
  @Test public void testQuoteCapitalFlowRequest() {
    Assert.assertEquals(MethodName.CAPITAL_FLOW, new QuoteCapitalFlowRequest().getApiMethodName());
    Assert.assertEquals(QuoteCapitalFlowResponse.class, new QuoteCapitalFlowRequest().getResponseClass());
    Assert.assertNotNull(QuoteCapitalFlowRequest.newRequest("AAPL", Market.US, CapitalPeriod.day).getApiModel());
  }
  @Test public void testQuoteContractRequest() {
    Assert.assertEquals(MethodName.QUOTE_CONTRACT, new QuoteContractRequest().getApiMethodName());
    Assert.assertEquals(QuoteContractResponse.class, new QuoteContractRequest().getResponseClass());
    Assert.assertNotNull(QuoteContractRequest.newRequest("AAPL").getApiModel());
    Assert.assertNotNull(QuoteContractRequest.newRequest("AAPL", SecType.STK).getApiModel());
    Assert.assertNotNull(QuoteContractRequest.newRequest("AAPL", SecType.STK, Language.zh_CN).getApiModel());
    Assert.assertNotNull(QuoteContractRequest.newRequest("AAPL", SecType.OPT, "20240119").getApiModel());
  }
  @Test public void testQuoteDelayRequest() {
    Assert.assertEquals(MethodName.QUOTE_DELAY, new QuoteDelayRequest().getApiMethodName());
    Assert.assertEquals(QuoteDelayResponse.class, new QuoteDelayRequest().getResponseClass());
    Assert.assertNotNull(QuoteDelayRequest.newRequest(SYMBOLS).getApiModel());
  }
  @Test public void testQuoteDepthRequest() {
    Assert.assertEquals(MethodName.QUOTE_DEPTH, new QuoteDepthRequest().getApiMethodName());
    Assert.assertEquals(QuoteDepthResponse.class, new QuoteDepthRequest().getResponseClass());
    Assert.assertNotNull(QuoteDepthRequest.newRequest(SYMBOLS, "us").getApiModel());
    Assert.assertNotNull(QuoteDepthRequest.newRequest(SYMBOLS, "us", TradeSession.PreMarket).getApiModel());
  }
  @Test public void testQuoteHistoryTimelineRequest() {
    Assert.assertEquals(MethodName.HISTORY_TIMELINE, new QuoteHistoryTimelineRequest().getApiMethodName());
    Assert.assertEquals(QuoteHistoryTimelineResponse.class, new QuoteHistoryTimelineRequest().getResponseClass());
    Assert.assertNotNull(QuoteHistoryTimelineRequest.newRequest(SYMBOLS).getApiModel());
    Assert.assertNotNull(QuoteHistoryTimelineRequest.newRequest(SYMBOLS, "2024-01-19").getApiModel());
    Assert.assertNotNull(QuoteHistoryTimelineRequest.newRequest(SYMBOLS, "2024-01-19", Language.zh_CN).getApiModel());
          }
  @Test public void testQuoteKlineRequest() {
    Assert.assertEquals(MethodName.KLINE, new QuoteKlineRequest().getApiMethodName());
    Assert.assertEquals(QuoteKlineResponse.class, new QuoteKlineRequest().getResponseClass());
    Assert.assertNotNull(QuoteKlineRequest.newRequest(SYMBOLS, KType.day).getApiModel());
    Assert.assertNotNull(QuoteKlineRequest.newRequest(SYMBOLS, KType.day, Long.valueOf(1L), Long.valueOf(2L)).getApiModel());
    Assert.assertNotNull(QuoteKlineRequest.newRequest(SYMBOLS, KType.day, "2024-01-01", "2024-06-30").getApiModel());
    Assert.assertNotNull(QuoteKlineRequest.newRequest(SYMBOLS, KType.day, "2024-01-01", "2024-06-30", true).getApiModel());
    Assert.assertNotNull(QuoteKlineRequest.newRequest(SYMBOLS, KType.day, "2024-01-01", "2024-06-30", TimeZoneId.NewYork).getApiModel());
    Assert.assertNotNull(QuoteKlineRequest.newRequest(SYMBOLS, KType.day, "2024-01-01", "2024-06-30", TimeZoneId.NewYork, true).getApiModel());
  }
  @Test public void testQuoteMarketRequest() {
    Assert.assertEquals(MethodName.MARKET_STATE, new QuoteMarketRequest().getApiMethodName());
    Assert.assertEquals(QuoteMarketResponse.class, new QuoteMarketRequest().getResponseClass());
    Assert.assertNotNull(QuoteMarketRequest.newRequest(Market.US).getApiModel());
    Assert.assertNotNull(QuoteMarketRequest.newRequest(Market.US, Language.zh_CN).getApiModel());
  }
  @Test public void testQuoteOvernightRequest() {
    Assert.assertEquals(MethodName.QUOTE_OVERNIGHT, new QuoteOvernightRequest().getApiMethodName());
    Assert.assertEquals(QuoteOvernightResponse.class, new QuoteOvernightRequest().getResponseClass());
    Assert.assertNotNull(QuoteOvernightRequest.newRequest(SYMBOLS).getApiModel());
    Assert.assertNotNull(QuoteOvernightRequest.newRequest(SYMBOLS, Language.zh_CN).getApiModel());
  }
  @Test public void testQuoteRealTimeQuoteRequest() {
    Assert.assertEquals(MethodName.QUOTE_REAL_TIME, new QuoteRealTimeQuoteRequest().getApiMethodName());
    Assert.assertEquals(QuoteRealTimeQuoteResponse.class, new QuoteRealTimeQuoteRequest().getResponseClass());
    Assert.assertNotNull(QuoteRealTimeQuoteRequest.newRequest(SYMBOLS).getApiModel());
    Assert.assertNotNull(QuoteRealTimeQuoteRequest.newCcRequest(SYMBOLS).getApiModel());
    Assert.assertNotNull(QuoteRealTimeQuoteRequest.newRequest(SYMBOLS, true).getApiModel());
    Assert.assertNotNull(QuoteRealTimeQuoteRequest.newRequest(SYMBOLS, true, Language.zh_CN).getApiModel());
  }
  @Test public void testQuoteShortableStockRequest() {
    Assert.assertEquals(MethodName.QUOTE_SHORTABLE_STOCKS, new QuoteShortableStockRequest().getApiMethodName());
    Assert.assertEquals(QuoteShortableStockResponse.class, new QuoteShortableStockRequest().getResponseClass());
    Assert.assertNotNull(QuoteShortableStockRequest.newRequest(SYMBOLS).getApiModel());
    Assert.assertNotNull(QuoteShortableStockRequest.newRequest(SYMBOLS, Language.zh_CN).getApiModel());
  }
  @Test public void testQuoteStockBrokerRequest() {
    Assert.assertEquals(MethodName.STOCK_BROKER, new QuoteStockBrokerRequest().getApiMethodName());
    Assert.assertEquals(QuoteStockBrokerResponse.class, new QuoteStockBrokerRequest().getResponseClass());
    Assert.assertNotNull(QuoteStockBrokerRequest.newRequest("AAPL").getApiModel());
    Assert.assertNotNull(QuoteStockBrokerRequest.newRequest("AAPL", Language.zh_CN).getApiModel());
    Assert.assertNotNull(QuoteStockBrokerRequest.newRequest("AAPL", 10).getApiModel());
    Assert.assertNotNull(QuoteStockBrokerRequest.newRequest("AAPL", 10, Language.zh_CN).getApiModel());
  }
  @Test public void testQuoteStockFundamentalRequest() {
    Assert.assertEquals(MethodName.STOCK_FUNDAMENTAL, new QuoteStockFundamentalRequest().getApiMethodName());
    Assert.assertEquals(QuoteStockFundamentalResponse.class, new QuoteStockFundamentalRequest().getResponseClass());
    Assert.assertNotNull(QuoteStockFundamentalRequest.newRequest(SYMBOLS, "us").getApiModel());
  }
  @Test public void testQuoteStockTradeRequest() {
    Assert.assertEquals(MethodName.QUOTE_STOCK_TRADE, new QuoteStockTradeRequest().getApiMethodName());
    Assert.assertEquals(QuoteStockTradeResponse.class, new QuoteStockTradeRequest().getResponseClass());
    Assert.assertNotNull(QuoteStockTradeRequest.newRequest(SYMBOLS).getApiModel());
  }
  @Test public void testQuoteSymbolNameRequest() {
    Assert.assertEquals(MethodName.ALL_SYMBOL_NAMES, new QuoteSymbolNameRequest().getApiMethodName());
    Assert.assertEquals(QuoteSymbolNameResponse.class, new QuoteSymbolNameRequest().getResponseClass());
    Assert.assertNotNull(QuoteSymbolNameRequest.newRequest(Market.US).getApiModel());
    Assert.assertNotNull(QuoteSymbolNameRequest.newRequest(Market.US, Language.zh_CN).getApiModel());
  }
  @Test public void testQuoteSymbolRequest() {
    Assert.assertEquals(MethodName.ALL_SYMBOLS, new QuoteSymbolRequest().getApiMethodName());
    Assert.assertEquals(QuoteSymbolResponse.class, new QuoteSymbolRequest().getResponseClass());
    Assert.assertNotNull(QuoteSymbolRequest.newRequest(Market.US).getApiModel());
    Assert.assertNotNull(QuoteSymbolRequest.newRequest(Market.US, Language.zh_CN).getApiModel());
    Assert.assertNotNull(QuoteSymbolRequest.newRequest(PackageName.package_popular).getApiModel());
    Assert.assertNotNull(QuoteSymbolRequest.newCcRequest().getApiModel());
  }
  @Test public void testQuoteTimelineRequest() {
    Assert.assertEquals(MethodName.TIMELINE, new QuoteTimelineRequest().getApiMethodName());
    Assert.assertEquals(QuoteTimelineResponse.class, new QuoteTimelineRequest().getResponseClass());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, Long.valueOf(1L)).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newCcRequest(SYMBOLS, Long.valueOf(1L)).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, Long.valueOf(1L), true).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, Long.valueOf(1L), TradeSession.PreMarket).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, Long.valueOf(1L), TimeLineType.day5).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, "2024-01-01", TimeLineType.day).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, "2024-01-01", TradeSession.Regular, TimeLineType.day).getApiModel());
  }
  @Test public void testQuoteTradeCalendarRequest() {
    Assert.assertEquals(MethodName.TRADING_CALENDAR, new QuoteTradeCalendarRequest().getApiMethodName());
    Assert.assertEquals(QuoteTradeCalendarResponse.class, new QuoteTradeCalendarRequest().getResponseClass());
    Assert.assertNotNull(QuoteTradeCalendarRequest.newRequest(Market.US).getApiModel());
    Assert.assertNotNull(QuoteTradeCalendarRequest.newRequest(Market.US, "2024-01-01", "2024-12-31").getApiModel());
  }
  @Test public void testQuoteTradeRankRequest() {
    Assert.assertEquals(MethodName.TRADE_RANK, new QuoteTradeRankRequest().getApiMethodName());
    Assert.assertEquals(QuoteTradeRankResponse.class, new QuoteTradeRankRequest().getResponseClass());
    Assert.assertNotNull(QuoteTradeRankRequest.newRequest(Market.US).getApiModel());
    Assert.assertNotNull(QuoteTradeRankRequest.newRequest(Market.US, Language.zh_CN).getApiModel());
  }
  @Test public void testQuoteTradeTickRequest() {
    Assert.assertEquals(MethodName.TRADE_TICK, new QuoteTradeTickRequest().getApiMethodName());
    Assert.assertEquals(QuoteTradeTickResponse.class, new QuoteTradeTickRequest().getResponseClass());
    Assert.assertNotNull(QuoteTradeTickRequest.newRequest(SYMBOLS).getApiModel());
    Assert.assertNotNull(QuoteTradeTickRequest.newRequest(SYMBOLS, Language.zh_CN).getApiModel());
    Assert.assertNotNull(QuoteTradeTickRequest.newRequest(SYMBOLS, Language.zh_CN, 100).getApiModel());
    Assert.assertNotNull(QuoteTradeTickRequest.newRequest(SYMBOLS, Long.valueOf(1L), Long.valueOf(2L)).getApiModel());
    Assert.assertNotNull(QuoteTradeTickRequest.newRequest(SYMBOLS, Long.valueOf(1L), Long.valueOf(2L), 100).getApiModel());
    Assert.assertNotNull(QuoteTradeTickRequest.newRequest(SYMBOLS, Long.valueOf(1L), Long.valueOf(2L), Language.zh_CN).getApiModel());
  }
  @Test public void testEmptySymbolsEdgeCase() {
    Assert.assertNotNull(QuoteDelayRequest.newRequest(Collections.emptyList()).getApiModel());
    Assert.assertNotNull(QuoteStockTradeRequest.newRequest(Collections.emptyList()).getApiModel());
  }

  @Test public void testQuoteTimelineRequest_overloads() {
    // includeHourTrading boolean overloads (deprecated)
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, 1L, true).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, 1L, true, Language.zh_CN).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, 1L, false, Language.zh_CN).getApiModel());
    // TradeSession + Language overload
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, 1L, TradeSession.PreMarket, Language.zh_CN).getApiModel());
    // TimeLineType overloads
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, 1L, TimeLineType.day5).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, 1L, true, TimeLineType.day5).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, 1L, TradeSession.AfterHours, TimeLineType.day5).getApiModel());
    // boolean includeHourTrading + timeLineType + lang
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, 1L, true, TimeLineType.day5, Language.zh_CN).getApiModel());
    // String beginTime overloads
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, "2024-01-01", TimeLineType.day).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, "2024-01-01", true, TimeLineType.day).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, "2024-01-01", TradeSession.Regular, TimeLineType.day).getApiModel());
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, "2024-01-01", TimeZoneId.NewYork, TradeSession.Regular, TimeLineType.day, Language.zh_CN).getApiModel());
    // null zoneId falls back to default
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, "2024-01-01", null, TradeSession.Regular, TimeLineType.day, Language.zh_CN).getApiModel());
    // invalid date string -> beginDate null
    Assert.assertNotNull(QuoteTimelineRequest.newRequest(SYMBOLS, "invalid-date", TimeZoneId.NewYork, TradeSession.Regular, TimeLineType.day, Language.zh_CN).getApiModel());
  }
  @Test public void testQuoteTimelineRequest_setters() {
    QuoteTimelineRequest req = QuoteTimelineRequest.newRequest(SYMBOLS);
    req.setTradeSession(TradeSession.All);
    Assert.assertEquals(TradeSession.All, req.getApiModel().getTradeSession());
    QuoteTimelineRequest cc = QuoteTimelineRequest.newCcRequest(SYMBOLS, 1L);
    Assert.assertEquals("CC", cc.getApiModel().getSecType());
    // withSecType null is no-op
    QuoteTimelineRequest req2 = QuoteTimelineRequest.newRequest(SYMBOLS);
    req2.withSecType(null);
    Assert.assertNull(req2.getApiModel().getSecType());
  }

  @Test public void testQuoteHistoryTimelineRequest_overloads() {
    // Long date overload
    Assert.assertNotNull(QuoteHistoryTimelineRequest.newRequest(SYMBOLS, 1704067200000L).getApiModel());
    // Long date + timezone + lang
    Assert.assertNotNull(QuoteHistoryTimelineRequest.newRequest(SYMBOLS, 1704067200000L, TimeZoneId.NewYork, Language.zh_CN).getApiModel());
    // null long date
    Assert.assertNotNull(QuoteHistoryTimelineRequest.newRequest(SYMBOLS, (Long) null).getApiModel());
    // null zoneId falls back to default
    Assert.assertNotNull(QuoteHistoryTimelineRequest.newRequest(SYMBOLS, 1704067200000L, null, Language.zh_CN).getApiModel());
  }
  @Test public void testQuoteHistoryTimelineRequest_builders() {
    QuoteHistoryTimelineRequest req = QuoteHistoryTimelineRequest.newRequest(SYMBOLS, "2024-01-19");
    req.withRight(com.tigerbrokers.stock.openapi.client.struct.enums.RightOption.br)
       .withTradeSession(TradeSession.All);
    req.setTradeSession(TradeSession.Regular);
    Assert.assertEquals(TradeSession.Regular, req.getApiModel().getTradeSession());
  }

  @Test public void testQuoteBrokerHoldRequest_setters() {
    QuoteBrokerHoldRequest req = QuoteBrokerHoldRequest.newRequest(Market.US, 10, 1, "volume", "asc");
    req.setLang(Language.zh_CN);
    req.setLimit(20);
    req.setPage(2);
    req.setDirection("desc");
    req.setOrderBy("amount");
    QuoteBrokerHoldModel m = (QuoteBrokerHoldModel) req.getApiModel();
    Assert.assertEquals(Integer.valueOf(20), m.getLimit());
    Assert.assertEquals(Integer.valueOf(2), m.getPage());
    Assert.assertEquals("desc", m.getDirection());
    Assert.assertEquals("amount", m.getOrderBy());
  }
}
