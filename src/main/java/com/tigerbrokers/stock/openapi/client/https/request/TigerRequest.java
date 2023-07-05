package com.tigerbrokers.stock.openapi.client.https.request;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * request interface
 * Created by lijiawen on 2018/04/25.
 */
public interface TigerRequest<T extends TigerResponse> {

  /**
   * get API method name
   *
   * @return API method name
   */
  MethodName getApiMethodName();

  /**
   * set API method version
   *
   * @param apiVersion API method version
   */
  void setApiVersion(String apiVersion);

  /**
   * get API method version
   *
   * @return API method version
   */
  String getApiVersion();

  /**
   * get response object's class
   *
   * @return response class
   */
  Class<T> getResponseClass();

  /**
   * set local request time
   *
   * @param timestamp request time
   */
  void setTimestamp(String timestamp);

  /**
   * get local request time
   *
   * @return request time
   */
  String getTimestamp();

  /**
   * set API request model parameter
   *
   * @param apiModel model parameter
   */
  void setApiModel(ApiModel apiModel);

  /**
   * get API request model parameter
   *
   * @return model parameter
   */
  ApiModel getApiModel();
}
