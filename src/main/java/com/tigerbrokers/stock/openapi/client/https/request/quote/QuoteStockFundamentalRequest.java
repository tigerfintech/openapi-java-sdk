package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteDepthModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteStockFundamentalResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

import java.util.List;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.DEFAULT_VERSION;

/**
 * author：bean
 * date：2024/12/19
 */
public class QuoteStockFundamentalRequest extends TigerCommonRequest implements TigerRequest<QuoteStockFundamentalResponse> {

  public QuoteStockFundamentalRequest() {
    setApiVersion(DEFAULT_VERSION);
    setApiMethodName(MethodName.STOCK_FUNDAMENTAL);
  }

  public static QuoteStockFundamentalRequest newRequest(List<String> symbols, String market) {
    QuoteStockFundamentalRequest request = new QuoteStockFundamentalRequest();
    QuoteDepthModel quoteDepthModel = new QuoteDepthModel(symbols, market);
    request.setApiModel(quoteDepthModel);
    return request;
  }

  @Override
  public Class<QuoteStockFundamentalResponse> getResponseClass() {
    return QuoteStockFundamentalResponse.class;
  }
}
