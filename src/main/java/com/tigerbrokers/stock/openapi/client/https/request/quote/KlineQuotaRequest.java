package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.KlineQuotaModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.KlineQuotaResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by bean on 2023/06/13.
 */
public class KlineQuotaRequest extends TigerCommonRequest implements TigerRequest<KlineQuotaResponse> {

  public KlineQuotaRequest() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.KLINE_QUOTA);
  }

  public static KlineQuotaRequest newRequest() {
    return newRequest(Boolean.FALSE);
  }

  public static KlineQuotaRequest newRequest(Boolean withDetails) {
    KlineQuotaRequest request = new KlineQuotaRequest();
    KlineQuotaModel model = new KlineQuotaModel(withDetails);
    request.setApiModel(model);
    return request;
  }

  public void setWithDetails(Boolean withDetails) {
    if (getApiModel() == null) {
      setApiModel(new KlineQuotaModel());
    }
    ((KlineQuotaModel)getApiModel()).setWithDetails(withDetails);
  }

  @Override
  public Class<KlineQuotaResponse> getResponseClass() {
    return KlineQuotaResponse.class;
  }
}
