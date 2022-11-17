package com.tigerbrokers.stock.openapi.client.websocket;

import com.tigerbrokers.stock.openapi.client.socket.ApiAuthentication;
import com.tigerbrokers.stock.openapi.client.socket.ApiCallbackDecoder4Stomp;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback4Stomp;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketHandler;
import com.tigerbrokers.stock.openapi.client.util.ApiCallbackDecoderUtils;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.util.CharsetUtil;

/**
 * @author zhaolei
 * create at 2018/12/20
 */
@ChannelHandler.Sharable
public class WebSocketHandshakerHandler extends SimpleChannelInboundHandler<Object> {

  private ApiAuthentication authentication;
  private ApiCallbackDecoder4Stomp decoder;

  private WebSocketClientHandshaker handshaker;

  private int clientSendInterval = 0;
  private int clientReceiveInterval = 0;

  public WebSocketHandshakerHandler(ApiAuthentication authentication, ApiComposeCallback4Stomp callback) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder4Stomp(callback);
  }

  public WebSocketHandshakerHandler(ApiAuthentication authentication, ApiComposeCallback4Stomp callback, int sendInterval,
      int receiveInterval) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder4Stomp(callback);
    this.clientSendInterval = sendInterval;
    this.clientReceiveInterval = receiveInterval;
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) {
  }

  public void setHandshaker(WebSocketClientHandshaker handshaker) {
    this.handshaker = handshaker;
  }

  public WebSocketClientHandshaker getHandshaker() {
    return this.handshaker;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    ApiLogger.info("netty channel inactive! channel:{}", ctx.channel());
    super.channelInactive(ctx);
    ctx.close();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    ApiLogger.error("handler exception caught:", cause);
    ctx.close();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    ApiLogger.info("WebSocketHandshakerHandler channelRead0 isHandshakeComplete:{}", this.handshaker.isHandshakeComplete());

    Channel ch = ctx.channel();
    FullHttpResponse response;
    if (!this.handshaker.isHandshakeComplete()) {
      response = (FullHttpResponse) msg;
      //握手协议返回，设置结束握手
      handshaker.finishHandshake(ch, response);
      //发送stomp connect请求
      StompFrame connectFrame;
      if (0 == this.clientReceiveInterval && 0 == this.clientSendInterval) {
        connectFrame = StompMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
            authentication.getVersion());
      } else {
        connectFrame = StompMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
            authentication.getVersion(),
            this.clientSendInterval == 0 ? 0 : this.clientSendInterval + WebSocketHandler.HEART_BEAT_SPAN,
            this.clientReceiveInterval == 0 ? 0 : this.clientReceiveInterval - WebSocketHandler.HEART_BEAT_SPAN);
      }
      ApiLogger.info("WebSocket Client connected. channel:{}, response headers[sec-websocket-extensions]:{},"
          + " then preparing to send connect token frame:{}", ch.id().asShortText(), response.headers(), connectFrame);

      ctx.writeAndFlush(connectFrame).addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
          if (future.isSuccess()) {
            ApiLogger.info("WebSocket Client send connect token frame successfully. channel:{}", ch.id().asShortText());
          } else {
            ApiLogger.error("WebSocket Client failed to send connect token. channel:{}, isDone:{}, cause:{}",
                ch.id().asShortText(), future.isDone(), future.cause().getMessage());
          }
        }
      });
    } else if (msg instanceof FullHttpResponse) {
      response = (FullHttpResponse) msg;
      throw new IllegalStateException(
          "Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content()
              .toString(CharsetUtil.UTF_8) + ')');
    } else {
      StompFrame stompFrame = (StompFrame) msg;
      ApiLogger.debug("received stop frame from server: {}", stompFrame);
      try {
        ApiCallbackDecoderUtils.executor(ctx, stompFrame, decoder);
      } catch (Throwable th) {
        ApiLogger.error("api callback fail. stompFrame:{}", stompFrame, th);
      }
    }
  }
}
