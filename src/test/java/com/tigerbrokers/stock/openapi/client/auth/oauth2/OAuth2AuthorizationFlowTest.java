package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Authorization-code flow: the {@code state} check and callback handling.
 *
 * <p>Covers test-plan cases A-01..A-06, A-09, A-28..A-32.
 *
 * <p>{@code state} is the CSRF defence of the whole browser flow -- it is the only thing tying
 * the callback back to the authorization <b>this process</b> started. The check was implemented
 * in both SDKs but had <b>no test on either side</b>; these fill that gap, mirroring
 * {@code test_oauth2_authorization_flow.py} case for case.
 *
 * <p>The flow is driven end to end: {@code login()} runs on a worker thread, the authorization
 * URL is intercepted, and the callback is delivered by hand to the real loopback port.
 */
public class OAuth2AuthorizationFlowTest {

  private static final Charset UTF_8 = Charset.forName("UTF-8");

  private FakeAuthorizationServer server;
  private Path home;

  /** The authorization request's query params, captured from the URL handed to the browser. */
  private Map<String, String> seenParams;

  @Before
  public void setUp() throws IOException {
    server = new FakeAuthorizationServer();
    home = Files.createTempDirectory("tigeropen-oauth2-authflow-");
  }

  @After
  public void tearDown() {
    server.close();
    deleteRecursively(home.toFile());
  }

  private static void deleteRecursively(File file) {
    File[] children = file.listFiles();
    if (children != null) {
      for (File child : children) {
        deleteRecursively(child);
      }
    }
    file.delete();
  }

  private OAuth2SessionManager session() {
    return OAuth2SessionManager.builder()
        .issuer(server.getIssuer())
        .clientId("cid")
        .home(home.toString())
        .authorizeTimeoutMinutes(1)
        .build();
  }

  /** Outcome of a driven login: exactly one of the two is set. */
  private static final class Outcome {
    OAuth2Token token;
    RuntimeException error;
  }

  /**
   * Runs {@code login()} on a worker thread and delivers the callback that {@code respond}
   * builds from the authorization request's params.
   */
  private Outcome runLogin(Function<Map<String, String>, String> respond) throws Exception {
    final Outcome outcome = new Outcome();
    final AtomicReference<Map<String, String>> captured = new AtomicReference<>();

    Thread worker =
        new Thread(
            () -> {
              try {
                outcome.token = session().login(url -> {
                  Map<String, String> params = queryOf(url);
                  captured.set(params);
                  String query = respond.apply(params);
                  new Thread(() -> deliver(params.get("redirect_uri"), query)).start();
                });
              } catch (RuntimeException e) {
                outcome.error = e;
              }
            });
    worker.start();
    worker.join(20_000);
    assertFalse("login() did not finish", worker.isAlive());

    seenParams = captured.get() == null ? new HashMap<>() : captured.get();
    return outcome;
  }

  private static Map<String, String> queryOf(String url) {
    Map<String, String> params = new LinkedHashMap<>();
    String query = url.substring(url.indexOf('?') + 1);
    for (String pair : query.split("&")) {
      int eq = pair.indexOf('=');
      if (eq <= 0) {
        continue;
      }
      try {
        params.put(
            URLDecoder.decode(pair.substring(0, eq), "UTF-8"),
            URLDecoder.decode(pair.substring(eq + 1), "UTF-8"));
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }
    return params;
  }

  private static void deliver(String redirectUri, String query) {
    try {
      HttpURLConnection connection =
          (HttpURLConnection) new URL(redirectUri + "?" + query).openConnection();
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);
      InputStream in =
          connection.getResponseCode() < 400
              ? connection.getInputStream()
              : connection.getErrorStream();
      if (in != null) {
        while (in.read() >= 0) {
          // drain
        }
        in.close();
      }
    } catch (Exception ignore) {
      // best effort: the assertions are on login()'s outcome
    }
  }

  private static String encode(String raw) {
    try {
      return URLEncoder.encode(raw, "UTF-8");
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  // ───────────────────────────────────────────────────── state 校验

  @Test
  public void matchingStateCompletesTheFlow() throws Exception {
    Outcome outcome = runLogin(p -> "code=the-code&state=" + encode(p.get("state")));

    assertNull(String.valueOf(outcome.error), outcome.error);
    assertEquals("AT1", outcome.token.getAccessToken());
  }

  /** The core CSRF check: a callback from a flow we never started. */
  @Test
  public void mismatchedStateIsRejected() throws Exception {
    Outcome outcome = runLogin(p -> "code=attacker-code&state=not-our-state");

    assertNull(outcome.token);
    assertNotNull(outcome.error);
    assertTrue(outcome.error instanceof OAuth2Exception);
    assertEquals(
        OAuth2Exception.Category.CALLBACK_FAILED, ((OAuth2Exception) outcome.error).getCategory());
    assertTrue(outcome.error.getMessage(), outcome.error.getMessage().contains("state mismatch"));
  }

  /** Rejecting after redeeming would defeat the point. */
  @Test
  public void mismatchedStateNeverRedeemsTheCode() throws Exception {
    runLogin(p -> "code=attacker-code&state=not-our-state");

    assertTrue(
        "a rejected callback must not reach the token endpoint", server.tokenCalls().isEmpty());
  }

  /** An omitted state is not "no opinion" -- it is a failed check. */
  @Test
  public void missingStateIsRejected() throws Exception {
    Outcome outcome = runLogin(p -> "code=the-code");

    assertNull(outcome.token);
    assertTrue(outcome.error.getMessage(), outcome.error.getMessage().contains("state mismatch"));
  }

  @Test
  public void emptyStateIsRejected() throws Exception {
    Outcome outcome = runLogin(p -> "code=the-code&state=");

    assertNull(outcome.token);
    assertEquals(
        OAuth2Exception.Category.CALLBACK_FAILED, ((OAuth2Exception) outcome.error).getCategory());
  }

  /** No trimming, no case folding, no prefix matching, no truncation. */
  @Test
  public void stateIsComparedExactly() throws Exception {
    Map<String, Function<String, String>> mutations = new LinkedHashMap<>();
    mutations.put("trailing space", s -> s + " ");
    mutations.put("truncated", s -> s.substring(0, s.length() - 1));
    mutations.put("extended", s -> s + "x");
    mutations.put("case flipped", OAuth2AuthorizationFlowTest::swapCase);

    for (Map.Entry<String, Function<String, String>> entry : mutations.entrySet()) {
      setUp();   // fresh server and home per mutation
      try {
        Outcome outcome =
            runLogin(p -> "code=the-code&state=" + encode(entry.getValue().apply(p.get("state"))));
        assertNull(entry.getKey(), outcome.token);
        assertEquals(
            entry.getKey(),
            OAuth2Exception.Category.CALLBACK_FAILED,
            ((OAuth2Exception) outcome.error).getCategory());
      } finally {
        tearDown();
      }
    }
  }

  private static String swapCase(String raw) {
    StringBuilder sb = new StringBuilder(raw.length());
    for (char c : raw.toCharArray()) {
      sb.append(Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c));
    }
    return sb.toString();
  }

  @Test
  public void missingCodeIsRejectedEvenWithAValidState() throws Exception {
    Outcome outcome = runLogin(p -> "state=" + encode(p.get("state")));

    assertNull(outcome.token);
    assertTrue(outcome.error.getMessage(), outcome.error.getMessage().contains("code"));
  }

  // ────────────────────────────────────── 重复参数（参数污染）

  /**
   * {@code ?state=ours&state=theirs} -- the <b>last</b> occurrence wins, same as Python.
   *
   * <p>The choice is pinned because the danger is divergence: if one side took the first and
   * another the last, an attacker could make the checking side and the redeeming side disagree.
   */
  @Test
  public void lastStateWinsSoAPollutedCallbackIsRejected() throws Exception {
    Outcome outcome =
        runLogin(p -> "code=the-code&state=" + encode(p.get("state")) + "&state=attacker");

    assertNull("the trailing state must be the one checked", outcome.token);
    assertEquals(
        OAuth2Exception.Category.CALLBACK_FAILED, ((OAuth2Exception) outcome.error).getCategory());
  }

  /** The mirror image, proving the rule is "last wins" rather than "any match". */
  @Test
  public void trailingValidStateIsAccepted() throws Exception {
    Outcome outcome =
        runLogin(p -> "code=the-code&state=attacker&state=" + encode(p.get("state")));

    assertNull(String.valueOf(outcome.error), outcome.error);
    assertEquals("AT1", outcome.token.getAccessToken());
  }

  @Test
  public void duplicateCodeAlsoTakesTheLast() throws Exception {
    Outcome outcome =
        runLogin(p -> "code=first&code=second&state=" + encode(p.get("state")));

    assertNull(String.valueOf(outcome.error), outcome.error);
    assertEquals("second", server.tokenCalls().get(0).form.get("code"));
  }

  // ────────────────────────────────────────────── state 生成

  /** A reused state would let an old callback be replayed into a new flow. */
  @Test
  public void eachAuthorizationUsesAFreshState() throws Exception {
    Set<String> states = new HashSet<>();
    for (int i = 0; i < 3; i++) {
      runLogin(
          p -> {
            states.add(p.get("state"));
            return "code=the-code&state=" + encode(p.get("state"));
          });
    }
    assertEquals("state must not repeat across authorizations", 3, states.size());
  }

  /** The verifier is sent only to the token endpoint, never in the browser URL. */
  @Test
  public void verifierIsFreshAndNeverLeavesTheProcess() throws Exception {
    Set<String> challenges = new HashSet<>();
    for (int i = 0; i < 3; i++) {
      runLogin(
          p -> {
            challenges.add(p.get("code_challenge"));
            assertFalse(
                "the verifier must never appear in the authorization URL",
                p.containsKey("code_verifier"));
            return "code=the-code&state=" + encode(p.get("state"));
          });
    }
    assertEquals(3, challenges.size());
  }

  /** A-01: what the browser is sent. */
  @Test
  public void authorizationRequestShape() throws Exception {
    runLogin(p -> "code=the-code&state=" + encode(p.get("state")));

    assertEquals("code", seenParams.get("response_type"));
    assertEquals("cid", seenParams.get("client_id"));
    assertEquals("S256", seenParams.get("code_challenge_method"));
    assertTrue(seenParams.get("redirect_uri").startsWith("http://127.0.0.1:"));
    assertEquals("api.quote:read api.trade:read", seenParams.get("scope"));
  }

  // ─────────────────────────────────────────────── 回调错误

  /** A-04: the user denying is a normal outcome. */
  @Test
  public void errorCallbackIsReportedAndNoCodeIsRedeemed() throws Exception {
    Outcome outcome =
        runLogin(p -> "error=access_denied&error_description=user+said+no&state="
            + encode(p.get("state")));

    assertNull(outcome.token);
    assertEquals(
        OAuth2Exception.Category.CALLBACK_FAILED, ((OAuth2Exception) outcome.error).getCategory());
    assertTrue(outcome.error.getMessage(), outcome.error.getMessage().contains("access_denied"));
    assertTrue(server.tokenCalls().isEmpty());
  }

  /** A denial with a mismatched state should still read as a denial. */
  @Test
  public void errorIsCheckedBeforeState() throws Exception {
    Outcome outcome = runLogin(p -> "error=access_denied&state=whatever");

    assertTrue(outcome.error.getMessage(), outcome.error.getMessage().contains("access_denied"));
  }

  // ──────────────────────────────────────────────── 换码请求

  @Test
  public void authorizationCodeGrantShape() throws Exception {
    runLogin(p -> "code=the-code&state=" + encode(p.get("state")));

    Map<String, String> form = server.tokenCalls().get(0).form;
    assertEquals("authorization_code", form.get("grant_type"));
    assertEquals("the-code", form.get("code"));
    assertEquals("cid", form.get("client_id"));
    assertNotNull(form.get("code_verifier"));
    assertEquals(seenParams.get("redirect_uri"), form.get("redirect_uri"));
  }

  /** S256(verifier) must equal the challenge that was sent in the authorization URL. */
  @Test
  public void verifierMatchesTheChallengeThatWasSent() throws Exception {
    runLogin(p -> "code=the-code&state=" + encode(p.get("state")));

    String verifier = server.tokenCalls().get(0).form.get("code_verifier");
    byte[] digest = MessageDigest.getInstance("SHA-256").digest(verifier.getBytes(UTF_8));
    String expected = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);

    assertEquals(seenParams.get("code_challenge"), expected);
  }

  /** A-05: nothing arrives, the caller must not block forever. */
  @Test
  public void callbackTimeoutIsReported() throws Exception {
    OAuth2SessionManager sessions =
        OAuth2SessionManager.builder()
            .issuer(server.getIssuer())
            .clientId("cid")
            .home(home.toString())
            .authorizeTimeoutMinutes(0)   // expire immediately
            .build();
    try {
      sessions.login(url -> {
        // deliberately never deliver a callback
      });
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.CALLBACK_FAILED, e.getCategory());
    }
    assertTrue(server.tokenCalls().isEmpty());
  }
}
