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

  public static OptionChainQueryV3Request of(List<OptionChainModel> items, OptionChainFilterModel filter) {
    OptionChainV3Model v3Model = new OptionChainV3Model();
    v3Model.setOptionFilter(filter);
    v3Model.setOptionBasic(items);
    OptionChainQueryV3Request request = new OptionChainQueryV3Request();
    request.setApiModel(v3Model);
    return request;
  }

  @Override
  public Class<OptionChainResponse> getResponseClass() {
    return OptionChainResponse.class;
  }
}