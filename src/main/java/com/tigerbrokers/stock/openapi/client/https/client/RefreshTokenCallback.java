package com.tigerbrokers.stock.openapi.client.https.client;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.user.item.UserTokenItem;

/**
 * @author bean
 * @date 2023/2/10 7:08 PM
 */
public interface RefreshTokenCallback {
  void tokenChange(ClientConfig clientConfig, String oldToken, UserTokenItem token);
}
