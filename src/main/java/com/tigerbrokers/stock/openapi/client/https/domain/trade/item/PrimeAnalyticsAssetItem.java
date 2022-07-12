package com.tigerbrokers.stock.openapi.client.https.domain.trade.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

/**
 * Description:
 *
 * @author bean
 * @date 2022/07/12
 */
public class PrimeAnalyticsAssetItem extends ApiModel {

    private Summary summary;
    private List<HistoryItem> history;

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<HistoryItem> getHistory() {
        return history;
    }

    public void setHistory(
        List<HistoryItem> history) {
        this.history = history;
    }

    public static class Summary {

        private Double pnl;
        private Double pnlPercentage;
        private Double annualizedReturn;
        private Double overUserPercentage;

        public Double getPnl() {
            return pnl;
        }

        public void setPnl(Double pnl) {
            this.pnl = pnl;
        }

        public Double getPnlPercentage() {
            return pnlPercentage;
        }

        public void setPnlPercentage(Double pnlPercentage) {
            this.pnlPercentage = pnlPercentage;
        }

        public Double getAnnualizedReturn() {
            return annualizedReturn;
        }

        public void setAnnualizedReturn(Double annualizedReturn) {
            this.annualizedReturn = annualizedReturn;
        }

        public Double getOverUserPercentage() {
            return overUserPercentage;
        }

        public void setOverUserPercentage(Double overUserPercentage) {
            this.overUserPercentage = overUserPercentage;
        }
    }

    public static class HistoryItem {

        private Long date;
        private Double asset;
        private Double pnl;
        private Double pnlPercentage;
        private Double cashBalance;
        private Double grossPositionValue;
        private Double deposit;
        private Double withdrawal;

        public Long getDate() {
            return date;
        }

        public void setDate(Long date) {
            this.date = date;
        }

        public Double getAsset() {
            return asset;
        }

        public void setAsset(Double asset) {
            this.asset = asset;
        }

        public Double getPnl() {
            return pnl;
        }

        public void setPnl(Double pnl) {
            this.pnl = pnl;
        }

        public Double getPnlPercentage() {
            return pnlPercentage;
        }

        public void setPnlPercentage(Double pnlPercentage) {
            this.pnlPercentage = pnlPercentage;
        }

        public Double getCashBalance() {
            return cashBalance;
        }

        public void setCashBalance(Double cashBalance) {
            this.cashBalance = cashBalance;
        }

        public Double getGrossPositionValue() {
            return grossPositionValue;
        }

        public void setGrossPositionValue(Double grossPositionValue) {
            this.grossPositionValue = grossPositionValue;
        }

        public Double getDeposit() {
            return deposit;
        }

        public void setDeposit(Double deposit) {
            this.deposit = deposit;
        }

        public Double getWithdrawal() {
            return withdrawal;
        }

        public void setWithdrawal(Double withdrawal) {
            this.withdrawal = withdrawal;
        }
    }
}
