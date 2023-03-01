package com.tigerbrokers.stock.openapi.client.https.client;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.user.item.UserTokenItem;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ConfigFileUtil;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TOKEN_FILENAME;

/**
 * @author bean
 * @date 2023/2/10 7:28 PM
 */
public class DefaultRefreshTokenCallback implements RefreshTokenCallback {

  @Override
  public void tokenChange(ClientConfig clientConfig, String oldToken, UserTokenItem tokenItem) {
    try {
      ApiLogger.info("tokenChange oldToken:{}, newTokenInfo:{}",
          oldToken, JSONObject.toJSONString(tokenItem));
      clientConfig.token = tokenItem.getToken();
      ConfigFileUtil.updateTokenFile(clientConfig.configFilePath, TOKEN_FILENAME, tokenItem.getToken());
    } catch (Throwable th) {
      ApiLogger.error("tokenChange process fail", th);
    }
  }
}
