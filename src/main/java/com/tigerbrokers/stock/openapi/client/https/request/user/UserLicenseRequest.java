package com.tigerbrokers.stock.openapi.client.https.request.user;

import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserLicenseResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by bean on 2022/12/13.
 */
public class UserLicenseRequest extends TigerCommonRequest implements TigerRequest<UserLicenseResponse> {

  public UserLicenseRequest() {
    setApiMethodName(MethodName.USER_LICENSE);
  }

  public static UserLicenseRequest newRequest() {
    UserLicenseRequest request = new UserLicenseRequest();
    return request;
  }

  @Override
  public Class<UserLicenseResponse> getResponseClass() {
    return UserLicenseResponse.class;
  }
}
