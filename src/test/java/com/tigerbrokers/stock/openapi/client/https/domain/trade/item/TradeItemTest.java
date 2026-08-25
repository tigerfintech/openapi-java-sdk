package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.tigerbrokers.stock.openapi.client.testsupport.PojoTester;
import org.junit.Test;

public class TradeItemTest {
  @Test
  public void pojoSurface() {
    PojoTester.testPackage(getClass().getPackage().getName());
  }
}
