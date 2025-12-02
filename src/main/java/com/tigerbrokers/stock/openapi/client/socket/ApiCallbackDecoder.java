package com.tigerbrokers.stock.openapi.client.socket;

import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.ERROR_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_CANCEL_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_SUB_SYMBOLS_END;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.TradeTick;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.KlineData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.PushData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBBOData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteDepthData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Response;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.TickData;
import com.tigerbrokers.stock.openapi.client.socket.executor.MessageCallbackExecutor;
import com.tigerbrokers.stock.openapi.client.socket.executor.PerDataTypeSingleThreadExecutor;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
import com.tigerbrokers.stock.openapi.client.util.QuoteDataUtil;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.TradeTickUtil;
import java.util.concurrent.RejectedExecutionException;

/**
 * Description: Created by lijiawen on 2018/05/23.
 */
public class ApiCallbackDecoder {

  private static final MessageCallbackExecutor DEFAULT_EXECUTOR = new PerDataTypeSingleThreadExecutor();

  private final ApiComposeCallback callback;
  private final MessageCallbackExecutor executor;

  public ApiCallbackDecoder(ApiComposeCallback callback) {
    this(callback, DEFAULT_EXECUTOR);
  }

  public ApiCallbackDecoder(ApiComposeCallback callback, MessageCallbackExecutor executor) {
    this.callback = callback;
    this.executor = (executor != null) ? executor : DEFAULT_EXECUTOR;
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
          final QuoteBasicData finalBasicData = basicData;
          executeCallback(() -> callback.quoteChange(finalBasicData), dataType,
              finalBasicData.getSymbol());
        }
        bboData = QuoteDataUtil.convertToAskBidData(pushData.getQuoteData());
        if (null != bboData) {
          final QuoteBBOData finalBboData = bboData;
          executeCallback(() -> callback.quoteAskBidChange(finalBboData), dataType,
              finalBboData.getSymbol());
        }
        break;
      case Option:
        basicData = QuoteDataUtil.convertToBasicData(pushData.getQuoteData());
        if (null != basicData) {
          final QuoteBasicData finalBasicData = basicData;
          executeCallback(() -> callback.optionChange(finalBasicData), dataType,
              finalBasicData.getSymbol());
        }
        bboData = QuoteDataUtil.convertToAskBidData(pushData.getQuoteData());
        if (null != bboData) {
          final QuoteBBOData finalBboData = bboData;
          executeCallback(() -> callback.optionAskBidChange(finalBboData), dataType,
              finalBboData.getSymbol());
        }
        break;
      case Future:
        basicData = QuoteDataUtil.convertToBasicData(pushData.getQuoteData());
        if (null != basicData) {
          final QuoteBasicData finalBasicData = basicData;
          executeCallback(() -> callback.futureChange(finalBasicData), dataType,
              finalBasicData.getSymbol());
        }
        bboData = QuoteDataUtil.convertToAskBidData(pushData.getQuoteData());
        if (null != bboData) {
          final QuoteBBOData finalBboData = bboData;
          executeCallback(() -> callback.futureAskBidChange(finalBboData), dataType,
              finalBboData.getSymbol());
        }
        break;
      case TradeTick:
        if (pushData.hasTickData()) {
          TickData tickData = pushData.getTickData();
          executeCallback(() -> callback.fullTickChange(tickData), dataType, tickData.getSymbol());
        } else {
          TradeTick tickData = TradeTickUtil.convert(pushData.getTradeTickData());
          executeCallback(() -> callback.tradeTickChange(tickData), dataType,
              tickData.getSymbol());
        }
        break;
      case QuoteDepth:
        QuoteDepthData quoteDepthData = pushData.getQuoteDepthData();
        executeCallback(() -> callback.depthQuoteChange(quoteDepthData), dataType,
            quoteDepthData.getSymbol());
        break;
      case Asset:
        executeCallback(() -> callback.assetChange(pushData.getAssetData()), dataType, null);
        break;
      case Position:
        executeCallback(() -> callback.positionChange(pushData.getPositionData()), dataType, null);
        break;
      case OrderStatus:
        executeCallback(() -> callback.orderStatusChange(pushData.getOrderStatusData()), dataType,
            null);
        break;
      case OrderTransaction:
        executeCallback(() -> callback.orderTransactionChange(pushData.getOrderTransactionData()),
            dataType, null);
        break;
      case StockTop:
        executeCallback(() -> callback.stockTopPush(pushData.getStockTopData()), dataType, null);
        break;
      case OptionTop:
        executeCallback(() -> callback.optionTopPush(pushData.getOptionTopData()), dataType, null);
        break;
      case Kline:
        final KlineData klineData = pushData.getKlineData();
        executeCallback(() -> callback.klineChange(klineData), dataType, klineData.getSymbol());
        break;
      default:
        ApiLogger.info("push data cannot be processed. {}", ProtoMessageUtil.toJson(msg));
    }
  }

  private void executeCallback(Runnable task, SocketCommon.DataType dataType, String symbol) {
    try {
      executor.execute(task, dataType, symbol);
    } catch (RejectedExecutionException ex) {
      ApiLogger.warn("message callback rejected, dataType:{}, symbol:{}", dataType, symbol, ex);
      task.run();
    } catch (Throwable th) {
      ApiLogger.error(
          "message callback execution error, dataType:" + dataType + ", symbol:" + symbol, th);
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
