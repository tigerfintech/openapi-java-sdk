package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public class MarketScannerBatchItem extends ApiModel {
    /** 请求的页面索引，默认为0 */
    private int page;
    /** 总页数 */
    private int totalPage;
    /** 该条件请求所有数据的个数 */
    private int totalCount;
    /** 每页条数 */
    private int pageSize;
    /** 返回的股票数据列表 */
    private List<MarketScannerItem> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<MarketScannerItem> getItems() {
        return items;
    }

    public void setItems(List<MarketScannerItem> items) {
        this.items = items;
    }
}
