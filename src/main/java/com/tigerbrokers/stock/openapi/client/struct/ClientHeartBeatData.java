package com.tigerbrokers.stock.openapi.client.struct;

/**
 * @author  zhaolei
 * @since  2019/3/12
 */
public class ClientHeartBeatData {

  /**
   * The client can guarantee the minimum interval for sending heartbeats, 0 means the client does not send heartbeats
   */
  private int sendInterval = 0;
  /**
   * The client wants to receive the heartbeat interval of the server, 0 means the client does not want to receive the server's heartbeat
   */
  private int receiveInterval = 0;

  public int getSendInterval() {
    return sendInterval;
  }

  public void setSendInterval(int sendInterval) {
    this.sendInterval = sendInterval;
  }

  public int getReceiveInterval() {
    return receiveInterval;
  }

  public void setReceiveInterval(int receiveInterval) {
    this.receiveInterval = receiveInterval;
  }

  public ClientHeartBeatData(int sendInterval, int receiveInterval) {
    this.sendInterval = sendInterval;
    this.receiveInterval = receiveInterval;
  }

  public ClientHeartBeatData() {
  }
}
