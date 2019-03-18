package com.tigerbrokers.stock.openapi.client.https.client;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Created by lijiawen 2018/04/25.
 */
public interface TigerClient {

  /**
   * @param request 请求对象
   * @param <T> request类型
   * @return TigerResponse 响应对象
   * @throws TigerApiException api异常
   */
  <T extends TigerResponse> T execute(TigerRequest<T> request) throws TigerApiException;
}
