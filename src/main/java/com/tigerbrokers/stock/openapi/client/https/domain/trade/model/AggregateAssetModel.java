package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Created on 2025/3/28
 *
 * @author: sukai
 */
public class AggregateAssetModel extends ApiModel {
    private String account;
    @JSONField(name = "base_currency")
    private String baseCurrency;
    @JSONField(name = "secret_key")
    private String secretKey;
    @JSONField(name = "seg_type")
    private String segType;

    public AggregateAssetModel(String account) {
        this.account = account;
    }

    public AggregateAssetModel(String account, String secretKey) {
        this.account = account;
        this.secretKey = secretKey;
    }

    public AggregateAssetModel(String account, String baseCurrency, String secretKey) {
        this.account = account;
        this.baseCurrency = baseCurrency;
        this.secretKey = secretKey;
    }

    public AggregateAssetModel(String account, String baseCurrency, String secretKey, String segType) {
        this.account = account;
        this.baseCurrency = baseCurrency;
        this.secretKey = secretKey;
        this.segType = segType;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public void setAccount(String account) {
        this.account = account;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSegType() {
        return segType;
    }

    public void setSegType(String segType) {
        this.segType = segType;
    }
}
