package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialReportModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;

/**
 * Description: created by liutongping on 2022/5/23
 */
public class FinancialReportRequestValidator implements RequestValidator<FinancialReportModel> {

  @Override
  public void validate(FinancialReportModel model) throws TigerApiException {
    if (null == model.getMarket()) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "market");
    }
    if (model.getSymbols() == null || model.getSymbols().size() == 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbols");
    }
    if (model.getFields() == null || model.getFields().size() == 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "fields");
    }
    if (null == model.getPeriodType()) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "period_type");
    }
  }
}
