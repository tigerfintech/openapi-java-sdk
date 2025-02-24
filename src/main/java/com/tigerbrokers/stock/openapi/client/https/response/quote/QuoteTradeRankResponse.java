package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.TradeRankItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

public class QuoteTradeRankResponse extends TigerResponse {

    @JSONField(name = "data")
    private TradeRankItem tradeRankItem;

    public TradeRankItem getTradeRankItem() {
        return tradeRankItem;
    }

    public void setTradeRankItem(TradeRankItem tradeRankItem) {
        this.tradeRankItem = tradeRankItem;
    }
} 