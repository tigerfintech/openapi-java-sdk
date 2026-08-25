package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.WarrantQuoteModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.WarrantFilterModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureRealTimeQuoteModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTickModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTradingDateModel;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorCoverageTest2 {

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

  // === OptionCommonRequestValidator ===

  @Test
  public void optionCommon_valid_passes() {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setRight("CALL");
    m.setStrike("150");
    m.setExpiry("20240119");
    assertPass(m);
  }

  @Test
  public void optionCommon_noSymbol_rejects() {
    OptionCommonModel m = new OptionCommonModel();
    m.setRight("CALL");
    m.setStrike("150");
    m.setExpiry("20240119");
    assertReject(m);
  }

  @Test
  public void optionCommon_noRight_rejects() {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setStrike("150");
    m.setExpiry("20240119");
    assertReject(m);
  }

  @Test
  public void optionCommon_noStrike_rejects() {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setRight("CALL");
    m.setExpiry("20240119");
    assertReject(m);
  }

  @Test
  public void optionCommon_noExpiry_rejects() {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setRight("CALL");
    m.setStrike("150");
    assertReject(m);
  }

  @Test
  public void optionCommon_invalidRight_rejects() {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setRight("INVALID");
    m.setStrike("150");
    m.setExpiry("20240119");
    assertReject(m);
  }

  @Test
  public void optionCommon_lowercaseRight_passes() {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setRight("call");
    m.setStrike("150");
    m.setExpiry("20240119");
    assertPass(m);
  }

  // === WarrantQuoteRequestValidator ===

  @Test
  public void warrantQuote_valid_passes() {
    WarrantQuoteModel m = new WarrantQuoteModel();
    m.setSymbols(Arrays.asList("12345.HK"));
    assertPass(m);
  }

  @Test
  public void warrantQuote_empty_rejects() {
    WarrantQuoteModel m = new WarrantQuoteModel();
    assertReject(m);
  }

  // === FutureQuoteRequestValidator ===

  @Test
  public void futureRealTimeQuote_valid_passes() {
    FutureRealTimeQuoteModel m = new FutureRealTimeQuoteModel();
    m.setContractCodes(Arrays.asList("CL2401"));
    assertPass(m);
  }

  @Test
  public void futureRealTimeQuote_empty_rejects() {
    FutureRealTimeQuoteModel m = new FutureRealTimeQuoteModel();
    assertReject(m);
  }

  @Test
  public void futureTick_valid_passes() {
    FutureTickModel m = new FutureTickModel();
    m.setContractCode("CL2401");
    m.setLimit(100);
    assertPass(m);
  }

  @Test
  public void futureTick_noContractCode_rejects() {
    FutureTickModel m = new FutureTickModel();
    m.setLimit(100);
    assertReject(m);
  }

  @Test
  public void futureTick_invalidLimit_rejects() {
    FutureTickModel m = new FutureTickModel();
    m.setContractCode("CL2401");
    m.setLimit(0);
    assertReject(m);
  }

  @Test
  public void futureTick_limitExceedsMax_rejects() {
    FutureTickModel m = new FutureTickModel();
    m.setContractCode("CL2401");
    m.setLimit(2000);
    assertReject(m);
  }

  @Test
  public void futureTradingDate_valid_passes() {
    FutureTradingDateModel m = new FutureTradingDateModel();
    m.setContractCode("CL2401");
    assertPass(m);
  }

  @Test
  public void futureTradingDate_empty_rejects() {
    FutureTradingDateModel m = new FutureTradingDateModel();
    assertReject(m);
  }
}
