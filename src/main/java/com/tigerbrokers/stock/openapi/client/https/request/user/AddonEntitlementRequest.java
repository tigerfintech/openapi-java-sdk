package com.tigerbrokers.stock.openapi.client.https.request.user;

import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.AddonEntitlementResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * 附加套餐权益查询请求
 */
public class AddonEntitlementRequest extends TigerCommonRequest implements TigerRequest<AddonEntitlementResponse> {

  public AddonEntitlementRequest() {
    setApiMethodName(MethodName.ADDON_ENTITLEMENTS);
  }

  public static AddonEntitlementRequest newRequest() {
    return new AddonEntitlementRequest();
  }

  @Override
  public Class<AddonEntitlementResponse> getResponseClass() {
    return AddonEntitlementResponse.class;
  }
}
