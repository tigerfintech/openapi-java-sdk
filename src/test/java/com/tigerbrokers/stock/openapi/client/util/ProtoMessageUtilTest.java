package com.tigerbrokers.stock.openapi.client.util;

import com.google.common.collect.Sets;
import com.google.protobuf.InvalidProtocolBufferException;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteData;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author liutongping
 * @date 2022/10/20
 */
@RunWith(MockitoJUnitRunner.class)
public class ProtoMessageUtilTest {

  @Test
  public void testSymbol() {
  }

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
    System.out.println(ProtoMessageUtil.toJson(ProtoMessageUtil.buildUnSubscribeMessage(symbols, QuoteSubject.Quote)));
  }

  public static void testSubscribeQuoteMessage() {
    System.out.println("======SubscribeQuoteMessage");
    Set<String> symbols = Sets.newHashSet("00700");
    System.out.println(ProtoMessageUtil.toJson(ProtoMessageUtil.buildSubscribeMessage(symbols, QuoteSubject.Quote)));
  }

  public static void testUnSubscribeAccountMessage() {
    System.out.println("======UnSubscribeAccountMessage");
    System.out.println(ProtoMessageUtil.toJson(ProtoMessageUtil.buildUnSubscribeMessage(Subject.Position)));
  }
  public static void testSubscribeAccountMessage() {
    System.out.println("======SubscribeAccountMessage");
    System.out.println(ProtoMessageUtil.toJson(ProtoMessageUtil.buildSubscribeMessage("13810712", Subject.Position)));
  }
  public static void testSendMsg() {
    System.out.println("======SEND REQ_SUB_SYMBOLS");
    System.out.println(ProtoMessageUtil.toJson(ProtoMessageUtil.buildSendMessage()));
  }

  public static void testBuildDisconnectMessage() {
    System.out.println(ProtoMessageUtil.toJson(ProtoMessageUtil.buildDisconnectMessage()));
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
    System.out.println(ProtoMessageUtil.toJson(data));

    //System.out.println(JsonFormat.printToString(data));
  }
}
