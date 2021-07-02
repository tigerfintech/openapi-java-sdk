package com.tigerbrokers.stock.openapi.client.struct;

import lombok.Data;

/**
 * 作者：ltc
 * 时间：2019/09/04
 */
@Data
public class OptionSymbol {

  private String symbol;
  private String expiry;
  private String strike;
  private String right;
}
