package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.EnumMap;
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
 * clientId the port must match the redirect_uri the user registered by hand, which the SDK
 * cannot know -- hence the agreed default of 18888.</p>
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
   * these glyphs are missing or ugly in plenty of default fonts.
   *
   * <p>All three share one geometry (64x64, r=29 circle) so the pages line up when compared.</p>
   */
  private static String icon(String color, String path) {
    return "<svg width='64' height='64' viewBox='0 0 64 64' aria-hidden='true'>"
        + "<circle cx='32' cy='32' r='29' fill='none' stroke='" + color + "' stroke-width='4'/>"
        + "<path d='" + path + "' fill='none' stroke='" + color + "' stroke-width='5'"
        + " stroke-linecap='round' stroke-linejoin='round'/></svg>";
  }

  /** Green tick. */
  private static final String ICON_SUCCESS = icon("#22c55e", "M19 33l9.5 9.5L45 22");

  /**
   * Grey dash. Deliberately <b>not</b> a red cross: the user chose to decline, which is a normal
   * outcome rather than an error, and colouring it like a failure misreports it.
   */
  private static final String ICON_CANCELLED = icon("#9ca3af", "M20 32h24");

  /** Red cross, for outcomes that really are failures. */
  private static final String ICON_FAILED = icon("#ef4444", "M22 22l20 20M42 22L22 42");

  /** The three outcomes a callback can carry. */
  private enum Outcome {
    SUCCESS,
    CANCELLED,
    FAILED
  }

  /**
   * Page copy, keyed by outcome. <b>English only, deliberately.</b>
   *
   * <p>The SDK's exceptions, log lines and error messages are all English; a callback page that
   * switched to the browser's language would be the one localised surface in an otherwise English
   * toolkit. It is also the page a developer screenshots into a bug report, where a fixed wording
   * is easier to search for and to quote.</p>
   *
   * <p>Kept byte-identical to the Python SDK's table -- same product surface, must not drift.</p>
   */
  private static final String TITLE = "Tiger OpenAPI";

  private static final Map<Outcome, String[]> TEXT = buildText();

  private static Map<Outcome, String[]> buildText() {
    Map<Outcome, String[]> text = new EnumMap<>(Outcome.class);
    text.put(Outcome.SUCCESS, new String[] {
        "Authorization successful",
        "You can close this page and return to the application."});
    text.put(Outcome.CANCELLED, new String[] {
        "Authorization cancelled",
        "You declined this authorization. You can close this page."});
    text.put(Outcome.FAILED, new String[] {
        "Authorization failed",
        "Please return to the application and try again."});
    return text;
  }

  /**
   * Classifies the callback.
   *
   * <p>{@code access_denied} is separated from every other error on purpose: it means the user
   * pressed "decline". Reporting their own choice as a failure -- and telling them to try again
   * -- describes the wrong thing and nudges them to redo something they meant to refuse.</p>
   */
  private static Outcome outcomeOf(Map<String, String> params) {
    if (params.containsKey("code")) {
      return Outcome.SUCCESS;
    }
    return "access_denied".equals(params.get("error")) ? Outcome.CANCELLED : Outcome.FAILED;
  }

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
        Map<String, String> params;
        try {
          synchronized (LoopbackReceiver.this.params) {
            if (latch.getCount() > 0) {
              LoopbackReceiver.this.params.putAll(parseQuery(exchange.getRequestURI().getRawQuery()));
            }
            params = new HashMap<>(LoopbackReceiver.this.params);
          }
          respond(exchange, params);
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
  /**
   * Renders the outcome page.
   *
   * <p><b>States the outcome and nothing else.</b> No code, state, error value or description is
   * displayed: the browser keeps the address bar and history, and the page may be screenshotted
   * or read by an extension. The authorization code is single-use, but leaking it before it is
   * redeemed lets someone else exchange it first.</p>
   *
   * <p>The caller still gets the full params -- only the <i>page</i> is redacted.</p>
   */
  private static void respond(HttpExchange exchange, Map<String, String> params)
      throws IOException {
    Outcome outcome = outcomeOf(params);
    String[] copy = TEXT.get(outcome);
    String icon = outcome == Outcome.SUCCESS ? ICON_SUCCESS
        : outcome == Outcome.CANCELLED ? ICON_CANCELLED : ICON_FAILED;

    String html = "<!DOCTYPE html><html><head><meta charset='utf-8'>"
        + "<meta name='viewport' content='width=device-width,initial-scale=1'>"
        + "<title>" + TITLE + "</title></head>"
        + "<body style=\"margin:0;min-height:100vh;display:flex;align-items:center;"
        + "justify-content:center;"
        + "font-family:-apple-system,BlinkMacSystemFont,'PingFang SC','Microsoft YaHei',"
        + "sans-serif;background:#fafafa;color:#1f2328\">"
        + "<main style='text-align:center;padding:48px 24px;max-width:420px'>"
        + icon
        + "<h2 style='margin:24px 0 8px;font-size:20px;font-weight:600'>" + copy[0] + "</h2>"
        + "<p style='margin:0;font-size:14px;line-height:1.6;color:#6b7280'>" + copy[1] + "</p>"
        + "</main></body></html>";

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
