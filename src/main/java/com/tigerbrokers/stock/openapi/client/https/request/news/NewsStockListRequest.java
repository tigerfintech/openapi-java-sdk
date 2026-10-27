package com.tigerbrokers.stock.openapi.client.https.request.news;

import com.tigerbrokers.stock.openapi.client.https.domain.news.model.NewsStockListModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.news.NewsStockListResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * 个股新闻列表请求（method=news_stock_list）。
 * symbol 必填（单标的、非空——下游资讯接口实际不支持批量查询）；
 * page_count / page_size 可选（传 null = 服务端默认 1 / 20；page_count >= 1，page_size 1-50）；
 * lang 可选（zh_CN/zh_TW/en_US，传 null = 中英混合内容）。
 */
public class NewsStockListRequest extends TigerCommonRequest
    implements TigerRequest<NewsStockListResponse> {

  public NewsStockListRequest() {
    setApiMethodName(MethodName.NEWS_STOCK_LIST);
  }

  public static NewsStockListRequest newRequest(String symbol, Language lang, Integer pageCount,
      Integer pageSize) {
    NewsStockListRequest request = new NewsStockListRequest();
    NewsStockListModel model = new NewsStockListModel(symbol, pageCount, pageSize);
    model.setLang(lang);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<NewsStockListResponse> getResponseClass() {
    return NewsStockListResponse.class;
  }
}
