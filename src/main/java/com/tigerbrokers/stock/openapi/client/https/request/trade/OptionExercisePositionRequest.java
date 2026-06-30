package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.OptionExercisePositionModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExercisePositionResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionExerciseType;

public class OptionExercisePositionRequest extends TigerCommonRequest implements
    TigerRequest<OptionExercisePositionResponse> {

  private OptionExercisePositionRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.OPTION_EXERCISE_POSITION);
  }

  /**
   * 查询指定类型下可行权的期权持仓
   *
   * @param account      交易账户
   * @param exerciseType 行权类型
   */
  public static OptionExercisePositionRequest buildRequest(
      String account, OptionExerciseType exerciseType) {
    OptionExercisePositionModel model = new OptionExercisePositionModel();
    model.setAccount(account);
    model.setType(exerciseType.name());
    OptionExercisePositionRequest request = new OptionExercisePositionRequest();
    request.setApiModel(model);
    return request;
  }

  public OptionExercisePositionRequest setSecretKey(String secretKey) {
    OptionExercisePositionModel model = (OptionExercisePositionModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  @Override
  public Class<OptionExercisePositionResponse> getResponseClass() {
    return OptionExercisePositionResponse.class;
  }
}
