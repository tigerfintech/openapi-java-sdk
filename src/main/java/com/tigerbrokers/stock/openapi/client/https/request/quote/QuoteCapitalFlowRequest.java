package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteCapitalFlowModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteCapitalFlowResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.CapitalPeriod;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by bean on 2022/11/25.
 */
public class QuoteCapitalFlowRequest extends TigerCommonRequest implements TigerRequest<QuoteCapitalFlowResponse> {

  public QuoteCapitalFlowRequest() {
    setApiMethodName(MethodName.CAPITAL_FLOW);
  }

  public static QuoteCapitalFlowRequest newRequest(String symbol,
      Market market, CapitalPeriod period) {
    QuoteCapitalFlowRequest request = new QuoteCapitalFlowRequest();

    QuoteCapitalFlowModel model = new QuoteCapitalFlowModel(symbol, market.name(), period.name());
    request.setApiModel(model);
    return request;
  }

  public void setLang(Language lang) {
    QuoteCapitalFlowModel model = (QuoteCapitalFlowModel)getApiModel();
    if (model != null) {
      model.setLang(lang);
    }
  }

  public void setBeginTime(Long beginTime) {
    QuoteCapitalFlowModel model = (QuoteCapitalFlowModel)getApiModel();
    if (model != null) {
      model.setBeginTime(beginTime);
    }
  }

  public void setEndTime(Long endTime) {
    QuoteCapitalFlowModel model = (QuoteCapitalFlowModel)getApiModel();
    if (model != null) {
      model.setEndTime(endTime);
    }
  }

  public void setLimit(Integer limit) {
    QuoteCapitalFlowModel model = (QuoteCapitalFlowModel)getApiModel();
    if (model != null) {
      model.setLimit(limit);
    }
  }

  @Override
  public Class<QuoteCapitalFlowResponse> getResponseClass() {
    return QuoteCapitalFlowResponse.class;
  }
}
