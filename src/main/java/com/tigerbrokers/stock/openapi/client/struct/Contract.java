package com.tigerbrokers.stock.openapi.client.struct;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contract implements Serializable{
  private Integer conid;
  private String stockId;
  private String identifier;
  private String symbol;
  private String secType;
  private String expiry;
  private String contractMonth;
  private Double strike;
  private String right;
  private Double multiplier;
  private String exchange;
  private String market;
  private String primaryExch;
  private String currency;
  private String localSymbol;
  private String tradingClass;
  private String name;
  private boolean forexConversion;
  private int status;
  private Double minTick;

  private String lastTradingDate;
  private String firstNoticeDate;
  private Long lastBiddingCloseTime;
  private boolean continuous;
}
