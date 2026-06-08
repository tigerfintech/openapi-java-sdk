package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.OptionExerciseRecordModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.OptionExerciseRecordResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

public class OptionExerciseRecordRequest extends TigerCommonRequest implements
    TigerRequest<OptionExerciseRecordResponse> {

  private OptionExerciseRecordRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.OPTION_EXERCISE_RECORD);
  }

  /**
   * 分页查询行权申请记录
   *
   * @param account 交易账户
   * @param page    页码，从 1 开始
   * @param size    每页数量，1-100
   */
  public static OptionExerciseRecordRequest buildRequest(String account, Integer page, Integer size) {
    OptionExerciseRecordModel model = new OptionExerciseRecordModel();
    model.setAccount(account);
    model.setPage(page);
    model.setSize(size);
    OptionExerciseRecordRequest request = new OptionExerciseRecordRequest();
    request.setApiModel(model);
    return request;
  }

  public OptionExerciseRecordRequest setStatus(String status) {
    OptionExerciseRecordModel model = (OptionExerciseRecordModel) getApiModel();
    model.setStatus(status);
    return this;
  }

  public OptionExerciseRecordRequest setType(String type) {
    OptionExerciseRecordModel model = (OptionExerciseRecordModel) getApiModel();
    model.setType(type);
    return this;
  }

  public OptionExerciseRecordRequest setSymbol(String symbol) {
    OptionExerciseRecordModel model = (OptionExerciseRecordModel) getApiModel();
    model.setSymbol(symbol);
    return this;
  }

  public OptionExerciseRecordRequest setOrderBy(String orderBy) {
    OptionExerciseRecordModel model = (OptionExerciseRecordModel) getApiModel();
    model.setOrderBy(orderBy);
    return this;
  }

  public OptionExerciseRecordRequest setSecretKey(String secretKey) {
    OptionExerciseRecordModel model = (OptionExerciseRecordModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  @Override
  public Class<OptionExerciseRecordResponse> getResponseClass() {
    return OptionExerciseRecordResponse.class;
  }
}
