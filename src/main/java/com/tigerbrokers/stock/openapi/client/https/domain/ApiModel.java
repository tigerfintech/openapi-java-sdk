package com.tigerbrokers.stock.openapi.client.https.domain;

import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import java.io.Serializable;

/**
 * Description:
 * Created by lijiawen on 2018/12/21.
 */
public abstract class ApiModel implements Serializable {

  private static final long serialVersionUID = 1L;

  protected Language lang;

  public Language getLang() {
    return lang;
  }

  public void setLang(Language lang) {
    this.lang = lang;
  }

  public String getAccount() {
    return null;
  }
  public void setAccount(String account) {
  }
}
