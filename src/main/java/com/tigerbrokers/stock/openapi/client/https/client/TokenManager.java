package com.tigerbrokers.stock.openapi.client.https.client;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserTokenRefreshRequest;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserTokenResponse;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ConfigUtil;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author bean
 * @date 2023/2/10 6:03 PM
 */
public class TokenManager {
  private static TokenManager tokenManager = new TokenManager();

  private final long REFRESH_INTERVAL_MS = TimeUnit.DAYS.toMillis(1);
  private ScheduledThreadPoolExecutor executorService;
  private ClientConfig clientConfig;
  private final List<RefreshTokenCallback> callbackList = new ArrayList<>();
  private final RefreshTokenCallback defaultCallback = new DefaultRefreshTokenCallback();

  private TokenManager() {}

  public static TokenManager getInstance() {
    return tokenManager;
  }

  public void destroy() {
    if (executorService != null && !executorService.isShutdown()) {
      executorService.shutdown();
    }
    callbackList.clear();
  }

  public void init(ClientConfig config) {
    if (config == null || !config.isAutoRefreshToken) {
      return;
    }
    this.clientConfig = config;
    boolean result = ConfigUtil.loadTokenFile(clientConfig);
    long tokenCreateTime = 0;
    try {
      tokenCreateTime = ConfigUtil.getCreateTime(clientConfig.token);
    } catch (Throwable th) {
      // ignore
    }

    register(defaultCallback);
    if (result && tokenCreateTime > 0) {
      executorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
          Thread t = Executors.defaultThreadFactory().newThread(r);
          t.setDaemon(true);
          return t;
        }
      });

      long initialDelay = tokenCreateTime + REFRESH_INTERVAL_MS - System.currentTimeMillis();
      initialDelay = initialDelay < 0 ? 0 : initialDelay;
      executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
              refreshToken();
            }
          }, initialDelay,
          REFRESH_INTERVAL_MS, TimeUnit.SECONDS);
    }
  }

  public void register(RefreshTokenCallback callback) {
    if (callback != null) {
      if (!callbackList.contains(callback)) {
        callbackList.add(callback);
      }
    }
  }

  public void unregister(RefreshTokenCallback callback) {
    if (callback != null) {
      callbackList.remove(callback);
    }
  }

  public List<RefreshTokenCallback> getCallbackList() {
    return callbackList;
  }

  private void refreshToken() {
    long tokenCreateTime = 0;
    try {
      tokenCreateTime = ConfigUtil.getCreateTime(clientConfig.token);
    } catch (Throwable th) {
      // ignore
    }
    if (tokenCreateTime + REFRESH_INTERVAL_MS - System.currentTimeMillis() > 0) {
      ApiLogger.info("refreshToken last update time:{}, ignore",
          DateUtils.printDate(tokenCreateTime, clientConfig.timeZone));
      return;
    }

    UserTokenRefreshRequest request = new UserTokenRefreshRequest();
    int count = 5;
    String oldToken = clientConfig.token;
    do {
      try {
        UserTokenResponse response = TigerHttpClient.getInstance().execute(request);
        if (response.isSuccess()) {
          ApiLogger.info("refreshToken success. return:" + JSONObject.toJSONString(response.getUserToken()));
          for (RefreshTokenCallback callback : callbackList) {
            callback.tokenChange(clientConfig, oldToken, response.getUserToken());
          }
          break;
        }
      } catch (Throwable th) {
        ApiLogger.warn("refreshToken fail. " + th.getMessage());
      } finally {
        count--;
      }
    } while(count > 0);
  }
}
