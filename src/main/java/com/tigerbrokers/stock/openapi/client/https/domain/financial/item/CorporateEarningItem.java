package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

import java.time.LocalDate;

/**
 * Description:
 * Created by lijiawen on 2019/11/27
 */
public class CorporateEarningItem extends CorporateActionItem {

  private Double expectedEps;
  private Double actualEps;
  private LocalDate reportDate;
  private String reportTime;
  private String fiscalQuarterEnding;

  public Double getExpectedEps() {
    return expectedEps;
  }

  public void setExpectedEps(Double expectedEps) {
    this.expectedEps = expectedEps;
  }

  public Double getActualEps() {
    return actualEps;
  }

  public void setActualEps(Double actualEps) {
    this.actualEps = actualEps;
  }

  public LocalDate getReportDate() {
    return reportDate;
  }

  public void setReportDate(LocalDate reportDate) {
    this.reportDate = reportDate;
  }

  public String getReportTime() {
    return reportTime;
  }

  public void setReportTime(String reportTime) {
    this.reportTime = reportTime;
  }

  public String getFiscalQuarterEnding() {
    return fiscalQuarterEnding;
  }

  public void setFiscalQuarterEnding(String fiscalQuarterEnding) {
    this.fiscalQuarterEnding = fiscalQuarterEnding;
  }

  @Override
  public String toString() {
    return "CorporateEarningItem{" +
        "expectedEps=" + expectedEps +
        ", actualEps=" + actualEps +
        ", reportDate=" + reportDate +
        ", reportTime='" + reportTime + '\'' +
        ", fiscalQuarterEnding='" + fiscalQuarterEnding + '\'' +
        '}';
  }
}
