package com.tigerbrokers.stock.openapi.client.websocket;

import com.tigerbrokers.stock.openapi.client.socket.ApiAuthentication;
import com.tigerbrokers.stock.openapi.client.socket.ApiCallbackDecoder;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketHandler;
import com.tigerbrokers.stock.openapi.client.util.ApiCallbackDecoderUtils;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import io.netty.channel.Channel;
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
  private ApiCallbackDecoder decoder;

  private WebSocketClientHandshaker handshaker;

  private int clientSendInterval = 0;
  private int clientReceiveInterval = 0;

  public WebSocketHandshakerHandler(ApiAuthentication authentication, ApiComposeCallback callback) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder(callback);
  }

  public WebSocketHandshakerHandler(ApiAuthentication authentication, ApiComposeCallback callback, int sendInterval,
      int receiveInterval) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder(callback);
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
    ApiLogger.info("netty channel inactive!，channel:{}", ctx.channel());
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
    ApiLogger.info("WebSocketHandshakerHandler channelRead0  {}", this.handshaker.isHandshakeComplete());

    Channel ch = ctx.channel();
    FullHttpResponse response;
    if (!this.handshaker.isHandshakeComplete()) {
      response = (FullHttpResponse) msg;
      //握手协议返回，设置结束握手
      handshaker.finishHandshake(ch, response);
      //发送stomp connect请求
      if (0 == this.clientReceiveInterval && 0 == this.clientSendInterval) {
        ctx.writeAndFlush(StompMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
            authentication.getVersion()));
      } else {
        ctx.writeAndFlush(StompMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
            authentication.getVersion(),
            this.clientSendInterval == 0 ? 0 : this.clientSendInterval + WebSocketHandler.HEART_BEAT_SPAN,
            this.clientReceiveInterval == 0 ? 0 : this.clientReceiveInterval - WebSocketHandler.HEART_BEAT_SPAN));
      }

      ApiLogger.info("WebSocket Client connected! response headers[sec-websocket-extensions]:{}", response.headers());
    } else if (msg instanceof FullHttpResponse) {
      response = (FullHttpResponse) msg;
      throw new IllegalStateException(
          "Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content()
              .toString(CharsetUtil.UTF_8) + ')');
    } else {
      StompFrame stompFrame = (StompFrame) msg;
      ApiLogger.debug("received stop frame from server: {}", stompFrame);
      ApiCallbackDecoderUtils.executor(ctx, stompFrame, decoder);
    }
  }
}
