package com.tigerbrokers.stock.openapi.client.https.domain.contract.item;

import com.tigerbrokers.stock.openapi.client.https.domain.fund.item.FundContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureContractItem;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

public class ContractItemCoverageTest {

  @Test
  public void testConvertFromFundContract() {
    FundContractItem fund = new FundContractItem();
    fund.setSymbol("12345");
    fund.setName("Test Fund");
    fund.setCurrency("USD");
    ContractItem item = ContractItem.convert(fund);
    Assert.assertNotNull(item);
    Assert.assertEquals("12345", item.getSymbol());
  }

  @Test
  public void testBuildStockContract() {
    ContractItem item = ContractItem.buildStockContract("AAPL", "USD");
    Assert.assertEquals("AAPL", item.getSymbol());
    Assert.assertEquals(SecType.STK.name(), item.getSecType());
  }

  @Test
  public void testBuildOptionContract_withIdentifier() throws Exception {
    String identifier = "AAPL  240119C00150000";
    ContractItem item = ContractItem.buildOptionContract(identifier);
    Assert.assertNotNull(item);
    Assert.assertEquals(SecType.OPT.name(), item.getSecType());
    // Verify the OCC identifier is parsed correctly: symbol / expiry / right / strike.
    Assert.assertEquals("AAPL", item.getSymbol());
    Assert.assertEquals("2024-01-19", item.getExpiry());
    Assert.assertEquals("CALL", item.getRight());
    Assert.assertEquals(150.0, item.getStrike(), 0.001);
  }

  @Test
  public void testBuildOptionContract_withParams() {
    ContractItem item = ContractItem.buildOptionContract("AAPL", "20240119", 150.0, "CALL");
    Assert.assertEquals("AAPL", item.getSymbol());
    Assert.assertEquals(SecType.OPT.name(), item.getSecType());
    Assert.assertEquals("20240119", item.getExpiry());
  }

  @Test
  public void testBuildWarrantContract() {
    ContractItem item = ContractItem.buildWarrantContract("12345", "20240119", 150.0, "CALL");
    Assert.assertEquals(SecType.WAR.name(), item.getSecType());
  }

  @Test
  public void testBuildCbbcContract() {
    ContractItem item = ContractItem.buildCbbcContract("12345", "20240119", 150.0, "CALL");
    Assert.assertEquals(SecType.IOPT.name(), item.getSecType());
  }

  @Test
  public void testBuildFutureContract() {
    ContractItem item = ContractItem.buildFutureContract("CL", "USD");
    Assert.assertEquals("CL", item.getSymbol());
    Assert.assertEquals(SecType.FUT.name(), item.getSecType());
  }

  @Test
  public void testBuildFutureContract_withExchange() {
    ContractItem item = ContractItem.buildFutureContract("CL", "USD", "NYMEX", "202401", 1000.0);
    Assert.assertEquals("CL", item.getSymbol());
    Assert.assertEquals(SecType.FUT.name(), item.getSecType());
  }
}
