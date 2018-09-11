package com.tigerbrokers.stock.openapi.client.socket;

/**
 * Description:
 * Created by lijiawen on 2018/05/16.
 */
public interface ApiComposeCallback extends TradeApiCallback, QuoteApiCallback, SubscribeApiCallback {

  void client(WebSocketClient client);

  void error(String errorMsg);

  void error(int id, int errorCode, String errorMsg);

  void connectionClosed();

  void connectAck();
}
