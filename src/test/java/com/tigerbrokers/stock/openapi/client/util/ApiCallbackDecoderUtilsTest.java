package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.socket.ApiCallbackDecoder;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Response;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ApiCallbackDecoderUtilsTest {

  private ChannelHandlerContext mockCtx() {
    ChannelHandlerContext ctx = mock(ChannelHandlerContext.class);
    Channel channel = mock(Channel.class);
    ChannelPipeline pipeline = mock(ChannelPipeline.class);
    when(ctx.channel()).thenReturn(channel);
    when(channel.pipeline()).thenReturn(pipeline);
    when(channel.id()).thenReturn(mock(io.netty.channel.ChannelId.class));
    return ctx;
  }

  @Test
  public void testExecutor_nullDecoder() {
    ChannelHandlerContext ctx = mockCtx();
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.DISCONNECT).build();
    ApiCallbackDecoderUtils.executor(ctx, response, null);
    verify(ctx, never()).close();
  }

  @Test
  public void testExecutor_nullCtx() {
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.DISCONNECT).build();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    ApiCallbackDecoderUtils.executor(null, response, decoder);
  }

  @Test
  public void testExecutor_nullResponse() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    ApiCallbackDecoderUtils.executor(ctx, null, decoder);
    verify(ctx, never()).close();
  }

  @Test
  public void testExecutor_disconnect() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    ApiComposeCallback callback = mock(ApiComposeCallback.class);
    when(decoder.getCallback()).thenReturn(callback);
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.DISCONNECT).build();
    ApiCallbackDecoderUtils.executor(ctx, response, decoder);
    verify(callback).connectionClosed();
    verify(ctx).close();
  }

  @Test
  public void testExecutor_disconnect_nullCallback() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    when(decoder.getCallback()).thenReturn(null);
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.DISCONNECT).build();
    ApiCallbackDecoderUtils.executor(ctx, response, decoder);
    verify(ctx).close();
  }

  @Test
  public void testExecutor_unknown() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.UNKNOWN).build();
    ApiCallbackDecoderUtils.executor(ctx, response, decoder);
    verify(ctx).close();
  }

  @Test
  public void testExecutor_heartbeat() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.HEARTBEAT).build();
    ApiCallbackDecoderUtils.executor(ctx, response, decoder);
    verify(decoder).processHeartBeat(anyString());
  }

  @Test
  public void testExecutor_message() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.MESSAGE).setId(1).build();
    ApiCallbackDecoderUtils.executor(ctx, response, decoder);
    verify(decoder).handle(response);
  }

  @Test
  public void testExecutor_error_codeZero() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    ApiComposeCallback callback = mock(ApiComposeCallback.class);
    when(decoder.getCallback()).thenReturn(callback);
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.ERROR).setCode(0).setMsg("error msg").build();
    ApiCallbackDecoderUtils.executor(ctx, response, decoder);
    verify(callback).error(contains("error msg"));
  }

  @Test
  public void testExecutor_error_codeZero_nullMsg() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    ApiComposeCallback callback = mock(ApiComposeCallback.class);
    when(decoder.getCallback()).thenReturn(callback);
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.ERROR).setCode(0).build();
    ApiCallbackDecoderUtils.executor(ctx, response, decoder);
    verify(callback).error(contains("channel"));
  }

  @Test
  public void testExecutor_error_positiveCode() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    ApiComposeCallback callback = mock(ApiComposeCallback.class);
    when(decoder.getCallback()).thenReturn(callback);
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.ERROR).setCode(500).setMsg("server error").setId(1).build();
    ApiCallbackDecoderUtils.executor(ctx, response, decoder);
    verify(callback).error(eq(1), eq(500), contains("server error"));
  }

  @Test
  public void testExecutor_error_kickOutCode() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    ApiComposeCallback callback = mock(ApiComposeCallback.class);
    when(decoder.getCallback()).thenReturn(callback);
    // CONNECTION_KICK_OUT_ERROR code
    int kickOutCode = com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode.CONNECTION_KICK_OUT_ERROR.getCode();
    Response response = Response.newBuilder().setCommand(SocketCommon.Command.ERROR).setCode(kickOutCode).setMsg("kicked out").setId(1).build();
    ApiCallbackDecoderUtils.executor(ctx, response, decoder);
    verify(callback).connectionKickout(eq(kickOutCode), anyString());
  }

  @Test
  public void testReceiveConnected_nullCallback() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    when(decoder.getCallback()).thenReturn(null);
    ApiCallbackDecoderUtils.receiveConnected(ctx, decoder, null);
    // Should not throw
  }

  @Test
  public void testReceiveConnected_emptyMsg() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    ApiComposeCallback callback = mock(ApiComposeCallback.class);
    when(decoder.getCallback()).thenReturn(callback);
    ApiCallbackDecoderUtils.receiveConnected(ctx, decoder, "");
    // Empty msg — should not call connectionAck since msg is empty
    verify(callback, never()).connectionAck();
  }

  @Test

  public void testReceiveConnected_msgWithoutHeartBeat() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    ApiComposeCallback callback = mock(ApiComposeCallback.class);
    when(decoder.getCallback()).thenReturn(callback);
    String msg = "{\"version\":\"1.0\"}";
    ApiCallbackDecoderUtils.receiveConnected(ctx, decoder, msg);
    verify(callback).connectionAck();
  }

  @Test
  public void testReceiveConnected_heartBeatStringValue() {
    ChannelHandlerContext ctx = mockCtx();
    ApiCallbackDecoder decoder = mock(ApiCallbackDecoder.class);
    ApiComposeCallback callback = mock(ApiComposeCallback.class);
    when(decoder.getCallback()).thenReturn(callback);
    String msg = "{\"version\":\"1.0\",\"heart-beat\":\"100\"}";
    ApiCallbackDecoderUtils.receiveConnected(ctx, decoder, msg);
    verify(callback).connectionAck();
  }
}
