package com.tigerbrokers.stock.openapi.client.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author ltc
 * @description
 * @date 2021-04-08
 */
public class NetworkUtil {

  private static final String NETWORK_DISCONNECTED = "Please check if the network connection is disconnected";

  private NetworkUtil() {
  }

  public static String getDeviceId() {
    try {
      InetAddress ia = getLocalHostLANAddress();
      byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
      if (mac == null) {
        ApiLogger.error(NETWORK_DISCONNECTED);
        throw new RuntimeException(NETWORK_DISCONNECTED);
      }
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < mac.length; i++) {
        if (i != 0) {
          sb.append(":");
        }
        int temp = mac[i] & 0xff;
        String str = Integer.toHexString(temp);
        if (str.length() == 1) {
          sb.append(0);
        }
        sb.append(str);
      }
      return sb.toString();
    } catch (SocketException | UnknownHostException e) {
      ApiLogger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
    try {
      InetAddress candidateAddress = null;
      for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
        NetworkInterface networkInterface = networkInterfaces.nextElement();
        for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
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
