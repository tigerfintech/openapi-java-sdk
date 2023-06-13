package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;

/**
 * Description:
 * Created by bean on 2023/06/13.
 */
public class KlineQuotaModel extends ApiModel {

  @JSONField(name = "with_details")
  private Boolean withDetails;

  public KlineQuotaModel() {
  }

  public KlineQuotaModel(Boolean withDetails) {
    this.withDetails = withDetails;
  }

  public Boolean getWithDetails() {
    return withDetails;
  }

  public void setWithDetails(Boolean withDetails) {
    this.withDetails = withDetails;
  }
}
