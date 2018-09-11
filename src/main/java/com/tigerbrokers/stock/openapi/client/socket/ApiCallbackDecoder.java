package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
import io.netty.handler.codec.stomp.StompFrame;
import java.nio.charset.Charset;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.*;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class ApiCallbackDecoder {

  private static Logger logger = LoggerFactory.getLogger(ApiCallbackDecoder.class);

  private ApiComposeCallback callback;
  private StompFrame stompFrame;
  private CyclicBarrier cyclicBarrier;
  private OrderIdPassport orderIdPassport;
  private boolean async;
  private int retType;
  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  public ApiCallbackDecoder(ApiComposeCallback callback, boolean async, CyclicBarrier cyclicBarrier,
      OrderIdPassport orderIdPassport) {
    this.callback = callback;
    this.cyclicBarrier = cyclicBarrier;
    this.async = async;
    this.orderIdPassport = orderIdPassport;
  }

  public synchronized void handle(StompFrame stompFrame) {
    init(stompFrame);

    switch (retType) {
      case GET_ORDER_NO_END:
        processGetOrderNoEnd();
        break;
      case PREVIEW_ORDER_END:
        processPreviewOrderEnd();
        break;
      case PLACE_ORDER_END:
        processPlaceOrderEnd();
        break;
      case CANCEL_ORDER_END:
        processCancelOrderEnd();
        break;
      case MODIFY_ORDER_END:
        processModifyOrderEnd();
        break;
      case GET_ASSET_END:
        processGetAssetEnd();
        break;
      case GET_POSITION_END:
        processGetPositionEnd();
        break;
      case GET_ACCOUNT_END:
        processGetAccountEnd();
        break;
      case SUBSCRIBE_POSITION:
        processPosition();
        break;
      case SUBSCRIBE_ASSET:
        processAsset();
        break;
      case SUBSCRIBE_ORDER_STATUS:
        processOrderStatus();
        break;
      case GET_MARKET_STATE_END:
        processMarketState();
        break;
      case GET_ALL_SYMBOLS_END:
        processAllSymbols();
        break;
      case GET_ALL_SYMBOL_NAMES_END:
        processAllSymbolNames();
        break;
      case GET_BRIEF_INFO_END:
        processBriefInfo();
        break;
      case GET_STOCK_DETAIL_END:
        processStockDetail();
        break;
      case GET_TIME_LINE_END:
        processTimeline();
        break;
      case GET_HOUR_TRADING_TIME_LINE_END:
        processHourTradingTimeline();
        break;
      case GET_KLINE_END:
        processKline();
        break;
      case GET_TRADING_TICK_END:
        processTradingTick();
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
      case END_CONN:
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

  private void processGetOrderNoEnd() {
    JSONObject jsonObject = JSON.parseObject(stompFrame.content().toString(DEFAULT_CHARSET));
    orderIdPassport.setOrderId(jsonObject.getIntValue("orderId"));
    callback.orderNoEnd(jsonObject);
    nonAsyncWait();
  }

  private void nonAsyncWait() {
    if (!async) {
      try {
        cyclicBarrier.await();
        cyclicBarrier.reset();
      } catch (InterruptedException e) {
        logger.error("interrupted exception", e);
      } catch (BrokenBarrierException e) {
        logger.error("broken barrier exception", e);
      }
    }
  }

  private void processPreviewOrderEnd() {
    callback.previewOrderEnd(JSON.parseObject(stompFrame.content().toString(DEFAULT_CHARSET)));
  }

  private void processPlaceOrderEnd() {
    callback.placeOrderEnd(JSON.parseObject(stompFrame.content().toString(DEFAULT_CHARSET)));
  }

  private void processCancelOrderEnd() {
    callback.cancelOrderEnd(JSON.parseObject(stompFrame.content().toString(DEFAULT_CHARSET)));
  }

  private void processModifyOrderEnd() {
    callback.modifyOrderEnd(JSON.parseObject(stompFrame.content().toString(DEFAULT_CHARSET)));
  }

  private void processGetAssetEnd() {
    callback.getAssetEnd(JSON.parseObject(stompFrame.content().toString(DEFAULT_CHARSET)));
  }

  private void processGetPositionEnd() {
    callback.getPositionEnd(JSON.parseObject(stompFrame.content().toString(DEFAULT_CHARSET)));
  }

  private void processGetAccountEnd() {
    callback.getAccountEnd(JSON.parseObject(stompFrame.content().toString(DEFAULT_CHARSET)));
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

  private void processMarketState() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getMarketStateEnd(JSONObject.parseObject(content));
  }

  private void processAllSymbols() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getAllSymbolsEnd(JSONObject.parseObject(content));
  }

  private void processAllSymbolNames() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getAllSymbolNamesEnd(JSONObject.parseObject(content));
  }

  private void processBriefInfo() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getBriefInfoEnd(JSONObject.parseObject(content));
  }

  private void processStockDetail() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getStockDetailEnd(JSONObject.parseObject(content));
  }

  private void processTimeline() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getTimelineEnd(JSONObject.parseObject(content));
  }

  private void processHourTradingTimeline() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getHourTradingTimelineEnd(JSONObject.parseObject(content));
  }

  private void processKline() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getKlineEnd(JSONObject.parseObject(content));
  }

  private void processTradingTick() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.getTradeTickEnd(JSONObject.parseObject(content));
  }

  private void processSubscribeQuoteChange() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.quoteChange(JSONObject.parseObject(content));
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

  private void processDefault() {
    logger.info("ret-type:{} cannot be processed.", retType);
  }
}
