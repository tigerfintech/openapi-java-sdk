package com.tigerbrokers.stock.openapi.client.https.domain.future.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

import java.util.List;

public class FutureDepthModel extends ApiModel {

    @JSONField(name = "contract_codes")
    private List<String> contractCodes;

    public FutureDepthModel() {
    }

    public FutureDepthModel(List<String> contractCodes) {
        this.contractCodes = contractCodes;
        this.lang = ClientConfig.DEFAULT_CONFIG.getDefaultLanguage();
    }

    public FutureDepthModel(List<String> contractCodes, Language lang) {
        this.contractCodes = contractCodes;
        this.lang = lang;
    }

    public List<String> getContractCodes() {
        return contractCodes;
    }

    public void setContractCodes(List<String> contractCodes) {
        this.contractCodes = contractCodes;
    }
}
