package com.tigerbrokers.stock.openapi.client.https;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsItem;
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsPageItem;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.request.news.NewsHighlightListRequest;
import com.tigerbrokers.stock.openapi.client.https.request.news.NewsStockListRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteMarketRequest;
import com.tigerbrokers.stock.openapi.client.https.response.news.NewsHighlightListResponse;
import com.tigerbrokers.stock.openapi.client.https.response.news.NewsStockListResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.testsupport.TestClientFactory;
import com.tigerbrokers.stock.openapi.client.testsupport.WireResponse;
import com.tigerbrokers.stock.openapi.client.util.HttpResult;
import com.tigerbrokers.stock.openapi.client.util.HttpUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;

/** 资讯接口逻辑覆盖测试（news_stock_list / news_highlight_list），与行情逻辑测试相互独立。 */
public class NewsApiLogicTest {

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

  // ---------- 资讯接口逐个覆盖 ----------

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
        NewsStockListRequest.newRequest("AAPL", Language.zh_CN, 1, 20);
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

  /**
   * news（MethodType.NEWS）必须路由到主网关（serverUrl），不得走行情域名（quoteServerUrl）——
   * 域名花园对 TBNZ/TBSG 配置了 <license>-QUOTE 分流（/hkg-quote 路径），标错 MethodType
   * 会把资讯流量打进行情集群。对照断言：QUOTE 类型仍走行情域名，防止路由逻辑被误改。
   */
  @Test
  public void testNewsRoutesToMainGatewayNotQuote() throws Exception {
    TigerHttpClient newsClient = TestClientFactory.createOfflineClient();
    java.lang.reflect.Field quoteUrlField = TigerHttpClient.class.getDeclaredField("quoteServerUrl");
    quoteUrlField.setAccessible(true);
    quoteUrlField.set(newsClient, "http://localhost:0/hkg-quote/gateway");

    java.lang.reflect.Method getServerUrl =
        TigerHttpClient.class.getDeclaredMethod("getServerUrl", TigerRequest.class);
    getServerUrl.setAccessible(true);

    Assert.assertEquals("news 请求必须走主网关",
        "http://localhost:0/gateway",
        getServerUrl.invoke(newsClient, NewsStockListRequest.newRequest(
            "AAPL", Language.zh_CN, 1, 20)));
    Assert.assertEquals("news 请求必须走主网关",
        "http://localhost:0/gateway",
        getServerUrl.invoke(newsClient, NewsHighlightListRequest.newRequest(Language.en_US, null)));
    Assert.assertEquals("QUOTE 类型应走行情域名（对照组）",
        "http://localhost:0/hkg-quote/gateway",
        getServerUrl.invoke(newsClient, QuoteMarketRequest.newRequest(Market.US)));
  }
}
