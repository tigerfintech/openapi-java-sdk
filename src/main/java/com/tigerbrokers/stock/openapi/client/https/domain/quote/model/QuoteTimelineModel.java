package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeLineType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TradeSession;

import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteTimelineModel extends QuoteSymbolModel {

  @JSONField(name = "begin_time")
  private Long beginTime;

  private String period;

  public QuoteTimelineModel(List<String> symbols, Long beginTime) {
    super(symbols);
    this.beginTime = beginTime;
    this.period = TimeLineType.day.name();
  }

  public QuoteTimelineModel(List<String> symbols, Long beginTime, TradeSession tradeSession) {
    super(symbols, tradeSession);
    this.beginTime = beginTime;
    this.period = TimeLineType.day.name();
  }

  public QuoteTimelineModel(List<String> symbols, Long beginTime, TradeSession tradeSession, Language language) {
    super(symbols, tradeSession, language);
    this.beginTime = beginTime;
    this.period = TimeLineType.day.name();
  }

  public QuoteTimelineModel(List<String> symbols, Long beginTime, TradeSession tradeSession, TimeLineType timeLineType,
                            Language language) {
    super(symbols, tradeSession, language);
    this.beginTime = beginTime;
    if (timeLineType != null) {
      this.period = timeLineType.name();
    }
  }

  public Long getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Long beginTime) {
    this.beginTime = beginTime;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }
}
