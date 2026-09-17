package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.HEART_BEAT;

/**
 * @author  zhaolei
 * @since  2019/3/13
 */
public class IdleTriggerHandler extends ChannelInboundHandlerAdapter {
  private WebSocketClient wsClient;
  private ApiCallbackDecoder apiCallbackDecoder = null;

  public IdleTriggerHandler(WebSocketClient wsClient, ApiCallbackDecoder decoder) {
    this.wsClient = wsClient;
    this.apiCallbackDecoder = decoder;
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleState state = ((IdleStateEvent) evt).state();
      if (IdleState.WRITER_IDLE == state) {
        if (this.wsClient.isUseProtobuf()) {
          ctx.channel().writeAndFlush(ProtoMessageUtil.buildHeartBeatMessage());
          // Piggybacks on the heartbeat: it is the one thing already known to run at a
          // fixed interval on a live connection. No-op unless the connection is OAuth2 and
          // the token is close to expiry.
          refreshTokenIfNeeded(ctx);
        } else {
          ctx.channel().writeAndFlush(StompMessageUtil.buildCommonSendMessage(HEART_BEAT));
        }
      } else if (IdleState.READER_IDLE == state) {
        ApiLogger.warn("server time out:{}", ctx.channel().id().asShortText());
        if (this.apiCallbackDecoder != null) {
          this.apiCallbackDecoder.serverHeartBeatTimeOut(ctx.channel().id().asShortText());
        }
      }
    } else {
      ctx.fireUserEventTriggered(evt);
    }
  }

  /**
   * Asks the protobuf handler to rotate the access token if it is close to expiry.
   *
   * <p>Delegated rather than done here because the pending-rotation state lives on the
   * channel next to the handler that sent the connect message. Any failure is swallowed: a
   * rotation that does not happen must not take down the heartbeat, which is what keeps the
   * connection alive.
   */
  private void refreshTokenIfNeeded(ChannelHandlerContext ctx) {
    try {
      ChannelHandler handler = ctx.channel().pipeline().get("webSocketHandler");
      if (handler instanceof ProtoSocketHandler) {
        ((ProtoSocketHandler) handler).refreshTokenIfNeeded(ctx);
      }
    } catch (Throwable t) {
      ApiLogger.error("refresh token check fail. channel:{}", ctx.channel().id().asShortText(), t);
    }
  }
}
