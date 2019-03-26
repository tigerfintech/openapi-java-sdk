package com.tigerbrokers.stock.openapi.client.struct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @创建人 zhaolei
 * @创建时间 2019/3/12
 * 描述
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ClientHeartBeatData {

  /**
   * client能保证发送心跳的最小间隔，0代表client不发送心跳
   */
  private int sendInterval = 0;
  /**
   * client希望收到server心跳的间隔，0代表client不希望收到server的心跳
   */
  private int receiveInterval = 0;
}
