package com.tigerbrokers.stock.openapi.client.socket.executor;

import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon.DataType;

/**
 * Executes callbacks directly on the calling (Netty I/O) thread, ensuring ordering while
 * avoiding extra scheduling overhead.
 */
public class CurrentThreadExecutor implements MessageCallbackExecutor {

  @Override
  public void execute(Runnable callback, DataType dataType, String symbol) {
    if (callback != null) {
      callback.run();
    }
  }

  @Override
  public void shutdown() {
  }

  @Override
  public void shutdownNow() {
  }
}
