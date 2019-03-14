package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import io.netty.handler.codec.stomp.StompFrame;
import java.nio.charset.Charset;

import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.END_CONN;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_CANCEL_SUBSCRIBE_END;
import static com.tigerbrokers.stock.openapi.client.constant.RspProtocolType.GET_OPTION_CHANGE_END;
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

  public ApiCallbackDecoder(ApiComposeCallback callback) {
    this.callback = callback;
  }

  public synchronized void handle(StompFrame stompFrame) {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    if (!StringUtils.isEmpty(content) && "Heart_Beat".equals(content)){
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
      case GET_OPTION_CHANGE_END:
        processSubscribeOptionEnd();
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

  private void processSubscribeOptionEnd() {
    String content = stompFrame.content().toString(DEFAULT_CHARSET);
    callback.optionChange(JSONObject.parseObject(content));
  }

  private void processDefault() {
    ApiLogger.info("ret-type:{} cannot be processed.", retType);
  }

  private void  processHeartBeat(final String content){
    callback.hearBeat(content);
  }

  public void serverHeartBeatTimeOut(String channelId){
    callback.serverHeartBeatTimeOut(channelId);
  }
}
