package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.CHARSET_UTF8;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.CONFIG_FILENAME;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.SEPARATOR;
import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.TOKEN_FILENAME;

/**
 * @author bean
 * @date 2023/2/10 2:45 PM
 */
public class FileUtil {

  private static final String PPRVATE_KEY_PREFIX = "KEY-----";
  private static final String PRIVATE_KEY_SUFFIX = "-----END";
  private static final String COMMENT_PREFIX = "#";
  private static final char EQUAL_CHAR = '=';
  private static final String UTF8_BOM="\uFEFF";

  private static final String CONFIG_FILE_PRIVATE_KEY = "private_key_pk8";
  private static final String CONFIG_FILE_TIGER_ID = "tiger_id";
  private static final String CONFIG_FILE_ACCOUNT = "account";
  private static final String CONFIG_FILE_LICENSE = "license";
  private static final String CONFIG_FILE_ENV = "env";
  private static final String TOKEN_FILE_TOKEN = "token";

  private static final Set<String> configFileKeys = new HashSet<>();
  static {
    configFileKeys.add(CONFIG_FILE_PRIVATE_KEY);
    configFileKeys.add(CONFIG_FILE_TIGER_ID);
    configFileKeys.add(CONFIG_FILE_ACCOUNT);
    configFileKeys.add(CONFIG_FILE_LICENSE);
    configFileKeys.add(CONFIG_FILE_ENV);
  }

  private FileUtil() {}

  private static boolean checkFile(String dir, String fileName, boolean writable) {
    if (StringUtils.isEmpty(dir)) {
      return false;
    }
    dir = dir.trim();
    Path configPath = Paths.get(dir);
    if (Files.notExists(configPath) || !Files.isDirectory(configPath)) {
      ApiLogger.info("config file directory[{}] is missing, ingore", dir);
      return false;
    }
    Path configFilePath = Paths.get(dir, fileName);
    if (Files.notExists(configFilePath)) {
      ApiLogger.info("config file[{}] is missing, ingore", configFilePath.toAbsolutePath().toString());
      return false;
    }
    if (!writable && !Files.isReadable(configFilePath)) {
      ApiLogger.warn("config file[{}] is unreadable", configFilePath.toAbsolutePath().toString());
      return false;
    }
    if (writable && !Files.isWritable(configFilePath)) {
      ApiLogger.warn("config file[{}] is unwritable", configFilePath.toAbsolutePath().toString());
      return false;
    }
    return true;
  }

  public static void loadConfigFile(ClientConfig clientConfig) {
    if (!checkFile(clientConfig.configFilePath, CONFIG_FILENAME, false)) {
      return;
    }

    Path configFilePath = Paths.get(clientConfig.configFilePath.trim(), CONFIG_FILENAME);
    Map<String, String> dataMap = readPropertiesFile(configFilePath, configFileKeys);

    for (Map.Entry<String, String> entry: dataMap.entrySet()) {
      switch (entry.getKey()) {
        case CONFIG_FILE_PRIVATE_KEY:
          clientConfig.privateKey = entry.getValue();
          break;
        case CONFIG_FILE_TIGER_ID:
          clientConfig.tigerId = entry.getValue();
          break;
        case CONFIG_FILE_ACCOUNT:
          clientConfig.defaultAccount = entry.getValue();
          break;
        case CONFIG_FILE_LICENSE:
          License license = License.getLicense(entry.getValue());
          if (null != license) {
            clientConfig.license = license;
          }
          break;
        case CONFIG_FILE_ENV:
          Env env = Env.getEnv(entry.getValue());
          if (null != env) {
            clientConfig.setEnv(env);
          }
          break;
        default:
          break;
      }
    }
  }

  public static boolean loadTokenFile(ClientConfig clientConfig) {
    if (!checkFile(clientConfig.configFilePath, TOKEN_FILENAME, false)) {
      return false;
    }

    Path tokenFilePath = Paths.get(clientConfig.configFilePath.trim(), TOKEN_FILENAME);
    Map<String, String> dataMap = readPropertiesFile(tokenFilePath, null);
    String token = dataMap.get(TOKEN_FILE_TOKEN);
    if (StringUtils.isEmpty(token)) {
      return false;
    }
    clientConfig.token = token;
    return true;
  }

  public static boolean updateTokenFile(ClientConfig clientConfig, String token) {
    if (StringUtils.isEmpty(token)) {
      return false;
    }
    if (!checkFile(clientConfig.configFilePath, TOKEN_FILENAME, true)) {
      return false;
    }

    Path tokenFilePath = Paths.get(clientConfig.configFilePath.trim(), TOKEN_FILENAME);
    try (FileWriter writer = new FileWriter(tokenFilePath.toFile())) {
      writer.write((new StringBuilder(TOKEN_FILE_TOKEN)).append(EQUAL_CHAR).append(token).toString());
      return true;
    } catch (IOException e) {
      ApiLogger.error("write file fail:" + tokenFilePath.toAbsolutePath(), e);
    }
    return false;
  }

  public static Map<String, String> readPropertiesFile(Path configFilePath,
      Set<String> includeKeys) {
    Map<String, String> dataMap = new HashMap<>();
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(configFilePath.toFile()))) {
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        if (line.startsWith(UTF8_BOM)) {
          line = line.substring(1);
        }
        line = line.trim();
        if (line.isEmpty() || line.startsWith(COMMENT_PREFIX)) {
          continue;
        }
        int idx = line.indexOf(EQUAL_CHAR);
        if (idx <= 0 || idx == line.length() - 1) {
          continue;
        }
        String fieldname = line.substring(0, idx).trim();
        if (includeKeys == null || includeKeys.size() == 0
            || includeKeys.contains(fieldname)) {
          dataMap.put(fieldname, line.substring(idx + 1).trim());
        }
      }
    } catch (IOException e) {
      ApiLogger.error("read file fail:" + configFilePath.toAbsolutePath(), e);
    }
    return dataMap;
  }

  /**
   * read private key from file(Remove first and last lines and line breaks)
   *
   * @param privateKeyFile absolute path
   * @return privateKey String
   */
  public static String readPrivateKey(String privateKeyFile) {
    String content = "";
    File file = new File(privateKeyFile);
    try (FileInputStream in = new FileInputStream(file)) {
      int size = in.available();
      byte[] buffer = new byte[size];
      in.read(buffer);
      content = processPrivateKey(new String(buffer, CHARSET_UTF8));
    } catch (IOException e) {
      ApiLogger.error("read file fail:" + privateKeyFile, e);
    }
    return content;
  }

  public static String processPrivateKey(String content) {
    int start = 0;
    int startIdx = content.indexOf(PPRVATE_KEY_PREFIX);
    if (startIdx > 0) {
      start = startIdx + PPRVATE_KEY_PREFIX.length();
    }
    int end = content.length();
    int endIndex = content.indexOf(PRIVATE_KEY_SUFFIX);
    if (endIndex > 0) {
      end = endIndex;
    }
    return trim(content, start, end);
  }

  private static String trim(String content, int start, int end) {
    StringBuilder builder = new StringBuilder();
    for (int i = start; i < end; i++) {
      char ch = content.charAt(i);
      if (ch == 10 || ch == 13 || ch == 32) {
        continue;
      }
      builder.append(ch);
    }
    return builder.toString();
  }

  public static long tryGetCreateTime(String token) {
    long tokenCreateTime = 0;
    if (!StringUtils.isEmpty(token)) {
      try {
        tokenCreateTime = getCreateTime(token);
      } catch (Throwable th) {
        // ignore
      }
    }
    return tokenCreateTime;
  }

  public static long getCreateTime(String token) {
    String text = new String(Base64.getDecoder().decode(
        token.getBytes(CHARSET_UTF8)), CHARSET_UTF8);
    int idx = text.indexOf(SEPARATOR);
    if (idx > 0) {
      return Long.parseLong(text.substring(0, idx));
    }
    return 0;
  }

  public static long getExpiredTime(String token) {
    String text = new String(Base64.getDecoder().decode(
        token.getBytes(CHARSET_UTF8)), CHARSET_UTF8);
    int idx = text.indexOf(SEPARATOR);
    if (idx > 0) {
      return Long.parseLong(text.substring(idx + 1, idx + 14));
    }
    return 0;
  }
}
