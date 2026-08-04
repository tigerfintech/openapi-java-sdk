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

  private TigerHttpResponse executeWithAccount(MethodName method) {
    TigerHttpRequest request = new TigerHttpRequest(method);
    request.setBizContent("{\"account\":\"" + account + "\"}");
    return client.execute(request);
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
    TigerHttpResponse response = executeWithAccount(MethodName.ASSETS);
    assertSuccess(response, "testAssets");
  }

  @Test
  public void testPositions() {
    TigerHttpResponse response = executeWithAccount(MethodName.POSITIONS);
    assertSuccess(response, "testPositions");
  }

  @Test
  public void testActiveOrders() {
    TigerHttpResponse response = executeWithAccount(MethodName.ACTIVE_ORDERS);
    assertSuccess(response, "testActiveOrders");
  }

  @Test
  public void testInactiveOrders() {
    TigerHttpResponse response = executeWithAccount(MethodName.INACTIVE_ORDERS);
    assertSuccess(response, "testInactiveOrders");
  }

  @Test
  public void testFilledOrders() {
    TigerHttpResponse response = executeWithAccount(MethodName.FILLED_ORDERS);
    assertSuccess(response, "testFilledOrders");
  }

  @Test
  public void testOrderTransactions() {
    TigerHttpResponse response = executeWithAccount(MethodName.ORDER_TRANSACTIONS);
    assertSuccess(response, "testOrderTransactions");
  }

  @Test
  public void testPrimeAssets() {
    TigerHttpResponse response = executeWithAccount(MethodName.PRIME_ASSETS);
    assertSuccess(response, "testPrimeAssets");
  }

  @Test
  public void testSegmentFundAvailable() {
    TigerHttpResponse response = executeWithAccount(MethodName.SEGMENT_FUND_AVAILABLE);
    assertSuccess(response, "testSegmentFundAvailable");
  }

  @Test
  public void testSegmentFundHistory() {
    TigerHttpResponse response = executeWithAccount(MethodName.SEGMENT_FUND_HISTORY);
    assertSuccess(response, "testSegmentFundHistory");
  }

  @Test
  public void testTransferFund() {
    TigerHttpResponse response = executeWithAccount(MethodName.TRANSFER_FUND);
    assertSuccess(response, "testTransferFund");
  }

  @Test
  public void testAggregateAssets() {
    TigerHttpResponse response = executeWithAccount(MethodName.AGGREGATE_ASSETS);
    assertSuccess(response, "testAggregateAssets");
  }

}