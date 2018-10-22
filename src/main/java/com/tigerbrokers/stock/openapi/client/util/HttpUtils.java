package com.tigerbrokers.stock.openapi.client.util;

import java.net.SocketTimeoutException;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.CONTENT_TYPE_JSON;

/**
 * Web 工具类
 */
public abstract class HttpUtils {

  private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

  public static final int CONNECT_TIMEOUT = 10000;
  public static final int SOCKET_TIMEOUT = 10000;

  public static String post(String url, String params) {
    return post(url, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
  }

  public static String post(String url, String params, int connectTimeout, int socketTimeout) {
    try {
      Content content = Request.Post(url)
          .bodyString(params, ContentType.create(CONTENT_TYPE_JSON))
          .connectTimeout(connectTimeout).socketTimeout(socketTimeout)
          .execute().returnContent();
      if (content != null) {
        return IOUtils.toString(content.asStream(), content.getType().getCharset());
      }
    } catch (SocketTimeoutException e) {
      logger.error("client post request socket time out exception:", e);
    } catch (Exception e) {
      logger.error("client post request exception:", e);
    }
    return null;
  }
}
