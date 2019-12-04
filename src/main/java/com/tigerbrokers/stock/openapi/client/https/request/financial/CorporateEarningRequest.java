package com.tigerbrokers.stock.openapi.client.https.request.financial;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.CorporateActionModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateEarningResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.CorporateActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.util.Date;

/**
 * Description:
 * Created by lijiawen on 2019/11/27
 */
public class CorporateEarningRequest extends TigerCommonRequest implements TigerRequest<CorporateEarningResponse> {

  public CorporateEarningRequest() {
    setApiMethodName(ApiServiceType.CORPORATE_ACTION);
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
  }

  public static CorporateEarningRequest newRequest(Market market, Date beginDate, Date endDate) {
    CorporateEarningRequest request = new CorporateEarningRequest();
    CorporateActionModel model = new CorporateActionModel();
    model.setActionType(CorporateActionType.EARNING);
    model.setMarket(market);
    model.setBeginDate(beginDate);
    model.setEndDate(endDate);
    request.setApiModel(model);

    return request;
  }

  @Override
  public Class<CorporateEarningResponse> getResponseClass() {
    return CorporateEarningResponse.class;
  }
}
