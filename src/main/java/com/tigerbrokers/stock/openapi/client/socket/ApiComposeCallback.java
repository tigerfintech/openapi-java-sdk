package com.tigerbrokers.stock.openapi.client.socket;

/**
 * Description:
 * Created by lijiawen on 2018/05/16.
 */
public interface ApiComposeCallback extends SubscribeApiCallback {

  void client(WebSocketClient client);

  void error(String errorMsg);

  void error(int id, int errorCode, String errorMsg);

  void connectionClosed();

  void connectAck();

  /**
   *
   * @param serverSendInterval      server保证发送心跳的最小间隔，0代表server不发送心跳
   * @param serverReceiveInterval   server希望收到client心跳的间隔，0表示server不希望收到client的心跳
   */
  void connectAck(int serverSendInterval, int serverReceiveInterval);

  void hearBeat(String heartBeatContent);

  void serverHeartBeatTimeOut(String channelIdAsLongText);
}
