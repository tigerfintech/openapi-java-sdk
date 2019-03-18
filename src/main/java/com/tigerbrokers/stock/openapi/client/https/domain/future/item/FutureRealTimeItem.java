package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.math.BigDecimal;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FutureRealTimeItem extends ApiModel {

  private String contractCode;
  private BigDecimal latestPrice;
  private Long latestSize;
  private Long latestTime;
  private BigDecimal bidPrice;
  private BigDecimal askPrice;
  private Long bidSize;
  private Long askSize;
  private Long openInterest;
  private Long volume;
  private BigDecimal open;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal settlement;
  private BigDecimal limitUp;
  private BigDecimal limitDown;

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public BigDecimal getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(BigDecimal latestPrice) {
    this.latestPrice = latestPrice;
  }

  public Long getLatestSize() {
    return latestSize;
  }

  public void setLatestSize(Long latestSize) {
    this.latestSize = latestSize;
  }

  public Long getLatestTime() {
    return latestTime;
  }

  public void setLatestTime(Long latestTime) {
    this.latestTime = latestTime;
  }

  public BigDecimal getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(BigDecimal bidPrice) {
    this.bidPrice = bidPrice;
  }

  public BigDecimal getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(BigDecimal askPrice) {
    this.askPrice = askPrice;
  }

  public Long getBidSize() {
    return bidSize;
  }

  public void setBidSize(Long bidSize) {
    this.bidSize = bidSize;
  }

  public Long getAskSize() {
    return askSize;
  }

  public void setAskSize(Long askSize) {
    this.askSize = askSize;
  }

  public Long getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(Long openInterest) {
    this.openInterest = openInterest;
  }

  public Long getVolume() {
    return volume;
  }

  public void setVolume(Long volume) {
    this.volume = volume;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public void setOpen(BigDecimal open) {
    this.open = open;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public void setLow(BigDecimal low) {
    this.low = low;
  }

  public BigDecimal getSettlement() {
    return settlement;
  }

  public void setSettlement(BigDecimal settlement) {
    this.settlement = settlement;
  }

  public BigDecimal getLimitUp() {
    return limitUp;
  }

  public void setLimitUp(BigDecimal limitUp) {
    this.limitUp = limitUp;
  }

  public BigDecimal getLimitDown() {
    return limitDown;
  }

  public void setLimitDown(BigDecimal limitDown) {
    this.limitDown = limitDown;
  }

  @Override
  public String toString() {
    return "FutureRealTimeItem{" +
        "latestPrice=" + latestPrice +
        ", latestSize=" + latestSize +
        ", latestTime=" + latestTime +
        ", bidPrice=" + bidPrice +
        ", askPrice=" + askPrice +
        ", bidSize=" + bidSize +
        ", askSize=" + askSize +
        ", openInterest=" + openInterest +
        ", volume=" + volume +
        ", open=" + open +
        ", high=" + high +
        ", low=" + low +
        ", settlement=" + settlement +
        ", limitUp=" + limitUp +
        ", limitDown=" + limitDown +
        '}';
  }
}
