package com.tigerbrokers.stock.openapi.client.https.request.user;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.user.model.UserTradeTokenModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserTradeTokenResponse;

/**
 * Description:
 * Created by lijiawen on 2019/03/13.
 */
public class UserTradeTokenRequest extends TigerCommonRequest implements TigerRequest<UserTradeTokenResponse> {

  public UserTradeTokenRequest() {
    setApiMethodName(ApiServiceType.USER_TRADE_TOKEN);
  }

  public static UserTradeTokenRequest newRequest(String tradePassword) {
    UserTradeTokenRequest request = new UserTradeTokenRequest();
    UserTradeTokenModel model = new UserTradeTokenModel(tradePassword);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<UserTradeTokenResponse> getResponseClass() {
    return UserTradeTokenResponse.class;
  }
}
