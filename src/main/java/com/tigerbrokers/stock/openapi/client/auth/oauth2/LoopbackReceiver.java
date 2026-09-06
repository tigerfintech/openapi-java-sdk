package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * The local callback receiver (RFC 8252 loopback).
 *
 * <p>Binds a port on {@code 127.0.0.1} and closes after receiving a single callback.</p>
 *
 * <p>With dynamic registration the port is 0, so the system assigns one and it is not
 * persisted -- registration sends {@code http://127.0.0.1/callback} as the redirect_uri, and
 * per RFC 8252 §7.3 the AS ignores the port when matching a loopback URI. With a manual
 * clientId, the fixed port registered in the console must be used.</p>
 *
 * <p>It binds only {@code 127.0.0.1}, not {@code 0.0.0.0}: the authorization code must not be
 * exposed to the local network. It uses the IP literal rather than {@code localhost}
 * (RFC 8252 §8.3) -- the latter depends on hosts resolution and can be redirected elsewhere,
 * and in testing the AS only waives port matching for the IP literal.</p>
 *
 * <p>It must be started <b>before</b> the authorization URL is handed to the user, otherwise
 * there is a race: a fast user can finish approving before the listener is up, and the
 * browser redirect then arrives at a port with nobody listening.</p>
 */
class LoopbackReceiver implements AutoCloseable {

  private static final Charset UTF_8 = Charset.forName("UTF-8");
  private static final String CALLBACK_PATH = "/callback";

  /**
   * Inline SVG rather than an image, an icon font or a unicode glyph: the page has to render
   * the same everywhere with no network access -- a callback page must not fetch anything, and
   * the check glyph is missing or ugly in plenty of default fonts.
   */
  private static final String CHECK_MARK =
      "<svg width='64' height='64' viewBox='0 0 64 64' aria-hidden='true'>"
          + "<circle cx='32' cy='32' r='29' fill='none' stroke='#22c55e' stroke-width='4'/>"
          + "<path d='M19 33l9.5 9.5L45 22' fill='none' stroke='#22c55e' stroke-width='5'"
          + " stroke-linecap='round' stroke-linejoin='round'/></svg>";

  private final HttpServer server;
  private final CountDownLatch latch = new CountDownLatch(1);
  private final Map<String, String> params = new HashMap<>();

  /**
   * @param port the port to bind; 0 means let the system assign a free one
   */
  LoopbackReceiver(int port) {
    try {
      server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
    } catch (IOException e) {
      // Only a fixed port can be taken, and then we must say which port and what to do about
      // it; a system-assigned port never collides
      String hint = port == 0 ? ""
          : " (port " + port + " is in use; free it or set another callbackPort"
              + " matching the redirect_uri registered for your client)";
      throw new OAuth2Exception(OAuth2Exception.Category.CALLBACK_FAILED,
          "cannot bind loopback callback server on 127.0.0.1:" + port + hint, e);
    }
    server.createContext(CALLBACK_PATH, new HttpHandler() {
      @Override
      public void handle(HttpExchange exchange) throws IOException {
        boolean success;
        try {
          synchronized (params) {
            if (latch.getCount() > 0) {
              params.putAll(parseQuery(exchange.getRequestURI().getRawQuery()));
            }
            success = params.containsKey("code");
          }
          respond(exchange, success);
        } finally {
          exchange.close();
          latch.countDown();
        }
      }
    });
    server.setExecutor(null);
    server.start();
  }

  String getRedirectUri() {
    return "http://127.0.0.1:" + server.getAddress().getPort() + CALLBACK_PATH;
  }

  /**
   * Waits for the browser callback.
   *
   * @return the query parameters carried by the callback
   * @throws OAuth2Exception on timeout
   */
  Map<String, String> await(long timeout, TimeUnit unit) {
    try {
      if (!latch.await(timeout, unit)) {
        throw new OAuth2Exception(OAuth2Exception.Category.CALLBACK_FAILED,
            "waiting for authorization callback timed out");
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new OAuth2Exception(OAuth2Exception.Category.CALLBACK_FAILED,
          "interrupted while waiting for authorization callback", e);
    }
    synchronized (params) {
      return new HashMap<>(params);
    }
  }

  @Override
  public void close() {
    try {
      server.stop(0);
    } catch (Exception e) {
      ApiLogger.debug("stop loopback server fail:{}", e.getMessage());
    }
  }

  /**
   * The callback page states the outcome only and displays no parameters.
   *
   * <p>The browser address bar and history keep a trace, and the page may be screenshotted or
   * read by an extension. The authorization code is single-use, but leaking it before it is
   * redeemed lets someone else exchange it for a token first.</p>
   */
  private static void respond(HttpExchange exchange, boolean success) throws IOException {
    String message = success
        ? CHECK_MARK + "<h2>Authentication successful</h2>"
            + "<p>You can close this page and return to the application.</p>"
        : "<h2>Authentication failed</h2>"
            + "<p>Please return to the application and try again.</p>";
    String html = "<html><head><meta charset='utf-8'><title>Tiger OpenAPI</title></head>"
        + "<body style=\"font-family:-apple-system,'PingFang SC',sans-serif;"
        + "padding:64px;text-align:center\">" + message + "</body></html>";
    byte[] bytes = html.getBytes(UTF_8);
    exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
    exchange.sendResponseHeaders(200, bytes.length);
    OutputStream out = exchange.getResponseBody();
    out.write(bytes);
    out.flush();
  }

  private static Map<String, String> parseQuery(String rawQuery) {
    Map<String, String> result = new HashMap<>();
    if (rawQuery == null || rawQuery.isEmpty()) {
      return result;
    }
    for (String pair : rawQuery.split("&")) {
      int idx = pair.indexOf('=');
      if (idx <= 0) {
        continue;
      }
      try {
        String key = java.net.URLDecoder.decode(pair.substring(0, idx), "UTF-8");
        String value = java.net.URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
        result.put(key, value);
      } catch (Exception e) {
        ApiLogger.debug("decode callback param fail:{}", e.getMessage());
      }
    }
    return result;
  }
}
