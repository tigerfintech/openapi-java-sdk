package com.tigerbrokers.stock.openapi.client.https.request.financial;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.CorporateActionModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateDividendResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.CorporateActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/02/28.
 */
public class CorporateDividendRequest extends TigerCommonRequest implements TigerRequest<CorporateDividendResponse> {

  public CorporateDividendRequest() {
    setApiMethodName(ApiServiceType.CORPORATE_ACTION);
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
  }

  public static CorporateDividendRequest newRequest(List<String> symbols, Market market, Date beginDate, Date endDate) {
    CorporateDividendRequest request = new CorporateDividendRequest();
    CorporateActionModel model = new CorporateActionModel();
    model.setActionType(CorporateActionType.DIVIDEND);
    model.setSymbols(symbols);
    model.setMarket(market);
    model.setBeginDate(beginDate);
    model.setEndDate(endDate);
    request.setApiModel(model);

    return request;
  }

  @Override
  public Class<CorporateDividendResponse> getResponseClass() {
    return CorporateDividendResponse.class;
  }
}
