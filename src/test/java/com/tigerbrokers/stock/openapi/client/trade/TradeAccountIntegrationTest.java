package com.tigerbrokers.stock.openapi.client.trade;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.contract.*;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.*;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.*;
import com.tigerbrokers.stock.openapi.client.testsupport.IntegTestConfig;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

/** Integration tests for trade/account/asset APIs. */
public class TradeAccountIntegrationTest {

  private static TigerHttpClient client;
  private static String account;

  @BeforeClass
  public static void setUpClass() {
    Assume.assumeTrue("enable with -Dtest.integ=true", Boolean.getBoolean("test.integ"));
    client = IntegTestConfig.createClient();
    account = IntegTestConfig.getAccount();
    Assert.assertNotNull("TIGEROPEN_ACCOUNT required", account);
  }

  private void assertSuccess(TigerResponse resp, String api) {
    Assert.assertNotNull(api + " returned null", resp);
    Assert.assertTrue(api + " failed: " + resp.getMessage(), resp.isSuccess());
  }

  @Test
  public void testAccounts() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ACCOUNTS);
    request.setBizContent("{}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testAccounts");
  }

  @Test
  public void testAssets() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ASSETS);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testAssets");
  }

  @Test
  public void testPositions() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.POSITIONS);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testPositions");
  }

  @Test
  public void testActiveOrders() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ACTIVE_ORDERS);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testActiveOrders");
  }

  @Test
  public void testInactiveOrders() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.INACTIVE_ORDERS);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testInactiveOrders");
  }

  @Test
  public void testFilledOrders() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.FILLED_ORDERS);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testFilledOrders");
  }

  @Test
  public void testOrderTransactions() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDER_TRANSACTIONS);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testOrderTransactions");
  }

  @Test
  public void testPrimeAssets() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.PRIME_ASSETS);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testPrimeAssets");
  }

  @Test
  public void testSegmentFundAvailable() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.SEGMENT_FUND_AVAILABLE);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testSegmentFundAvailable");
  }

  @Test
  public void testSegmentFundHistory() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.SEGMENT_FUND_HISTORY);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testSegmentFundHistory");
  }

  @Test
  public void testTransferFund() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.TRANSFER_FUND);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testTransferFund");
  }

  @Test
  public void testAggregateAssets() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.AGGREGATE_ASSETS);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    TigerHttpResponse response = client.execute(request);
    assertSuccess(response, "testAggregateAssets");
  }

}