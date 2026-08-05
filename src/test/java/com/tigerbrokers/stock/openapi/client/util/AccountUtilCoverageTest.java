package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.enums.AccountType;
import org.junit.Assert;
import org.junit.Test;

public class AccountUtilCoverageTest {

  @Test
  public void testIsOmnibusAccount() {
    Assert.assertTrue(AccountUtil.isOmnibusAccount("12345678"));
    Assert.assertTrue(AccountUtil.isOmnibusAccount("12345"));
    Assert.assertFalse(AccountUtil.isOmnibusAccount(null));
    Assert.assertFalse(AccountUtil.isOmnibusAccount(""));
    Assert.assertFalse(AccountUtil.isOmnibusAccount("U1234567")); // starts with U
    Assert.assertFalse(AccountUtil.isOmnibusAccount("12345678901234567")); // 17 chars = paper
  }

  @Test
  public void testIsVirtualAccount() {
    Assert.assertTrue(AccountUtil.isVirtualAccount("12345678901234567")); // 17 chars
    Assert.assertFalse(AccountUtil.isVirtualAccount(null));
    Assert.assertFalse(AccountUtil.isVirtualAccount(""));
    Assert.assertFalse(AccountUtil.isVirtualAccount("12345678")); // too short
    Assert.assertFalse(AccountUtil.isVirtualAccount("123456789012345678")); // too long
  }

  @Test
  public void testIsGlobalAccount() {
    Assert.assertTrue(AccountUtil.isGlobalAccount("U1234567"));
    Assert.assertTrue(AccountUtil.isGlobalAccount("DU1234567"));
    Assert.assertTrue(AccountUtil.isGlobalAccount("F1234567"));
    Assert.assertTrue(AccountUtil.isGlobalAccount("DF1234567"));
    Assert.assertFalse(AccountUtil.isGlobalAccount(null));
    Assert.assertFalse(AccountUtil.isGlobalAccount(""));
    Assert.assertFalse(AccountUtil.isGlobalAccount("12345678"));
    Assert.assertFalse(AccountUtil.isGlobalAccount("A1234567"));
  }

  @Test
  public void testGetAccountType() {
    Assert.assertEquals(AccountType.GLOBAL, AccountUtil.getAccountType("U1234567"));
    Assert.assertEquals(AccountType.GLOBAL, AccountUtil.getAccountType("DU1234567"));
    Assert.assertEquals(AccountType.GLOBAL, AccountUtil.getAccountType("F1234567"));
    Assert.assertEquals(AccountType.GLOBAL, AccountUtil.getAccountType("DF1234567"));
    Assert.assertEquals(AccountType.STANDARD, AccountUtil.getAccountType("12345678"));
    Assert.assertEquals(AccountType.PAPER, AccountUtil.getAccountType("12345678901234567"));
    Assert.assertEquals(AccountType.PAPER, AccountUtil.getAccountType(null));
    Assert.assertEquals(AccountType.PAPER, AccountUtil.getAccountType(""));
    Assert.assertEquals(AccountType.PAPER, AccountUtil.getAccountType("ABCDEF"));
  }

  @Test
  public void testParseAccount_nullRequest() {
    Assert.assertNull(AccountUtil.parseAccount(null));
  }
}
