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
    Assume.assumeNotNull("set -Dtest.contract.id for check exercise", contractId);
    OptionExerciseCheckRequest request =
        OptionExerciseCheckRequest.buildRequest(account, contractId, OptionExerciseType.Exercise);

    OptionExerciseCheckResponse response = client.execute(request);

    Assert.assertNotNull(response);
    Assert.assertTrue("check exercise failed: " + response.getMessage(), response.isSuccess());
    OptionExerciseCheckItem item = response.getItem();
    Assert.assertNotNull("check exercise item should not be null", item);
    Assert.assertNotNull("availableQuantity should not be null", item.getAvailableQuantity());
    Assert.assertTrue("availableQuantity should be >= 0", item.getAvailableQuantity() >= 0);
    Assert.assertNotNull("position should not be null", item.getPosition());
    Assert.assertTrue("position should be >= 0", item.getPosition() >= 0);
    Assert.assertNotNull("symbol should not be null", item.getSymbol());
    Assert.assertTrue("symbol should not be empty", !item.getSymbol().isEmpty());
    System.out.println("checkExercise: item=" + (item == null ? "null" : "availableQuantity="
        + item.getAvailableQuantity() + " position=" + item.getPosition()));
  }

  @Test
  public void testCheckExpireWithItmRate() {
    Assume.assumeNotNull("set -Dtest.contract.id for check expire", contractId);
    OptionExerciseCheckRequest request =
        OptionExerciseCheckRequest.buildRequest(account, contractId, OptionExerciseType.Expire)
            .setItmRate(5);

    OptionExerciseCheckResponse response = client.execute(request);

    Assert.assertNotNull(response);
    Assert.assertTrue("check expire failed: " + response.getMessage(), response.isSuccess());
    OptionExerciseCheckItem expireItem = response.getItem();
    Assert.assertNotNull("expire item should not be null", expireItem);
    Assert.assertNotNull("expire availableQuantity should not be null", expireItem.getAvailableQuantity());
    Assert.assertTrue("expire availableQuantity should be >= 0", expireItem.getAvailableQuantity() >= 0);
    Assert.assertNotNull("expire position should not be null", expireItem.getPosition());
    Assert.assertTrue("expire position should be >= 0", expireItem.getPosition() >= 0);
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
    Assert.assertNotNull("positions page should not be null", page);
    Assert.assertNotNull("itemCount should not be null", page.getItemCount());
    Assert.assertTrue("itemCount should be >= 0", page.getItemCount() >= 0);
    if (page.getItems() != null && !page.getItems().isEmpty()) {
      com.tigerbrokers.stock.openapi.client.https.domain.trade.item.OptionExercisePositionItem first = page.getItems().get(0);
      Assert.assertNotNull("position symbol should not be null", first.getSymbol());
      Assert.assertTrue("position symbol should not be empty", !first.getSymbol().isEmpty());
      Assert.assertNotNull("position contractId should not be null", first.getContractId());
      Assert.assertTrue("position contractId should be > 0", first.getContractId() > 0);
      Assert.assertNotNull("position expireDate should not be null", first.getExpireDate());
      Assert.assertTrue("position expireDate should not be empty", !first.getExpireDate().isEmpty());
      Assert.assertNotNull("position strike should not be null", first.getStrike());
      Assert.assertTrue("position strike should not be empty", !first.getStrike().isEmpty());
      Assert.assertNotNull("position callPut should not be null", first.getCallPut());
      Assert.assertTrue("position callPut should not be empty", !first.getCallPut().isEmpty());
      Assert.assertNotNull("position market should not be null", first.getMarket());
      Assert.assertTrue("position market should not be empty", !first.getMarket().isEmpty());
      Assert.assertNotNull("position position should not be null", first.getPosition());
      Assert.assertTrue("position position should be >= 0", first.getPosition() >= 0);
      System.out.println("getPositions: itemCount=" + page.getItemCount());
      page.getItems().forEach(p -> System.out.println(
          "  contractId=" + p.getContractId() + " symbol=" + p.getSymbol()
          + " expireDate=" + p.getExpireDate() + " strike=" + p.getStrike()
          + " callPut=" + p.getCallPut() + " position=" + p.getPosition()
          + " availableQty=" + p.getAvailableQuantity()));
    } else {
      System.out.println("getPositions: itemCount=" + page.getItemCount() + " (no positions)");
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
    Assert.assertNotNull("records page should not be null", page);
    Assert.assertNotNull("itemCount should not be null", page.getItemCount());
    Assert.assertTrue("itemCount should be >= 0", page.getItemCount() >= 0);
    Assert.assertNotNull("pageCount should not be null", page.getPageCount());
    Assert.assertTrue("pageCount should be >= 0", page.getPageCount() >= 0);
    System.out.println("getRecords: itemCount=" + page.getItemCount()
        + " pageCount=" + page.getPageCount());
    if (page.getItems() != null && !page.getItems().isEmpty()) {
      com.tigerbrokers.stock.openapi.client.https.domain.trade.item.OptionExerciseRecordItem first = page.getItems().get(0);
      Assert.assertNotNull("record id should not be null", first.getId());
      Assert.assertTrue("record id should be > 0", first.getId() > 0);
      Assert.assertNotNull("record symbol should not be null", first.getSymbol());
      Assert.assertTrue("record symbol should not be empty", !first.getSymbol().isEmpty());
      Assert.assertNotNull("record status should not be null", first.getStatus());
      Assert.assertTrue("record status should not be empty", !first.getStatus().isEmpty());
      Assert.assertNotNull("record type should not be null", first.getType());
      Assert.assertTrue("record type should not be empty", !first.getType().isEmpty());
      Assert.assertNotNull("record callPut should not be null", first.getCallPut());
      Assert.assertTrue("record callPut should not be empty", !first.getCallPut().isEmpty());
      Assert.assertNotNull("record expireDate should not be null", first.getExpireDate());
      Assert.assertTrue("record expireDate should not be empty", !first.getExpireDate().isEmpty());
      Assert.assertNotNull("record strike should not be null", first.getStrike());
      Assert.assertTrue("record strike should not be empty", !first.getStrike().isEmpty());
    }
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
    OptionExerciseRecordPageItem filterPage = response.getItem();
    Assert.assertNotNull("filtered records page should not be null", filterPage);
    Assert.assertNotNull("filtered itemCount should not be null", filterPage.getItemCount());
    Assert.assertTrue("filtered itemCount should be >= 0", filterPage.getItemCount() >= 0);
    Assert.assertNotNull("filtered pageCount should not be null", filterPage.getPageCount());
    Assert.assertTrue("filtered pageCount should be >= 0", filterPage.getPageCount() >= 0);
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
    Assert.assertNotNull("recordId should not be null", recordId);
    Assert.assertTrue("recordId should be > 0", recordId > 0);

    OptionExerciseCancelRequest cancelRequest =
        OptionExerciseCancelRequest.buildRequest(account, recordId);
    OptionExerciseCancelResponse cancelResponse = client.execute(cancelRequest);
    Assert.assertNotNull(cancelResponse);
    Assert.assertTrue("cancel failed: " + cancelResponse.getMessage(), cancelResponse.isSuccess());
    System.out.println("cancel success, id=" + recordId);
  }
}
