package com.tigerbrokers.stock.openapi.client.util.watch;

import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

/**
 * @author bean
 * @date 2023/2/22 8:04 PM
 */
public class FileWatchedService {

  private WatchService watchService;

  private FileWatchedListener listener;

  /**
   * constructor
   *
   * @param path     watched directory
   * @param listener file listener
   * @throws IOException
   */
  public FileWatchedService(Path path, FileWatchedListener listener) throws IOException {
    watchService = FileSystems.getDefault().newWatchService();
    path.register(watchService,
        StandardWatchEventKinds.ENTRY_CREATE,
        StandardWatchEventKinds.ENTRY_DELETE,
        StandardWatchEventKinds.ENTRY_MODIFY,
        StandardWatchEventKinds.OVERFLOW);

    this.listener = listener;
  }

  public void watch() {
    while (true) {
      try {
        WatchKey watchKey = watchService.take();
        List<WatchEvent<?>> watchEventList = watchKey.pollEvents();
        for (WatchEvent<?> watchEvent : watchEventList) {
          WatchEvent.Kind<?> kind = watchEvent.kind();

          WatchEvent<Path> curEvent = (WatchEvent<Path>) watchEvent;
          if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
            listener.onModified(curEvent);
          } else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
            listener.onCreated(curEvent);
          } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
            listener.onDeleted(curEvent);
          } else if (kind == StandardWatchEventKinds.OVERFLOW) {
            listener.onOverflowed(curEvent);
          }
        }

        if (!watchKey.reset()) {
          break;
        }
      } catch (Throwable th) {
        ApiLogger.error("watch file fail", th);
      }
    }
  }

}


