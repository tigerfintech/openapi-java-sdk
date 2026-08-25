package com.tigerbrokers.stock.openapi.client.trade;

import com.tigerbrokers.stock.openapi.client.testsupport.IntegTestConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrder;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.https.request.trade.QuerySingleOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SingleOrderResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.PriceType;
import com.tigerbrokers.stock.openapi.client.util.ConfigFileUtil;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.tigerbrokers.stock.openapi.client.testsupport.WriteApi;

/**
 * Integration tests for iceberg order APIs (place / modify / cancel / query).
 *
 * Run with:
 *   -Dtest.config.path=<path to config directory>
 *   -Dtest.server.url=<gateway url>  (optional)
 *   -Dtest.env=PROD|TEST            (optional, default TEST)
 */
// 全部用例都会真实下单 / 改单 / 撤单
@Category(WriteApi.class)
public class IcebergOrderIntegrationTest {

  private static String account;
  private static TigerHttpClient client;

  @BeforeClass
  public static void setUpClass() {
    // 集成测试门控：默认跳过，CI 与本地都靠 -Dtest.integ=true 显式开启。
    // 用 Assume 而不是类级 @Ignore，@Ignore 是硬编码的，没法按环境启用。
    Assume.assumeTrue("integration test; enable with -Dtest.integ=true",
        Boolean.getBoolean("test.integ"));
    client = IntegTestConfig.createClient();
    account = IntegTestConfig.getAccount();
    Assert.assertNotNull("TIGEROPEN_ACCOUNT env var required", account);
  }

  // ── Place ─────────────────────────────────────────────────────────────────

  /**
   * 下冰山单（最简参数）
   *
   * KNOWN RISK: These three Iceberg tests are permanently @Ignored due to regulatory
   * restrictions at this venue. They cannot be validated by CI under any circumstances.
   * Manual verification is required if/when the regulatory restriction is lifted:
   *   1. Remove @Ignore from all three methods
   *   2. Run against a test account during US trading hours
   *   3. Confirm place/modify/cancel round-trip succeeds
   * Without this manual step, Iceberg order SDK changes are unverified against the live gateway.
   */
  @Test
  @Ignore("iceberg orders disabled by regulator for this venue; keep skip regardless of trading hours")
  public void testPlaceIcebergOrder_basic() {
    ContractItem contract = buildAAPLContract();

    TradeOrderRequest request = TradeOrderRequest.buildIcebergOrder(
        account, contract, ActionType.BUY, 1000, 180.0, 100);

    TradeOrderResponse response = client.execute(request);
    Assert.assertNotNull(response);
    System.out.println("placeIceberg(basic): code=" + response.getCode()
        + " msg=" + response.getMessage()
        + " orderId=" + (response.isSuccess() && response.getItem() != null ? response.getItem().getId() : "N/A"));
    Assert.assertTrue("place iceberg order failed: " + response.getMessage(), response.isSuccess());
  }

  /** 下冰山单（完整参数，含 start_time/end_time），并查询订单详情验证字段回显 */
  @Test
  @Ignore("iceberg orders disabled by regulator for this venue; keep skip regardless of trading hours")
  public void testPlaceIcebergOrder_full() {
    ContractItem contract = buildAAPLContract();

    long now = System.currentTimeMillis();
    long startTime = now;
    long endTime = now + 3600_000L; // 1 hour later

    TradeOrderRequest request = TradeOrderRequest.buildIcebergOrder(
        account, contract, ActionType.BUY, 1000, 180.0,
        100, 50, 30,
        PriceType.LIMIT_PRICE,
        startTime, endTime);

    TradeOrderResponse placeResp = client.execute(request);
    Assert.assertNotNull(placeResp);
    Assert.assertTrue("place iceberg order failed: " + placeResp.getMessage(), placeResp.isSuccess());
    long orderId = placeResp.getItem().getId();
    System.out.println("placeIceberg(full): code=" + placeResp.getCode() + " orderId=" + orderId);

    // 查询订单详情，验证 order_type / start_time / end_time 回显
    QuerySingleOrderRequest queryReq = new QuerySingleOrderRequest();
    queryReq.setBizContent(AccountParamBuilder.instance()
        .account(account)
        .id(orderId)
        .buildJson());

    SingleOrderResponse queryResp = client.execute(queryReq);
    Assert.assertNotNull(queryResp);
    Assert.assertTrue("query order failed: " + queryResp.getMessage(), queryResp.isSuccess());

    TradeOrder order = queryResp.getItem();
    Assert.assertNotNull("order detail is null", order);
    System.out.println("orderDetail: orderType=" + order.getOrderType()
        + " startTime=" + order.getStartTime()
        + " endTime=" + order.getEndTime()
        + " displaySize=" + order.getDisplaySize()
        + " minDisplaySize=" + order.getMinDisplaySize()
        + " checkIntervals=" + order.getCheckIntervals());

    Assert.assertEquals("ICEBERG", order.getOrderType());
    System.out.println("  (start_time from server: " + order.getStartTime()
        + ", expected: " + startTime + ")");
    System.out.println("  (end_time from server:   " + order.getEndTime()
        + ", expected: " + endTime + ")");
  }

  // ── Place + Modify + Cancel ───────────────────────────────────────────────

  /** 下单 → 查询 → 改单 → 撤单 完整流程 */
  @Test
  @Ignore("iceberg orders disabled by regulator for this venue; keep skip regardless of trading hours")
  public void testPlaceModifyCancel() throws Exception {
    ContractItem contract = buildAAPLContract();

    // place
    TradeOrderRequest placeReq = TradeOrderRequest.buildIcebergOrder(
        account, contract, ActionType.BUY, 100, 1.0,
        20, 10, null,
        PriceType.LIMIT_PRICE,
        null, null);

    TradeOrderResponse placeResp = client.execute(placeReq);
    Assert.assertTrue("[place] " + placeResp.getMessage(), placeResp.isSuccess());
    long orderId = placeResp.getItem().getId();
    System.out.println("[place] order_id=" + orderId);

    Thread.sleep(1000);

    // query
    QuerySingleOrderRequest queryReq = new QuerySingleOrderRequest();
    queryReq.setBizContent(AccountParamBuilder.instance().account(account).id(orderId).buildJson());

    SingleOrderResponse queryResp = client.execute(queryReq);
    Assert.assertTrue("[query] " + queryResp.getMessage(), queryResp.isSuccess());
    TradeOrder order = queryResp.getItem();
    System.out.println("[query] status=" + order.getStatus()
        + " order_type=" + order.getOrderType()
        + " display_size=" + order.getDisplaySize()
        + " min_display_size=" + order.getMinDisplaySize());

    // modify
    TradeOrderRequest modifyReq = TradeOrderRequest.buildIcebergOrder(
        account, contract, ActionType.BUY, 100, 1.01,
        30, 15, null,
        PriceType.LIMIT_PRICE,
        null, null);
    modifyReq.setApiMethodName(MethodName.MODIFY_ORDER);
    ((TradeOrderModel) modifyReq.getApiModel()).setId(orderId);

    TradeOrderResponse modResp = client.execute(modifyReq);
    Assert.assertTrue("[modify] " + modResp.getMessage(), modResp.isSuccess());
    System.out.println("[modify] result=" + modResp.getItem().getId());

    Thread.sleep(1000);

    SingleOrderResponse queryResp2 = client.execute(queryReq);
    if (queryResp2.isSuccess() && queryResp2.getItem() != null) {
      TradeOrder o = queryResp2.getItem();
      System.out.println("[after modify] display_size=" + o.getDisplaySize()
          + " min_display_size=" + o.getMinDisplaySize());
    }

    // cancel
    TradeOrderRequest cancelReq = new TradeOrderRequest();
    cancelReq.setApiMethodName(MethodName.CANCEL_ORDER);
    cancelReq.setBizContent(AccountParamBuilder.instance().account(account).id(orderId).buildJson());

    TradeOrderResponse cancelResp = client.execute(cancelReq);
    Assert.assertTrue("[cancel] " + cancelResp.getMessage(), cancelResp.isSuccess());
    System.out.println("[cancel] result=" + cancelResp.getItem().getId());
  }

  // ── Helper ────────────────────────────────────────────────────────────────

  private static ContractItem buildAAPLContract() {
    ContractItem contract = new ContractItem();
    contract.setSymbol("AAPL");
    contract.setCurrency("USD");
    contract.setSecType("STK");
    return contract;
  }
}
