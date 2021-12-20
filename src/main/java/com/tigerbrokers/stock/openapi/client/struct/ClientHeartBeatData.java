package com.tigerbrokers.stock.openapi.client.struct;

/**
 * @author  zhaolei
 * @since  2019/3/12
 * 描述
 */
public class ClientHeartBeatData {

  /**
   * client能保证发送心跳的最小间隔，0代表client不发送心跳
   */
  private int sendInterval = 0;
  /**
   * client希望收到server心跳的间隔，0代表client不希望收到server的心跳
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
