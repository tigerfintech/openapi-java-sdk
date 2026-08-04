package com.tigerbrokers.stock.openapi.client.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateDelistingItem;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateIpoItem;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateSymbolChangeItem;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateDelistingRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateIpoRequest;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateSymbolChangeRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateDelistingResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateIpoResponse;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateSymbolChangeResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.util.ConfigFileUtil;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.tigerbrokers.stock.openapi.client.testsupport.ReadOnlyApi;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Integration tests for corporate action new types: SYMBOL_CHANGE, DELISTING, IPO.
 *
 * Run with:
 *   -Dtest.config.path=<path to config directory>
 *   -Dtest.env=PROD|TEST                           (optional, default PROD)
 *
 * Or via environment variable:
 *   TIGER_CONFIG_PATH=<path to config directory> mvn test -Dtest=CorporateActionIntegrationTest
 */

@Category(ReadOnlyApi.class)
public class CorporateActionIntegrationTest {

  private static TigerHttpClient client;
  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

  @BeforeClass
  public static void setUpClass() {
    // 集成测试门控：默认跳过，CI 与本地都靠 -Dtest.integ=true 显式开启。
    // 用 Assume 而不是类级 @Ignore，@Ignore 是硬编码的，没法按环境启用。
    Assume.assumeTrue("integration test; enable with -Dtest.integ=true",
        Boolean.getBoolean("test.integ"));
    // Support both -Dtest.config.path JVM property and TIGER_CONFIG_PATH env var
    String configPath = System.getProperty("test.config.path");
    if (configPath == null || configPath.isEmpty()) {
      configPath = System.getenv("TIGER_CONFIG_PATH");
    }
    Assert.assertNotNull(
        "set -Dtest.config.path=<config dir> or TIGER_CONFIG_PATH env var",
        configPath);

    String envStr = System.getProperty("test.env",
        System.getenv().getOrDefault("TIGER_ENV", "PROD"));
    Env env = "TEST".equalsIgnoreCase(envStr) ? Env.TEST : Env.PROD;

    ClientConfig config = new ClientConfig();
    config.configFilePath = configPath;
    config.setEnv(env);
    ConfigFileUtil.loadConfigFile(config);

    Assert.assertNotNull("tigerId not loaded from config", config.tigerId);
    Assert.assertNotNull("privateKey not loaded from config", config.privateKey);

    client = TigerHttpClient.getInstance();
    client.clientConfig(config);
    System.out.println("env=" + env + " tigerId=" + config.tigerId);
  }

  @Test
  public void testGetCorporateSymbolChange() {
    CorporateSymbolChangeRequest request = CorporateSymbolChangeRequest.newRequest(
        Arrays.asList("META"),
        Market.US,
        parse("2022-01-01"),
        parse("2023-01-01")
    );
    CorporateSymbolChangeResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertTrue(response.isSuccess());
    Map<String, List<CorporateSymbolChangeItem>> items = response.getItems();
    Assert.assertNotNull(items);
    Assert.assertFalse("expect at least one SYMBOL_CHANGE record for META", items.isEmpty());
    CorporateSymbolChangeItem first = items.values().iterator().next().get(0);
    Assert.assertNotNull(first.getOldSymbol());
    Assert.assertNotNull(first.getNewSymbol());
    System.out.println("SYMBOL_CHANGE items: " + items);
  }

  @Test
  public void testGetCorporateDelisting() {
    CorporateDelistingRequest request = CorporateDelistingRequest.newRequest(
        Arrays.asList("TWTR"),
        Market.US,
        parse("2022-01-01"),
        parse("2023-01-01")
    );
    CorporateDelistingResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertTrue(response.isSuccess());
    Map<String, List<CorporateDelistingItem>> items = response.getItems();
    Assert.assertNotNull(items);
    Assert.assertFalse("expect at least one DELISTING record for TWTR", items.isEmpty());
    CorporateDelistingItem first = items.values().iterator().next().get(0);
    Assert.assertNotNull(first.getAnnouncedDate());
    System.out.println("DELISTING items: " + items);
  }

  @Test
  public void testGetCorporateIpo() {
    CorporateIpoRequest request = CorporateIpoRequest.newRequest(
        Arrays.asList("RIVN"),
        Market.US,
        parse("2021-01-01"),
        parse("2022-01-01")
    );
    CorporateIpoResponse response = client.execute(request);
    Assert.assertNotNull(response);
    Assert.assertTrue(response.isSuccess());
    Map<String, List<CorporateIpoItem>> items = response.getItems();
    Assert.assertNotNull(items);
    Assert.assertFalse("expect at least one IPO record for RIVN", items.isEmpty());
    CorporateIpoItem first = items.values().iterator().next().get(0);
    Assert.assertNotNull(first.getListingDate());
    System.out.println("IPO items: " + items);
  }

  private static java.util.Date parse(String s) {
    try { return SDF.parse(s); }
    catch (Exception e) { throw new RuntimeException(e); }
  }
}
