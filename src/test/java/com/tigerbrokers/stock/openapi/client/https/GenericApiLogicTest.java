package com.tigerbrokers.stock.openapi.client.https;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.testsupport.TestClientFactory;
import com.tigerbrokers.stock.openapi.client.util.HttpResult;
import com.tigerbrokers.stock.openapi.client.util.HttpUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;

/**
 * Generic API logic coverage: TigerHttpRequest(MethodName) path.
 * These methods have no typed Request class.
 */
public class GenericApiLogicTest {

  private static MockedStatic<HttpUtils> httpUtilsMock;
  private static TigerHttpClient client;

  @BeforeClass
  public static void setUpClass() {
    httpUtilsMock = Mockito.mockStatic(HttpUtils.class);
    httpUtilsMock.when(() -> HttpUtils.get(anyString(), nullable(String.class))).thenReturn("{}");
    httpUtilsMock.when(() -> HttpUtils.postForResult(anyString(), anyString(), nullable(String.class), anyInt()))
        .thenReturn(new HttpResult(200, "{\"code\":0,\"message\":\"success\",\"timestamp\":1700000000,\"data\":\"{}\"}"));
    client = TestClientFactory.createOfflineClient();
  }

  @AfterClass
  public static void tearDownClass() { if (httpUtilsMock != null) httpUtilsMock.close(); }

  @Test
  public void testAccounts() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ACCOUNTS);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("ACCOUNTS", response);
    Assert.assertTrue("ACCOUNTS code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testActiveOrders() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ACTIVE_ORDERS);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("ACTIVE_ORDERS", response);
    Assert.assertTrue("ACTIVE_ORDERS code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testAssets() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ASSETS);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("ASSETS", response);
    Assert.assertTrue("ASSETS code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testBatchPlaceOrder() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.BATCH_PLACE_ORDER);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("BATCH_PLACE_ORDER", response);
    Assert.assertTrue("BATCH_PLACE_ORDER code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testBrief() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.BRIEF);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("BRIEF", response);
    Assert.assertTrue("BRIEF code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testCancelOrder() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.CANCEL_ORDER);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("CANCEL_ORDER", response);
    Assert.assertTrue("CANCEL_ORDER code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testFilledOrders() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.FILLED_ORDERS);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("FILLED_ORDERS", response);
    Assert.assertTrue("FILLED_ORDERS code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testGetQuotePermission() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.GET_QUOTE_PERMISSION);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("GET_QUOTE_PERMISSION", response);
    Assert.assertTrue("GET_QUOTE_PERMISSION code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testGrabQuotePermission() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.GRAB_QUOTE_PERMISSION);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("GRAB_QUOTE_PERMISSION", response);
    Assert.assertTrue("GRAB_QUOTE_PERMISSION code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testHourTradingTimeline() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.HOUR_TRADING_TIMELINE);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("HOUR_TRADING_TIMELINE", response);
    Assert.assertTrue("HOUR_TRADING_TIMELINE code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testInactiveOrders() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.INACTIVE_ORDERS);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("INACTIVE_ORDERS", response);
    Assert.assertTrue("INACTIVE_ORDERS code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testIndustryList() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.INDUSTRY_LIST);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("INDUSTRY_LIST", response);
    Assert.assertTrue("INDUSTRY_LIST code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testIndustryStocks() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.INDUSTRY_STOCKS);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("INDUSTRY_STOCKS", response);
    Assert.assertTrue("INDUSTRY_STOCKS code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testModifyOrder() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.MODIFY_ORDER);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("MODIFY_ORDER", response);
    Assert.assertTrue("MODIFY_ORDER code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testOrderNo() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDER_NO);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("ORDER_NO", response);
    Assert.assertTrue("ORDER_NO code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testOrderTransactions() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDER_TRANSACTIONS);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("ORDER_TRANSACTIONS", response);
    Assert.assertTrue("ORDER_TRANSACTIONS code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testStockDetail() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.STOCK_DETAIL);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("STOCK_DETAIL", response);
    Assert.assertTrue("STOCK_DETAIL code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testStockIndustry() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.STOCK_INDUSTRY);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("STOCK_INDUSTRY", response);
    Assert.assertTrue("STOCK_INDUSTRY code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testWarrantFilter() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.WARRANT_FILTER);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("WARRANT_FILTER", response);
    Assert.assertTrue("WARRANT_FILTER code=" + response.getCode(), response.getCode() >= 0);
  }

  @Test
  public void testWarrantRealTimeQuote() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.WARRANT_REAL_TIME_QUOTE);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    Assert.assertNotNull("WARRANT_REAL_TIME_QUOTE", response);
    Assert.assertTrue("WARRANT_REAL_TIME_QUOTE code=" + response.getCode(), response.getCode() >= 0);
  }

}