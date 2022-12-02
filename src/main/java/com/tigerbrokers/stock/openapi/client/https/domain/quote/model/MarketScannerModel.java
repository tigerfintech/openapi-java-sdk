package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public class MarketScannerModel extends ApiModel {
    private Market market;
    @JSONField(name = "base_filter_list")
    private List<BaseFilter> baseFilterList;
    @JSONField(name = "accumulate_filter_list")
    private List<AccumulateFilter> accumulateFilterList;
    @JSONField(name = "financial_filter_list")
    private List<FinancialFilter> financialFilterList;
    @JSONField(name = "multi_tags_filter_list")
    private List<MultiTagsRelationFilter> multiTagsFilterList;
    @JSONField(name = "sort_field_data")
    private SortFieldData sortFieldData;
    private int page;
    @JSONField(name = "page_size")
    private int pageSize;

    public MarketScannerModel() {
    }

    public MarketScannerModel(Market market, List<BaseFilter> baseFilterList,
                              List<AccumulateFilter> accumulateFilterList, List<FinancialFilter> financialFilterList,
                              List<MultiTagsRelationFilter> multiTagsFilterList, SortFieldData sortFieldData, int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        this.market = market;
        this.baseFilterList = baseFilterList;
        this.accumulateFilterList = accumulateFilterList;
        this.financialFilterList = financialFilterList;
        this.multiTagsFilterList = multiTagsFilterList;
        this.sortFieldData = sortFieldData;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public List<BaseFilter> getBaseFilterList() {
        return baseFilterList;
    }

    public void setBaseFilterList(List<BaseFilter> baseFilterList) {
        this.baseFilterList = baseFilterList;
    }

    public List<AccumulateFilter> getAccumulateFilterList() {
        return accumulateFilterList;
    }

    public void setAccumulateFilterList(List<AccumulateFilter> accumulateFilterList) {
        this.accumulateFilterList = accumulateFilterList;
    }

    public List<FinancialFilter> getFinancialFilterList() {
        return financialFilterList;
    }

    public void setFinancialFilterList(List<FinancialFilter> financialFilterList) {
        this.financialFilterList = financialFilterList;
    }

    public List<MultiTagsRelationFilter> getMultiTagsFilterList() {
        return multiTagsFilterList;
    }

    public void setMultiTagsFilterList(List<MultiTagsRelationFilter> multiTagsFilterList) {
        this.multiTagsFilterList = multiTagsFilterList;
    }

    public SortFieldData getSortFieldData() {
        return sortFieldData;
    }

    public void setSortFieldData(SortFieldData sortFieldData) {
        this.sortFieldData = sortFieldData;
    }
}
