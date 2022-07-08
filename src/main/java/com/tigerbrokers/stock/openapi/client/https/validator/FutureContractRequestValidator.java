package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContinuousContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContractByConCodeModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContractByExchCodeModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureCurrentContractModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

/**
 * Description: created by liutongping on 2022/5/23
 */
public class FutureContractRequestValidator implements RequestValidator<ApiModel> {

  @Override
  public void validate(ApiModel model) throws TigerApiException {
    if (model instanceof FutureContinuousContractModel) {
      if (StringUtils.isEmpty(((FutureContinuousContractModel) model).getType())) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "type");
      }
    } else if (model instanceof FutureCurrentContractModel) {
      if (StringUtils.isEmpty(((FutureCurrentContractModel) model).getType())) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "type");
      }
    } else if (model instanceof FutureContractByConCodeModel) {
      if (StringUtils.isEmpty(((FutureContractByConCodeModel) model).getContractCode())) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "contract_code");
      }
    } else if (model instanceof FutureContractByExchCodeModel) {
      if (StringUtils.isEmpty(((FutureContractByExchCodeModel) model).getExchangeCode())) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "exchange_code");
      }
    }
  }
}
