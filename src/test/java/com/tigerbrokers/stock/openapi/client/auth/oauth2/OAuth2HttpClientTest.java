package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.tigerbrokers.stock.openapi.client.auth.Authentication;
import com.tigerbrokers.stock.openapi.client.auth.AuthenticationAttempt;
import com.tigerbrokers.stock.openapi.client.auth.AuthenticationType;
import com.tigerbrokers.stock.openapi.client.auth.RequestAuthContext;
import com.tigerbrokers.stock.openapi.client.auth.RetryDecision;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.util.HttpResult;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Executors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * OAuth2 data plane: how a business request carries the credential.
 *
 * <p>Covers test-plan cases E-04, G-02..G-06, H-02..H-09, K-01, L-01, L-02.</p>
 *
 * <p>The most important cases here are the <b>regression</b> ones: signature mode must be
 * unchanged by the OAuth2 work.</p>
 */
public class OAuth2HttpClientTest {

  private static final Charset UTF_8 = Charset.forName("UTF-8");

  /** PKCS8 DER base64, the format TigerSignature.rsaSign needs. */
  private static final String TEST_PRIVATE_KEY;

  static {
    try {
      KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
      generator.initialize(2048);
      KeyPair pair = generator.generateKeyPair();
      TEST_PRIVATE_KEY = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
    } catch (Exception e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private FakeGateway gateway;

  @Before
  public void setUp() {
    gateway = new FakeGateway();
  }

  @After
  public void tearDown() {
    gateway.close();
  }

  /** Business gateway: records the Authorization header and body of every request. */
  private static class FakeGateway {
    final HttpServer server;
    final String url;
    final List<String> authHeaders = Collections.synchronizedList(new ArrayList<String>());
    final List<String> bodies = Collections.synchronizedList(new ArrayList<String>());
    final Deque<Integer> statuses = new ArrayDeque<>();

    FakeGateway() {
      try {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
      url = "http://127.0.0.1:" + server.getAddress().getPort() + "/gateway";
      server.createContext("/gateway", new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
          authHeaders.add(exchange.getRequestHeaders().getFirst("Authorization"));
          bodies.add(read(exchange.getRequestBody()));
          int status;
          synchronized (statuses) {
            status = statuses.isEmpty() ? 200 : statuses.poll();
          }
          byte[] body = "{\"code\":0,\"message\":\"success\",\"data\":\"{}\"}".getBytes(UTF_8);
          exchange.getResponseHeaders().set("Content-Type", "application/json");
          exchange.sendResponseHeaders(status, body.length);
          OutputStream out = exchange.getResponseBody();
          try {
            out.write(body);
          } finally {
            out.close();
          }
        }
      });
      server.setExecutor(Executors.newCachedThreadPool());
      server.start();
    }

    void close() {
      server.stop(0);
    }

    JSONObject body(int index) {
      return JSON.parseObject(bodies.get(index));
    }
  }

  private static String read(InputStream in) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    byte[] chunk = new byte[1024];
    int n;
    while ((n = in.read(chunk)) > 0) {
      buffer.write(chunk, 0, n);
    }
    return new String(buffer.toByteArray(), UTF_8);
  }

  /** Stands in for OAuth2Authentication on the data-plane side; counts refreshes. */
  private static class StubAuthentication implements Authentication {
    String token = "tok-1";
    String refreshedTo = "tok-refreshed";
    int refreshes;

    @Override
    public AuthenticationType type() {
      return AuthenticationType.OAUTH2;
    }

    @Override
    public AuthenticationAttempt apply(RequestAuthContext context) {
      context.setAuthorizationHeader("Bearer " + token);
      return new AuthenticationAttempt(AuthenticationType.OAUTH2, token);
    }

    @Override
    public RetryDecision onUnauthorized(AuthenticationAttempt attempt, HttpResult response) {
      refreshes++;
      if (refreshedTo == null || refreshedTo.equals(attempt.getCredential())) {
        return RetryDecision.noRetry();
      }
      token = refreshedTo;
      return RetryDecision.retry("Bearer " + token);
    }

    @Override
    public void close() {
    }
  }

  private TigerHttpClient oauth2Client(Authentication authentication) {
    ClientConfig config = new ClientConfig();
    config.authentication = authentication;
    TigerHttpClient client = new TigerHttpClient();
    client.clientConfig(config);
    client.useCustomServerUrl(gateway.url);
    return client;
  }

  private TigerHttpResponse call(TigerHttpClient client, MethodName method) {
    TigerHttpRequest request = new TigerHttpRequest(method);
    request.setBizContent("{}");
    return client.execute(request);
  }

  // ------------------------------------------------------------ request shape

  /** G-02, H-02: the gateway branches on the presence of {@code sign}. */
  @Test
  public void oauth2BodyHasNoSignatureFields() {
    call(oauth2Client(new StubAuthentication()), MethodName.ACCOUNTS);

    JSONObject body = gateway.body(0);
    for (String forbidden : new String[] {"sign", "tiger_id", "sign_type",
        "access_token", "trade_token"}) {
      assertFalse(forbidden + " in an OAuth2 body makes the gateway ignore the Bearer token",
          body.containsKey(forbidden));
    }
  }

  @Test
  public void oauth2BodyKeepsTheBusinessFields() {
    call(oauth2Client(new StubAuthentication()), MethodName.ACCOUNTS);

    JSONObject body = gateway.body(0);
    assertEquals("accounts", body.getString("method"));
    assertNotNull(body.getString("timestamp"));
    assertNotNull(body.getString("charset"));
  }

  /** §9.5: device_id is a business parameter and drives market-data seat arbitration. */
  @Test
  public void deviceIdIsSentInOauth2Mode() {
    call(oauth2Client(new StubAuthentication()), MethodName.ACCOUNTS);
    assertNotNull(gateway.body(0).getString(TigerApiConstants.DEVICE_ID));
  }

  /** G-03: unlike the HK license token, this one carries a scheme. */
  @Test
  public void bearerPrefixIsUsed() {
    call(oauth2Client(new StubAuthentication()), MethodName.ACCOUNTS);
    assertEquals("Bearer tok-1", gateway.authHeaders.get(0));
  }

  // --------------------------------------------------------- 401 retry rules

  /** G-04. */
  @Test
  public void unauthorizedRefreshesAndRetriesOnce() {
    StubAuthentication authentication = new StubAuthentication();
    gateway.statuses.add(401);

    call(oauth2Client(authentication), MethodName.ACCOUNTS);

    assertEquals(2, gateway.authHeaders.size());
    assertEquals("Bearer tok-1", gateway.authHeaders.get(0));
    assertEquals("Bearer tok-refreshed", gateway.authHeaders.get(1));
    assertEquals(1, authentication.refreshes);
  }

  /**
   * G-05: a 401 does not prove the order was not placed, so a retry risks a duplicate. The
   * same reasoning covers cancelling and modifying.
   */
  @Test
  public void orderOperationsAreNeverRetried() {
    for (MethodName method : new MethodName[] {MethodName.PLACE_ORDER,
        MethodName.CANCEL_ORDER, MethodName.MODIFY_ORDER}) {
      gateway.authHeaders.clear();
      gateway.bodies.clear();
      StubAuthentication authentication = new StubAuthentication();
      gateway.statuses.add(401);

      call(oauth2Client(authentication), method);

      assertEquals(method + " must be sent exactly once", 1, gateway.authHeaders.size());
      assertEquals(method + " must not trigger a refresh", 0, authentication.refreshes);
    }
  }

  /** E-04: a refresh returns the same scope, so retrying a 403 is guaranteed to fail. */
  @Test
  public void forbiddenDoesNotTriggerARefresh() {
    StubAuthentication authentication = new StubAuthentication();
    gateway.statuses.add(403);

    call(oauth2Client(authentication), MethodName.ACCOUNTS);

    assertEquals(1, gateway.authHeaders.size());
    assertEquals(0, authentication.refreshes);
  }

  /** C-03: retrying with the same credential just collects the same 401. */
  @Test
  public void unchangedTokenAfterRefreshMeansNoRetry() {
    StubAuthentication authentication = new StubAuthentication();
    authentication.refreshedTo = "tok-1";   // the refresh handed back the same value
    gateway.statuses.add(401);

    call(oauth2Client(authentication), MethodName.ACCOUNTS);

    assertEquals(1, gateway.authHeaders.size());
    assertEquals(1, authentication.refreshes);
  }

  /** Two consecutive 401s must not loop. */
  @Test
  public void retryStopsAfterOneAttempt() {
    StubAuthentication authentication = new StubAuthentication();
    gateway.statuses.add(401);
    gateway.statuses.add(401);

    call(oauth2Client(authentication), MethodName.ACCOUNTS);

    assertEquals(2, gateway.authHeaders.size());
    assertEquals(1, authentication.refreshes);
  }

  // ------------------------------------------------------- signature regression

  /**
   * L-01, L-02: with no Authentication configured nothing about the existing path may
   * change -- the body still carries the signature fields.
   */
  @Test
  public void signatureBodyStillCarriesTheSignatureFields() {
    ClientConfig config = new ClientConfig();
    config.tigerId = "123456";
    config.privateKey = TEST_PRIVATE_KEY;
    config.setEnv(Env.SANDBOX);
    config.failRetryCounts = 0;
    TigerHttpClient client = new TigerHttpClient();
    client.clientConfig(config);
    client.useCustomServerUrl(gateway.url);

    call(client, MethodName.ACCOUNTS);

    JSONObject body = gateway.body(0);
    assertEquals("123456", body.getString("tiger_id"));
    assertNotNull(body.getString("sign"));
    assertNotNull(body.getString("sign_type"));
  }

  /** H-04: signature mode sends the bare license token, with no scheme prefix. */
  @Test
  public void signatureModeSendsTheLicenseTokenUnprefixed() {
    ClientConfig config = new ClientConfig();
    config.tigerId = "123456";
    config.privateKey = TEST_PRIVATE_KEY;
    config.setEnv(Env.SANDBOX);
    config.failRetryCounts = 0;
    config.token = "license-token-value";
    TigerHttpClient client = new TigerHttpClient();
    client.clientConfig(config);
    client.useCustomServerUrl(gateway.url);

    call(client, MethodName.ACCOUNTS);

    assertEquals("license-token-value", gateway.authHeaders.get(0));
  }

  /** H-05: a 401 in signature mode must not go anywhere near the OAuth2 retry path. */
  @Test
  public void signatureModeDoesNotRetryOn401() {
    ClientConfig config = new ClientConfig();
    config.tigerId = "123456";
    config.privateKey = TEST_PRIVATE_KEY;
    config.setEnv(Env.SANDBOX);
    config.failRetryCounts = 0;
    TigerHttpClient client = new TigerHttpClient();
    client.clientConfig(config);
    client.useCustomServerUrl(gateway.url);
    gateway.statuses.add(401);

    call(client, MethodName.ACCOUNTS);

    assertEquals(1, gateway.authHeaders.size());
  }

  // ------------------------------------------------- Authentication contract

  /** A third-party implementation must be usable without a tigerId or private key. */
  @Test
  public void customAuthenticationIsAcceptedWithoutSignatureCredentials() {
    Authentication custom = new Authentication() {
      @Override
      public AuthenticationType type() {
        return AuthenticationType.OAUTH2;
      }

      @Override
      public AuthenticationAttempt apply(RequestAuthContext context) {
        context.setAuthorizationHeader("Bearer custom");
        return new AuthenticationAttempt(AuthenticationType.OAUTH2, "custom");
      }

      @Override
      public RetryDecision onUnauthorized(AuthenticationAttempt attempt, HttpResult response) {
        return RetryDecision.noRetry();
      }

      @Override
      public void close() {
      }
    };

    call(oauth2Client(custom), MethodName.ACCOUNTS);
    assertEquals("Bearer custom", gateway.authHeaders.get(0));
  }

  /** Signature mode with no credentials must still fail loudly, as it always has. */
  @Test
  public void signatureModeStillRequiresATigerId() {
    ClientConfig config = new ClientConfig();
    try {
      new TigerHttpClient().clientConfig(config);
      fail("expected RuntimeException");
    } catch (RuntimeException expected) {
      assertTrue(String.valueOf(expected.getMessage()),
          String.valueOf(expected.getMessage()).contains("tigerId"));
    }
  }

  @Test
  public void oauth2AuthenticationRequiresASessionManager() {
    try {
      new OAuth2Authentication(null);
      fail("expected IllegalArgumentException");
    } catch (IllegalArgumentException expected) {
      // the data plane cannot invent a credential on its own
    }
  }

  /** Only a 401 is a credential problem; everything else must be left alone. */
  @Test
  public void onUnauthorizedIgnoresNon401Statuses() {
    OAuth2SessionManager sessions = OAuth2SessionManager.builder()
        .issuer("http://127.0.0.1:1")
        .clientId("cid")
        .build();
    OAuth2Authentication authentication = new OAuth2Authentication(sessions);
    AuthenticationAttempt attempt =
        new AuthenticationAttempt(AuthenticationType.OAUTH2, "tok-1");

    for (int status : new int[] {200, 403, 500, 503}) {
      HttpResult result = new HttpResult(status, "{}");
      assertFalse("status " + status,
          authentication.onUnauthorized(attempt, result).shouldRetry());
    }
    assertNull(RetryDecision.noRetry().getAuthorizationHeader());
  }
}
