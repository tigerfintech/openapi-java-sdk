package com.tigerbrokers.stock.openapi.client.socket.executor;

import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon.DataType;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Executor implementation that keeps independent queues per {@link DataType} and, for quote-related
 * types, routes callbacks to single-thread workers chosen by symbol hash.
 */
public class PerDataTypeSymbolHashExecutor implements MessageCallbackExecutor {

  private static final int DEFAULT_QUEUE_SIZE = 50000;
  private static final int DEFAULT_SYMBOL_THREADS = 4;
  private static final Set<DataType> QUOTE_DATA_TYPES = EnumSet.of(
      DataType.Quote,
      DataType.Option,
      DataType.Future,
      DataType.QuoteDepth,
      DataType.TradeTick,
      DataType.Kline
  );
  private static final Set<DataType> ORDER_DATA_TYPES = EnumSet.of(
      DataType.OrderStatus,
      DataType.OrderTransaction
  );

  private final ConcurrentMap<DataType, ExecutorGroup> executorGroups = new ConcurrentHashMap<>();
  private final AtomicBoolean running = new AtomicBoolean(true);
  private final int queueCapacity;
  private final int symbolThreadCount;

  public PerDataTypeSymbolHashExecutor() {
    this(DEFAULT_QUEUE_SIZE, DEFAULT_SYMBOL_THREADS);
  }

  public PerDataTypeSymbolHashExecutor(int queueCapacity, int symbolThreadCount) {
    this.queueCapacity = queueCapacity <= 0 ? DEFAULT_QUEUE_SIZE : queueCapacity;
    this.symbolThreadCount = symbolThreadCount <= 0 ? DEFAULT_SYMBOL_THREADS : symbolThreadCount;
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
    ExecutorGroup group = executorGroups.computeIfAbsent(dataType, this::createGroup);
    group.execute(callback, symbol);
  }

  private ExecutorGroup createGroup(DataType dataType) {
    if (QUOTE_DATA_TYPES.contains(dataType)) {
      return new SymbolHashGroup("tiger-" + dataType.name().toLowerCase(), symbolThreadCount,
          queueCapacity);
    }
    return new SingleThreadGroup(dataType, "tiger-" + dataType.name().toLowerCase(), queueCapacity);
  }

  @Override
  public void shutdown() {
    if (!running.compareAndSet(true, false)) {
      return;
    }
    executorGroups.values().forEach(ExecutorGroup::shutdown);
    executorGroups.clear();
  }

  @Override
  public void shutdownNow() {
    if (!running.compareAndSet(true, false)) {
      return;
    }
    executorGroups.values().forEach(ExecutorGroup::shutdownNow);
    executorGroups.clear();
  }

  private interface ExecutorGroup {

    void execute(Runnable task, String symbol);

    void shutdown();

    void shutdownNow();
  }

  private static class SingleThreadGroup implements ExecutorGroup {

    private final ExecutorService executor;

    SingleThreadGroup(DataType dataType, String threadName, int queueCapacity) {
      RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
      if (ORDER_DATA_TYPES.contains(dataType)) {
        handler = new CallerRunsPolicy();
      }

      this.executor = new ThreadPoolExecutor(
          1,
          1,
          0L,
          TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<>(queueCapacity),
          buildThreadFactory(threadName + "-worker"),
          handler
      );
    }

    @Override
    public void execute(Runnable task, String symbol) {
      executor.submit(task);
    }

    @Override
    public void shutdown() {
      executor.shutdown();
    }

    @Override
    public void shutdownNow() {
      executor.shutdownNow();
    }
  }

  private static class SymbolHashGroup implements ExecutorGroup {

    private final ExecutorService[] executors;

    SymbolHashGroup(String baseName, int threadCount, int queueCapacity) {
      int safeCount = threadCount <= 0 ? DEFAULT_SYMBOL_THREADS : threadCount;
      this.executors = new ExecutorService[safeCount];
      for (int i = 0; i < safeCount; i++) {
        final String threadName = baseName + "-worker-" + i;
        executors[i] = new ThreadPoolExecutor(
            1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(queueCapacity),
            buildThreadFactory(threadName),
            new ThreadPoolExecutor.DiscardOldestPolicy()
        );
      }
    }

    @Override
    public void execute(Runnable task, String symbol) {
      pick(symbol).submit(task);
    }

    private ExecutorService pick(String symbol) {
      if (symbol == null || symbol.isEmpty()) {
        return executors[0];
      }
      int index = (symbol.hashCode() & 0x7fffffff) % executors.length;

      return executors[index];
    }

    @Override
    public void shutdown() {
      for (ExecutorService executor : executors) {
        executor.shutdown();
      }
    }

    @Override
    public void shutdownNow() {
      for (ExecutorService executor : executors) {
        executor.shutdownNow();
      }
    }
  }

  private static ThreadFactory buildThreadFactory(String threadName) {
    return r -> {
      Thread t = new Thread(r, threadName);
      t.setDaemon(true);
      return t;
    };
  }
}