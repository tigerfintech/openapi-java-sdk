package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.enums.AccountType;
import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * Created by lijiawen on 2018/12/03.
 */
@Slf4j
public class AccountUtil {

  public static boolean isOmnibusAccount(String account) {
    if (StringUtils.isEmpty(account)) {
      return false;
    }
    try {
      return StringUtils.isNumeric(account) && account.length() >= 6 && account.length() <= 8;
    } catch (Exception e) {
      log.error("isOmnibusAccount {}", e.getMessage(), e);
      return false;
    }
  }

  public static boolean isGlobalAccount(String account) {
    if (StringUtils.isEmpty(account)) {
      return false;
    }
    if (account.startsWith("U") || account.startsWith("DU") || account.startsWith("F") || account.startsWith("DF")) {
      return true;
    }
    return false;
  }

  public static AccountType getAccountType(String account) {
    if (isGlobalAccount(account)) {
      return AccountType.GLOBAL;
    } else if (isOmnibusAccount(account)) {
      return AccountType.STANDARD;
    } else {
      return AccountType.PAPER;
    }
  }

}
