package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.OptionSymbol;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.util.SymbolUtil;
import java.util.Date;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionCommonModel extends ApiModel {

  protected String symbol;
  protected String right;
  protected String strike;
  protected Long expiry;

  public OptionCommonModel() {
  }

  public OptionCommonModel(String identifier) throws TigerApiException {
    OptionSymbol optionSymbol = SymbolUtil.convertToOptionSymbolObject(identifier);
    setSymbol(optionSymbol.getSymbol());
    setRight(optionSymbol.getRight());
    setStrike(optionSymbol.getStrike());
    setExpiry(optionSymbol.getExpiry(), SymbolUtil.getZoneIdBySymbol(optionSymbol.getSymbol()));
  }

  public OptionCommonModel(String symbol, String right, String strike, Long expiry) {
    this.symbol = symbol;
    this.right = right;
    this.strike = strike;
    this.expiry = expiry;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public Long getExpiry() {
    return expiry;
  }

  public void setExpiry(Long expiry) {
    this.expiry = expiry;
  }

  public void setExpiry(String expiry) {
    TimeZoneId timeZoneId = StringUtils.isEmpty(this.symbol) ?
        TimeZoneId.NewYork : SymbolUtil.getZoneIdBySymbol(this.symbol);
    Date date = DateUtils.getZoneDate(expiry, timeZoneId);
    if (date != null) {
      this.expiry = date.getTime();
    }
  }

  public void setExpiry(String expiry, TimeZoneId zoneId) {
    Date date = DateUtils.getZoneDate(expiry, zoneId);
    if (date != null) {
      this.expiry = date.getTime();
    }
  }

  @Override
  public String toString() {
    return "OptionCommonModel{" +
        "symbol='" + symbol + '\'' +
        ", right='" + right + '\'' +
        ", strike='" + strike + '\'' +
        ", expiry='" + expiry + '\'' +
        '}';
  }
}
