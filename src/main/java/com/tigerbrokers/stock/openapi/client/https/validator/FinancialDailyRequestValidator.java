package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialDailyModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;

/**
 * Description: created by liutongping on 2022/5/23
 */
public class FinancialDailyRequestValidator implements RequestValidator<FinancialDailyModel> {

  @Override
  public void validate(FinancialDailyModel model) throws TigerApiException {
    if (null == model.getMarket()) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "market");
    }
    if (model.getSymbols() == null || model.getSymbols().size() == 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbols");
    }
    if (model.getFields() == null || model.getFields().size() == 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "fields");
    }

    if (null == model.getBeginDate()) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "begin_date");
    }
    if (null == model.getEndDate()) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "end_date");
    }
    if (!model.getBeginDate().before(model.getEndDate())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_RANGE_ERROR, "begin_date", "end_date");
    }
  }
}
