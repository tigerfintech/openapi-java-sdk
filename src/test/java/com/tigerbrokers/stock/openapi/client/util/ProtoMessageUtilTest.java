package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.socket.data.pb.Request;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class ProtoMessageUtilTest {

  @Test
  public void testToJson_null() {
    Assert.assertNull(ProtoMessageUtil.toJson(null));
  }

  @Test
  public void testToJson_valid() {
    Request req = ProtoMessageUtil.buildHeartBeatMessage();
    String json = ProtoMessageUtil.toJson(req);
    Assert.assertNotNull(json);
    Assert.assertTrue(json.contains("HEARTBEAT"));
  }

  @Test
  public void testBuildConnectMessage() {
    Request req = ProtoMessageUtil.buildConnectMessage("tiger123", "sign456", "1.0", 30, 60, true);
    Assert.assertEquals(SocketCommon.Command.CONNECT, req.getCommand());
    Assert.assertTrue(req.getId() > 0);
    Assert.assertEquals("tiger123", req.getConnect().getTigerId());
    Assert.assertEquals("sign456", req.getConnect().getSign());
    Assert.assertEquals("1.0", req.getConnect().getAcceptVersion());
    Assert.assertEquals(30, req.getConnect().getSendInterval());
    Assert.assertEquals(60, req.getConnect().getReceiveInterval());
    Assert.assertTrue(req.getConnect().getUseFullTick());
  }

  @Test(expected = RuntimeException.class)
  public void testBuildConnectMessage_negativeInterval() {
    ProtoMessageUtil.buildConnectMessage("tiger", "sign", "1.0", -1, 60, true);
  }

  @Test(expected = RuntimeException.class)
  public void testBuildConnectMessage_negativeReceiveInterval() {
    ProtoMessageUtil.buildConnectMessage("tiger", "sign", "1.0", 30, -1, true);
  }

  @Test
  public void testBuildSendMessage() {
    Request req = ProtoMessageUtil.buildSendMessage();
    Assert.assertEquals(SocketCommon.Command.SEND, req.getCommand());
    Assert.assertTrue(req.getId() > 0);
  }

  @Test
  public void testBuildHeartBeatMessage() {
    Request req = ProtoMessageUtil.buildHeartBeatMessage();
    Assert.assertEquals(SocketCommon.Command.HEARTBEAT, req.getCommand());
    Assert.assertTrue(req.getId() > 0);
  }

  @Test
  public void testBuildSubscribeMessage_subject() {
    Request req = ProtoMessageUtil.buildSubscribeMessage(Subject.Asset);
    Assert.assertEquals(SocketCommon.Command.SUBSCRIBE, req.getCommand());
    Assert.assertTrue(req.getId() > 0);
  }

  @Test
  public void testBuildSubscribeMessage_accountSubject() {
    Request req = ProtoMessageUtil.buildSubscribeMessage("acct1", Subject.Asset);
    Assert.assertEquals(SocketCommon.Command.SUBSCRIBE, req.getCommand());
    Assert.assertEquals("acct1", req.getSubscribe().getAccount());
  }

  @Test
  public void testBuildSubscribeMessage_nullAccount() {
    Request req = ProtoMessageUtil.buildSubscribeMessage(null, Subject.Asset);
    Assert.assertEquals(SocketCommon.Command.SUBSCRIBE, req.getCommand());
  }

  @Test
  public void testBuildSubscribeMessage_symbols() {
    Set<String> symbols = new HashSet<>();
    symbols.add("AAPL");
    symbols.add("GOOG");
    Request req = ProtoMessageUtil.buildSubscribeMessage(symbols, QuoteSubject.TradeTick);
    Assert.assertEquals(SocketCommon.Command.SUBSCRIBE, req.getCommand());
  }

  @Test
  public void testBuildSubscribeMessage_market() {
    Set<String> indicators = new HashSet<>();
    indicators.add("indicator1");
    Request req = ProtoMessageUtil.buildSubscribeMessage(Market.US, QuoteSubject.Quote, indicators);
    Assert.assertEquals(SocketCommon.Command.SUBSCRIBE, req.getCommand());
  }

  @Test
  public void testBuildSubscribeMessage_marketNullIndicators() {
    Request req = ProtoMessageUtil.buildSubscribeMessage(Market.US, QuoteSubject.Quote, null);
    Assert.assertEquals(SocketCommon.Command.SUBSCRIBE, req.getCommand());
  }

  @Test
  public void testBuildUnSubscribeMessage_subject() {
    Request req = ProtoMessageUtil.buildUnSubscribeMessage(Subject.Asset);
    Assert.assertEquals(SocketCommon.Command.UNSUBSCRIBE, req.getCommand());
  }

  @Test
  public void testBuildUnSubscribeMessage_symbols() {
    Set<String> symbols = new HashSet<>();
    symbols.add("AAPL");
    Request req = ProtoMessageUtil.buildUnSubscribeMessage(symbols, QuoteSubject.TradeTick);
    Assert.assertEquals(SocketCommon.Command.UNSUBSCRIBE, req.getCommand());
  }

  @Test
  public void testBuildUnSubscribeMessage_market() {
    Set<String> symbols = new HashSet<>();
    symbols.add("AAPL");
    Request req = ProtoMessageUtil.buildUnSubscribeMessage(Market.US, QuoteSubject.TradeTick, symbols);
    Assert.assertEquals(SocketCommon.Command.UNSUBSCRIBE, req.getCommand());
  }

  @Test
  public void testBuildUnSubscribeMessage_marketNullSymbols() {
    Request req = ProtoMessageUtil.buildUnSubscribeMessage(Market.US, QuoteSubject.TradeTick, null);
    Assert.assertEquals(SocketCommon.Command.UNSUBSCRIBE, req.getCommand());
  }

  @Test
  public void testBuildDisconnectMessage() {
    Request req = ProtoMessageUtil.buildDisconnectMessage();
    Assert.assertEquals(SocketCommon.Command.DISCONNECT, req.getCommand());
    Assert.assertTrue(req.getId() > 0);
  }

  @Test
  public void testIdIncrements() {
    int id1 = ProtoMessageUtil.buildHeartBeatMessage().getId();
    int id2 = ProtoMessageUtil.buildHeartBeatMessage().getId();
    Assert.assertTrue(id2 > id1);
  }
}
