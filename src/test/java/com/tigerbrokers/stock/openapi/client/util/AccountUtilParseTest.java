package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import org.junit.Assert;
import org.junit.Test;

public class AccountUtilParseTest {

  @Test
  public void testParseAccount_nullRequest() {
    Assert.assertNull(AccountUtil.parseAccount(null));
  }

  @Test
  public void testParseAccount_tigerHttpRequest() {
    TigerHttpRequest req = new TigerHttpRequest(MethodName.USER_LICENSE);
    req.setBizContent("{\"account\":\"test123\"}");
    String account = AccountUtil.parseAccount(req);
    Assert.assertEquals("test123", account);
  }

  @Test
  public void testParseAccount_tigerHttpRequest_noAccount() {
    TigerHttpRequest req = new TigerHttpRequest(MethodName.USER_LICENSE);
    req.setBizContent("{\"license\":0}");
    String account = AccountUtil.parseAccount(req);
    Assert.assertNull(account);
  }
}
