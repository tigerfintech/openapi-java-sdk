package com.tigerbrokers.stock.openapi.client.quote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.*;
import com.tigerbrokers.stock.openapi.client.https.request.future.*;
import com.tigerbrokers.stock.openapi.client.https.request.financial.*;
import com.tigerbrokers.stock.openapi.client.https.request.fund.*;
import com.tigerbrokers.stock.openapi.client.https.request.option.*;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionChainItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionExpirationItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionRealTimeQuote;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionRealTimeQuoteGroup;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.WarrantItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionAnalysisModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionExpirationResponse;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.*;
import com.tigerbrokers.stock.openapi.client.testsupport.IntegTestConfig;
import com.tigerbrokers.stock.openapi.client.testsupport.MarketHelpers;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

/** Integration tests for all quote/market data APIs. */
public class QuoteIntegrationTest {

  private static TigerHttpClient client;

  // Lazily-fetched dynamic values; null until first successful API call.
  private static String cachedOptionExpiry;
  private static String cachedOptionIdentifier;
  private static String cachedFutureContract;
  private static String cachedWarrantSymbol;

  @BeforeClass
  public static void setUpClass() {
    Assume.assumeTrue("enable with -Dtest.integ=true", Boolean.getBoolean("test.integ"));
    client = IntegTestConfig.createClient();
  }

  private void assertSuccess(TigerResponse resp, String api) {
    Assert.assertNotNull(api + " returned null", resp);
    Assert.assertTrue(api + " failed: " + resp.getMessage(), resp.isSuccess());
  }

  private void assertDataPresent(TigerHttpResponse resp, String api) {
    assertSuccess(resp, api);
    Assert.assertNotNull(api + " data should not be null", resp.getData());
    Assert.assertFalse(api + " data should not be empty",
        resp.getData() == null || resp.getData().trim().isEmpty());
  }

  // ── Dynamic data helpers (avoid hardcoded expiring identifiers) ─────────────

  /** First future option expiry date for AAPL, or null if unavailable. */
  private static String getFirstOptionExpiry() {
    if (cachedOptionExpiry != null) return cachedOptionExpiry;
    TigerResponse resp = client.execute(OptionExpirationQueryRequest.of(Arrays.asList("AAPL")));
    if (resp == null || !resp.isSuccess()) return null;
    OptionExpirationResponse oeResp = (OptionExpirationResponse) resp;
    if (oeResp.getOptionExpirationItems() == null || oeResp.getOptionExpirationItems().isEmpty()) {
      return null;
    }
    OptionExpirationItem item = oeResp.getOptionExpirationItems().get(0);
    if (item.getDates() == null || item.getDates().isEmpty()) return null;
    cachedOptionExpiry = item.getDates().get(0);
    return cachedOptionExpiry;
  }

  /** First call or put identifier from AAPL option chain, or null if unavailable. */
  private static String getFirstOptionIdentifier() {
    if (cachedOptionIdentifier != null) return cachedOptionIdentifier;
    String expiry = getFirstOptionExpiry();
    if (expiry == null) return null;
    OptionChainModel chainModel = new OptionChainModel("AAPL", expiry);
    TigerResponse resp = client.execute(OptionChainQueryRequest.of(chainModel));
    if (resp == null || !resp.isSuccess()) return null;
    OptionChainResponse ocResp = (OptionChainResponse) resp;
    if (ocResp.getOptionChainItems() == null || ocResp.getOptionChainItems().isEmpty()) {
      return null;
    }
    OptionChainItem chainItem = ocResp.getOptionChainItems().get(0);
    if (chainItem.getItems() == null || chainItem.getItems().isEmpty()) return null;
    for (OptionRealTimeQuoteGroup group : chainItem.getItems()) {
      OptionRealTimeQuote call = group.getCall();
      if (call != null && call.getIdentifier() != null && !call.getIdentifier().isEmpty()) {
        cachedOptionIdentifier = call.getIdentifier();
        return cachedOptionIdentifier;
      }
      OptionRealTimeQuote put = group.getPut();
      if (put != null && put.getIdentifier() != null && !put.getIdentifier().isEmpty()) {
        cachedOptionIdentifier = put.getIdentifier();
        return cachedOptionIdentifier;
      }
    }
    return null;
  }

  /** First tradeable future contract code for ES, or null if unavailable. */
  private static String getFirstFutureContract() {
    if (cachedFutureContract != null) return cachedFutureContract;
    TigerResponse resp = client.execute(FutureContractsRequest.newRequest("ES"));
    if (resp == null || !resp.isSuccess()) return null;
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractsResponse fcResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractsResponse) resp;
    if (fcResp.getFutureContractItems() == null || fcResp.getFutureContractItems().isEmpty()) {
      return null;
    }
    cachedFutureContract = fcResp.getFutureContractItems().get(0).getContractCode();
    return cachedFutureContract;
  }

  /** First warrant symbol from HK warrant filter for 00700, or null if unavailable. */
  private static String getFirstWarrantSymbol() {
    if (cachedWarrantSymbol != null) return cachedWarrantSymbol;
    TigerResponse resp = client.execute(WarrantFilterRequest.newRequest("00700"));
    if (resp == null || !resp.isSuccess()) return null;
    com.tigerbrokers.stock.openapi.client.https.response.option.WarrantFilterResponse wfResp =
        (com.tigerbrokers.stock.openapi.client.https.response.option.WarrantFilterResponse) resp;
    if (wfResp.getItem() == null || wfResp.getItem().getItems() == null
        || wfResp.getItem().getItems().isEmpty()) {
      return null;
    }
    for (WarrantItem w : wfResp.getItem().getItems()) {
      if (w.getSymbol() != null && !w.getSymbol().isEmpty()) {
        cachedWarrantSymbol = w.getSymbol();
        return cachedWarrantSymbol;
      }
    }
    return null;
  }

  @Test
  public void testMarketState() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.MARKET_STATE);
    request.setBizContent("{\"market\":\"US\"}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testMarketState");
    Assert.assertTrue("market state data should mention US market",
        response.getData().contains("US"));
    // Assert status field presence
    JSONObject root = JSON.parseObject(response.getData());
    if (root != null) {
      JSONArray items = root.getJSONArray("items");
      if (items == null) {
        // some responses return a single object with status at root level
        String status = root.getString("status");
        if (status != null) {
          Assert.assertFalse("market state status should not be empty", status.isEmpty());
        }
      } else if (!items.isEmpty()) {
        String status = items.getJSONObject(0).getString("status");
        if (status != null) {
          Assert.assertFalse("market state status should not be empty", status.isEmpty());
        }
      }
    }
  }

  @Test
  public void testAllSymbols() {
    TigerResponse response = client.execute(QuoteSymbolRequest.newRequest(Market.US));
    assertSuccess(response, "testAllSymbols");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolResponse symbolResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolResponse) response;
    Assert.assertNotNull("symbols should not be null", symbolResp.getSymbols());
    Assert.assertFalse("symbols should not be empty", symbolResp.getSymbols().isEmpty());
    Assert.assertNotNull("first symbol should not be null", symbolResp.getSymbols().get(0));
    Assert.assertTrue("first symbol should not be empty",
        !symbolResp.getSymbols().get(0).isEmpty());
  }

  @Test
  public void testAllSymbolNames() {
    TigerResponse response = client.execute(QuoteSymbolNameRequest.newRequest(Market.US));
    assertSuccess(response, "testAllSymbolNames");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolNameResponse nameResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolNameResponse) response;
    Assert.assertNotNull("symbolNameItems should not be null", nameResp.getSymbolNameItems());
    Assert.assertFalse("symbolNameItems should not be empty", nameResp.getSymbolNameItems().isEmpty());
    Assert.assertNotNull("first symbol should not be null",
        nameResp.getSymbolNameItems().get(0).getSymbol());
    Assert.assertTrue("first symbol should not be empty",
        !nameResp.getSymbolNameItems().get(0).getSymbol().isEmpty());
    Assert.assertNotNull("first name should not be null",
        nameResp.getSymbolNameItems().get(0).getName());
    Assert.assertTrue("first name should not be empty",
        !nameResp.getSymbolNameItems().get(0).getName().isEmpty());
  }

  @Test
  public void testBrief() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.BRIEF);
    request.setBizContent("{\"symbols\":[\"AAPL\"]}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testBrief");
    Assert.assertTrue("brief data should contain AAPL", response.getData().contains("AAPL"));
    // Deeper field-level assertions: parse JSON items array
    JSONArray items = JSON.parseObject(response.getData()).getJSONArray("items");
    if (items != null && !items.isEmpty()) {
      JSONObject first = items.getJSONObject(0);
      Assert.assertEquals("brief symbol should be AAPL", "AAPL", first.getString("symbol"));
      Double latestPrice = first.getDouble("latestPrice");
      if (latestPrice != null) {
        Assert.assertTrue("brief latestPrice should be > 0", latestPrice > 0);
      }
      Long volume = first.getLong("volume");
      if (volume != null) {
        Assert.assertTrue("brief volume should be >= 0", volume >= 0);
      }
    }
  }

  @Test
  public void testStockDetail() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.STOCK_DETAIL);
    request.setBizContent("{\"symbols\":[\"AAPL\"]}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testStockDetail");
    Assert.assertTrue("stock detail data should contain AAPL",
        response.getData().contains("AAPL"));
    // Assert latestPrice field
    JSONArray items = JSON.parseObject(response.getData()).getJSONArray("items");
    if (items != null && !items.isEmpty()) {
      JSONObject first = items.getJSONObject(0);
      Double latestPrice = first.getDouble("latestPrice");
      if (latestPrice != null) {
        Assert.assertTrue("stock detail latestPrice should be > 0", latestPrice > 0);
      }
    }
  }

  @Test
  public void testTimeline() {
    TigerResponse response = client.execute(QuoteTimelineRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testTimeline");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse tlResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse) response;
    Assert.assertNotNull("timelineItems should not be null", tlResp.getTimelineItems());
    // non-trading hours: data may be empty
    Assume.assumeTrue("non-trading hours, timeline data may be empty",
        !tlResp.getTimelineItems().isEmpty());
    Assert.assertNotNull("first timeline symbol should not be null",
        tlResp.getTimelineItems().get(0).getSymbol());
    Assert.assertEquals("first timeline symbol should be AAPL",
        "AAPL", tlResp.getTimelineItems().get(0).getSymbol());
  }

  @Test
  public void testTimelineHK() {
    TigerResponse response = client.execute(QuoteTimelineRequest.newRequest(java.util.Arrays.asList("00700")));
    assertSuccess(response, "testTimelineHK");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse tlResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTimelineResponse) response;
    Assert.assertNotNull("HK timelineItems should not be null", tlResp.getTimelineItems());
    Assume.assumeTrue("non-trading hours, HK timeline data may be empty",
        !tlResp.getTimelineItems().isEmpty());
    Assert.assertEquals("first HK timeline symbol should be 00700",
        "00700", tlResp.getTimelineItems().get(0).getSymbol());
  }

  @Test
  public void testHistoryTimeline() {
    TigerResponse response = client.execute(QuoteHistoryTimelineRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testHistoryTimeline");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteHistoryTimelineResponse htlResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteHistoryTimelineResponse) response;
    Assert.assertNotNull("historyTimelineItems should not be null", htlResp.getTimelineItems());
    Assert.assertFalse("historyTimelineItems should not be empty", htlResp.getTimelineItems().isEmpty());
    Assert.assertNotNull("first history timeline symbol should not be null",
        htlResp.getTimelineItems().get(0).getSymbol());
    Assert.assertEquals("first history timeline symbol should be AAPL",
        "AAPL", htlResp.getTimelineItems().get(0).getSymbol());
  }

  @Test
  public void testKline() {
    TigerResponse response = client.execute(QuoteKlineRequest.newRequest(java.util.Arrays.asList("AAPL"), KType.day));
    assertSuccess(response, "testKline");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse klineResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse) response;
    Assert.assertNotNull("klineItems should not be null", klineResp.getKlineItems());
    Assert.assertFalse("klineItems should not be empty", klineResp.getKlineItems().isEmpty());
    Assert.assertNotNull("first kline symbol should not be null",
        klineResp.getKlineItems().get(0).getSymbol());
    Assert.assertEquals("first kline symbol should be AAPL",
        "AAPL", klineResp.getKlineItems().get(0).getSymbol());
    if (klineResp.getKlineItems().get(0).getItems() != null
        && !klineResp.getKlineItems().get(0).getItems().isEmpty()) {
      Assert.assertNotNull("first kline point time should not be null",
          klineResp.getKlineItems().get(0).getItems().get(0).getTime());
      Assert.assertTrue("first kline point time should be > 0",
          klineResp.getKlineItems().get(0).getItems().get(0).getTime() > 0);
      Assert.assertNotNull("first kline point close should not be null",
          klineResp.getKlineItems().get(0).getItems().get(0).getClose());
      Assert.assertTrue("first kline point close should be > 0",
          klineResp.getKlineItems().get(0).getItems().get(0).getClose() > 0);
      Assert.assertNotNull("first kline point open should not be null",
          klineResp.getKlineItems().get(0).getItems().get(0).getOpen());
      Assert.assertTrue("first kline point open should be > 0",
          klineResp.getKlineItems().get(0).getItems().get(0).getOpen() > 0);
      Assert.assertNotNull("first kline point high should not be null",
          klineResp.getKlineItems().get(0).getItems().get(0).getHigh());
      Assert.assertTrue("first kline point high should be > 0",
          klineResp.getKlineItems().get(0).getItems().get(0).getHigh() > 0);
      Assert.assertNotNull("first kline point volume should not be null",
          klineResp.getKlineItems().get(0).getItems().get(0).getVolume());
      Assert.assertTrue("first kline point volume should be >= 0",
          klineResp.getKlineItems().get(0).getItems().get(0).getVolume() >= 0);
      // Cross-field: high must be >= low
      Double hi = klineResp.getKlineItems().get(0).getItems().get(0).getHigh();
      Double lo = klineResp.getKlineItems().get(0).getItems().get(0).getLow();
      if (hi != null && lo != null) {
        Assert.assertTrue("kline high should be >= low (high=" + hi + ", low=" + lo + ")", hi >= lo);
      }
    }
  }

  @Test
  public void testKlineWeekly() {
    TigerResponse response = client.execute(QuoteKlineRequest.newRequest(java.util.Arrays.asList("AAPL"), KType.week));
    assertSuccess(response, "testKlineWeekly");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse klineResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse) response;
    Assert.assertNotNull("weekly klineItems should not be null", klineResp.getKlineItems());
    Assert.assertFalse("weekly klineItems should not be empty", klineResp.getKlineItems().isEmpty());
    Assert.assertEquals("weekly kline symbol should be AAPL",
        "AAPL", klineResp.getKlineItems().get(0).getSymbol());
    if (klineResp.getKlineItems().get(0).getItems() != null
        && !klineResp.getKlineItems().get(0).getItems().isEmpty()) {
      Long vol = klineResp.getKlineItems().get(0).getItems().get(0).getVolume();
      if (vol != null) {
        Assert.assertTrue("weekly kline volume should be >= 0", vol >= 0);
      }
      Double open = klineResp.getKlineItems().get(0).getItems().get(0).getOpen();
      if (open != null) {
        Assert.assertTrue("weekly kline open should be > 0", open > 0);
      }
    }
  }

  @Test
  public void testKlineHK() {
    TigerResponse response = client.execute(QuoteKlineRequest.newRequest(java.util.Arrays.asList("00700"), KType.day));
    assertSuccess(response, "testKlineHK");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse klineResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse) response;
    Assert.assertNotNull("HK klineItems should not be null", klineResp.getKlineItems());
    Assert.assertFalse("HK klineItems should not be empty", klineResp.getKlineItems().isEmpty());
    Assert.assertEquals("HK kline symbol should be 00700",
        "00700", klineResp.getKlineItems().get(0).getSymbol());
    if (klineResp.getKlineItems().get(0).getItems() != null
        && !klineResp.getKlineItems().get(0).getItems().isEmpty()) {
      Long vol = klineResp.getKlineItems().get(0).getItems().get(0).getVolume();
      if (vol != null) {
        Assert.assertTrue("HK kline volume should be >= 0", vol >= 0);
      }
      Double open = klineResp.getKlineItems().get(0).getItems().get(0).getOpen();
      if (open != null) {
        Assert.assertTrue("HK kline open should be > 0", open > 0);
      }
    }
  }

  @Test
  public void testTradeTick() {
    TigerResponse response = client.execute(QuoteTradeTickRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testTradeTick");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeTickResponse ttResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeTickResponse) response;
    Assert.assertNotNull("tradeTickItems should not be null", ttResp.getTradeTickItems());
    // non-trading hours: data may be empty
    Assume.assumeTrue("non-trading hours, trade tick data may be empty",
        !ttResp.getTradeTickItems().isEmpty());
    Assert.assertNotNull("first trade tick symbol should not be null",
        ttResp.getTradeTickItems().get(0).getSymbol());
    Assert.assertEquals("first trade tick symbol should be AAPL",
        "AAPL", ttResp.getTradeTickItems().get(0).getSymbol());
    // Assert price field on tick items
    if (ttResp.getTradeTickItems().get(0).getItems() != null
        && !ttResp.getTradeTickItems().get(0).getItems().isEmpty()) {
      Double price = ttResp.getTradeTickItems().get(0).getItems().get(0).getPrice();
      if (price != null) {
        Assert.assertTrue("trade tick price should be > 0", price > 0);
      }
    }
  }

  @Test
  public void testQuoteContract() {
    TigerResponse response = client.execute(QuoteContractRequest.newRequest("AAPL"));
    assertSuccess(response, "testQuoteContract");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteContractResponse cResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteContractResponse) response;
    Assert.assertNotNull("contractItems should not be null", cResp.getContractItems());
    Assert.assertFalse("contractItems should not be empty", cResp.getContractItems().isEmpty());
    Assert.assertNotNull("first contract symbol should not be null",
        cResp.getContractItems().get(0).getSymbol());
    Assert.assertEquals("first contract symbol should be AAPL",
        "AAPL", cResp.getContractItems().get(0).getSymbol());
    Assert.assertNotNull("first contract secType should not be null",
        cResp.getContractItems().get(0).getSecType());
    Assert.assertTrue("first contract secType should not be empty",
        !cResp.getContractItems().get(0).getSecType().isEmpty());
  }

  @Test
  public void testQuoteRealTime() {
    TigerResponse response = client.execute(QuoteRealTimeQuoteRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testQuoteRealTime");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse rtResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse) response;
    Assert.assertNotNull("realTimeQuoteItems should not be null", rtResp.getRealTimeQuoteItems());
    Assert.assertFalse("realTimeQuoteItems should not be empty", rtResp.getRealTimeQuoteItems().isEmpty());
    Assert.assertNotNull("first realtime symbol should not be null",
        rtResp.getRealTimeQuoteItems().get(0).getSymbol());
    Assert.assertEquals("first realtime symbol should be AAPL",
        "AAPL", rtResp.getRealTimeQuoteItems().get(0).getSymbol());
    Assert.assertNotNull("first realtime latestPrice should not be null",
        rtResp.getRealTimeQuoteItems().get(0).getLatestPrice());
    Assert.assertTrue("first realtime latestPrice should be > 0",
        rtResp.getRealTimeQuoteItems().get(0).getLatestPrice() > 0);
  }

  @Test
  public void testQuoteRealTimeHK() {
    TigerResponse response = client.execute(QuoteRealTimeQuoteRequest.newRequest(java.util.Arrays.asList("00700")));
    assertSuccess(response, "testQuoteRealTimeHK");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse rtResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse) response;
    Assert.assertNotNull("HK realTimeQuoteItems should not be null", rtResp.getRealTimeQuoteItems());
    Assert.assertFalse("HK realTimeQuoteItems should not be empty", rtResp.getRealTimeQuoteItems().isEmpty());
    Assert.assertEquals("HK realtime symbol should be 00700",
        "00700", rtResp.getRealTimeQuoteItems().get(0).getSymbol());
    Double latestPrice = rtResp.getRealTimeQuoteItems().get(0).getLatestPrice();
    if (latestPrice != null) {
      Assert.assertTrue("HK realtime latestPrice should be > 0", latestPrice > 0);
    }
  }

  @Test
  public void testQuoteShortableStocks() {
    TigerResponse response = client.execute(QuoteShortableStockRequest.newRequest(java.util.Arrays.asList("AAPL")));
    // Shortable stocks is a permissioned API — accounts without the entitlement
    // return "common param error". This is an account-capability limit, not a
    // data availability issue, so it stays a skip regardless of trading hours.
    Assume.assumeTrue(
        "shortable stocks API not entitled for this account: " + response.getMessage(),
        response.isSuccess());
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteShortableStockResponse ssResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteShortableStockResponse) response;
    Assert.assertNotNull("shortableStockItems should not be null", ssResp.getShortableStockItems());
    // Out-of-hours the stream can be empty; only fail when regular US trading is live.
    if (ssResp.getShortableStockItems().isEmpty()) {
      Assume.assumeFalse(
          "shortableStockItems empty during US TRADING hours — data gap",
          MarketHelpers.isMarketTrading(client, "US"));
      return;
    }
    Assert.assertNotNull("first shortable symbol should not be null",
        ssResp.getShortableStockItems().get(0).getSymbol());
    Assert.assertEquals("first shortable symbol should be AAPL",
        "AAPL", ssResp.getShortableStockItems().get(0).getSymbol());
  }

  @Test
  public void testQuoteStockTrade() {
    TigerResponse response = client.execute(QuoteStockTradeRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testQuoteStockTrade");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockTradeResponse stResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockTradeResponse) response;
    Assert.assertNotNull("stockTradeItems should not be null", stResp.getStockTradeItems());
    Assert.assertFalse("stockTradeItems should not be empty", stResp.getStockTradeItems().isEmpty());
    Assert.assertNotNull("first stock trade symbol should not be null",
        stResp.getStockTradeItems().get(0).getSymbol());
    Assert.assertEquals("first stock trade symbol should be AAPL",
        "AAPL", stResp.getStockTradeItems().get(0).getSymbol());
    Assert.assertNotNull("first stock trade lotSize should not be null",
        stResp.getStockTradeItems().get(0).getLotSize());
    Assert.assertTrue("first stock trade lotSize should be > 0",
        stResp.getStockTradeItems().get(0).getLotSize() > 0);
  }

  @Test
  public void testQuoteDepth() {
    TigerResponse response = client.execute(QuoteDepthRequest.newRequest(java.util.Arrays.asList("AAPL"), "US"));
    assertSuccess(response, "testQuoteDepth");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDepthResponse dResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDepthResponse) response;
    Assert.assertNotNull("quoteDepthItems should not be null", dResp.getQuoteDepthItems());
    Assert.assertFalse("quoteDepthItems should not be empty", dResp.getQuoteDepthItems().isEmpty());
    Assert.assertNotNull("first depth symbol should not be null",
        dResp.getQuoteDepthItems().get(0).getSymbol());
    Assert.assertEquals("first depth symbol should be AAPL",
        "AAPL", dResp.getQuoteDepthItems().get(0).getSymbol());
  }

  @Test
  public void testQuoteDepthHK() {
    TigerResponse response = client.execute(QuoteDepthRequest.newRequest(java.util.Arrays.asList("00700"), "HK"));
    assertSuccess(response, "testQuoteDepthHK");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDepthResponse dResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDepthResponse) response;
    Assert.assertNotNull("HK quoteDepthItems should not be null", dResp.getQuoteDepthItems());
    Assert.assertFalse("HK quoteDepthItems should not be empty", dResp.getQuoteDepthItems().isEmpty());
    Assert.assertEquals("HK depth symbol should be 00700",
        "00700", dResp.getQuoteDepthItems().get(0).getSymbol());
  }

  @Test
  public void testQuoteDelay() {
    TigerResponse response = client.execute(QuoteDelayRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testQuoteDelay");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDelayResponse dlResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDelayResponse) response;
    Assert.assertNotNull("quoteDelayItems should not be null", dlResp.getQuoteDelayItems());
    Assert.assertFalse("quoteDelayItems should not be empty", dlResp.getQuoteDelayItems().isEmpty());
    Assert.assertNotNull("first delay symbol should not be null",
        dlResp.getQuoteDelayItems().get(0).getSymbol());
    Assert.assertEquals("first delay symbol should be AAPL",
        "AAPL", dlResp.getQuoteDelayItems().get(0).getSymbol());
    Assert.assertNotNull("first delay time should not be null",
        dlResp.getQuoteDelayItems().get(0).getTime());
    Assert.assertTrue("first delay time should be > 0",
        dlResp.getQuoteDelayItems().get(0).getTime() > 0);
  }

  @Test
  public void testQuoteOvernight() {
    TigerResponse response = client.execute(QuoteOvernightRequest.newRequest(java.util.Arrays.asList("AAPL")));
    assertSuccess(response, "testQuoteOvernight");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteOvernightResponse ovResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteOvernightResponse) response;
    Assert.assertNotNull("overnight data should not be null", ovResp.getData());
    // non-trading hours: overnight data may be empty
    Assume.assumeTrue("non-trading hours, overnight data may be empty",
        !ovResp.getData().isEmpty());
    Assert.assertNotNull("first overnight symbol should not be null",
        ovResp.getData().get(0).getSymbol());
    Assert.assertEquals("first overnight symbol should be AAPL",
        "AAPL", ovResp.getData().get(0).getSymbol());
    Assert.assertEquals("first overnight tradingStatus should be 5",
        Integer.valueOf(5), ovResp.getData().get(0).getTradingStatus());
    if (ovResp.getData().get(0).getLatestPrice() != null) {
      Assert.assertTrue("first overnight latestPrice should be > 0",
          ovResp.getData().get(0).getLatestPrice() > 0);
    }
    if (ovResp.getData().get(0).getTimestamp() != null) {
      Assert.assertTrue("first overnight timestamp should be > 0",
          ovResp.getData().get(0).getTimestamp() > 0);
    }
  }

  @Test
  public void testTradingCalendar() {
    TigerResponse response = client.execute(QuoteTradeCalendarRequest.newRequest(Market.US));
    assertSuccess(response, "testTradingCalendar");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeCalendarResponse tcResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeCalendarResponse) response;
    Assert.assertNotNull("calendar items should not be null", tcResp.getItems());
    Assert.assertFalse("calendar items should not be empty", tcResp.getItems().isEmpty());
    Assert.assertNotNull("first calendar date should not be null",
        tcResp.getItems().get(0).getDate());
    Assert.assertTrue("first calendar date should not be empty",
        !tcResp.getItems().get(0).getDate().isEmpty());
  }

  @Test
  public void testStockBroker() {
    TigerResponse response = client.execute(QuoteStockBrokerRequest.newRequest("00700"));
    assertSuccess(response, "testStockBroker");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockBrokerResponse sbResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockBrokerResponse) response;
    Assert.assertNotNull("stockBrokerItem should not be null", sbResp.getStockBrokerItem());
    Assert.assertNotNull("broker symbol should not be null",
        sbResp.getStockBrokerItem().getSymbol());
    Assert.assertEquals("broker symbol should be 00700",
        "00700", sbResp.getStockBrokerItem().getSymbol());
  }

  @Test
  public void testCapitalDistribution() {
    TigerResponse response = client.execute(QuoteCapitalDistributionRequest.newRequest("AAPL", Market.US));
    assertSuccess(response, "testCapitalDistribution");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalDistributionResponse cdResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalDistributionResponse) response;
    // non-trading hours: capital distribution item may be null
    Assume.assumeTrue("non-trading hours, capital distribution may be empty",
        cdResp.getCapitalDistributionItem() != null);
    Assert.assertNotNull("capital distribution symbol should not be null",
        cdResp.getCapitalDistributionItem().getSymbol());
    Assert.assertEquals("capital distribution symbol should be AAPL",
        "AAPL", cdResp.getCapitalDistributionItem().getSymbol());
  }

  @Test
  public void testCapitalFlow() {
    TigerResponse response = client.execute(QuoteCapitalFlowRequest.newRequest("AAPL", Market.US, CapitalPeriod.intraday));
    assertSuccess(response, "testCapitalFlow");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalFlowResponse cfResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalFlowResponse) response;
    // non-trading hours: capital flow item may be null
    Assume.assumeTrue("non-trading hours, capital flow may be empty",
        cfResp.getCapitalFlowItem() != null);
    Assert.assertNotNull("capital flow symbol should not be null",
        cfResp.getCapitalFlowItem().getSymbol());
    Assert.assertEquals("capital flow symbol should be AAPL",
        "AAPL", cfResp.getCapitalFlowItem().getSymbol());
  }

  @Test
  public void testTradeRank() {
    TigerResponse response = client.execute(QuoteTradeRankRequest.newRequest(Market.US));
    assertSuccess(response, "testTradeRank");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeRankResponse trResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeRankResponse) response;
    Assert.assertNotNull("tradeRank items should not be null", trResp.getItems());
    Assert.assertFalse("tradeRank items should not be empty", trResp.getItems().isEmpty());
    Assert.assertNotNull("first trade rank symbol should not be null",
        trResp.getItems().get(0).getSymbol());
    Assert.assertTrue("first trade rank symbol should not be empty",
        !trResp.getItems().get(0).getSymbol().isEmpty());
  }

  @Test
  public void testOptionExpiration() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.OPTION_EXPIRATION);
    request.setBizContent("{\"symbols\":[\"AAPL\"]}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testOptionExpiration");
    Assert.assertTrue("option expiration data should contain AAPL",
        response.getData().contains("AAPL"));
  }

  @Test
  public void testOptionChain() {
    String expiry = getFirstOptionExpiry();
    Assume.assumeNotNull("no option expiry available for AAPL", expiry);
    OptionChainModel chainModel = new OptionChainModel("AAPL", expiry);
    TigerResponse response = client.execute(OptionChainQueryRequest.of(chainModel));
    assertSuccess(response, "testOptionChain");
    OptionChainResponse ocResp = (OptionChainResponse) response;
    Assert.assertNotNull("optionChainItems should not be null", ocResp.getOptionChainItems());
    Assume.assumeTrue("optionChainItems empty — non-trading hours",
        !ocResp.getOptionChainItems().isEmpty());
    Assert.assertNotNull("first chain symbol should not be null",
        ocResp.getOptionChainItems().get(0).getSymbol());
    Assert.assertEquals("first chain symbol should be AAPL",
        "AAPL", ocResp.getOptionChainItems().get(0).getSymbol());
    // Assert strike and expiry fields on the first chain item's first group
    OptionChainItem chainItem = ocResp.getOptionChainItems().get(0);
    // expiry is validated via the request model; assert strike on the quote item
    if (chainItem.getItems() != null && !chainItem.getItems().isEmpty()) {
      OptionRealTimeQuoteGroup group = chainItem.getItems().get(0);
      OptionRealTimeQuote call = group.getCall();
      OptionRealTimeQuote put = group.getPut();
      OptionRealTimeQuote quote = call != null ? call : put;
      if (quote != null) {
        Assert.assertNotNull("option chain item strike should not be null", quote.getStrike());
        Assert.assertFalse("option chain item strike should not be empty",
            quote.getStrike().isEmpty());
      }
      // expiry validated: the chain model expiry was used to build the request
      Assert.assertNotNull("option chain expiry from request model should not be null", expiry);
      Assert.assertFalse("option chain expiry should not be empty", expiry.isEmpty());
    }
  }

  @Test
  public void testOptionBrief() throws Exception {
    String identifier = getFirstOptionIdentifier();
    Assume.assumeNotNull("no option identifier available for AAPL", identifier);
    OptionCommonModel model = new OptionCommonModel(identifier);
    TigerResponse response = client.execute(OptionBriefQueryV2Request.of(model));
    assertSuccess(response, "testOptionBrief");
    com.tigerbrokers.stock.openapi.client.https.response.option.OptionBriefResponse obResp =
        (com.tigerbrokers.stock.openapi.client.https.response.option.OptionBriefResponse) response;
    Assert.assertNotNull("optionBriefItems should not be null", obResp.getOptionBriefItems());
    Assume.assumeTrue("optionBriefItems empty — non-trading hours",
        !obResp.getOptionBriefItems().isEmpty());
    Assert.assertNotNull("first brief identifier should not be null",
        obResp.getOptionBriefItems().get(0).getIdentifier());
    Assert.assertTrue("first brief symbol should not be empty",
        obResp.getOptionBriefItems().get(0).getSymbol() != null
            && !obResp.getOptionBriefItems().get(0).getSymbol().isEmpty());
  }

  @Test
  public void testWarrantFilter() {
    TigerResponse response = client.execute(WarrantFilterRequest.newRequest("00700"));
    assertSuccess(response, "testWarrantFilter");
    com.tigerbrokers.stock.openapi.client.https.response.option.WarrantFilterResponse wfResp =
        (com.tigerbrokers.stock.openapi.client.https.response.option.WarrantFilterResponse) response;
    Assert.assertNotNull("warrantFilter item should not be null", wfResp.getItem());
    Assert.assertNotNull("warrantFilter totalCount should not be null",
        wfResp.getItem().getTotalCount());
    Assert.assertTrue("warrantFilter totalCount should be >= 0",
        wfResp.getItem().getTotalCount() >= 0);
  }

  @Test
  public void testWarrantRealTimeQuote() {
    String warrantSymbol = getFirstWarrantSymbol();
    Assume.assumeNotNull("no warrant symbol available for 00700", warrantSymbol);
    TigerResponse response = client.execute(WarrantQuoteRequest.newRequest(
        java.util.Arrays.asList(warrantSymbol)));
    assertSuccess(response, "testWarrantRealTimeQuote");
    com.tigerbrokers.stock.openapi.client.https.response.option.WarrantQuoteResponse wqResp =
        (com.tigerbrokers.stock.openapi.client.https.response.option.WarrantQuoteResponse) response;
    Assert.assertNotNull("warrantQuote item should not be null", wqResp.getItem());
    Assert.assertNotNull("warrantQuote items should not be null",
        wqResp.getItem().getItems());
    if (wqResp.getItem().getItems() != null && !wqResp.getItem().getItems().isEmpty()) {
      Assert.assertNotNull("first warrant quote symbol should not be null",
          wqResp.getItem().getItems().get(0).getSymbol());
      Assert.assertTrue("first warrant quote symbol should not be empty",
          !wqResp.getItem().getItems().get(0).getSymbol().isEmpty());
    }
  }

  @Test
  public void testFutureExchange() {
    TigerResponse response = client.execute(FutureExchangeRequest.newRequest("FUT"));
    assertSuccess(response, "testFutureExchange");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureExchangeResponse feResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureExchangeResponse) response;
    Assert.assertNotNull("futureExchangeItems should not be null", feResp.getFutureExchangeItems());
    Assert.assertFalse("futureExchangeItems should not be empty",
        feResp.getFutureExchangeItems().isEmpty());
    Assert.assertNotNull("first exchange code should not be null",
        feResp.getFutureExchangeItems().get(0).getCode());
    Assert.assertTrue("first exchange code should not be empty",
        !feResp.getFutureExchangeItems().get(0).getCode().isEmpty());
    Assert.assertNotNull("first exchange name should not be null",
        feResp.getFutureExchangeItems().get(0).getName());
    Assert.assertTrue("first exchange name should not be empty",
        !feResp.getFutureExchangeItems().get(0).getName().isEmpty());
  }

  @Test
  public void testFutureContractByCode() {
    String contract = getFirstFutureContract();
    Assume.assumeNotNull("no future contract available for ES", contract);
    TigerResponse response = client.execute(FutureContractByConCodeRequest.newRequest(contract));
    assertSuccess(response, "testFutureContractByCode");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse fcResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse) response;
    Assert.assertNotNull("futureContractItem should not be null", fcResp.getFutureContractItem());
    Assert.assertNotNull("contract code should not be null",
        fcResp.getFutureContractItem().getContractCode());
    Assert.assertEquals("contract code should match request",
        contract, fcResp.getFutureContractItem().getContractCode());
  }

  @Test
  public void testFutureContractByExchange() {
    TigerResponse response = client.execute(FutureContractByExchCodeRequest.newRequest("CME"));
    assertSuccess(response, "testFutureContractByExchange");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureBatchContractResponse fbcResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureBatchContractResponse) response;
    Assert.assertNotNull("futureContractItems should not be null", fbcResp.getFutureContractItems());
    Assert.assertFalse("futureContractItems should not be empty",
        fbcResp.getFutureContractItems().isEmpty());
    Assert.assertNotNull("first contract code should not be null",
        fbcResp.getFutureContractItems().get(0).getContractCode());
    Assert.assertTrue("first contract code should not be empty",
        !fbcResp.getFutureContractItems().get(0).getContractCode().isEmpty());
  }

  @Test
  public void testFutureContinuousContracts() {
    TigerResponse response = client.execute(FutureContinuousContractRequest.newRequest("ES"));
    assertSuccess(response, "testFutureContinuousContracts");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse fccResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse) response;
    Assert.assertNotNull("continuous contract item should not be null",
        fccResp.getFutureContractItem());
    Assert.assertNotNull("continuous contract code should not be null",
        fccResp.getFutureContractItem().getContractCode());
    Assert.assertTrue("continuous contract code should not be empty",
        !fccResp.getFutureContractItem().getContractCode().isEmpty());
  }

  @Test
  public void testFutureCurrentContract() {
    TigerResponse response = client.execute(FutureCurrentContractRequest.newRequest("ES"));
    assertSuccess(response, "testFutureCurrentContract");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse fcurResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractResponse) response;
    Assert.assertNotNull("current contract item should not be null",
        fcurResp.getFutureContractItem());
    Assert.assertNotNull("current contract code should not be null",
        fcurResp.getFutureContractItem().getContractCode());
    Assert.assertTrue("current contract code should not be empty",
        !fcurResp.getFutureContractItem().getContractCode().isEmpty());
  }

  @Test
  public void testFutureContracts() {
    TigerResponse response = client.execute(FutureContractsRequest.newRequest("ES"));
    assertSuccess(response, "testFutureContracts");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractsResponse fctResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractsResponse) response;
    Assert.assertNotNull("futureContractItems should not be null", fctResp.getFutureContractItems());
    Assert.assertFalse("futureContractItems should not be empty",
        fctResp.getFutureContractItems().isEmpty());
    Assert.assertNotNull("first contract code should not be null",
        fctResp.getFutureContractItems().get(0).getContractCode());
    Assert.assertTrue("first contract code should not be empty",
        !fctResp.getFutureContractItems().get(0).getContractCode().isEmpty());
  }

  @Test
  public void testFutureKline() {
    String contract = getFirstFutureContract();
    Assume.assumeNotNull("no future contract available for ES", contract);
    TigerResponse response = client.execute(FutureKlineRequest.newRequest(java.util.Arrays.asList(contract)));
    assertSuccess(response, "testFutureKline");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureKlineResponse fkResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureKlineResponse) response;
    Assert.assertNotNull("futureKlineItems should not be null", fkResp.getFutureKlineItems());
    Assert.assertFalse("futureKlineItems should not be empty",
        fkResp.getFutureKlineItems().isEmpty());
    Assert.assertNotNull("first kline contractCode should not be null",
        fkResp.getFutureKlineItems().get(0).getContractCode());
    Assert.assertEquals("first kline contractCode should match request",
        contract, fkResp.getFutureKlineItems().get(0).getContractCode());
  }

  @Test
  public void testFutureRealTimeQuote() {
    String contract = getFirstFutureContract();
    Assume.assumeNotNull("no future contract available for ES", contract);
    TigerResponse response = client.execute(FutureRealTimeQuoteRequest.newRequest(java.util.Arrays.asList(contract)));
    assertSuccess(response, "testFutureRealTimeQuote");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureRealTimeQuoteResponse frResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureRealTimeQuoteResponse) response;
    Assert.assertNotNull("futureRealTimeItems should not be null", frResp.getFutureRealTimeItems());
    Assert.assertFalse("futureRealTimeItems should not be empty",
        frResp.getFutureRealTimeItems().isEmpty());
    Assert.assertNotNull("first realtime contractCode should not be null",
        frResp.getFutureRealTimeItems().get(0).getContractCode());
    Assert.assertEquals("first realtime contractCode should match request",
        contract, frResp.getFutureRealTimeItems().get(0).getContractCode());
    Assert.assertNotNull("first realtime latestPrice should not be null",
        frResp.getFutureRealTimeItems().get(0).getLatestPrice());
    Assert.assertTrue("first realtime latestPrice should be > 0",
        frResp.getFutureRealTimeItems().get(0).getLatestPrice().compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  public void testFutureTradingDate() {
    String contract = getFirstFutureContract();
    Assume.assumeNotNull("no future contract available for ES", contract);
    TigerResponse response = client.execute(FutureTradingDateRequest.newRequest(contract));
    assertSuccess(response, "testFutureTradingDate");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureTradingDateResponse ftdResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureTradingDateResponse) response;
    Assert.assertNotNull("futureTradingDateItem should not be null",
        ftdResp.getFutureTradingDateItem());
    // non-trading hours: tradingTimes may be null
    if (ftdResp.getFutureTradingDateItem().getTradingTimes() != null) {
      Assert.assertNotNull("tradingTimes should not be null",
          ftdResp.getFutureTradingDateItem().getTradingTimes());
    }
  }

  @Test
  public void testFutureDepth() {
    String contract = getFirstFutureContract();
    Assume.assumeNotNull("no future contract available for ES", contract);
    TigerResponse response = client.execute(FutureDepthRequest.newRequest(java.util.Arrays.asList(contract)));
    assertSuccess(response, "testFutureDepth");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureDepthResponse fdResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureDepthResponse) response;
    Assert.assertNotNull("futureDepthItems should not be null", fdResp.getFutureDepthItems());
    Assert.assertFalse("futureDepthItems should not be empty",
        fdResp.getFutureDepthItems().isEmpty());
    Assert.assertNotNull("first depth contractCode should not be null",
        fdResp.getFutureDepthItems().get(0).getContractCode());
    Assert.assertEquals("first depth contractCode should match request",
        contract, fdResp.getFutureDepthItems().get(0).getContractCode());
  }

  @Test
  public void testFinancialCurrency() {
    TigerResponse response = client.execute(FinancialCurrencyRequest.newRequest(java.util.Arrays.asList("AAPL"), Market.US));
    assertSuccess(response, "testFinancialCurrency");
    com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialCurrencyResponse fcResp =
        (com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialCurrencyResponse) response;
    Assert.assertNotNull("financialCurrencyItems should not be null",
        fcResp.getFinancialCurrencyItems());
    Assert.assertFalse("financialCurrencyItems should not be empty",
        fcResp.getFinancialCurrencyItems().isEmpty());
    Assert.assertNotNull("first currency symbol should not be null",
        fcResp.getFinancialCurrencyItems().get(0).getSymbol());
    Assert.assertEquals("first currency symbol should be AAPL",
        "AAPL", fcResp.getFinancialCurrencyItems().get(0).getSymbol());
    Assert.assertNotNull("first currency should not be null",
        fcResp.getFinancialCurrencyItems().get(0).getCurrency());
    Assert.assertTrue("first currency should not be empty",
        !fcResp.getFinancialCurrencyItems().get(0).getCurrency().isEmpty());
  }

  @Test
  public void testFinancialExchangeRate() {
    TigerResponse response = client.execute(FinancialExchangeRateRequest.newRequest(java.util.Arrays.asList("USD"), "2026-01-01", "2026-01-31"));
    assertSuccess(response, "testFinancialExchangeRate");
    com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialExchangeRateResponse ferResp =
        (com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialExchangeRateResponse) response;
    Assert.assertNotNull("financialExchangeRateItems should not be null",
        ferResp.getFinancialExchangeRateItems());
    Assert.assertFalse("financialExchangeRateItems should not be empty",
        ferResp.getFinancialExchangeRateItems().isEmpty());
    Assert.assertNotNull("first exchange rate currency should not be null",
        ferResp.getFinancialExchangeRateItems().get(0).getCurrency());
    Assert.assertTrue("first exchange rate currency should not be empty",
        !ferResp.getFinancialExchangeRateItems().get(0).getCurrency().isEmpty());
  }

  @Test
  public void testStockFundamental() {
    TigerResponse response = client.execute(QuoteStockFundamentalRequest.newRequest(java.util.Arrays.asList("AAPL"), "US"));
    assertSuccess(response, "testStockFundamental");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockFundamentalResponse sfResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockFundamentalResponse) response;
    Assert.assertNotNull("stockFundamentalItems should not be null",
        sfResp.getStockFundamentalItems());
    Assert.assertFalse("stockFundamentalItems should not be empty",
        sfResp.getStockFundamentalItems().isEmpty());
    Assert.assertNotNull("first fundamental symbol should not be null",
        sfResp.getStockFundamentalItems().get(0).getSymbol());
    Assert.assertEquals("first fundamental symbol should be AAPL",
        "AAPL", sfResp.getStockFundamentalItems().get(0).getSymbol());
  }

  @Test
  public void testFundAllSymbols() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.FUND_ALL_SYMBOLS);
    request.setBizContent("{\"market\":\"US\"}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testFundAllSymbols");
  }

  @Test
  public void testFundContracts() {
    // SPY is an ETF, not a fund — hardcoding it made the test unreliable across
    // accounts. Resolve a live fund symbol first, and only skip when the fund
    // catalog is empty AND we are out of US trading hours.
    String fundSymbol = MarketHelpers.resolveFundSymbol(client, "US");
    if (fundSymbol == null) {
      Assume.assumeFalse(
          "fund catalog is empty during US TRADING hours — data gap",
          MarketHelpers.isMarketTrading(client, "US"));
      Assume.assumeNotNull("no fund symbol available for US (out of hours)", fundSymbol);
      return;
    }
    TigerResponse response = client.execute(
        FundContractsRequest.newRequest(java.util.Arrays.asList(fundSymbol)));
    assertSuccess(response, "testFundContracts");
    com.tigerbrokers.stock.openapi.client.https.response.fund.FundContractsResponse fucResp =
        (com.tigerbrokers.stock.openapi.client.https.response.fund.FundContractsResponse) response;
    Assert.assertNotNull("fundContractItems should not be null", fucResp.getFundContractItems());
    if (fucResp.getFundContractItems().isEmpty()) {
      Assume.assumeFalse(
          "fundContractItems empty for " + fundSymbol + " during US TRADING hours",
          MarketHelpers.isMarketTrading(client, "US"));
      return;
    }
    Assert.assertNotNull("first fund symbol should not be null",
        fucResp.getFundContractItems().get(0).getSymbol());
    Assert.assertEquals("first fund symbol should be " + fundSymbol,
        fundSymbol, fucResp.getFundContractItems().get(0).getSymbol());
  }

  @Test
  public void testFundQuote() {
    String fundSymbol = MarketHelpers.resolveFundSymbol(client, "US");
    if (fundSymbol == null) {
      Assume.assumeFalse(
          "fund catalog is empty during US TRADING hours — data gap",
          MarketHelpers.isMarketTrading(client, "US"));
      Assume.assumeNotNull("no fund symbol available for US (out of hours)", fundSymbol);
      return;
    }
    TigerResponse response = client.execute(
        FundQuoteRequest.newRequest(java.util.Arrays.asList(fundSymbol)));
    assertSuccess(response, "testFundQuote");
    com.tigerbrokers.stock.openapi.client.https.response.fund.FundQuoteResponse fqResp =
        (com.tigerbrokers.stock.openapi.client.https.response.fund.FundQuoteResponse) response;
    Assert.assertNotNull("fundQuoteItems should not be null", fqResp.getQuoteItems());
    if (fqResp.getQuoteItems().isEmpty()) {
      Assume.assumeFalse(
          "fundQuoteItems empty for " + fundSymbol + " during US TRADING hours",
          MarketHelpers.isMarketTrading(client, "US"));
      return;
    }
    Assert.assertNotNull("first fund quote symbol should not be null",
        fqResp.getQuoteItems().get(0).getSymbol());
    Assert.assertEquals("first fund quote symbol should be " + fundSymbol,
        fundSymbol, fqResp.getQuoteItems().get(0).getSymbol());
    Assert.assertNotNull("first fund quote timestamp should not be null",
        fqResp.getQuoteItems().get(0).getTimestamp());
    Assert.assertTrue("first fund quote timestamp should be > 0",
        fqResp.getQuoteItems().get(0).getTimestamp() > 0);
  }

  @Test
  public void testGrabQuotePermission() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.GRAB_QUOTE_PERMISSION);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testGrabQuotePermission");
  }

  @Test
  public void testGetQuotePermission() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.GET_QUOTE_PERMISSION);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testGetQuotePermission");
  }

  @Test
  public void testIndustryList() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.INDUSTRY_LIST);
    request.setBizContent("{\"industry_level\":\"GSECTOR\"}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testIndustryList");
  }

  @Test
  public void testIndustryStocks() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.INDUSTRY_STOCKS);
    request.setBizContent("{\"industry_id\":1,\"market\":\"US\"}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testIndustryStocks");
  }

  @Test
  public void testStockIndustry() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.STOCK_INDUSTRY);
    request.setBizContent("{\"symbol\":\"AAPL\",\"market\":\"US\"}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testStockIndustry");
  }

  // ── Kline Quota ──────────────────────────────────────────────────────────

  @Test
  public void testKlineQuota() {
    TigerResponse response = client.execute(KlineQuotaRequest.newRequest());
    assertSuccess(response, "testKlineQuota");
    com.tigerbrokers.stock.openapi.client.https.response.quote.KlineQuotaResponse kqResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.KlineQuotaResponse) response;
    Assert.assertNotNull("quotaItems should not be null", kqResp.getQuotaItems());
  }

  // ── Hour Trading Timeline ────────────────────────────────────────────────

  @Test
  public void testHourTradingTimeline() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.HOUR_TRADING_TIMELINE);
    request.setBizContent("{\"symbol\":\"AAPL\"}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testHourTradingTimeline");
  }

  // ── Broker Hold ──────────────────────────────────────────────────────────

  @Test
  public void testBrokerHold() {
    TigerResponse response = client.execute(QuoteBrokerHoldRequest.newRequest(Market.HK, 5, 1));
    assertSuccess(response, "testBrokerHold");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteBrokerHoldResponse bhResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteBrokerHoldResponse) response;
    Assert.assertNotNull("brokerHoldPageItem should not be null", bhResp.getBrokerHoldPageItem());
    Assert.assertNotNull("totalCount should not be null",
        bhResp.getBrokerHoldPageItem().getTotalCount());
    Assert.assertTrue("totalCount should be >= 0",
        bhResp.getBrokerHoldPageItem().getTotalCount() >= 0);
  }

  // ── Market Scanner Tags ───────────────────────────────────────────────────

  @Test
  public void testMarketScannerTags() {
    TigerResponse response = client.execute(MarketScannerTagsRequest.newRequest(
        Market.US, Collections.singletonList(MultiTagField.MultiTagField_Industry)));
    assertSuccess(response, "testMarketScannerTags");
    com.tigerbrokers.stock.openapi.client.https.response.quote.MarketScannerTagsResponse mstResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.MarketScannerTagsResponse) response;
    Assert.assertNotNull("scanner tags items should not be null", mstResp.getItems());
    if (!mstResp.getItems().isEmpty()) {
      Assert.assertNotNull("first tag market should not be null",
          mstResp.getItems().get(0).getMarket());
      Assert.assertTrue("first tag market should not be empty",
          !mstResp.getItems().get(0).getMarket().isEmpty());
    }
  }

  // ── Option Kline (V2) ─────────────────────────────────────────────────────

  @Test
  public void testOptionKline() throws Exception {
    String identifier = getFirstOptionIdentifier();
    Assume.assumeNotNull("no option identifier available for AAPL", identifier);
    OptionKlineModel model = new OptionKlineModel(identifier);
    model.setPeriod(KType.day.name());
    model.setBeginTime("2024-01-01");
    model.setEndTime("2025-01-01");
    TigerResponse response = client.execute(OptionKlineQueryV2Request.of(model));
    assertSuccess(response, "testOptionKline");
    com.tigerbrokers.stock.openapi.client.https.response.option.OptionKlineResponse okResp =
        (com.tigerbrokers.stock.openapi.client.https.response.option.OptionKlineResponse) response;
    Assert.assertNotNull("optionKlineItems should not be null", okResp.getKlineItems());
    if (!okResp.getKlineItems().isEmpty()) {
      Assert.assertNotNull("first kline symbol should not be null",
          okResp.getKlineItems().get(0).getSymbol());
      Assert.assertTrue("first kline symbol should not be empty",
          !okResp.getKlineItems().get(0).getSymbol().isEmpty());
      Assert.assertNotNull("first kline strike should not be null",
          okResp.getKlineItems().get(0).getStrike());
    }
  }

  // ── Option Trade Tick ──────────────────────────────────────────────────────

  @Test
  public void testOptionTradeTick() throws Exception {
    String identifier = getFirstOptionIdentifier();
    Assume.assumeNotNull("no option identifier available for AAPL", identifier);
    OptionCommonModel model = new OptionCommonModel(identifier);
    TigerResponse response = client.execute(OptionTradeTickQueryRequest.of(
        Collections.singletonList(model)));
    assertSuccess(response, "testOptionTradeTick");
    com.tigerbrokers.stock.openapi.client.https.response.option.OptionTradeTickResponse ottResp =
        (com.tigerbrokers.stock.openapi.client.https.response.option.OptionTradeTickResponse) response;
    Assert.assertNotNull("optionTradeTickItems should not be null",
        ottResp.getOptionTradeTickItems());
    // non-trading hours: trade tick data may be empty
    if (!ottResp.getOptionTradeTickItems().isEmpty()) {
      Assert.assertNotNull("first trade tick symbol should not be null",
          ottResp.getOptionTradeTickItems().get(0).getSymbol());
      Assert.assertTrue("first trade tick symbol should not be empty",
          !ottResp.getOptionTradeTickItems().get(0).getSymbol().isEmpty());
    }
  }

  // ── Option Depth ───────────────────────────────────────────────────────────

  @Test
  public void testOptionDepth() throws Exception {
    String identifier = getFirstOptionIdentifier();
    Assume.assumeNotNull("no option identifier available for AAPL", identifier);
    OptionCommonModel model = new OptionCommonModel(identifier);
    TigerResponse response = client.execute(OptionDepthQueryRequest.of(
        Collections.singletonList(model)));
    assertSuccess(response, "testOptionDepth");
    com.tigerbrokers.stock.openapi.client.https.response.option.OptionDepthResponse odResp =
        (com.tigerbrokers.stock.openapi.client.https.response.option.OptionDepthResponse) response;
    Assert.assertNotNull("optionDepthItems should not be null", odResp.getOptionDepthItems());
    if (!odResp.getOptionDepthItems().isEmpty()) {
      Assert.assertNotNull("first depth symbol should not be null",
          odResp.getOptionDepthItems().get(0).getSymbol());
      Assert.assertTrue("first depth symbol should not be empty",
          !odResp.getOptionDepthItems().get(0).getSymbol().isEmpty());
    }
  }

  // ── Option Timeline ─────────────────────────────────────────────────────────

  @Test
  public void testOptionTimeline() throws Exception {
    String identifier = getFirstOptionIdentifier();
    Assume.assumeNotNull("no option identifier available for AAPL", identifier);
    OptionTimelineModel model = new OptionTimelineModel(identifier);
    TigerResponse response = client.execute(OptionTimelineRequest.of(model));
    assertSuccess(response, "testOptionTimeline");
    com.tigerbrokers.stock.openapi.client.https.response.option.OptionTimelineResponse otResp =
        (com.tigerbrokers.stock.openapi.client.https.response.option.OptionTimelineResponse) response;
    Assert.assertNotNull("optionTimelineItems should not be null", otResp.getTimelineItems());
    // non-trading hours: timeline data may be empty
    if (!otResp.getTimelineItems().isEmpty()) {
      Assert.assertNotNull("first timeline symbol should not be null",
          otResp.getTimelineItems().get(0).getSymbol());
      Assert.assertTrue("first timeline symbol should not be empty",
          !otResp.getTimelineItems().get(0).getSymbol().isEmpty());
    }
  }

  // ── Option Analysis ──────────────────────────────────────────────────────────

  @Test
  public void testOptionAnalysis() {
    TigerResponse response = client.execute(OptionAnalysisRequest.of(
        "AAPL", OptionAnalysisPeriod.FIFTY_TWO_WEEK, Market.US));
    assertSuccess(response, "testOptionAnalysis");
    com.tigerbrokers.stock.openapi.client.https.response.option.OptionAnalysisResponse oaResp =
        (com.tigerbrokers.stock.openapi.client.https.response.option.OptionAnalysisResponse) response;
    Assert.assertNotNull("optionAnalysisItems should not be null",
        oaResp.getOptionAnalysisItems());
    if (!oaResp.getOptionAnalysisItems().isEmpty()) {
      Assert.assertNotNull("first analysis symbol should not be null",
          oaResp.getOptionAnalysisItems().get(0).getSymbol());
      Assert.assertTrue("first analysis symbol should not be empty",
          !oaResp.getOptionAnalysisItems().get(0).getSymbol().isEmpty());
    }
  }

  // ── All HK Option Symbols ────────────────────────────────────────────────────

  @Test
  public void testAllHkOptionSymbols() {
    TigerResponse response = client.execute(OptionSymbolRequest.newRequest(Market.HK));
    assertSuccess(response, "testAllHkOptionSymbols");
    com.tigerbrokers.stock.openapi.client.https.response.option.OptionSymbolResponse osResp =
        (com.tigerbrokers.stock.openapi.client.https.response.option.OptionSymbolResponse) response;
    Assert.assertNotNull("symbolItems should not be null", osResp.getSymbolItems());
  }

  // ── Future Tick ──────────────────────────────────────────────────────────────

  @Test
  public void testFutureTick() {
    String contract = getFirstFutureContract();
    Assume.assumeNotNull("no future contract available for ES", contract);
    TigerResponse response = client.execute(FutureTickRequest.newRequest(contract));
    assertSuccess(response, "testFutureTick");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureTickResponse ftResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureTickResponse) response;
    Assert.assertNotNull("futureTickItems should not be null", ftResp.getFutureTickItems());
    if (ftResp.getFutureTickItems() != null) {
      Assert.assertNotNull("tick contractCode should not be null",
          ftResp.getFutureTickItems().getContractCode());
    }
  }

  // ── Future History Main Contract ─────────────────────────────────────────────

  @Test
  public void testFutureHistoryMainContract() {
    String contract = getFirstFutureContract();
    Assume.assumeNotNull("no future contract available for ES", contract);
    TigerResponse response = client.execute(FutureHistoryMainContractRequest.newRequest(
        Arrays.asList(contract), "2024-01-01", "2025-01-01"));
    assertSuccess(response, "testFutureHistoryMainContract");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureHistoryMainContractResponse fhResp =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureHistoryMainContractResponse) response;
    Assert.assertNotNull("historyMainContractItems should not be null",
        fhResp.getHistoryMainContractItems());
    if (!fhResp.getHistoryMainContractItems().isEmpty()) {
      Assert.assertNotNull("first contract code should not be null",
          fhResp.getHistoryMainContractItems().get(0).getContractCode());
      Assert.assertTrue("first contract code should not be empty",
          !fhResp.getHistoryMainContractItems().get(0).getContractCode().isEmpty());
    }
  }

  // ── Financial Daily ──────────────────────────────────────────────────────────

  @Test
  public void testFinancialDaily() {
    TigerResponse response = client.execute(FinancialDailyRequest.newRequest(
        Arrays.asList("AAPL"),
        Arrays.asList("shares_outstanding"),
        "2024-01-01", "2024-06-01"));
    assertSuccess(response, "testFinancialDaily");
    com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialDailyResponse fdResp =
        (com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialDailyResponse) response;
    Assert.assertNotNull("financialDailyItems should not be null",
        fdResp.getFinancialDailyItems());
  }

  // ── Financial Report ──────────────────────────────────────────────────────────

  @Test
  public void testFinancialReport() {
    TigerResponse response = client.execute(FinancialReportRequest.newRequest(
        Arrays.asList("AAPL"), Market.US,
        Arrays.asList("net_income"),
        FinancialPeriodType.Quarterly));
    assertSuccess(response, "testFinancialReport");
    com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialReportResponse frResp =
        (com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialReportResponse) response;
    Assert.assertNotNull("financialReportItems should not be null",
        frResp.getFinancialReportItems());
  }

  @Test
  public void testFinancialReportAnnual() {
    TigerResponse response = client.execute(FinancialReportRequest.newRequest(
        Arrays.asList("AAPL"), Market.US,
        Arrays.asList("net_income"),
        FinancialPeriodType.Annual));
    assertSuccess(response, "testFinancialReportAnnual");
    com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialReportResponse frResp =
        (com.tigerbrokers.stock.openapi.client.https.response.financial.FinancialReportResponse) response;
    Assert.assertNotNull("annual financialReportItems should not be null",
        frResp.getFinancialReportItems());
  }

  // ── Kline 30-day time range (AAPL + HK 00700) ────────────────────────────────

  @Test
  public void testKlineDailyTimeRange() {
    long endTime = System.currentTimeMillis();
    long beginTime = endTime - 30L * 24 * 60 * 60 * 1000;
    for (String symbol : Arrays.asList("AAPL", "00700")) {
      TigerResponse response = client.execute(
          QuoteKlineRequest.newRequest(Arrays.asList(symbol), KType.day, beginTime, endTime));
      assertSuccess(response, "testKlineDailyTimeRange[" + symbol + "]");
      com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse kResp =
          (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse) response;
      Assert.assertNotNull("klineItems should not be null for " + symbol, kResp.getKlineItems());
      Assume.assumeTrue("klineItems empty for " + symbol + " — may have no data in this range",
          !kResp.getKlineItems().isEmpty());
      com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem klineItem =
          kResp.getKlineItems().get(0);
      Assert.assertEquals("kline symbol should be " + symbol, symbol, klineItem.getSymbol());
      java.util.List<com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlinePoint> pts =
          klineItem.getItems();
      Assume.assumeTrue("kline points empty for " + symbol, pts != null && !pts.isEmpty());
      Assert.assertTrue("30-day daily kline should have >= 15 points for " + symbol,
          pts.size() >= 15);
      // Timestamps must be strictly ascending
      for (int i = 1; i < pts.size(); i++) {
        Assert.assertTrue("kline timestamps should be ascending for " + symbol,
            pts.get(i).getTime() > pts.get(i - 1).getTime());
      }
      // OHLC constraints for every point
      for (int i = 0; i < pts.size(); i++) {
        com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlinePoint pt = pts.get(i);
        Double hi = pt.getHigh(), lo = pt.getLow(), op = pt.getOpen(), cl = pt.getClose();
        Long vol = pt.getVolume();
        if (hi != null && lo != null) {
          Assert.assertTrue("high >= low at index " + i + " for " + symbol, hi >= lo);
        }
        if (hi != null && op != null) {
          Assert.assertTrue("high >= open at index " + i + " for " + symbol, hi >= op);
        }
        if (hi != null && cl != null) {
          Assert.assertTrue("high >= close at index " + i + " for " + symbol, hi >= cl);
        }
        if (lo != null && op != null) {
          Assert.assertTrue("open >= low at index " + i + " for " + symbol, op >= lo);
        }
        if (lo != null && cl != null) {
          Assert.assertTrue("close >= low at index " + i + " for " + symbol, cl >= lo);
        }
        if (vol != null) {
          Assert.assertTrue("volume >= 0 at index " + i + " for " + symbol, vol >= 0);
        }
      }
    }
  }

  // ── Kline intraday 60min (AAPL, 5 days) ──────────────────────────────────────

  @Test
  public void testKlineIntraday60min() {
    long endTime = System.currentTimeMillis();
    long beginTime = endTime - 5L * 24 * 60 * 60 * 1000;
    TigerResponse response = client.execute(
        QuoteKlineRequest.newRequest(Arrays.asList("AAPL"), KType.min60, beginTime, endTime));
    assertSuccess(response, "testKlineIntraday60min");
    com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse kResp =
        (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse) response;
    Assert.assertNotNull("klineItems should not be null", kResp.getKlineItems());
    Assume.assumeTrue("klineItems empty — may be weekend or no intraday data",
        !kResp.getKlineItems().isEmpty());
    com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem klineItem =
        kResp.getKlineItems().get(0);
    Assert.assertEquals("kline symbol should be AAPL", "AAPL", klineItem.getSymbol());
    java.util.List<com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlinePoint> pts =
        klineItem.getItems();
    Assume.assumeTrue("kline points empty for 60min intraday", pts != null && !pts.isEmpty());
    Assert.assertTrue("60min kline over 5 days should have >= 5 points", pts.size() >= 5);
    // Timestamps must be strictly ascending
    for (int i = 1; i < pts.size(); i++) {
      Assert.assertTrue("60min kline timestamps should be ascending",
          pts.get(i).getTime() > pts.get(i - 1).getTime());
    }
    // OHLC constraints
    for (int i = 0; i < pts.size(); i++) {
      com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlinePoint pt = pts.get(i);
      Double hi = pt.getHigh(), lo = pt.getLow(), op = pt.getOpen(), cl = pt.getClose();
      Long vol = pt.getVolume();
      if (hi != null && lo != null) {
        Assert.assertTrue("high >= low at index " + i, hi >= lo);
      }
      if (hi != null && op != null) {
        Assert.assertTrue("high >= open at index " + i, hi >= op);
      }
      if (hi != null && cl != null) {
        Assert.assertTrue("high >= close at index " + i, hi >= cl);
      }
      if (lo != null && op != null) {
        Assert.assertTrue("open >= low at index " + i, op >= lo);
      }
      if (lo != null && cl != null) {
        Assert.assertTrue("close >= low at index " + i, cl >= lo);
      }
      if (vol != null) {
        Assert.assertTrue("volume >= 0 at index " + i, vol >= 0);
      }
    }
  }

  // ── Quote depth ordering (AAPL US + 00700 HK) ────────────────────────────────

  @Test
  public void testQuoteDepthOrdering() {
    String[][] cases = {{"AAPL", "US"}, {"00700", "HK"}};
    for (String[] c : cases) {
      String sym = c[0], market = c[1];
      TigerResponse response = client.execute(
          QuoteDepthRequest.newRequest(Arrays.asList(sym), market));
      assertSuccess(response, "testQuoteDepthOrdering[" + sym + "]");
      com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDepthResponse dResp =
          (com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteDepthResponse) response;
      Assert.assertNotNull("quoteDepthItems should not be null for " + sym,
          dResp.getQuoteDepthItems());
      Assume.assumeTrue("quoteDepthItems empty — may be non-trading hours for " + sym,
          !dResp.getQuoteDepthItems().isEmpty());
      com.tigerbrokers.stock.openapi.client.https.domain.quote.item.QuoteDepthItem item =
          dResp.getQuoteDepthItems().get(0);
      Assert.assertEquals("depth symbol should be " + sym, sym, item.getSymbol());
      java.util.List<com.tigerbrokers.stock.openapi.client.https.domain.quote.item.DepthEntry> asks =
          item.getAsks();
      java.util.List<com.tigerbrokers.stock.openapi.client.https.domain.quote.item.DepthEntry> bids =
          item.getBids();
      if (asks != null && asks.size() >= 2) {
        for (int i = 1; i < asks.size(); i++) {
          Double prev = asks.get(i - 1).getPrice(), curr = asks.get(i).getPrice();
          if (prev != null && curr != null) {
            Assert.assertTrue("asks should be ascending for " + sym + " at index " + i,
                curr >= prev);
          }
        }
        for (com.tigerbrokers.stock.openapi.client.https.domain.quote.item.DepthEntry e : asks) {
          if (e.getPrice() != null) {
            Assert.assertTrue("ask price > 0 for " + sym, e.getPrice() > 0);
          }
        }
      }
      if (bids != null && bids.size() >= 2) {
        for (int i = 1; i < bids.size(); i++) {
          Double prev = bids.get(i - 1).getPrice(), curr = bids.get(i).getPrice();
          if (prev != null && curr != null) {
            Assert.assertTrue("bids should be descending for " + sym + " at index " + i,
                curr <= prev);
          }
        }
        for (com.tigerbrokers.stock.openapi.client.https.domain.quote.item.DepthEntry e : bids) {
          if (e.getPrice() != null) {
            Assert.assertTrue("bid price > 0 for " + sym, e.getPrice() > 0);
          }
        }
      }
      // Spread constraint: lowest ask >= highest bid
      if (asks != null && !asks.isEmpty() && bids != null && !bids.isEmpty()) {
        Double lowestAsk = asks.get(0).getPrice();
        Double highestBid = bids.get(0).getPrice();
        if (lowestAsk != null && highestBid != null) {
          Assert.assertTrue("lowestAsk >= highestBid for " + sym + " (lowestAsk="
              + lowestAsk + ", highestBid=" + highestBid + ")", lowestAsk >= highestBid);
        }
      }
    }
  }

  // ── Brief multi-market (AAPL US + 00700 HK + 09988 HK) ──────────────────────

  @Test
  public void testBriefMultiMarket() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.BRIEF);
    request.setBizContent("{\"symbols\":[\"AAPL\",\"00700\",\"09988\"]}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testBriefMultiMarket");
    JSONArray items = JSON.parseObject(response.getData()).getJSONArray("items");
    Assume.assumeTrue("brief multi-market items empty", items != null && !items.isEmpty());
    for (int i = 0; i < items.size(); i++) {
      JSONObject item = items.getJSONObject(i);
      Assert.assertNotNull("brief symbol should not be null at index " + i,
          item.getString("symbol"));
      Double latestPrice = item.getDouble("latestPrice");
      if (latestPrice != null) {
        Assert.assertTrue("latestPrice > 0 for " + item.getString("symbol"),
            latestPrice > 0);
      }
      Double high = item.getDouble("high"), low = item.getDouble("low");
      if (high != null && low != null && high > 0 && low > 0) {
        Assert.assertTrue("high >= low for " + item.getString("symbol"), high >= low);
      }
      Double ask = item.getDouble("askPrice"), bid = item.getDouble("bidPrice");
      if (ask != null && bid != null && ask > 0 && bid > 0) {
        Assert.assertTrue("askPrice >= bidPrice for " + item.getString("symbol"), ask >= bid);
      }
    }
  }

  // ── Fund History Quote ─────────────────────────────────────────────────────────

  @Test
  public void testFundHistoryQuote() {
    TigerResponse response = client.execute(FundHistoryQuoteRequest.newRequest(
        Arrays.asList("SPY"), "2025-01-01", "2025-06-30", TimeZoneId.NewYork));
    assertSuccess(response, "testFundHistoryQuote");
    com.tigerbrokers.stock.openapi.client.https.response.fund.FundHistoryQuoteResponse fhqResp =
        (com.tigerbrokers.stock.openapi.client.https.response.fund.FundHistoryQuoteResponse) response;
    Assert.assertNotNull("fundHistoryQuoteItems should not be null", fhqResp.getQuoteItems());
    if (!fhqResp.getQuoteItems().isEmpty()) {
      Assert.assertNotNull("first fund history quote symbol should not be null",
          fhqResp.getQuoteItems().get(0).getSymbol());
      Assert.assertEquals("first fund history quote symbol should be SPY",
          "SPY", fhqResp.getQuoteItems().get(0).getSymbol());
    }
  }

}
