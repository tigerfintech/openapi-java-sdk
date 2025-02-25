package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;

import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2021/11/30
 */
public class PrimeAssetItem extends ApiModel {

    private String accountId;
    private Long updateTimestamp;
    private List<Segment> segments;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Long updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public static class Segment {

        private String capability;
        private String category;
        private String currency;
        private Double cashBalance;
        private Double cashAvailableForTrade;
        private Double cashAvailableForWithdrawal;
        private Double grossPositionValue;
        private Double equityWithLoan;
        private Double netLiquidation;
        private Double initMargin;
        private Double maintainMargin;
        private Double overnightMargin;
        private Double unrealizedPL;
        private Double unrealizedPLByCostOfCarry;
        private Double realizedPL;
        private Double totalTodayPL;
        private Double excessLiquidation;
        private Double overnightLiquidation;
        private Double buyingPower;
        private Double leverage;
        private Double lockedFunds;
        private Double uncollected;
        private List<CurrencyAssets> currencyAssets;
        private List<String> consolidatedSegTypes;

        public String getCapability() {
            return capability;
        }

        public void setCapability(String capability) {
            this.capability = capability;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

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

        public Double getCashAvailableForTrade() {
            return cashAvailableForTrade;
        }

        public void setCashAvailableForTrade(Double cashAvailableForTrade) {
            this.cashAvailableForTrade = cashAvailableForTrade;
        }

        public Double getCashAvailableForWithdrawal() {
            return cashAvailableForWithdrawal;
        }

        public void setCashAvailableForWithdrawal(Double cashAvailableForWithdrawal) {
            this.cashAvailableForWithdrawal = cashAvailableForWithdrawal;
        }

        public Double getGrossPositionValue() {
            return grossPositionValue;
        }

        public void setGrossPositionValue(Double grossPositionValue) {
            this.grossPositionValue = grossPositionValue;
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

        public Double getOvernightMargin() {
            return overnightMargin;
        }

        public void setOvernightMargin(Double overnightMargin) {
            this.overnightMargin = overnightMargin;
        }

        public Double getUnrealizedPL() {
            return unrealizedPL;
        }

        public void setUnrealizedPL(Double unrealizedPL) {
            this.unrealizedPL = unrealizedPL;
        }

        public Double getRealizedPL() {
            return realizedPL;
        }

        public void setRealizedPL(Double realizedPL) {
            this.realizedPL = realizedPL;
        }

        public Double getExcessLiquidation() {
            return excessLiquidation;
        }

        public void setExcessLiquidation(Double excessLiquidation) {
            this.excessLiquidation = excessLiquidation;
        }

        public Double getOvernightLiquidation() {
            return overnightLiquidation;
        }

        public void setOvernightLiquidation(Double overnightLiquidation) {
            this.overnightLiquidation = overnightLiquidation;
        }

        public Double getBuyingPower() {
            return buyingPower;
        }

        public void setBuyingPower(Double buyingPower) {
            this.buyingPower = buyingPower;
        }

        public Double getLeverage() {
            return leverage;
        }

        public void setLeverage(Double leverage) {
            this.leverage = leverage;
        }

        public List<CurrencyAssets> getCurrencyAssets() {
            return currencyAssets;
        }

        public void setCurrencyAssets(List<CurrencyAssets> currencyAssets) {
            this.currencyAssets = currencyAssets;
        }

        public List<String> getConsolidatedSegTypes() {
            return consolidatedSegTypes;
        }

        public void setConsolidatedSegTypes(List<String> consolidatedSegTypes) {
            this.consolidatedSegTypes = consolidatedSegTypes;
        }

        public Double getUnrealizedPLByCostOfCarry() {
            return unrealizedPLByCostOfCarry;
        }

        public void setUnrealizedPLByCostOfCarry(Double unrealizedPLByCostOfCarry) {
            this.unrealizedPLByCostOfCarry = unrealizedPLByCostOfCarry;
        }

        public Double getTotalTodayPL() {
            return totalTodayPL;
        }

        public void setTotalTodayPL(Double totalTodayPL) {
            this.totalTodayPL = totalTodayPL;
        }

        public Double getLockedFunds() {
            return lockedFunds;
        }

        public void setLockedFunds(Double lockedFunds) {
            this.lockedFunds = lockedFunds;
        }

        public Double getUncollected() {
            return uncollected;
        }

        public void setUncollected(Double uncollected) {
            this.uncollected = uncollected;
        }

        public CurrencyAssets getAssetByCurrency(Currency currency) {
            if (currency == Currency.ALL) {
                return null;
            }
            if (currencyAssets == null) {
                return null;
            }
            for (CurrencyAssets asset : currencyAssets) {
                if (currency.name().equalsIgnoreCase(asset.getCurrency())) {
                    return asset;
                }
            }
            return null;
        }
    }

    public static class CurrencyAssets {

        private String currency;
        private Double cashBalance;
        private Double cashAvailableForTrade;
        private Double grossPositionValue;
        private Double stockMarketValue;
        private Double optionMarketValue;
        private Double futuresMarketValue;
        private Double fundMarketValue;
        private Double unrealizedPL;
        private Double realizedPL;

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

        public Double getCashAvailableForTrade() {
            return cashAvailableForTrade;
        }

        public void setCashAvailableForTrade(Double cashAvailableForTrade) {
            this.cashAvailableForTrade = cashAvailableForTrade;
        }

        public Double getGrossPositionValue() {
            return grossPositionValue;
        }

        public void setGrossPositionValue(Double grossPositionValue) {
            this.grossPositionValue = grossPositionValue;
        }

        public Double getStockMarketValue() {
            return stockMarketValue;
        }

        public void setStockMarketValue(Double stockMarketValue) {
            this.stockMarketValue = stockMarketValue;
        }

        public Double getOptionMarketValue() {
            return optionMarketValue;
        }

        public void setOptionMarketValue(Double optionMarketValue) {
            this.optionMarketValue = optionMarketValue;
        }

        public Double getFuturesMarketValue() {
            return futuresMarketValue;
        }

        public void setFuturesMarketValue(Double futuresMarketValue) {
            this.futuresMarketValue = futuresMarketValue;
        }

        public Double getFundMarketValue() {
            return fundMarketValue;
        }

        public void setFundMarketValue(Double fundMarketValue) {
            this.fundMarketValue = fundMarketValue;
        }

        public Double getUnrealizedPL() {
            return unrealizedPL;
        }

        public void setUnrealizedPL(Double unrealizedPL) {
            this.unrealizedPL = unrealizedPL;
        }

        public Double getRealizedPL() {
            return realizedPL;
        }

        public void setRealizedPL(Double realizedPL) {
            this.realizedPL = realizedPL;
        }
    }
}
