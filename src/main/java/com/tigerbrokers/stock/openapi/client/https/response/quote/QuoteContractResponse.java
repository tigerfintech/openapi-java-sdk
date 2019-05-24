package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.struct.Contract;
import java.util.List;
import lombok.Data;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
@Data
public class QuoteContractResponse extends TigerResponse {

  @JSONField(name = "data")
  private List<Contract> contractItems;
}
