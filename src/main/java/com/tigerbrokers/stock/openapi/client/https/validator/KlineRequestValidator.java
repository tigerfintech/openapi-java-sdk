package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureKlineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteKlineModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import java.util.List;

/**
 * Description: created by liutongping on 2022/05/10
 */
public class KlineRequestValidator implements RequestValidator<ApiModel> {

  @Override
  public void validate(ApiModel model) throws TigerApiException {
    if (model instanceof QuoteKlineModel) {
      QuoteKlineModel klineModel = (QuoteKlineModel) model;
      if (klineModel.getSymbols() == null || klineModel.getSymbols().isEmpty()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbols");
      }
      if (StringUtils.isEmpty(klineModel.getkType())) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "period");
      }
      if (!StringUtils.isEmpty(klineModel.getPageToken()) && klineModel.getSymbols().size() != 1) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_ERROR, "pageToken is only for single symbol");
      }
    } else if (model instanceof FutureKlineModel) {
      FutureKlineModel klineModel = (FutureKlineModel) model;
      List<String> symbols = klineModel.getContractCodes();
      if (symbols == null || symbols.isEmpty()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "contractCodes");
      }
      if (StringUtils.isEmpty(klineModel.getPeriod())) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "period");
      }
      if (null == klineModel.getBeginTime()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "begin_time");
      }
      if (null == klineModel.getEndTime()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "end_time");
      }
      if (klineModel.getBeginTime().compareTo(klineModel.getEndTime()) >= 0) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_RANGE_ERROR, "begin_time", "end_time");
      }

      if (!StringUtils.isEmpty(klineModel.getPageToken()) && klineModel.getContractCodes().size() != 1) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_ERROR, "pageToken is only for single contractCode");
      }
    }
  }
}
