package com.tigerbrokers.stock.openapi.client.socket.executor;

import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon.DataType;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Default executor used by the SDK: each {@link DataType} owns its own single-thread queue
 * so callbacks of the same type are processed sequentially while different types do not block
 * one another.
 */
public class PerDataTypeSingleThreadExecutor implements MessageCallbackExecutor {

  private static final int DEFAULT_QUEUE_SIZE = 50000;

  private final Map<DataType, ExecutorService> threadPools = new ConcurrentHashMap<>();
  private final AtomicBoolean running = new AtomicBoolean(true);

  private static final Set<DataType> ORDER_DATA_TYPES = EnumSet.of(
      DataType.OrderStatus,
      DataType.OrderTransaction
  );

  private final int capacity;

  public PerDataTypeSingleThreadExecutor() {
    this(DEFAULT_QUEUE_SIZE);
  }

  public PerDataTypeSingleThreadExecutor(int capacity) {
    this.capacity = capacity;
  }

  @Override
  public void execute(Runnable callback, DataType dataType, String symbol) {
    if (!running.get()) {
      return;
    }
    if (dataType == null || callback == null) {
      if (callback != null) {
        callback.run();
      }
      return;
    }

    ExecutorService executorService = threadPools.computeIfAbsent(dataType,
        this::createSingleThreadExecutor);
    executorService.submit(callback);
  }

  ExecutorService createSingleThreadExecutor(DataType dataType) {
    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
    if (ORDER_DATA_TYPES.contains(dataType)) {
      handler = new CallerRunsPolicy();
    }
    return new ThreadPoolExecutor(
        1,
        1,
        0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<>(capacity),
        r -> {
          String threadName = "tiger-" + dataType.name().toLowerCase() + "-worker";
          Thread t = new Thread(r, threadName);
          t.setDaemon(true);
          return t;
        },
        handler
    );
  }

  @Override
  public void shutdown() {
    if (!running.compareAndSet(true, false)) {
      return;
    }
    for (ExecutorService executorService : threadPools.values()) {
      executorService.shutdown();
    }
    threadPools.clear();
  }

  @Override
  public void shutdownNow() {
    if (!running.compareAndSet(true, false)) {
      return;
    }
    for (ExecutorService executorService : threadPools.values()) {
      executorService.shutdownNow();
    }
    threadPools.clear();
  }
}
