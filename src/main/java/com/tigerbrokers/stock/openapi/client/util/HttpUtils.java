package com.tigerbrokers.stock.openapi.client.util;

import java.net.ConnectException;
import java.util.HashSet;
import java.util.Set;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Web 工具类
 */
public class HttpUtils {

  public static final int CONNECT_TIMEOUT = 5000;
  public static final int SOCKET_TIMEOUT = 5000;

  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private static ConnectionPool connectionPool = new ConnectionPool(5, 60, TimeUnit.SECONDS);

  private static Set<Class<? extends Exception>> retryExceptionSet =
      new HashSet<Class<? extends Exception>>() {{
        add(IOException.class);
        add(ConnectException.class);
  }};

  public static OkHttpClient client = new OkHttpClient.Builder()
      .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
      .readTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
      .connectionPool(connectionPool)
      .retryOnConnectionFailure(true)
      .build();

  public static String post(String url, String json) throws Exception {
    return post(url, json, 0);
  }
  public static String post(String url, String json, int retryCount) throws Exception {
    if (url == null || json == null) {
      throw new RuntimeException("request url or json param cannot be null");
    }
    RequestBody body = RequestBody.create(JSON, json);
    okhttp3.Request request = new okhttp3.Request.Builder()
        .url(url)
        .post(body)
        .build();
    int requstCount = 0;
    String result = null;
    boolean needRetry = retryCount > 0;
    do {
      requstCount++;
      try {
        Response response = client.newCall(request).execute();
        if (response == null) {
          ApiLogger.error("HttpUtils response is null");
          throw new RuntimeException("http response is null");
        }
        if (response.body() == null) {
          ApiLogger.error("HttpUtils response body is null");
          throw new RuntimeException("http response body is null");
        }
        result = response.body().string();
      } catch (Exception e) {
        ApiLogger.info("HttpUtils execute[{}] times, fail:{}", requstCount, e.getMessage());
        if (requstCount > retryCount || !retryExceptionSet.contains(e.getClass())) {
          needRetry = false;
          throw e;
        }
      } finally {
        if (result != null && result.indexOf("internal_error:A system error occurred, please try again later") < 0) {
          return result;
        } else {
          needRetry &= requstCount <= retryCount;
          if (needRetry) {
            requestWaitInterval(requstCount);
          }
        }
      }
    } while(needRetry);
    return result;
  }

  private static void requestWaitInterval(int requstCount) {
    try {
      // 200ms, 400ms, 800ms
      TimeUnit.MILLISECONDS.sleep((int)Math.pow(2, requstCount) * 100);
    } catch(InterruptedException ie) {
      // ignore
    }
  }

  public static String get(String url) throws Exception {
    if (url == null) {
      throw new RuntimeException("request url param cannot be null");
    }
    try {
      okhttp3.Request request = new okhttp3.Request.Builder()
          .url(url)
          .build();

      Response response = client.newCall(request).execute();
      if (response == null) {
        ApiLogger.debug("HttpUtils response is null, url:{}", url);
        throw new RuntimeException("http response is null");
      }
      if(response.body() == null) {
        ApiLogger.debug("HttpUtils response body is null, url:{}", url);
        throw new RuntimeException("http response body is null");
      }
      return response.body().string();
    } catch (IOException e) {
      ApiLogger.error("HttpUtils execute io exception:{}", e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      ApiLogger.error("HttpUtils execute exception:{}", e.getMessage(), e);
      throw e;
    }
  }
}
