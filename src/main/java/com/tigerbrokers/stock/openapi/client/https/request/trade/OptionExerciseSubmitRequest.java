package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.OptionExerciseSubmitModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseSubmitResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionExerciseType;

public class OptionExerciseSubmitRequest extends TigerCommonRequest implements
    TigerRequest<OptionExerciseSubmitResponse> {

  private OptionExerciseSubmitRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.OPTION_EXERCISE_SUBMIT);
  }

  /**
   * 提交提前行权申请（Exercise 类型）
   *
   * @param account       交易账户
   * @param contractId    期权合约 ID
   * @param quantity      行权数量
   * @param executingDate 行权执行日期，格式 yyyy-MM-dd
   * @param isForce       是否强制行权
   */
  public static OptionExerciseSubmitRequest buildExerciseRequest(
      String account, Long contractId, Double quantity,
      String executingDate, Boolean isForce) {
    OptionExerciseSubmitModel model = new OptionExerciseSubmitModel();
    model.setAccount(account);
    model.setContractId(contractId);
    model.setType(OptionExerciseType.Exercise.name());
    model.setQuantity(quantity);
    model.setExecutingDate(executingDate);
    model.setIsForce(isForce);
    OptionExerciseSubmitRequest request = new OptionExerciseSubmitRequest();
    request.setApiModel(model);
    return request;
  }

  /**
   * 提交提前放弃行权申请（Expire 类型）
   *
   * @param account    交易账户
   * @param contractId 期权合约 ID
   * @param quantity   行权数量
   * @param itmRate    价内率（0-10），可选
   */
  public static OptionExerciseSubmitRequest buildExpireRequest(
      String account, Long contractId, Double quantity, Integer itmRate) {
    OptionExerciseSubmitModel model = new OptionExerciseSubmitModel();
    model.setAccount(account);
    model.setContractId(contractId);
    model.setType(OptionExerciseType.Expire.name());
    model.setQuantity(quantity);
    model.setItmRate(itmRate);
    OptionExerciseSubmitRequest request = new OptionExerciseSubmitRequest();
    request.setApiModel(model);
    return request;
  }

  public OptionExerciseSubmitRequest setSecretKey(String secretKey) {
    OptionExerciseSubmitModel model = (OptionExerciseSubmitModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  @Override
  public Class<OptionExerciseSubmitResponse> getResponseClass() {
    return OptionExerciseSubmitResponse.class;
  }
}
