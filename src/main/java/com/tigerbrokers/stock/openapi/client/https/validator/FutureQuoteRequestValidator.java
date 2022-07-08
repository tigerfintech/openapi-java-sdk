package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureRealTimeQuoteModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTickModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTradingDateModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import java.util.List;

/**
 * Description: created by liutongping on 2022/5/23
 */
public class FutureQuoteRequestValidator implements RequestValidator<ApiModel> {

  @Override
  public void validate(ApiModel model) throws TigerApiException {
    if (model instanceof FutureTradingDateModel) {
      if (StringUtils.isEmpty(((FutureTradingDateModel) model).getContractCode())) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "contract_code");
      }
    } else if (model instanceof FutureRealTimeQuoteModel) {
      List<String> contractCodes = ((FutureRealTimeQuoteModel) model).getContractCodes();
      if (null == contractCodes || contractCodes.size() == 0) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "contract_codes");
      }
    } else if (model instanceof FutureTickModel) {
      FutureTickModel futureTickModel = (FutureTickModel) model;
      List<String> contractCodes = futureTickModel.getContractCodes();
      if (null == contractCodes || contractCodes.size() == 0) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "contract_codes");
      }
      if (null == futureTickModel.getBeginIndex()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "begin_index");
      }
      if (null == futureTickModel.getEndIndex()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "end_index");
      }
    }
  }
}
