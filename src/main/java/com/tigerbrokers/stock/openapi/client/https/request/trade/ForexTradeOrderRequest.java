package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.ForexTradeOrderModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.ForexTradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

public class ForexTradeOrderRequest extends TigerCommonRequest implements TigerRequest<ForexTradeOrderResponse> {

  private ForexTradeOrderRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.PLACE_FOREX_ORDER);
  }

  public static ForexTradeOrderRequest newRequest(ForexTradeOrderModel model) {
    ForexTradeOrderRequest request = new ForexTradeOrderRequest();
    request.setApiModel(model);
    return request;
  }

  public static ForexTradeOrderRequest buildRequest(SegmentType segType,
      Currency sourceCurrency, Double sourceAmount, Currency targetCurrency) {
    return buildRequest(null, segType, sourceCurrency, sourceAmount, targetCurrency);
  }

  public static ForexTradeOrderRequest buildRequest(
      String account, SegmentType segType,
      Currency sourceCurrency, Double sourceAmount, Currency targetCurrency) {
    ForexTradeOrderModel model = new ForexTradeOrderModel();
    if (StringUtils.isEmpty(account)) {
      model.setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    } else {
      model.setAccount(account);
    }
    if (segType != null && segType != SegmentType.ALL) {
      model.setSegType(segType);
    }
    if (sourceCurrency != null && sourceCurrency != Currency.ALL) {
      model.setSourceCurrency(sourceCurrency);
    }
    if (targetCurrency != null && targetCurrency != Currency.ALL) {
      model.setTargetCurrency(targetCurrency);
    }
    model.setSourceAmount(sourceAmount);
    return newRequest(model);
  }

  public ForexTradeOrderRequest segType(SegmentType segType) {
    ForexTradeOrderModel model = (ForexTradeOrderModel) getApiModel();
    if (segType != null && segType != SegmentType.ALL) {
      model.setSegType(segType);
    } else {
      model.setSegType(null);
    }
    return this;
  }

  public ForexTradeOrderRequest sourceCurrency(Currency currency) {
    ForexTradeOrderModel model = (ForexTradeOrderModel) getApiModel();
    if (currency != null && currency != Currency.ALL) {
      model.setSourceCurrency(currency);
    } else {
      model.setSourceCurrency(null);
    }
    return this;
  }

  public ForexTradeOrderRequest targetCurrency(Currency currency) {
    ForexTradeOrderModel model = (ForexTradeOrderModel) getApiModel();
    if (currency != null && currency != Currency.ALL) {
      model.setTargetCurrency(currency);
    } else {
      model.setTargetCurrency(null);
    }
    return this;
  }

  public ForexTradeOrderRequest sourceAmount(Double sourceAmount) {
    ForexTradeOrderModel model = (ForexTradeOrderModel) getApiModel();
    model.setSourceAmount(sourceAmount);
    return this;
  }

  public ForexTradeOrderRequest timeInForce(TimeInForce timeInForce) {
    ForexTradeOrderModel model = (ForexTradeOrderModel) getApiModel();
    model.setTimeInForce(timeInForce);
    return this;
  }

  public ForexTradeOrderRequest externalId(String externalId) {
    ForexTradeOrderModel model = (ForexTradeOrderModel) getApiModel();
    model.setExternalId(externalId);
    return this;
  }

  public ForexTradeOrderRequest account(String account) {
    ForexTradeOrderModel model = (ForexTradeOrderModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public ForexTradeOrderRequest secretKey(String secretKey) {
    ForexTradeOrderModel model = (ForexTradeOrderModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public ForexTradeOrderRequest lang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  @Override
  public Class<ForexTradeOrderResponse> getResponseClass() {
    return ForexTradeOrderResponse.class;
  }
}
