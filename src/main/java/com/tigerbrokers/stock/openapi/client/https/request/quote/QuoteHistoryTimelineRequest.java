package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteHistoryTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteHistoryTimelineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2022/04/12.
 */
public class QuoteHistoryTimelineRequest extends TigerCommonRequest implements TigerRequest<QuoteHistoryTimelineResponse> {

  public QuoteHistoryTimelineRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.HISTORY_TIMELINE);
  }

  public static QuoteHistoryTimelineRequest newRequest(List<String> symbols) {
    return newRequest(symbols, null);
  }

  public static QuoteHistoryTimelineRequest newRequest(List<String> symbols, String date) {
    return newRequest(symbols, date, Language.en_US);
  }

  public static QuoteHistoryTimelineRequest newRequest(List<String> symbols, String date, Language lang) {
    QuoteHistoryTimelineRequest request = new QuoteHistoryTimelineRequest();
    QuoteHistoryTimelineModel model = new QuoteHistoryTimelineModel(symbols, date);
    model.setLang(lang);
    request.setApiModel(model);
    return request;
  }

  public QuoteHistoryTimelineRequest withRight(RightOption rightOption) {
    if (rightOption != null) {
      if (apiModel instanceof QuoteHistoryTimelineModel) {
        QuoteHistoryTimelineModel timelimeModel = (QuoteHistoryTimelineModel) apiModel;
        timelimeModel.setRight(rightOption);
      }
    }
    return this;
  }

  @Override
  public Class<QuoteHistoryTimelineResponse> getResponseClass() {
    return QuoteHistoryTimelineResponse.class;
  }
}
