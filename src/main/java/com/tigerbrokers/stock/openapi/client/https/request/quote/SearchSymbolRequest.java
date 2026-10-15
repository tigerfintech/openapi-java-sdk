package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.SearchSymbolModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.SearchSymbolResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

public class SearchSymbolRequest extends TigerCommonRequest implements TigerRequest<SearchSymbolResponse> {

  public SearchSymbolRequest() {
    setApiMethodName(MethodName.SEARCH_SYMBOL);
  }

  public static SearchSymbolRequest newRequest(String keyword) {
    SearchSymbolRequest request = new SearchSymbolRequest();
    SearchSymbolModel model = new SearchSymbolModel(keyword);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<SearchSymbolResponse> getResponseClass() {
    return SearchSymbolResponse.class;
  }
}
