package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.MarketScannerBatchItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public class MarketScannerResponse extends TigerResponse {
    @JSONField(name = "data")
    private MarketScannerBatchItem marketScannerBatchItem;

    public MarketScannerBatchItem getMarketScannerBatchItem() {
        return marketScannerBatchItem;
    }

    public void setMarketScannerBatchItem(MarketScannerBatchItem marketScannerBatchItem) {
        this.marketScannerBatchItem = marketScannerBatchItem;
    }
}
