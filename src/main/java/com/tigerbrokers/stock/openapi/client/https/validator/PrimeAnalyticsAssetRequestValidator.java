package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PrimeAnalyticsAssetModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

/**
 * Description: created by liutongping on 2021/11/5
 */
public class PrimeAnalyticsAssetRequestValidator implements RequestValidator<PrimeAnalyticsAssetModel> {

  @Override
  public void validate(PrimeAnalyticsAssetModel model) throws TigerApiException {
    if (StringUtils.isEmpty(model.getAccount())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "account");
    }
  }
}
