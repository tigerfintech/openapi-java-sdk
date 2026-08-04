package com.tigerbrokers.stock.openapi.client.https;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteMarketRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteStockTradeRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteSymbolNameRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteSymbolRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteMarketResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockTradeResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolNameResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteSymbolResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.testsupport.TestClientFactory;
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

/**
 * 行情接口逻辑覆盖测试。
 *
 * <p>每个用例走完 TigerHttpClient.execute() 的完整链路：
 * 构造 Request → setDefaultAccount → validate → buildParams → 签名 →
 * HttpUtils.post(MOCKED) → JSON.parseObject(Response)
 *
 * <p>MockedStatic 拦截 HttpUtils.post / HttpUtils.get，返回预设的 wire JSON。
 * 这样覆盖了请求序列化和响应反序列化的全链路，但不出网。
 */
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
    httpUtilsMock.when(() -> HttpUtils.post(anyString(), anyString(), nullable(String.class), anyInt()))
        .thenReturn(wire);
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
  }

  @Test
  public void testGetKline() {
    String rawData = "{\"AAPL\":{\"period\":\"day\",\"items\":[{\"time\":1700000000000,\"open\":150.0,\"high\":155.0,\"low\":149.0,\"close\":153.0,\"volume\":1000000}]}}";
    Object data = JSON.parse(rawData);
    mockResponse(data);
    QuoteKlineRequest request = QuoteKlineRequest.newRequest(Collections.singletonList("AAPL"), KType.day);
    QuoteKlineResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertEquals(0, response.getCode());
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
