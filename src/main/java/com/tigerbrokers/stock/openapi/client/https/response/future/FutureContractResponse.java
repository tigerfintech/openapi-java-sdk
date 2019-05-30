package com.tigerbrokers.stock.openapi.client.https.response.future;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import lombok.Data;

/**
 * Description:
 * Created by lijiawen on 2018/12/25.
 */
@Data
public class FutureContractResponse extends TigerResponse {

  @JSONField(name = "data")
  private FutureContractItem futureContractItem;
}
