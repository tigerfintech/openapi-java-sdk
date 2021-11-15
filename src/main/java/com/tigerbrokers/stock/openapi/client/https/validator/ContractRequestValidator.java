package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.BaseContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractsModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import java.util.List;

/**
 * Description: created by liutongping on 2021/11/5
 */
public class ContractRequestValidator implements RequestValidator<BaseContractModel> {

  @Override
  public void validate(BaseContractModel model) throws TigerApiException {
    if (StringUtils.isEmpty(model.getAccount())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "account");
    }
    if (StringUtils.isEmpty(model.getSecType())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "sec_type");
    }

    if (model instanceof ContractModel) {
      if (StringUtils.isEmpty(((ContractModel) model).getSymbol())) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbol");
      }
    } else if (model instanceof ContractsModel) {
      List<String> symbols = ((ContractsModel) model).getSymbols();
      if (symbols == null || symbols.isEmpty()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbols");
      }
    }
  }
}
