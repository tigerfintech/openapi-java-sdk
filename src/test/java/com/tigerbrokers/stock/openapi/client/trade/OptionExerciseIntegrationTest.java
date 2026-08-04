package com.tigerbrokers.stock.openapi.client.trade;

import com.tigerbrokers.stock.openapi.client.testsupport.IntegTestConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.OptionExerciseCheckItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.OptionExercisePositionPageItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.OptionExerciseRecordPageItem;
import com.tigerbrokers.stock.openapi.client.https.request.trade.OptionExerciseCancelRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.OptionExerciseCheckRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.OptionExercisePositionRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.OptionExerciseRecordRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.OptionExerciseSubmitRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseCancelResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseCheckResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExercisePositionResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseRecordResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseSubmitResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionExerciseType;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.tigerbrokers.stock.openapi.client.testsupport.ReadOnlyApi;
import com.tigerbrokers.stock.openapi.client.testsupport.WriteApi;

/**
 * Integration tests for option exercise APIs.
 *
 * Run with:
 *   -Dtest.config.path=<path to config directory>
 *   -Dtest.account=<trade account>
 *   -Dtest.contract.id=<exercisable contract id>
 *   -Dtest.server.url=<gateway url>  (optional)
 */
// 类上标只读，唯一涉及写操作的 testSubmitAndCancelExercise 单独标 WriteApi。
// contract job 用 -DexcludedGroups=...WriteApi 把它排掉，避免每次 push 都真实提交行权。
@Category(ReadOnlyApi.class)
public class OptionExerciseIntegrationTest {

  private static String account;
  private static Long contractId;
  private static TigerHttpClient client;

  @BeforeClass
  public static void setUpClass() {
    Assume.assumeTrue("integration test; enable with -Dtest.integ=true",
        Boolean.getBoolean("test.integ"));
    client = IntegTestConfig.createClient();
    account = IntegTestConfig.getAccount();
    Assert.assertNotNull("TIGEROPEN_ACCOUNT env var required", account);

    String contractIdStr = System.getProperty("test.contract.id");
    if (contractIdStr == null || contractIdStr.isEmpty()) {
      contractIdStr = System.getenv("TIGEROPEN_CONTRACT_ID");
    }
    if (contractIdStr != null && !contractIdStr.isEmpty()) {
      contractId = Long.parseLong(contractIdStr);
    }
  }

  @Test
  public void testCheckExercise() {
    OptionExerciseCheckRequest request =
        OptionExerciseCheckRequest.buildRequest(account, contractId, OptionExerciseType.Exercise);

    OptionExerciseCheckResponse response = client.execute(request);

    Assert.assertNotNull(response);
    Assert.assertTrue("check exercise failed: " + response.getMessage(), response.isSuccess());
    OptionExerciseCheckItem item = response.getItem();
    System.out.println("checkExercise: item=" + (item == null ? "null" : "availableQuantity="
        + item.getAvailableQuantity() + " position=" + item.getPosition()));
  }

  @Test
  public void testCheckExpireWithItmRate() {
    OptionExerciseCheckRequest request =
        OptionExerciseCheckRequest.buildRequest(account, contractId, OptionExerciseType.Expire)
            .setItmRate(5);

    OptionExerciseCheckResponse response = client.execute(request);

    Assert.assertNotNull(response);
    Assert.assertTrue("check expire failed: " + response.getMessage(), response.isSuccess());
    System.out.println("checkExpire: item=" + response.getItem());
  }

  @Test
  public void testGetExercisePositions() {
    OptionExercisePositionRequest request =
        OptionExercisePositionRequest.buildRequest(account, OptionExerciseType.Exercise);

    OptionExercisePositionResponse response = client.execute(request);

    Assert.assertNotNull(response);
    Assert.assertTrue("get positions failed: " + response.getMessage(), response.isSuccess());
    OptionExercisePositionPageItem page = response.getItem();
    Assert.assertNotNull(page);
    System.out.println("getPositions: itemCount=" + page.getItemCount());
    if (page.getItems() != null) {
      page.getItems().forEach(p -> System.out.println(
          "  contractId=" + p.getContractId() + " symbol=" + p.getSymbol()
          + " expireDate=" + p.getExpireDate() + " strike=" + p.getStrike()
          + " callPut=" + p.getCallPut() + " position=" + p.getPosition()
          + " availableQty=" + p.getAvailableQuantity()));
    }
  }

  @Test
  public void testGetExerciseRecords() {
    OptionExerciseRecordRequest request =
        OptionExerciseRecordRequest.buildRequest(account, 1, 10);

    OptionExerciseRecordResponse response = client.execute(request);

    Assert.assertNotNull(response);
    Assert.assertTrue("get records failed: " + response.getMessage(), response.isSuccess());
    OptionExerciseRecordPageItem page = response.getItem();
    Assert.assertNotNull(page);
    System.out.println("getRecords: itemCount=" + page.getItemCount()
        + " pageCount=" + page.getPageCount());
  }

  @Test
  public void testGetExerciseRecordsWithFilters() {
    OptionExerciseRecordRequest request =
        OptionExerciseRecordRequest.buildRequest(account, 1, 20)
            .setType(OptionExerciseType.Exercise.name())
            .setOrderBy("symbol");

    OptionExerciseRecordResponse response = client.execute(request);

    Assert.assertNotNull(response);
    Assert.assertTrue("get records with filter failed: " + response.getMessage(),
        response.isSuccess());
    System.out.println("getRecordsFiltered: " + response.getItem());
  }

  // 会真实提交并撤销行权申请，只在手动触发的 integ job 里跑。
  // 原先靠注释掉 @Test 来禁用，现在由 WriteApi 分类 + -Dgroups 选择控制。
  @Test
  @Category(WriteApi.class)
  public void testSubmitAndCancelExercise() {
    Assume.assumeNotNull("set -Dtest.contract.id for write tests", contractId);
    OptionExerciseSubmitRequest submitRequest =
        OptionExerciseSubmitRequest.buildExerciseRequest(account, contractId, 1.0, null, false);
    OptionExerciseSubmitResponse submitResponse = client.execute(submitRequest);
    Assert.assertNotNull(submitResponse);
    Assert.assertTrue("submit failed: " + submitResponse.getMessage(), submitResponse.isSuccess());
    System.out.println("submit success");

    OptionExerciseRecordRequest recordRequest =
        OptionExerciseRecordRequest.buildRequest(account, 1, 5);
    OptionExerciseRecordResponse recordResponse = client.execute(recordRequest);
    Assert.assertTrue(recordResponse.isSuccess());
    Assert.assertNotNull(recordResponse.getItem());
    Assert.assertFalse(recordResponse.getItem().getItems().isEmpty());
    Long recordId = recordResponse.getItem().getItems().get(0).getId();

    OptionExerciseCancelRequest cancelRequest =
        OptionExerciseCancelRequest.buildRequest(account, recordId);
    OptionExerciseCancelResponse cancelResponse = client.execute(cancelRequest);
    Assert.assertNotNull(cancelResponse);
    Assert.assertTrue("cancel failed: " + cancelResponse.getMessage(), cancelResponse.isSuccess());
    System.out.println("cancel success, id=" + recordId);
  }
}
