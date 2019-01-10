package com.tigerbrokers.stock.openapi.client.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.AsciiHeadersEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.stomp.DefaultStompFrame;
import io.netty.handler.codec.stomp.LastStompContentSubframe;
import io.netty.handler.codec.stomp.StompContentSubframe;
import io.netty.handler.codec.stomp.StompHeadersSubframe;
import io.netty.handler.codec.stomp.StompSubframe;
import io.netty.util.CharsetUtil;
import java.util.List;
import java.util.Map;

/**
 * @author  zhaolei
 * create at 2018/12/20
 */
public class WebSocketStompFrameEncoder extends MessageToMessageEncoder<StompSubframe> {

  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, StompSubframe stompSubframe, List<Object> list)
      throws Exception {
    if (stompSubframe instanceof DefaultStompFrame) {
      DefaultStompFrame frame = (DefaultStompFrame) stompSubframe;
      ByteBuf frameBuf = encodeFrameAndContent(frame, channelHandlerContext);
      list.add(new BinaryWebSocketFrame(true, 0, frameBuf));
    }
  }

  private static ByteBuf encodeFrameAndContent(DefaultStompFrame frame, ChannelHandlerContext ctx) {
    ByteBuf buf = ctx.alloc().buffer();//TODO 可优化
    //head
    buf.writeCharSequence(frame.command().toString(), CharsetUtil.US_ASCII);
    buf.writeByte(StompConstants.LF);
    AsciiHeadersEncoder
        headersEncoder =
        new AsciiHeadersEncoder(buf, AsciiHeadersEncoder.SeparatorType.COLON, AsciiHeadersEncoder.NewlineType.LF);
    for (Map.Entry<CharSequence, CharSequence> entry : frame.headers()) {
      headersEncoder.encode(entry);
    }
    buf.writeByte(StompConstants.LF);
    //content
    if (frame.content() != null) {
      buf.writeBytes(frame.content());
    }
    buf.writeByte(StompConstants.NUL);
    return buf;
  }

  private static ByteBuf encodeContent(StompContentSubframe content, ChannelHandlerContext ctx) {
    if (content instanceof LastStompContentSubframe) {
      ByteBuf buf = ctx.alloc().buffer(content.content().readableBytes() + 1);
      buf.writeBytes(content.content());
      buf.writeByte(StompConstants.NUL);
      return buf;
    } else {
      return content.content().retain();
    }
  }

  private static ByteBuf encodeFrame(StompHeadersSubframe frame, ChannelHandlerContext ctx) {
    ByteBuf buf = ctx.alloc().buffer();

    buf.writeCharSequence(frame.command().toString(), CharsetUtil.US_ASCII);
    buf.writeByte(StompConstants.LF);
    AsciiHeadersEncoder
        headersEncoder =
        new AsciiHeadersEncoder(buf, AsciiHeadersEncoder.SeparatorType.COLON, AsciiHeadersEncoder.NewlineType.LF);
    for (Map.Entry<CharSequence, CharSequence> entry : frame.headers()) {
      headersEncoder.encode(entry);
    }
    buf.writeByte(StompConstants.LF);
    return buf;
  }
}
