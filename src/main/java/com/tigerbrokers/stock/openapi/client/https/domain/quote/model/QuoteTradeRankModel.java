package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

public class QuoteTradeRankModel extends ApiModel {
    private Market market;

    public QuoteTradeRankModel() {
    }

    public QuoteTradeRankModel(Market market, Language lang) {
        this.market = market;
        this.lang = lang;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }
} 