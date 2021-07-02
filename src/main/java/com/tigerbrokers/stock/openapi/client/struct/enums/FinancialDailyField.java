package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public enum FinancialDailyField {
  tevToLtmTotalRevenues("tev_to_ltm_total_revenues", 100061L),
  tevToLtmEbitda("tev_to_ltm_ebitda", 100063L),
  tevToLtmEbit("tev_to_ltm_ebit", 100062L),
  marketCapToLtmTotalRevenues("market_cap_to_ltm_total_revenues", 100064L),
  marketCapToLtmEbtExclUnusualItems("market_cap_to_ltm_ebt_excl_unusual_items", 100065L),
  tevToLtmUnleveredFcf("tev_to_ltm_unlevered_fcf", 102334L),
  marketCapToLtmLeveredFcf("market_cap_to_ltm_levered_fcf", 102336L),
  totalEnterpriseValue("total_enterprise_value", 100060L),
  marketCapitalization("market_capitalization", 100054L),
  sharesOutstanding("shares_outstanding", 100053L);

  private String field;
  private long dataItemId;

  FinancialDailyField(String field, long dataItemId) {
    this.field = field;
    this.dataItemId = dataItemId;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public long getDataItemId() {
    return dataItemId;
  }

  public void setDataItemId(long dataItemId) {
    this.dataItemId = dataItemId;
  }
}
