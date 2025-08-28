package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradeSession;
import java.util.List;

/**
 * author：ltc
 * date：2019/08/13
 */
public class QuoteDepthModel extends ApiModel {

  private List<String> symbols;
  private String market;
  @JSONField(name = "trade_session")
  private TradeSession tradeSession;

  public QuoteDepthModel(List<String> symbols, String market) {
    this.symbols = symbols;
    this.market = market;
  }

  public QuoteDepthModel(List<String> symbols, String market, TradeSession tradeSession) {
    this.symbols = symbols;
    this.market = market;
    this.tradeSession = tradeSession;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  public TradeSession getTradeSession() {
    return tradeSession;
  }

  public void setTradeSession(TradeSession tradeSession) {
    this.tradeSession = tradeSession;
  }
}
