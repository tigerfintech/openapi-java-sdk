package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/26.
 */
public class OptionRealTimeQuoteGroup implements Serializable {

  private OptionRealTimeQuote put;
  private OptionRealTimeQuote call;

  public OptionRealTimeQuote getPut() {
    return put;
  }

  public void setPut(OptionRealTimeQuote put) {
    this.put = put;
  }

  public OptionRealTimeQuote getCall() {
    return call;
  }

  public void setCall(OptionRealTimeQuote call) {
    this.call = call;
  }

  @Override
  public String toString() {
    return "OptionRealTimeQuoteGroup{" +
        "put=" + put +
        ", call=" + call +
        '}';
  }
}
