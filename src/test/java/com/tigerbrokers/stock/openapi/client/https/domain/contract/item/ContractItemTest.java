package com.tigerbrokers.stock.openapi.client.https.domain.contract.item;

import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureContractItem;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.testsupport.PojoTester;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

public class ContractItemTest {

  @Test
  public void pojoSurface() {
    PojoTester.testPackage(getClass().getPackage().getName());
  }

  @Test
  public void convert_fromFutureContract_populatesFields() {
    FutureContractItem future = new FutureContractItem();
    future.setContractCode("CL");
    future.setType("FUT");
    future.setIbCode("CL");
    future.setName("Crude Oil");
    future.setContractMonth("202401");
    future.setExchangeCode("NYMEX");
    future.setMultiplier(new BigDecimal("1000"));
    future.setMinTick(new BigDecimal("0.01"));
    future.setLastTradingDate("2024-01-19");
    future.setFirstNoticeDate("2024-01-19");
    future.setLastBiddingCloseTime(0L);
    future.setCurrency("USD");
    future.setContinuous(true);
    future.setTrade(true);

    ContractItem item = ContractItem.convert(future);
    Assert.assertEquals(SecType.FUT.name(), item.getSecType());
    Assert.assertEquals("CL", item.getSymbol());
    Assert.assertEquals("Crude Oil", item.getName());
    Assert.assertEquals("NYMEX", item.getExchange());
    Assert.assertEquals("USD", item.getCurrency());
  }

  @Test
  public void convert_fromNullFields_returnsNotNull() {
    FutureContractItem future = new FutureContractItem();
    ContractItem item = ContractItem.convert(future);
    Assert.assertNotNull(item);
  }
}
