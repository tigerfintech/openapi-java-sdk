package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteBrokerHoldModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteBrokerHoldResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Created on 2025/4/15
 *
 * @author: sukai
 */
public class QuoteBrokerHoldRequest extends TigerCommonRequest implements TigerRequest<QuoteBrokerHoldResponse> {

  public QuoteBrokerHoldRequest() {
    setApiMethodName(MethodName.BROKER_HOLD);
  }

  public static QuoteBrokerHoldRequest newRequest(Market market, Integer limit, Integer page) {
    QuoteBrokerHoldRequest request = new QuoteBrokerHoldRequest();
    QuoteBrokerHoldModel model = new QuoteBrokerHoldModel(market, limit, page);
    request.setApiModel(model);
    return request;
  }

  public static QuoteBrokerHoldRequest newRequest(Market market, Integer limit) {
    QuoteBrokerHoldRequest request = new QuoteBrokerHoldRequest();
    QuoteBrokerHoldModel model = new QuoteBrokerHoldModel(market, limit);
    request.setApiModel(model);
    return request;
  }

  public static QuoteBrokerHoldRequest newRequest(Market market, Integer limit, Integer page, String orderBy, String direction) {
    QuoteBrokerHoldRequest request = new QuoteBrokerHoldRequest();
    QuoteBrokerHoldModel model = new QuoteBrokerHoldModel(market, limit, page, orderBy, direction);
    request.setApiModel(model);
    return request;
  }

  public void setLang(Language lang) {
    QuoteBrokerHoldModel model = (QuoteBrokerHoldModel)getApiModel();
    if (model != null) {
      model.setLang(lang);
    }
  }

  public void setLimit(Integer limit) {
    QuoteBrokerHoldModel model = (QuoteBrokerHoldModel)getApiModel();
    if (model != null) {
      model.setLimit(limit);
    }
  }

  public void setPage(Integer page) {
    QuoteBrokerHoldModel model = (QuoteBrokerHoldModel)getApiModel();
    if (model != null) {
      model.setPage(page);
    }
  }

  public void setDirection(String direction) {
    QuoteBrokerHoldModel model = (QuoteBrokerHoldModel)getApiModel();
    if (model != null) {
      model.setDirection(direction);
    }
  }

  public void setOrderBy(String orderBy) {
    QuoteBrokerHoldModel model = (QuoteBrokerHoldModel)getApiModel();
    if (model != null) {
      model.setOrderBy(orderBy);
    }
  }


  @Override
  public Class<QuoteBrokerHoldResponse> getResponseClass() {
    return QuoteBrokerHoldResponse.class;
  }
}
