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
   * @param sendInterval client能保证发送心跳的最小间隔，0代表采用服务端配置
   * @param receiveInterval client希望收到server心跳的间隔，0代表采用服务端配置
   * @return StompFrame
   */
  public static Request buildConnectMessage(String tigerId, String sign, String version,
      int sendInterval, int receiveInterval) {
    if (sendInterval < 0 || receiveInterval < 0) {
      throw new RuntimeException("sendInterval < 0 or receiveInterval < 0");
    }
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.CONNECT).setId(increment.addAndGet(1));

    Request.Connect.Builder conBuild = Request.Connect.newBuilder();
    conBuild.setAcceptVersion(version)
        .setSdkVersion(SdkVersionUtils.getSdkVersion())
        .setTigerId(tigerId)
        .setSign(sign)
        .setSendInterval(sendInterval).setReceiveInterval(receiveInterval);

    builder.setConnect(conBuild.build());
    return builder.build();
  }

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

  public static Request buildSubscribeMessage(Market market, QuoteSubject subject) {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.SUBSCRIBE)
        .setId(increment.addAndGet(1));

    Request.Subscribe.Builder subBuild = Request.Subscribe.newBuilder();
    subBuild.setDataType(SocketCommon.DataType.valueOf(subject.name()));
    subBuild.setMarket(market.name());

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
    subBuild.setSymbols(HeaderBuilder.join(symbols));

    builder.setSubscribe(subBuild.build());
    return builder.build();
  }

  public static Request buildUnSubscribeMessage(Market market, QuoteSubject subject) {
    Request.Builder builder = Request.newBuilder();
    builder.setCommand(SocketCommon.Command.UNSUBSCRIBE)
        .setId(increment.addAndGet(1));

    Request.Subscribe.Builder subBuild = Request.Subscribe.newBuilder();
    subBuild.setDataType(SocketCommon.DataType.valueOf(subject.name()));
    subBuild.setMarket(market.name());

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
