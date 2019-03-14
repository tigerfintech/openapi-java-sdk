package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.util.ApiCallbackDecoderUtils;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.stomp.StompFrame;

@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<StompFrame> {

  private ApiAuthentication authentication;
  private ApiCallbackDecoder decoder;
  private int clientSendInterval = 0;
  private int clientReceiveInterval = 0;
  public final static int HEART_BEAT_SPAN = 1000;

  public WebSocketHandler(ApiAuthentication authentication, ApiComposeCallback callback) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder(callback);
  }

  public WebSocketHandler(ApiAuthentication authentication, ApiComposeCallback callback, int sendInterval,
      int receiveInterval) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder(callback);
    this.clientSendInterval = sendInterval;
    this.clientReceiveInterval = receiveInterval;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    if (0 == this.clientSendInterval && 0 == this.clientReceiveInterval) {
      ctx.writeAndFlush(StompMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
          authentication.getVersion()));
    } else {
      ctx.writeAndFlush(StompMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
          authentication.getVersion(), this.clientSendInterval == 0 ? 0 : this.clientSendInterval + HEART_BEAT_SPAN,
          this.clientReceiveInterval == 0 ? 0 : this.clientReceiveInterval - HEART_BEAT_SPAN));
    }
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    ApiLogger.info("netty channel inactive!");
    super.channelInactive(ctx);
    ctx.close();
  }

  @Override
  public void channelRead0(ChannelHandlerContext ctx, StompFrame frame) throws Exception {
    ApiLogger.debug("received frame from server: {}", frame);

    ApiCallbackDecoderUtils.executor(ctx, frame, decoder);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    ApiLogger.error("handler exception caught:", cause);
    ctx.close();
  }
}
