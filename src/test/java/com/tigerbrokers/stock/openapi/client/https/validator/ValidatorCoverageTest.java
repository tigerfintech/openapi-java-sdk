package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractsModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteDepthModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteMarketModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteStockTradeModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteSymbolModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteStockBrokerModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionExpirationModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialDailyModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.CorporateActionModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialReportModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContractByConCodeModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureExchangeModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureKlineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureRealTimeQuoteModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureDepthModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTickModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorCoverageTest {

  private void assertReject(ApiModel model) {
    try {
      ValidatorManager.getInstance().validate(model);
      Assert.fail("should reject: " + model.getClass().getSimpleName());
    } catch (TigerApiException e) {
      Assert.assertTrue(e.getErrCode() > 0);
    }
  }

  private void assertPass(ApiModel model) {
    try {
      ValidatorManager.getInstance().validate(model);
    } catch (TigerApiException e) {
      Assert.fail("should pass: " + model.getClass().getSimpleName() + " - " + e.getMessage());
    }
  }

  // === Contract validators ===

  @Test
  public void contractModel_valid_passes() {
    ContractModel m = new ContractModel("AAPL", SecType.STK.name());
    m.setAccount("acct");
    assertPass(m);
  }

  @Test
  public void contractModel_noSymbol_rejects() {
    ContractModel m = new ContractModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK.name());
    assertReject(m);
  }

  @Test
  public void contractModel_noAccount_rejects() {
    ContractModel m = new ContractModel("AAPL");
    m.setSecType(SecType.STK.name());
    assertReject(m);
  }

  @Test
  public void contractModel_option_requiresFields() {
    ContractModel m = new ContractModel("AAPL", SecType.OPT.name());
    m.setAccount("acct");
    assertReject(m);

    m.setExpiry("20240119");
    m.setStrike(150.0);
    m.setRight("CALL");
    assertPass(m);
  }

  @Test
  public void contractsModel_valid_passes() {
    ContractsModel m = new ContractsModel(Arrays.asList("AAPL", "GOOG"));
    m.setAccount("acct");
    m.setSecType(SecType.STK.name());
    assertPass(m);
  }

  @Test
  public void contractsModel_emptySymbols_rejects() {
    ContractsModel m = new ContractsModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK.name());
    assertReject(m);
  }

  // === Quote validators ===

  @Test
  public void quoteStockTradeModel_valid_passes() {
    QuoteStockTradeModel m = new QuoteStockTradeModel();
    m.setSymbols(Arrays.asList("AAPL"));
    assertPass(m);
  }

  @Test
  public void quoteStockTradeModel_empty_rejects() {
    QuoteStockTradeModel m = new QuoteStockTradeModel();
    assertReject(m);
  }

  @Test
  public void quoteDepthModel_valid_passes() {
    QuoteDepthModel m = new QuoteDepthModel(Arrays.asList("AAPL"), "US");
    assertPass(m);
  }

  @Test
  public void quoteDepthModel_noMarket_rejects() {
    QuoteDepthModel m = new QuoteDepthModel(Arrays.asList("AAPL"), "");
    assertReject(m);
  }

  @Test
  public void quoteMarketModel_valid_passes() {
    QuoteMarketModel m = new QuoteMarketModel();
    m.setMarket(Market.US);
    assertPass(m);
  }

  @Test
  public void quoteMarketModel_empty_rejects() {
    QuoteMarketModel m = new QuoteMarketModel();
    assertReject(m);
  }

  @Test
  public void quoteSymbolModel_valid_passes() {
    QuoteSymbolModel m = new QuoteSymbolModel();
    m.setSymbols(Arrays.asList("AAPL"));
    assertPass(m);
  }

  @Test
  public void quoteSymbolModel_empty_noReject() {
    QuoteSymbolModel m = new QuoteSymbolModel();
    assertPass(m);
  }

  @Test
  public void quoteStockBrokerModel_valid_passes() {
    QuoteStockBrokerModel m = new QuoteStockBrokerModel();
    m.setSymbol("AAPL");
    assertPass(m);
  }

  @Test
  public void quoteStockBrokerModel_empty_rejects() {
    QuoteStockBrokerModel m = new QuoteStockBrokerModel();
    assertReject(m);
  }

  // === Option validators ===

  @Test
  public void optionChainModel_stillRejectsWithSymbolAndExpiry() {
    // symbol + expiry alone is not enough — additional required fields (e.g. strike/right)
    // are still missing, so the validator should reject.
    OptionChainModel m = new OptionChainModel();
    m.setSymbol("AAPL");
    m.setExpiry("20240119");
    assertReject(m);
  }

  @Test
  public void optionChainModel_empty_rejects() {
    OptionChainModel m = new OptionChainModel();
    assertReject(m);
  }

  @Test
  public void optionExpirationModel_valid_passes() {
    OptionExpirationModel m = new OptionExpirationModel();
    m.setSymbols(Arrays.asList("AAPL"));
    assertPass(m);
  }

  @Test
  public void optionExpirationModel_empty_rejects() {
    OptionExpirationModel m = new OptionExpirationModel();
    assertReject(m);
  }

  // === Financial validators ===

  @Test
  public void financialDailyModel_needsMoreFields() {
    FinancialDailyModel m = new FinancialDailyModel();
    m.setSymbols(Arrays.asList("AAPL"));
    m.setMarket(Market.US);
    m.setFields(Arrays.asList("revenue"));
    assertReject(m);
  }

  @Test
  public void financialDailyModel_empty_rejects() {
    FinancialDailyModel m = new FinancialDailyModel();
    assertReject(m);
  }

  @Test
  public void corporateActionModel_needsMoreFields() {
    CorporateActionModel m = new CorporateActionModel();
    m.setSymbols(Arrays.asList("AAPL"));
    m.setMarket(Market.US);
    m.setActionType(com.tigerbrokers.stock.openapi.client.struct.enums.CorporateActionType.DIVIDEND);
    assertReject(m);
  }

  @Test
  public void corporateActionModel_empty_rejects() {
    CorporateActionModel m = new CorporateActionModel();
    assertReject(m);
  }

  @Test
  public void financialReportModel_needsMoreFields() {
    FinancialReportModel m = new FinancialReportModel();
    m.setSymbols(Arrays.asList("AAPL"));
    m.setMarket(Market.US);
    m.setFields(Arrays.asList("revenue"));
    assertReject(m);
  }

  @Test
  public void financialReportModel_empty_rejects() {
    FinancialReportModel m = new FinancialReportModel();
    assertReject(m);
  }

  // === Future validators ===

  @Test
  public void futureContractByConCodeModel_valid_passes() {
    FutureContractByConCodeModel m = new FutureContractByConCodeModel();
    m.setContractCode("CL");
    assertPass(m);
  }

  @Test
  public void futureContractByConCodeModel_empty_rejects() {
    FutureContractByConCodeModel m = new FutureContractByConCodeModel();
    assertReject(m);
  }

  @Test
  public void futureExchangeModel_valid_passes() {
    FutureExchangeModel m = new FutureExchangeModel();
    m.setSecType(SecType.FUT.name());
    assertPass(m);
  }

  // === Place order validators ===

  @Test
  public void placeOrder_valid_passes() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.LMT);
    m.setTotalQuantity(100L);
    m.setLimitPrice(150.0);
    assertPass(m);
  }

  @Test
  public void placeOrder_noAccount_rejects() {
    TradeOrderModel m = new TradeOrderModel();
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.MKT);
    m.setTotalQuantity(100L);
    assertReject(m);
  }

  @Test
  public void placeOrder_noAction_rejects() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setOrderType(OrderType.MKT);
    m.setTotalQuantity(100L);
    assertReject(m);
  }

  @Test
  public void placeOrder_lmt_requiresLimitPrice() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.LMT);
    m.setTotalQuantity(100L);
    assertReject(m);
  }

  @Test
  public void placeOrder_noQuantity_rejects() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.MKT);
    assertReject(m);
  }

  @Test
  public void nullModel_noCrash() throws TigerApiException {
    ValidatorManager.getInstance().validate(null);
  }
}
