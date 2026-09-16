package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.socket.ApiAuthentication;
import com.tigerbrokers.stock.openapi.client.socket.ProtoSocketHandler;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.builder.HeaderBuilder;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Response;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Socket in-place token rotation.
 *
 * <p>Covers test-plan cases C-13..C-16 and H-17. No socket is opened: an
 * {@link EmbeddedChannel} records the frames the handler decides to write.</p>
 */
public class ProtoSocketOAuth2Test {

  private static final long REFRESH_REPLY_TIMEOUT_MILLIS = 30_000L;

  /** Mirrors the handler's private in-progress marker. */
  private static final String REFRESH_IN_PROGRESS = "__refresh_in_progress__";

  /**
   * PKCS8 DER base64. A real key is needed even for the signature-mode cases:
   * {@code ApiAuthentication.build} returns null when signing fails.
   */
  private static final String TEST_PRIVATE_KEY;

  static {
    try {
      java.security.KeyPairGenerator generator =
          java.security.KeyPairGenerator.getInstance("RSA");
      generator.initialize(2048);
      java.security.KeyPair pair = generator.generateKeyPair();
      TEST_PRIVATE_KEY =
          java.util.Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
    } catch (Exception e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private FakeAuthorizationServer server;
  private Path home;
  private EmbeddedChannel channel;
  private ProtoSocketHandler handler;

  @Before
  public void setUp() throws IOException {
    server = new FakeAuthorizationServer();
    home = Files.createTempDirectory("tigeropen-oauth2-socket-");
  }

  @After
  public void tearDown() {
    if (channel != null) {
      channel.finishAndReleaseAll();
    }
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

  private OAuth2SessionManager sessions() {
    return OAuth2SessionManager.builder()
        .issuer(server.getIssuer())
        .clientId("cid")
        .home(home.toString())
        .build();
  }

  private void seed(long remainingSeconds, String accessToken) {
    OAuth2Token token = new OAuth2Token();
    token.setIssuer(server.getIssuer());
    token.setClientId("cid");
    token.setAccessToken(accessToken);
    token.setRefreshToken("RT0");
    token.setScope("api.quote:read");
    token.setExpiresAt(System.currentTimeMillis() + remainingSeconds * 1000L);
    new OAuth2TokenStore(home.toString()).saveToken(token);
  }

  /** Wires the handler into an embedded channel; null sessions means signature mode. */
  private ChannelHandlerContext connect(OAuth2SessionManager sessions) {
    ClientConfig config = new ClientConfig();
    config.tigerId = "123456";
    ApiAuthentication authentication = sessions == null
        ? ApiAuthentication.build(config, TEST_PRIVATE_KEY, HeaderBuilder.DEFAULT_VERSION)
        : ApiAuthentication.buildWithOAuth2(config, sessions, HeaderBuilder.DEFAULT_VERSION);
    handler = new ProtoSocketHandler(authentication, null);
    // Added after construction on purpose: an EmbeddedChannel is active as soon as it is
    // built, so passing the handler to the constructor would fire channelActive and run the
    // whole connect handshake. These tests are about rotation, not about connecting.
    channel = new EmbeddedChannel();
    channel.pipeline().addLast(handler);
    return channel.pipeline().context(handler);
  }

  private List<Request> refreshFrames() {
    List<Request> frames = new ArrayList<>();
    Object frame;
    while ((frame = channel.readOutbound()) != null) {
      if (frame instanceof Request
          && ((Request) frame).getCommand() == SocketCommon.Command.REFRESH_TOKEN) {
        frames.add((Request) frame);
      }
    }
    return frames;
  }

  /**
   * The rotation runs on a shared executor and then hops back to the event loop, so the
   * test has to let both run before asserting.
   */
  private List<Request> awaitRefreshFrames() throws InterruptedException {
    long deadline = System.currentTimeMillis() + 5_000;
    while (System.currentTimeMillis() < deadline) {
      channel.runPendingTasks();
      List<Request> frames = refreshFrames();
      if (!frames.isEmpty()) {
        return frames;
      }
      Thread.sleep(10);
    }
    channel.runPendingTasks();
    return refreshFrames();
  }

  private void settle() throws InterruptedException {
    for (int i = 0; i < 50; i++) {
      channel.runPendingTasks();
      Thread.sleep(10);
    }
  }

  private static Response reply(int code) {
    return Response.newBuilder()
        .setCommand(SocketCommon.Command.REFRESH_TOKEN)
        .setCode(code)
        .setMsg("")
        .build();
  }

  /**
   * The exchange budget has to outlast the token exchange's own HTTP timeouts.
   *
   * <p>The two values live in different packages and cannot reference each other, so this is
   * what keeps them in step: raising the read timeout without raising the budget brings back
   * duplicate exchanges under a slow authorization server.</p>
   */
  @Test
  public void theExchangeBudgetOutlastsTheHttpTimeouts() {
    long httpWorstCaseMillis =
        (OAuth2HttpUtils.CONNECT_TIMEOUT_SECONDS + OAuth2HttpUtils.READ_TIMEOUT_SECONDS) * 1000L;
    assertTrue("exchange budget " + ProtoSocketHandler.REFRESH_EXCHANGE_TIMEOUT_MILLIS
            + "ms must exceed the token exchange's own " + httpWorstCaseMillis + "ms",
        ProtoSocketHandler.REFRESH_EXCHANGE_TIMEOUT_MILLIS > httpWorstCaseMillis);
  }

  // ------------------------------------------------------------- triggering

  /**
   * An exchange still inside its budget must not be released.
   *
   * <p>Releasing it starts a second exchange while the first is still going, and the second
   * presents a refresh token the first has already rotated away.</p>
   */
  @Test
  public void anExchangeInsideItsBudgetIsLeftAlone() throws Exception {
    seed(60, "AT-old");
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).set(REFRESH_IN_PROGRESS);
    // Inside the exchange budget.
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_SENT_AT)
        .set(System.currentTimeMillis() - ProtoSocketHandler.REFRESH_EXCHANGE_TIMEOUT_MILLIS
            + 2_000L);

    handler.refreshTokenIfNeeded(ctx);
    settle();

    assertTrue("a second exchange must not be started while the first is running",
        server.tokenCalls().isEmpty());
    assertEquals(REFRESH_IN_PROGRESS,
        channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).get());
  }

  /** The exchange budget still has to expire, or a dead exchange would block every retry. */
  @Test
  public void anExchangePastItsOwnBudgetFreesTheNextAttempt() throws Exception {
    seed(60, "AT-old");
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).set(REFRESH_IN_PROGRESS);
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_SENT_AT)
        .set(System.currentTimeMillis() - ProtoSocketHandler.REFRESH_EXCHANGE_TIMEOUT_MILLIS
            - 1_000L);

    handler.refreshTokenIfNeeded(ctx);
    List<Request> frames = awaitRefreshFrames();

    assertEquals(1, frames.size());
    assertEquals("AT1", frames.get(0).getRefreshToken().getAccessToken());
  }

  /** C-13: a fresh token needs no rotation, so nothing is sent. */
  @Test
  public void freshTokenSendsNothing() throws Exception {
    seed(3600, "AT-current");
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-current");

    handler.refreshTokenIfNeeded(ctx);
    settle();

    assertTrue(refreshFrames().isEmpty());
    assertTrue(server.tokenCalls().isEmpty());
  }

  /**
   * A token advanced elsewhere must reach this connection, even though the session's own token
   * now looks perfectly fresh.
   *
   * <p>What the server checks is the token pinned on this connection, not whatever the session
   * holds. Anything else sharing the session advances it without touching this channel: an HTTP
   * request refreshing first, a 401 retry, another connection rotating, application code asking
   * for a token. Judging by the session alone misses all of those -- by the time the heartbeat
   * looks, the session's token has a full lifetime ahead of it and reports no refresh due, while
   * this connection is still pinned to one that is minutes from being dropped.</p>
   *
   * <p>That was the failure: rotation never fired on the path it was built for, and the
   * connection was left to expire and reconnect, losing its subscriptions on the way.</p>
   */
  @Test
  public void tokenAdvancedElsewhereIsPushedToTheConnection() throws Exception {
    // The session's token is nowhere near expiry, as just after an HTTP request refreshed it.
    seed(3600, "AT-refreshed-by-http");
    ChannelHandlerContext ctx = connect(sessions());
    // This connection still presents the token it connected with -- the one the server holds.
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-pinned-at-connect");

    handler.refreshTokenIfNeeded(ctx);
    List<Request> frames = awaitRefreshFrames();

    assertEquals("the connection must be told about the token the session already holds",
        1, frames.size());
    assertEquals("AT-refreshed-by-http", frames.get(0).getRefreshToken().getAccessToken());
    assertTrue("the session's token is already fresh, so no exchange is needed",
        server.tokenCalls().isEmpty());
  }

  /**
   * A connection that never pinned a token gives no basis for guessing what the server holds,
   * so it falls back to the session's refresh window instead of rotating on every heartbeat.
   */
  @Test
  public void connectionWithoutAPinnedTokenFallsBackToTheRefreshWindow() throws Exception {
    seed(3600, "AT-current");
    ChannelHandlerContext ctx = connect(sessions());
    // OAUTH_ACCESS_TOKEN deliberately left unset.

    handler.refreshTokenIfNeeded(ctx);
    settle();

    assertTrue(refreshFrames().isEmpty());
    assertTrue(server.tokenCalls().isEmpty());
  }

  /** C-13: inside the window the token is rotated in place, without reconnecting. */
  @Test
  public void expiringTokenIsRotatedInPlace() throws Exception {
    seed(60, "AT-old");
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");

    handler.refreshTokenIfNeeded(ctx);
    List<Request> frames = awaitRefreshFrames();

    assertEquals(1, frames.size());
    assertEquals("AT1", frames.get(0).getRefreshToken().getAccessToken());
    assertEquals(SocketCommon.Command.REFRESH_TOKEN, frames.get(0).getCommand());
  }

  /** The session manager hands back the current token until its own window opens. */
  @Test
  public void unchangedTokenIsNotSent() throws Exception {
    // Inside the socket's window but the exchange returns the same value: seed a token that
    // needs refresh, and let the AS hand back the very token already pinned
    seed(60, "AT-same");
    server.queueToken("{\"access_token\":\"AT-same\",\"refresh_token\":\"RT1\","
        + "\"expires_in\":3600,\"scope\":\"api.quote:read\"}");
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-same");

    handler.refreshTokenIfNeeded(ctx);
    settle();

    assertTrue("nothing to tell the server", refreshFrames().isEmpty());
    assertNull(channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).get());
  }

  /** H-17 regression: signature connections must not go near any of this. */
  @Test
  public void signatureModeDoesNothing() throws Exception {
    ChannelHandlerContext ctx = connect(null);

    handler.refreshTokenIfNeeded(ctx);
    settle();

    assertTrue(refreshFrames().isEmpty());
    assertTrue(server.calls.isEmpty());
  }

  /** A failed exchange is no reason to drop a working connection. */
  @Test
  public void failedExchangeKeepsTheConnectionAndClearsTheClaim() throws Exception {
    seed(60, "AT-old");
    server.queueTokenError("temporarily_unavailable");
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");

    handler.refreshTokenIfNeeded(ctx);
    settle();

    assertTrue(refreshFrames().isEmpty());
    assertNull(channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).get());
    assertEquals("the pinned token is untouched",
        "AT-old", channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).get());
  }

  // ----------------------------------------------------------- concurrency

  /** C-15: a pending reply blocks a second send inside the window. */
  @Test
  public void pendingReplyBlocksASecondSend() throws Exception {
    seed(60, "AT-old");
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).set("AT-inflight");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_SENT_AT).set(System.currentTimeMillis());

    handler.refreshTokenIfNeeded(ctx);
    settle();

    assertTrue(refreshFrames().isEmpty());
    assertTrue("no exchange may be started either", server.tokenCalls().isEmpty());
  }

  /**
   * C-14: an old server ignores {@code REFRESH_TOKEN} and never replies. Without the reply
   * timeout the claim would never clear and no later rotation could happen.
   */
  @Test
  public void noReplyWithinTheWindowFreesTheNextAttempt() throws Exception {
    seed(60, "AT-old");
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).set("AT-abandoned");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_SENT_AT)
        .set(System.currentTimeMillis() - REFRESH_REPLY_TIMEOUT_MILLIS - 1);

    handler.refreshTokenIfNeeded(ctx);
    List<Request> frames = awaitRefreshFrames();

    assertEquals(1, frames.size());
    assertEquals("AT1", frames.get(0).getRefreshToken().getAccessToken());
  }

  // --------------------------------------------------------------- replies

  /** C-16: success moves the pinned token forward. */
  @Test
  public void successPinsTheNewToken() {
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).set("AT-new");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_SENT_AT).set(System.currentTimeMillis());

    handler.onRefreshTokenResponse(ctx, reply(TigerApiCode.SUCCESS.getCode()));

    assertEquals("AT-new", channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).get());
    assertNull(channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).get());
  }

  /**
   * C-16: the server explicitly leaves its own channel attributes untouched when it rejects
   * a rotation, so the pinned token must stay as it was.
   */
  @Test
  public void rejectionKeepsThePreviousToken() {
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).set("AT-new");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_SENT_AT).set(System.currentTimeMillis());

    handler.onRefreshTokenResponse(ctx, reply(4001));

    assertEquals("AT-old", channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).get());
    assertNull("a rejection must still free the slot",
        channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).get());
  }

  /** A reply arriving while the placeholder is held cannot match a message we sent. */
  @Test
  public void replyWhileThePlaceholderIsHeldPinsNothing() throws Exception {
    seed(60, "AT-old");
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");
    // Claim the slot the way refreshTokenIfNeeded does, then reply before the exchange ends
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).set("__refresh_in_progress__");
    channel.attr(ProtoSocketHandler.OAUTH_REFRESH_SENT_AT).set(System.currentTimeMillis());

    handler.onRefreshTokenResponse(ctx, reply(TigerApiCode.SUCCESS.getCode()));

    assertEquals("AT-old", channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).get());
    assertNull(channel.attr(ProtoSocketHandler.OAUTH_REFRESH_PENDING).get());
  }

  @Test
  public void unsolicitedReplyIsHarmless() {
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");

    handler.onRefreshTokenResponse(ctx, reply(TigerApiCode.SUCCESS.getCode()));

    assertEquals("AT-old", channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).get());
  }

  /** The whole cycle: heartbeat -> exchange -> send -> acknowledge -> quiet again. */
  @Test
  public void rotationCompletesEndToEnd() throws Exception {
    seed(60, "AT-old");
    ChannelHandlerContext ctx = connect(sessions());
    channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).set("AT-old");

    handler.refreshTokenIfNeeded(ctx);
    List<Request> frames = awaitRefreshFrames();
    assertEquals(1, frames.size());

    handler.onRefreshTokenResponse(ctx, reply(TigerApiCode.SUCCESS.getCode()));
    assertEquals("AT1", channel.attr(ProtoSocketHandler.OAUTH_ACCESS_TOKEN).get());

    // A later heartbeat now finds the token fresh and sends nothing more
    handler.refreshTokenIfNeeded(ctx);
    settle();
    assertTrue(refreshFrames().isEmpty());
  }

  // ------------------------------------------------------- connect message

  /** H-12: the connect message carries the bearer token and no signature material. */
  @Test
  public void oauth2AuthenticationExposesTheTokenAndNoTigerId() {
    seed(3600, "AT-current");
    ClientConfig config = new ClientConfig();
    config.tigerId = "123456";
    ApiAuthentication authentication =
        ApiAuthentication.buildWithOAuth2(config, sessions(), HeaderBuilder.DEFAULT_VERSION);

    assertTrue(authentication.isOauth2());
    assertEquals("AT-current", authentication.getAccessToken());
    assertNull("tigerId comes from the token's sub claim server-side",
        authentication.getTigerId());
    assertNotNull(authentication.getSessionManager());
  }

  /**
   * H-13: this sits on the netty connect path, where an exception would surface as a bare
   * channel failure -- null lets the caller log the real reason instead.
   */
  @Test
  public void noAuthorizationYieldsNullRatherThanThrowing() {
    ClientConfig config = new ClientConfig();
    ApiAuthentication authentication =
        ApiAuthentication.buildWithOAuth2(config, sessions(), HeaderBuilder.DEFAULT_VERSION);

    assertNull(authentication.getAccessToken());
  }

  @Test
  public void signatureAuthenticationIsNotOauth2() {
    ClientConfig config = new ClientConfig();
    config.tigerId = "123456";
    ApiAuthentication authentication =
        ApiAuthentication.build(config, TEST_PRIVATE_KEY, HeaderBuilder.DEFAULT_VERSION);

    assertTrue(!authentication.isOauth2());
    assertNull(authentication.getAccessToken());
    assertNull(authentication.getSessionManager());
    assertEquals("123456", authentication.getTigerId());
  }

  @Test
  public void oauth2RequiresASessionManager() {
    try {
      ApiAuthentication.buildWithOAuth2(new ClientConfig(), null, HeaderBuilder.DEFAULT_VERSION);
      org.junit.Assert.fail("expected IllegalArgumentException");
    } catch (IllegalArgumentException expected) {
      // nothing to authenticate with
    }
  }
}
