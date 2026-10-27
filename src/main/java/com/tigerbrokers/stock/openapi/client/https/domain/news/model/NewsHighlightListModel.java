package com.tigerbrokers.stock.openapi.client.https.domain.news.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * news_highlight_list 请求参数。
 * 翻页只走 last_id 游标（服务端决策 #14 撤除 page_count，调下游固定 pageCount=1）；
 * lang 为两接口共用可选参数，见父类 ApiModel.lang
 * （Language 枚举值 zh_CN/zh_TW/en_US 与服务端值域一致；不设置 = 空串 = 中英混合内容，
 * 服务端对非法值回退 en_US）。
 */
public class NewsHighlightListModel extends ApiModel {

  @JSONField(name = "last_id")
  private String lastId;

  public NewsHighlightListModel(String lastId) {
    this.lastId = lastId;
  }

  public String getLastId() {
    return lastId;
  }

  public void setLastId(String lastId) {
    this.lastId = lastId;
  }
}
