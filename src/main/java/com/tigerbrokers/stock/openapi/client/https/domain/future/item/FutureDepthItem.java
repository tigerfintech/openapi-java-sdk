package com.tigerbrokers.stock.openapi.client.https.domain.future.item;

import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.List;

public class FutureDepthItem extends ApiModel {

  private String contractId;
  private String contractCode;
  private List<FutureDepthAskBidItem> ask;
  private List<FutureDepthAskBidItem> bid;

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public List<FutureDepthAskBidItem> getAsk() {
    return ask;
  }

  public void setAsk(List<FutureDepthAskBidItem> ask) {
    this.ask = ask;
  }

  public List<FutureDepthAskBidItem> getBid() {
    return bid;
  }

  public void setBid(List<FutureDepthAskBidItem> bid) {
    this.bid = bid;
  }
}
