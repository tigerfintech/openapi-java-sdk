package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import java.util.List;

/**
 * Description: created by liutongping on 2022/05/23
 */
public class BatchRequestValidator implements RequestValidator<BatchApiModel<? extends ApiModel>> {

  @Override
  public void validate(BatchApiModel<? extends ApiModel> batchApiModel) throws TigerApiException {
    if (null == batchApiModel.getItems() || batchApiModel.getItems().size() == 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "batchApiModel");
    }
    List<? extends ApiModel> models = batchApiModel.getItems();
    if (models.get(0) instanceof OptionKlineModel) {
      for (OptionKlineModel model : (List<OptionKlineModel>) batchApiModel.getItems()) {
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

        if (null == model.getBeginTime()) {
          throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "begin_time");
        }
        if (null == model.getEndTime()) {
          throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "end_time");
        }
        if (model.getBeginTime().compareTo(model.getEndTime()) >= 0) {
          throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_RANGE_ERROR, "begin_time", "end_time");
        }
      }
    } else if (models.get(0) instanceof OptionCommonModel) {
      for (OptionCommonModel model : (List<OptionCommonModel>) batchApiModel.getItems()) {
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
      }
    }
  }
}
