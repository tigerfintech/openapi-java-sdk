package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.PushData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Response;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
import com.tigerbrokers.stock.openapi.client.util.QuoteDataUtil;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.TradeTickUtil;

import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.DEFAULT_PUSH_DATA_END;
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

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class ApiCallbackDecoder {

  private ApiComposeCallback callback;

  public ApiCallbackDecoder(ApiComposeCallback callback) {
    this.callback = callback;
  }

  public synchronized void handle(Response msg) {
    int code = msg.getCode();

    switch (code) {
      case DEFAULT_PUSH_DATA_END:
      case SUBSCRIBE_POSITION:
      case SUBSCRIBE_ASSET:
      case SUBSCRIBE_ORDER_STATUS:
      case SUBSCRIBE_ORDER_TRANSACTION:
      case GET_QUOTE_CHANGE_END:
      case GET_TRADING_TICK_END:
        processSubscribeDataChange(msg);
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

  private void processSubscribeDataChange(Response msg) {
    PushData pushData = msg.getBody();
    if (pushData == null || pushData.getDataType() == null) {
      return;
    }
    SocketCommon.DataType dataType = pushData.getDataType();
    switch (dataType) {
      case Quote:
        QuoteDataUtil.convertToBasicData(pushData.getQuoteData()).ifPresent(
            x -> callback.quoteChange(x));
        QuoteDataUtil.convertToAskBidData(pushData.getQuoteData()).ifPresent(
            x -> callback.quoteAskBidChange(x));
        break;
      case Option:
        QuoteDataUtil.convertToBasicData(pushData.getQuoteData()).ifPresent(
            x -> callback.optionChange(x));
        QuoteDataUtil.convertToAskBidData(pushData.getQuoteData()).ifPresent(
            x -> callback.optionAskBidChange(x));
        break;
      case Future:
        QuoteDataUtil.convertToBasicData(pushData.getQuoteData()).ifPresent(
            x -> callback.futureChange(x));
        QuoteDataUtil.convertToAskBidData(pushData.getQuoteData()).ifPresent(
            x -> callback.futureAskBidChange(x));
        break;
      case TradeTick:
        callback.tradeTickChange(TradeTickUtil.convert(pushData.getTradeTickData()));
        break;
      case QuoteDepth:
        callback.depthQuoteChange(pushData.getQuoteDepthData());
        break;
      case Asset:
        callback.assetChange(pushData.getAssetData());
        break;
      case Position:
        callback.positionChange(pushData.getPositionData());
        break;
      case OrderStatus:
        callback.orderStatusChange(pushData.getOrderStatusData());
        break;
      case OrderTransaction:
        callback.orderTransactionChange(pushData.getOrderTransactionData());
        break;
      default:
        ApiLogger.info("push data cannot be processed.", ProtoMessageUtil.toJson(msg));
    }
  }

  private void processGetSubscribedSymbols(Response msg) {
    String subscribedSymbol = msg.getMsg();
    callback.getSubscribedSymbolEnd(JSONObject.parseObject(subscribedSymbol, SubscribedSymbol.class));
  }

  private void processSubscribeEnd(Response msg) {
    SocketCommon.DataType dataType = msg.getBody() == null ? null : msg.getBody().getDataType();
    callback.subscribeEnd(msg.getId(),
        dataType == null ? null : dataType.name(), msg.getMsg());
  }

  private void processCancelSubscribeEnd(Response msg) {
    SocketCommon.DataType dataType = msg.getBody() == null ? null : msg.getBody().getDataType();
    callback.cancelSubscribeEnd(msg.getId(),
        dataType == null ? null : dataType.name(), msg.getMsg());
  }

  private void processErrorEnd(Response msg) {
    if (!StringUtils.isEmpty(msg.getMsg())) {
      callback.error(msg.getMsg());
    } else {
      callback.error("unknown error");
    }
  }

  private void processDefault(Response msg) {
    ApiLogger.info("receive MESSAGE's code:{} cannot be processed.", msg.getCode());
  }

  public void processHeartBeat(final String content) {
    callback.hearBeat(content);
  }

  public void serverHeartBeatTimeOut(String channelId) {
    callback.serverHeartBeatTimeOut(channelId);
  }
}
