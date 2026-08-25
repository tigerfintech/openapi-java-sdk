package com.tigerbrokers.stock.openapi.client.https.domain.contract.model;

import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ContractModelTest {

  @Test
  public void testDefaultConstructor() {
    ContractModel m = new ContractModel();
    Assert.assertNull(m.getSymbol());
    Assert.assertNull(m.getAccount());
  }

  @Test
  public void testSymbolConstructor() {
    ContractModel m = new ContractModel("AAPL");
    Assert.assertEquals("AAPL", m.getSymbol());
    Assert.assertEquals(SecType.STK.name(), m.getSecType());
    Assert.assertNull(m.getAccount());
  }

  @Test
  public void testSymbolSecTypeConstructor() {
    ContractModel m = new ContractModel("AAPL", SecType.OPT.name());
    Assert.assertEquals("AAPL", m.getSymbol());
    Assert.assertEquals(SecType.OPT.name(), m.getSecType());
  }

  @Test
  public void testFullConstructor() {
    ContractModel m = new ContractModel("AAPL", SecType.OPT.name(), "USD", "20240119", 150.0, "CALL");
    Assert.assertEquals("AAPL", m.getSymbol());
    Assert.assertEquals(SecType.OPT.name(), m.getSecType());
    Assert.assertEquals("USD", m.getCurrency());
    Assert.assertEquals("20240119", m.getExpiry());
    Assert.assertEquals(150.0, m.getStrike(), 0.001);
    Assert.assertEquals("CALL", m.getRight());
  }

  @Test
  public void testGetStockModel() {
    ContractModel m = ContractModel.getStockModel("AAPL");
    Assert.assertEquals("AAPL", m.getSymbol());
    Assert.assertEquals(SecType.STK.name(), m.getSecType());
  }

  @Test
  public void testGetOptionModel() {
    ContractModel m = ContractModel.getOptionModel("AAPL", "20240119", 150.0, "CALL");
    Assert.assertEquals("AAPL", m.getSymbol());
    Assert.assertEquals(SecType.OPT.name(), m.getSecType());
  }

  @Test
  public void testGetWarrantModel() {
    ContractModel m = ContractModel.getWarrantModel("12345", "20240119", 150.0, "CALL");
    Assert.assertEquals(SecType.WAR.name(), m.getSecType());
  }

  @Test
  public void testGetCbbcModel() {
    ContractModel m = ContractModel.getCbbcModel("12345", "20240119", 150.0, "CALL");
    Assert.assertEquals(SecType.IOPT.name(), m.getSecType());
  }

  @Test
  public void testGetFutureModel() {
    ContractModel m = ContractModel.getFutureModel("CL");
    Assert.assertEquals("CL", m.getSymbol());
    Assert.assertEquals(SecType.FUT.name(), m.getSecType());
  }

  @Test
  public void testGetCcModel() {
    ContractModel m = ContractModel.getCcModel("EURUSD");
    Assert.assertEquals("EURUSD", m.getSymbol());
    Assert.assertEquals(SecType.CC.name(), m.getSecType());
  }

  @Test
  public void testContractsModel() {
    List<String> symbols = Arrays.asList("AAPL", "GOOG");
    ContractsModel m = new ContractsModel(symbols);
    Assert.assertEquals(symbols, m.getSymbols());
    Assert.assertEquals(SecType.STK.name(), m.getSecType());

    ContractsModel m2 = new ContractsModel(symbols, SecType.OPT.name(), "20240119", 150.0, "CALL");
    Assert.assertEquals(SecType.OPT.name(), m2.getSecType());
    Assert.assertEquals("20240119", m2.getExpiry());
  }

  @Test
  public void testBaseContractModel() {
    BaseContractModel m = new BaseContractModel();
    m.setAccount("acct1");
    m.setSecType("STK");
    m.setCurrency("USD");
    m.setExpiry("20240119");
    m.setStrike(150.0);
    m.setRight("CALL");
    m.setExchange("NYSE");
    m.setSecretKey("secret");
    Assert.assertEquals("acct1", m.getAccount());
    Assert.assertEquals("STK", m.getSecType());
    Assert.assertEquals("USD", m.getCurrency());
    Assert.assertEquals("20240119", m.getExpiry());
    Assert.assertEquals("NYSE", m.getExchange());
    Assert.assertEquals("secret", m.getSecretKey());
  }
}
