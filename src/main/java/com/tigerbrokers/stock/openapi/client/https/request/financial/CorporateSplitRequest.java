package com.tigerbrokers.stock.openapi.client.https.request.financial;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.CorporateActionModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateSplitResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.CorporateActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/02/28.
 */
public class CorporateSplitRequest extends TigerCommonRequest implements TigerRequest<CorporateSplitResponse> {

  public CorporateSplitRequest() {
    setApiMethodName(MethodName.CORPORATE_ACTION);
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
  }

  public static CorporateSplitRequest newRequest(List<String> symbols, Market market, Date beginDate, Date endDate) {
    CorporateSplitRequest request = new CorporateSplitRequest();
    CorporateActionModel model = new CorporateActionModel();
    model.setActionType(CorporateActionType.SPLIT);
    model.setSymbols(symbols);
    model.setMarket(market);
    model.setBeginDate(beginDate);
    model.setEndDate(endDate);
    request.setApiModel(model);

    return request;
  }

  @Override
  public Class<CorporateSplitResponse> getResponseClass() {
    return CorporateSplitResponse.class;
  }
}
