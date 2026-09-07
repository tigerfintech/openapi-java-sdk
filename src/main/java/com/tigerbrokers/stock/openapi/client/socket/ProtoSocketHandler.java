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

  /**
   * How early to rotate the token before it expires.
   *
   * <p>Deliberately the same as {@code OAuth2SessionManager}'s default refresh-ahead window.
   * A wider window here would not buy anything: this check decides only whether to <i>ask</i>
   * for a token, and the session manager hands back the existing one until its own window
   * opens. The two disagreeing just means the extra margin is spent finding the token
   * unchanged and doing nothing.
   *
   * <p>Both are fixed constants and neither is configurable, so they cannot drift apart at
   * runtime. If either is ever made tunable, the effective window becomes the <i>smaller</i>
   * of the two: a rotation message goes out only where both agree the token is due, since this
   * check has to fire before the session manager is even asked, and the session manager has to
   * hand back a changed token before there is anything to send.
   */
  private static final long REFRESH_AHEAD_MILLIS = 5 * 60 * 1000L;

  /**
   * How long to wait for a {@code REFRESH_TOKEN} reply before giving up on that attempt.
   *
   * <p>Required, not optional: an old server silently ignores the command, so without a
   * timeout the pending marker would never clear and no later attempt could ever be made.
   */
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

  /**
   * Placeholder held in {@link #OAUTH_REFRESH_PENDING} while the token exchange is still
   * running and the new token is not known yet.
   *
   * <p>The marker has to be claimed before the exchange starts, or the next heartbeat would
   * start a second one. Not a real token, and never sent: it is replaced by the actual value
   * before the {@code REFRESH_TOKEN} message goes out.
   */
  private static final String REFRESH_IN_PROGRESS = "__refresh_in_progress__";

  /** When the in-flight {@code REFRESH_TOKEN} was sent, for the reply timeout. */
  public static final AttributeKey<Long> OAUTH_REFRESH_SENT_AT =
      AttributeKey.valueOf("tigerOauthRefreshSentAt");

  /**
   * Runs the token exchange, which must not happen on the event loop.
   *
   * <p>{@code OAuth2SessionManager.refresh} is a synchronous HTTP call under a lock, and the
   * socket runs on a single-threaded {@code NioEventLoopGroup}. Left inline it would stall
   * every read and write on this connection for as long as the exchange takes -- long enough
   * to trip the read-idle timeout, so a rotation meant to keep the connection alive would be
   * what kills it.
   *
   * <p>Static and lazily created: shared by every connection in the process, and never
   * started at all unless some connection actually rotates a token. Daemon, so it does not
   * hold up JVM exit; single-threaded, so the exchanges it runs stay serialized just as the
   * session manager's own lock would have them.
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
    // Fetched here rather than cached at build time: every reconnect goes through
    // channelActive, and a reconnect must present the token that is valid now.
    // Null in signature mode, which is what makes the legacy connect message unchanged.
    //
    // Kept inline even though this can trigger a blocking token exchange, unlike the
    // rotation path. There is no established connection yet to stall: nothing has been
    // subscribed, no heartbeat is running, and the only thing delayed is this connect
    // attempt, which the caller already treats as asynchronous.
    String accessToken = authentication.getAccessToken();
    if (authentication.isOauth2() && accessToken == null) {
      // Connecting without a credential would just get rejected by the server, and the
      // reason (never authorized / refresh failed) would be lost by then
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
   * Rotates this connection's access token if the current one is close to expiry.
   *
   * <p>Driven by the heartbeat rather than a timer of its own: the heartbeat already runs on
   * a live connection at a known interval, and a token with a day-long TTL does not need
   * finer granularity than that. A separate timer would have to track connection liveness
   * all over again.
   *
   * <p>Silent on an old server. {@code REFRESH_TOKEN} lands in the default branch of the
   * server's command switch and no reply ever comes, so a pending rotation is abandoned
   * after {@link #REFRESH_REPLY_TIMEOUT_MILLIS} instead of blocking further attempts
   * forever. The connection keeps working until the server drops it.
   *
   * <p>Runs on the event loop and stays cheap: everything here is an in-memory check. The
   * one part that can block -- obtaining the token -- is handed to {@link #refreshExecutor()}.
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
      // No reply within the window: either an old server that ignores the command, the reply
      // was lost, or the exchange itself is stuck. Clear it so a later attempt can happen.
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
