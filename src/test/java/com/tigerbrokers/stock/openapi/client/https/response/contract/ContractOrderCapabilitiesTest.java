package com.tigerbrokers.stock.openapi.client.https.response.contract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.OrderCapabilityItem;
import org.junit.Test;

public class ContractOrderCapabilitiesTest {

  private static final String CAPABILITIES = "["
      + "{\"orderType\":\"LMT\",\"timeInForce\":\"DAY\",\"tradingSessionType\":\"PRE_RTH_POST\"},"
      + "{\"orderType\":\"LMT\",\"timeInForce\":\"GTC\",\"tradingSessionType\":\"PRE_RTH_POST\"},"
      + "{\"orderType\":\"LMT\",\"timeInForce\":\"GTD\",\"tradingSessionType\":\"PRE_RTH_POST\"},"
      + "{\"orderType\":\"LMT\",\"timeInForce\":\"DAY\",\"tradingSessionType\":\"RTH\"},"
      + "{\"orderType\":\"LMT\",\"timeInForce\":\"GTC\",\"tradingSessionType\":\"RTH\"},"
      + "{\"orderType\":\"LMT\",\"timeInForce\":\"GTD\",\"tradingSessionType\":\"RTH\"}]";

  @Test
  public void parsesSingleContractCapabilitiesWithCamelCaseFields() {
    ContractResponse response = parseSingle("{\"symbol\":\"AAPL\",\"secType\":\"OPT\","
        + "\"orderCapabilities\":" + CAPABILITIES + "}");

    ContractItem item = response.getItem();
    assertEquals("AAPL", item.getSymbol());
    assertNotNull(item.getOrderCapabilities());
    assertEquals(6, item.getOrderCapabilities().size());
    OrderCapabilityItem capability = item.getOrderCapabilities().get(0);
    assertEquals("LMT", capability.getOrderType());
    assertEquals("DAY", capability.getTimeInForce());
    assertEquals("PRE_RTH_POST", capability.getTradingSessionType());
    assertEquals(JSON.parseArray(CAPABILITIES), serialized(item).getJSONArray("orderCapabilities"));
    assertFalse(serialized(item).containsKey("order_capabilities"));
  }

  @Test
  public void parsesCapabilitiesInBatchContractResponses() {
    ContractsResponse response = parseBatch("{\"symbol\":\"AAPL\",\"orderCapabilities\":"
        + CAPABILITIES + "},{\"symbol\":\"MSFT\"}");

    assertEquals(2, response.getItems().size());
    assertEquals(6, response.getItems().get(0).getOrderCapabilities().size());
    assertEquals("RTH", response.getItems().get(0).getOrderCapabilities().get(5).getTradingSessionType());
    assertNull(response.getItems().get(1).getOrderCapabilities());
  }

  @Test
  public void preservesExtendedTradingSession() {
    ContractItem item = parseSingle("{\"orderCapabilities\":[{\"orderType\":\"LMT\","
        + "\"timeInForce\":\"GTC\",\"tradingSessionType\":\"PRE_RTH_POST\"}]}").getItem();

    assertEquals("PRE_RTH_POST", item.getOrderCapabilities().get(0).getTradingSessionType());
    JSONObject capability = serialized(item).getJSONArray("orderCapabilities").getJSONObject(0);
    assertEquals(3, capability.size());
    assertEquals("PRE_RTH_POST", capability.getString("tradingSessionType"));
  }

  @Test
  public void absentOrNullCapabilitiesRemainNullForLegacyResponses() {
    for (String data : new String[]{"{\"symbol\":\"AAPL\"}",
        "{\"symbol\":\"AAPL\",\"orderCapabilities\":null}"}) {
      ContractItem single = parseSingle(data).getItem();
      ContractItem batch = parseBatch(data).getItems().get(0);

      assertEquals("AAPL", single.getSymbol());
      assertNull(single.getOrderCapabilities());
      assertNull(batch.getOrderCapabilities());
      assertFalse(serialized(single).containsKey("orderCapabilities"));
      assertFalse(serialized(batch).containsKey("orderCapabilities"));
    }
  }

  @Test
  public void preservesEmptyCapabilities() {
    String data = "{\"symbol\":\"AAPL\",\"orderCapabilities\":[]}";
    ContractItem single = parseSingle(data).getItem();
    ContractItem batch = parseBatch(data).getItems().get(0);

    assertNotNull(single.getOrderCapabilities());
    assertTrue(single.getOrderCapabilities().isEmpty());
    assertNotNull(batch.getOrderCapabilities());
    assertTrue(batch.getOrderCapabilities().isEmpty());
    assertEquals(JSON.parseArray("[]"), serialized(single).getJSONArray("orderCapabilities"));
    assertEquals(JSON.parseArray("[]"), serialized(batch).getJSONArray("orderCapabilities"));
  }

  private ContractResponse parseSingle(String data) {
    return JSON.parseObject("{\"code\":0,\"message\":\"success\",\"data\":" + data + "}",
        ContractResponse.class);
  }

  private ContractsResponse parseBatch(String items) {
    return JSON.parseObject("{\"code\":0,\"message\":\"success\",\"data\":{\"items\":[" + items + "]}}",
        ContractsResponse.class);
  }

  private JSONObject serialized(ContractItem item) {
    return JSON.parseObject(JSON.toJSONString(item));
  }
}
