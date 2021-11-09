/*
 * TigerBrokers
 * Copyright (C) 2014-2021 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainFilterModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainV2Model;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse;
import java.util.ArrayList;
import java.util.List;

public class OptionChainQueryV2Request extends TigerCommonRequest implements TigerRequest<OptionChainResponse> {

  public OptionChainQueryV2Request() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.OPTION_CHAIN);
  }

  public static OptionChainQueryV2Request of(OptionChainModel item, OptionChainFilterModel filter) {
    List<OptionChainModel> models = new ArrayList<>();
    models.add(item);
    return of(models, filter);
  }

  public static OptionChainQueryV2Request of(List<OptionChainModel> items, OptionChainFilterModel filter) {
    OptionChainV2Model v2Model = new OptionChainV2Model();
    v2Model.setOptionFilter(filter);
    v2Model.setOptionBasic(items);
    OptionChainQueryV2Request request = new OptionChainQueryV2Request();
    request.setApiModel(v2Model);
    return request;
  }

  @Override
  public Class<OptionChainResponse> getResponseClass() {
    return OptionChainResponse.class;
  }
}