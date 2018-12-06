package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.alibaba.fastjson.JSONObject;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/06.
 */
public class OptionExpirationQueryRequest {

  private List<String> symbols;

  public OptionExpirationQueryRequest(List<String> symbols) {
    this.symbols = symbols;
  }

  public static OptionExpirationQueryRequest of(List<String> symbols) {
    return new OptionExpirationQueryRequest(symbols);
  }

  public String toJson() {
    return JSONObject.toJSONString(this);
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }
}
