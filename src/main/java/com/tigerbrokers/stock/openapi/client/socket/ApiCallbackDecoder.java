package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.PushData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBBOData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Response;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
import com.tigerbrokers.stock.openapi.client.util.QuoteDataUtil;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.TradeTickUtil;

import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.ERROR_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_CANCEL_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_SUB_SYMBOLS_END;

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
        processSubscribeDataChange(msg);
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
    QuoteBasicData basicData = null;
    QuoteBBOData bboData = null;
    SocketCommon.DataType dataType = pushData.getDataType();
    switch (dataType) {
      case Quote:
        basicData = QuoteDataUtil.convertToBasicData(pushData.getQuoteData());
        if (null != basicData) {
          callback.quoteChange(basicData);
        }
        bboData = QuoteDataUtil.convertToAskBidData(pushData.getQuoteData());
        if (null != bboData) {
          callback.quoteAskBidChange(bboData);
        }
        break;
      case Option:
        basicData = QuoteDataUtil.convertToBasicData(pushData.getQuoteData());
        if (null != basicData) {
          callback.optionChange(basicData);
        }
        bboData = QuoteDataUtil.convertToAskBidData(pushData.getQuoteData());
        if (null != bboData) {
          callback.optionAskBidChange(bboData);
        }
        break;
      case Future:
        basicData = QuoteDataUtil.convertToBasicData(pushData.getQuoteData());
        if (null != basicData) {
          callback.futureChange(basicData);
        }
        bboData = QuoteDataUtil.convertToAskBidData(pushData.getQuoteData());
        if (null != bboData) {
          callback.futureAskBidChange(bboData);
        }
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
      case StockTop:
        callback.stockTopPush(pushData.getStockTopData());
      case OptionTop:
        callback.optionTopPush(pushData.getOptionTopData());
        break;
      default:
        ApiLogger.info("push data cannot be processed. {}", ProtoMessageUtil.toJson(msg));
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

  public void processHeartBeat(final String content) {
    callback.hearBeat(content);
  }

  public void serverHeartBeatTimeOut(String channelId) {
    callback.serverHeartBeatTimeOut(channelId);
  }
}
