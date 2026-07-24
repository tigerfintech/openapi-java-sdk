package com.tigerbrokers.stock.openapi.client.https.request.financial;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.CorporateActionModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateSymbolChangeResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.CorporateActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.Date;
import java.util.List;

public class CorporateSymbolChangeRequest extends TigerCommonRequest implements TigerRequest<CorporateSymbolChangeResponse> {

  public CorporateSymbolChangeRequest() {
    setApiMethodName(MethodName.CORPORATE_ACTION);
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
  }

  public static CorporateSymbolChangeRequest newRequest(List<String> symbols, Market market, Date beginDate, Date endDate) {
    CorporateSymbolChangeRequest request = new CorporateSymbolChangeRequest();
    CorporateActionModel model = new CorporateActionModel();
    model.setActionType(CorporateActionType.SYMBOL_CHANGE);
    model.setSymbols(symbols);
    model.setMarket(market);
    model.setBeginDate(beginDate);
    model.setEndDate(endDate);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<CorporateSymbolChangeResponse> getResponseClass() {
    return CorporateSymbolChangeResponse.class;
  }
}
