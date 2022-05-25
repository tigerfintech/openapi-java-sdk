package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import java.util.List;

/**
 * Description: created by liutongping on 2022/05/23
 */
public class BatchRequestValidator implements RequestValidator<BatchApiModel<? extends ApiModel>> {
  OptionChainRequestValidator optionChainRequestValidator = new OptionChainRequestValidator();
  OptionCommonRequestValidator optionCommonRequestValidator = new OptionCommonRequestValidator();

  @Override
  public void validate(BatchApiModel<? extends ApiModel> batchApiModel) throws TigerApiException {
    if (null == batchApiModel.getItems() || batchApiModel.getItems().size() == 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "batchApiModel");
    }
    List<? extends ApiModel> models = batchApiModel.getItems();
    if (models.get(0) instanceof OptionCommonModel) {
      for (OptionCommonModel model : (List<OptionCommonModel>) batchApiModel.getItems()) {
        optionCommonRequestValidator.validate(model);
      }
    } else if (models.get(0) instanceof OptionChainModel) {
      for (OptionChainModel model : (List<OptionChainModel>) batchApiModel.getItems()) {
        optionChainRequestValidator.validate(model);
      }
    }
  }
}
