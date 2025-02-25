package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TradeRankItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

public class QuoteTradeRankResponse extends TigerResponse {

    @JSONField(name = "data")
    private List<TradeRankItem> items;

    public List<TradeRankItem> getItems() {
        return items;
    }

    public void setItems(List<TradeRankItem> items) {
        this.items = items;
    }
} 