package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

public class TradeRankItem extends ApiModel {
    private Integer page;
    private Integer totalPage; 
    private Integer totalCount;
    private List<TradeRankDetailItem> items;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<TradeRankDetailItem> getItems() {
        return items;
    }

    public void setItems(List<TradeRankDetailItem> items) {
        this.items = items;
    }
} 