package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureRealTimeQuoteModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTickModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTradingDateModel;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorDirectTest {

  @Test
  public void optionCommonValidator_valid() throws TigerApiException {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setRight("CALL");
    m.setStrike("150");
    m.setExpiry(1705708800000L);
    new OptionCommonRequestValidator().validate(m);
  }

  @Test(expected = TigerApiException.class)
  public void optionCommonValidator_noSymbol() throws TigerApiException {
    OptionCommonModel m = new OptionCommonModel();
    m.setRight("CALL");
    m.setStrike("150");
    m.setExpiry(1705708800000L);
    new OptionCommonRequestValidator().validate(m);
  }

  @Test(expected = TigerApiException.class)
  public void optionCommonValidator_noRight() throws TigerApiException {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setStrike("150");
    m.setExpiry(1705708800000L);
    new OptionCommonRequestValidator().validate(m);
  }

  @Test(expected = TigerApiException.class)
  public void optionCommonValidator_noStrike() throws TigerApiException {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setRight("CALL");
    m.setExpiry(1705708800000L);
    new OptionCommonRequestValidator().validate(m);
  }

  @Test(expected = TigerApiException.class)
  public void optionCommonValidator_noExpiry() throws TigerApiException {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setRight("CALL");
    m.setStrike("150");
    new OptionCommonRequestValidator().validate(m);
  }

  @Test(expected = TigerApiException.class)
  public void optionCommonValidator_invalidRight() throws TigerApiException {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setRight("INVALID");
    m.setStrike("150");
    m.setExpiry(1705708800000L);
    new OptionCommonRequestValidator().validate(m);
  }

  @Test
  public void optionCommonValidator_lowercaseRight() throws TigerApiException {
    OptionCommonModel m = new OptionCommonModel();
    m.setSymbol("AAPL");
    m.setRight("call");
    m.setStrike("150");
    m.setExpiry(1705708800000L);
    new OptionCommonRequestValidator().validate(m);
    Assert.assertEquals("CALL", m.getRight());
  }

  @Test
  public void futureQuoteValidator_realTime_valid() throws TigerApiException {
    FutureRealTimeQuoteModel m = new FutureRealTimeQuoteModel();
    m.setContractCodes(Arrays.asList("CL2401"));
    new FutureQuoteRequestValidator().validate(m);
  }

  @Test(expected = TigerApiException.class)
  public void futureQuoteValidator_realTime_empty() throws TigerApiException {
    FutureRealTimeQuoteModel m = new FutureRealTimeQuoteModel();
    new FutureQuoteRequestValidator().validate(m);
  }

  @Test
  public void futureQuoteValidator_tick_valid() throws TigerApiException {
    FutureTickModel m = new FutureTickModel();
    m.setContractCode("CL2401");
    m.setLimit(100);
    new FutureQuoteRequestValidator().validate(m);
  }

  @Test(expected = TigerApiException.class)
  public void futureQuoteValidator_tick_noContractCode() throws TigerApiException {
    FutureTickModel m = new FutureTickModel();
    m.setLimit(100);
    new FutureQuoteRequestValidator().validate(m);
  }

  @Test(expected = TigerApiException.class)
  public void futureQuoteValidator_tick_invalidLimit() throws TigerApiException {
    FutureTickModel m = new FutureTickModel();
    m.setContractCode("CL2401");
    m.setLimit(0);
    new FutureQuoteRequestValidator().validate(m);
  }

  @Test(expected = TigerApiException.class)
  public void futureQuoteValidator_tick_limitExceedsMax() throws TigerApiException {
    FutureTickModel m = new FutureTickModel();
    m.setContractCode("CL2401");
    m.setLimit(2000);
    new FutureQuoteRequestValidator().validate(m);
  }

  @Test
  public void futureQuoteValidator_tradingDate_valid() throws TigerApiException {
    FutureTradingDateModel m = new FutureTradingDateModel();
    m.setContractCode("CL2401");
    new FutureQuoteRequestValidator().validate(m);
  }

  @Test(expected = TigerApiException.class)
  public void futureQuoteValidator_tradingDate_empty() throws TigerApiException {
    FutureTradingDateModel m = new FutureTradingDateModel();
    new FutureQuoteRequestValidator().validate(m);
  }

  @Test
  public void futureQuoteValidator_nullModel() throws TigerApiException {
    new FutureQuoteRequestValidator().validate(null);
  }
}
