package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.OptionExerciseCheckModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseCheckResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionExerciseType;

public class OptionExerciseCheckRequest extends TigerCommonRequest implements
    TigerRequest<OptionExerciseCheckResponse> {

  private OptionExerciseCheckRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.OPTION_EXERCISE_CHECK);
  }

  /**
   * 行权检验 — 预估行权后正股持仓变化
   *
   * @param account      交易账户
   * @param contractId   期权合约 ID
   * @param exerciseType 行权类型
   */
  public static OptionExerciseCheckRequest buildRequest(
      String account, Long contractId, OptionExerciseType exerciseType) {
    OptionExerciseCheckModel model = new OptionExerciseCheckModel();
    model.setAccount(account);
    model.setContractId(contractId);
    model.setType(exerciseType.name());
    OptionExerciseCheckRequest request = new OptionExerciseCheckRequest();
    request.setApiModel(model);
    return request;
  }

  public OptionExerciseCheckRequest setQuantity(Double quantity) {
    OptionExerciseCheckModel model = (OptionExerciseCheckModel) getApiModel();
    model.setQuantity(quantity);
    return this;
  }

  public OptionExerciseCheckRequest setExecutingDate(String executingDate) {
    OptionExerciseCheckModel model = (OptionExerciseCheckModel) getApiModel();
    model.setExecutingDate(executingDate);
    return this;
  }

  public OptionExerciseCheckRequest setIsForce(Boolean isForce) {
    OptionExerciseCheckModel model = (OptionExerciseCheckModel) getApiModel();
    model.setIsForce(isForce);
    return this;
  }

  public OptionExerciseCheckRequest setItmRate(Integer itmRate) {
    OptionExerciseCheckModel model = (OptionExerciseCheckModel) getApiModel();
    model.setItmRate(itmRate);
    return this;
  }

  public OptionExerciseCheckRequest setSecretKey(String secretKey) {
    OptionExerciseCheckModel model = (OptionExerciseCheckModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  @Override
  public Class<OptionExerciseCheckResponse> getResponseClass() {
    return OptionExerciseCheckResponse.class;
  }
}
