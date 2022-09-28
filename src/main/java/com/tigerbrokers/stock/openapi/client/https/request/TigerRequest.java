package com.tigerbrokers.stock.openapi.client.https.request;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

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
  MethodName getApiMethodName();

  /**
   * 设置当前接口的版本
   *
   * @param apiVersion API版本
   */
  void setApiVersion(String apiVersion);

  /**
   * 得到当前接口的版本
   *
   * @return API版本
   */
  String getApiVersion();

  /**
   * 得到当前API的响应结果类型
   *
   * @return 响应类型
   */
  Class<T> getResponseClass();

  /**
   * 设置请求时间
   *
   * @param timestamp 请求时间
   */
  void setTimestamp(String timestamp);

  /**
   * 得到请求时间
   *
   * @return 请求时间
   */
  String getTimestamp();

  /**
   * 设置请求业务参数
   *
   * @param apiModel 业务参数
   */
  void setApiModel(ApiModel apiModel);

  /**
   * 得到请求业务参数
   *
   * @return 业务参数
   */
  ApiModel getApiModel();
}
