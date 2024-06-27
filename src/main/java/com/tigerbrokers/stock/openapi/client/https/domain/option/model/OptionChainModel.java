package com.tigerbrokers.stock.openapi.client.https.domain.option.model;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import com.tigerbrokers.stock.openapi.client.util.SymbolUtil;
import java.util.Date;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionChainModel extends OptionModel {

  private String symbol;
  private Long expiry;

  public OptionChainModel() {
  }

  public OptionChainModel(String symbol, Long expiry) {
    this.symbol = symbol;
    this.expiry = expiry;
  }

  public OptionChainModel(String symbol, String expiry) {
    this(symbol, expiry, SymbolUtil.getZoneIdBySymbol(symbol));
  }

  public OptionChainModel(String symbol, String expiry, TimeZoneId timeZoneId) {
    this.symbol = symbol;
    Date date = DateUtils.getZoneDate(expiry, timeZoneId);
    if (date != null) {
      this.expiry = date.getTime();
    }
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Long getExpiry() {
    return expiry;
  }

  public void setExpiry(Long expiry) {
    this.expiry = expiry;
  }

  public void setExpiry(String expiry) {
    setExpiry(expiry, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }

  public void setExpiry(String expiry, TimeZoneId zoneId) {
    Date date = DateUtils.getZoneDate(expiry, zoneId);
    if (date != null) {
      this.expiry = date.getTime();
    }
  }

  @Override
  public String toString() {
    return "OptionChainModel{" +
        "market='" + market + '\'' +
        ", symbol='" + symbol + '\'' +
        ", expiry='" + expiry + '\'' +
        '}';
  }
}
