package com.tigerbrokers.stock.openapi.client.https.client;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Created by lijiawen 2018/04/25.
 */
public interface TigerClient {

  /**
   * @param request request parameter
   * @param <T> request type
   * @return TigerResponse response object
   * @throws TigerApiException api exception
   */
  <T extends TigerResponse> T execute(TigerRequest<T> request) throws TigerApiException;
}
