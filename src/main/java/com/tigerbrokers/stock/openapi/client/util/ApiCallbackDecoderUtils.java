package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.socket.ApiCallbackDecoder;
import com.tigerbrokers.stock.openapi.client.socket.ApiCallbackDecoder4Stomp;
import com.tigerbrokers.stock.openapi.client.socket.IdleTriggerHandler;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketClient;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketHandler;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Response;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.builder.HeaderBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.handler.timeout.IdleStateHandler;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.VERSION;
import static io.netty.handler.codec.stomp.StompHeaders.HEART_BEAT;

/**
 * @author  zhaolei
 * @since  2018/12/20
 * 描述
 */
public class ApiCallbackDecoderUtils {

  public final static String IDLE_STATE_HANDLER = "idleStateHandler";
  public final static String IDLE_TRIGGER_HANDLER = "idleTriggerHandler";

  public static void executor(ChannelHandlerContext ctx, StompFrame frame, ApiCallbackDecoder4Stomp decoder)
      throws Exception {
    if (null == decoder || null == ctx) {
      return;
    }

    switch (frame.command()) {
      case CONNECTED:
        ApiLogger.info("connect token validation success:{}", frame);
        WebSocketClient.getInstance().connectCountDown();
        if (decoder.getCallback() != null) {
          // set stomp version
          HeaderBuilder.setUseVersion(frame.headers().getAsString(VERSION));
          String value = frame.headers().getAsString(HEART_BEAT);
          // set hearbeat time
          if (!StringUtils.isEmpty(value)) {
            String[] arrayValue = value.split(",");
            if (null != arrayValue && arrayValue.length >= 2) {
              int serverSendInterval = StringUtils.isEmpty(arrayValue[0]) ? 0 : Integer.valueOf(arrayValue[0]);
              int serverReceiveInterval = StringUtils.isEmpty(arrayValue[1]) ? 0 : Integer.valueOf(arrayValue[1]);
              if (serverSendInterval > 0 || serverReceiveInterval > 0) {
                if (null == ctx.channel().pipeline().get(IDLE_STATE_HANDLER)) {
                  serverSendInterval =
                      serverSendInterval == 0 ? 0 : serverSendInterval + WebSocketHandler.HEART_BEAT_SPAN;
                  serverReceiveInterval =
                      serverReceiveInterval == 0 ? 0 : serverReceiveInterval - WebSocketHandler.HEART_BEAT_SPAN;

                  ctx.channel().pipeline().addBefore(WebSocketClient.SOCKET_ENCODER, IDLE_STATE_HANDLER,
                      new IdleStateHandler(serverSendInterval, serverReceiveInterval, 0, TimeUnit.MILLISECONDS));

                  ctx.channel()
                      .pipeline()
                      .addAfter(IDLE_STATE_HANDLER, IDLE_TRIGGER_HANDLER, new IdleTriggerHandler(decoder));
                }
              }
              decoder.getCallback().connectionAck(serverSendInterval, serverReceiveInterval);
            } else {
              decoder.getCallback().connectionAck();
            }
          } else {
            decoder.getCallback().connectionAck();
          }
        }
        break;
      case MESSAGE:
        decoder.handle(frame);
        break;
      case RECEIPT:
        break;
      case ERROR:
        if (decoder.getCallback() != null) {
          if (frame != null && frame.content() != null) {
            String content = frame.content().toString(Charset.defaultCharset());
            try {
              JSONObject jsonObject = JSONObject.parseObject(content);
              if (jsonObject.getIntValue("code") == TigerApiCode.CONNECTION_KICK_OFF_ERROR.getCode()) {
                ApiLogger.info(content);
                // close the connection(Do not send disconnect command)
                WebSocketClient.getInstance().closeConnect(false);
                String errMessage = jsonObject.getString("message");
                // callback
                decoder.getCallback().connectionKickoff(TigerApiCode.CONNECTION_KICK_OFF_ERROR.getCode(),
                    errMessage == null ? TigerApiCode.CONNECTION_KICK_OFF_ERROR.getMessage() : errMessage);
                return;
              }
            } catch (Throwable th) {
              // ignore...
            }
            decoder.getCallback().error(content);
          } else if (frame != null) {
            decoder.getCallback().error(JSONObject.toJSONString(frame));
          } else {
            decoder.getCallback().error("unknown error");
          }
        }
        break;
      case DISCONNECT:
        if (decoder.getCallback() != null) {
          decoder.getCallback().connectionClosed();
        }
        ctx.close();
        break;
      case UNKNOWN:
        ctx.close();
        break;
      default:
        break;
    }
  }

  public static void executor(ChannelHandlerContext ctx, Response response, ApiCallbackDecoder decoder) {
    if (null == decoder || null == ctx || null == response || null == response.getCommand()) {
      return;
    }

    switch (response.getCommand()) {
      case CONNECTED:
        ApiLogger.info("connect token validation success:{}", ProtoMessageUtil.toJson(response));
        WebSocketClient.getInstance().connectCountDown();
        if (decoder.getCallback() != null && !StringUtils.isEmpty(response.getMsg())) {
          JSONObject jsonObject = JSONObject.parseObject(response.getMsg());
          // set version
          HeaderBuilder.setUseVersion(jsonObject.getString(VERSION));
          // set hearbeat time
          String value = jsonObject.getString(TigerApiConstants.HEART_BEAT);
          if (!StringUtils.isEmpty(value)) {
            String[] arrayValue = value.split(",");
            if (null != arrayValue && arrayValue.length >= 2) {
              int serverSendInterval = StringUtils.isEmpty(arrayValue[0]) ? 0 : Integer.valueOf(arrayValue[0]);
              int serverReceiveInterval = StringUtils.isEmpty(arrayValue[1]) ? 0 : Integer.valueOf(arrayValue[1]);
              if (serverSendInterval > 0 || serverReceiveInterval > 0) {
                if (null == ctx.channel().pipeline().get(IDLE_STATE_HANDLER)) {
                  serverSendInterval =
                      serverSendInterval == 0 ? 0 : serverSendInterval + WebSocketHandler.HEART_BEAT_SPAN;
                  serverReceiveInterval =
                      serverReceiveInterval == 0 ? 0 : serverReceiveInterval - WebSocketHandler.HEART_BEAT_SPAN;

                  ctx.channel().pipeline().addBefore(WebSocketClient.SOCKET_ENCODER, IDLE_STATE_HANDLER,
                      new IdleStateHandler(serverSendInterval, serverReceiveInterval, 0, TimeUnit.MILLISECONDS));

                  ctx.channel()
                      .pipeline()
                      .addAfter(IDLE_STATE_HANDLER, IDLE_TRIGGER_HANDLER, new IdleTriggerHandler(decoder));
                }
              }
              decoder.getCallback().connectionAck(serverSendInterval, serverReceiveInterval);
            } else {
              decoder.getCallback().connectionAck();
            }
          } else {
            decoder.getCallback().connectionAck();
          }
        }
        break;
      case MESSAGE:
        decoder.handle(response);
        break;
      case ERROR:
        if (decoder.getCallback() != null) {
          if (response.getCode() > 0) {
            try {
              if (response.getCode() == TigerApiCode.CONNECTION_KICK_OFF_ERROR.getCode()) {
                String errMessage = response.getMsg() == null
                    ? TigerApiCode.CONNECTION_KICK_OFF_ERROR.getMessage() : response.getMsg();
                ApiLogger.info(errMessage);
                // close the connection(Do not send disconnect command)
                WebSocketClient.getInstance().closeConnect(false);
                // callback
                decoder.getCallback().connectionKickoff(response.getCode(), errMessage);
                return;
              }
            } catch (Throwable th) {
              // ignore...
            }
            decoder.getCallback().error(response.getId(), response.getCode(), response.getMsg());
          } else if (response.getMsg() != null) {
            decoder.getCallback().error(response.getMsg());
          } else {
            decoder.getCallback().error("unknown error");
          }
        }
        break;
      case DISCONNECT:
        if (decoder.getCallback() != null) {
          decoder.getCallback().connectionClosed();
        }
        ctx.close();
        break;
      case UNKNOWN:
        ctx.close();
        break;
      default:
        break;
    }
  }
}
