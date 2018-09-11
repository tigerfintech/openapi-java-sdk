package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Web 工具类
 */
public abstract class WebUtils {

  private static final String DEFAULT_CHARSET = TigerApiConstants.CHARSET_UTF8;
  private static final String METHOD_POST = "POST";

  private static class DefaultTrustManager implements X509TrustManager {

    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }
  }

  public static String doPost(String url, String contentType, String content, int connectTimeout,
      int readTimeout) throws IOException {
    HttpURLConnection conn = null;
    DataOutputStream out = null;
    try {
      conn = getConnection(new URL(url), METHOD_POST, contentType);
      conn.setConnectTimeout(connectTimeout);
      conn.setReadTimeout(readTimeout);
      conn.connect();

      out = new DataOutputStream(conn.getOutputStream());
      out.writeBytes(content);

      return getResponseAsString(conn);
    } catch (IOException e) {
      throw e;
    } finally {
      if (out != null) {
        out.close();
      }
      if (conn != null) {
        conn.disconnect();
      }
    }
  }

  private static HttpURLConnection getConnection(URL url, String method, String contentType) throws IOException {
    HttpURLConnection conn;
    if ("https".equals(url.getProtocol())) {
      SSLContext ctx;
      try {
        ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()},
            new SecureRandom());
      } catch (Exception e) {
        throw new IOException(e);
      }
      HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
      connHttps.setSSLSocketFactory(ctx.getSocketFactory());
      connHttps.setHostnameVerifier(new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
      conn = connHttps;
    } else {
      conn = (HttpURLConnection) url.openConnection();
    }

    conn.setRequestMethod(method);
    conn.setDoOutput(true);
    conn.setDoInput(true);
    conn.setUseCaches(false);

    conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html,application/json,application/xml");
    conn.setRequestProperty("User-Agent", "top-sdk-java");
    conn.setRequestProperty("Content-Type", contentType);
    return conn;
  }

  protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
    String charset = getResponseCharset(conn.getContentType());
    InputStream es = conn.getErrorStream();
    if (es == null) {
      return getStreamAsString(conn.getInputStream(), charset);
    } else {
      String msg = getStreamAsString(es, charset);
      if (StringUtils.isEmpty(msg)) {
        throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
      } else {
        return msg;
      }
    }
  }

  private static String getStreamAsString(InputStream stream, String charset) throws IOException {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
      StringWriter writer = new StringWriter();
      char[] chars = new char[256];
      int count = 0;
      while ((count = reader.read(chars)) > 0) {
        writer.write(chars, 0, count);
      }
      return writer.toString();
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  private static String getResponseCharset(String contentType) {
    String charset = DEFAULT_CHARSET;

    if (!StringUtils.isEmpty(contentType)) {
      String[] params = contentType.split(";");
      for (String param : params) {
        param = param.trim();
        if (param.startsWith("charset")) {
          String[] pair = param.split("=", 2);
          if (pair.length == 2) {
            if (!StringUtils.isEmpty(pair[1])) {
              charset = pair[1].trim();
            }
          }
          break;
        }
      }
    }

    return charset;
  }
}
