package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/07/10.
 */
public enum AccountStatus implements CodeEnumType {
  New(0), Funded(1), Open(2), Pending(3), Abandoned(4), Rejected(5),
  Closed(6), Unknown(-1);

  private int code;

  AccountStatus(int code) {
    this.code = code;
  }

  @Override
  public int getCode() {
    return code;
  }

}
