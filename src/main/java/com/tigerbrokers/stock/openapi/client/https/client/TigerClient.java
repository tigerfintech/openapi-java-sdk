package com.tigerbrokers.stock.openapi.client.https.client;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Created by lijiawen 2018/04/25.
 *
 */
public interface TigerClient {

  /**
   * @throws TigerApiException
   */
  <T extends TigerResponse> T execute(TigerRequest<T> request);
}
