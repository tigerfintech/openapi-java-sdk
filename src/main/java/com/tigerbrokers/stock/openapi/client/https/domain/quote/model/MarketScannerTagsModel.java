package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MultiTagField;
import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2023/04/19
 */
public class MarketScannerTagsModel extends ApiModel {
    private Market market;
    @JSONField(name = "multi_tag_field_list")
    private List<MultiTagField> multiTagFieldList;

    public MarketScannerTagsModel() {
    }

    public MarketScannerTagsModel(Market market, List<MultiTagField> multiTagFieldList) {
        this.market = market;
        this.multiTagFieldList = multiTagFieldList;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public List<MultiTagField> getMultiTagFieldList() {
        return multiTagFieldList;
    }

    public void setMultiTagFieldList(
        List<MultiTagField> multiTagFieldList) {
        this.multiTagFieldList = multiTagFieldList;
    }
}
