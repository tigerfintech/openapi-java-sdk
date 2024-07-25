package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.struct.enums.BizType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Env;
import com.tigerbrokers.stock.openapi.client.struct.enums.License;
import com.tigerbrokers.stock.openapi.client.struct.enums.Protocol;
import io.netty.handler.ssl.OpenSsl;
import io.netty.handler.ssl.SslProvider;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * description: Created by ltc on 2021-04-08
 */
public class NetworkUtil {

  private static final String GET_DEVICE_ERROR = "please check network connection";
  private static final int MAC_ARRAY_LENGTH = 6;
  private static final int MAC_LENGTH = MAC_ARRAY_LENGTH * 3 - 1;

  private NetworkUtil() {
  }

  public static String getDeviceId() {
    InetAddress inetAddress = null;
    try {
      inetAddress = getLocalHostLANAddress();
    } catch (Throwable th) {
      ApiLogger.error("getLocalHostLANAddress failed. {}, {}", th.getMessage(), GET_DEVICE_ERROR);
    }
    return getMacFromInetAddress(inetAddress);
  }

  public static String getMacFromInetAddress(InetAddress inetAddress) {
    byte[] mac = null;
    if (inetAddress != null) {
      try {
        mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
      } catch (Throwable th) {
        ApiLogger.info("getHardwareAddress failed. {}", th.getMessage());
      }
    }
    if (mac == null || mac.length != MAC_ARRAY_LENGTH) {
      // try converting ipv6 to mac address
      if (inetAddress != null && inetAddress instanceof Inet6Address) {
        String ip = inetAddress.getHostAddress();
        // 1030::C9B4:FF12:48AA:1A2B
        String[] items = ip.split("\\:");
        StringBuilder builder = new StringBuilder();
        for (String item : items) {
          if (item == null || item.length() == 0 || "0" .equals(item)) {
            continue;
          }
          // ipv6 may ignore the leading 0
          if (item.length() == 3 || item.length() == 1) {
            item = "0" + item;
          }
          if (item.length() == 4) {
            builder.append(item, 0, 2).append(':');
            builder.append(item, 2, 4).append(':');
          } else if (item.length() == 2) {
            builder.append(item, 0, 2).append(':');
          }
        }
        if (builder.length() >= MAC_LENGTH) {
          return builder.substring(0, MAC_LENGTH);
        }
      }

      // try converting ipv4 to mac address
      mac = new byte[MAC_ARRAY_LENGTH];
      if (inetAddress != null && inetAddress instanceof Inet4Address) {
        String ip = inetAddress.getHostAddress();
        String[] items = ip.split("\\.");
        for (int i = 0; i < MAC_ARRAY_LENGTH; i++) {
          mac[i] = (byte) StringUtils.toInt(items[i % items.length], 0);
        }
      } else {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < MAC_ARRAY_LENGTH; i++) {
          mac[i] = (byte) random.nextInt(255);
        }
      }
    }
    StringBuilder deviceId = new StringBuilder();
    for (int i = 0; i < mac.length; i++) {
      if (i != 0) {
        deviceId.append(":");
      }
      String str = Integer.toHexString(mac[i] & 0xff);
      if (str.length() == 1) {
        deviceId.append(0);
      }
      deviceId.append(str);
    }
    return deviceId.toString();
  }

  private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
    try {
      InetAddress candidateAddress = null;
      for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
          networkInterfaces.hasMoreElements(); ) {
        NetworkInterface networkInterface = networkInterfaces.nextElement();
        // exclude virtual network
        if (networkInterface.isLoopback() || networkInterface.isVirtual()
            || networkInterface.isPointToPoint() || !networkInterface.isUp()) {
          continue;
        }
        for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            inetAddresses.hasMoreElements(); ) {
          InetAddress inetAddress = inetAddresses.nextElement();
          if (!inetAddress.isLoopbackAddress()) {
            if (inetAddress.isSiteLocalAddress()) {
              return inetAddress;
            } else if (candidateAddress == null) {
              candidateAddress = inetAddress;
            }
          }
        }
      }
      if (candidateAddress != null) {
        return candidateAddress;
      }
      InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
      if (jdkSuppliedAddress == null) {
        throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
      }
      return jdkSuppliedAddress;
    } catch (Exception e) {
      UnknownHostException unknownHostException = new UnknownHostException(
          "Failed to determine LAN address: " + e);
      unknownHostException.initCause(e);
      throw unknownHostException;
    }
  }

  public static String[] getSupportedProtocolsSet(String[] serverSupportedProtocols, SslProvider sslProvider) {
    if (serverSupportedProtocols == null || serverSupportedProtocols.length == 0) {
      ApiLogger.error("Server Supported protocols (OpenSSL) is empty. serverSupportedProtocols:{}", serverSupportedProtocols);
      return serverSupportedProtocols;
    }
    Set<String> localSupportedProtocols;
    if (sslProvider == SslProvider.JDK) {
      localSupportedProtocols = getJdkSupportedProtocolsSet();
    } else {
      localSupportedProtocols = getOpenSSLSupportedProtocolsSet();
    }
    if (localSupportedProtocols.isEmpty()) {
      ApiLogger.error("Local Supported protocols ({}): {}, is empty", sslProvider, localSupportedProtocols);
      return null;
    }

    ApiLogger.info("Local Supported protocols ({}): {}", sslProvider, localSupportedProtocols);
    Set<String> supportedProtocolsSet = new LinkedHashSet<>();
    for (String protocol : serverSupportedProtocols) {
      if (localSupportedProtocols.contains(protocol)) {
        supportedProtocolsSet.add(protocol);
      }
    }

    if (supportedProtocolsSet.isEmpty()) {
      String[] supportedProtocols = new String[serverSupportedProtocols.length];
      System.arraycopy(serverSupportedProtocols,0,
          supportedProtocols,0, serverSupportedProtocols.length);
      return supportedProtocols;
    }
    return supportedProtocolsSet.toArray(new String[supportedProtocolsSet.size()]);
  }

  private static Set<String> getJdkSupportedProtocolsSet() {
    SSLSocket socket = null;
    try {
      SSLContext context = SSLContext.getInstance("TLS");
      context.init(null, null, null);
      SSLSocketFactory factory = context.getSocketFactory();
      socket = (SSLSocket) factory.createSocket();
    } catch (Throwable th) {
      ApiLogger.error("getJdkSupportedProtocolsSet exception:{}", th.getMessage(), th);
    }
    if (socket == null || socket.getSupportedProtocols() == null) {
      return Collections.emptySet();
    }
    Set<String> localSupportedProtocols = new LinkedHashSet<>();
    for (String protocol : socket.getSupportedProtocols()) {
      localSupportedProtocols.add(protocol);
    }
    return localSupportedProtocols;
  }

  private static Set<String> getOpenSSLSupportedProtocolsSet() {
    Set<String> localSupportedProtocols = Collections.emptySet();
    try {
      Field supportedProtocolsSetField = OpenSsl.class.getDeclaredField("SUPPORTED_PROTOCOLS_SET");
      if (supportedProtocolsSetField != null) {
        supportedProtocolsSetField.setAccessible(true);
        localSupportedProtocols = (Set<String>) supportedProtocolsSetField.get(OpenSsl.class);
      }
    } catch (Throwable th) {
      ApiLogger.error("getOpenSSLSupportedProtocolsSet exception:{}", th.getMessage(), th);
    }
    return localSupportedProtocols;
  }

  private static String getDefaultPort(ClientConfig clientConfig, Protocol protocol) {
    String port = "";
    if (protocol != Protocol.HTTP) {
      if (clientConfig.getEnv() == Env.PROD) {
        port = clientConfig.isSslSocket ? TigerApiConstants.DEFAULT_PROD_SOCKET_SSL_PORT
            : TigerApiConstants.DEFAULT_PROD_SOCKET_PORT;
      } else {
        port = clientConfig.isSslSocket ? TigerApiConstants.DEFAULT_SANDBOX_SOCKET_SSL_PORT
            : TigerApiConstants.DEFAULT_SANDBOX_SOCKET_PORT;
      }
    }
    return port;
  }

  /**
   * get http server address
   */
  public static String getHttpServerAddress(String originalAddress) {
    return getHttpServerAddress(null, originalAddress).get(BizType.COMMON);
  }

  public static Map<BizType, String> getHttpServerAddress(License license, String originalAddress) {
    return refreshAndGetServerAddress(Protocol.HTTP, license, originalAddress);
  }

  public static String getServerAddress(String originalAddress) {
    return refreshAndGetServerAddress(ClientConfig.DEFAULT_CONFIG.isSslSocket ? Protocol.SECURE_SOCKET : Protocol.WEB_SOCKET,
        null, originalAddress).get(BizType.SOCKET);
  }

  private static Map<BizType, String> refreshAndGetServerAddress(Protocol protocol, License license, String originalAddress) {
    ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
    Env env = clientConfig.getEnv();
    String port = getDefaultPort(clientConfig, protocol);
    String commonUrl = null;
    String domainGardenResponse = null;
    List<Map<String, Object>> domainConfigList = Collections.emptyList();
    try {
      domainGardenResponse = HttpUtils.get(TigerApiConstants.DOMAIN_GARDEN_ADDRESS);
      Map<String, Object> domainConfigMap = JSON.parseObject(domainGardenResponse, Map.class);
      if (domainConfigMap != null && domainConfigMap.get("items") != null) {
        domainConfigList = (List<Map<String, Object>>)domainConfigMap.get("items");
      }
    } catch (Throwable th) {
      ApiLogger.warn("domain garden return:{}, error:{}", domainGardenResponse, th.getMessage());
    }
    // if get domain config data failed and original address is not emtpy, return original address
    if (domainConfigList.isEmpty()) {
      final String addressUrl = StringUtils.isEmpty(originalAddress)
          ? String.format(protocol.getUrlFormat(), getDefaultUrl(env, protocol), port) : originalAddress;
      return new HashMap<BizType, String>() {{ put(protocol == Protocol.HTTP ? BizType.COMMON : BizType.SOCKET, addressUrl);}};
    }

    Map<BizType, String> domainUrlMap = new HashMap<>();
    for (Map<String, Object> configMap : domainConfigList) {
      Object openapiConfig = configMap.get(env.getConfigFieldName());
      if (openapiConfig != null && openapiConfig instanceof Map) {
        Map<String, Object> dataMap = (Map<String, Object>) openapiConfig;
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
          if (Protocol.WEB_SOCKET.getPortFieldName().equals(entry.getKey())
              || Protocol.SECURE_SOCKET.getPortFieldName().equals(entry.getKey())) {
            if (protocol.getPortFieldName().equals(entry.getKey())) {
              port = entry.getValue().toString();
            }
            continue;
          }
          String domainUrl = entry.getValue().toString().replace("https://", "");
          BizType bizType = convertBizType(license, entry.getKey());
          if (bizType != null) {
            domainUrlMap.put(bizType, String.format(protocol.getUrlFormat(), domainUrl, port));
          }
          commonUrl = (commonUrl == null && bizType == BizType.COMMON) ? domainUrl : commonUrl;
        }
      }
    }
    if (commonUrl == null) {
      commonUrl = getDefaultUrl(env, protocol);
      domainUrlMap.put(BizType.COMMON, String.format(protocol.getUrlFormat(), commonUrl, port));
    }
    if (protocol != Protocol.HTTP) {
      domainUrlMap.put(BizType.SOCKET, String.format(protocol.getUrlFormat(), commonUrl, port));
    }
    return domainUrlMap;
  }

  private static String getDefaultUrl(Env env, Protocol protocol) {
    if (env == null) {
      return TigerApiConstants.DEFAULT_PROD_DOMAIN_URL;
    }
    switch (env) {
      case SANDBOX:
        return TigerApiConstants.DEFAULT_SANDBOX_DOMAIN_URL;
      case TEST:
        return Protocol.HTTP == protocol ? TigerApiConstants.DEFAULT_TEST_DOMAIN_URL : TigerApiConstants.API_TEST_SOCKET_DOMAIN_URL;
      default:
        return TigerApiConstants.DEFAULT_PROD_DOMAIN_URL;
    }
  }

  private static BizType convertBizType(License license, String key) {
    if (BizType.COMMON.name().equals(key)) {
      return BizType.COMMON;
    } else if (license != null) {
      if (license.name().equals(key)) {
        return BizType.TRADE;
      } else if (key.equals(license.name() + "-" + BizType.QUOTE.name())) {
        return BizType.QUOTE;
      } else if (key.equals(license.name() + "-" + BizType.PAPER.name())) {
        return BizType.PAPER;
      }
    }
    return null;
  }
}
