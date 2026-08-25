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

  // ── Amount-order (cash_amount) validation ────────────────────────────────

  /** Amount order (cash_amount > 0, no quantity) should validate cleanly. */
  @Test
  public void placeOrder_amountOrder_passesWithoutQuantity() throws TigerApiException {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("12345678901234567");
    m.setSecType(com.tigerbrokers.stock.openapi.client.struct.enums.SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(com.tigerbrokers.stock.openapi.client.struct.enums.ActionType.BUY);
    m.setOrderType(OrderType.MKT);
    m.setCashAmount(100.0);
    // totalQuantity intentionally not set — validator should skip that check
    // when cashAmount > 0, so this must not throw.
    vm.validate(m);
  }

  /** Amount order with explicit quantity=0 is still allowed since cash_amount takes over. */
  @Test
  public void placeOrder_amountOrder_passesWithZeroQuantity() throws TigerApiException {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("12345678901234567");
    m.setSecType(com.tigerbrokers.stock.openapi.client.struct.enums.SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(com.tigerbrokers.stock.openapi.client.struct.enums.ActionType.BUY);
    m.setOrderType(OrderType.MKT);
    m.setCashAmount(100.0);
    m.setTotalQuantity(0L);
    vm.validate(m);
  }

  /** Regular order (no cash_amount) still requires positive quantity. */
  @Test
  public void placeOrder_regularOrder_stillRejectsMissingQuantity() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("12345678901234567");
    m.setSecType(com.tigerbrokers.stock.openapi.client.struct.enums.SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(com.tigerbrokers.stock.openapi.client.struct.enums.ActionType.BUY);
    m.setOrderType(OrderType.MKT);
    // Neither cashAmount nor totalQuantity — must still reject.
    try {
      vm.validate(m);
      Assert.fail("should reject");
    } catch (TigerApiException e) {
      Assert.assertTrue(e.getMessage(), e.getMessage().contains("total_quantity"));
    }
  }

  /** Regular order with zero quantity still rejected (regression guard). */
  @Test
  public void placeOrder_regularOrder_stillRejectsZeroQuantity() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("12345678901234567");
    m.setSecType(com.tigerbrokers.stock.openapi.client.struct.enums.SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(com.tigerbrokers.stock.openapi.client.struct.enums.ActionType.BUY);
    m.setOrderType(OrderType.MKT);
    m.setTotalQuantity(0L);
    // cashAmount is null — regular quantity path enforced.
    try {
      vm.validate(m);
      Assert.fail("should reject");
    } catch (TigerApiException e) {
      Assert.assertTrue(e.getMessage(), e.getMessage().contains("total_quantity"));
    }
  }
}
