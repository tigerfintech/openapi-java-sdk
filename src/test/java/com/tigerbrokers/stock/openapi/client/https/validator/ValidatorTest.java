package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteKlineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteMarketModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.TigerApiException;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

  private static final ValidatorManager vm = ValidatorManager.getInstance();

  @Test
  public void nullModel_noCrash() throws TigerApiException {
    vm.validate(null);
  }

  @Test
  public void placeOrder_noAction_rejects() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("12345678901234567");
    m.setOrderType(OrderType.LMT);
    m.setSymbol("AAPL");
    try {
      vm.validate(m);
      Assert.fail("should reject");
    } catch (TigerApiException e) {
      Assert.assertTrue(e.getErrCode() > 0);
    }
  }

  @Test
  public void kline_empty_rejects() {
    QuoteKlineModel m = new QuoteKlineModel();
    try {
      vm.validate(m);
      Assert.fail("should reject");
    } catch (TigerApiException e) {
      Assert.assertTrue(e.getErrCode() > 0);
    }
  }

  @Test
  public void market_valid_passes() throws TigerApiException {
    QuoteMarketModel m = new QuoteMarketModel();
    m.setMarket(Market.US);
    vm.validate(m);
  }
}
