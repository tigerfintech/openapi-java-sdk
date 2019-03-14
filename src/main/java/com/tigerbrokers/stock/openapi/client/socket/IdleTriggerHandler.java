package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @创建人 zhaolei
 * @创建时间 2019/3/13
 * 描述
 */
@Slf4j
public class IdleTriggerHandler extends ChannelInboundHandlerAdapter {

  private ApiCallbackDecoder apiCallbackDecoder = null;

  public IdleTriggerHandler(ApiCallbackDecoder decoder){
    this.apiCallbackDecoder = decoder;
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleState state = ((IdleStateEvent) evt).state();
      if (IdleState.WRITER_IDLE == state) {
        ctx.channel().writeAndFlush(StompMessageUtil.buildCommonSendMessage("Heart_Beat"));
      } else if (IdleState.READER_IDLE == state) {
        log.warn("server time out:{}", ctx.channel().id().asLongText());
        if (this.apiCallbackDecoder != null){
          this.apiCallbackDecoder.serverHeartBeatTimeOut(ctx.channel().id().asLongText());
        }
      }
    } else {
      ctx.fireUserEventTriggered(evt);
    }
  }
}
