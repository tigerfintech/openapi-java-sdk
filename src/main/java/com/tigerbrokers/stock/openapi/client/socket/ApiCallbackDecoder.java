package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.socket.data.pb.ApiMsg;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.ERROR_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_CANCEL_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_QUOTE_CHANGE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_SUB_SYMBOLS_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_TRADING_TICK_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIBE_ASSET;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIBE_ORDER_STATUS;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIBE_ORDER_TRANSACTION;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.SUBSCRIBE_POSITION;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.HEART_BEAT;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class ApiCallbackDecoder {

  private ApiComposeCallback callback;

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
      case SUBSCRIBE_ORDER_TRANSACTION:
        processOrderTransaction(msg);
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
    callback.positionChange(msg.getPositionData());
  }

  private void processAsset(ApiMsg msg) {
    callback.assetChange(msg.getAssetData());
  }

  private void processOrderStatus(ApiMsg msg) {
    callback.orderStatusChange(msg.getOrderStatusData());
  }

  private void processOrderTransaction(ApiMsg msg) {
    callback.orderTransactionChange(msg.getOrderTransactionData());
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
        callback.optionChange(msg.getQuoteOptionData());
        break;
      case TradeTick:
        // callback.tradeTickChange(TradeTickUtil.decodeData(jsonObject));
        // TODO
        callback.tradeTickChange(msg.getTradeTickData());
        break;
      case Future:
        callback.futureChange(msg.getQuoteFutureData());
        break;
      case QuoteDepth:
        callback.depthQuoteChange(msg.getQuoteDepthData());
        break;
      case Asset:
        callback.assetChange(msg.getAssetData());
        break;
      case Position:
        callback.positionChange(msg.getPositionData());
        break;
      case OrderStatus:
        callback.orderStatusChange(msg.getOrderStatusData());
        break;
      case OrderTransaction:
        callback.orderTransactionChange(msg.getOrderTransactionData());
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
    //String subject = stompFrame.headers().getAsString(StompHeaders.SUBSCRIPTION);
    //String content = stompFrame.content().toString(DEFAULT_CHARSET);
    //callback.subscribeEnd(id, subject, JSONObject.parseObject(content));
  }

  private void processCancelSubscribeEnd(ApiMsg msg) {
    //String subject = stompFrame.headers().getAsString(StompHeaders.SUBSCRIPTION);
    //String content = stompFrame.content().toString(DEFAULT_CHARSET);
    //callback.cancelSubscribeEnd(id, subject, JSONObject.parseObject(content));
  }

  private void processErrorEnd(ApiMsg msg) {
    if (!StringUtils.isEmpty(msg.getContent())) {
      callback.error(msg.getContent());
    } else if (!StringUtils.isEmpty(msg.getMessage())) {
      callback.error(msg.getMessage());
    } else {
      callback.error("unknown error");
    }
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
