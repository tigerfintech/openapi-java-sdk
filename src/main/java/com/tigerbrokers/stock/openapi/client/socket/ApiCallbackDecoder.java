package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.TradeTickUtil;
import io.netty.handler.codec.stomp.StompFrame;
import java.nio.charset.Charset;

import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.ERROR_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_CANCEL_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_QUOTE_CHANGE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_SUB_SYMBOLS_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_TRADING_TICK_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.ID_HEADER;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.RET_HEADER;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIBE_ASSET;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIBE_ORDER_STATUS;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIBE_POSITION;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIPTION_HEADER;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class ApiCallbackDecoder {

  private ApiComposeCallback callback;
  private StompFrame stompFrame;
  private int retType;
  private String id;
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
      case GET_TRADING_TICK_END:
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
    this.id = stompFrame.headers().getAsString(ID_HEADER);
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
    QuoteSubject subject = QuoteSubject.valueOf(type);
    if (type == null) {
      return;
    }
    switch (subject) {
      case Quote:
        callback.quoteChange(jsonObject);
        break;
      case Option:
        callback.optionChange(jsonObject);
        break;
      case TradeTick:
        callback.tradeTickChange(TradeTickUtil.decodeData(jsonObject));
        break;
      case Future:
        callback.futureChange(jsonObject);
        break;
      case QuoteDepth:
        callback.depthQuoteChange(jsonObject);
        break;
      default:
        callback.quoteChange(jsonObject);
    }
  }

  private void processGetSubscribedSymbols() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getSubscribedSymbolEnd(JSONObject.parseObject(content, SubscribedSymbol.class));
  }

  private void processSubscribeEnd() {
    String subject = stompFrame.headers().getAsString(SUBSCRIPTION_HEADER);
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.subscribeEnd(id, subject, JSONObject.parseObject(content));
  }

  private void processCancelSubscribeEnd() {
    String subject = stompFrame.headers().getAsString(SUBSCRIPTION_HEADER);
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.cancelSubscribeEnd(id, subject, JSONObject.parseObject(content));
  }

  private void processErrorEnd() {
    if (stompFrame != null && stompFrame.content() != null) {
      String content = stompFrame.content().toString(DEFAULT_CHARSET);
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
