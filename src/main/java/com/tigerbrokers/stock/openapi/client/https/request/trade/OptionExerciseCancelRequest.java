package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.OptionExerciseCancelModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseCancelResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

public class OptionExerciseCancelRequest extends TigerCommonRequest implements
    TigerRequest<OptionExerciseCancelResponse> {

  private OptionExerciseCancelRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.OPTION_EXERCISE_CANCEL);
  }

  /**
   * 撤销已提交的行权申请
   *
   * @param account 交易账户
   * @param id      行权申请记录 ID
   */
  public static OptionExerciseCancelRequest buildRequest(String account, Long id) {
    OptionExerciseCancelModel model = new OptionExerciseCancelModel();
    model.setAccount(account);
    model.setId(id);
    OptionExerciseCancelRequest request = new OptionExerciseCancelRequest();
    request.setApiModel(model);
    return request;
  }

  public OptionExerciseCancelRequest setSecretKey(String secretKey) {
    OptionExerciseCancelModel model = (OptionExerciseCancelModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  @Override
  public Class<OptionExerciseCancelResponse> getResponseClass() {
    return OptionExerciseCancelResponse.class;
  }
}
