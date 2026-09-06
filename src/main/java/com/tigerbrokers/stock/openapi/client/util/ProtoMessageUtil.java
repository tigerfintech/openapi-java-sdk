package com.tigerbrokers.stock.openapi.client.util;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Request;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.client.util.builder.HeaderBuilder;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 * Created by bean on 2022/11/07.
 */
public class ProtoMessageUtil {

  private static AtomicInteger increment = new AtomicInteger(0);

  public static String toJson(Message message) {
    if (null == message) {
      return null;
    }
    try {
      return JsonFormat.printer().omittingInsignificantWhitespace().print(message);
    } catch (InvalidProtocolBufferException e) {
      // ingore
    }
    return null;
  }

  /**
   * @param tigerId tigerId
   * @param sign sign
   * @param version version
   * @param sendInterval The client can guarantee the minimum interval for sending heartbeats, 0 means adopting server configuration
   * @param receiveInterval The interval at which the client expects to receive the heartbeat from the server, 0 means the server configuration is used
   * @return StompFrame
   */
  public static Request buildConnectMessage(String tigerId, String sign, String version,
      int sendInterval, int receiveInterval, boolean useFullTick) {
    return buildConnectMessage(tigerId, sign, version, sendInterval, receiveInterval, useFullTick, null);
  }

  /**
   * @param tigerId tigerId, null in OAuth2 mode (the server takes it from the token's sub claim)
   * @param sign sign, null in OAuth2 mode
   * @param version version
   * @param sendInterval The client can guarantee the minimum interval for sending heartbeats, 0 means adopting server configuration
   * @param receiveInterval The interval at which the client expects to receive the heartbeat from the server, 0 means the server configuration is used
   * @param accessToken OAuth2 access token; when non-empty, tigerId and sign are left unset
   * @return Request
   */
  public static Request buildConnectMessage(String tigerId, String sign, String version,
      int sendInterval, int receiveInterval, boolean useFullTick, String accessToken) {
    if (sendInterval < 0 || receiveInterval < 0) {
      throw new RuntimeException("sendInterval < 0 or receiveInterval < 0");
    }
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.CONNECT).setId(increment.addAndGet(1));

    Request.Connect.Builder conBuild = Request.Connect.newBuilder();
    conBuild.setAcceptVersion(version)
        .setSdkVersion(SdkVersionUtils.getSdkVersion())
        .setUseFullTick(useFullTick)
        .setSendInterval(sendInterval).setReceiveInterval(receiveInterval);

    // Exclusive by construction: the server branches on "is sign blank", so sending both
    // would silently take the signature branch and ignore the token.
    if (StringUtils.isEmpty(accessToken)) {
      // proto3 rejects null for a non-optional string; empty string keeps the wire bytes
      // identical to before, since proto3 does not serialize an empty scalar either way
      conBuild.setTigerId(tigerId == null ? "" : tigerId)
          .setSign(sign == null ? "" : sign);
    } else {
      conBuild.setAccessToken(accessToken);
    }

    builder.setConnect(conBuild.build());
    return builder.build();
  }

  /**
   * Rotates the access token on an established connection, without reconnecting.
   *
   * <p>An access token has a TTL of about a day while a socket connection can live much
   * longer, so a long-lived connection will outlive its token. Without this the server's
   * periodic revalidation keeps checking a token the client has already replaced, finds it
   * expired, and closes a connection that should still be valid.
   *
   * <p>An old server does not know {@code REFRESH_TOKEN} and silently ignores it (the
   * default branch of its command switch), so the caller must not wait for a reply
   * indefinitely.
   */
  public static Request buildRefreshTokenMessage(String accessToken) {
    if (StringUtils.isEmpty(accessToken)) {
      throw new IllegalArgumentException("accessToken is empty");
    }
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.REFRESH_TOKEN)
        .setId(increment.addAndGet(1));
    builder.setRefreshToken(Request.RefreshToken.newBuilder().setAccessToken(accessToken).build());
    return builder.build();
  }

  /**
   * Masks the access token before a {@code Request} reaches the log.
   *
   * <p>A {@code Request} can carry a token in <b>two</b> places: {@code connect.accessToken}
   * and {@code refreshToken.accessToken}. Without this, the existing
   * {@code toJson(connect)} log line would write a usable Bearer credential into the log
   * file verbatim.
   *
   * <p>An <b>overload</b> rather than a new method name, so existing {@code toJson(request)}
   * call sites move here on their own -- the compiler picks the more specific overload when
   * the static type is {@code Request}. Nothing is copied when no token is present.
   *
   * <p>If {@code Request} later gains another token-bearing field, <b>add it here too</b>,
   * or it will silently reach the log.
   */
  public static String toJson(Request request) {
    if (null == request) {
      return null;
    }
    boolean hasConnectToken =
        request.hasConnect() && !StringUtils.isEmpty(request.getConnect().getAccessToken());
    boolean hasRefreshToken =
        request.hasRefreshToken() && !StringUtils.isEmpty(request.getRefreshToken().getAccessToken());
    if (!hasConnectToken && !hasRefreshToken) {
      return toJson((Message) request);
    }
    Request.Builder redacted = request.toBuilder();
    if (hasConnectToken) {
      redacted.getConnectBuilder().setAccessToken(REDACTED);
    }
    if (hasRefreshToken) {
      redacted.getRefreshTokenBuilder().setAccessToken(REDACTED);
    }
    return toJson((Message) redacted.build());
  }

  private static final String REDACTED = "***";

  public static Request buildSendMessage() {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.SEND)
        .setId(increment.addAndGet(1));

    return builder.build();
  }

  public static Request buildHeartBeatMessage() {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.HEARTBEAT)
        .setId(increment.addAndGet(1));

    return builder.build();
  }

  public static Request buildSubscribeMessage(Subject subject) {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.SUBSCRIBE)
        .setId(increment.addAndGet(1));

    Request.Subscribe.Builder subBuild = Request.Subscribe.newBuilder();
    subBuild.setDataType(SocketCommon.DataType.valueOf(subject.name()));

    builder.setSubscribe(subBuild.build());
    return builder.build();
  }

  public static Request buildSubscribeMessage(String account, Subject subject) {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.SUBSCRIBE)
        .setId(increment.addAndGet(1));

    Request.Subscribe.Builder subBuild = Request.Subscribe.newBuilder();
    subBuild.setDataType(SocketCommon.DataType.valueOf(subject.name()));
    if (account != null) {
      subBuild.setAccount(account);
    }

    builder.setSubscribe(subBuild.build());
    return builder.build();
  }

  public static Request buildSubscribeMessage(Set<String> symbols, QuoteSubject subject) {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.SUBSCRIBE)
        .setId(increment.addAndGet(1));

    Request.Subscribe.Builder subBuild = Request.Subscribe.newBuilder();
    subBuild.setDataType(SocketCommon.DataType.valueOf(subject.name()));
    subBuild.setSymbols(HeaderBuilder.join(symbols));

    builder.setSubscribe(subBuild.build());
    return builder.build();
  }

  public static Request buildSubscribeMessage(Market market, QuoteSubject subject, Set<String> indicatorNames) {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.SUBSCRIBE)
        .setId(increment.addAndGet(1));

    Request.Subscribe.Builder subBuild = Request.Subscribe.newBuilder();
    subBuild.setDataType(SocketCommon.DataType.valueOf(subject.name()));
    subBuild.setMarket(market.name());
    if (indicatorNames != null) {
      subBuild.setSymbols(HeaderBuilder.join(indicatorNames));
    }

    builder.setSubscribe(subBuild.build());
    return builder.build();
  }

  public static Request buildUnSubscribeMessage(Subject subject) {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.UNSUBSCRIBE)
        .setId(increment.addAndGet(1));

    Request.Subscribe.Builder subBuild = Request.Subscribe.newBuilder();
    subBuild.setDataType(SocketCommon.DataType.valueOf(subject.name()));

    builder.setSubscribe(subBuild.build());
    return builder.build();
  }

  public static Request buildUnSubscribeMessage(Set<String> symbols, QuoteSubject subject) {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.UNSUBSCRIBE)
        .setId(increment.addAndGet(1));

    Request.Subscribe.Builder subBuild = Request.Subscribe.newBuilder();
    subBuild.setDataType(SocketCommon.DataType.valueOf(subject.name()));
    if (symbols != null) {
      subBuild.setSymbols(HeaderBuilder.join(symbols));
    }

    builder.setSubscribe(subBuild.build());
    return builder.build();
  }

  public static Request buildUnSubscribeMessage(Market market, QuoteSubject subject, Set<String> symbols) {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.UNSUBSCRIBE)
        .setId(increment.addAndGet(1));

    Request.Subscribe.Builder subBuild = Request.Subscribe.newBuilder();
    subBuild.setDataType(SocketCommon.DataType.valueOf(subject.name()));
    subBuild.setMarket(market.name());
    if (symbols != null) {
      subBuild.setSymbols(HeaderBuilder.join(symbols));
    }

    builder.setSubscribe(subBuild.build());
    return builder.build();
  }

  public static Request buildDisconnectMessage() {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.DISCONNECT)
        .setId(increment.addAndGet(1));

    return builder.build();
  }
}
