package com.tigerbrokers.stock.openapi.client.websocket;

import com.tigerbrokers.stock.openapi.client.socket.ApiAuthentication;
import com.tigerbrokers.stock.openapi.client.socket.ApiCallbackDecoder;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.OrderIdPassport;
import com.tigerbrokers.stock.openapi.client.util.ApiCallbackDecoderUtils;
import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.util.CharsetUtil;
import java.util.concurrent.CyclicBarrier;
import lombok.extern.slf4j.Slf4j;

/**
 * @创建人 zhaolei
 * @创建时间 2018/12/20
 * 描述
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketHandshakerHandler extends SimpleChannelInboundHandler<Object> {

  private ApiAuthentication authentication;
  private ApiCallbackDecoder decoder;

  private WebSocketClientHandshaker handshaker;
  private ChannelPromise handshakeFuture;

  public WebSocketHandshakerHandler(ApiAuthentication authentication, ApiComposeCallback callback, boolean async,
      CyclicBarrier cyclicBarrier,
      OrderIdPassport orderIdPassport) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder(callback, async, cyclicBarrier, orderIdPassport);
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) {
    handshakeFuture = ctx.newPromise();
  }

  public void setHandshaker(WebSocketClientHandshaker handshaker){
    this.handshaker = handshaker;
  }

  public WebSocketClientHandshaker getHandshaker(){
    return this.handshaker;
  }

  public ChannelPromise getHandshakeFuture(){
    return this.handshakeFuture;
  }

  public void setHandshakeFuture(ChannelPromise handshakeFuture){
    this.handshakeFuture = handshakeFuture;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    log.info("netty channel inactive!，channel:{}", ctx.channel());
    super.channelInactive(ctx);
    ctx.close();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    log.error("handler exception caught:", cause);
    ctx.close();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    log.info("WebSocketHandshakerHandler channelRead0  {}", this.handshaker.isHandshakeComplete());

    Channel ch = ctx.channel();
    FullHttpResponse response;
    if(!this.handshaker.isHandshakeComplete()){
      response = (FullHttpResponse)msg;
      try {
        //握手协议返回，设置结束握手
        this.handshaker.finishHandshake(ch, response);
        //设置成功
        this.handshakeFuture.setSuccess();
        log.info("WebSocket Client connected! response headers[sec-websocket-extensions]:{}", response.headers());
        //发送stomp connect请求
        ctx.writeAndFlush(StompMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),authentication.getVersion()));
      } catch (WebSocketHandshakeException var7) {
        String errorMsg = String.format("WebSocket Client failed to connect,status:%s,reason:%s", response.status(), response.content().toString(CharsetUtil.UTF_8));
        this.handshakeFuture.setFailure(new Exception(errorMsg));
      }
    }else if(msg instanceof FullHttpResponse){
      response = (FullHttpResponse)msg;
      throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
    }else{
      StompFrame stompFrame = (StompFrame)msg;
      log.debug("received stop frame from server: {}", stompFrame);
      ApiCallbackDecoderUtils.executor(ctx, stompFrame, decoder);
    }
  }
}
