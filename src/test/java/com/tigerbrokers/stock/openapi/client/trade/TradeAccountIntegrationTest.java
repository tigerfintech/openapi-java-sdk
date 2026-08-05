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

  private void assertDataPresent(TigerHttpResponse resp, String api) {
    assertSuccess(resp, api);
    Assert.assertNotNull(api + " data should not be null", resp.getData());
    Assert.assertFalse(api + " data should not be empty",
        resp.getData() == null || resp.getData().trim().isEmpty());
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
    assertDataPresent(response, "testAccounts");
    Assert.assertTrue("accounts data should contain account field",
        response.getData().contains("account"));
  }

  @Test
  public void testAssets() {
    TigerHttpResponse response = executeWithAccount(MethodName.ASSETS);
    assertDataPresent(response, "testAssets");
    Assert.assertTrue("assets data should contain the requested account",
        response.getData().contains(account));
  }

  @Test
  public void testPositions() {
    TigerHttpResponse response = executeWithAccount(MethodName.POSITIONS);
    assertDataPresent(response, "testPositions");
    Assert.assertTrue("positions data should contain the requested account",
        response.getData().contains(account));
  }

  @Test
  public void testActiveOrders() {
    TigerHttpResponse response = executeWithAccount(MethodName.ACTIVE_ORDERS);
    assertDataPresent(response, "testActiveOrders");
    Assert.assertTrue("active orders data should contain the requested account",
        response.getData().contains(account));
  }

  @Test
  public void testInactiveOrders() {
    TigerHttpResponse response = executeWithAccount(MethodName.INACTIVE_ORDERS);
    assertDataPresent(response, "testInactiveOrders");
    Assert.assertTrue("inactive orders data should contain the requested account",
        response.getData().contains(account));
  }

  @Test
  public void testFilledOrders() {
    TigerHttpResponse response = executeWithAccount(MethodName.FILLED_ORDERS);
    assertDataPresent(response, "testFilledOrders");
    Assert.assertTrue("filled orders data should contain the requested account",
        response.getData().contains(account));
  }

  @Test
  public void testOrderTransactions() {
    TigerHttpResponse response = executeWithAccount(MethodName.ORDER_TRANSACTIONS);
    assertDataPresent(response, "testOrderTransactions");
    Assert.assertTrue("order transactions data should contain the requested account",
        response.getData().contains(account));
  }

  @Test
  public void testPrimeAssets() {
    TigerHttpResponse response = executeWithAccount(MethodName.PRIME_ASSETS);
    assertDataPresent(response, "testPrimeAssets");
    Assert.assertTrue("prime assets data should contain the requested account",
        response.getData().contains(account));
  }

  @Test
  public void testSegmentFundAvailable() {
    TigerHttpResponse response = executeWithAccount(MethodName.SEGMENT_FUND_AVAILABLE);
    assertDataPresent(response, "testSegmentFundAvailable");
    Assert.assertTrue("segment fund available data should contain the requested account",
        response.getData().contains(account));
  }

  @Test
  public void testSegmentFundHistory() {
    TigerHttpResponse response = executeWithAccount(MethodName.SEGMENT_FUND_HISTORY);
    assertDataPresent(response, "testSegmentFundHistory");
    Assert.assertTrue("segment fund history data should contain the requested account",
        response.getData().contains(account));
  }

  @Test
  public void testTransferFund() {
    TigerHttpResponse response = executeWithAccount(MethodName.TRANSFER_FUND);
    assertSuccess(response, "testTransferFund");
    Assert.assertNotNull("testTransferFund data should not be null", response.getData());
    Assert.assertFalse("testTransferFund data should not be empty",
        response.getData().trim().isEmpty());
  }

  @Test
  public void testAggregateAssets() {
    TigerHttpResponse response = executeWithAccount(MethodName.AGGREGATE_ASSETS);
    assertDataPresent(response, "testAggregateAssets");
    Assert.assertTrue("aggregate assets data should contain the requested account",
        response.getData().contains(account));
  }

}
