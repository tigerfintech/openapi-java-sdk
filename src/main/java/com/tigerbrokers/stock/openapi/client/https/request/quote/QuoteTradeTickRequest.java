package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteTradeTickModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeTickResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradeSession;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteTradeTickRequest extends TigerCommonRequest implements TigerRequest<QuoteTradeTickResponse> {

  public QuoteTradeTickRequest() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.TRADE_TICK);
  }

  public static QuoteTradeTickRequest newRequest(List<String> symbols) {
    return newRequest(symbols, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteTradeTickRequest newRequest(List<String> symbols, Language lang) {
    QuoteTradeTickRequest request = new QuoteTradeTickRequest();
    QuoteTradeTickModel model = new QuoteTradeTickModel(symbols, lang);
    request.setApiModel(model);
    return request;
  }

  public static QuoteTradeTickRequest newRequest(List<String> symbols, Language lang, Integer limit) {
    QuoteTradeTickRequest request = new QuoteTradeTickRequest();
    QuoteTradeTickModel model = new QuoteTradeTickModel(symbols, lang);
    model.setLimit(limit);
    request.setApiModel(model);
    return request;
  }

  public static QuoteTradeTickRequest newRequest(List<String> symbols, Long beginIndex, Long endIndex) {
    QuoteTradeTickRequest request = new QuoteTradeTickRequest();
    QuoteTradeTickModel model = new QuoteTradeTickModel(symbols, beginIndex, endIndex,
        ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
    request.setApiModel(model);
    return request;
  }

  public static QuoteTradeTickRequest newRequest(List<String> symbols, Long beginIndex, Long endIndex, Integer limit) {
    QuoteTradeTickRequest request = new QuoteTradeTickRequest();
    QuoteTradeTickModel model = new QuoteTradeTickModel(symbols, beginIndex, endIndex,
            ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
    model.setLimit(limit);
    request.setApiModel(model);
    return request;
  }

  public static QuoteTradeTickRequest newRequest(List<String> symbols, Long beginIndex, Long endIndex, Language lang) {
    QuoteTradeTickRequest request = new QuoteTradeTickRequest();
    QuoteTradeTickModel model = new QuoteTradeTickModel(symbols, beginIndex, endIndex, lang);
    request.setApiModel(model);
    return request;
  }

  public void setTradeSession(TradeSession tradeSession) {
    ((QuoteTradeTickModel)apiModel).setTradeSession(tradeSession);
  }

  @Override
  public Class<QuoteTradeTickResponse> getResponseClass() {
    return QuoteTradeTickResponse.class;
  }
}
