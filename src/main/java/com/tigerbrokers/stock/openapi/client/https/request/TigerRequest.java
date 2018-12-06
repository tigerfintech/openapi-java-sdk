package com.tigerbrokers.stock.openapi.client.https.request;

import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * 请求接口。
 * Created by lijiawen on 2018/04/25.
 */
public interface TigerRequest<T extends TigerResponse> {

  /**
   * 获取API名称。
   *
   * @return API名称
   */
  String getApiMethodName();

  /**
   * 得到当前接口的版本
   *
   * @return API版本
   */
  String getApiVersion();
}
