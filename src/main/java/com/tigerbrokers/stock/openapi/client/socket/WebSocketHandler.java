package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.stomp.StompFrame;
import java.nio.charset.Charset;
import java.util.concurrent.CyclicBarrier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<StompFrame> {

  private static Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

  private ApiAuthentication authentication;
  private ApiCallbackDecoder decoder;

  public WebSocketHandler(ApiAuthentication authentication, ApiComposeCallback callback, boolean async,
      CyclicBarrier cyclicBarrier,
      OrderIdPassport orderIdPassport) {
    this.authentication = authentication;
    this.decoder = new ApiCallbackDecoder(callback, async, cyclicBarrier, orderIdPassport);
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) {
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ctx.writeAndFlush(StompMessageUtil.buildConnectMessage(authentication.getTigerId(), authentication.getSign(),
        authentication.getVersion()));
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    logger.info("netty channel inactive!");
    super.channelInactive(ctx);
    ctx.close();
  }

  @Override
  public void channelRead0(ChannelHandlerContext ctx, StompFrame frame) throws Exception {
    logger.debug("received frame from server: {}", frame);

    switch (frame.command()) {
      case CONNECTED:
        if (decoder.getCallback() != null) {
          decoder.getCallback().connectAck();
        }
        break;
      case MESSAGE:
        decoder.handle(frame);
        break;
      case RECEIPT:
        break;
      case ERROR:
        if (decoder.getCallback() != null) {
          decoder.getCallback().error(frame.content().toString(Charset.defaultCharset()));
        }
        break;
      case DISCONNECT:
        if (decoder.getCallback() != null) {
          decoder.getCallback().connectionClosed();
        }
        ctx.close();
        break;
      default:
        break;
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    logger.error("handler exception caught:", cause);
    ctx.close();
  }
}
