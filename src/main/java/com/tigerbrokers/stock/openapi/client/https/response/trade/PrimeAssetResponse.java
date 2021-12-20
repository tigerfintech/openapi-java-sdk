package com.tigerbrokers.stock.openapi.client.https.response.trade;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PrimeAssetItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Category;

import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2021/11/30
 */
public class PrimeAssetResponse extends TigerResponse {
    @JSONField(name = "data")
    private PrimeAssetItem item;

    public PrimeAssetItem getItem() {
        return item;
    }

    public void setItem(PrimeAssetItem item) {
        this.item = item;
    }

    public PrimeAssetItem.Segment getSegment(Category category) {
        if (item == null) {
            return null;
        }
        List<PrimeAssetItem.Segment> segments = item.getSegments();
        if (segments == null) {
            return null;
        }
        for (PrimeAssetItem.Segment segment : segments) {
            if (category.name().equalsIgnoreCase(segment.getCategory())) {
                return segment;
            }
        }
        return null;
    }
}
