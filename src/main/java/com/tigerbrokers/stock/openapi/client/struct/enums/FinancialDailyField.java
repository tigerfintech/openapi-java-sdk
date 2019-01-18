package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2019/01/18.
 */
public enum FinancialDailyField {
  tevTotalRev("tevTotalRev", 100061),
  tevEbitda("tevEbitda", 100063),
  tevEbit("tevEbit", 100062),
  mktcapTotalRev("mktcapTotalRev", 100064),
  mktcapEbtExcl("mktcapEbtExcl", 100065),
  tevUfcf("tevUfcf", 102334),
  marketCapLfcf("marketCapLfcf", 102336),
  tev("tev", 100060),
  marketcap("marketcap", 100054),
  sharesoutstanding("sharesoutstanding", 100053);

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
