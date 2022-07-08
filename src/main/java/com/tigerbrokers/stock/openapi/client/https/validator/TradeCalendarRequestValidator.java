package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.TradeCalendarModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;

/**
 * Description: created by liutongping on 2022/6/23
 */
public class TradeCalendarRequestValidator implements RequestValidator<TradeCalendarModel> {

  @Override
  public void validate(TradeCalendarModel model) throws TigerApiException {
    if (null == model.getMarket()) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "market");
    }
  }
}
