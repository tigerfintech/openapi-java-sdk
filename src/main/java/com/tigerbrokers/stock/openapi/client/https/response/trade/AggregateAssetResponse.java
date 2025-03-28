package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.AggregateAssetItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class AggregateAssetResponse extends TigerResponse {
    @JSONField(name = "data")
    private AggregateAssetItem item;

    public AggregateAssetItem getItem() {
        return item;
    }

    public void setItem(AggregateAssetItem item) {
        this.item = item;
    }
}
