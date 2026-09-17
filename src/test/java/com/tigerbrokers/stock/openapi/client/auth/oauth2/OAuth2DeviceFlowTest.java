package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Device authorization grant (RFC 8628).
 *
 * <p>Covers test-plan cases A-13..A-18 and H-16.</p>
 *
 * <p>The polling loop sleeps for real, so the fake server hands out a one-second interval and
 * a short {@code expires_in}; the assertions are on poll counts rather than wall-clock time.</p>
 */
public class OAuth2DeviceFlowTest {

  private FakeAuthorizationServer server;
  private Path home;
  private final List<OAuth2DeviceAuthorization> shown = new ArrayList<>();

  @Before
  public void setUp() throws IOException {
    server = new FakeAuthorizationServer();
    home = Files.createTempDirectory("tigeropen-oauth2-device-");
    shown.clear();
    // One-second polling keeps the suite quick; the AS normally sends no interval at all
    server.queueDevice(device(1, 600));
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

  private String device(long intervalSeconds, long expiresIn) {
    return "{\"device_code\":\"DEV-CODE\",\"user_code\":\"ABCD-EFGH\","
        + "\"verification_uri\":\"" + server.getBaseUrl() + "/activate\","
        + "\"expires_in\":" + expiresIn + ",\"interval\":" + intervalSeconds + "}";
  }

  private OAuth2SessionManager session() {
    return OAuth2SessionManager.builder()
        .issuer(server.getIssuer())
        .clientId("cid")
        .home(home.toString())
        .build();
  }

  private OAuth2Token login() {
    return login(session());
  }

  private OAuth2Token login(OAuth2SessionManager sessions) {
    return sessions.loginWithDeviceCode(device -> shown.add(device));
  }

  // -------------------------------------------------- device authorization

  @Test
  public void happyPathReturnsAndPersistsAToken() {
    assertEquals("AT1", login().getAccessToken());
    assertEquals("AT1", session().status().getAccessToken());
  }

  @Test
  public void requestCarriesClientIdAndScope() {
    login();
    FakeAuthorizationServer.Call call = server.callsTo("/device").get(0);
    assertEquals("cid", call.form.get("client_id"));
    assertEquals("api.quote:read api.trade:read", call.form.get("scope"));
  }

  @Test
  public void tokenRequestUsesTheDeviceCodeGrant() {
    login();
    FakeAuthorizationServer.Call call = server.tokenCalls().get(0);
    assertEquals("urn:ietf:params:oauth:grant-type:device_code", call.form.get("grant_type"));
    assertEquals("DEV-CODE", call.form.get("device_code"));
    assertEquals("cid", call.form.get("client_id"));
  }

  /** The user cannot approve a code they have not been given yet. */
  @Test
  public void userIsShownTheCodeBeforePollingStarts() {
    final AtomicInteger tokenCallsWhenShown = new AtomicInteger(-1);
    session().loginWithDeviceCode(device -> {
      shown.add(device);
      tokenCallsWhenShown.set(server.tokenCalls().size());
    });

    assertEquals(1, shown.size());
    assertEquals("ABCD-EFGH", shown.get(0).getUserCode());
    assertEquals(0, tokenCallsWhenShown.get());
  }

  @Test
  public void missingEndpointIsReportedBeforeAnyRequest() {
    server.advertiseDevice = false;
    try {
      login();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.METADATA_UNAVAILABLE, e.getCategory());
    }
    assertTrue(server.callsTo("/device").isEmpty());
  }

  @Test
  public void nullCallbackIsRejected() {
    try {
      session().loginWithDeviceCode(null);
      fail("expected IllegalArgumentException");
    } catch (IllegalArgumentException expected) {
      // without it the user would never see the code
    }
  }

  @Test
  public void errorResponseIsReported() {
    server.deviceResponses.clear();
    server.queueDeviceError("invalid_client");
    try {
      login();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED, e.getCategory());
      assertTrue(e.getMessage(), e.getMessage().contains("invalid_client"));
    }
  }

  /** Without a user_code there is nothing to show, so polling would be pointless. */
  @Test
  public void incompleteResponseIsRejectedBeforePolling() {
    server.deviceResponses.clear();
    server.queueDevice("{\"device_code\":\"DEV\",\"expires_in\":600}");
    try {
      login();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED, e.getCategory());
    }
    assertTrue("must not poll without a user code", server.tokenCalls().isEmpty());
  }

  /** With no verification page the user has a code and nowhere to enter it. */
  @Test
  public void missingVerificationUriIsRejected() {
    server.deviceResponses.clear();
    server.queueDevice("{\"device_code\":\"DEV\",\"user_code\":\"UC\",\"expires_in\":600}");
    try {
      login();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED, e.getCategory());
      assertTrue(e.getMessage(), e.getMessage().contains("verification_uri"));
    }
  }

  // ------------------------------------------------------------- polling

  /** A-13: pending is the normal state while the user walks to their phone. */
  @Test
  public void authorizationPendingKeepsPolling() {
    server.queueTokenError("authorization_pending");
    server.queueTokenError("authorization_pending");

    assertEquals("AT1", login().getAccessToken());
    assertEquals(3, server.tokenCalls().size());
  }

  /** A-15: the user said no; polling on would be harassment. */
  @Test
  public void accessDeniedStopsImmediately() {
    server.queueTokenError("access_denied");
    try {
      login();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED, e.getCategory());
      assertTrue(e.getMessage(), e.getMessage().contains("denied"));
    }
    assertEquals(1, server.tokenCalls().size());
  }

  /** A-16. */
  @Test
  public void expiredTokenStopsImmediately() {
    server.queueTokenError("expired_token");
    try {
      login();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED, e.getCategory());
    }
    assertEquals(1, server.tokenCalls().size());
  }

  @Test
  public void unexpectedErrorIsReportedAsATokenFailure() {
    server.queueTokenError("invalid_scope");
    try {
      login();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.TOKEN_REQUEST_FAILED, e.getCategory());
      assertTrue(e.getMessage(), e.getMessage().contains("invalid_scope"));
    }
  }

  /**
   * A-18: {@code invalid_grant} here refers to the device_code, not to the stored refresh
   * token, so the existing authorization on disk must survive. This is exactly why the
   * polling loop does not reuse the token-exchange error handling.
   */
  @Test
  public void aFailedDeviceFlowLeavesAnExistingAuthorizationAlone() {
    OAuth2Token existing = new OAuth2Token();
    existing.setIssuer(server.getIssuer());
    existing.setClientId("cid");
    existing.setAccessToken("KEEP");
    existing.setRefreshToken("RT");
    existing.setExpiresAt(System.currentTimeMillis() + 3600_000L);
    new OAuth2TokenStore(home.toString()).saveToken(existing);

    server.queueTokenError("invalid_grant");
    try {
      login();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED, e.getCategory());
    }

    OAuth2Token stored = new OAuth2TokenStore(home.toString()).loadToken("cid", server.getIssuer());
    assertNotNull("a dead device_code must not delete a good authorization", stored);
    assertEquals("KEEP", stored.getAccessToken());
  }

  /**
   * A-17: the loop never sleeps past the deadline, so it does not sit through a full
   * interval after the user_code has already expired.
   */
  @Test
  public void deadlineIsEnforcedLocally() {
    server.deviceResponses.clear();
    server.queueDevice(device(1, 3));
    for (int i = 0; i < 10; i++) {
      server.queueTokenError("authorization_pending");
    }

    long started = System.currentTimeMillis();
    try {
      login();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED, e.getCategory());
      assertTrue(e.getMessage(), e.getMessage().contains("expired"));
    }
    long elapsed = System.currentTimeMillis() - started;
    assertTrue("must not overshoot the 3s lifetime, took " + elapsed + "ms", elapsed < 4_500);
    assertEquals(2, server.tokenCalls().size());
  }

  /**
   * A-14: {@code slow_down} raises the interval permanently, so fewer polls fit in the same
   * lifetime. Without the raise this budget would allow two polls, not one.
   */
  @Test
  public void slowDownRaisesTheInterval() {
    server.deviceResponses.clear();
    server.queueDevice(device(1, 3));
    server.queueTokenError("slow_down");
    for (int i = 0; i < 10; i++) {
      server.queueTokenError("authorization_pending");
    }

    try {
      login();
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.DEVICE_AUTHORIZATION_FAILED, e.getCategory());
    }
    assertEquals("the raised interval must leave room for only one poll",
        1, server.tokenCalls().size());
  }

  // -------------------------------------------------- loginWithDeviceCodeIfNeeded

  /** H-16: a usable token must not send the user off to another device. */
  @Test
  public void ifNeededReusesAUsableToken() {
    OAuth2Token existing = new OAuth2Token();
    existing.setIssuer(server.getIssuer());
    existing.setClientId("cid");
    existing.setAccessToken("AT-existing");
    existing.setRefreshToken("RT");
    existing.setExpiresAt(System.currentTimeMillis() + 3600_000L);
    new OAuth2TokenStore(home.toString()).saveToken(existing);

    OAuth2Token token = session().loginWithDeviceCodeIfNeeded(device -> shown.add(device));

    assertEquals("AT-existing", token.getAccessToken());
    assertTrue(shown.isEmpty());
    assertTrue(server.callsTo("/device").isEmpty());
  }

  /** A-26 applies here too: a transient failure must not prompt the user. */
  @Test
  public void ifNeededPropagatesATransientRefreshFailure() {
    OAuth2Token existing = new OAuth2Token();
    existing.setIssuer(server.getIssuer());
    existing.setClientId("cid");
    existing.setAccessToken("AT-expiring");
    existing.setRefreshToken("RT");
    existing.setExpiresAt(System.currentTimeMillis() + 60_000L);
    new OAuth2TokenStore(home.toString()).saveToken(existing);

    server.queueTokenError("temporarily_unavailable");
    try {
      session().loginWithDeviceCodeIfNeeded(device -> shown.add(device));
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.TOKEN_REQUEST_FAILED, e.getCategory());
    }
    assertTrue("must not start a device authorization", shown.isEmpty());
  }
}
