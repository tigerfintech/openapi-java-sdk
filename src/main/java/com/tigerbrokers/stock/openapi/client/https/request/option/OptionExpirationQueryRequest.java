package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionExpirationModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionExpirationResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/06.
 */
public class OptionExpirationQueryRequest extends TigerCommonRequest implements TigerRequest<OptionExpirationResponse> {

  @JSONField(name = "symbols")
  private OptionExpirationModel optionExpirationModel = new OptionExpirationModel();

  public OptionExpirationQueryRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.OPTION_EXPIRATION);
    setApiModel(optionExpirationModel);
  }

  public OptionExpirationQueryRequest(List<String> symbols) {
    this();
    optionExpirationModel.setSymbols(symbols);
  }

  public static OptionExpirationQueryRequest of(List<String> symbols) {
    return new OptionExpirationQueryRequest(symbols);
  }

  @Override
  public Class<OptionExpirationResponse> getResponseClass() {
    return OptionExpirationResponse.class;
  }
}
