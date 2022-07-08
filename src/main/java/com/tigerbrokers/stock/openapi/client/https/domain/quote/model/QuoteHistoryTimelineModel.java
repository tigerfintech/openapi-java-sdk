package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2022/04/12.
 */
public class QuoteHistoryTimelineModel extends QuoteSymbolModel {

  /** yyyyMMdd */
  private String date;

  private RightOption right;

  public QuoteHistoryTimelineModel(List<String> symbols, String date) {
    super(symbols);
    this.date = date;
  }

  public QuoteHistoryTimelineModel(List<String> symbols, String date, Language language) {
    super(symbols, language);
    this.date = date;
  }

  public QuoteHistoryTimelineModel(List<String> symbols, String date, RightOption right,
      Language language) {
    super(symbols, language);
    this.date = date;
    this.right = right;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public RightOption getRight() {
    return right;
  }

  public void setRight(RightOption right) {
    this.right = right;
  }
}
