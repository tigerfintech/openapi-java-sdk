package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 *
 * @author kevin
 * @date 2021/11/30
 */
public class PrimeAssetModel extends ApiModel {
    private String account;

    public PrimeAssetModel(String account) {
        this.account = account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return this.account;
    }
}
