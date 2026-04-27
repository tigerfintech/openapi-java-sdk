package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Created on 2025/5/27
 *
 * @author: sukai
 */
public class TradeOrderPreviewItem extends ApiModel {
  private String account;
  private String status;
  private Double initMargin;
  private Double maintMargin;
  private Double equityWithLoan;
  private Double initMarginBefore;
  private Double maintMarginBefore;
  private Double equityWithLoanBefore;
  private String marginCurrency;
  private Double commission;
  private Double minCommission;
  private Double maxCommission;
  private String commissionCurrency;
  private Double maxOrderSize;
  private String warningText;
  private Boolean isPass;
  private Double availableEE;
  private Double excessLiquidity;
  private Double overnightLiquidation;
  private Double gst;

  private String message;

  @Override
  public String getAccount() {
    return account;
  }

  @Override
  public void setAccount(String account) {
    this.account = account;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Double getInitMargin() {
    return initMargin;
  }

  public void setInitMargin(Double initMargin) {
    this.initMargin = initMargin;
  }

  public Double getMaintMargin() {
    return maintMargin;
  }

  public void setMaintMargin(Double maintMargin) {
    this.maintMargin = maintMargin;
  }

  public Double getEquityWithLoan() {
    return equityWithLoan;
  }

  public void setEquityWithLoan(Double equityWithLoan) {
    this.equityWithLoan = equityWithLoan;
  }

  public Double getInitMarginBefore() {
    return initMarginBefore;
  }

  public void setInitMarginBefore(Double initMarginBefore) {
    this.initMarginBefore = initMarginBefore;
  }

  public Double getMaintMarginBefore() {
    return maintMarginBefore;
  }

  public void setMaintMarginBefore(Double maintMarginBefore) {
    this.maintMarginBefore = maintMarginBefore;
  }

  public Double getEquityWithLoanBefore() {
    return equityWithLoanBefore;
  }

  public void setEquityWithLoanBefore(Double equityWithLoanBefore) {
    this.equityWithLoanBefore = equityWithLoanBefore;
  }

  public String getMarginCurrency() {
    return marginCurrency;
  }

  public void setMarginCurrency(String marginCurrency) {
    this.marginCurrency = marginCurrency;
  }

  public Double getCommission() {
    return commission;
  }

  public void setCommission(Double commission) {
    this.commission = commission;
  }

  public Double getMinCommission() {
    return minCommission;
  }

  public void setMinCommission(Double minCommission) {
    this.minCommission = minCommission;
  }

  public Double getMaxCommission() {
    return maxCommission;
  }

  public void setMaxCommission(Double maxCommission) {
    this.maxCommission = maxCommission;
  }

  public String getCommissionCurrency() {
    return commissionCurrency;
  }

  public void setCommissionCurrency(String commissionCurrency) {
    this.commissionCurrency = commissionCurrency;
  }

  public Double getMaxOrderSize() {
    return maxOrderSize;
  }

  public void setMaxOrderSize(Double maxOrderSize) {
    this.maxOrderSize = maxOrderSize;
  }

  public String getWarningText() {
    return warningText;
  }

  public void setWarningText(String warningText) {
    this.warningText = warningText;
  }

  public Boolean getPass() {
    return isPass;
  }

  public void setPass(Boolean pass) {
    isPass = pass;
  }

  public Double getAvailableEE() {
    return availableEE;
  }

  public void setAvailableEE(Double availableEE) {
    this.availableEE = availableEE;
  }

  public Double getExcessLiquidity() {
    return excessLiquidity;
  }

  public void setExcessLiquidity(Double excessLiquidity) {
    this.excessLiquidity = excessLiquidity;
  }

  public Double getOvernightLiquidation() {
    return overnightLiquidation;
  }

  public void setOvernightLiquidation(Double overnightLiquidation) {
    this.overnightLiquidation = overnightLiquidation;
  }

  public Double getGst() {
    return gst;
  }

  public void setGst(Double gst) {
    this.gst = gst;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
