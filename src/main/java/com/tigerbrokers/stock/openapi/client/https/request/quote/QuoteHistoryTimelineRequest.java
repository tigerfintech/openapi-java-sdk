package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteHistoryTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteHistoryTimelineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
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
    return newRequest(symbols, DateUtils.printSystemDate());
  }

  public static QuoteHistoryTimelineRequest newRequest(List<String> symbols, String date) {
    return newRequest(symbols, date, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteHistoryTimelineRequest newRequest(List<String> symbols, String date, Language lang) {
    QuoteHistoryTimelineRequest request = new QuoteHistoryTimelineRequest();
    QuoteHistoryTimelineModel model = new QuoteHistoryTimelineModel(symbols, date, lang);
    model.setRight(RightOption.br);
    request.setApiModel(model);
    return request;
  }

  public static QuoteHistoryTimelineRequest newRequest(List<String> symbols, Long date) {
    return newRequest(symbols, date, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone(),
        ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static QuoteHistoryTimelineRequest newRequest(List<String> symbols,
      Long date, TimeZoneId zoneId, Language lang) {
    QuoteHistoryTimelineRequest request = new QuoteHistoryTimelineRequest();
    String dateStr = null;
    if (date != null) {
      dateStr = DateUtils.printDate(date, zoneId == null ? ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone() : zoneId);
    }
    QuoteHistoryTimelineModel model = new QuoteHistoryTimelineModel(symbols, dateStr, lang);
    model.setRight(RightOption.br);
    request.setApiModel(model);
    return request;
  }

  public QuoteHistoryTimelineRequest withRight(RightOption rightOption) {
    QuoteHistoryTimelineModel timelimeModel = (QuoteHistoryTimelineModel) apiModel;
    timelimeModel.setRight(rightOption);
    return this;
  }

  @Override
  public Class<QuoteHistoryTimelineResponse> getResponseClass() {
    return QuoteHistoryTimelineResponse.class;
  }
}
