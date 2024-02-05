package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.WarrantFilterModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

/**
 * Description: created by bean on 2023/02/02
 */
public class WarrantFilterRequestValidator implements RequestValidator<WarrantFilterModel> {

  @Override
  public void validate(WarrantFilterModel model) throws TigerApiException {
    if (StringUtils.isEmpty(model.getSymbol())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbol");
    }
  }
}
