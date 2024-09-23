package com.tigerbrokers.stock.openapi.client.util.watch;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author bean
 * @date 2023/2/22 7:55 PM
 */
public interface FileWatchedListener {
  void onCreated(WatchEvent<Path> watchEvent);

  void onModified(WatchEvent<Path> watchEvent);

  void onDeleted(WatchEvent<Path> watchEvent);

  void onOverflowed(WatchEvent<Path> watchEvent);
}
