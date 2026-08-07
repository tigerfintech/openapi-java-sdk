package com.tigerbrokers.stock.openapi.client.financial;

import com.tigerbrokers.stock.openapi.client.testsupport.IntegTestConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateSymbolChangeItem;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateSymbolChangeRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateSymbolChangeResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.CorporateActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.tigerbrokers.stock.openapi.client.testsupport.ReadOnlyApi;

/**
 * Integration test for SYMBOL_CHANGE corporate action type.
 *
 * Run with:
 *   -Dtest.config.path=<config dir containing tiger_openapi_config.properties>
 *   -Dtest.server.url=<server url>/gateway
 */

@Category(ReadOnlyApi.class)
public class CorporateSymbolChangeIntegrationTest {

  private static TigerHttpClient client;

  @BeforeClass
  public static void setUp() throws Exception {
    // 集成测试门控：默认跳过，CI 与本地都靠 -Dtest.integ=true 显式开启。
    // 用 Assume 而不是类级 @Ignore，@Ignore 是硬编码的，没法按环境启用。
    Assume.assumeTrue("integration test; enable with -Dtest.integ=true",
        Boolean.getBoolean("test.integ"));
    client = IntegTestConfig.createClient();
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
    Assert.assertNotNull("items should not be null", items);
    if (items != null && !items.isEmpty()) {
      items.forEach((symbol, list) -> {
        Assert.assertNotNull("list for " + symbol + " should not be null", list);
        Assert.assertFalse("list for " + symbol + " should not be empty", list.isEmpty());
        list.forEach(item -> {
          Assert.assertNotNull("actionType should not be null for " + symbol, item.getActionType());
          Assert.assertEquals("actionType should be SYMBOL_CHANGE for " + symbol,
              CorporateActionType.SYMBOL_CHANGE, item.getActionType());
          Assert.assertNotNull("symbol should not be null", item.getSymbol());
          Assert.assertTrue("symbol should not be empty for " + symbol,
              !item.getSymbol().isEmpty());
          Assert.assertNotNull("market should not be null", item.getMarket());
          Assert.assertEquals("market should be US for " + symbol, "US", item.getMarket());
          Assert.assertNotNull("executeDate should not be null", item.getExecuteDate());
          Assert.assertNotNull("oldSymbol should not be null", item.getOldSymbol());
          Assert.assertFalse("oldSymbol should not be empty", item.getOldSymbol().isEmpty());
          Assert.assertNotNull("newSymbol should not be null", item.getNewSymbol());
          Assert.assertFalse("newSymbol should not be empty", item.getNewSymbol().isEmpty());
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

    Map<String, List<CorporateSymbolChangeItem>> hkItems = response.getItems();
    Assert.assertNotNull("HK items should not be null", hkItems);
    if (hkItems != null && !hkItems.isEmpty()) {
      hkItems.forEach((symbol, list) -> {
        Assert.assertNotNull("HK list for " + symbol + " should not be null", list);
        Assert.assertFalse("HK list for " + symbol + " should not be empty", list.isEmpty());
        list.forEach(item -> {
          Assert.assertNotNull("HK actionType should not be null for " + symbol, item.getActionType());
          Assert.assertEquals("HK actionType should be SYMBOL_CHANGE for " + symbol,
              CorporateActionType.SYMBOL_CHANGE, item.getActionType());
          Assert.assertNotNull("HK symbol should not be null", item.getSymbol());
          Assert.assertTrue("HK symbol should not be empty for " + symbol,
              !item.getSymbol().isEmpty());
          Assert.assertNotNull("HK market should not be null", item.getMarket());
          Assert.assertEquals("HK market should be HK for " + symbol, "HK", item.getMarket());
          Assert.assertNotNull("HK executeDate should not be null", item.getExecuteDate());
          Assert.assertNotNull("HK oldSymbol should not be null", item.getOldSymbol());
          Assert.assertFalse("HK oldSymbol should not be empty", item.getOldSymbol().isEmpty());
          Assert.assertNotNull("HK newSymbol should not be null", item.getNewSymbol());
          Assert.assertFalse("HK newSymbol should not be empty", item.getNewSymbol().isEmpty());
        });
      });
    }
  }
}
