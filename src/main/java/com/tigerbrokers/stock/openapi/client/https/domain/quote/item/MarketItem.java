package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class MarketItem extends ApiModel {

  /**
   * US:美股，CN:沪深，HK:港股
   **/
  private String market;

  /**
   * 市场状态(未开盘，交易中，休市等）
   **/
  private String marketStatus;

  /**
   * 最近开盘、交易时间  MM-dd HH:mm:ss z
   **/
  private String openTime;

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getMarketStatus() {
    return marketStatus;
  }

  public void setMarketStatus(String marketStatus) {
    this.marketStatus = marketStatus;
  }

  public String getOpenTime() {
    return openTime;
  }

  public void setOpenTime(String openTime) {
    this.openTime = openTime;
  }

  @Override
  public String toString() {
    return "MarketItem{" +
        "market='" + market + '\'' +
        ", marketStatus='" + marketStatus + '\'' +
        ", openTime='" + openTime + '\'' +
        '}';
  }
}
