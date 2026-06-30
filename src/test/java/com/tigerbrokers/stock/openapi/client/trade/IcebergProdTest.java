package com.tigerbrokers.stock.openapi.client.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TradeConstants;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.TradeOrder;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.https.request.trade.QuerySingleOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SingleOrderResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.util.ConfigFileUtil;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * PROD iceberg place / modify / cancel integration test.
 *
 * Run with:
 *   mvn test -pl openapi-java-sdk -Dtest=IcebergProdTest -Dtest.config.path=<path to config directory>
 */
@Ignore("Integration test — requires live PROD config, run manually")
public class IcebergProdTest {

  private static String account;
  private static TigerHttpClient client;

  @BeforeClass
  public static void setUpClass() {
    String configPath = System.getProperty("test.config.path");
    Assert.assertNotNull("set -Dtest.config.path=<config dir>", configPath);

    ClientConfig config = new ClientConfig();
    config.configFilePath = configPath;
    config.setEnv(Env.PROD);
    ConfigFileUtil.loadConfigFile(config);

    account = config.defaultAccount;
    Assert.assertNotNull("account not loaded", account);
    System.out.println("account=" + account + " tigerId=" + config.tigerId);

    client = TigerHttpClient.getInstance();
    client.clientConfig(config);
  }

  @Test
  public void testPlaceModifyCancel() throws Exception {
    ContractItem contract = new ContractItem();
    contract.setSymbol("AAPL");
    contract.setCurrency("USD");
    contract.setSecType("STK");

    // place
    TradeOrderRequest placeReq = TradeOrderRequest.buildIcebergOrder(
        account, contract, ActionType.BUY, 100, 1.0,
        20, 10, null,
        TradeConstants.ICEBERG_PRICE_TYPE_LIMIT,
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

    // modify: reuse buildIcebergOrder, change method name
    TradeOrderRequest modifyReq = TradeOrderRequest.buildIcebergOrder(
        account, contract, ActionType.BUY, 100, 1.01,
        30, 15, null,
        TradeConstants.ICEBERG_PRICE_TYPE_LIMIT,
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
}
