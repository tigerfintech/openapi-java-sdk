package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

public class TradeRankItem extends ApiModel {
    private String symbol;
    private String market;
    private String name;
    private String secType;
    private Double changeRate;
    private Double sellOrderRate;
    private Double buyOrderRate;
    private TradeRankHourTradingItem hourTrading;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public Double getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
    }

    public Double getSellOrderRate() {
        return sellOrderRate;
    }

    public void setSellOrderRate(Double sellOrderRate) {
        this.sellOrderRate = sellOrderRate;
    }

    public Double getBuyOrderRate() {
        return buyOrderRate;
    }

    public void setBuyOrderRate(Double buyOrderRate) {
        this.buyOrderRate = buyOrderRate;
    }

    public TradeRankHourTradingItem getHourTrading() {
        return hourTrading;
    }

    public void setHourTrading(TradeRankHourTradingItem hourTrading) {
        this.hourTrading = hourTrading;
    }
} 