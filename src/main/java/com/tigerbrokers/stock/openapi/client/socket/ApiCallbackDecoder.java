package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.PushData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Response;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.TradeTickUtil;

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

  public synchronized void handle(Response msg) {
    if (SocketCommon.Command.HEARTBEAT == msg.getCommand()) {
      processHeartBeat(HEART_BEAT);
      return;
    }
    int retType = msg.getRetType();

    switch (retType) {
      case SUBSCRIBE_POSITION:
      case SUBSCRIBE_ASSET:
      case SUBSCRIBE_ORDER_STATUS:
      case SUBSCRIBE_ORDER_TRANSACTION:
      case GET_QUOTE_CHANGE_END:
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

  private void processPosition(Response msg) {
    callback.positionChange(msg.getBody().getPositionData());
  }

  private void processAsset(Response msg) {
    callback.assetChange(msg.getBody().getAssetData());
  }

  private void processOrderStatus(Response msg) {
    callback.orderStatusChange(msg.getBody().getOrderStatusData());
  }

  private void processOrderTransaction(Response msg) {
    callback.orderTransactionChange(msg.getBody().getOrderTransactionData());
  }

  private void processSubscribeQuoteChange(Response msg) {
    PushData pushData = msg.getBody();
    if (pushData == null || pushData.getDataType() == null) {
      return;
    }
    SocketCommon.DataType dataType = pushData.getDataType();
    switch (dataType) {
      case Quote:
        callback.quoteChange(pushData.getQuoteData());
        break;
      case Option:
        callback.optionChange(pushData.getQuoteData());
        break;
      case Future:
        callback.futureChange(pushData.getQuoteData());
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
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("code", msg.getCode());
    jsonObject.put("message", msg.getMsg());
    callback.subscribeEnd(String.valueOf(msg.getId()),
        dataType == null ? null : dataType.name(), jsonObject);
  }

  private void processCancelSubscribeEnd(Response msg) {
    SocketCommon.DataType dataType = msg.getBody() == null ? null : msg.getBody().getDataType();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("code", msg.getCode());
    jsonObject.put("message", msg.getMsg());
    callback.cancelSubscribeEnd(String.valueOf(msg.getId()),
        dataType == null ? null : dataType.name(), jsonObject);
  }

  private void processErrorEnd(Response msg) {
    if (!StringUtils.isEmpty(msg.getMsg())) {
      callback.error(msg.getMsg());
    } else {
      callback.error("unknown error");
    }
  }

  private void processDefault(Response msg) {
    ApiLogger.info("retType:{} cannot be processed.", msg.getRetType());
  }

  private void processHeartBeat(final String content) {
    callback.hearBeat(content);
  }

  public void serverHeartBeatTimeOut(String channelId) {
    callback.serverHeartBeatTimeOut(channelId);
  }
}
