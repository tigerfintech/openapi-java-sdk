package com.tigerbrokers.stock.openapi.client.socket.executor;

import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon.DataType;
/**
 * Abstraction for dispatching decoded socket callbacks. The SDK uses
 * {@link PerDataTypeSingleThreadExecutor} by default.
 */
public interface MessageCallbackExecutor {

  /**
   * Execute a callback.
   *
   * @param callback the task to run
   * @param dataType the data type of the message
   * @param symbol   quote symbol, may be {@code null}
   */
  void execute(Runnable callback, DataType dataType, String symbol);

  /**
   * Gracefully shut down underlying resources.
   */
  void shutdown();

  /**
   * Immediately shut down underlying resources.
   */
  void shutdownNow();
}