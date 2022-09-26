package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.struct.enums.AccountType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.ACCOUNT;

/**
 * Description:
 * Created by lijiawen on 2018/12/03.
 */
public class AccountUtil {

  private static final Logger log = LoggerFactory.getLogger(AccountUtil.class);

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

  public static String parseAccount(TigerRequest request) {
    if (null == request) {
      return null;
    }
    String account;
    if (request instanceof TigerHttpRequest) {
      String bizContent = ((TigerHttpRequest) request).getBizContent();
      account = (String)JSONObject.parseObject(bizContent).get(ACCOUNT);
    } else {
      ApiModel apiModel = request.getApiModel();
      if (apiModel instanceof BatchApiModel) {
        apiModel = (ApiModel)((BatchApiModel) apiModel).getItems().get(0);
      }
      account = apiModel.getAccount();
    }
    return account;
  }
}
