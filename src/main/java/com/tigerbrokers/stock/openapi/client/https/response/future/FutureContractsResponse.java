package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/08/10
 */
public class FutureContractsResponse extends TigerResponse {
    @JSONField(name = "data")
    private List<FutureContractItem> futureContractItems;

    public List<FutureContractItem> getFutureContractItems() {
        return futureContractItems;
    }

    public void setFutureContractItems(List<FutureContractItem> futureContractItems) {
        this.futureContractItems = futureContractItems;
    }
}
