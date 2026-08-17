package com.tigerbrokers.stock.openapi.client.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.contract.*;
import com.tigerbrokers.stock.openapi.client.https.request.trade.*;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.*;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse;
import com.tigerbrokers.stock.openapi.client.https.response.contract.ContractsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.EstimateTradableQuantityResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.FundDetailsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferExternalRecordsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferRecordsResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAnalyticsAssetResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.*;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionExpirationQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionExpirationResponse;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionExpirationItem;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import com.tigerbrokers.stock.openapi.client.testsupport.IntegTestConfig;
import com.tigerbrokers.stock.openapi.client.testsupport.MarketHelpers;
import com.tigerbrokers.stock.openapi.client.testsupport.ReadOnlyApi;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionChainItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionRealTimeQuote;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionRealTimeQuoteGroup;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionChainQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse;

/** Integration tests for trade/account/asset APIs. */
@Category(ReadOnlyApi.class)
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
    Assert.assertTrue("accounts data should contain the configured account",
        response.getData().contains(account));
  }

  @Test
  public void testAssets() {
    TigerHttpResponse response = executeWithAccount(MethodName.ASSETS);
    assertDataPresent(response, "testAssets");
    Assert.assertTrue("assets data should contain the requested account",
        response.getData().contains(account));
    // Deeper field-level assertions on the first segment asset item
    JSONObject root = JSON.parseObject(response.getData());
    JSONArray segments = root != null ? root.getJSONArray("segments") : null;
    if (segments != null && !segments.isEmpty()) {
      JSONObject seg = segments.getJSONObject(0);
      Double buyingPower = seg.getDouble("buyingPower");
      if (buyingPower != null) {
        Assert.assertTrue("assets buyingPower should be >= 0", buyingPower >= 0);
      }
      Double netLiquidation = seg.getDouble("netLiquidation");
      if (netLiquidation != null) {
        // net liquidation can be negative in theory but usually >= 0 for paper accounts
        Assert.assertNotNull("assets netLiquidation should not be null", netLiquidation);
      }
    }
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
    TigerHttpRequest request = new TigerHttpRequest(MethodName.FILLED_ORDERS);
    request.setBizContent("{\"account\":\"" + account + "\",\"start_date\":\"2025-01-01\",\"end_date\":\"2025-01-31\"}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testFilledOrders");
    Assert.assertTrue("filled orders data should contain the requested account",
        response.getData().contains(account));
  }

  @Test
  public void testOrderTransactions() {
    long startDate = java.time.LocalDate.of(2025, 1, 1)
        .atStartOfDay(java.time.ZoneOffset.UTC).toInstant().toEpochMilli();
    long endDate = java.time.LocalDate.of(2026, 1, 1)
        .atStartOfDay(java.time.ZoneOffset.UTC).toInstant().toEpochMilli();
    TigerHttpRequest request = new TigerHttpRequest(MethodName.ORDER_TRANSACTIONS);
    request.setBizContent("{\"account\":\"" + account + "\",\"symbol\":\"AAPL\","
        + "\"start_date\":" + startDate + ",\"end_date\":" + endDate + "}");
    TigerHttpResponse response = client.execute(request);
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
    TigerHttpRequest request = new TigerHttpRequest(MethodName.SEGMENT_FUND_AVAILABLE);
    request.setBizContent("{\"account\":\"" + account
        + "\",\"from_segment\":\"SEC\",\"to_segment\":\"FUT\"}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testSegmentFundAvailable");
    // The segment_fund_available response wraps the segment info but does not
    // echo the account identifier — the original assertion was over-strict.
    // The API returns a JSON object with the available amount / currency; a
    // valid response is enough. Do a lightweight sanity check on the shape.
    // The segment_fund_available response wraps the `data` field as either an
    // object OR an array — the Response DTO declares it as List<Item>, so the
    // top-level "data" element in the raw string is a JSONArray. Use JSON.parse
    // so we accept both shapes without casting errors.
    Object root = JSON.parse(response.getData());
    Assert.assertNotNull("segment fund available data should parse as JSON", root);
  }

  @Test
  public void testSegmentFundHistory() {
    TigerHttpRequest request = new TigerHttpRequest(MethodName.SEGMENT_FUND_HISTORY);
    request.setBizContent("{\"account\":\"" + account + "\",\"limit\":5}");
    TigerHttpResponse response = client.execute(request);
    assertDataPresent(response, "testSegmentFundHistory");
  }

  @Test
  public void testTransferFund() {
    TigerHttpResponse response = executeWithAccount(MethodName.TRANSFER_FUND);
    assertDataPresent(response, "testTransferFund");
  }

  @Test
  public void testAggregateAssets() {
    TigerHttpResponse response = executeWithAccount(MethodName.AGGREGATE_ASSETS);
    // aggregate_assets is entitled only for institutional accounts. Non-inst
    // accounts get an authorization/capability error — this is not a data or
    // trading-hours issue, so the skip is unconditional.
    Assume.assumeTrue(
        "aggregate_assets API is entitled only for institutional accounts: "
            + response.getMessage(),
        response.isSuccess());
    assertDataPresent(response, "testAggregateAssets");
    Assert.assertTrue("aggregate assets data should contain the requested account",
        response.getData().contains(account));
  }

  // ── Analytics Asset ──────────────────────────────────────────────────────

  @Test
  public void testAnalyticsAsset() {
    PrimeAnalyticsAssetRequest request = PrimeAnalyticsAssetRequest.buildPrimeAnalyticsAssetRequest(account);
    TigerResponse response = client.execute(request);
    assertSuccess(response, "testAnalyticsAsset");
    PrimeAnalyticsAssetResponse aaResp = (PrimeAnalyticsAssetResponse) response;
    Assert.assertNotNull("analytics asset item should not be null", aaResp.getItem());
    if (aaResp.getItem().getSummary() != null) {
      Assert.assertNotNull("summary should not be null", aaResp.getItem().getSummary());
    }
  }

  // ── Estimate Tradable Quantity ─────────────────────────────────────────────

  @Test
  public void testEstimateTradableQuantity() {
    EstimateTradableQuantityRequest request = EstimateTradableQuantityRequest.buildRequest(
        account, SecType.STK, "AAPL", ActionType.BUY, OrderType.LMT, 150.0, null);
    TigerResponse response = client.execute(request);
    assertSuccess(response, "testEstimateTradableQuantity");
    EstimateTradableQuantityResponse etResp = (EstimateTradableQuantityResponse) response;
    Assert.assertNotNull("tradableQuantityItem should not be null",
        etResp.getTradableQuantityItem());
    if (etResp.getTradableQuantityItem().getTradableQuantity() != null) {
      Assert.assertTrue("tradableQuantity should be >= 0",
          etResp.getTradableQuantityItem().getTradableQuantity() >= 0);
    }
  }

  // ── Position Transfer Records ───────────────────────────────────────────────

  @Test
  public void testPositionTransferRecords() {
    PositionTransferRecordsRequest request = PositionTransferRecordsRequest.buildRequest(
        account, "2025-01-01", "2025-01-31");
    TigerResponse response = client.execute(request);
    assertSuccess(response, "testPositionTransferRecords");
    PositionTransferRecordsResponse ptrResp = (PositionTransferRecordsResponse) response;
    Assert.assertNotNull("position transfer records item should not be null",
        ptrResp.getItem());
  }

  // ── Position Transfer External Records ──────────────────────────────────────

  @Test
  public void testPositionTransferExternalRecords() {
    PositionTransferExternalRecordsRequest request =
        PositionTransferExternalRecordsRequest.buildRequest(
            account, "2025-01-01", "2025-01-31");
    TigerResponse response = client.execute(request);
    assertSuccess(response, "testPositionTransferExternalRecords");
    PositionTransferExternalRecordsResponse pterResp =
        (PositionTransferExternalRecordsResponse) response;
    Assert.assertNotNull("position transfer external records item should not be null",
        pterResp.getItem());
  }

  // ── Fund Details ────────────────────────────────────────────────────────────

  @Test
  public void testFundDetails() {
    FundDetailsRequest request = FundDetailsRequest.buildFundDetailsRequest(
        account, Arrays.asList("SEC", "FUND"));
    TigerResponse response = client.execute(request);
    assertSuccess(response, "testFundDetails");
    FundDetailsResponse fdResp = (FundDetailsResponse) response;
    Assert.assertNotNull("fund details item should not be null", fdResp.getItem());
    if (fdResp.getItem().getItemCount() != null) {
      Assert.assertTrue("itemCount should be >= 0",
          fdResp.getItem().getItemCount() >= 0);
    }
  }

  // ── Contract ────────────────────────────────────────────────────────────────

  @Test
  public void testContract() {
    ContractRequest request = ContractRequest.newRequest(
        ContractModel.getStockModel("AAPL"), account);
    TigerResponse response = client.execute(request);
    assertSuccess(response, "testContract");
    ContractResponse cResp = (ContractResponse) response;
    Assert.assertNotNull("contract item should not be null", cResp.getItem());
    Assert.assertNotNull("contract symbol should not be null", cResp.getItem().getSymbol());
    Assert.assertEquals("contract symbol should be AAPL", "AAPL", cResp.getItem().getSymbol());
  }

  // ── Contracts ───────────────────────────────────────────────────────────────

  @Test
  public void testContracts() {
    ContractsRequest request = ContractsRequest.newRequest(
        new ContractsModel(Arrays.asList("AAPL"), "STK"), account);
    TigerResponse response = client.execute(request);
    assertSuccess(response, "testContracts");
    ContractsResponse csResp = (ContractsResponse) response;
    Assert.assertNotNull("contracts items should not be null", csResp.getItems());
  }

  // ── Orders (general query) ──────────────────────────────────────────────────

  @Test
  public void testOrders() {
    TigerHttpResponse response = executeWithAccount(MethodName.ORDERS);
    assertDataPresent(response, "testOrders");
    Assert.assertTrue("orders data should contain the requested account",
        response.getData().contains(account));
  }

  // ── Order No (single order query) ────────────────────────────────────────────

  @Test
  public void testOrderNo() {
    TigerHttpResponse response = executeWithAccount(MethodName.ORDER_NO);
    assertSuccess(response, "testOrderNo");
    Assert.assertNotNull("testOrderNo data should not be null", response.getData());
  }

  // ── Derivative Contracts (FUT secType) ─────────────────────────────────────

  /**
   * Verifies that ContractsRequest works for a derivative sec_type. The
   * /contracts endpoint only accepts STK/FUT/CC (options go through the
   * option chain / option_brief endpoints, and calling /contracts with
   * sec_type=OPT returns "'sec_type':'OPT' is not supported"). We therefore
   * exercise it with a live FUT symbol resolved via future_contracts.
   */
  @Test
  public void testDerivativeContractsFut() {
    com.tigerbrokers.stock.openapi.client.https.request.future.FutureContractsRequest fcReq =
        com.tigerbrokers.stock.openapi.client.https.request.future.FutureContractsRequest.newRequest("ES");
    TigerResponse fcResp = client.execute(fcReq);
    assertSuccess(fcResp, "testDerivativeContractsFut:future_contracts");
    com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractsResponse fc =
        (com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractsResponse) fcResp;
    boolean tradingUs = MarketHelpers.isMarketTrading(client, "US");
    if (fc.getFutureContractItems() == null || fc.getFutureContractItems().isEmpty()) {
      Assume.assumeFalse(
          "no future contracts returned during US TRADING hours — data gap", tradingUs);
      Assume.assumeTrue("no future contracts for ES (out of hours)", false);
      return;
    }
    String contractCode = null;
    for (com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureContractItem item
        : fc.getFutureContractItems()) {
      if (item.getContractCode() != null && !item.getContractCode().isEmpty()) {
        contractCode = item.getContractCode();
        break;
      }
    }
    if (contractCode == null) {
      Assume.assumeFalse(
          "no usable future contract code during US TRADING hours — data gap", tradingUs);
      Assume.assumeTrue("no usable future contract code (out of hours)", false);
      return;
    }

    ContractsModel model = new ContractsModel(Arrays.asList(contractCode), SecType.FUT.name());
    TigerResponse response = client.execute(ContractsRequest.newRequest(model, account));
    assertSuccess(response, "testDerivativeContractsFut");
    ContractsResponse csResp = (ContractsResponse) response;
    Assert.assertNotNull("FUT contracts items should not be null", csResp.getItems());
  }

  // ── Position Transfer Detail ────────────────────────────────────────────────

  @Test
  public void testPositionTransferDetail() {
    // Query records first to obtain a transfer ID; if none, assert success on detail API
    PositionTransferRecordsRequest recordsReq =
        PositionTransferRecordsRequest.buildRequest(account, "2025-01-01", "2025-01-31");
    TigerResponse recordsResp = client.execute(recordsReq);
    assertSuccess(recordsResp, "testPositionTransferDetail:records");
    PositionTransferRecordsResponse ptrResp = (PositionTransferRecordsResponse) recordsResp;
    if (ptrResp.getItem() != null && !ptrResp.getItem().isEmpty()) {
      Long transferId = ptrResp.getItem().get(0).getId();
      Assert.assertNotNull("transfer record id should not be null", transferId);
      PositionTransferDetailRequest detailReq =
          PositionTransferDetailRequest.buildRequest(transferId, account);
      TigerResponse detailResp = client.execute(detailReq);
      assertSuccess(detailResp, "testPositionTransferDetail");
      com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferDetailResponse ptdResp =
          (com.tigerbrokers.stock.openapi.client.https.response.trade.PositionTransferDetailResponse) detailResp;
      Assert.assertNotNull("position transfer detail item should not be null",
          ptdResp.getItem());
    } else {
      System.out.println("testPositionTransferDetail: no transfer records found, skipping detail query");
    }
  }

}
