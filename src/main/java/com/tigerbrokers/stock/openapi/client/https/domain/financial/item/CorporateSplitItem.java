package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

/**
 * Description:
 * Created by lijiawen on 2019/02/28.
 */
public class CorporateSplitItem extends CorporateActionItem {

  private Double fromFactor;
  private Double toFactor;
  private Double ratio;
  private String type;

  public Double getFromFactor() {
    return fromFactor;
  }

  public void setFromFactor(Double fromFactor) {
    this.fromFactor = fromFactor;
  }

  public Double getToFactor() {
    return toFactor;
  }

  public void setToFactor(Double toFactor) {
    this.toFactor = toFactor;
  }

  public Double getRatio() {
    return ratio;
  }

  public void setRatio(Double ratio) {
    this.ratio = ratio;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
