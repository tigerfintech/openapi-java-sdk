package com.tigerbrokers.stock.openapi.client.https.response.contract;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.List;
import lombok.Data;

/**
 * 用于接受账户api合约接口的返回值
 * 作者：ltc
 * 时间：2019/05/29
 */
@Data
public class ContractResponse extends TigerResponse {
  @JSONField(serialize = false)
  private String data;

  private ContractItem item;

  /** 兼容V2.0接口查询Warrant和CBBC的返回 */
  @JSONField(serialize = false)
  private List<ContractItem> items;
}
