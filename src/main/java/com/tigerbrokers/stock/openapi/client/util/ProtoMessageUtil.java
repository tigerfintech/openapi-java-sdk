package com.tigerbrokers.stock.openapi.client.util;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.ApiMsg;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Request;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.client.util.builder.StompHeaderBuilder;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.stomp.DefaultStompFrame;
import io.netty.handler.codec.stomp.StompCommand;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.handler.codec.stomp.StompHeaders;
import java.nio.charset.Charset;
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
      //
    }
    return null;
  }

  /**
   * @param login login
   * @param passcode passcode
   * @param version version
   * @return StompFrame
   */
  public static ApiMsg buildConnectMessage(String login, String passcode, String version) {
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.CONNECT);
    builder.setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setAcceptVersion(version)
        .setSdkVersion(SdkVersionUtils.getSdkVersion())
        .setId(increment.addAndGet(1))
        .setLogin(login)
        .setPasscode(passcode);

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  /**
   * @param login login
   * @param passcode passcode
   * @param version version
   * @param sendInterval client能保证发送心跳的最小间隔，0代表client不发送心跳
   * @param receiveInterval client希望收到server心跳的间隔，0代表client不希望收到server的心跳
   * @return StompFrame
   */
  public static ApiMsg buildConnectMessage(String login, String passcode, String version, int sendInterval,
      int receiveInterval) {
    if (sendInterval < 0 || receiveInterval < 0) {
      throw new RuntimeException("sendInterval < 0 or receiveInterval < 0");
    }
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.CONNECT);
    builder.setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setAcceptVersion(version)
        .setSdkVersion(SdkVersionUtils.getSdkVersion())
        .setId(increment.addAndGet(1))
        .setLogin(login)
        .setPasscode(passcode)
        .setHeartBeat(String.format("%d,%d", sendInterval, receiveInterval));

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  public static StompFrame buildSendMessage(int reqType, String message) {
    if (reqType <= 0) {
      throw new RuntimeException("reqType不能为空");
    }
    StompFrame stompFrame;
    int id = increment.addAndGet(1);
    if (message != null) {
      stompFrame =
          new DefaultStompFrame(StompCommand.SEND, ByteBufAllocator.DEFAULT.buffer().writeBytes(
              message.getBytes(Charset.defaultCharset())));
    } else {
      stompFrame = new DefaultStompFrame(StompCommand.SEND);
    }
    StompHeaders headers = StompHeaderBuilder.instance().id(id).host().reqType(reqType).build();
    stompFrame.headers().set(headers);

    return stompFrame;
  }

  public static StompFrame buildCommonSendMessage(String message) {
    StompFrame stompFrame;
    int id = increment.addAndGet(1);
    if (message != null) {
      stompFrame =
          new DefaultStompFrame(StompCommand.SEND, ByteBufAllocator.DEFAULT.buffer().writeBytes(
              message.getBytes(Charset.defaultCharset())));
    } else {
      stompFrame = new DefaultStompFrame(StompCommand.SEND);
    }
    StompHeaders headers = StompHeaderBuilder.instance().id(id).host().build();
    stompFrame.headers().set(headers);

    return stompFrame;
  }

  public static StompFrame buildSubscribeMessage(Subject subject) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.SUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers = StompHeaderBuilder.instance().id(id).host().subject(subject).build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildSubscribeMessage(String account, Subject subject, Set<String> focusKeys) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.SUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers = StompHeaderBuilder.instance()
        .id(id)
        .account(account)
        .host()
        .subject(subject)
        .focusKeys(focusKeys)
        .build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildSubscribeMessage(Set<String> symbols, QuoteSubject subject) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.SUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers =
        StompHeaderBuilder.instance()
            .id(id)
            .host()
            .subject(subject.name())
            .symbols(symbols)
            .build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildSubscribeMessage(Set<String> symbols, QuoteSubject subject, Set<String> focusKeys) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.SUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers =
        StompHeaderBuilder.instance()
            .id(id)
            .host()
            .subject(subject.name())
            .symbols(symbols)
            .focusKeys(focusKeys)
            .build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildSubscribeMessage(Subject subject, Set<String> focusKeys) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.SUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers =
        StompHeaderBuilder.instance().id(id).host().subject(subject).focusKeys(focusKeys).build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildUnSubscribeMessage(Subject subject) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.UNSUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers = StompHeaderBuilder.instance().id(id).host().subject(subject).build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildUnSubscribeMessage(Set<String> symbols, QuoteSubject subject) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.UNSUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers =
        StompHeaderBuilder.instance().id(id).host().subject(subject.name()).symbols(symbols).build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildDisconnectMessage(String login) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.DISCONNECT);
    StompHeaders headers = StompHeaderBuilder.instance().login(login).host().build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static void main(String[] args) {
    // TODO delete
    QuoteData.Builder build = QuoteData.newBuilder();
     build.setSymbol("00700");
    //build.setSymbol(null);不能设置为null
    // build.setAskSize(Int32Value.of(1));
    build.setAskSize(1);
    build.setBidSize(68);
    QuoteData data = build.build();

    test(data);

    byte[] bytes = data.toByteArray();

    try {
      QuoteData data2 = QuoteData.parseFrom(bytes);
      test(data2);
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
    }
  }

  public static void test(QuoteData data) {
    System.out.println("==================");
    System.out.println(data.getSymbol());
    System.out.println(data.hasVolume() + "--" + data.getVolume());
    System.out.println(data.hasAskSize() + "--" + data.getAskSize());
    System.out.println(data.hasBidSize() + "--" + data.getBidSize());
    System.out.println(data.hasLatestPrice() + "--" + data.getLatestPrice());
    System.out.println(toJson(data));

    //System.out.println(JsonFormat.printToString(data));
  }
}
