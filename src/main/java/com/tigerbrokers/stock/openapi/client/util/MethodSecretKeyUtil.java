package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.EnumSet;

public final class MethodSecretKeyUtil {

  private static final EnumSet<MethodName> NEED_SECRET_KEY_METHODS =
      EnumSet.of(
          MethodName.POSITION_TRANSFER,
          MethodName.POSITION_TRANSFER_RECORDS,
          MethodName.POSITION_TRANSFER_DETAIL,
          MethodName.POSITION_TRANSFER_EXTERNAL_RECORDS
      );

  private MethodSecretKeyUtil() {
  }

  public static boolean needSecretKey(MethodName methodName) {
    if (methodName == null) {
      return false;
    }
    return NEED_SECRET_KEY_METHODS.contains(methodName);
  }
}
