/*
 * TigerBrokers
 * Copyright (C) 2014-2023 All Rights Reserved.
 */
package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.WarrantFilterModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.WarrantFilterResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionPrice;
import com.tigerbrokers.stock.openapi.client.struct.enums.SortDir;
import com.tigerbrokers.stock.openapi.client.struct.enums.WarrantState;
import com.tigerbrokers.stock.openapi.client.struct.enums.WarrantType;
import java.util.HashSet;
import java.util.Set;

public class WarrantFilterRequest extends TigerCommonRequest implements TigerRequest<WarrantFilterResponse> {

  public WarrantFilterRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.WARRANT_FILTER);
  }

  public static WarrantFilterRequest newRequest(String symbol) {
    WarrantFilterRequest request = new WarrantFilterRequest();
    WarrantFilterModel model = new WarrantFilterModel(symbol);
    model.setLang(ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
    request.setApiModel(model);
    return request;
  }

  @Override
  public final WarrantFilterModel getApiModel() {
    WarrantFilterModel model = (WarrantFilterModel)super.getApiModel();
    if (null == model) {
      model = new WarrantFilterModel();
      setApiModel(model);
    }
    return model;
  }

  public WarrantFilterRequest lang(Language lang) {
    getApiModel().setLang(lang);
    return this;
  }

  public WarrantFilterRequest page(Integer page) {
    getApiModel().page(page);
    return this;
  }

  public WarrantFilterRequest pageSize(Integer pageSize) {
    getApiModel().pageSize(pageSize);
    return this;
  }

  /** expireDate, latestPrice, changeRate, change, volume, amount, lotSize, type, strike, entitlementPrice, outstandingRatio, premium, effectiveLeverage, breakevenPoint, delta, impliedVolatility, callPrice, beforeCallLevel, leverageRatio, entitlementRatio, inOutPrice */
  public WarrantFilterRequest sortFieldName(String sortFieldName) {
    getApiModel().sortFieldName(sortFieldName);
    return this;
  }

  public WarrantFilterRequest sortDir(SortDir sortDir) {
    getApiModel().sortDir(sortDir);
    return this;
  }

  public WarrantFilterRequest warrantType(WarrantType... warrantType) {
    if(warrantType != null && warrantType.length > 0) {
      Set<Integer> warrantTypeSets = new HashSet<>();
      for (WarrantType item : warrantType) {
        if (item != null) {
          warrantTypeSets.add(item.getValue());
        }
      }
      if (warrantTypeSets.size() > 0) {
        getApiModel().warrantType(warrantTypeSets);
      }
    }
    return this;
  }

  public WarrantFilterRequest issuerName(String issuerName) {
    getApiModel().issuerName(issuerName);
    return this;
  }

  public WarrantFilterRequest expireYM(String expireYM) {
    getApiModel().expireYM(expireYM);
    return this;
  }

  public WarrantFilterRequest state(WarrantState state) {
    if (null != state) {
      getApiModel().state(state.getValue());
    }
    return this;
  }

  public WarrantFilterRequest inOutPrice(OptionPrice... inOutPrice) {
    if(inOutPrice != null && inOutPrice.length > 0) {
      Set<Integer> inOutPriceSets = new HashSet<>();
      for (OptionPrice item : inOutPrice) {
        if (item != null) {
          inOutPriceSets.add(item.getValue());
        }
      }
      if (inOutPriceSets.size() > 0) {
        getApiModel().inOutPrice(inOutPriceSets);
      }
    }
    return this;
  }

  public WarrantFilterRequest lotSize(Set<Integer> lotSize) {
    getApiModel().lotSize(lotSize);
    return this;
  }

  public WarrantFilterRequest entitlementRatio(Set<Double> entitlementRatio) {
    getApiModel().entitlementRatio(entitlementRatio);
    return this;
  }

  public WarrantFilterRequest strike(Double min, Double max) {
    getApiModel().strike(min, max);
    return this;
  }

  public WarrantFilterRequest effectiveLeverage(Double min, Double max) {
    getApiModel().effectiveLeverage(min, max);
    return this;
  }

  public WarrantFilterRequest leverageRatio(Double min, Double max) {
    getApiModel().leverageRatio(min, max);
    return this;
  }

  public WarrantFilterRequest callPrice(Double min, Double max) {
    getApiModel().callPrice(min, max);
    return this;
  }

  public WarrantFilterRequest volume(Long min, Long max) {
    getApiModel().volume(min, max);
    return this;
  }

  public WarrantFilterRequest premium(Double min, Double max) {
    getApiModel().premium(min, max);
    return this;
  }

  public WarrantFilterRequest outstandingRatio(Double min, Double max) {
    getApiModel().outstandingRatio(min, max);
    return this;
  }

  public WarrantFilterRequest impliedVolatility(Double min, Double max) {
    getApiModel().impliedVolatility(min, max);
    return this;
  }

  @Override
  public Class<WarrantFilterResponse> getResponseClass() {
    return WarrantFilterResponse.class;
  }
}