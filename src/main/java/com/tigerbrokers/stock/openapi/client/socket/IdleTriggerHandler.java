package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.HEART_BEAT;

/**
 * @author  zhaolei
 * @since  2019/3/13
 * 描述
 */
public class IdleTriggerHandler extends ChannelInboundHandlerAdapter {

  private static final Logger log = LoggerFactory.getLogger(IdleTriggerHandler.class);

  private ApiCallbackDecoder apiCallbackDecoder = null;

  public IdleTriggerHandler(ApiCallbackDecoder decoder) {
    this.apiCallbackDecoder = decoder;
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleState state = ((IdleStateEvent) evt).state();
      if (IdleState.WRITER_IDLE == state) {
        if (WebSocketClient.getInstance().isUseProtobuf()) {
          ctx.channel().writeAndFlush(ProtoMessageUtil.buildCommonSendMessage(HEART_BEAT));
        } else {
          ctx.channel().writeAndFlush(StompMessageUtil.buildCommonSendMessage(HEART_BEAT));
        }
      } else if (IdleState.READER_IDLE == state) {
        log.warn("server time out:{}", ctx.channel().id().asShortText());
        if (this.apiCallbackDecoder != null) {
          this.apiCallbackDecoder.serverHeartBeatTimeOut(ctx.channel().id().asShortText());
        }
      }
    } else {
      ctx.fireUserEventTriggered(evt);
    }
  }
}
