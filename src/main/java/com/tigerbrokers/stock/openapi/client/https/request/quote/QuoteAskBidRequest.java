package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteAskBidModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteAskBidResponse;
import java.util.List;

/**
 * 作者：ltc
 * 时间：2019/08/13
 */
public class QuoteAskBidRequest extends TigerCommonRequest implements TigerRequest<QuoteAskBidResponse> {

  public QuoteAskBidRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.ASK_BID);
  }

  public static QuoteAskBidRequest newRequest(List<String> symbols, Integer hkQuoteAuthority) {
    QuoteAskBidRequest request = new QuoteAskBidRequest();
    QuoteAskBidModel quoteAskBidModel = new QuoteAskBidModel(symbols, hkQuoteAuthority);
    request.setApiModel(quoteAskBidModel);
    return request;
  }

  @Override
  public Class<QuoteAskBidResponse> getResponseClass() {
    return QuoteAskBidResponse.class;
  }
}
