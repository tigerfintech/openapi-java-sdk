package com.tigerbrokers.stock.openapi.client.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web 工具类
 */
public class HttpUtils {

  private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

  public static final int CONNECT_TIMEOUT = 10000;
  public static final int SOCKET_TIMEOUT = 10000;

  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  public static OkHttpClient client = new OkHttpClient.Builder()
      .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .readTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
      .build();

  public static String post(String url, String json) {
    if (url == null || json == null) {
      return null;
    }
    try {
      RequestBody body = RequestBody.create(JSON, json);
      okhttp3.Request request = new okhttp3.Request.Builder()
          .url(url)
          .post(body)
          .build();

      Response response = client.newCall(request).execute();
      if (response != null && response.body() != null) {
        return response.body().string();
      }
    } catch (IOException e) {
      logger.error("HttpUtils execute io exception:{}", e.getMessage(), e);
    } catch (Exception e) {
      logger.error("HttpUtils execute exception:{}", e.getMessage(), e);
    }
    return null;
  }
}
