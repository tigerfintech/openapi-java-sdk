package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.DEFAULT_DOMAIN_KEY;

/**
 * description: Created by ltc on 2021-04-08
 */
public class NetworkUtil {

  private static final String GET_DEVICE_ERROR = "Please check if the network connection is disconnected";
  private static final int MAC_ARRAY_LENGTH = 6;
  private static final int MAC_LENGTH = MAC_ARRAY_LENGTH * 3 - 1;
  private static final Set<String> ONLINE_DOMAIN_SET = new HashSet<String>(){
    {
      add(TigerApiConstants.API_ONLINE_DOMAIN_URL);
      add(TigerApiConstants.API_ONLINE_DOMAIN_URL_OLD);
    }
  };

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
          // ipv6可能忽略前导的0
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

  public static String[] getOpenSslSupportedProtocolsSet(String[] serverSupportedProtocols,
      SslProvider sslProvider) {
    if (serverSupportedProtocols == null || serverSupportedProtocols.length == 0) {
      ApiLogger.error("Server Supported protocols (OpenSSL) is empty. serverSupportedProtocols:{}", serverSupportedProtocols);
      return serverSupportedProtocols;
    }

    Set<String> supportedProtocolsSet = Collections.emptySet();
    if (sslProvider == SslProvider.OPENSSL) {
      Set<String> localSupportedProtocols = Collections.emptySet();
      try {
        Field supportedProtocolsSetField = OpenSsl.class.getDeclaredField("SUPPORTED_PROTOCOLS_SET");
        if (supportedProtocolsSetField != null) {
          supportedProtocolsSetField.setAccessible(true);
          localSupportedProtocols = (Set<String>) supportedProtocolsSetField.get(OpenSsl.class);
        }
      } catch (Throwable th) {
        ApiLogger.error("getOpenSslSupportedProtocolsSet exception:{}", th.getMessage(), th);
      }
      if (localSupportedProtocols.isEmpty()) {
        ApiLogger.error("Local Supported protocols (OpenSSL): {}, is empty", localSupportedProtocols);
        return null;
      }

      ApiLogger.info("Local Supported protocols (OpenSSL): {}", localSupportedProtocols);
      supportedProtocolsSet = new LinkedHashSet<>();
      for (String protocol : serverSupportedProtocols) {
        if (localSupportedProtocols.contains(protocol)) {
          supportedProtocolsSet.add(protocol);
        }
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

  public static boolean isOnlineEnv(String serverUrl) {
    for (String domainName : ONLINE_DOMAIN_SET) {
      if (serverUrl.contains(domainName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * get http server address
   */
  public static String getHttpServerAddress() {
    return refreshAndGetServerAddress(Protocol.HTTP);
  }

  public static String getServerAddress() {
    return refreshAndGetServerAddress(ClientConfig.DEFAULT_CONFIG.getSubscribeProtocol());
  }

  private static String getDefaultPort(ClientConfig clientConfig, Protocol protocol) {
    String port = "";
    if (protocol != Protocol.HTTP) {
      if (clientConfig.getEnv() == Env.PROD) {
        port = protocol == Protocol.STOMP ? TigerApiConstants.DEFAULT_PROD_STOMP_PORT
            : TigerApiConstants.DEFAULT_PROD_SOCKET_PORT;
      } else {
        port = protocol == Protocol.STOMP ? TigerApiConstants.DEFAULT_SANDBOX_STOMP_PORT
            : TigerApiConstants.DEFAULT_SANDBOX_SOCKET_PORT;
      }
    }
    return port;
  }

  private static String refreshAndGetServerAddress(Protocol protocol) {
    ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
    Env env = clientConfig.getEnv();
    String domainUrl = (env == Env.PROD ? TigerApiConstants.DEFAULT_PROD_DOMAIN_URL
        : TigerApiConstants.DEFAULT_SANDBOX_DOMAIN_URL);
    String port = getDefaultPort(clientConfig, protocol);

    String data = null;
    try {
      data = HttpUtils.get(TigerApiConstants.DOMAIN_GARDEN_ADDRESS);
    } catch (Throwable th) {
      // ignore
    }
    if (StringUtils.isEmpty(data)) {
      return String.format(protocol.getUrlFormat(), domainUrl, port);
    }
    Map<String, Object> returnMap = null;
    try {
      returnMap = JSON.parseObject(data, Map.class);
    } catch (Exception e) {
      ApiLogger.error("domain config response error, data:{}", data);
    }
    if (returnMap == null || returnMap.get("items") == null) {
      return String.format(protocol.getUrlFormat(), domainUrl, port);
    }
    License license = clientConfig.getLicense();
    List<Map<String, Object>> list = (List<Map<String, Object>>)returnMap.get("items");
    boolean match = false;
    for (Map<String, Object> configMap : list) {
      Map<String, Object> dataMap = null;
      Object openapiConfig = configMap.get(env.getConfigFieldName());
      if (openapiConfig instanceof Map) {
        dataMap = (Map<String, Object>)openapiConfig;
      }
      if (dataMap == null) {
        continue;
      }
      for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
        if (Protocol.STOMP.getPortFieldName().equals(entry.getKey())
            || Protocol.SOCKET.getPortFieldName().equals(entry.getKey())) {
          if (protocol.getPortFieldName().equals(entry.getKey())) {
            port = entry.getValue().toString();
          }
        } else {
          String value = entry.getValue().toString();
          if (value.startsWith("https://")) {
            value = value.substring("https://".length());
          }
          if (env == Env.PROD) {
            ONLINE_DOMAIN_SET.add(value);
          }
          if (license.name().equals(entry.getKey())) {
            domainUrl = value;
            match = true;
          }
          if (DEFAULT_DOMAIN_KEY.equals(entry.getKey()) && !match) {
            domainUrl = value;
          }
        }
      }
    }
    return  String.format(protocol.getUrlFormat(), domainUrl, port);
  }
}
