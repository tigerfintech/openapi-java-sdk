package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * Loopback callback receiver (RFC 8252).
 *
 * <p>Covers test-plan cases A-02..A-06, K-05, K-06. The callback is where the authorization
 * code lands, so most of these are security assertions rather than functional ones.</p>
 */
public class LoopbackReceiverTest {

  private static final Charset UTF_8 = Charset.forName("UTF-8");

  private static String get(String url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
    connection.setConnectTimeout(5000);
    connection.setReadTimeout(5000);
    InputStream in = connection.getResponseCode() < 400
        ? connection.getInputStream() : connection.getErrorStream();
    try {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      if (in != null) {
        byte[] chunk = new byte[1024];
        int n;
        while ((n = in.read(chunk)) > 0) {
          buffer.write(chunk, 0, n);
        }
      }
      return new String(buffer.toByteArray(), UTF_8);
    } finally {
      if (in != null) {
        in.close();
      }
    }
  }

  private static int statusOf(String url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
    connection.setConnectTimeout(5000);
    connection.setReadTimeout(5000);
    int status = connection.getResponseCode();
    connection.disconnect();
    return status;
  }

  // ---------------------------------------------------------- redirect uri

  /**
   * K-06: RFC 8252 §8.3 -- {@code localhost} depends on hosts resolution and can be pointed
   * somewhere else entirely.
   */
  @Test
  public void usesTheIpLiteralNotLocalhost() {
    try (LoopbackReceiver receiver = new LoopbackReceiver(0)) {
      assertTrue(receiver.getRedirectUri(),
          receiver.getRedirectUri().startsWith("http://127.0.0.1:"));
      assertFalse(receiver.getRedirectUri().contains("localhost"));
      assertTrue(receiver.getRedirectUri().endsWith("/callback"));
    }
  }

  /** K-05: the authorization code must not be exposed to the local network. */
  @Test
  public void portZeroGetsASystemAssignedPort() throws IOException {
    try (LoopbackReceiver receiver = new LoopbackReceiver(0)) {
      String uri = receiver.getRedirectUri();
      int port = Integer.parseInt(uri.substring("http://127.0.0.1:".length(), uri.indexOf("/callback")));
      assertTrue("expected an assigned port, got " + port, port > 0);
      // The listener has to be up already, before the URL is handed to the user
      assertEquals(200, statusOf(uri + "?code=x&state=y"));
    }
  }

  /** A fixed port is what a manually registered redirect_uri requires. */
  @Test
  public void fixedPortIsHonoured() throws IOException {
    int free;
    ServerSocket probe = new ServerSocket(0);
    try {
      free = probe.getLocalPort();
    } finally {
      probe.close();
    }
    try (LoopbackReceiver receiver = new LoopbackReceiver(free)) {
      assertTrue(receiver.getRedirectUri().contains(":" + free + "/"));
    }
  }

  /** A collision on a fixed port must say which port and what to do about it. */
  @Test
  public void takenFixedPortReportsThePortAndTheFix() throws IOException {
    // Must be bound to 127.0.0.1, not the wildcard: SO_REUSEADDR lets a loopback bind
    // succeed alongside a wildcard one, so a wildcard blocker would not collide
    ServerSocket blocker = new ServerSocket();
    blocker.bind(new java.net.InetSocketAddress("127.0.0.1", 0));
    int taken = blocker.getLocalPort();
    try {
      new LoopbackReceiver(taken).close();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.CALLBACK_FAILED, e.getCategory());
      assertTrue(e.getMessage(), e.getMessage().contains(String.valueOf(taken)));
      assertTrue(e.getMessage(), e.getMessage().contains("callbackPort"));
    } finally {
      blocker.close();
    }
  }

  // -------------------------------------------------------- callback capture

  @Test
  public void queryParametersAreCaptured() throws Exception {
    try (LoopbackReceiver receiver = new LoopbackReceiver(0)) {
      get(receiver.getRedirectUri() + "?code=the-code&state=the-state");
      Map<String, String> params = receiver.await(5, TimeUnit.SECONDS);
      assertEquals("the-code", params.get("code"));
      assertEquals("the-state", params.get("state"));
    }
  }

  @Test
  public void urlEncodedValuesAreDecoded() throws Exception {
    try (LoopbackReceiver receiver = new LoopbackReceiver(0)) {
      get(receiver.getRedirectUri() + "?code=a%2Bb%2Fc&state=s");
      assertEquals("a+b/c", receiver.await(5, TimeUnit.SECONDS).get("code"));
    }
  }

  /** A-04: the user denying is a normal outcome, not a crash. */
  @Test
  public void errorCallbackIsCaptured() throws Exception {
    try (LoopbackReceiver receiver = new LoopbackReceiver(0)) {
      get(receiver.getRedirectUri() + "?error=access_denied&error_description=nope");
      Map<String, String> params = receiver.await(5, TimeUnit.SECONDS);
      assertEquals("access_denied", params.get("error"));
      assertNull(params.get("code"));
    }
  }

  /** A-05. */
  @Test
  public void timeoutIsReportedAsCallbackFailed() {
    try (LoopbackReceiver receiver = new LoopbackReceiver(0)) {
      receiver.await(200, TimeUnit.MILLISECONDS);
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.CALLBACK_FAILED, e.getCategory());
      assertTrue(e.getMessage(), e.getMessage().contains("timed out"));
    }
  }

  @Test
  public void otherPathsAreNotTheCallback() throws Exception {
    try (LoopbackReceiver receiver = new LoopbackReceiver(0)) {
      String base = receiver.getRedirectUri().replace("/callback", "");
      assertEquals(404, statusOf(base + "/something-else"));
    }
  }

  /** A second request must not overwrite a completed result. */
  @Test
  public void firstCallbackWins() throws Exception {
    try (LoopbackReceiver receiver = new LoopbackReceiver(0)) {
      get(receiver.getRedirectUri() + "?code=first&state=s");
      assertEquals("first", receiver.await(5, TimeUnit.SECONDS).get("code"));

      get(receiver.getRedirectUri() + "?code=second&state=s");
      assertEquals("first", receiver.await(5, TimeUnit.SECONDS).get("code"));
    }
  }

  @Test
  public void awaitReturnsACopy() throws Exception {
    try (LoopbackReceiver receiver = new LoopbackReceiver(0)) {
      get(receiver.getRedirectUri() + "?code=x&state=y");
      Map<String, String> params = receiver.await(5, TimeUnit.SECONDS);
      params.put("code", "mutated");
      assertEquals("x", receiver.await(5, TimeUnit.SECONDS).get("code"));
    }
  }

  @Test
  public void closeIsIdempotentAndReleasesThePort() throws Exception {
    LoopbackReceiver receiver = new LoopbackReceiver(0);
    String uri = receiver.getRedirectUri();
    int port = Integer.parseInt(uri.substring("http://127.0.0.1:".length(), uri.indexOf("/callback")));
    receiver.close();
    receiver.close();

    ServerSocket probe = new ServerSocket();
    try {
      probe.setReuseAddress(true);
      probe.bind(new java.net.InetSocketAddress("127.0.0.1", port));
    } finally {
      probe.close();
    }
  }

  // ----------------------------------------------------------- callback page

  private String page(String query) throws Exception {
    return pageWithLanguage(query, null);
  }

  private String pageWithLanguage(String query, String acceptLanguage) throws Exception {
    try (LoopbackReceiver receiver = new LoopbackReceiver(0)) {
      HttpURLConnection connection =
          (HttpURLConnection) new URL(receiver.getRedirectUri() + query).openConnection();
      if (acceptLanguage != null) {
        connection.setRequestProperty("Accept-Language", acceptLanguage);
      }
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);
      InputStream in = connection.getInputStream();
      try {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] chunk = new byte[1024];
        int n;
        while ((n = in.read(chunk)) > 0) {
          buffer.write(chunk, 0, n);
        }
        return new String(buffer.toByteArray(), UTF_8);
      } finally {
        in.close();
      }
    }
  }

  /**
   * A-06: the page renders in the user's browser -- it must leak nothing.
   *
   * <p>It also has to report the <b>right</b> outcome: {@code access_denied} is the user
   * pressing "decline", not a failure.
   */
  @Test
  public void successPage() throws Exception {
    String body = page("?code=SECRET-CODE&state=SECRET-STATE");

    assertTrue(body, body.contains("Authorization successful"));
    assertTrue("green tick", body.contains("#22c55e"));
  }

  /**
   * The user chose to decline; calling that a failure describes the wrong thing and nudges
   * them to redo something they meant to refuse.
   */
  @Test
  public void declinedPageIsNotReportedAsAFailure() throws Exception {
    String body = page("?error=access_denied&state=s");

    assertTrue(body, body.contains("Authorization cancelled"));
    assertFalse(body, body.toLowerCase().contains("failed"));
    assertFalse("must not push a retry", body.toLowerCase().contains("try again"));
  }

  /** Grey dash, not a red cross -- declining is a normal outcome. */
  @Test
  public void declinedPageUsesANeutralIcon() throws Exception {
    String body = page("?error=access_denied&state=s");

    assertTrue("neutral grey", body.contains("#9ca3af"));
    assertFalse("must not be coloured as an error", body.contains("#ef4444"));
  }

  @Test
  public void realErrorPage() throws Exception {
    String body = page("?error=server_error&state=s");

    assertTrue(body, body.contains("Authorization failed"));
    assertTrue("red cross", body.contains("#ef4444"));
  }

  /** A callback with neither {@code code} nor {@code error} is malformed, not a decline. */
  @Test
  public void missingCodeWithoutAnErrorIsAFailure() throws Exception {
    String body = page("?state=s");

    assertTrue(body, body.contains("Authorization failed"));
    assertTrue(body.contains("#ef4444"));
  }

  /**
   * <b>Fixed English, on purpose.</b> The SDK's exceptions, logs and error messages are all
   * English; a callback page that followed the browser would be the one localised surface in
   * an otherwise English toolkit. It is also what gets screenshotted into bug reports, where
   * a fixed wording is easier to search for and quote.
   */
  @Test
  public void pageIsEnglishRegardlessOfBrowserLanguage() throws Exception {
    for (String header : new String[] {"zh-CN,zh;q=0.9", "zh-TW", "zh-HK", "ja", "fr-FR", ""}) {
      String body = pageWithLanguage("?error=access_denied&state=s", header);
      assertTrue(header, body.contains("Authorization cancelled"));
    }
  }

  /** Catches a stray localised string being reintroduced into any of the three. */
  @Test
  public void noPageContainsNonAsciiCopy() throws Exception {
    for (String query : new String[] {"?code=x&state=y", "?error=access_denied",
        "?error=server_error"}) {
      String body = pageWithLanguage(query, "zh-CN");
      String heading = body.replaceAll(".*<h2[^>]*>([^<]*)</h2>.*", "$1");
      String detail = body.replaceAll(".*<p [^>]*>([^<]*)</p>.*", "$1");
      assertTrue(query + " -> " + heading, heading.matches("\\p{ASCII}*"));
      assertTrue(query + " -> " + detail, detail.matches("\\p{ASCII}*"));
    }
  }

  @Test
  public void noPageLeaksAnyParameter() throws Exception {
    String[] secrets = {"SECRET-CODE", "SECRET-STATE", "server_error", "access_denied"};
    String[] pages = {
        page("?code=SECRET-CODE&state=SECRET-STATE"),
        page("?error=access_denied&state=SECRET-STATE"),
        page("?error=server_error&state=SECRET-STATE")};

    for (String body : pages) {
      for (String secret : secrets) {
        assertFalse(secret, body.contains(secret));
      }
    }
  }

  /** It must render identically with no network; the icons are inline SVG. */
  @Test
  public void pageLoadsNoExternalResources() throws Exception {
    for (String query : new String[] {"?code=x&state=y", "?error=access_denied",
        "?error=server_error"}) {
      String body = page(query);
      assertTrue(body.contains("<svg"));
      for (String tag : new String[] {"<img", "<script", "<link", "http://", "https://"}) {
        assertFalse("callback page must not fetch " + tag + " in " + query, body.contains(tag));
      }
    }
  }

  /** The three outcomes differ only in icon and copy -- same geometry, same shell. */
  @Test
  public void pagesShareOneLayout() throws Exception {
    for (String query : new String[] {"?code=x&state=y", "?error=access_denied",
        "?error=server_error"}) {
      String body = page(query);
      assertTrue("same icon geometry", body.contains("viewBox='0 0 64 64'"));
      assertTrue("same content shell", body.contains("max-width:420px"));
      assertTrue(body.contains("<h2"));
      assertTrue(body.contains("<p "));
    }
  }
}
