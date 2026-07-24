package com.tigerbrokers.stock.openapi.client.financial;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateSymbolChangeItem;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateSymbolChangeRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateSymbolChangeResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Integration test for SYMBOL_CHANGE corporate action type.
 *
 * Run with:
 *   -Dtest.config.path=<config dir containing tiger_openapi_config.properties>
 *   -Dtest.server.url=<server url>/gateway
 */

@Ignore("Integration test — run manually with -Dtest.config.path and -Dtest.server.url")
public class CorporateSymbolChangeIntegrationTest {

  private static TigerHttpClient client;

  @BeforeClass
  public static void setUp() throws Exception {
    String configPath = System.getProperty("test.config.path");
    Assert.assertNotNull("set -Dtest.config.path=<config dir path>", configPath);
    // configFilePath expects a directory; SDK will look for tiger_openapi_config.properties inside it

    String serverUrl = System.getProperty("test.server.url", "");

    ClientConfig config = new ClientConfig();
    config.configFilePath = configPath;
    config.setEnv(Env.TEST);

    client = TigerHttpClient.getInstance();
    if (!serverUrl.isEmpty()) {
      client.useCustomServerUrl(serverUrl);
    }
    client.clientConfig(config);
    // call again after clientConfig — initDomainRefreshTask may overwrite the url
    if (!serverUrl.isEmpty()) {
      client.useCustomServerUrl(serverUrl);
    }
  }

  @Test
  public void testSymbolChangeUS() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date begin = sdf.parse("2020-01-01");
    Date end = sdf.parse("2025-12-31");

    // Known US symbol changes:
    //   TWTR→X (2022-10), FB→META (2021-06), UVXY (reverse splits multiple times)
    //   BKNG (formerly PCLN), GOOGL/GOOG class split rename events
    CorporateSymbolChangeRequest request = CorporateSymbolChangeRequest.newRequest(
        Arrays.asList("X", "TWTR", "META", "FB", "UVXY", "PCLN", "BKNG"), Market.US, begin, end);

    CorporateSymbolChangeResponse response = client.execute(request);

    System.out.println("response code: " + response.getCode());
    System.out.println("response message: " + response.getMessage());
    System.out.println("items: " + response.getItems());

    Assert.assertNotNull(response);
    Assert.assertTrue("request failed: " + response.getMessage(), response.isSuccess());

    Map<String, List<CorporateSymbolChangeItem>> items = response.getItems();
    if (items != null && !items.isEmpty()) {
      items.forEach((symbol, list) -> {
        System.out.println("symbol=" + symbol + ", count=" + list.size());
        list.forEach(item -> {
          System.out.println("  " + item);
          Assert.assertNotNull(item.getActionType());
        });
      });
    } else {
      System.out.println("no symbol change data found for given range");
    }
  }

  @Test
  public void testSymbolChangeHK() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date begin = sdf.parse("2022-01-01");
    Date end = sdf.parse("2024-12-31");

    CorporateSymbolChangeRequest request = CorporateSymbolChangeRequest.newRequest(
        Arrays.asList("00001", "00700"), Market.HK, begin, end);

    CorporateSymbolChangeResponse response = client.execute(request);

    System.out.println("HK response code: " + response.getCode());
    System.out.println("HK items: " + response.getItems());

    Assert.assertNotNull(response);
    Assert.assertTrue("HK request failed: " + response.getMessage(), response.isSuccess());
  }
}
