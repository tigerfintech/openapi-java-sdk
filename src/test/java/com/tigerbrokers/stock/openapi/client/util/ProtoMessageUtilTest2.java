package com.tigerbrokers.stock.openapi.client.util;

import com.google.common.collect.Sets;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Request;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for {@link ProtoMessageUtil}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProtoMessageUtilTest2 {

  @Test
  public void testBuildConnectMessage() {
    Request request = ProtoMessageUtil.buildConnectMessage("tigerId001", "sign123", "3", 5000, 5000, false);
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.CONNECT, request.getCommand());
    Assert.assertTrue(request.getId() > 0);
    Assert.assertEquals("tigerId001", request.getConnect().getTigerId());
    Assert.assertEquals("sign123", request.getConnect().getSign());
    Assert.assertEquals("3", request.getConnect().getAcceptVersion());
    Assert.assertEquals(5000, request.getConnect().getSendInterval());
    Assert.assertEquals(5000, request.getConnect().getReceiveInterval());
    Assert.assertFalse(request.getConnect().getUseFullTick());
  }

  @Test(expected = RuntimeException.class)
  public void testBuildConnectMessageNegativeSendInterval() {
    ProtoMessageUtil.buildConnectMessage("tigerId001", "sign123", "3", -1, 5000, false);
  }

  @Test(expected = RuntimeException.class)
  public void testBuildConnectMessageNegativeReceiveInterval() {
    ProtoMessageUtil.buildConnectMessage("tigerId001", "sign123", "3", 5000, -1, false);
  }

  @Test
  public void testBuildSendMessage() {
    Request request = ProtoMessageUtil.buildSendMessage();
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.SEND, request.getCommand());
    Assert.assertTrue(request.getId() > 0);
  }

  @Test
  public void testBuildHeartBeatMessage() {
    Request request = ProtoMessageUtil.buildHeartBeatMessage();
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.HEARTBEAT, request.getCommand());
    Assert.assertTrue(request.getId() > 0);
  }

  @Test
  public void testBuildSubscribeMessageSubject() {
    Request request = ProtoMessageUtil.buildSubscribeMessage(Subject.Position);
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.SUBSCRIBE, request.getCommand());
    Assert.assertTrue(request.getId() > 0);
    Assert.assertEquals(SocketCommon.DataType.Position, request.getSubscribe().getDataType());
  }

  @Test
  public void testBuildSubscribeMessageWithAccount() {
    Request request = ProtoMessageUtil.buildSubscribeMessage("13810712", Subject.Position);
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.SUBSCRIBE, request.getCommand());
    Assert.assertEquals("13810712", request.getSubscribe().getAccount());
    Assert.assertEquals(SocketCommon.DataType.Position, request.getSubscribe().getDataType());
  }

  @Test
  public void testBuildSubscribeMessageWithNullAccount() {
    Request request = ProtoMessageUtil.buildSubscribeMessage(null, Subject.Position);
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.SUBSCRIBE, request.getCommand());
    Assert.assertEquals("", request.getSubscribe().getAccount());
  }

  @Test
  public void testBuildSubscribeMessageWithSymbols() {
    Set<String> symbols = Sets.newHashSet("00700");
    Request request = ProtoMessageUtil.buildSubscribeMessage(symbols, QuoteSubject.Quote);
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.SUBSCRIBE, request.getCommand());
    Assert.assertEquals(SocketCommon.DataType.Quote, request.getSubscribe().getDataType());
    Assert.assertEquals("00700", request.getSubscribe().getSymbols());
  }

  @Test
  public void testBuildUnSubscribeMessageSubject() {
    Request request = ProtoMessageUtil.buildUnSubscribeMessage(Subject.Position);
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.UNSUBSCRIBE, request.getCommand());
    Assert.assertEquals(SocketCommon.DataType.Position, request.getSubscribe().getDataType());
  }

  @Test
  public void testBuildUnSubscribeMessageWithSymbols() {
    Set<String> symbols = Sets.newHashSet("00700");
    Request request = ProtoMessageUtil.buildUnSubscribeMessage(symbols, QuoteSubject.Quote);
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.UNSUBSCRIBE, request.getCommand());
    Assert.assertEquals(SocketCommon.DataType.Quote, request.getSubscribe().getDataType());
  }

  @Test
  public void testBuildUnSubscribeMessageWithNullSymbols() {
    Request request = ProtoMessageUtil.buildUnSubscribeMessage(null, QuoteSubject.Quote);
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.UNSUBSCRIBE, request.getCommand());
    Assert.assertEquals("", request.getSubscribe().getSymbols());
  }

  @Test
  public void testBuildDisconnectMessage() {
    Request request = ProtoMessageUtil.buildDisconnectMessage();
    Assert.assertNotNull(request);
    Assert.assertEquals(SocketCommon.Command.DISCONNECT, request.getCommand());
    Assert.assertTrue(request.getId() > 0);
  }

  @Test
  public void testToJson() {
    Request request = ProtoMessageUtil.buildHeartBeatMessage();
    String json = ProtoMessageUtil.toJson(request);
    Assert.assertNotNull(json);
    Assert.assertTrue(json.contains("HEARTBEAT"));
  }

  @Test
  public void testToJsonNull() {
    Assert.assertNull(ProtoMessageUtil.toJson(null));
  }
}
