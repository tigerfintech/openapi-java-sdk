package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradeSession;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteTradeTickModel extends QuoteSymbolModel {

  @JSONField(name = "begin_index")
  private Long beginIndex;
  @JSONField(name = "end_index")
  private Long endIndex;
  private Integer limit;
  @JSONField(name = "trade_session")
  private TradeSession tradeSession;

  public QuoteTradeTickModel() {
    this.lang = ClientConfig.DEFAULT_CONFIG.getDefaultLanguage();
  }

  public QuoteTradeTickModel(List<String> symbols) {
    super(symbols);
  }

  public QuoteTradeTickModel(List<String> symbols, Language lang) {
    super(symbols, lang);
  }

  public QuoteTradeTickModel(List<String> symbols, Long beginIndex, Long endIndex) {
    super(symbols);
    this.beginIndex = beginIndex;
    this.endIndex = endIndex;
  }

  public QuoteTradeTickModel(List<String> symbols, Long beginIndex, Long endIndex, Language lang) {
    super(symbols, lang);
    this.beginIndex = beginIndex;
    this.endIndex = endIndex;
  }

  public Long getBeginIndex() {
    return beginIndex;
  }

  public void setBeginIndex(Long beginIndex) {
    this.beginIndex = beginIndex;
  }

  public Long getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Long endIndex) {
    this.endIndex = endIndex;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public TradeSession getTradeSession() {
    return tradeSession;
  }

  public void setTradeSession(TradeSession tradeSession) {
    this.tradeSession = tradeSession;
  }
}
