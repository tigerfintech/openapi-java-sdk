package com.tigerbrokers.stock.openapi.client.util;

import org.junit.Assert;
import org.junit.Test;

public class AccountUtilTest {

  @Test
  public void virtualAccount_17digits() {
    Assert.assertTrue(AccountUtil.isVirtualAccount("12345678901234567"));
  }

  @Test
  public void virtualAccount_short() {
    Assert.assertFalse(AccountUtil.isVirtualAccount("600021133"));
  }

  @Test
  public void virtualAccount_null() {
    Assert.assertFalse(AccountUtil.isVirtualAccount(null));
  }

  @Test
  public void virtualAccount_empty() {
    Assert.assertFalse(AccountUtil.isVirtualAccount(""));
  }

  @Test
  public void omnibusAccount_short_numeric() {
    // numeric + length < 17 -> omnibus
    Assert.assertTrue(AccountUtil.isOmnibusAccount("600021133765"));
  }

  @Test
  public void omnibusAccount_paper() {
    // 17-digit paper account is NOT omnibus
    Assert.assertFalse(AccountUtil.isOmnibusAccount("12345678901234567"));
  }

  @Test
  public void globalAccount_U() {
    Assert.assertTrue(AccountUtil.isGlobalAccount("U1234567"));
  }

  @Test
  public void globalAccount_DU() {
    Assert.assertTrue(AccountUtil.isGlobalAccount("DU1234567"));
  }

  @Test
  public void globalAccount_numeric() {
    Assert.assertFalse(AccountUtil.isGlobalAccount("600021133"));
  }
}
