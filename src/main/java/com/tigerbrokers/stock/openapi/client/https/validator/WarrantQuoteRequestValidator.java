package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.WarrantQuoteModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;

/**
 * Description: created by bean on 2023/02/02
 */
public class WarrantQuoteRequestValidator implements RequestValidator<WarrantQuoteModel> {

  @Override
  public void validate(WarrantQuoteModel model) throws TigerApiException {
    if (model.getSymbols() == null || model.getSymbols().size() == 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbols");
    }
  }
}
