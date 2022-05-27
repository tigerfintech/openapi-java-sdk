package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

/**
 * Description: created by liutongping on 2022/5/25
 */
public class OptionCommonRequestValidator implements RequestValidator<OptionCommonModel> {

  @Override
  public void validate(OptionCommonModel model) throws TigerApiException {

    if (StringUtils.isEmpty(model.getSymbol())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbol");
    }
    if (StringUtils.isEmpty(model.getRight())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "right");
    }
    if (StringUtils.isEmpty(model.getStrike())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "strike");
    }
    if (null == model.getExpiry()) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "expiry");
    }

    try {
      Right rightEnum = Right.valueOf(model.getRight().toUpperCase());
      model.setRight(rightEnum.name());
    } catch (Exception e) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_VALUE_ERROR, "right");
    }

    if (model instanceof OptionKlineModel) {
      OptionKlineModel optionKlineModel = (OptionKlineModel) model;
      if (null == optionKlineModel.getBeginTime()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "begin_time");
      }
      if (null == optionKlineModel.getEndTime()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "end_time");
      }
      if (optionKlineModel.getBeginTime().compareTo(optionKlineModel.getEndTime()) >= 0) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_RANGE_ERROR, "begin_time", "end_time");
      }
    }
  }
}
