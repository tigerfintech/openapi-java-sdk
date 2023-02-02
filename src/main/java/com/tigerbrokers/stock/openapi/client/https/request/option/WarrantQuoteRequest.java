/*
 * TigerBrokers
 * Copyright (C) 2014-2023 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.WarrantQuoteModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.WarrantQuoteResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.List;

public class WarrantQuoteRequest extends TigerCommonRequest implements TigerRequest<WarrantQuoteResponse> {

  public WarrantQuoteRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.WARRANT_REAL_TIME_QUOTE);
  }

  public static WarrantQuoteRequest newRequest(List<String> symbols) {
    WarrantQuoteRequest request = new WarrantQuoteRequest();
    WarrantQuoteModel model = new WarrantQuoteModel(symbols);
    model.setLang(ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
    request.setApiModel(model);
    return request;
  }

  @Override
  public final WarrantQuoteModel getApiModel() {
    WarrantQuoteModel model = (WarrantQuoteModel)super.getApiModel();
    if (null == model) {
      model = new WarrantQuoteModel();
      setApiModel(model);
    }
    return model;
  }

  public WarrantQuoteRequest lang(Language lang) {
    getApiModel().setLang(lang);
    return this;
  }

  @Override
  public Class<WarrantQuoteResponse> getResponseClass() {
    return WarrantQuoteResponse.class;
  }
}