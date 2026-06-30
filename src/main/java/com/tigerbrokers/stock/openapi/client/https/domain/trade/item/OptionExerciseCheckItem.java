package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

public class OptionExerciseCheckItem {

  private Double availableQuantity;
  private Double position;
  private Double stkPosition;
  private Double stkPositionChange;
  private Double stkPositionBefore;
  private Double stkPositionAfter;
  private String symbol;

  public Double getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(Double availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public Double getPosition() {
    return position;
  }

  public void setPosition(Double position) {
    this.position = position;
  }

  public Double getStkPosition() {
    return stkPosition;
  }

  public void setStkPosition(Double stkPosition) {
    this.stkPosition = stkPosition;
  }

  public Double getStkPositionChange() {
    return stkPositionChange;
  }

  public void setStkPositionChange(Double stkPositionChange) {
    this.stkPositionChange = stkPositionChange;
  }

  public Double getStkPositionBefore() {
    return stkPositionBefore;
  }

  public void setStkPositionBefore(Double stkPositionBefore) {
    this.stkPositionBefore = stkPositionBefore;
  }

  public Double getStkPositionAfter() {
    return stkPositionAfter;
  }

  public void setStkPositionAfter(Double stkPositionAfter) {
    this.stkPositionAfter = stkPositionAfter;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return "OptionExerciseCheckItem{" +
        "availableQuantity=" + availableQuantity +
        ", position=" + position +
        ", stkPosition=" + stkPosition +
        ", stkPositionChange=" + stkPositionChange +
        ", stkPositionBefore=" + stkPositionBefore +
        ", stkPositionAfter=" + stkPositionAfter +
        ", symbol='" + symbol + '\'' +
        '}';
  }
}
