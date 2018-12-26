package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class ContractBase implements Serializable {

  /**
   * 合约代码
   */
  protected String symbol;

  /**
   * 合约名称
   */
  protected String name;

  /**
   * 交易所
   */
  protected String exchange;

  /**
   * 市场
   */
  protected String market;

  /**
   * 合约类型
   */
  protected String secType;

  /**
   * 币种
   */
  protected String currency;

  /**
   * 到期日(期权、窝轮、牛熊证、期货)，格式：yyyy-MM-dd
   */
  protected String expiry;

  /**
   * 底层价格
   */
  protected String strike;

  /**
   * 每手数量
   */
  protected Double multiplier;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public Double getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(Double multiplier) {
    this.multiplier = multiplier;
  }

  @Override
  public String toString() {
    return "ContractBase{" +
        "symbol='" + symbol + '\'' +
        ", name='" + name + '\'' +
        ", exchange='" + exchange + '\'' +
        ", market='" + market + '\'' +
        ", secType='" + secType + '\'' +
        ", currency='" + currency + '\'' +
        ", expiry='" + expiry + '\'' +
        ", strike='" + strike + '\'' +
        ", multiplier=" + multiplier +
        '}';
  }
}
