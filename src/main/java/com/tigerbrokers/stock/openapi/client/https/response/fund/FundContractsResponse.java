package com.tigerbrokers.stock.openapi.client.https.response.fund;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.fund.item.FundContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
public class FundContractsResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<FundContractItem> fundContractItems;

  public List<FundContractItem> getFundContractItems() {
    return fundContractItems;
  }

  public void setFundContractItems(
      List<FundContractItem> fundContractItems) {
    this.fundContractItems = fundContractItems;
  }
}
