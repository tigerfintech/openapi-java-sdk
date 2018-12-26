package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class WarrantItem extends ApiModel {

  /**
   * 对应的正股股票代码
   */
  private String symbol;

  /**
   * 合约类型（WAR：涡轮、IOPT：牛熊证）
   */
  private String secType;

  /**
   * 涡轮牛熊证数据列表
   */
  private List<WarrantContract> items;

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

  public List<WarrantContract> getItems() {
    return items;
  }

  public void setItems(List<WarrantContract> items) {
    this.items = items;
  }

  @Override public String toString() {
    return "WarrantItem{" +
        "symbol='" + symbol + '\'' +
        ", secType='" + secType + '\'' +
        ", items=" + items +
        '}';
  }
}
