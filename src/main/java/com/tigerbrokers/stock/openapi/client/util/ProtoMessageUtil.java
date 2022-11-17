package com.tigerbrokers.stock.openapi.client.util;

import com.google.common.collect.Sets;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import com.tigerbrokers.stock.openapi.client.constant.ReqProtocolType;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.ApiMsg;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Request;
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
    builder.setCommand(ApiMsg.Command.CONNECT).setId(increment.addAndGet(1));
    builder.setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setAcceptVersion(version)
        .setSdkVersion(SdkVersionUtils.getSdkVersion())
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
  public static ApiMsg buildConnectMessage(String login, String passcode, String version,
      int sendInterval, int receiveInterval) {
    if (sendInterval < 0 || receiveInterval < 0) {
      throw new RuntimeException("sendInterval < 0 or receiveInterval < 0");
    }
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.CONNECT).setId(increment.addAndGet(1));
    builder.setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setAcceptVersion(version)
        .setSdkVersion(SdkVersionUtils.getSdkVersion())
        .setLogin(login)
        .setPasscode(passcode)
        .setHeartBeat(String.format("%d,%d", sendInterval, receiveInterval));

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  public static ApiMsg buildSendMessage(int reqType, String message) {
    if (reqType <= 0) {
      throw new RuntimeException("reqType不能为空");
    }
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.SEND)
        .setId(increment.addAndGet(1))
        .setRetType(reqType);
    if (message != null) {
      builder.setMessage(message);
    }

    return builder.build();
  }

  public static ApiMsg buildCommonSendMessage(String message) {
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.SEND)
        .setId(increment.addAndGet(1));
    if (message != null) {
      builder.setMessage(message);
    }

    return builder.build();
  }

  public static ApiMsg buildSubscribeMessage(Subject subject) {
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.SUBSCRIBE)
        .setId(increment.addAndGet(1))
        .setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setSubject(subject.name());

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  public static ApiMsg buildSubscribeMessage(String account, Subject subject, Set<String> focusKeys) {
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.SUBSCRIBE)
        .setId(increment.addAndGet(1))
        .setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setSubject(subject.name());
    if (account != null) {
      reqBuild.setAccount(account);
    }
    if (focusKeys != null && !focusKeys.isEmpty()) {
      reqBuild.setFocusKeys(HeaderBuilder.join(focusKeys));
    }

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  public static ApiMsg buildSubscribeMessage(Set<String> symbols, QuoteSubject subject) {
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.SUBSCRIBE)
        .setId(increment.addAndGet(1))
        .setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setSubject(subject.name())
        .setSymbols(HeaderBuilder.join(symbols));

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  public static ApiMsg buildSubscribeMessage(Set<String> symbols, QuoteSubject subject, Set<String> focusKeys) {
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.SUBSCRIBE)
        .setId(increment.addAndGet(1))
        .setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setSubject(subject.name())
        .setSymbols(HeaderBuilder.join(symbols));
    if (focusKeys != null && !focusKeys.isEmpty()) {
      reqBuild.setFocusKeys(HeaderBuilder.join(focusKeys));
    }

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  public static ApiMsg buildSubscribeMessage(Subject subject, Set<String> focusKeys) {
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.SUBSCRIBE)
        .setId(increment.addAndGet(1))
        .setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setSubject(subject.name());
    if (focusKeys != null && !focusKeys.isEmpty()) {
      reqBuild.setFocusKeys(HeaderBuilder.join(focusKeys));
    }

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  public static ApiMsg buildUnSubscribeMessage(Subject subject) {
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.UNSUBSCRIBE)
        .setId(increment.addAndGet(1))
        .setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setSubject(subject.name());

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  public static ApiMsg buildUnSubscribeMessage(Set<String> symbols, QuoteSubject subject) {
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.UNSUBSCRIBE)
        .setId(increment.addAndGet(1))
        .setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setSubject(subject.name())
        .setSymbols(HeaderBuilder.join(symbols));

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  public static ApiMsg buildDisconnectMessage(String login) {
    ApiMsg.Builder builder = ApiMsg.newBuilder();
    builder.setCommand(ApiMsg.Command.DISCONNECT).setId(increment.addAndGet(1));
    builder.setDataType(ApiMsg.Type.Request);

    Request.Builder reqBuild = Request.newBuilder();
    reqBuild.setLogin(login);

    builder.setRequest(reqBuild.build());
    return builder.build();
  }

  ///==============TODO delete
  public static void main(String[] args) {
    //testBuildDisconnectMessage();
    //testDataParse();

    testSendMsg();
    testSubscribeAccountMessage();
    testUnSubscribeAccountMessage();
    testSubscribeQuoteMessage();
    testUnSubscribeQuoteMessage();

  }

  public static void testUnSubscribeQuoteMessage() {
    System.out.println("======UnSubscribeQuoteMessage");
    Set<String> symbols = Sets.newHashSet("00700");
    System.out.println(toJson(buildUnSubscribeMessage(symbols, QuoteSubject.Quote)));
  }

  public static void testSubscribeQuoteMessage() {
    System.out.println("======SubscribeQuoteMessage");
    Set<String> symbols = Sets.newHashSet("00700");
    System.out.println(toJson(buildSubscribeMessage(symbols, QuoteSubject.Quote, null)));
  }

  public static void testUnSubscribeAccountMessage() {
    System.out.println("======UnSubscribeAccountMessage");
    System.out.println(toJson(buildUnSubscribeMessage(Subject.Position)));
  }
  public static void testSubscribeAccountMessage() {
    System.out.println("======SubscribeAccountMessage");
    System.out.println(toJson(buildSubscribeMessage("13810712", Subject.Position, null)));
  }
  public static void testSendMsg() {
    System.out.println("======SEND REQ_SUB_SYMBOLS");
    System.out.println(toJson(buildSendMessage(ReqProtocolType.REQ_SUB_SYMBOLS, null)));
  }

  public static void testBuildDisconnectMessage() {
    System.out.println(toJson(buildDisconnectMessage("20151141")));
  }

  public static void testDataParse() {
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
