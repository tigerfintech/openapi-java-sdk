package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

/**
 * Local storage for OAuth2 tokens and dynamic client registrations.
 *
 * <p>Tokens are keyed by client ID and registrations by issuer. Token files are restricted to
 * the owner on POSIX file systems. The storage directory is selected from the explicit home,
 * {@code TIGEROPEN_HOME}, or {@code ~/.tiger/openapi/}, in that order.</p>
 */
public class OAuth2TokenStore {

  private static final Charset UTF_8 = Charset.forName("UTF-8");
  private static final String ENV_HOME = "TIGEROPEN_HOME";

  private final Path home;

  public OAuth2TokenStore() {
    this(null);
  }

  public OAuth2TokenStore(String customHome) {
    String dir = customHome;
    if (dir == null || dir.isEmpty()) {
      dir = System.getenv(ENV_HOME);
    }
    if (dir == null || dir.isEmpty()) {
      dir = System.getProperty("user.home") + File.separator + ".tiger"
          + File.separator + "openapi";
    }
    this.home = new File(dir).toPath();
  }

  public Path getHome() {
    return home;
  }

  /**
   * Reads the authorization state for a given clientId.
   *
   * @param expectedIssuer the expected issuer; a mismatch with the one recorded in the file is
   *     treated as a cache miss (returns null)
   * @return token state, or {@code null} when the file is absent or unreadable
   */
  public OAuth2Token loadToken(String clientId, String expectedIssuer) {
    Path file = tokenFile(clientId);
    if (!Files.exists(file)) {
      return null;
    }
    try {
      OAuth2Token token = JSON.parseObject(readString(file), OAuth2Token.class);
      if (token == null) {
        return null;
      }
      if (token.getSchemaVersion() != 1) {
        // Reject unsupported schemas instead of inferring their structure.
        throw new OAuth2Exception(OAuth2Exception.Category.STORAGE_FAILED,
            "unsupported token schema_version: " + token.getSchemaVersion()
                + ", file: " + file);
      }
      if (expectedIssuer != null && !expectedIssuer.equals(token.getIssuer())) {
        ApiLogger.info("token issuer mismatch, treat as cache miss. clientId:{}", clientId);
        return null;
      }
      return token;
    } catch (OAuth2Exception e) {
      throw e;
    } catch (Exception e) {
      ApiLogger.error("read token file fail:{}", file, e);
      return null;
    }
  }

  /** Atomic write plus 0600. */
  public void saveToken(OAuth2Token token) {
    if (token == null || token.getClientId() == null) {
      throw new IllegalArgumentException("token or clientId is null");
    }
    writeAtomic(tokenFile(token.getClientId()), JSON.toJSONString(token));
  }

  /** For logout: clears only the token and keeps the client_id. */
  public void deleteToken(String clientId) {
    try {
      Files.deleteIfExists(tokenFile(clientId));
    } catch (IOException e) {
      ApiLogger.error("delete token file fail. clientId:{}", clientId, e);
    }
  }

  /**
   * Reads the client_id previously obtained by dynamic registration for a given issuer.
   *
   * <p>The issuer is known at startup, so the filename is computed directly and read; no
   * directory scan is needed.</p>
   */
  public String loadClientId(String issuer) {
    OAuth2ClientRegistration reg = loadClientRegistration(issuer);
    return reg == null ? null : reg.getClientId();
  }

  /** Reads the full registration, including registeredScopes for deciding whether the scope suffices. */
  public OAuth2ClientRegistration loadClientRegistration(String issuer) {
    Path file = clientFile(issuer);
    if (!Files.exists(file)) {
      return null;
    }
    try {
      return JSON.parseObject(readString(file), OAuth2ClientRegistration.class);
    } catch (Exception e) {
      ApiLogger.error("read client file fail:{}", file, e);
      return null;
    }
  }

  public void saveClientRegistration(OAuth2ClientRegistration registration) {
    if (registration == null || registration.getIssuer() == null) {
      throw new IllegalArgumentException("registration or issuer is null");
    }
    writeAtomic(clientFile(registration.getIssuer()), JSON.toJSONString(registration));
  }

  /**
   * Removes a dynamic client registration after an {@code invalid_client} response.
   *
   * <p>Normal logout retains the registration.</p>
   */
  public void deleteClientRegistration(String issuer) {
    try {
      Files.deleteIfExists(clientFile(issuer));
    } catch (IOException e) {
      ApiLogger.error("delete client file fail. issuer:{}", issuer, e);
    }
  }

  Path tokenFile(String clientId) {
    return home.resolve("tokens").resolve(safeFileName(clientId) + ".json");
  }

  Path clientFile(String issuer) {
    return home.resolve("clients").resolve(hash(issuer) + ".json");
  }

  /**
   * A clientId is normally a UUID and usable as a filename directly, but it comes from the
   * server so that cannot be assumed. Dangerous characters such as path separators are
   * replaced to prevent writing outside the directory.
   */
  private static String safeFileName(String raw) {
    if (raw == null || raw.isEmpty()) {
      throw new IllegalArgumentException("clientId is empty");
    }
    StringBuilder sb = new StringBuilder(raw.length());
    for (int i = 0; i < raw.length(); i++) {
      char c = raw.charAt(i);
      boolean ok = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
          || (c >= '0' && c <= '9') || c == '-' || c == '_' || c == '.';
      sb.append(ok ? c : '_');
    }
    // Path separators and consecutive dots are normalized to keep the result within the directory.
    String name = sb.toString();
    while (name.contains("..")) {
      name = name.replace("..", "__");
    }
    return name;
  }

  /** The issuer is a URL containing {@code ://} and {@code /}, so it cannot be a filename directly. */
  static String hash(String issuer) {
    if (issuer == null) {
      throw new IllegalArgumentException("issuer is null");
    }
    try {
      java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
      byte[] digest = md.digest(issuer.getBytes(UTF_8));
      StringBuilder sb = new StringBuilder();
      // The first 16 bytes are enough to avoid collisions and keep the filename short
      for (int i = 0; i < 16; i++) {
        sb.append(String.format("%02x", digest[i]));
      }
      return sb.toString();
    } catch (Exception e) {
      throw new OAuth2Exception(OAuth2Exception.Category.STORAGE_FAILED,
          "hash issuer fail", e);
    }
  }

  private static String readString(Path file) throws IOException {
    return new String(Files.readAllBytes(file), UTF_8);
  }

  /**
   * Writes through a same-directory temporary file and atomically replaces the target when
   * supported.
   */
  private static void writeAtomic(Path target, String content) {
    try {
      Path dir = target.getParent();
      Files.createDirectories(dir);
      restrictDirectory(dir);
      Path tmp = Files.createTempFile(dir, ".tmp-", ".json");
      restrictFile(tmp);
      Files.write(tmp, content.getBytes(UTF_8));
      try {
        Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE);
      } catch (AtomicMoveNotSupportedException e) {
        Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING);
      }
      restrictFile(target);
    } catch (IOException e) {
      throw new OAuth2Exception(OAuth2Exception.Category.STORAGE_FAILED,
          "write file fail: " + target, e);
    }
  }

  /**
   * Restricts a token file to owner read and write access on POSIX file systems.
   */
  private static void restrictFile(Path file) {
    try {
      Set<PosixFilePermission> perms = new HashSet<>();
      perms.add(PosixFilePermission.OWNER_READ);
      perms.add(PosixFilePermission.OWNER_WRITE);
      Files.setPosixFilePermissions(file, perms);
    } catch (Exception ignore) {
      // POSIX permissions are unavailable on some file systems.
    }
  }

  private static void restrictDirectory(Path dir) {
    try {
      Set<PosixFilePermission> perms = new HashSet<>();
      perms.add(PosixFilePermission.OWNER_READ);
      perms.add(PosixFilePermission.OWNER_WRITE);
      perms.add(PosixFilePermission.OWNER_EXECUTE);
      Files.setPosixFilePermissions(dir, perms);
    } catch (Exception ignore) {
      // POSIX permissions are unavailable on some file systems.
    }
  }
}
