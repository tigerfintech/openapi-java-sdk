package com.tigerbrokers.stock.openapi.client.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author ltc
 * @description
 * @date 2021-04-08
 */
public class NetworkUtil {

  /**
   * 获取主机mac地址
   */
  public static String getLocalMac() {
    try {
      InetAddress ia = InetAddress.getLocalHost();
      byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
      if (mac == null) {
        ApiLogger.error("Please check if the network connection is disconnected");
        return null;
      }
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < mac.length; i++) {
        if (i != 0) {
          sb.append("-");
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
    }
    return null;
  }
}
