package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.util.ApiCallbackDecoderUtils;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.stomp.StompFrame;

@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<StompFrame> {

  private ApiAuthentication authentication;
  private ApiCallbackDecoder4Stomp decoder;
  private int clientSendInterval = 0;
  private int clientReceiveInterval = 0;
  public final static int HEART_BEAT_SPAN = 1000;

  public WebSocketHandler(ApiAuthentication authentication, ApiComposeCallback callback) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder4Stomp((ApiComposeCallback4Stomp) callback);
  }

  public WebSocketHandler(ApiAuthentication authentication, ApiComposeCallback callback, int sendInterval,
      int receiveInterval) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder4Stomp((ApiComposeCallback4Stomp) callback);
    this.clientSendInterval = sendInterval;
    this.clientReceiveInterval = receiveInterval;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    StompFrame connectFrame;
    if (0 == this.clientSendInterval && 0 == this.clientReceiveInterval) {
      connectFrame = StompMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
          authentication.getVersion());
    } else {
      connectFrame = StompMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
          authentication.getVersion(), this.clientSendInterval == 0 ? 0 : this.clientSendInterval + HEART_BEAT_SPAN,
          this.clientReceiveInterval == 0 ? 0 : this.clientReceiveInterval - HEART_BEAT_SPAN);
    }
    ApiLogger.info("netty channel active. channel:{}, preparing to send connect token frame:{}",
        ctx.channel().id().asShortText(), connectFrame);
    ctx.writeAndFlush(connectFrame).addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) {
        if (future.isSuccess()) {
          ApiLogger.info("send connect token frame successfully. channel:{}", ctx.channel().id().asShortText());
        } else {
          ApiLogger.error("failed to send connect token. channel:{}, isDone:{}, cause:{}",
              ctx.channel().id().asShortText(), future.isDone(), future.cause() == null ? null : future.cause().getMessage());
        }
      }
    });
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    ApiLogger.info("netty channel inactive! channel:{}", ctx.channel().id().asShortText());
    super.channelInactive(ctx);
    ctx.close();
  }

  @Override
  public void channelRead0(ChannelHandlerContext ctx, StompFrame frame) throws Exception {
    ApiLogger.debug("received frame from server: {}", frame);

    try {
      ApiCallbackDecoderUtils.executor(ctx, frame, decoder);
    } catch (Throwable th) {
      ApiLogger.error("api callback fail. frame:{}", frame, th);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    ApiLogger.error("handler exception caught, channel:{}", ctx.channel().id(), cause);
    ctx.close();
  }
}
