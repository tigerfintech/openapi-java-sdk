package com.tigerbrokers.stock.openapi.client.https.request.user;

import com.tigerbrokers.stock.openapi.client.https.domain.user.model.UserTradePasswordResetModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserTradePasswordResetResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by lijiawen on 2019/05/05.
 */
public class UserTradePasswordResetRequest extends TigerCommonRequest implements
    TigerRequest<UserTradePasswordResetResponse> {

  public UserTradePasswordResetRequest() {
    setApiMethodName(MethodName.USER_TRADE_PASSWORD_RESET);
  }

  public static UserTradePasswordResetRequest newRequest(String idNo, String tradePassword, String verifyCode) {
    UserTradePasswordResetRequest request = new UserTradePasswordResetRequest();
    UserTradePasswordResetModel model = new UserTradePasswordResetModel(idNo, tradePassword, verifyCode);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<UserTradePasswordResetResponse> getResponseClass() {
    return UserTradePasswordResetResponse.class;
  }
}
