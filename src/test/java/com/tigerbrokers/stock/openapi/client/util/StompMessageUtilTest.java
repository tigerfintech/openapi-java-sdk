package com.tigerbrokers.stock.openapi.client.util;

import com.google.common.collect.Sets;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import io.netty.handler.codec.stomp.StompCommand;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.handler.codec.stomp.StompHeaders;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for {@link StompMessageUtil}.
 */
@RunWith(MockitoJUnitRunner.class)
public class StompMessageUtilTest {

  @Test
  public void testBuildConnectMessageBasic() {
    StompFrame frame = StompMessageUtil.buildConnectMessage("login001", "passcode001", "1.2");
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.CONNECT, frame.command());
    Assert.assertEquals("1.2", frame.headers().get(StompHeaders.ACCEPT_VERSION));
    Assert.assertEquals("login001", frame.headers().get(StompHeaders.LOGIN));
    Assert.assertEquals("passcode001", frame.headers().get(StompHeaders.PASSCODE));
    Assert.assertEquals("localhost", frame.headers().get(StompHeaders.HOST));
    Assert.assertNotNull(frame.headers().get(StompHeaders.ID));
  }

  @Test
  public void testBuildConnectMessageWithHeartBeat() {
    StompFrame frame = StompMessageUtil.buildConnectMessage("login001", "passcode001", "1.2", 5000, 5000);
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.CONNECT, frame.command());
    Assert.assertEquals("1.2", frame.headers().get(StompHeaders.ACCEPT_VERSION));
    Assert.assertEquals("login001", frame.headers().get(StompHeaders.LOGIN));
    Assert.assertEquals("passcode001", frame.headers().get(StompHeaders.PASSCODE));
    Assert.assertEquals("5000,5000", frame.headers().get(StompHeaders.HEART_BEAT));
  }

  @Test(expected = RuntimeException.class)
  public void testBuildConnectMessageZeroHeartBeat() {
    // Zero is valid (means no heartbeat), only negative throws
    // Actually zero is accepted, let's test negative instead
    StompMessageUtil.buildConnectMessage("login001", "passcode001", "1.2", -1, 5000);
  }

  @Test(expected = RuntimeException.class)
  public void testBuildConnectMessageNegativeHeartBeat() {
    StompMessageUtil.buildConnectMessage("login001", "passcode001", "1.2", 5000, -1);
  }

  @Test
  public void testBuildConnectMessageZeroIsOk() {
    // Zero interval means no heartbeat, should not throw
    StompFrame frame = StompMessageUtil.buildConnectMessage("login001", "passcode001", "1.2", 0, 0);
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.CONNECT, frame.command());
    Assert.assertEquals("0,0", frame.headers().get(StompHeaders.HEART_BEAT));
  }

  @Test
  public void testBuildSendMessageWithMessage() {
    StompFrame frame = StompMessageUtil.buildSendMessage(1, "hello");
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.SEND, frame.command());
    Assert.assertEquals("1", frame.headers().get("req-type"));
    Assert.assertNotNull(frame.headers().get(StompHeaders.ID));
    Assert.assertEquals("localhost", frame.headers().get(StompHeaders.HOST));
  }

  @Test
  public void testBuildSendMessageNullMessage() {
    StompFrame frame = StompMessageUtil.buildSendMessage(1, null);
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.SEND, frame.command());
    Assert.assertEquals("1", frame.headers().get("req-type"));
  }

  @Test(expected = RuntimeException.class)
  public void testBuildSendMessageInvalidReqType() {
    StompMessageUtil.buildSendMessage(0, "hello");
  }

  @Test(expected = RuntimeException.class)
  public void testBuildSendMessageNegativeReqType() {
    StompMessageUtil.buildSendMessage(-1, "hello");
  }

  @Test
  public void testBuildCommonSendMessageWithMessage() {
    StompFrame frame = StompMessageUtil.buildCommonSendMessage("common message");
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.SEND, frame.command());
    Assert.assertNotNull(frame.headers().get(StompHeaders.ID));
    Assert.assertEquals("localhost", frame.headers().get(StompHeaders.HOST));
    // Should NOT have req-type header
    Assert.assertNull(frame.headers().get("req-type"));
  }

  @Test
  public void testBuildCommonSendMessageNull() {
    StompFrame frame = StompMessageUtil.buildCommonSendMessage(null);
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.SEND, frame.command());
  }

  @Test
  public void testBuildSubscribeMessageSubject() {
    StompFrame frame = StompMessageUtil.buildSubscribeMessage(Subject.Position);
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.SUBSCRIBE, frame.command());
    Assert.assertEquals("Position", frame.headers().get(StompHeaders.SUBSCRIPTION));
    Assert.assertNotNull(frame.headers().get(StompHeaders.ID));
    Assert.assertEquals("localhost", frame.headers().get(StompHeaders.HOST));
  }

  @Test
  public void testBuildSubscribeMessageWithAccountAndFocusKeys() {
    Set<String> focusKeys = Sets.newHashSet("key1", "key2");
    StompFrame frame = StompMessageUtil.buildSubscribeMessage("13810712", Subject.Position, focusKeys);
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.SUBSCRIBE, frame.command());
    Assert.assertEquals("13810712", frame.headers().get("account"));
    Assert.assertEquals("Position", frame.headers().get(StompHeaders.SUBSCRIPTION));
    Assert.assertEquals("localhost", frame.headers().get(StompHeaders.HOST));
  }

  @Test
  public void testBuildSubscribeMessageWithSymbols() {
    Set<String> symbols = Sets.newHashSet("00700");
    StompFrame frame = StompMessageUtil.buildSubscribeMessage(symbols, QuoteSubject.Quote);
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.SUBSCRIBE, frame.command());
    Assert.assertEquals("Quote", frame.headers().get(StompHeaders.SUBSCRIPTION));
    Assert.assertEquals("00700", frame.headers().get("symbols"));
  }

  @Test
  public void testBuildSubscribeMessageWithSymbolsAndFocusKeys() {
    Set<String> symbols = Sets.newHashSet("00700");
    Set<String> focusKeys = Sets.newHashSet("key1");
    StompFrame frame = StompMessageUtil.buildSubscribeMessage(symbols, QuoteSubject.Quote, focusKeys);
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.SUBSCRIBE, frame.command());
    Assert.assertEquals("Quote", frame.headers().get(StompHeaders.SUBSCRIPTION));
    Assert.assertEquals("00700", frame.headers().get("symbols"));
    Assert.assertEquals("key1", frame.headers().get("keys"));
  }

  @Test
  public void testBuildUnSubscribeMessageSubject() {
    StompFrame frame = StompMessageUtil.buildUnSubscribeMessage(Subject.Position);
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.UNSUBSCRIBE, frame.command());
    Assert.assertEquals("Position", frame.headers().get(StompHeaders.SUBSCRIPTION));
  }

  @Test
  public void testBuildUnSubscribeMessageWithSymbols() {
    Set<String> symbols = Sets.newHashSet("00700");
    StompFrame frame = StompMessageUtil.buildUnSubscribeMessage(symbols, QuoteSubject.Quote);
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.UNSUBSCRIBE, frame.command());
    Assert.assertEquals("Quote", frame.headers().get(StompHeaders.SUBSCRIPTION));
    Assert.assertEquals("00700", frame.headers().get("symbols"));
  }

  @Test
  public void testBuildDisconnectMessage() {
    StompFrame frame = StompMessageUtil.buildDisconnectMessage("login001");
    Assert.assertNotNull(frame);
    Assert.assertEquals(StompCommand.DISCONNECT, frame.command());
    Assert.assertEquals("login001", frame.headers().get(StompHeaders.LOGIN));
    Assert.assertEquals("localhost", frame.headers().get(StompHeaders.HOST));
  }
}
