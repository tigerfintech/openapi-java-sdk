package com.tigerbrokers.stock.openapi.client.util;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.struct.enums.AccountType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class AccountUtilTest2 {
  @Test
  public void testIsOmnibusAccount_null() { Assert.assertFalse(AccountUtil.isOmnibusAccount(null)); }
  @Test
  public void testIsOmnibusAccount_empty() { Assert.assertFalse(AccountUtil.isOmnibusAccount("")); }
  @Test
  public void testIsVirtualAccount_null() { Assert.assertFalse(AccountUtil.isVirtualAccount(null)); }
  @Test
  public void testIsGlobalAccount_F() { Assert.assertTrue(AccountUtil.isGlobalAccount("F1234567")); }
  @Test
  public void testIsGlobalAccount_DF() { Assert.assertTrue(AccountUtil.isGlobalAccount("DF1234567")); }
  @Test
  public void testIsGlobalAccount_null() { Assert.assertFalse(AccountUtil.isGlobalAccount(null)); }
  @Test
  public void testGetAccountType_global() { Assert.assertEquals(AccountType.GLOBAL, AccountUtil.getAccountType("U1234567")); Assert.assertEquals(AccountType.GLOBAL, AccountUtil.getAccountType("F1234567")); }
  @Test
  public void testGetAccountType_standard() { Assert.assertEquals(AccountType.STANDARD, AccountUtil.getAccountType("600021133")); }
  @Test
  public void testGetAccountType_paper() { Assert.assertEquals(AccountType.PAPER, AccountUtil.getAccountType("12345678901234567")); }
  @Test
  public void testParseAccount_null() { Assert.assertNull(AccountUtil.parseAccount(null)); }
  @Test
  public void testParseAccount_tigerHttpRequest() {
    TigerHttpRequest request = mock(TigerHttpRequest.class);
    when(request.getBizContent()).thenReturn("{\"account\":\"123456789\"}");
    Assert.assertEquals("123456789", AccountUtil.parseAccount(request));
  }
}
