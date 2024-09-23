package com.tigerbrokers.stock.openapi.client.https.request.user;

import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserTokenResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by bean on 2023/02/08.
 */
public class UserTokenRefreshRequest extends TigerCommonRequest implements TigerRequest<UserTokenResponse> {

  public UserTokenRefreshRequest() {
    setApiMethodName(MethodName.USER_TOKEN_REFRESH);
  }

  public static UserTokenRefreshRequest newRequest() {
    UserTokenRefreshRequest request = new UserTokenRefreshRequest();
    return request;
  }

  @Override
  public Class<UserTokenResponse> getResponseClass() {
    return UserTokenResponse.class;
  }
}
