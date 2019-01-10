package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.socket.ApiCallbackDecoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.stomp.StompFrame;
import java.nio.charset.Charset;

import static com.tigerbrokers.stock.openapi.client.socket.WebSocketClient.connectCountDown;

/**
 * 创建人 zhaolei
 * 创建时间 2018/12/20
 * 描述
 */
public class ApiCallbackDecoderUtils {

  public static void executor(ChannelHandlerContext ctx, StompFrame frame, ApiCallbackDecoder decoder)
      throws Exception {
    if (null == decoder || null == ctx) {
      return;
    }

    switch (frame.command()) {
      case CONNECTED:
        if (decoder.getCallback() != null) {
          decoder.getCallback().connectAck();
        }
        connectCountDown.countDown();
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
}
