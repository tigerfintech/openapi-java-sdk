package com.tigerbrokers.stock.openapi.client.util.watch;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TokenManager;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TOKEN_FILENAME;

/**
 * @author bean
 * @date 2023/2/22 7:56 PM
 */
public class TokenFileWatched implements FileWatchedListener {

  private final ClientConfig clientConfig;
  private final TokenManager tokenManager;

  public TokenFileWatched(ClientConfig clientConfig, TokenManager tokenManager) {
    this.clientConfig = clientConfig;
    this.tokenManager = tokenManager;
  }

  @Override
  public void onCreated(WatchEvent<Path> watchEvent) {
    String fileName = watchEvent.context().toString();
    if (!TOKEN_FILENAME.equals(fileName)) {
      return;
    }
    boolean load = tokenManager.loadTokenFile(clientConfig);
    ApiLogger.info("{} is created, reload token {}", fileName, load ? "success" : "fail");

  }

  @Override
  public void onModified(WatchEvent<Path> watchEvent) {
    String fileName = watchEvent.context().toString();
    if (!TOKEN_FILENAME.equals(fileName)) {
      return;
    }
    boolean load = tokenManager.loadTokenFile(clientConfig);
    ApiLogger.info("{} is modifed, reload token {}", fileName, load ? "success" : "fail");
  }

  @Override
  public void onDeleted(WatchEvent<Path> watchEvent) {
    String fileName = watchEvent.context().toString();
    if (!TOKEN_FILENAME.equals(fileName)) {
      return;
    }
    ApiLogger.info("{} is deleted, ignore", fileName);
  }

  @Override
  public void onOverflowed(WatchEvent<Path> watchEvent) {
    String fileName = watchEvent.context().toString();
    if (!TOKEN_FILENAME.equals(fileName)) {
      return;
    }
    ApiLogger.info("{} is overflowed, ignore", fileName);
  }
}
