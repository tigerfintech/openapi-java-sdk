package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

/**
 * @author liutongping
 * @date 2024/10/31
 */
public class ChargeDetails {
  private String type;
  private String typeDesc;
  private Double originalAmount;
  private Double afterDiscountAmount;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTypeDesc() {
    return typeDesc;
  }

  public void setTypeDesc(String typeDesc) {
    this.typeDesc = typeDesc;
  }

  public Double getOriginalAmount() {
    return originalAmount;
  }

  public void setOriginalAmount(Double originalAmount) {
    this.originalAmount = originalAmount;
  }

  public Double getAfterDiscountAmount() {
    return afterDiscountAmount;
  }

  public void setAfterDiscountAmount(Double afterDiscountAmount) {
    this.afterDiscountAmount = afterDiscountAmount;
  }
}
