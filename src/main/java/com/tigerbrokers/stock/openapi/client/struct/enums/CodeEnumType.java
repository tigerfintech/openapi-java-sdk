package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by lijiawen on 2018/08/23.
 */
public interface CodeEnumType {

  int getCode();

  static <E extends Enum<?> & CodeEnumType> E codeOf(Class<E> enumClazz, int code) {
    E[] enums = enumClazz.getEnumConstants();
    for (E e : enums) {
      if (e.getCode() == code) {
        return e;
      }
    }
    return null;
  }
}
