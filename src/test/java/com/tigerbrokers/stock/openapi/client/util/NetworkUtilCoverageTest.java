package com.tigerbrokers.stock.openapi.client.util;

import java.net.InetAddress;
import org.junit.Test;

public class NetworkUtilCoverageTest {

  @Test
  public void testGetDeviceId() {
    NetworkUtil.getDeviceId();
  }

  @Test
  public void testGetMacFromInetAddress_loopback() throws Exception {
    InetAddress addr = InetAddress.getLoopbackAddress();
    NetworkUtil.getMacFromInetAddress(addr);
  }

  @Test
  public void testGetSupportedProtocolsSet_emptyInput() {
    String[] serverProtocols = new String[]{"TLSv1.2", "TLSv1.3"};
    try {
      NetworkUtil.getSupportedProtocolsSet(serverProtocols, null);
    } catch (Exception e) {
      // may throw if SslProvider is null
    }
  }

  @Test
  public void testGetSupportedProtocolsSet_nullInput() {
    try {
      NetworkUtil.getSupportedProtocolsSet(null, null);
    } catch (Exception e) {
      // may throw for null input
    }
  }
}
