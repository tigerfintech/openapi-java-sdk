package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionExpirationModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import java.util.List;

/**
 * Description: created by liutongping on 2022/5/23
 */
public class OptionExpirationRequestValidator implements RequestValidator<OptionExpirationModel> {

  @Override
  public void validate(OptionExpirationModel model) throws TigerApiException {
    List<String> symbols = model.getSymbols();
    if (null == symbols || symbols.size() == 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbols");
    }
  }
}
