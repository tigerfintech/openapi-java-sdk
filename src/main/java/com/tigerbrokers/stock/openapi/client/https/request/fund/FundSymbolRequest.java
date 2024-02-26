package com.tigerbrokers.stock.openapi.client.https.request.fund;

import com.tigerbrokers.stock.openapi.client.https.domain.fund.model.FundSymbolModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.fund.FundSymbolResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by bean on 2023/07/25.
 */
public class FundSymbolRequest extends TigerCommonRequest implements TigerRequest<FundSymbolResponse> {

  public FundSymbolRequest() {
    setApiMethodName(MethodName.FUND_ALL_SYMBOLS);
  }

  public static FundSymbolRequest newRequest() {
    FundSymbolRequest request = new FundSymbolRequest();
    FundSymbolModel model = new FundSymbolModel();
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FundSymbolResponse> getResponseClass() {
    return FundSymbolResponse.class;
  }
}
