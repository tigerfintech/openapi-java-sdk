package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteStockTradeModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockTradeResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2019/01/07.
 */
public class QuoteStockTradeRequest extends TigerCommonRequest implements TigerRequest<QuoteStockTradeResponse> {

  public QuoteStockTradeRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.QUOTE_STOCK_TRADE);
  }

  public static QuoteStockTradeRequest newRequest(List<String> symbols) {
    QuoteStockTradeRequest request = new QuoteStockTradeRequest();
    QuoteStockTradeModel model = new QuoteStockTradeModel();
    model.setSymbols(symbols);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteStockTradeResponse> getResponseClass() {
    return QuoteStockTradeResponse.class;
  }
}
