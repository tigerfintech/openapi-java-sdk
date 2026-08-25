package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteCapitalFlowModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteCapitalModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteHistoryTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.AttachType;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorBranchTest {

  private void assertReject(ApiModel model) {
    try {
      ValidatorManager.getInstance().validate(model);
      Assert.fail("should reject");
    } catch (TigerApiException e) {
      Assert.assertTrue(e.getErrCode() > 0);
    }
  }

  private void assertPass(ApiModel model) {
    try {
      ValidatorManager.getInstance().validate(model);
    } catch (TigerApiException e) {
      Assert.fail("should pass: " + e.getMessage());
    }
  }

  @Test
  public void quoteContractModel_opt_valid() {
    QuoteContractModel m = new QuoteContractModel("AAPL", SecType.OPT);
    m.setExpiry("20240119");
    assertPass(m);
  }

  @Test
  public void quoteContractModel_opt_noExpiry_rejects() {
    QuoteContractModel m = new QuoteContractModel("AAPL", SecType.OPT);
    assertReject(m);
  }

  @Test
  public void quoteContractModel_war_valid() {
    QuoteContractModel m = new QuoteContractModel("12345", SecType.WAR);
    m.setExpiry("20240119");
    assertPass(m);
  }

  @Test
  public void quoteHistoryTimelineModel_valid() {
    QuoteHistoryTimelineModel m = new QuoteHistoryTimelineModel(Arrays.asList("AAPL"), "2024-01-01");
    assertPass(m);
  }

  @Test
  public void quoteHistoryTimelineModel_noDate() {
    QuoteHistoryTimelineModel m = new QuoteHistoryTimelineModel(Arrays.asList("AAPL"), null);
    assertReject(m);
  }

  @Test
  public void quoteCapitalModel_valid() {
    QuoteCapitalModel m = new QuoteCapitalModel("AAPL", "US");
    assertPass(m);
  }

  @Test
  public void quoteCapitalModel_noSymbol() {
    QuoteCapitalModel m = new QuoteCapitalModel(null, "US");
    assertReject(m);
  }

  @Test
  public void quoteCapitalModel_noMarket() {
    QuoteCapitalModel m = new QuoteCapitalModel("AAPL", null);
    assertReject(m);
  }

  @Test
  public void quoteCapitalFlowModel_valid() {
    QuoteCapitalFlowModel m = new QuoteCapitalFlowModel("AAPL", "US", "1d");
    assertPass(m);
  }

  @Test
  public void quoteCapitalFlowModel_noPeriod() {
    QuoteCapitalFlowModel m = new QuoteCapitalFlowModel("AAPL", "US", null);
    assertReject(m);
  }

  @Test
  public void placeOrder_stp_noAuxPrice() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.STP);
    m.setTotalQuantity(100L);
    assertReject(m);
  }

  @Test
  public void placeOrder_stp_valid() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.STP);
    m.setTotalQuantity(100L);
    m.setAuxPrice(140.0);
    assertPass(m);
  }

  @Test
  public void placeOrder_stpLmt_valid() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.STP_LMT);
    m.setTotalQuantity(100L);
    m.setLimitPrice(145.0);
    m.setAuxPrice(140.0);
    assertPass(m);
  }

  @Test
  public void placeOrder_gtd_noExpireTime() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.LMT);
    m.setTotalQuantity(100L);
    m.setLimitPrice(145.0);
    m.setTimeInForce(TimeInForce.GTD);
    assertReject(m);
  }

  @Test
  public void placeOrder_gtd_valid() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.LMT);
    m.setTotalQuantity(100L);
    m.setLimitPrice(145.0);
    m.setTimeInForce(TimeInForce.GTD);
    m.setExpireTime(9999999999L);
    assertPass(m);
  }

  @Test
  public void placeOrder_brackets_noProfitTakerPrice() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.STK);
    m.setSymbol("AAPL");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.MKT);
    m.setTotalQuantity(100L);
    m.setAttachType(AttachType.BRACKETS);
    assertReject(m);
  }

  @Test
  public void placeOrder_mleg_noSymbol() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.MLEG);
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.MKT);
    m.setTotalQuantity(100L);
    assertPass(m);
  }

  @Test
  public void placeOrder_fund_noQuantity() {
    TradeOrderModel m = new TradeOrderModel();
    m.setAccount("acct");
    m.setSecType(SecType.FUND);
    m.setSymbol("FUND123");
    m.setAction(ActionType.BUY);
    m.setOrderType(OrderType.MKT);
    assertPass(m);
  }
}
