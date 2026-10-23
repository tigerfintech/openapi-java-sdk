package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.NewsPageItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/** 市场要闻列表响应（method=news_highlight_list）。 */
public class NewsHighlightListResponse extends TigerResponse {

  @JSONField(name = "data")
  private NewsPageItem newsPageItem;

  public NewsPageItem getNewsPageItem() {
    return newsPageItem;
  }

  public void setNewsPageItem(NewsPageItem newsPageItem) {
    this.newsPageItem = newsPageItem;
  }
}
