package com.tigerbrokers.stock.openapi.client.https.response.quote;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.AddonEntitlementItem;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;

/**
 * 附加套餐权益查询响应
 */
public class AddonEntitlementResponse extends TigerResponse {

  @JSONField(name = "data")
  private AddonEntitlementItem addonEntitlementItem;

  public AddonEntitlementItem getAddonEntitlementItem() {
    return addonEntitlementItem;
  }

  public void setAddonEntitlementItem(AddonEntitlementItem addonEntitlementItem) {
    this.addonEntitlementItem = addonEntitlementItem;
  }
}
