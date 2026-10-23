package com.tigerbrokers.stock.openapi.client.https.request.news;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.NewsHighlightListModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.NewsHighlightListResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * 市场要闻列表请求（method=news_highlight_list），无必填参数：
 * 翻页只走 last_id 游标（服务端决策 #14 已撤除 page_count），
 * lastId 取上一页 items 最后一条的 id（传 null 从最新开始）；
 * lang 可选（zh_CN/zh_TW/en_US，传 null = 中英混合内容）。
 */
public class NewsHighlightListRequest extends TigerCommonRequest
    implements TigerRequest<NewsHighlightListResponse> {

  public NewsHighlightListRequest() {
    setApiMethodName(MethodName.NEWS_HIGHLIGHT_LIST);
  }

  public static NewsHighlightListRequest newRequest(Language lang, String lastId) {
    NewsHighlightListRequest request = new NewsHighlightListRequest();
    NewsHighlightListModel model = new NewsHighlightListModel(lastId);
    model.setLang(lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<NewsHighlightListResponse> getResponseClass() {
    return NewsHighlightListResponse.class;
  }
}
