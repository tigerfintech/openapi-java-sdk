package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by bean on 2022/11/14.
 */
public class CapitalDistributionItem extends ApiModel {

  /**
   * symbol
   */
  private String symbol;

  /**
   * inTotal - outTotal
   */
  private Double netInflow;

  /**
   * Amount of inflow capital, all orders
   */
  private Double inAll;
  /**
   * Amount of inflow capital, large orders
   */
  private Double inBig;
  /**
   * Amount of inflow capital, medium orders
   */
  private Double inMid;
  /**
   * Amount of inflow capital, small orders
   */
  private Double inSmall;

  /**
   * Amount of outflow capital, all orders
   */
  private Double outAll;
  /**
   * Amount of outflow capital, large orders
   */
  private Double outBig;
  /**
   * Amount of outflow capital, medium orders
   */
  private Double outMid;
  /**
   * Amount of outflow capital, small orders
   */
  private Double outSmall;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getNetInflow() {
    return netInflow;
  }

  public void setNetInflow(Double netInflow) {
    this.netInflow = netInflow;
  }

  public Double getInAll() {
    return inAll;
  }

  public void setInAll(Double inAll) {
    this.inAll = inAll;
  }

  public Double getInBig() {
    return inBig;
  }

  public void setInBig(Double inBig) {
    this.inBig = inBig;
  }

  public Double getInMid() {
    return inMid;
  }

  public void setInMid(Double inMid) {
    this.inMid = inMid;
  }

  public Double getInSmall() {
    return inSmall;
  }

  public void setInSmall(Double inSmall) {
    this.inSmall = inSmall;
  }

  public Double getOutAll() {
    return outAll;
  }

  public void setOutAll(Double outAll) {
    this.outAll = outAll;
  }

  public Double getOutBig() {
    return outBig;
  }

  public void setOutBig(Double outBig) {
    this.outBig = outBig;
  }

  public Double getOutMid() {
    return outMid;
  }

  public void setOutMid(Double outMid) {
    this.outMid = outMid;
  }

  public Double getOutSmall() {
    return outSmall;
  }

  public void setOutSmall(Double outSmall) {
    this.outSmall = outSmall;
  }

  @Override
  public String toString() {
    return "CapitalDistributionItem{" +
        "symbol='" + symbol + '\'' +
        ", netInflow=" + netInflow +
        ", inAll=" + inAll +
        ", inBig=" + inBig +
        ", inMid=" + inMid +
        ", inSmall=" + inSmall +
        ", outAll=" + outAll +
        ", outBig=" + outBig +
        ", outMid=" + outMid +
        ", outSmall=" + outSmall +
        '}';
  }
}
