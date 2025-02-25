package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.DepositWithdrawModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.DepositWithdrawResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.DEFAULT_VERSION;

/**
 * Description:
 * Created by bean on 2024/12/20.
 */
public class DepositWithdrawRequest extends TigerCommonRequest implements TigerRequest<DepositWithdrawResponse> {

  private DepositWithdrawRequest() {
    setApiVersion(DEFAULT_VERSION);
    setApiMethodName(MethodName.TRANSFER_FUND);
  }

  public static DepositWithdrawRequest newRequest() {
    return newRequest(new DepositWithdrawModel());
  }

  public static DepositWithdrawRequest newRequest(DepositWithdrawModel model) {
    DepositWithdrawRequest request = new DepositWithdrawRequest();
    request.setApiModel(model);
    if (StringUtils.isEmpty(model.getAccount())) {
      model.setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    } else if (StringUtils.isEmpty(model.getSecretKey())) {
      model.setAccount(ClientConfig.DEFAULT_CONFIG.secretKey);
    }
    if (null == model.getSegType()) {
      model.setSegType(SegmentType.SEC);
    }
    if (null == model.getLang()) {
      model.setLang(ClientConfig.DEFAULT_CONFIG.language);
    }
    return request;
  }

  public DepositWithdrawRequest account(String account) {
    DepositWithdrawModel model = (DepositWithdrawModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public DepositWithdrawRequest secretKey(String secretKey) {
    DepositWithdrawModel model = (DepositWithdrawModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public DepositWithdrawRequest lang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  @Override
  public Class<DepositWithdrawResponse> getResponseClass() {
    return DepositWithdrawResponse.class;
  }
}
