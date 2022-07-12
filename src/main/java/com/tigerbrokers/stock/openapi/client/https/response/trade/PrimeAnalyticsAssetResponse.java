package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PrimeAnalyticsAssetItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 *
 * @author bean
 * @date 2022/07/12
 */
public class PrimeAnalyticsAssetResponse extends TigerResponse {
    @JSONField(name = "data")
    private PrimeAnalyticsAssetItem item;

    public PrimeAnalyticsAssetItem getItem() {
        return item;
    }

    public void setItem(PrimeAnalyticsAssetItem item) {
        this.item = item;
    }

    public PrimeAnalyticsAssetItem.Summary getSummary() {
        if (item == null) {
            return null;
        }
        return item.getSummary();
    }

    public List<PrimeAnalyticsAssetItem.HistoryItem> getHistory() {
        if (item == null) {
            return null;
        }
        return item.getHistory();
    }
}
