package com.tigerbrokers.stock.openapi.client.struct.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Created by lijiawen on 2022/06/17.
 */
public enum ParticipantCode {
  AMEX("a","NYSE American, LLC (NYSE American)"),
  BX("b", "NASDAQ OMX BX, Inc. (NASDAQ OMX BX)"),
  NSX("c", "NYSE National, Inc. (NYSE National)"),
  ADF("d", "FINRA Alternative Display Facility (ADF)"),
  MIAX("h","MIAX Pearl Exchange, LLC (MIAX)"),
  ISE("i", "International Securities Exchange, LLC (ISE)"),
  EDGA("j","Cboe EDGA Exchange, Inc. (Cboe EDGA)"),
  EDGX("k","Cboe EDGX Exchange, Inc. (Cboe EDGX)"),
  LTSE("l","Long-Term Stock Exchange, Inc. (LTSE)"),
  CHO("m", "NYSE Chicago, Inc. (NYSE Chicago)"),
  NYSE("n","New York Stock Exchange, LLC (NYSE)"),
  ARCA("p","NYSE Arca, Inc. (NYSE Arca)"),
  CTS("s", "Consolidated Tape System (CTS)"),
  NSDQ("t","NASDAQ Stock Market, LLC (NASDAQ)"),
  MEMX("u","Members Exchange, LLC (MEMX)"),
  IEX("v", "Investors’ Exchange, LLC. (IEX)"),
  CBSX("w","CBOE Stock Exchange, Inc. (CBSX)"),
  PSX("x", "NASDAQ OMX PSX, Inc. (NASDAQ OMX PSX)"),
  BYX("y", "Cboe BYX Exchange, Inc. (Cboe BYX)"),
  BZX("z", "Cboe BZX Exchange, Inc. (Cboe BZX)");

  private String code;
  private String name;

  private ParticipantCode(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  private static Map<String, ParticipantCode> partCodeNameMap = new HashMap();

  static {
    for (ParticipantCode item : values()) {
      partCodeNameMap.put(item.getCode(), item);
    }
  }

  public static ParticipantCode getParticipantByCode(String code) {
    if (null == code) {
      return null;
    }
    return partCodeNameMap.get(code);
  }
}

