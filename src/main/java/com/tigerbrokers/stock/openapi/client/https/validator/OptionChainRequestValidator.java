package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainV3Model;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import java.util.List;

/**
 * Description: created by liutongping on 2022/5/23
 */
public class OptionChainRequestValidator implements RequestValidator<ApiModel> {

  @Override
  public void validate(ApiModel model) throws TigerApiException {

    if (model instanceof OptionChainModel) {
      OptionChainModel optionChainModel = (OptionChainModel) model;
      if (StringUtils.isEmpty(optionChainModel.getSymbol())) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbol");
      }
      if (null == optionChainModel.getExpiry()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "expiry");
      }
    } else if (model instanceof OptionChainV3Model) {
      List<OptionChainModel> optionBasic = ((OptionChainV3Model) model).getOptionBasic();
      if (null == optionBasic || optionBasic.size() == 0) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "optionBasic");
      }
      for (OptionChainModel optionChainModel : optionBasic) {
        validate(optionChainModel);
      }
    }
  }
}
