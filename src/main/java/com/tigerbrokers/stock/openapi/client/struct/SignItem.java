package com.tigerbrokers.stock.openapi.client.struct;

import java.io.Serializable;

/**
 * Created by cheneg on 2015/9/10.
 */
public class SignItem implements Serializable {

  private static final long serialVersionUID = 6531196726066096786L;

  public SignItem(String signSource, String sign) {
    this.signSource = signSource;
    this.sign = sign;
  }

  /**
   * signature source string
   */
  private String signSource;

  /**
   * signature content
   */
  private String sign;

  public String getSignSource() {
    return signSource;
  }

  public void setSignSource(String signSource) {
    this.signSource = signSource;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }
}
