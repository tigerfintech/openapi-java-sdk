package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.TradeCalendarModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeCalendarResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

/**
 * Description:
 * Created by bean on 2022/06/23.
 */
public class QuoteTradeCalendarRequest extends TigerCommonRequest implements TigerRequest<QuoteTradeCalendarResponse> {

  public QuoteTradeCalendarRequest() {
    setApiVersion(V2_0);
    setApiMethodName(ApiServiceType.TRADING_CALENDAR);
  }

  /**
   * Construct request parameters
   * @param market US, HK, CN
   * @return
   */
  public static QuoteTradeCalendarRequest newRequest(Market market) {
    return newRequest(market, null, null);
  }

  /**
   * Construct request parameters
   *
   * @param market US, HK, CN
   * @param beginDate yyyy-MM-dd
   * @param endDate yyyy-MM-dd
   * @return
   */
  public static QuoteTradeCalendarRequest newRequest(Market market, String beginDate, String endDate) {
    QuoteTradeCalendarRequest request = new QuoteTradeCalendarRequest();
    TradeCalendarModel model = new TradeCalendarModel();
    model.setMarket(market);
    model.setBeginDate(beginDate);
    model.setEndDate(endDate);
    request.setApiModel(model);

    return request;
  }

  @Override
  public Class<QuoteTradeCalendarResponse> getResponseClass() {
    return QuoteTradeCalendarResponse.class;
  }
}
