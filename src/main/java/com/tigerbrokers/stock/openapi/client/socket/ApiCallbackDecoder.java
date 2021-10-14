package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import io.netty.handler.codec.stomp.StompFrame;
import java.nio.charset.Charset;

import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.ERROR_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_CANCEL_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_QUOTE_CHANGE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_SUB_SYMBOLS_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.RET_HEADER;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIBE_ASSET;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIBE_ORDER_STATUS;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIBE_POSITION;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class ApiCallbackDecoder {

  private ApiComposeCallback callback;
  private StompFrame stompFrame;
  private int retType;
  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
  private static final String HEART_BEAT = "heart-beat";

  public ApiCallbackDecoder(ApiComposeCallback callback) {
    this.callback = callback;
  }

  public synchronized void handle(StompFrame stompFrame) {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    if (!StringUtils.isEmpty(content) && HEART_BEAT.equals(content)) {
      processHeartBeat(content);
      return;
    }
    init(stompFrame);

    switch (retType) {
      case SUBSCRIBE_POSITION:
        processPosition();
        break;
      case SUBSCRIBE_ASSET:
        processAsset();
        break;
      case SUBSCRIBE_ORDER_STATUS:
        processOrderStatus();
        break;
      case GET_QUOTE_CHANGE_END:
        processSubscribeQuoteChange();
        break;
      case GET_SUB_SYMBOLS_END:
        processGetSubscribedSymbols();
        break;
      case GET_SUBSCRIBE_END:
        processSubscribeEnd();
        break;
      case GET_CANCEL_SUBSCRIBE_END:
        processCancelSubscribeEnd();
        break;
      case ERROR_END:
        processErrorEnd();
        break;
      default:
        processDefault();
        break;
    }
  }

  private void init(StompFrame stompFrame) {
    this.stompFrame = stompFrame;
    this.retType = stompFrame.headers().getInt(RET_HEADER);
  }

  public ApiComposeCallback getCallback() {
    return callback;
  }

  private void processPosition() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.positionChange(JSON.parseObject(content));
  }

  private void processAsset() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.assetChange(JSONObject.parseObject(content));
  }

  private void processOrderStatus() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.orderStatusChange(JSONObject.parseObject(content));
  }

  private void processSubscribeQuoteChange() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    if (content == null) {
      return;
    }
    JSONObject jsonObject = JSONObject.parseObject(content);
    if (jsonObject == null) {
      return;
    }
    String type = jsonObject.getString("type");
    if (type == null) {
      return;
    }
    if (type.equals(QuoteSubject.Quote.name())) {
      callback.quoteChange(jsonObject);
    } else if (type.equals(QuoteSubject.Option.name())) {
      callback.optionChange(jsonObject);
    } else if (type.equals(QuoteSubject.Future.name())) {
      callback.futureChange(jsonObject);
    } else if (type.equals(QuoteSubject.QuoteDepth.name())) {
      callback.depthQuoteChange(jsonObject);
    } else {
      callback.quoteChange(jsonObject);
    }
  }

  private void processGetSubscribedSymbols() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getSubscribedSymbolEnd(JSONObject.parseObject(content, SubscribedSymbol.class));
  }

  private void processSubscribeEnd() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.subscribeEnd(JSONObject.parseObject(content));
  }

  private void processCancelSubscribeEnd() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.cancelSubscribeEnd(JSONObject.parseObject(content));
  }

  private void processErrorEnd() {
    if (stompFrame != null && stompFrame.content() != null) {
      String content = stompFrame.content().toString(DEFAULT_CHARSET);
      try {
        JSONObject jsonObject = JSONObject.parseObject(content);
        if (jsonObject.getIntValue("code") == TigerApiCode.CONNECTION_KICK_OFF_ERROR.getCode()) {
          String errMessage = jsonObject.getString("message");
          // callback. close the connection and stop reconnect
          callback.connectionKickoff(TigerApiCode.CONNECTION_KICK_OFF_ERROR.getCode(),
              errMessage == null ? TigerApiCode.CONNECTION_KICK_OFF_ERROR.getMessage() : errMessage);
          return;
        }
      } catch (Exception e) {
        // ignore...
      }
      callback.error(content);
    } else if (stompFrame != null) {
      callback.error(JSONObject.toJSONString(stompFrame));
    } else {
      callback.error("unknown error");
    }
  }

  private void processDefault() {
    ApiLogger.info("ret-type:{} cannot be processed.", retType);
  }

  private void processHeartBeat(final String content) {
    callback.hearBeat(content);
  }

  public void serverHeartBeatTimeOut(String channelId) {
    callback.serverHeartBeatTimeOut(channelId);
  }
}
