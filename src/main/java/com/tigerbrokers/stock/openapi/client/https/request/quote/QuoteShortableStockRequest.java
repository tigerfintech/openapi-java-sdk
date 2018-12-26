package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteSymbolModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteShortableStockResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteShortableStockRequest extends TigerCommonRequest
    implements TigerRequest<QuoteShortableStockResponse> {

  public QuoteShortableStockRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.SHORTABLE_STOCKS);
  }

  public static QuoteShortableStockRequest newRequest(List<String> symbols) {
    return newRequest(symbols, null);
  }

  public static QuoteShortableStockRequest newRequest(List<String> symbols, Language lang) {
    QuoteShortableStockRequest request = new QuoteShortableStockRequest();
    QuoteSymbolModel model = new QuoteSymbolModel(symbols, lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<QuoteShortableStockResponse> getResponseClass() {
    return QuoteShortableStockResponse.class;
  }
}
