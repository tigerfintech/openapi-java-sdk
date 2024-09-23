/*
 * TigerBrokers
 * Copyright (C) 2014-2021 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainFilterModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainV3Model;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.ArrayList;
import java.util.List;

public class OptionChainQueryV3Request extends TigerCommonRequest implements TigerRequest<OptionChainResponse> {

  public OptionChainQueryV3Request() {
    setApiVersion(V3_0);
    setApiMethodName(MethodName.OPTION_CHAIN);
  }

  public static OptionChainQueryV3Request of(OptionChainModel item, OptionChainFilterModel filter) {
    List<OptionChainModel> models = new ArrayList<>();
    models.add(item);
    return of(models, filter);
  }

  public static OptionChainQueryV3Request of(OptionChainModel item, OptionChainFilterModel filter, Market market) {
    List<OptionChainModel> models = new ArrayList<>();
    models.add(item);
    return of(models, filter, market);
  }

  public static OptionChainQueryV3Request of(List<OptionChainModel> items, OptionChainFilterModel filter) {
    return of(items, filter, Market.US);
  }

  public static OptionChainQueryV3Request of(List<OptionChainModel> items,
                                             OptionChainFilterModel filter,
                                             Market market) {
    OptionChainV3Model v3Model = new OptionChainV3Model();
    v3Model.setOptionFilter(filter);
    v3Model.setOptionBasic(items);
    v3Model.setMarket(market);
    OptionChainQueryV3Request request = new OptionChainQueryV3Request();
    request.setApiModel(v3Model);
    return request;
  }

  public OptionChainV3Model getApiModel() {
    if (apiModel == null) {
      apiModel = new OptionChainV3Model();
    }
    return (OptionChainV3Model)apiModel;
  }

  public OptionChainQueryV3Request setMarket(Market market) {
    getApiModel().setMarket(market);
    return this;
  }

  public OptionChainQueryV3Request setOptionBasic(List<OptionChainModel> items) {
    getApiModel().setOptionBasic(items);
    return this;
  }

  public OptionChainQueryV3Request setOptionFilter(OptionChainFilterModel filter) {
    getApiModel().setOptionFilter(filter);
    return this;
  }

  public OptionChainQueryV3Request setReturnGreekValue(Boolean returnGreekValue) {
    getApiModel().setReturnGreekValue(returnGreekValue);
    return this;
  }

  @Override
  public Class<OptionChainResponse> getResponseClass() {
    return OptionChainResponse.class;
  }
}