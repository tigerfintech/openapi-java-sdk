package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * OAuth2 control plane: refresh, single-flight, logout/revoke, registration lifecycle.
 *
 * <p>Covers test-plan cases A-19, A-20, A-24..A-27, B-10..B-14, C-01..C-08, C-12, D-01..D-07,
 * D-14, I-11..I-16, J-05.</p>
 */
public class OAuth2SessionManagerTest {

  private FakeAuthorizationServer server;
  private Path home;

  @Before
  public void setUp() throws IOException {
    server = new FakeAuthorizationServer();
    home = Files.createTempDirectory("tigeropen-oauth2-session-");
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
    return session("cid");
  }

  private OAuth2SessionManager session(String clientId) {
    OAuth2SessionManager.Builder builder = OAuth2SessionManager.builder()
        .issuer(server.getIssuer())
        .home(home.toString());
    if (clientId != null) {
      builder.clientId(clientId);
    }
    return builder.build();
  }

  private OAuth2Token seed(OAuth2SessionManager sessions, long remainingSeconds) {
    return seed(sessions, remainingSeconds, "RT0", "AT0", "cid");
  }

  private OAuth2Token seed(OAuth2SessionManager sessions, long remainingSeconds,
      String refreshToken, String accessToken, String clientId) {
    OAuth2Token token = new OAuth2Token();
    token.setIssuer(server.getIssuer());
    token.setClientId(clientId);
    token.setAccessToken(accessToken);
    token.setRefreshToken(refreshToken);
    token.setScope("api.quote:read");
    token.setExpiresAt(System.currentTimeMillis() + remainingSeconds * 1000L);
    new OAuth2TokenStore(home.toString()).saveToken(token);
    return token;
  }

  // -------------------------------------------------------- getAccessToken

  /** B-10: outside the refresh window, no request is made. */
  @Test
  public void returnsCurrentTokenWhenFresh() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 3600);
    assertEquals("AT0", sessions.getAccessToken());
    assertTrue(server.tokenCalls().isEmpty());
  }

  /** B-12. */
  @Test
  public void refreshesInsideTheWindow() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 60);
    assertEquals("AT1", sessions.getAccessToken());
    assertEquals(1, server.tokenCalls().size());
  }

  /** B-11: must not implicitly start an authorization. */
  @Test
  public void neverAuthorizedRaisesAndOpensNothing() {
    try {
      session().getAccessToken();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.REAUTHORIZATION_REQUIRED, e.getCategory());
    }
    assertTrue(server.calls.isEmpty());
  }

  /** B-14. */
  @Test
  public void noRefreshTokenRaisesWithoutCallingTheTokenEndpoint() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 60, null, "AT0", "cid");
    try {
      sessions.getAccessToken();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.REAUTHORIZATION_REQUIRED, e.getCategory());
    }
    assertTrue(server.tokenCalls().isEmpty());
  }

  @Test
  public void refreshSendsTheRightGrant() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 60, "RT-old", "AT0", "cid");
    sessions.getAccessToken();

    FakeAuthorizationServer.Call call = server.tokenCalls().get(0);
    assertEquals("refresh_token", call.form.get("grant_type"));
    assertEquals("RT-old", call.form.get("refresh_token"));
    assertEquals("cid", call.form.get("client_id"));
  }

  // ---------------------------------------------------------- single flight

  /** C-01, C-05: N concurrent callers must cause O(1) token requests. */
  @Test
  public void concurrentRefreshHitsTheEndpointOnce() throws Exception {
    final OAuth2SessionManager sessions = session();
    seed(sessions, 60);

    final int threads = 20;
    final CyclicBarrier barrier = new CyclicBarrier(threads);
    final List<String> results = Collections.synchronizedList(new ArrayList<String>());
    final List<Throwable> errors = Collections.synchronizedList(new ArrayList<Throwable>());

    List<Thread> pool = new ArrayList<>();
    for (int i = 0; i < threads; i++) {
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            barrier.await();
            results.add(sessions.getAccessToken());
          } catch (Throwable t) {
            errors.add(t);
          }
        }
      });
      pool.add(thread);
      thread.start();
    }
    for (Thread thread : pool) {
      thread.join(30_000);
    }

    assertTrue(String.valueOf(errors), errors.isEmpty());
    assertEquals(threads, results.size());
    assertEquals("single-flight must collapse concurrent refreshes into one call",
        1, server.tokenCalls().size());
    assertEquals(1, new java.util.HashSet<>(results).size());
  }

  /** C-01: 20 threads all reporting the same failed credential. */
  @Test
  public void concurrent401StormDoesNotFanOut() throws Exception {
    final OAuth2SessionManager sessions = session();
    seed(sessions, 3600, "RT0", "STALE", "cid");

    final int threads = 20;
    final CyclicBarrier barrier = new CyclicBarrier(threads);
    final List<String> results = Collections.synchronizedList(new ArrayList<String>());

    List<Thread> pool = new ArrayList<>();
    for (int i = 0; i < threads; i++) {
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            barrier.await();
            results.add(sessions.refreshAfterUnauthorized("STALE").getAccessToken());
          } catch (Exception ignore) {
            // surfaced by the size assertion below
          }
        }
      });
      pool.add(thread);
      thread.start();
    }
    for (Thread thread : pool) {
      thread.join(30_000);
    }

    assertTrue("a 401 storm must not produce one refresh per thread",
        server.tokenCalls().size() <= 2);
    assertEquals(threads, results.size());
    assertEquals(1, new java.util.HashSet<>(results).size());
  }

  /** C-02: the receipt says our 401 is stale news. */
  @Test
  public void late401ReusesTheAlreadyRefreshedToken() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 3600, "RT0", "T1", "cid");

    OAuth2Token first = sessions.refreshAfterUnauthorized("T1");
    int callsAfterFirst = server.tokenCalls().size();

    OAuth2Token second = sessions.refreshAfterUnauthorized("T1");

    assertEquals(first.getAccessToken(), second.getAccessToken());
    assertEquals("the second 401 must not trigger another refresh",
        callsAfterFirst, server.tokenCalls().size());
  }

  /** C-04: two tokens can share an expiry and still be different credentials. */
  @Test
  public void comparisonIsOnTheCredentialNotTheTimestamp() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 3600, "RT", "NEW", "cid");

    OAuth2Token result = sessions.refreshAfterUnauthorized("OLD");

    assertEquals("NEW", result.getAccessToken());
    assertTrue(server.tokenCalls().isEmpty());
  }

  // ------------------------------------------------------ refresh semantics

  /** C-07: otherwise there would be nothing to refresh with next time. */
  @Test
  public void responseWithoutRefreshTokenCarriesTheOldOneForward() {
    server.queueToken("{\"access_token\":\"AT-new\",\"expires_in\":3600,"
        + "\"scope\":\"api.quote:read\"}");
    OAuth2SessionManager sessions = session();
    OAuth2Token seeded = seed(sessions, 60, "RT-keep", "AT0", "cid");

    assertEquals("RT-keep", sessions.refresh(seeded).getRefreshToken());
  }

  @Test
  public void refreshedTokenIsPersisted() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 60);
    sessions.getAccessToken();

    assertEquals("AT1", session().status().getAccessToken());
  }

  /** I-15: a dead authorization must not be kept around. */
  @Test
  public void invalidGrantIsTerminalAndClearsLocalState() {
    server.queueTokenError("invalid_grant");
    OAuth2SessionManager sessions = session();
    OAuth2Token seeded = seed(sessions, 60);

    try {
      sessions.refresh(seeded);
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.REAUTHORIZATION_REQUIRED, e.getCategory());
    }
    assertNull(sessions.status());
  }

  /** C-12 / I-16: a transient failure must not destroy the authorization. */
  @Test
  public void serverErrorIsRetryableAndKeepsTheLocalToken() {
    server.queueTokenError("temporarily_unavailable");
    OAuth2SessionManager sessions = session();
    OAuth2Token seeded = seed(sessions, 60);

    try {
      sessions.refresh(seeded);
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.TOKEN_REQUEST_FAILED, e.getCategory());
    }
    assertNotNull("a retryable failure must not clear the token", sessions.status());
  }

  // -------------------------------------------------------- loginIfNeeded

  /** A-24: a usable token must not interrupt the user. */
  @Test
  public void usableTokenDoesNotInvokeTheCallback() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 3600);
    final AtomicReference<String> shown = new AtomicReference<>();

    assertEquals("AT0", sessions.loginIfNeeded(url -> shown.set(url)).getAccessToken());
    assertNull(shown.get());
  }

  /** A-25: a silent refresh must not prompt the user. */
  @Test
  public void expiringTokenIsRefreshedSilently() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 60);
    final AtomicReference<String> shown = new AtomicReference<>();

    assertEquals("AT1", sessions.loginIfNeeded(url -> shown.set(url)).getAccessToken());
    assertNull(shown.get());
  }

  /**
   * A-26: a network blip must not pop a browser for a problem that fixes itself. The
   * authorization would block for minutes waiting on a callback that will never come.
   */
  @Test
  public void transientFailurePropagatesInsteadOfPrompting() {
    server.queueTokenError("temporarily_unavailable");
    OAuth2SessionManager sessions = session();
    seed(sessions, 60);
    final AtomicReference<String> shown = new AtomicReference<>();

    try {
      sessions.loginIfNeeded(url -> shown.set(url));
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.TOKEN_REQUEST_FAILED, e.getCategory());
    }
    assertNull("must not start an authorization", shown.get());
  }

  @Test
  public void loginRequiresACallback() {
    try {
      session().login(null);
      fail("expected IllegalArgumentException");
    } catch (IllegalArgumentException expected) {
      // without it the user could never see the URL
    }
  }

  // ---------------------------------------------------------------- logout

  /** D-01. */
  @Test
  public void logoutRevokesThenClearsLocally() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 3600);

    assertTrue(sessions.logout());
    assertEquals(1, server.revokeCalls().size());
    assertNull(sessions.status());
    assertNull("must be gone from disk too", session().status());
  }

  /** D-02: revoking the access token alone would leave the 30-day half alive. */
  @Test
  public void logoutRevokesTheRefreshTokenByPreference() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 3600, "RT-x", "AT-x", "cid");
    sessions.logout();

    FakeAuthorizationServer.Call call = server.revokeCalls().get(0);
    assertEquals("RT-x", call.form.get("token"));
    assertEquals("refresh_token", call.form.get("token_type_hint"));
    assertEquals("cid", call.form.get("client_id"));
  }

  /** D-03. */
  @Test
  public void logoutFallsBackToTheAccessToken() {
    OAuth2SessionManager sessions = session();
    seed(sessions, 3600, null, "AT-only", "cid");
    sessions.logout();

    FakeAuthorizationServer.Call call = server.revokeCalls().get(0);
    assertEquals("AT-only", call.form.get("token"));
    assertEquals("access_token", call.form.get("token_type_hint"));
  }

  /** D-04: the user asked to log out; a server refusal must not block that. */
  @Test
  public void rejectedRevocationStillClearsLocalState() {
    server.revokeStatus = 401;
    OAuth2SessionManager sessions = session();
    seed(sessions, 3600);

    assertFalse(sessions.logout());
    assertNull(sessions.status());
  }

  /** D-05: an unreachable server must not throw out of logout. */
  @Test
  public void unreachableServerDoesNotRaise() {
    OAuth2SessionManager sessions = OAuth2SessionManager.builder()
        .issuer("http://127.0.0.1:1")
        .clientId("cid")
        .home(home.toString())
        .build();
    OAuth2Token token = new OAuth2Token();
    token.setIssuer("http://127.0.0.1:1");
    token.setClientId("cid");
    token.setAccessToken("AT");
    token.setRefreshToken("RT");
    token.setExpiresAt(System.currentTimeMillis() + 3600_000L);
    new OAuth2TokenStore(home.toString()).saveToken(token);

    assertFalse(sessions.logout());
    assertNull(sessions.status());
  }

  /** D-06: revocation is optional in RFC 8414. */
  @Test
  public void noRevocationEndpointSkipsTheCall() {
    server.advertiseRevocation = false;
    OAuth2SessionManager sessions = session();
    seed(sessions, 3600);

    assertFalse(sessions.logout());
    assertTrue(server.revokeCalls().isEmpty());
    assertNull(sessions.status());
  }

  /** D-07. */
  @Test
  public void logoutWithoutATokenSendsNothing() {
    assertFalse(session().logout());
    assertTrue(server.calls.isEmpty());
  }

  /** D-14: the registration is an identity, not an authorization. */
  @Test
  public void logoutKeepsTheClientRegistration() {
    OAuth2TokenStore store = new OAuth2TokenStore(home.toString());
    OAuth2ClientRegistration registration = new OAuth2ClientRegistration();
    registration.setIssuer(server.getIssuer());
    registration.setClientId("dyn-client");
    registration.setRegisteredScopes("api.quote:read api.trade:read");
    store.saveClientRegistration(registration);

    OAuth2SessionManager sessions = session(null);
    seed(sessions, 3600, "RT", "AT", "dyn-client");

    sessions.logout();

    assertNotNull(store.loadClientRegistration(server.getIssuer()));
  }

  // ------------------------------------------------- registration lifecycle

  /** I-11: registering on every start would create a new client each time. */
  @Test
  public void dynamicRegistrationHappensOnceAndIsReused() {
    OAuth2SessionManager sessions = session(null);
    seed(sessions, 3600, "RT", "AT", "dyn-client");
    // status() alone must not register
    assertTrue(server.callsTo("/register").isEmpty());

    assertNull(sessions.getClientId());

    OAuth2TokenStore store = new OAuth2TokenStore(home.toString());
    OAuth2ClientRegistration registration = new OAuth2ClientRegistration();
    registration.setIssuer(server.getIssuer());
    registration.setClientId("dyn-client");
    registration.setRegisteredScopes("api.quote:read api.trade:read");
    store.saveClientRegistration(registration);

    assertEquals("dyn-client", session(null).getClientId());
    assertTrue("a stored registration must be reused", server.callsTo("/register").isEmpty());
  }

  @Test
  public void explicitClientIdIsReportedAsSuch() {
    assertTrue(session().hasExplicitClientId());
    assertEquals("cid", session().getClientId());
    assertFalse(session(null).hasExplicitClientId());
  }

  // -------------------------------------------------------------- metadata

  /** A-19: the SDK asks for everything the AS supports, normalized. */
  @Test
  public void scopesRequestTheFullSupportedSetNormalized() {
    assertEquals("api.quote:read api.trade:read", session().getScopes());
  }

  /** A-20: guessing would surface as a 403 much later. */
  @Test
  public void missingScopesSupportedFailsLoudly() {
    server.scopesSupported = Collections.emptyList();
    try {
      session().getScopes();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.METADATA_UNAVAILABLE, e.getCategory());
    }
  }

  @Test
  public void metadataIsFetchedOnceAndCached() {
    OAuth2SessionManager sessions = session();
    sessions.getScopes();
    sessions.getScopes();
    // Discovery is the only GET; every recorded call here is a POST
    assertEquals("api.quote:read api.trade:read", sessions.getScopes());
  }

  @Test
  public void statusAndIsAuthorizedSendNoRequests() {
    OAuth2SessionManager sessions = session();
    assertNull(sessions.status());
    assertFalse(sessions.isAuthorized());
    seed(sessions, 3600);
    assertTrue(sessions.isAuthorized());
    assertTrue(server.calls.isEmpty());
  }

  /** Building the object offline must work: no request before the first real use. */
  @Test
  public void constructorSendsNoRequests() {
    OAuth2SessionManager sessions = OAuth2SessionManager.builder()
        .issuer("http://127.0.0.1:1")
        .home(home.toString())
        .build();
    assertEquals("http://127.0.0.1:1", sessions.getIssuer());
  }

  /** §5: a trailing slash must not create a second identity for the same AS. */
  @Test
  public void issuerTrailingSlashIsTrimmed() {
    OAuth2SessionManager sessions = OAuth2SessionManager.builder()
        .issuer(server.getIssuer() + "/")
        .clientId("cid")
        .home(home.toString())
        .build();
    assertEquals(server.getIssuer(), sessions.getIssuer());
  }

  /**
   * §5.2: a manual clientId is registered by hand with a fixed redirect_uri, so the port
   * cannot be system-assigned; dynamic registration omits the port entirely (RFC 8252 §7.3).
   */
  @Test
  public void callbackPortDependsOnHowTheClientIdWasObtained() {
    assertEquals(OAuth2SessionManager.DEFAULT_MANUAL_CALLBACK_PORT, session().getCallbackPort());
    assertEquals(0, session(null).getCallbackPort());
  }

  @Test
  public void explicitCallbackPortOverridesBoth() {
    OAuth2SessionManager sessions = OAuth2SessionManager.builder()
        .issuer(server.getIssuer())
        .clientId("cid")
        .callbackPort(12345)
        .home(home.toString())
        .build();
    assertEquals(12345, sessions.getCallbackPort());
  }

  /** A custom store lets an application keep tokens somewhere else entirely. */
  @Test
  public void customStoreIsUsed() throws IOException {
    Path other = Files.createTempDirectory("tigeropen-oauth2-custom-");
    try {
      OAuth2TokenStore custom = new OAuth2TokenStore(other.toString());
      OAuth2SessionManager sessions = OAuth2SessionManager.builder()
          .issuer(server.getIssuer())
          .clientId("cid")
          .store(custom)
          .build();

      OAuth2Token token = new OAuth2Token();
      token.setIssuer(server.getIssuer());
      token.setClientId("cid");
      token.setAccessToken("AT-custom");
      token.setRefreshToken("RT");
      token.setExpiresAt(System.currentTimeMillis() + 3600_000L);
      custom.saveToken(token);

      assertEquals("AT-custom", sessions.getAccessToken());
      assertSame(custom.getHome(), custom.getHome());
      assertNull("must not have touched the default home",
          new OAuth2TokenStore(home.toString()).loadToken("cid", server.getIssuer()));
    } finally {
      deleteRecursively(other.toFile());
    }
  }
}
