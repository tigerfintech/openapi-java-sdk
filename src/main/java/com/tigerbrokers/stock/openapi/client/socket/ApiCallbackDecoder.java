package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.ApiMsg;
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
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.HEART_BEAT;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class ApiCallbackDecoder {

  private ApiComposeCallback callback;
  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  public ApiCallbackDecoder(ApiComposeCallback callback) {
    this.callback = callback;
  }

  public synchronized void handle(ApiMsg msg) {
    String content = msg.getContent();
    if (!StringUtils.isEmpty(content) && HEART_BEAT.equals(content)) {
      processHeartBeat(content);
      return;
    }
    int retType = msg.getRetType();

    switch (retType) {
      case SUBSCRIBE_POSITION:
        processPosition(msg);
        break;
      case SUBSCRIBE_ASSET:
        processAsset(msg);
        break;
      case SUBSCRIBE_ORDER_STATUS:
        processOrderStatus(msg);
        break;
      case GET_QUOTE_CHANGE_END:
        processSubscribeQuoteChange(msg);
        break;
      case GET_TRADING_TICK_END:
        processSubscribeQuoteChange(msg);
        break;
      case GET_SUB_SYMBOLS_END:
        processGetSubscribedSymbols(msg);
        break;
      case GET_SUBSCRIBE_END:
        processSubscribeEnd(msg);
        break;
      case GET_CANCEL_SUBSCRIBE_END:
        processCancelSubscribeEnd(msg);
        break;
      case ERROR_END:
        processErrorEnd(msg);
        break;
      default:
        processDefault(msg);
        break;
    }
  }

  public ApiComposeCallback getCallback() {
    return callback;
  }

  private void processPosition(ApiMsg msg) {
    //String content = stompFrame.content().toString(DEFAULT_CHARSET);
    //callback.positionChange(JSON.parseObject(content));
  }

  private void processAsset(ApiMsg msg) {
    //String content = stompFrame.content().toString(DEFAULT_CHARSET);
    //callback.assetChange(JSONObject.parseObject(content));
  }

  private void processOrderStatus(ApiMsg msg) {
    //String content = stompFrame.content().toString(DEFAULT_CHARSET);
    //callback.orderStatusChange(JSONObject.parseObject(content));
  }

  private void processSubscribeQuoteChange(ApiMsg msg) {
    ApiMsg.Type dataType = msg.getDataType();
    if (dataType == null) {
      return;
    }
    switch (dataType) {
      case Quote:
        callback.quoteChange(msg.getQuoteData());
        break;
      case Option:
        // callback.optionChange(jsonObject);
        break;
      case TradeTick:
        // callback.tradeTickChange(TradeTickUtil.decodeData(jsonObject));
        break;
      case Future:
        // callback.futureChange(jsonObject);
        break;
      case QuoteDepth:
        callback.depthQuoteChange(msg.getQuoteDepthData());
        break;
      case Asset:
        // callback.assetChange(msg.getQuoteDepthData());
        break;
      case Position:
        // callback.positionChange(msg.getQuoteDepthData());
        break;
      case OrderStatus:
        // callback.orderStatusChange(msg.getQuoteDepthData());
        break;
      default:
        callback.quoteChange(msg.getQuoteData());
    }
  }

  private void processGetSubscribedSymbols(ApiMsg msg) {
    //String content = stompFrame.content().toString(DEFAULT_CHARSET);
    //callback.getSubscribedSymbolEnd(JSONObject.parseObject(content, SubscribedSymbol.class));
  }

  private void processSubscribeEnd(ApiMsg msg) {
    //String subject = stompFrame.headers().getAsString(SUBSCRIPTION_HEADER);
    //String content = stompFrame.content().toString(DEFAULT_CHARSET);
    //callback.subscribeEnd(id, subject, JSONObject.parseObject(content));
  }

  private void processCancelSubscribeEnd(ApiMsg msg) {
    //String subject = stompFrame.headers().getAsString(SUBSCRIPTION_HEADER);
    //String content = stompFrame.content().toString(DEFAULT_CHARSET);
    //callback.cancelSubscribeEnd(id, subject, JSONObject.parseObject(content));
  }

  private void processErrorEnd(ApiMsg msg) {
    //if (stompFrame != null && stompFrame.content() != null) {
    //  String content = stompFrame.content().toString(DEFAULT_CHARSET);
    //  callback.error(content);
    //} else if (stompFrame != null) {
    //  callback.error(JSONObject.toJSONString(stompFrame));
    //} else {
    //  callback.error("unknown error");
    //}
  }

  private void processDefault(ApiMsg msg) {
    ApiLogger.info("ret-type:{} cannot be processed.", msg.getRetType());
  }

  private void processHeartBeat(final String content) {
    callback.hearBeat(content);
  }

  public void serverHeartBeatTimeOut(String channelId) {
    callback.serverHeartBeatTimeOut(channelId);
  }
}
