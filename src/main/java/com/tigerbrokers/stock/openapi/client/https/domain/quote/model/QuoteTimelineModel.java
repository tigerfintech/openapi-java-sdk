package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class QuoteTimelineModel extends QuoteSymbolModel {

  @JSONField(name = "begin_time")
  private Long beginTime;

  public QuoteTimelineModel() {
  }

  public QuoteTimelineModel(List<String> symbols) {
    super(symbols);
  }

  public QuoteTimelineModel(List<String> symbols, Long beginTime) {
    super(symbols);
    this.beginTime = beginTime;
  }

  public QuoteTimelineModel(List<String> symbols, Long beginTime, Language language) {
    super(symbols, language);
    this.beginTime = beginTime;
  }

  public Long getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Long beginTime) {
    this.beginTime = beginTime;
  }
}
