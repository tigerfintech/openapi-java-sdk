package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.socket.data.pb.ApiMsg;
import com.tigerbrokers.stock.openapi.client.util.ApiCallbackDecoderUtils;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ProtoSocketHandler extends SimpleChannelInboundHandler<ApiMsg> {

  private ApiAuthentication authentication;
  private ApiCallbackDecoder decoder;
  private int clientSendInterval = 0;
  private int clientReceiveInterval = 0;
  public final static int HEART_BEAT_SPAN = 1000;

  public ProtoSocketHandler(ApiAuthentication authentication, ApiComposeCallback callback) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder(callback);
  }

  public ProtoSocketHandler(ApiAuthentication authentication, ApiComposeCallback callback, int sendInterval,
      int receiveInterval) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder(callback);
    this.clientSendInterval = sendInterval;
    this.clientReceiveInterval = receiveInterval;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ApiMsg connectMsg;
    if (0 == this.clientSendInterval && 0 == this.clientReceiveInterval) {
      connectMsg = ProtoMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
          authentication.getVersion());
    } else {
      connectMsg = ProtoMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
          authentication.getVersion(), this.clientSendInterval == 0 ? 0 : this.clientSendInterval + HEART_BEAT_SPAN,
          this.clientReceiveInterval == 0 ? 0 : this.clientReceiveInterval - HEART_BEAT_SPAN);
    }
    ApiLogger.info("netty channel active. channel:{}, preparing to send connect token:{}",
        ctx.channel().id().asShortText(), ProtoMessageUtil.toJson(connectMsg.getRequest()));
    ctx.writeAndFlush(connectMsg).addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) {
        if (future.isSuccess()) {
          ApiLogger.info("send connect token successfully. channel:{}", ctx.channel().id().asShortText());
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
  public void channelRead0(ChannelHandlerContext ctx, ApiMsg msg) throws Exception {
    ApiLogger.debug("received msg from server: {}", ProtoMessageUtil.toJson(msg));

    try {
      ApiCallbackDecoderUtils.executor(ctx, msg, decoder);
    } catch (Throwable th) {
      ApiLogger.error("api callback fail. frame:{}", msg, th);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    ApiLogger.error("handler exception caught, channel:{}", ctx.channel().id(), cause);
    ctx.close();
  }
}
