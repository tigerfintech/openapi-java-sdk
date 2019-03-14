package com.tigerbrokers.stock.openapi.client.https.request.user;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.https.domain.user.model.UserLoginModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserLoginResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.GrantType;

/**
 * Description:
 * Created by lijiawen on 2019/03/13.
 */
public class UserLoginRequest extends TigerCommonRequest implements TigerRequest<UserLoginResponse> {

  public UserLoginRequest() {
    setApiMethodName(ApiServiceType.USER_LOGIN);
  }

  public static UserLoginRequest newRequest(String userName, String password) {
    UserLoginRequest request = new UserLoginRequest();
    UserLoginModel model = new UserLoginModel(userName, password);
    request.setApiModel(model);
    return request;
  }

  public static UserLoginRequest newRequest(String userName, String password, GrantType grantType) {
    UserLoginRequest request = new UserLoginRequest();
    UserLoginModel model = new UserLoginModel(userName, password, grantType);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<UserLoginResponse> getResponseClass() {
    return UserLoginResponse.class;
  }
}
