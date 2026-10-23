package com.tigerbrokers.stock.openapi.client.https;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.NewsItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.NewsPageItem;
import com.tigerbrokers.stock.openapi.client.https.request.news.NewsHighlightListRequest;
import com.tigerbrokers.stock.openapi.client.https.request.news.NewsStockListRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteMarketRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteStockTradeRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteSymbolNameRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteSymbolRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.NewsHighlightListResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.NewsStockListResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteMarketResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockTradeResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolNameResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.testsupport.TestClientFactory;
import com.tigerbrokers.stock.openapi.client.util.HttpResult;
import com.tigerbrokers.stock.openapi.client.util.HttpUtils;
import java.util.Arrays;
import java.util.Collections;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;

/** 行情接口逻辑覆盖测试。 */
public class QuoteApiLogicTest {

  private static MockedStatic<HttpUtils> httpUtilsMock;
  private static TigerHttpClient client;

  @BeforeClass
  public static void setUpClass() {
    httpUtilsMock = Mockito.mockStatic(HttpUtils.class);
    // HttpUtils.get 用于域名花园，返回空让它走 fallback
    httpUtilsMock.when(() -> HttpUtils.get(anyString(), nullable(String.class)))
        .thenReturn("{}");
    client = TestClientFactory.createOfflineClient();
  }

  @AfterClass
  public static void tearDownClass() {
    if (httpUtilsMock != null) {
      httpUtilsMock.close();
    }
  }

  private void mockResponse(Object data) {
    String wire = JSON.toJSONString(new WireResponse(0, "success", data));
    httpUtilsMock.when(() -> HttpUtils.postForResult(anyString(), anyString(), nullable(String.class), anyInt()))
        .thenReturn(new HttpResult(200, wire));
  }

  // ---------- 行情接口逐个覆盖 ----------

  @Test
  public void testGetMarketState() {
    mockResponse(Arrays.asList(
        new MarketItem("US", "Trading", "TRADING", "09:30:00 EDT", "09:30:00 EDT")
    ));
    QuoteMarketRequest request = QuoteMarketRequest.newRequest(Market.US);
    QuoteMarketResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertEquals(0, response.getCode());
    Assert.assertNotNull(response.getMarketItems());
    Assert.assertFalse(response.getMarketItems().isEmpty());
    Assert.assertEquals("US", response.getMarketItems().get(0).getMarket());
  }

  @Test
  public void testGetSymbols() {
    mockResponse(Arrays.asList("AAPL", "TSLA", "NVDA"));
    QuoteSymbolRequest request = QuoteSymbolRequest.newRequest(Market.US);
    QuoteSymbolResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertEquals(0, response.getCode());
    Assert.assertNotNull(response.getSymbols());
    Assert.assertTrue(response.getSymbols().contains("AAPL"));
  }

  @Test
  public void testGetSymbolNames() {
    mockResponse(Arrays.asList(
        new SymbolNameEntry("AAPL", "Apple Inc"),
        new SymbolNameEntry("TSLA", "Tesla Inc")
    ));
    QuoteSymbolNameRequest request = QuoteSymbolNameRequest.newRequest(Market.US);
    QuoteSymbolNameResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertEquals(0, response.getCode());
    Assert.assertNotNull(response.getSymbolNameItems());
  }

  @Test
  public void testGetStockTrade() {
    String rawData = "[{\"symbol\":\"AAPL\",\"items\":[{\"volume\":1000,\"time\":\"09:30\",\"price\":150.5}]}]";
    Object data = JSON.parse(rawData);
    mockResponse(data);
    QuoteStockTradeRequest request = QuoteStockTradeRequest.newRequest(Collections.singletonList("AAPL"));
    QuoteStockTradeResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertEquals(0, response.getCode());
    Assert.assertNotNull("stockTradeItems should not be null", response.getStockTradeItems());
    Assert.assertFalse("stockTradeItems should not be empty", response.getStockTradeItems().isEmpty());
    Assert.assertEquals("AAPL", response.getStockTradeItems().get(0).getSymbol());
  }

  @Test
  public void testGetKline() {
    String rawData = "[{\"symbol\":\"AAPL\",\"period\":\"day\",\"items\":[{\"time\":1700000000000,\"open\":150.0,\"high\":155.0,\"low\":149.0,\"close\":153.0,\"volume\":1000000}]}]";
    Object data = JSON.parse(rawData);
    mockResponse(data);
    QuoteKlineRequest request = QuoteKlineRequest.newRequest(Collections.singletonList("AAPL"), KType.day);
    QuoteKlineResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertEquals(0, response.getCode());
    Assert.assertNotNull("klineItems should not be null", response.getKlineItems());
    Assert.assertFalse("klineItems should not be empty", response.getKlineItems().isEmpty());
    Assert.assertEquals("AAPL", response.getKlineItems().get(0).getSymbol());
    Assert.assertFalse("AAPL kline points should not be empty",
        response.getKlineItems().get(0).getItems().isEmpty());
    Assert.assertEquals(150.0, response.getKlineItems().get(0).getItems().get(0).getOpen(), 0.001);
  }

  /**
   * news 系列接口（news_stock_list / news_highlight_list），契约见 stock-openapi
   * docs/news_api_design.md §3.1-§3.3，用真实 wire 形状（camelCase 出参）验证反序列化。
   */
  @Test
  public void testGetNewsStockList() {
    String rawData = "{\"items\":[{\"id\":\"123456\",\"title\":\"Apple Q2\",\"summary\":\"Earnings beat\","
        + "\"pubTimestamp\":1729413600,"
        + "\"url\":\"https://example.com/news/123456\"}],"
        + "\"pageCount\":1,\"pageSize\":20,\"totalPage\":10,\"totalSize\":200}";
    mockResponse(JSON.parse(rawData));
    NewsStockListRequest request =
        NewsStockListRequest.newRequest(Arrays.asList("AAPL", "TSLA"), Language.zh_CN, 1, 20);
    NewsStockListResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertEquals(0, response.getCode());
    NewsPageItem page = response.getNewsPageItem();
    Assert.assertNotNull("newsPageItem should not be null", page);
    Assert.assertEquals(Integer.valueOf(1), page.getPageCount());
    Assert.assertEquals(Integer.valueOf(20), page.getPageSize());
    Assert.assertEquals(Integer.valueOf(10), page.getTotalPage());
    Assert.assertEquals(Integer.valueOf(200), page.getTotalSize());
    Assert.assertNotNull("items should not be null", page.getItems());
    Assert.assertEquals(1, page.getItems().size());
    NewsItem item = page.getItems().get(0);
    Assert.assertEquals("123456", item.getId());
    Assert.assertEquals("Apple Q2", item.getTitle());
    Assert.assertEquals("Earnings beat", item.getSummary());
    Assert.assertEquals(Long.valueOf(1729413600), item.getPubTimestamp());
    Assert.assertEquals("https://example.com/news/123456", item.getUrl());
  }

  @Test
  public void testGetNewsHighlightList() {
    String rawData = "{\"items\":[{\"id\":\"789\",\"title\":\"Fed cuts rates\",\"summary\":\"FOMC decision\","
        + "\"pubTimestamp\":1761429600,"
        + "\"url\":\"https://example.com/highlight/detail?id=789\"}],"
        + "\"pageCount\":2,\"pageSize\":20,\"totalPage\":5,\"totalSize\":100}";
    mockResponse(JSON.parse(rawData));
    NewsHighlightListRequest request = NewsHighlightListRequest.newRequest(Language.en_US, "789");
    NewsHighlightListResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertEquals(0, response.getCode());
    NewsPageItem page = response.getNewsPageItem();
    Assert.assertNotNull("newsPageItem should not be null", page);
    Assert.assertEquals(Integer.valueOf(2), page.getPageCount());
    Assert.assertEquals(Integer.valueOf(5), page.getTotalPage());
    Assert.assertEquals(Integer.valueOf(100), page.getTotalSize());
    Assert.assertNotNull("items should not be null", page.getItems());
    Assert.assertEquals(1, page.getItems().size());
    NewsItem item = page.getItems().get(0);
    Assert.assertEquals("789", item.getId());
    Assert.assertEquals("Fed cuts rates", item.getTitle());
    Assert.assertEquals(Long.valueOf(1761429600), item.getPubTimestamp());
  }

  // ---------- 辅助类 ----------

  /** wire 信封格式，与服务端一致 */
  static class WireResponse {
    public int code;
    public String message;
    public Object data;
    public long timestamp = 1700000000;

    WireResponse(int code, String message, Object data) {
      this.code = code;
      this.message = message;
      this.data = data;
    }
  }

  /** 简化的 MarketItem，只含必要字段用于 mock */
  static class MarketItem {
    public String market;
    public String marketStatus;
    public String status;
    public String openTime;
    public String closeTime;

    MarketItem(String market, String marketStatus, String status, String openTime, String closeTime) {
      this.market = market;
      this.marketStatus = marketStatus;
      this.status = status;
      this.openTime = openTime;
      this.closeTime = closeTime;
    }
  }

  static class SymbolNameEntry {
    public String symbol;
    public String name;

    SymbolNameEntry(String symbol, String name) {
      this.symbol = symbol;
      this.name = name;
    }
  }
}
