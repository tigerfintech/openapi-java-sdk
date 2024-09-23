package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by bean on 2022/09/09.
 */
public enum License {
  TBNZ, TBSG, TBHK, TBAU;

  public static License getLicense(String name) {
    if (name == null || name.isEmpty()) {
      return null;
    }
    try {
      return License.valueOf(name);
    } catch (Throwable th) {
    }
    return null;
  }
}
