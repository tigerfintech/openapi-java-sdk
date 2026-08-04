package com.tigerbrokers.stock.openapi.client.financial;

import com.tigerbrokers.stock.openapi.client.testsupport.IntegTestConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateSymbolChangeItem;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateSymbolChangeRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateSymbolChangeResponse;
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
