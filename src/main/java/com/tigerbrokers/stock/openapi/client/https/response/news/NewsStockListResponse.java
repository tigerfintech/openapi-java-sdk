package com.tigerbrokers.stock.openapi.client.https.response.news;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsPageItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/** 个股新闻列表响应（method=news_stock_list）。 */
public class NewsStockListResponse extends TigerResponse {

  @JSONField(name = "data")
  private NewsPageItem newsPageItem;

  public NewsPageItem getNewsPageItem() {
    return newsPageItem;
  }

  public void setNewsPageItem(NewsPageItem newsPageItem) {
    this.newsPageItem = newsPageItem;
  }
}
