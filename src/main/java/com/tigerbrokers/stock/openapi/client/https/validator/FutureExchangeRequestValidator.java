package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureExchangeModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

/**
 * Description: created by liutongping on 2022/5/23
 */
public class FutureExchangeRequestValidator implements RequestValidator<FutureExchangeModel> {

  @Override
  public void validate(FutureExchangeModel model) throws TigerApiException {

    if (StringUtils.isEmpty(model.getSecType())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "sec_type");
    }
    SecType secType = SecType.valueOf(model.getSecType().toUpperCase());
    if (null == secType) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_VALUE_ERROR, "sec_type");
    }
    if (SecType.FUT != secType && SecType.FOP != secType) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_VALUE_ERROR, "sec_type");
    }
    model.setSecType(secType.name());
  }
}
