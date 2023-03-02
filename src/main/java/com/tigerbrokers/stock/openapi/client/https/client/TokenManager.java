package com.tigerbrokers.stock.openapi.client.https.client;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.request.user.UserTokenRefreshRequest;
import com.tigerbrokers.stock.openapi.client.https.response.user.UserTokenResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ConfigFileUtil;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.watch.FileWatchedListener;
import com.tigerbrokers.stock.openapi.client.util.watch.FileWatchedService;
import com.tigerbrokers.stock.openapi.client.util.watch.TokenFileWatched;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TOKEN_FILENAME;
import static com.tigerbrokers.stock.openapi.client.util.ConfigFileUtil.TOKEN_FILE_TOKEN;

/**
 * @author bean
 * @date 2023/2/10 6:03 PM
 */
public class TokenManager {
  private static final TokenManager tokenManager = new TokenManager();

  // refresh every 5 days by default
  private final int defaultRefreshIntervalDays = 5;
  private long refreshIntervalMs = TimeUnit.DAYS.toMillis(defaultRefreshIntervalDays);
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
    if (config == null) {
      return;
    }
    this.clientConfig = config;
    loadTokenFile(config);
    addTokenFileWatch(config);

    if (!config.isAutoRefreshToken) {
      return;
    }
    register(defaultCallback);

    executorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        Thread t = Executors.defaultThreadFactory().newThread(r);
        t.setDaemon(true);
        return t;
      }
    });

    if (config.refreshTokenIntervalDays > 0) {
      refreshIntervalMs = TimeUnit.DAYS.toMillis(config.refreshTokenIntervalDays);
    }
    long tokenCreateTime = ConfigFileUtil.tryGetCreateTime(clientConfig.token);
    long initialDelay = tokenCreateTime + refreshIntervalMs - System.currentTimeMillis();
    if (initialDelay <= 0) {
      refreshToken();
      tokenCreateTime = ConfigFileUtil.tryGetCreateTime(clientConfig.token);
      initialDelay = tokenCreateTime + refreshIntervalMs - System.currentTimeMillis();
    }
    initialDelay = getDelayTime(clientConfig.refreshTokenTime, initialDelay);

    executorService.scheduleWithFixedDelay(new Runnable() {
      @Override
      public void run() {
        refreshToken();
      }
    }, initialDelay, refreshIntervalMs, TimeUnit.MILLISECONDS);
    ApiLogger.info("init auto refresh token task success");
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
    if (StringUtils.isEmpty(clientConfig.token) && !loadTokenFile(clientConfig)) {
      return;
    }
    long tokenCreateTime = ConfigFileUtil.tryGetCreateTime(clientConfig.token);
    if (tokenCreateTime == 0) {
      ApiLogger.warn("local token is invalid:{}, refreshToken ignore", clientConfig.token);
      return;
    }
    if (tokenCreateTime + refreshIntervalMs - System.currentTimeMillis() > 0) {
      ApiLogger.info("refreshToken last update time:{}, ignore", DateUtils.printDateTime(
          tokenCreateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), clientConfig.timeZone));
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
        TimeUnit.SECONDS.sleep(5);
      } catch (Throwable th) {
        ApiLogger.warn("refreshToken fail. " + th.getMessage());
      } finally {
        count--;
      }
    } while(count > 0);
  }

  public boolean loadTokenFile(ClientConfig clientConfig) {
    if (!ConfigFileUtil.checkFile(clientConfig.configFilePath, TOKEN_FILENAME, false)) {
      return false;
    }
    Path tokenFilePath = Paths.get(clientConfig.configFilePath.trim(), TOKEN_FILENAME);
    Map<String, String> dataMap = ConfigFileUtil.readPropertiesFile(tokenFilePath);
    String token = dataMap.get(TOKEN_FILE_TOKEN);
    if (StringUtils.isEmpty(token)) {
      return false;
    }
    clientConfig.token = token;
    return true;
  }

  /**
   * Update the hour, minute, and second for the specified timestamp
   * @param baseTimestamp the specified timestamp
   * @param time formate:HH:mm:ss, 16:30:00 etc
   * @return
   */
  private long getTimeInMillis(long baseTimestamp, String time) {
    if (StringUtils.isEmpty(time)) {
      return -1;
    }
    TimeZoneId timeZoneId = ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone();
    Long timestamp = DateUtils.getTimestamp(
        DateUtils.printDate(baseTimestamp, timeZoneId) + " " + time, timeZoneId);
    return timestamp == null ? -1 : timestamp;
  }

  /**
   * get the delay time for refresh token
   * @param time formate:HH:mm:ss, 16:30:00 etc
   * @return
   */
  private long getDelayTime(String time, long initialDelay) {
    initialDelay = initialDelay <= 0 ? 0 : initialDelay;
    long baseTimestamp = System.currentTimeMillis();
    long refreshTimestamp = getTimeInMillis(baseTimestamp, time);
    if (refreshTimestamp < 0) {
      return initialDelay;
    }

    if (initialDelay > 0) {
      baseTimestamp += initialDelay;
      refreshTimestamp = getTimeInMillis(baseTimestamp, time);
    }
    long delayTime = refreshTimestamp - baseTimestamp + initialDelay;
    if (delayTime < 0) {
      delayTime += TimeUnit.DAYS.toMillis(1);
    }
    return delayTime;
  }

  public void addTokenFileWatch(ClientConfig config) {
    try {
      if (null == config || StringUtils.isEmpty(config.configFilePath)) {
        return;
      }
      // if token file exists, add listener
      if (ConfigFileUtil.checkFile(config.configFilePath, TOKEN_FILENAME, false)) {
        Path configFilePath = Paths.get(config.configFilePath);
        FileWatchedListener tokenFileListener = new TokenFileWatched(config);
        FileWatchedService fileWatchedService = new FileWatchedService(configFilePath, tokenFileListener);
        new Thread() {
          @Override
          public void run() {
            fileWatchedService.watch();
          }
        }.start();

        ApiLogger.info("addTokenFileWatch success.");
      }
    } catch (Exception e) {
      ApiLogger.error("addTokenFileWatch fail.", e);
    }
  }
}
