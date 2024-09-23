package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.EstimateTradableQuantityModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.EstimateTradableQuantityResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

public class EstimateTradableQuantityRequest extends TigerCommonRequest implements TigerRequest<EstimateTradableQuantityResponse> {

  private EstimateTradableQuantityRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.ESTIMATE_TRADABLE_QUANTITY);
  }

  public static EstimateTradableQuantityRequest newRequest(EstimateTradableQuantityModel model) {
    EstimateTradableQuantityRequest request = new EstimateTradableQuantityRequest();
    request.setApiModel(model);
    return request;
  }

  /**
   * construct request with default account
   * @param secType
   * @param symbol
   * @param action BUY/SELL
   * @param orderType
   * @param limitPrice Limit price, required when order_type is LMT/STP_LMT
   * @param stopPrice Stop price, required when order_type is STP/STP_LMT
   * @return
   */
  public static EstimateTradableQuantityRequest buildRequest(
      SecType secType, String symbol,
      ActionType action, OrderType orderType,
      Double limitPrice, Double stopPrice) {
    return buildRequest(null, secType, symbol, action, orderType, limitPrice, stopPrice);
  }

  /**
   * construct request
   * @param account
   * @param secType
   * @param symbol
   * @param action BUY/SELL
   * @param orderType
   * @param limitPrice Limit price, required when order_type is LMT/STP_LMT
   * @param stopPrice Stop price, required when order_type is STP/STP_LMT
   * @return
   */
  public static EstimateTradableQuantityRequest buildRequest(
      String account, SecType secType, String symbol,
      ActionType action, OrderType orderType,
      Double limitPrice, Double stopPrice) {
    EstimateTradableQuantityModel model = new EstimateTradableQuantityModel();
    if (StringUtils.isEmpty(account)) {
      model.setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    } else {
      model.setAccount(account);
    }
    model.setSecType(secType);
    model.setSymbol(symbol);
    model.setAction(action);
    model.setOrderType(orderType);
    model.setLimitPrice(limitPrice);
    model.setStopPrice(stopPrice);
    return newRequest(model);
  }

  public EstimateTradableQuantityRequest account(String account) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public EstimateTradableQuantityRequest secretKey(String secretKey) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public EstimateTradableQuantityRequest segType(SegmentType segType) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setSegType(segType);
    return this;
  }

  public EstimateTradableQuantityRequest secType(SecType secType) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setSecType(secType);
    return this;
  }

  public EstimateTradableQuantityRequest symbol(String symbol) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setSymbol(symbol);
    return this;
  }

  public EstimateTradableQuantityRequest expiry(String expiry) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setExpiry(expiry);
    return this;
  }

  public EstimateTradableQuantityRequest strike(String strike) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setStrike(strike);
    return this;
  }

  public EstimateTradableQuantityRequest right(String right) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setRight(right);
    return this;
  }

  public EstimateTradableQuantityRequest action(ActionType action) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setAction(action);
    return this;
  }

  public EstimateTradableQuantityRequest orderType(OrderType orderType) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setOrderType(orderType);
    return this;
  }

  public EstimateTradableQuantityRequest limitPrice(Double limitPrice) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setLimitPrice(limitPrice);
    return this;
  }

  public EstimateTradableQuantityRequest stopPrice(Double stopPrice) {
    EstimateTradableQuantityModel model = (EstimateTradableQuantityModel) getApiModel();
    model.setStopPrice(stopPrice);
    return this;
  }

  public EstimateTradableQuantityRequest lang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  @Override
  public Class<EstimateTradableQuantityResponse> getResponseClass() {
    return EstimateTradableQuantityResponse.class;
  }
}
