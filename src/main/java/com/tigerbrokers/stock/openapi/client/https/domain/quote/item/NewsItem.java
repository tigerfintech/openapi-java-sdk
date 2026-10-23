package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

/**
 * 资讯条目，news_stock_list 与 news_highlight_list 共用。
 * 服务端响应为 camelCase，字段名与 JSON key 相同，无需 @JSONField。
 * pubTime（格式化字符串）暂不透出，发布时间用 pubTimestamp 表达。
 */
public class NewsItem {

  private String id;

  private String title;

  private String summary;

  /** 发布时间戳（秒级，服务端 Long 返回） */
  private Long pubTimestamp;

  private String url;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public Long getPubTimestamp() {
    return pubTimestamp;
  }

  public void setPubTimestamp(Long pubTimestamp) {
    this.pubTimestamp = pubTimestamp;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "NewsItem{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", summary='" + summary + '\'' +
        ", pubTimestamp=" + pubTimestamp +
        ", url='" + url + '\'' +
        '}';
  }
}
