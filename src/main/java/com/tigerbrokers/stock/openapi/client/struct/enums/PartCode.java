package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 * Created by liutongping on 2024/05/23.
 */
public enum PartCode {
  AMEX("a", 0),
  BOX("b", 1),
  CBOE("c", 2),
  EMLD("d", 3),
  EDGX("e", 4),
  GEM("h", 5),
  ISE("i", 6),
  MCRY("j", 7),
  MIAX("m", 8),
  ARCA("n", 9),
  MPRL("p", 10),
  NSDQ("q", 11),
  BX("t", 12),
  C2("w", 13),
  PHLX("x", 14),
  BZX("z", 15);

  static final PartCode[] partCodes = new PartCode[values().length];
  final String code;
  final int index;

  private PartCode(String code, int index) {
    this.code = code;
    this.index = index;
  }

  public int index() {
    return this.index;
  }

  public static PartCode of(String code) {
    if (code != null) {
      PartCode[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
        PartCode c = var1[var3];
        if (c.code.equals(code)) {
          return c;
        }
      }
    }

    return null;
  }

  public static PartCode of(int index) {
    return partCodes[index];
  }

  static {
    PartCode[] var0 = values();
    int var1 = var0.length;

    for(int var2 = 0; var2 < var1; ++var2) {
      PartCode pc = var0[var2];
      partCodes[pc.index] = pc;
    }

  }
}
