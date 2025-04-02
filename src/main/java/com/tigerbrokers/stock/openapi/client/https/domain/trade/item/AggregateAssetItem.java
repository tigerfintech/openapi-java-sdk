package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Created on 2025/3/28
 *
 * @author: sukai
 */
public class AggregateAssetItem extends ApiModel {
    private String currency;
    private Double cashBalance;
    private Double cashBalanceWithInTransit;
    private Double equityWithLoan;
    private Double netLiquidation;
    private Double initMargin;
    private Double maintainMargin;
    private Double tradeCurrencyMargin;
    private Double intradayRiskRatio;
    private Double grossPositionValue;
    private Double optionMarketValue;
    private Double stockMarketValue;
    private Double cashAvailableForTrade;
    private Double availableCash;
    private Double lockedFunds;
    private Double lockedCash;
    private Double creditLimit;
    private Double excessEquity;
    private Double excessLiquidity;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(Double cashBalance) {
        this.cashBalance = cashBalance;
    }

    public Double getCashBalanceWithInTransit() {
        return cashBalanceWithInTransit;
    }

    public void setCashBalanceWithInTransit(Double cashBalanceWithInTransit) {
        this.cashBalanceWithInTransit = cashBalanceWithInTransit;
    }

    public Double getEquityWithLoan() {
        return equityWithLoan;
    }

    public void setEquityWithLoan(Double equityWithLoan) {
        this.equityWithLoan = equityWithLoan;
    }

    public Double getNetLiquidation() {
        return netLiquidation;
    }

    public void setNetLiquidation(Double netLiquidation) {
        this.netLiquidation = netLiquidation;
    }

    public Double getInitMargin() {
        return initMargin;
    }

    public void setInitMargin(Double initMargin) {
        this.initMargin = initMargin;
    }

    public Double getMaintainMargin() {
        return maintainMargin;
    }

    public void setMaintainMargin(Double maintainMargin) {
        this.maintainMargin = maintainMargin;
    }

    public Double getTradeCurrencyMargin() {
        return tradeCurrencyMargin;
    }

    public void setTradeCurrencyMargin(Double tradeCurrencyMargin) {
        this.tradeCurrencyMargin = tradeCurrencyMargin;
    }

    public Double getIntradayRiskRatio() {
        return intradayRiskRatio;
    }

    public void setIntradayRiskRatio(Double intradayRiskRatio) {
        this.intradayRiskRatio = intradayRiskRatio;
    }

    public Double getGrossPositionValue() {
        return grossPositionValue;
    }

    public void setGrossPositionValue(Double grossPositionValue) {
        this.grossPositionValue = grossPositionValue;
    }

    public Double getOptionMarketValue() {
        return optionMarketValue;
    }

    public void setOptionMarketValue(Double optionMarketValue) {
        this.optionMarketValue = optionMarketValue;
    }

    public Double getStockMarketValue() {
        return stockMarketValue;
    }

    public void setStockMarketValue(Double stockMarketValue) {
        this.stockMarketValue = stockMarketValue;
    }

    public Double getCashAvailableForTrade() {
        return cashAvailableForTrade;
    }

    public void setCashAvailableForTrade(Double cashAvailableForTrade) {
        this.cashAvailableForTrade = cashAvailableForTrade;
    }

    public Double getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(Double availableCash) {
        this.availableCash = availableCash;
    }

    public Double getLockedFunds() {
        return lockedFunds;
    }

    public void setLockedFunds(Double lockedFunds) {
        this.lockedFunds = lockedFunds;
    }

    public Double getLockedCash() {
        return lockedCash;
    }

    public void setLockedCash(Double lockedCash) {
        this.lockedCash = lockedCash;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getExcessEquity() {
        return excessEquity;
    }

    public void setExcessEquity(Double excessEquity) {
        this.excessEquity = excessEquity;
    }

    public Double getExcessLiquidity() {
        return excessLiquidity;
    }

    public void setExcessLiquidity(Double excessLiquidity) {
        this.excessLiquidity = excessLiquidity;
    }
}
