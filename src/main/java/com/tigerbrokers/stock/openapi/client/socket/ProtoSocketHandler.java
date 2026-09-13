package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.auth.oauth2.OAuth2SessionManager;
import com.tigerbrokers.stock.openapi.client.auth.oauth2.OAuth2Token;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Request;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Response;
import com.tigerbrokers.stock.openapi.client.socket.executor.MessageCallbackExecutor;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.ApiCallbackDecoderUtils;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@ChannelHandler.Sharable
public class ProtoSocketHandler extends SimpleChannelInboundHandler<Response> {

  private ApiAuthentication authentication;
  private ApiCallbackDecoder decoder;
  private int clientSendInterval = 0;
  private int clientReceiveInterval = 0;
  public final static int HEART_BEAT_SPAN = 1000;

  /** OAuth2 access-token refresh threshold. */
  private static final long REFRESH_AHEAD_MILLIS = 5 * 60 * 1000L;

  /** Timeout for a pending {@code REFRESH_TOKEN} acknowledgement. */
  private static final long REFRESH_REPLY_TIMEOUT_MILLIS = 30 * 1000L;

  /**
   * The access token this channel is currently authenticated with.
   *
   * <p>Per-channel rather than per-client because it describes the state of one connection:
   * a reconnect builds a new channel and sends a fresh connect message, so the new channel
   * starts with a clean value instead of inheriting a stale one.
   */
  public static final AttributeKey<String> OAUTH_ACCESS_TOKEN =
      AttributeKey.valueOf("tigerOauthAccessToken");

  /** The token carried by an in-flight {@code REFRESH_TOKEN}; null when none is outstanding. */
  public static final AttributeKey<String> OAUTH_REFRESH_PENDING =
      AttributeKey.valueOf("tigerOauthRefreshPending");

  /** Marker held while token exchange is in progress; never sent as a credential. */
  private static final String REFRESH_IN_PROGRESS = "__refresh_in_progress__";

  /** When the in-flight {@code REFRESH_TOKEN} was sent, for the reply timeout. */
  public static final AttributeKey<Long> OAUTH_REFRESH_SENT_AT =
      AttributeKey.valueOf("tigerOauthRefreshSentAt");

  /**
   * Executor for blocking token exchange operations.
   *
   * <p>The daemon executor is shared across connections and serialized to preserve the session
   * manager's single-flight behavior without blocking the socket event loop.</p>
   */
  private static volatile ExecutorService refreshExecutor;

  private static ExecutorService refreshExecutor() {
    ExecutorService local = refreshExecutor;
    if (local == null) {
      synchronized (ProtoSocketHandler.class) {
        local = refreshExecutor;
        if (local == null) {
          local = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
              Thread t = new Thread(r, "tiger-socket-token-refresh");
              t.setDaemon(true);
              return t;
            }
          });
          refreshExecutor = local;
        }
      }
    }
    return local;
  }

  public ProtoSocketHandler(ApiAuthentication authentication, ApiComposeCallback callback) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder(callback);
  }

  public ProtoSocketHandler(ApiAuthentication authentication, ApiComposeCallback callback,
      MessageCallbackExecutor executor, int sendInterval, int receiveInterval) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder(callback, executor);
    this.clientSendInterval = sendInterval;
    this.clientReceiveInterval = receiveInterval;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    // Resolve the current token for each connection attempt. Signature mode returns null.
    String accessToken = authentication.getAccessToken();
    if (authentication.isOauth2() && accessToken == null) {
      // Reject connections that cannot obtain an OAuth2 access token.
      ApiLogger.error("no oauth2 access token available, cannot connect. channel:{}",
          ctx.channel().id().asShortText());
      ctx.close();
      return;
    }
    Request connect = ProtoMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
        authentication.getVersion(), this.clientSendInterval == 0 ? 0 : this.clientSendInterval + HEART_BEAT_SPAN,
        this.clientReceiveInterval == 0 ? 0 : this.clientReceiveInterval - HEART_BEAT_SPAN,
        authentication.getClientConfig().useFullTick, accessToken);
    // toJson(Request) masks the token; passing connect.getConnect() would pick the plain
    // Message overload and write the credential to the log
    ApiLogger.info("netty channel active. channel:{}, preparing to send connect token:{}",
        ctx.channel().id().asShortText(), ProtoMessageUtil.toJson(connect));
    if (accessToken != null) {
      ctx.channel().attr(OAUTH_ACCESS_TOKEN).set(accessToken);
    }
    ctx.writeAndFlush(connect).addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) {
        if (future.isSuccess()) {
          ApiLogger.info("send connect token successfully. channel:{}", ctx.channel().id().asShortText());
        } else {
          ApiLogger.error("failed to send connect token. channel:{}, isDone:{}, cause:{}",
              ctx.channel().id().asShortText(), future.isDone(), future.cause() == null ? null : future.cause().getMessage());
        }
      }
    });
    super.channelActive(ctx);
  }

  /**
   * Rotates the connection access token near expiry.
   *
   * <p>Heartbeat processing performs only in-memory checks. Blocking token exchange is delegated
   * to {@link #refreshExecutor()}. Pending rotations expire after
   * {@link #REFRESH_REPLY_TIMEOUT_MILLIS} when no acknowledgement is received.</p>
   */
  public void refreshTokenIfNeeded(ChannelHandlerContext ctx) {
    if (!authentication.isOauth2()) {
      return;
    }
    final Channel channel = ctx.channel();
    String pending = channel.attr(OAUTH_REFRESH_PENDING).get();
    if (pending != null) {
      Long sentAt = channel.attr(OAUTH_REFRESH_SENT_AT).get();
      if (sentAt != null && System.currentTimeMillis() - sentAt < REFRESH_REPLY_TIMEOUT_MILLIS) {
        // Still waiting for a reply; do not send a second one. Also covers an exchange that
        // is still running on the refresh executor, since the marker is set before it starts.
        return;
      }
      // Clear a timed-out refresh claim so a later attempt can proceed.
      ApiLogger.info("refresh token got no reply within {}ms, channel:{}",
          REFRESH_REPLY_TIMEOUT_MILLIS, channel.id().asShortText());
      channel.attr(OAUTH_REFRESH_PENDING).set(null);
      channel.attr(OAUTH_REFRESH_SENT_AT).set(null);
    }

    OAuth2SessionManager sessions = authentication.getSessionManager();
    OAuth2Token current = sessions == null ? null : sessions.status();
    if (current == null || !current.needsRefresh(REFRESH_AHEAD_MILLIS)) {
      return;
    }

    // Claimed before the exchange starts, not after: the next heartbeat must find a rotation
    // already in progress. The real token replaces this placeholder once it is known, and
    // the reply timeout above releases the claim if the exchange never gets that far.
    channel.attr(OAUTH_REFRESH_PENDING).set(REFRESH_IN_PROGRESS);
    channel.attr(OAUTH_REFRESH_SENT_AT).set(System.currentTimeMillis());
    try {
      refreshExecutor().execute(new Runnable() {
        @Override
        public void run() {
          obtainAndSendToken(channel);
        }
      });
    } catch (Throwable t) {
      // Executor refused the task (shutting down, out of threads). Release the claim, or no
      // later attempt could be made.
      clearRefreshClaim(channel);
      ApiLogger.error("refresh token not scheduled. channel:{}", channel.id().asShortText(), t);
    }
  }

  /**
   * Obtains the rotated token and sends it, off the event loop.
   *
   * <p>The token exchange blocks here, on the refresh thread. The channel attributes are
   * then touched back on the event loop so that all reads and writes of them stay on that
   * one thread and need no locking of their own.
   */
  private void obtainAndSendToken(final Channel channel) {
    final String newToken;
    try {
      // Refreshes when inside the session manager's own window, so this is the rotated token
      newToken = authentication.getAccessToken();
    } catch (Throwable t) {
      clearRefreshClaimLater(channel);
      ApiLogger.error("refresh token fail. channel:{}", channel.id().asShortText(), t);
      return;
    }
    if (newToken == null) {
      // Refresh failed. Not a reason to drop the connection: the token pinned on the server
      // is usually still valid, and the server will close the connection itself if it is not.
      clearRefreshClaimLater(channel);
      ApiLogger.warn("refresh token skipped, no usable access token. channel:{}",
          channel.id().asShortText());
      return;
    }
    channel.eventLoop().execute(new Runnable() {
      @Override
      public void run() {
        if (!channel.isActive()) {
          // Connection went away while the exchange was running. A reconnect builds a new
          // channel and presents the token in its connect message, so there is nothing to do.
          clearRefreshClaim(channel);
          return;
        }
        String pinned = channel.attr(OAUTH_ACCESS_TOKEN).get();
        if (newToken.equals(pinned)) {
          // Unchanged: the session manager's window had not opened yet, so it handed back the
          // token the server already has. Nothing to tell the server.
          clearRefreshClaim(channel);
          return;
        }
        channel.attr(OAUTH_REFRESH_PENDING).set(newToken);
        channel.attr(OAUTH_REFRESH_SENT_AT).set(System.currentTimeMillis());
        channel.writeAndFlush(ProtoMessageUtil.buildRefreshTokenMessage(newToken));
        ApiLogger.info("refresh token sent. channel:{}", channel.id().asShortText());
      }
    });
  }

  /** Must run on the event loop. */
  private static void clearRefreshClaim(Channel channel) {
    channel.attr(OAUTH_REFRESH_PENDING).set(null);
    channel.attr(OAUTH_REFRESH_SENT_AT).set(null);
  }

  /** Hops to the event loop first, for callers on the refresh thread. */
  private static void clearRefreshClaimLater(final Channel channel) {
    try {
      channel.eventLoop().execute(new Runnable() {
        @Override
        public void run() {
          clearRefreshClaim(channel);
        }
      });
    } catch (Throwable t) {
      // Event loop is shutting down. The claim dies with the channel anyway.
      ApiLogger.debug("refresh claim not cleared, channel:{}", channel.id().asShortText());
    }
  }

  /**
   * Applies the server's acknowledgement of a rotation.
   *
   * <p>Only moves the pinned token forward on success. On rejection the pinned token stays
   * as it was, matching what the server still has on the channel -- the server explicitly
   * leaves its own attributes untouched when it rejects a rotation.
   */
  public void onRefreshTokenResponse(ChannelHandlerContext ctx, Response response) {
    Channel channel = ctx.channel();
    String pending = channel.attr(OAUTH_REFRESH_PENDING).get();
    clearRefreshClaim(channel);
    if (response.getCode() == TigerApiCode.SUCCESS.getCode()) {
      // Never pin the in-progress placeholder: a reply arriving while it is still held did not
      // come from a message we sent, so there is no token of ours for it to acknowledge.
      if (pending != null && !REFRESH_IN_PROGRESS.equals(pending)) {
        channel.attr(OAUTH_ACCESS_TOKEN).set(pending);
      }
      ApiLogger.info("refresh token accepted. channel:{}, detail:{}",
          channel.id().asShortText(), response.getMsg());
    } else {
      ApiLogger.warn("refresh token rejected. channel:{}, code:{}, msg:{}",
          channel.id().asShortText(), response.getCode(), response.getMsg());
    }
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    ApiLogger.info("netty channel inactive! channel:{}", ctx.channel().id().asShortText());
    super.channelInactive(ctx);
    ctx.close();
  }

  @Override
  public void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
    ApiLogger.debug("received msg from server: {}", ProtoMessageUtil.toJson(msg));

    try {
      ApiCallbackDecoderUtils.executor(ctx, msg, decoder);
    } catch (Throwable th) {
      ApiLogger.error("api callback fail. response:{}", ProtoMessageUtil.toJson(msg), th);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    ApiLogger.error("handler exception caught, channel:{}", ctx.channel().id(), cause);
    ctx.close();
  }
}
