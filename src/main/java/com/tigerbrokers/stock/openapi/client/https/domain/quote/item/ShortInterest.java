package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class ShortInterest implements Serializable {

  /**
   * 回补天数
   */
  private Double daysToCover;

  /**
   * 日成交量
   */
  private Long avgDailyVolume;

  /**
   * 未平仓做空量
   */
  private Long shortInterest;

  /**
   * 日期，格式：yyyy-MM-dd
   */
  private String settlementDate;

  /**
   * 占流通股本比例
   */
  private Double percentOfFloat;

  public Double getDaysToCover() {
    return daysToCover;
  }

  public void setDaysToCover(Double daysToCover) {
    this.daysToCover = daysToCover;
  }

  public Long getAvgDailyVolume() {
    return avgDailyVolume;
  }

  public void setAvgDailyVolume(Long avgDailyVolume) {
    this.avgDailyVolume = avgDailyVolume;
  }

  public Long getShortInterest() {
    return shortInterest;
  }

  public void setShortInterest(Long shortInterest) {
    this.shortInterest = shortInterest;
  }

  public String getSettlementDate() {
    return settlementDate;
  }

  public void setSettlementDate(String settlementDate) {
    this.settlementDate = settlementDate;
  }

  public Double getPercentOfFloat() {
    return percentOfFloat;
  }

  public void setPercentOfFloat(Double percentOfFloat) {
    this.percentOfFloat = percentOfFloat;
  }

  @Override
  public String toString() {
    return "ShortInterest{" +
        "daysToCover=" + daysToCover +
        ", avgDailyVolume=" + avgDailyVolume +
        ", shortInterest=" + shortInterest +
        ", settlementDate='" + settlementDate + '\'' +
        ", percentOfFloat=" + percentOfFloat +
        '}';
  }
}
