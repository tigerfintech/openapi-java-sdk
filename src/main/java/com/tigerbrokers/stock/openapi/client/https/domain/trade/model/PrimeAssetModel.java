package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 *
 * @author kevin
 * @date 2021/11/30
 */
public class PrimeAssetModel extends ApiModel {
    private String account;
    @JSONField(name = "secret_key")
    private String secretKey;

    public PrimeAssetModel(String account) {
        this.account = account;
    }

    public PrimeAssetModel(String account, String secretKey) {
        this.account = account;
        this.secretKey = secretKey;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return this.account;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
