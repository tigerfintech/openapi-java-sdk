package com.tigerbrokers.stock.openapi.client.socket;

/**
 * Description:
 * Created by lijiawen on 2018/05/16.
 */
public interface ApiComposeCallback extends SubscribeApiCallback {

  void error(String errorMsg);

  void error(int id, int errorCode, String errorMsg);

  void connectionClosed();

  /**
   * kicked out by a new connection. recommend to terminate the connection.
   * The same tigerId has only one active connection
   * @param errorCode errorCode
   * @param errorMsg errorMsg
   */
  void connectionKickout(int errorCode, String errorMsg);

  void connectionAck();

  /**
   * @param serverSendInterval The server guarantees the minimum interval for sending heartbeats, 0 means the server does not send heartbeats
   * @param serverReceiveInterval The interval at which the server expects to receive client heartbeats, 0 means the server does not wish to receive client heartbeats
   */
  void connectionAck(int serverSendInterval, int serverReceiveInterval);

  void hearBeat(String heartBeatContent);

  void serverHeartBeatTimeOut(String channelId);
}
