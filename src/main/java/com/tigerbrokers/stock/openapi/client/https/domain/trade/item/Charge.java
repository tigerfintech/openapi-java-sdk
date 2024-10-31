package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import java.util.List;

/**
 * @author liutongping
 * @date 2024/10/31
 */
public class Charge {
  private String category;
  private String categoryDesc;
  private Double total;
  List<ChargeDetails> details;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCategoryDesc() {
    return categoryDesc;
  }

  public void setCategoryDesc(String categoryDesc) {
    this.categoryDesc = categoryDesc;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public List<ChargeDetails> getDetails() {
    return details;
  }

  public void setDetails(List<ChargeDetails> details) {
    this.details = details;
  }
}
