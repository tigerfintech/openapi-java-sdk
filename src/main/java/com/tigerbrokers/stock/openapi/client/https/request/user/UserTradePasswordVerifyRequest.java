package com.tigerbrokers.stock.openapi.client.https.request.user;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.user.model.UserTradePasswordVerifyModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserTradePasswordVerifyResponse;

/**
 * Description:
 * Created by lijiawen on 2019/05/05.
 */
public class UserTradePasswordVerifyRequest extends TigerCommonRequest
    implements TigerRequest<UserTradePasswordVerifyResponse> {

  public UserTradePasswordVerifyRequest() {
    setApiMethodName(ApiServiceType.USER_TRADE_PASSWORD_VERIFY);
  }

  public static UserTradePasswordVerifyRequest newRequest(String idNo) {
    UserTradePasswordVerifyRequest request = new UserTradePasswordVerifyRequest();
    UserTradePasswordVerifyModel model = new UserTradePasswordVerifyModel(idNo);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<UserTradePasswordVerifyResponse> getResponseClass() {
    return UserTradePasswordVerifyResponse.class;
  }
}
