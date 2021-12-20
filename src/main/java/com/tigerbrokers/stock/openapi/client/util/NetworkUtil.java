package com.tigerbrokers.stock.openapi.client.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Random;

/**
 * description: Created by ltc on 2021-04-08
 */
public class NetworkUtil {

  private static final String GET_DEVICE_ERROR = "Please check if the network connection is disconnected";
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
}
