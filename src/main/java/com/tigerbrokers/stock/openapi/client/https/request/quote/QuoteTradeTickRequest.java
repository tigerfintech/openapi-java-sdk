package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteTradeTickModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeTickResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradeStatus;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteTradeTickRequest extends TigerCommonRequest implements TigerRequest<QuoteTradeTickResponse> {

  public QuoteTradeTickRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.TRADE_TICK);
  }

  public static QuoteTradeTickRequest newRequest(List<String> symbols) {
    return newRequest(symbols, Language.en_US);
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

  @Deprecated
  public static QuoteTradeTickRequest newRequest(List<String> symbols, Long beginIndex, Long endIndex) {
    QuoteTradeTickRequest request = new QuoteTradeTickRequest();
    QuoteTradeTickModel model = new QuoteTradeTickModel(symbols, beginIndex, endIndex, Language.en_US);
    request.setApiModel(model);
    return request;
  }

  @Deprecated
  public static QuoteTradeTickRequest newRequest(List<String> symbols, Long beginIndex, Long endIndex, Language lang) {
    QuoteTradeTickRequest request = new QuoteTradeTickRequest();
    QuoteTradeTickModel model = new QuoteTradeTickModel(symbols, beginIndex, endIndex, lang);
    request.setApiModel(model);
    return request;
  }

  public void setTradeStatus(TradeStatus tradeStatus) {
    ((QuoteTradeTickModel)apiModel).setTradeStatus(tradeStatus);
  }

  @Override
  public Class<QuoteTradeTickResponse> getResponseClass() {
    return QuoteTradeTickResponse.class;
  }
}
