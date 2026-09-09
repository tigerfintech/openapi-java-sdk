package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.tigerbrokers.stock.openapi.client.auth.AuthenticationAttempt;
import com.tigerbrokers.stock.openapi.client.auth.AuthenticationType;
import com.tigerbrokers.stock.openapi.client.auth.RequestAuthContext;
import com.tigerbrokers.stock.openapi.client.auth.RetryDecision;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import org.junit.Test;

/**
 * Pure functions and value types: PKCE, scope normalization, credential masking.
 *
 * <p>Covers test-plan cases A-08, A-19, A-21, K-02, K-03, K-04, and the cross-SDK
 * conformance vectors in the integration doc appendix.</p>
 */
public class OAuth2PrimitivesTest {

  private static Object invokeStatic(String name, Class<?>[] types, Object... args)
      throws Exception {
    Method method = OAuth2SessionManager.class.getDeclaredMethod(name, types);
    method.setAccessible(true);
    return method.invoke(null, args);
  }

  private static String s256(String verifier) throws Exception {
    return (String) invokeStatic("s256", new Class<?>[] {String.class}, verifier);
  }

  private static String normalizeScopes(String raw) throws Exception {
    return (String) invokeStatic("normalizeScopes", new Class<?>[] {String.class}, raw);
  }

  private static boolean scopesCover(String registered, String requested) throws Exception {
    return (Boolean) invokeStatic("scopesCover",
        new Class<?>[] {String.class, String.class}, registered, requested);
  }

  private static String randomUrlSafe(int bytes) throws Exception {
    return (String) invokeStatic("randomUrlSafe", new Class<?>[] {int.class}, bytes);
  }

  // ------------------------------------------------------------------ PKCE

  /**
   * A-08: the RFC 7636 Appendix B worked example. Getting this wrong makes every
   * authorization fail at the token endpoint, with an error that points nowhere useful.
   */
  @Test
  public void s256MatchesTheRfc7636Vector() throws Exception {
    assertEquals("E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM",
        s256("dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk"));
  }

  @Test
  public void challengeIsBase64UrlWithoutPadding() throws Exception {
    String challenge = s256("any-verifier");
    assertEquals(43, challenge.length());
    assertFalse(challenge.contains("="));
    assertFalse(challenge.contains("+"));
    assertFalse(challenge.contains("/"));
  }

  /**
   * The verifier is 64 bytes and the state 24, matching the Python SDK. RFC 7636 allows
   * 43-128 characters for the verifier; 64 bytes base64url-encodes to 86.
   */
  @Test
  public void randomValuesAreUrlSafeAndTheAgreedLength() throws Exception {
    assertEquals(86, randomUrlSafe(64).length());
    assertEquals(32, randomUrlSafe(24).length());
    for (int i = 0; i < 20; i++) {
      String value = randomUrlSafe(64);
      assertFalse(value, value.contains("=") || value.contains("+") || value.contains("/"));
    }
  }

  @Test
  public void randomValuesDoNotRepeat() throws Exception {
    Set<String> seen = new HashSet<>();
    for (int i = 0; i < 100; i++) {
      seen.add(randomUrlSafe(24));
    }
    assertEquals("a repeated state or verifier would defeat the point", 100, seen.size());
  }

  // ----------------------------------------------------------------- scope

  /**
   * A-21: {@code "b a"} and {@code "a b"} are the same set; without normalization every
   * startup would decide re-registration was needed.
   */
  @Test
  public void scopesAreSortedAndDeduplicated() throws Exception {
    assertEquals("api.quote:read api.trade:read",
        normalizeScopes("api.trade:read api.quote:read"));
    assertEquals("api.quote:read api.trade:read",
        normalizeScopes("api.quote:read  api.trade:read api.quote:read"));
    assertEquals("api.quote:read", normalizeScopes("  api.quote:read  "));
  }

  @Test
  public void normalizingIsIdempotent() throws Exception {
    String once = normalizeScopes("c b a");
    assertEquals(once, normalizeScopes(once));
  }

  @Test
  public void emptyScopeIsRejected() throws Exception {
    for (String raw : new String[] {null, "", "   "}) {
      try {
        normalizeScopes(raw);
        fail("expected IllegalArgumentException for [" + raw + "]");
      } catch (Exception e) {
        assertTrue(String.valueOf(e.getCause()),
            e.getCause() instanceof IllegalArgumentException);
      }
    }
  }

  /** Decides whether a stored registration still covers what we need. */
  @Test
  public void scopesCoverIsASubsetCheck() throws Exception {
    assertTrue(scopesCover("a b c", "a c"));
    assertTrue(scopesCover("a b", "a b"));
    assertFalse(scopesCover("a b", "a b c"));
    assertFalse(scopesCover(null, "a"));
  }

  // ----------------------------------------------------------- token model

  private static OAuth2Token token(long remainingMillis) {
    OAuth2Token token = new OAuth2Token();
    token.setClientId("cid");
    token.setAccessToken("AT");
    token.setRefreshToken("RT");
    token.setExpiresAt(System.currentTimeMillis() + remainingMillis);
    return token;
  }

  /** B-10: the refresh window boundary. */
  @Test
  public void needsRefreshBoundary() {
    assertFalse(token(301_000).needsRefresh(300_000));
    assertTrue(token(299_000).needsRefresh(300_000));
    assertTrue(token(-1).needsRefresh(0));
  }

  @Test
  public void remainingMillisGoesNegativeOnceExpired() {
    assertTrue(token(-5_000).remainingMillis() < 0);
  }

  @Test
  public void hasRefreshToken() {
    OAuth2Token token = token(1000);
    assertTrue(token.hasRefreshToken());
    token.setRefreshToken(null);
    assertFalse(token.hasRefreshToken());
    token.setRefreshToken("");
    assertFalse(token.hasRefreshToken());
  }

  /** K-02: a token in a log line is a credential in a log line. */
  @Test
  public void tokenToStringLeaksNoCredential() {
    OAuth2Token token = token(1000);
    token.setAccessToken("secret-access-value");
    token.setRefreshToken("secret-refresh-value");

    String text = token.toString();
    assertFalse(text, text.contains("secret-access-value"));
    assertFalse(text, text.contains("secret-refresh-value"));
    assertTrue(text, text.contains("cid"));
  }

  @Test
  public void registrationToStringIsSafe() {
    OAuth2ClientRegistration registration = new OAuth2ClientRegistration();
    registration.setIssuer("https://as.example.com");
    registration.setClientId("client-abc");
    assertTrue(registration.toString().contains("client-abc"));
  }

  /** A-18 / K-02: holding the device_code is equivalent to holding the authorization. */
  @Test
  public void deviceAuthorizationToStringNeverPrintsTheDeviceCode() {
    OAuth2DeviceAuthorization device = new OAuth2DeviceAuthorization();
    device.setDeviceCode("SECRET-DEVICE-CODE");
    device.setUserCode("ABCD-EFGH");
    device.setVerificationUri("https://example.com/activate");

    String text = device.toString();
    assertFalse(text, text.contains("SECRET-DEVICE-CODE"));
    assertTrue("the user code is meant to be displayed", text.contains("ABCD-EFGH"));
  }

  /**
   * RFC 8628 §3.5; Tiger's authorization server sends no interval, so the field stays null
   * and the session manager falls back to its own default.
   */
  @Test
  public void deviceIntervalIsNullUnlessTheServerSendsOne() {
    OAuth2DeviceAuthorization device = new OAuth2DeviceAuthorization();
    assertNull(device.getInterval());
    assertEquals(5L, OAuth2SessionManager.DEFAULT_DEVICE_POLL_INTERVAL_SECONDS);
    device.setInterval(7L);
    assertEquals(Long.valueOf(7), device.getInterval());
  }

  /** The pre-filled URI saves the user typing the code, so prefer it when present. */
  @Test
  public void bestVerificationUriPrefersThePreFilledOne() {
    OAuth2DeviceAuthorization device = new OAuth2DeviceAuthorization();
    device.setVerificationUri("https://x/activate");
    assertEquals("https://x/activate", device.getBestVerificationUri());

    device.setVerificationUriComplete("https://x/activate?code=ABCD");
    assertEquals("https://x/activate?code=ABCD", device.getBestVerificationUri());
  }

  // -------------------------------------------------------- auth value types

  /**
   * The attempt is a receipt of which credential a request used; it exists only so a 401
   * can be told apart from a stale one.
   */
  @Test
  public void attemptCarriesTheCredentialButDoesNotPrintIt() {
    AuthenticationAttempt attempt =
        new AuthenticationAttempt(AuthenticationType.OAUTH2, "secret-token");
    assertEquals("secret-token", attempt.getCredential());
    assertEquals(AuthenticationType.OAUTH2, attempt.getType());
    assertFalse(attempt.toString(), attempt.toString().contains("secret-token"));
  }

  /** K-03: the decision carries a header, so it must not print one. */
  @Test
  public void retryDecisionDoesNotPrintTheHeader() {
    RetryDecision retry = RetryDecision.retry("Bearer secret-token");
    assertTrue(retry.shouldRetry());
    assertEquals("Bearer secret-token", retry.getAuthorizationHeader());
    assertFalse(retry.toString(), retry.toString().contains("secret-token"));

    RetryDecision no = RetryDecision.noRetry();
    assertFalse(no.shouldRetry());
    assertNull(no.getAuthorizationHeader());
  }

  /**
   * Named after the constraint rather than the business operation: what matters at this
   * layer is only whether a second attempt is safe.
   */
  @Test
  public void requestAuthContextCarriesTheRetryConstraint() {
    RequestAuthContext retryable =
        new RequestAuthContext(new LinkedHashMap<String, Object>(), true);
    assertTrue(retryable.isRetryable());
    assertNull(retryable.getAuthorizationHeader());
    retryable.setAuthorizationHeader("Bearer x");
    assertEquals("Bearer x", retryable.getAuthorizationHeader());

    assertFalse(new RequestAuthContext(new LinkedHashMap<String, Object>(), false).isRetryable());
  }

  @Test
  public void exceptionCategoriesDistinguishTerminalFromTransient() {
    assertTrue(new OAuth2Exception(OAuth2Exception.Category.REAUTHORIZATION_REQUIRED, "x")
        .isReauthorizationRequired());
    assertFalse(new OAuth2Exception(OAuth2Exception.Category.TOKEN_REQUEST_FAILED, "x")
        .isReauthorizationRequired());
  }

  @Test
  public void exceptionToStringNamesTheCategory() {
    String text = new OAuth2Exception(OAuth2Exception.Category.CALLBACK_FAILED, "boom").toString();
    assertTrue(text, text.contains("CALLBACK_FAILED"));
    assertTrue(text, text.contains("boom"));
  }

  /** §5.2: the two client-id origins imply different callback ports. */
  @Test
  public void manualCallbackPortIsTheAgreedConstant() {
    assertEquals(18888, OAuth2SessionManager.DEFAULT_MANUAL_CALLBACK_PORT);
    assertNotEquals(0, OAuth2SessionManager.DEFAULT_MANUAL_CALLBACK_PORT);
  }
}
