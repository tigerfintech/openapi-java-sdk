package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Fake authorization server for the OAuth2 tests: discovery, registration, token, device
 * authorization and revocation.
 *
 * <p>Binds 127.0.0.1 on a system-assigned port, so the tests need no real environment. Every
 * call is recorded; responses can be queued to drive a specific branch, and anything not
 * queued falls back to a success.</p>
 */
public class FakeAuthorizationServer implements AutoCloseable {

  private static final Charset UTF_8 = Charset.forName("UTF-8");

  private final HttpServer server;
  private final String baseUrl;

  /** Every request, in order, as (path, form). */
  public final List<Call> calls = new ArrayList<>();
  public final Deque<String> tokenResponses = new ArrayDeque<>();
  public final Deque<String> deviceResponses = new ArrayDeque<>();
  public final Deque<String> registerResponses = new ArrayDeque<>();

  /** When set, a token request blocks on this until released. See {@link #holdTokenEndpoint()}. */
  private volatile CountDownLatch tokenGate;

  /** Counts down when a held token request has arrived and is waiting on the gate. */
  private volatile CountDownLatch tokenRequestArrived;

  /** Ordinal of the token request that should run {@link #tokenRequestHook} before replying. */
  private volatile int hookOnTokenRequest;
  private volatile Runnable tokenRequestHook;

  public volatile int revokeStatus = 200;
  public volatile boolean advertiseRevocation = true;
  public volatile boolean advertiseDevice = true;
  public volatile boolean advertiseRegistration = true;
  public volatile List<String> scopesSupported =
      java.util.Arrays.asList("api.quote:read", "api.trade:read");

  private int serial;

  public static class Call {
    public final String path;
    public final Map<String, String> form;
    public final String body;

    Call(String path, Map<String, String> form, String body) {
      this.path = path;
      this.form = form;
      this.body = body;
    }
  }

  public FakeAuthorizationServer() {
    try {
      server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    } catch (IOException e) {
      throw new IllegalStateException("cannot start fake authorization server", e);
    }
    baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
    server.createContext("/", new Handler());
    server.setExecutor(Executors.newCachedThreadPool());
    server.start();
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public String getIssuer() {
    return baseUrl;
  }

  @Override
  public void close() {
    server.stop(0);
  }

  public synchronized List<Call> callsTo(String path) {
    List<Call> result = new ArrayList<>();
    for (Call call : calls) {
      if (call.path.equals(path)) {
        result.add(call);
      }
    }
    return result;
  }

  public List<Call> tokenCalls() {
    return callsTo("/token");
  }

  /**
   * Runs {@code hook} just before the reply to the {@code ordinal}-th token request, standing
   * in for another process changing shared state mid-exchange.
   */
  public void onTokenRequest(int ordinal, Runnable hook) {
    this.hookOnTokenRequest = ordinal;
    this.tokenRequestHook = hook;
  }

  private void runTokenRequestHook() {
    Runnable hook = this.tokenRequestHook;
    if (hook != null && tokenCalls().size() == hookOnTokenRequest) {
      hook.run();
    }
  }

  /**
   * Makes the next token request block until {@link #releaseTokenEndpoint()} is called, standing
   * in for a slow authorization server.
   *
   * @return a latch that counts down once a token request has actually arrived and is waiting
   */
  public CountDownLatch holdTokenEndpoint() {
    tokenRequestArrived = new CountDownLatch(1);
    tokenGate = new CountDownLatch(1);
    return tokenRequestArrived;
  }

  /** Lets a held token request complete. */
  public void releaseTokenEndpoint() {
    CountDownLatch gate = this.tokenGate;
    this.tokenGate = null;
    if (gate != null) {
      gate.countDown();
    }
  }

  public List<Call> revokeCalls() {
    return callsTo("/revoke");
  }

  /** Queues a raw JSON body for the next token request. */
  public void queueToken(String json) {
    tokenResponses.add(json);
  }

  /** Queues an OAuth2 error response for the next token request. */
  public void queueTokenError(String error) {
    tokenResponses.add("{\"error\":\"" + error + "\"}");
  }

  public void queueDevice(String json) {
    deviceResponses.add(json);
  }

  public void queueDeviceError(String error) {
    deviceResponses.add("{\"error\":\"" + error + "\"}");
  }

  private synchronized String nextToken() {
    if (!tokenResponses.isEmpty()) {
      return tokenResponses.poll();
    }
    serial++;
    JSONObject json = new JSONObject(new LinkedHashMap<String, Object>());
    json.put("access_token", "AT" + serial);
    json.put("refresh_token", "RT" + serial);
    json.put("token_type", "Bearer");
    json.put("expires_in", 3600);
    json.put("scope", "api.quote:read");
    return json.toJSONString();
  }

  private synchronized String nextDevice() {
    if (!deviceResponses.isEmpty()) {
      return deviceResponses.poll();
    }
    JSONObject json = new JSONObject(new LinkedHashMap<String, Object>());
    json.put("device_code", "DEV-CODE");
    json.put("user_code", "ABCD-EFGH");
    json.put("verification_uri", baseUrl + "/activate");
    json.put("expires_in", 600);
    return json.toJSONString();
  }

  private synchronized String nextRegistration() {
    if (!registerResponses.isEmpty()) {
      return registerResponses.poll();
    }
    JSONObject json = new JSONObject(new LinkedHashMap<String, Object>());
    json.put("client_id", "dyn-client");
    json.put("scope", "api.quote:read api.trade:read");
    return json.toJSONString();
  }

  private String metadata() {
    JSONObject json = new JSONObject(new LinkedHashMap<String, Object>());
    json.put("issuer", baseUrl);
    json.put("authorization_endpoint", baseUrl + "/authorize");
    json.put("token_endpoint", baseUrl + "/token");
    if (advertiseRegistration) {
      json.put("registration_endpoint", baseUrl + "/register");
    }
    if (advertiseDevice) {
      json.put("device_authorization_endpoint", baseUrl + "/device");
    }
    if (advertiseRevocation) {
      json.put("revocation_endpoint", baseUrl + "/revoke");
    }
    json.put("scopes_supported", scopesSupported);
    return json.toJSONString();
  }

  private class Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      String path = exchange.getRequestURI().getPath();
      if ("GET".equals(exchange.getRequestMethod())) {
        respond(exchange, 200, metadata());
        return;
      }
      String body = read(exchange.getRequestBody());
      synchronized (FakeAuthorizationServer.this) {
        calls.add(new Call(path, parseForm(body), body));
      }
      if ("/revoke".equals(path)) {
        respond(exchange, revokeStatus, "");
      } else if ("/register".equals(path)) {
        respond(exchange, 200, nextRegistration());
      } else if ("/device".equals(path)) {
        respond(exchange, 200, nextDevice());
      } else {
        awaitTokenGate();
        runTokenRequestHook();
        respond(exchange, 200, nextToken());
      }
    }
  }

  /**
   * Blocks a token request until the gate is opened, so a test can hold an exchange in flight
   * and observe what other callers do meanwhile.
   */
  private void awaitTokenGate() {
    CountDownLatch gate = this.tokenGate;
    if (gate == null) {
      return;
    }
    tokenRequestArrived.countDown();
    try {
      // Bounded so a mistake in a test fails as a test rather than hanging the build.
      gate.await(30, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private static void respond(HttpExchange exchange, int status, String body) throws IOException {
    byte[] bytes = body.getBytes(UTF_8);
    exchange.getResponseHeaders().set("Content-Type", "application/json");
    exchange.sendResponseHeaders(status, bytes.length);
    OutputStream out = exchange.getResponseBody();
    try {
      out.write(bytes);
    } finally {
      out.close();
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

  private static Map<String, String> parseForm(String raw) {
    Map<String, String> form = new LinkedHashMap<>();
    if (raw == null || raw.isEmpty() || raw.startsWith("{")) {
      if (raw != null && raw.startsWith("{")) {
        JSONObject json = JSON.parseObject(raw);
        for (String key : json.keySet()) {
          Object value = json.get(key);
          form.put(key, value == null ? null : String.valueOf(value));
        }
      }
      return form;
    }
    for (String pair : raw.split("&")) {
      int eq = pair.indexOf('=');
      if (eq < 0) {
        continue;
      }
      try {
        form.put(URLDecoder.decode(pair.substring(0, eq), "UTF-8"),
            URLDecoder.decode(pair.substring(eq + 1), "UTF-8"));
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }
    return form;
  }
}
