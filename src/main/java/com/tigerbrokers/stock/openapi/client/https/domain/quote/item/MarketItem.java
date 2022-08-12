package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class MarketItem extends ApiModel {

  /**
   * US:US Stock，CN:China Stock，HK:Hong Kong Stock
   **/
  private String market;

  /**
   * Market status，including：NOT_YET_OPEN:未开盘,PRE_HOUR_TRADING:盘前交易,TRADING:交易中,MIDDLE_CLOSE:午间休市,POST_HOUR_TRADING:盘后交易,CLOSING:已收盘,EARLY_CLOSED:提前休市,MARKET_CLOSED:休市
   **/
  private String status;

  /**
   * Market status description(未开盘，交易中，休市等）
   **/
  private String marketStatus;

  /**
   * market latest open time、trading time  MM-dd HH:mm:ss z
   **/
  private String openTime;

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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
        ", status='" + status + '\'' +
        ", marketStatus='" + marketStatus + '\'' +
        ", openTime='" + openTime + '\'' +
        '}';
  }
}
