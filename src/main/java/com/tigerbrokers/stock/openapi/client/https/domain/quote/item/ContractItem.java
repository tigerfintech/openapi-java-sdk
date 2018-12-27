package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class ContractItem extends ApiModel {

  /**
   * 对应的正股股票代码
   */
  private String symbol;

  /**
   * 合约类型（WAR：涡轮、IOPT：牛熊证）
   */
  private String secType;

  /**
   * 合约数据列表
   */
  private List<QuoteContract> items;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public List<QuoteContract> getItems() {
    return items;
  }

  public void setItems(List<QuoteContract> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "ContractItem{" +
        "symbol='" + symbol + '\'' +
        ", secType='" + secType + '\'' +
        ", items=" + items +
        '}';
  }
}
