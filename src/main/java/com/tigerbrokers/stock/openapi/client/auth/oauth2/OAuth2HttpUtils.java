package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * HTTP operations for OAuth2 endpoints.
 *
 * <p>Supports form-encoded requests and preserves OAuth2 error responses returned with non-success
 * HTTP status codes.</p>
 */
final class OAuth2HttpUtils {

  private static final MediaType JSON_TYPE =
      MediaType.parse("application/json; charset=utf-8");

  /** Calls in the authorization flow are interactive, so the timeouts are generous. */
  private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .build();

  private OAuth2HttpUtils() {
  }

  static JSONObject getJson(String url) {
    Request request = new Request.Builder().url(url).get().build();
    return execute(request, url);
  }

  static JSONObject postJson(String url, Object payload) {
    RequestBody body = RequestBody.create(JSON_TYPE, JSON.toJSONString(payload));
    Request request = new Request.Builder().url(url).post(body).build();
    return execute(request, url);
  }

  static JSONObject postForm(String url, Map<String, String> form) {
    FormBody.Builder builder = new FormBody.Builder();
    for (Map.Entry<String, String> e : form.entrySet()) {
      if (e.getValue() != null) {
        builder.add(e.getKey(), e.getValue());
      }
    }
    Request request = new Request.Builder().url(url).post(builder.build()).build();
    return execute(request, url);
  }

  /**
   * Posts a form and returns the HTTP status without requiring a response body.
   *
   * <p>RFC 7009 token revocation may return an empty successful response.</p>
   *
   * @return HTTP status code
   * @throws OAuth2Exception on a connection failure
   */
  static int postFormForStatus(String url, Map<String, String> form) {
    FormBody.Builder builder = new FormBody.Builder();
    for (Map.Entry<String, String> e : form.entrySet()) {
      if (e.getValue() != null) {
        builder.add(e.getKey(), e.getValue());
      }
    }
    Request request = new Request.Builder().url(url).post(builder.build()).build();
    Response response = null;
    try {
      response = CLIENT.newCall(request).execute();
      return response.code();
    } catch (IOException e) {
      throw new OAuth2Exception(OAuth2Exception.Category.TOKEN_REQUEST_FAILED,
          "request fail: " + url, e);
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }

  /**
   * Sends the request and parses the JSON response.
   *
   * <p>A 4xx also returns its body instead of throwing -- OAuth2 errors
   * ({@code invalid_grant}, {@code authorization_pending} and friends) live in the 4xx body,
   * and the caller needs to read the {@code error} field to decide whether to keep waiting,
   * refresh, or re-authorize. Only a connection failure or a non-JSON response throws.</p>
   */
  private static JSONObject execute(Request request, String url) {
    Response response = null;
    try {
      response = CLIENT.newCall(request).execute();
      ResponseBody body = response.body();
      String text = body == null ? "" : body.string();
      if (text.isEmpty()) {
        throw new OAuth2Exception(OAuth2Exception.Category.TOKEN_REQUEST_FAILED,
            "empty response from " + url + ", http status " + response.code());
      }
      JSONObject json = JSON.parseObject(text);
      if (json == null) {
        throw new OAuth2Exception(OAuth2Exception.Category.TOKEN_REQUEST_FAILED,
            "non-JSON response from " + url + ", http status " + response.code());
      }
      return json;
    } catch (OAuth2Exception e) {
      throw e;
    } catch (IOException e) {
      throw new OAuth2Exception(OAuth2Exception.Category.METADATA_UNAVAILABLE,
          "request fail: " + url, e);
    } catch (RuntimeException e) {
      throw new OAuth2Exception(OAuth2Exception.Category.TOKEN_REQUEST_FAILED,
          "parse response fail: " + url, e);
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }
}
