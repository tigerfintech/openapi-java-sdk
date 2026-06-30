package com.tigerbrokers.stock.openapi.client.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TradeConstants;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrder;
import com.tigerbrokers.stock.openapi.client.https.request.trade.QuerySingleOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SingleOrderResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.util.ConfigFileUtil;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Integration tests for iceberg order APIs.
 *
 * Run with:
 *   -Dtest.config.path=<path to config directory>
 *   -Dtest.server.url=<gateway url>  (optional)
 */
@Ignore("Integration test — requires live config, run manually")
public class IcebergOrderIntegrationTest {

  private static String account;
  private static TigerHttpClient client;

  @BeforeClass
  public static void setUpClass() {
    String configPath = System.getProperty("test.config.path");
    Assert.assertNotNull("set -Dtest.config.path=<config dir>", configPath);

    ClientConfig config = new ClientConfig();
    config.configFilePath = configPath;
    config.setEnv(Env.TEST);
    ConfigFileUtil.loadConfigFile(config);

    account = config.defaultAccount;
    Assert.assertNotNull("account not loaded from config", account);
    Assert.assertNotNull("tigerId not loaded from config", config.tigerId);
    Assert.assertNotNull("privateKey not loaded from config", config.privateKey);

    System.out.println("account=" + account + " tigerId=" + config.tigerId);

    String serverUrl = System.getProperty("test.server.url");

    client = TigerHttpClient.getInstance();
    if (serverUrl != null && !serverUrl.isEmpty()) {
      client.useCustomServerUrl(serverUrl);
    }
    client.clientConfig(config);
  }

  /** 下冰山单（最简参数） */
  @Test
  public void testPlaceIcebergOrder_basic() {
    ContractItem contract = new ContractItem();
    contract.setSymbol("AAPL");
    contract.setCurrency("USD");
    contract.setSecType("STK");

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
  public void testPlaceIcebergOrder_full() {
    ContractItem contract = new ContractItem();
    contract.setSymbol("AAPL");
    contract.setCurrency("USD");
    contract.setSecType("STK");

    long now = System.currentTimeMillis();
    long startTime = now;
    long endTime = now + 3600_000L; // 1 hour later

    TradeOrderRequest request = TradeOrderRequest.buildIcebergOrder(
        account, contract, ActionType.BUY, 1000, 180.0,
        100, 50, 30,
        TradeConstants.ICEBERG_PRICE_TYPE_LIMIT,
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
    // start_time / end_time 目前服务端查单接口暂不回传，仅记录实际值供观察
    // 若后续服务端支持回传，可改为精确断言
    System.out.println("  (start_time from server: " + order.getStartTime()
        + ", expected: " + startTime + ")");
    System.out.println("  (end_time from server:   " + order.getEndTime()
        + ", expected: " + endTime + ")");
  }
}
