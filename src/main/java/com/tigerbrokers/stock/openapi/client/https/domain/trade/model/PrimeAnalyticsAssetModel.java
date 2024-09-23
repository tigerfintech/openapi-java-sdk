package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/07/12
 */
public class PrimeAnalyticsAssetModel extends ApiModel {
    private String account;
    @JSONField(name = "start_date")
    private String startDate;
    @JSONField(name = "end_date")
    private String endDate;
    @JSONField(name = "seg_type")
    private SegmentType segType;
    private Currency currency;
    @JSONField(name = "sub_account")
    private String subAccount;
    @JSONField(name = "secret_key")
    private String secretKey;

    public PrimeAnalyticsAssetModel(String account) {
        this.account = account;
    }

    public PrimeAnalyticsAssetModel(String account, String secretKey) {
        this.account = account;
        this.secretKey = secretKey;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return this.account;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public SegmentType getSegType() {
        return segType;
    }

    public void setSegType(SegmentType segType) {
        this.segType = segType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "PrimeAnalyticsAssetModel{" +
            "account=" + account +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", secretKey='" + secretKey + '\'' +
            ", segType=" + segType +
            ", currency=" + currency +
            ", subAccount=" + subAccount +
            '}';
    }
}
