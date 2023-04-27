package com.tigerbrokers.stock.openapi.client.https.domain.contract.item;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureContractItem;
import com.tigerbrokers.stock.openapi.client.struct.OptionSymbol;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.util.FastJsonBooleanDeserializer;
import com.tigerbrokers.stock.openapi.client.util.SymbolUtil;
import java.util.List;

public class ContractItem extends ApiModel {

  private Integer contractId;
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
  private String primaryExchange;
  private String currency;
  private String localSymbol;
  private String tradingClass;
  private String name;
  private Boolean tradeable;
  private Boolean closeOnly;
  private Double minTick;
  private Boolean marginable;
  private Double shortInitialMargin;
  private Double shortMaintenanceMargin;
  private Double shortFeeRate;
  @JSONField(deserializeUsing = FastJsonBooleanDeserializer.class)
  private Boolean shortable;
  private Long shortableCount;
  private Double longInitialMargin;
  private Double longMaintenanceMargin;
  private String lastTradingDate;
  private String firstNoticeDate;
  private Long lastBiddingCloseTime;
  private Boolean continuous;
  /** future contract fields */
  private String type;
  private String ibCode;
  private List<TickSizeItem> tickSizes;
  private Boolean isEtf;
  private Integer etfLeverage;
  /** Intraday initial margin discount */
  private Double discountedDayInitialMargin;
  /** Intraday maintenance margin discount */
  private Double discountedDayMaintenanceMargin;
  /** Intraday margin discount period time zone  */
  private String discountedTimeZoneCode;
  private String discountedStartAt;
  private String discountedEndAt;

  public Integer getContractId() {
    return contractId;
  }

  public void setContractId(Integer contractId) {
    this.contractId = contractId;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getSecType() {
    return secType;
  }

  public void setSecType(String secType) {
    this.secType = secType;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public String getContractMonth() {
    return contractMonth;
  }

  public void setContractMonth(String contractMonth) {
    this.contractMonth = contractMonth;
  }

  public Double getStrike() {
    return strike;
  }

  public void setStrike(Double strike) {
    this.strike = strike;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  public Double getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(Double multiplier) {
    this.multiplier = multiplier;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getPrimaryExchange() {
    return primaryExchange;
  }

  public void setPrimaryExchange(String primaryExchange) {
    this.primaryExchange = primaryExchange;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getLocalSymbol() {
    return localSymbol;
  }

  public void setLocalSymbol(String localSymbol) {
    this.localSymbol = localSymbol;
  }

  public String getTradingClass() {
    return tradingClass;
  }

  public void setTradingClass(String tradingClass) {
    this.tradingClass = tradingClass;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean isTradeable() {
    return tradeable;
  }

  public void setTradeable(Boolean tradeable) {
    this.tradeable = tradeable;
  }

  public Boolean isCloseOnly() {
    return closeOnly;
  }

  public void setCloseOnly(Boolean closeOnly) {
    this.closeOnly = closeOnly;
  }

  public Double getMinTick() {
    return minTick;
  }

  public void setMinTick(Double minTick) {
    this.minTick = minTick;
  }

  public Boolean isMarginable() {
    return marginable;
  }

  public void setMarginable(Boolean marginable) {
    this.marginable = marginable;
  }

  public Double getShortInitialMargin() {
    return shortInitialMargin;
  }

  public void setShortInitialMargin(Double shortInitialMargin) {
    this.shortInitialMargin = shortInitialMargin;
  }

  public Double getShortMaintenanceMargin() {
    return shortMaintenanceMargin;
  }

  public void setShortMaintenanceMargin(Double shortMaintenanceMargin) {
    this.shortMaintenanceMargin = shortMaintenanceMargin;
  }

  public Double getShortFeeRate() {
    return shortFeeRate;
  }

  public void setShortFeeRate(Double shortFeeRate) {
    this.shortFeeRate = shortFeeRate;
  }

  public Boolean isShortable() {
    return shortable;
  }

  public void setShortable(Boolean shortable) {
    this.shortable = shortable;
  }

  public Long getShortableCount() {
    return shortableCount;
  }

  public void setShortableCount(Long shortableCount) {
    this.shortableCount = shortableCount;
  }

  public Double getLongInitialMargin() {
    return longInitialMargin;
  }

  public void setLongInitialMargin(Double longInitialMargin) {
    this.longInitialMargin = longInitialMargin;
  }

  public Double getLongMaintenanceMargin() {
    return longMaintenanceMargin;
  }

  public void setLongMaintenanceMargin(Double longMaintenanceMargin) {
    this.longMaintenanceMargin = longMaintenanceMargin;
  }

  public String getLastTradingDate() {
    return lastTradingDate;
  }

  public void setLastTradingDate(String lastTradingDate) {
    this.lastTradingDate = lastTradingDate;
  }

  public String getFirstNoticeDate() {
    return firstNoticeDate;
  }

  public void setFirstNoticeDate(String firstNoticeDate) {
    this.firstNoticeDate = firstNoticeDate;
  }

  public Long getLastBiddingCloseTime() {
    return lastBiddingCloseTime;
  }

  public void setLastBiddingCloseTime(Long lastBiddingCloseTime) {
    this.lastBiddingCloseTime = lastBiddingCloseTime;
  }

  public Boolean isContinuous() {
    return continuous;
  }

  public void setContinuous(Boolean continuous) {
    this.continuous = continuous;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getIbCode() {
    return ibCode;
  }

  public void setIbCode(String ibCode) {
    this.ibCode = ibCode;
  }

  public List<TickSizeItem> getTickSizes() {
    return tickSizes;
  }

  public void setTickSizes(
      List<TickSizeItem> tickSizes) {
    this.tickSizes = tickSizes;
  }

  public Boolean isEtf() {
    return isEtf;
  }

  public Boolean getEtf() {
    return isEtf;
  }

  public void setEtf(Boolean etf) {
    isEtf = etf;
  }

  public Integer getEtfLeverage() {
    return etfLeverage;
  }

  public void setEtfLeverage(Integer etfLeverage) {
    this.etfLeverage = etfLeverage;
  }

  public Double getDiscountedDayInitialMargin() {
    return discountedDayInitialMargin;
  }

  public void setDiscountedDayInitialMargin(Double discountedDayInitialMargin) {
    this.discountedDayInitialMargin = discountedDayInitialMargin;
  }

  public Double getDiscountedDayMaintenanceMargin() {
    return discountedDayMaintenanceMargin;
  }

  public void setDiscountedDayMaintenanceMargin(Double discountedDayMaintenanceMargin) {
    this.discountedDayMaintenanceMargin = discountedDayMaintenanceMargin;
  }

  public String getDiscountedTimeZoneCode() {
    return discountedTimeZoneCode;
  }

  public void setDiscountedTimeZoneCode(String discountedTimeZoneCode) {
    this.discountedTimeZoneCode = discountedTimeZoneCode;
  }

  public String getDiscountedStartAt() {
    return discountedStartAt;
  }

  public void setDiscountedStartAt(String discountedStartAt) {
    this.discountedStartAt = discountedStartAt;
  }

  public String getDiscountedEndAt() {
    return discountedEndAt;
  }

  public void setDiscountedEndAt(String discountedEndAt) {
    this.discountedEndAt = discountedEndAt;
  }

  @Override
  public String toString() {
    return "ContractItem{" +
            "contractId=" + contractId +
            ", identifier='" + identifier + '\'' +
            ", symbol='" + symbol + '\'' +
            ", secType='" + secType + '\'' +
            ", expiry='" + expiry + '\'' +
            ", contractMonth='" + contractMonth + '\'' +
            ", strike=" + strike +
            ", right='" + right + '\'' +
            ", multiplier=" + multiplier +
            ", exchange='" + exchange + '\'' +
            ", market='" + market + '\'' +
            ", primaryExchange='" + primaryExchange + '\'' +
            ", currency='" + currency + '\'' +
            ", localSymbol='" + localSymbol + '\'' +
            ", tradingClass='" + tradingClass + '\'' +
            ", name='" + name + '\'' +
            ", tradeable=" + tradeable +
            ", closeOnly=" + closeOnly +
            ", minTick=" + minTick +
            ", marginable=" + marginable +
            ", shortInitialMargin=" + shortInitialMargin +
            ", shortMaintenanceMargin=" + shortMaintenanceMargin +
            ", shortFeeRate=" + shortFeeRate +
            ", shortable=" + shortable +
            ", shortableCount=" + shortableCount +
            ", longInitialMargin=" + longInitialMargin +
            ", longMaintenanceMargin=" + longMaintenanceMargin +
            ", lastTradingDate='" + lastTradingDate + '\'' +
            ", firstNoticeDate='" + firstNoticeDate + '\'' +
            ", lastBiddingCloseTime=" + lastBiddingCloseTime +
            ", continuous=" + continuous +
            ", type='" + type + '\'' +
            ", ibCode='" + ibCode + '\'' +
            ", tickSizes=" + tickSizes +
            ", isEtf=" + isEtf +
            ", etfLeverage=" + etfLeverage +
            '}';
  }

  public static ContractItem convert(FutureContractItem futureContractItem) {
    ContractItem contractItem = new ContractItem();
    contractItem.setSecType(SecType.FUT.name());
    contractItem.setSymbol(futureContractItem.getContractCode());
    contractItem.setType(futureContractItem.getType());
    contractItem.setIbCode(futureContractItem.getIbCode());
    contractItem.setName(futureContractItem.getName());
    contractItem.setContractMonth(futureContractItem.getContractMonth());
    contractItem.setExchange(futureContractItem.getExchangeCode());
    contractItem.setMultiplier(futureContractItem.getMultiplier() == null ? null : futureContractItem.getMultiplier().doubleValue());
    contractItem.setMinTick(futureContractItem.getMinTick() == null ? null : futureContractItem.getMinTick().doubleValue());

    contractItem.setExpiry(futureContractItem.getLastTradingDate());
    contractItem.setFirstNoticeDate(futureContractItem.getFirstNoticeDate());
    contractItem.setLastBiddingCloseTime(futureContractItem.getLastBiddingCloseTime());
    contractItem.setCurrency(futureContractItem.getCurrency());
    contractItem.setTradeable(futureContractItem.isTrade());
    contractItem.setContinuous(futureContractItem.isContinuous());
    return contractItem;
  }

  public static ContractItem buildStockContract(String symbol, String currency) {
    ContractItem contractItem = new ContractItem();
    contractItem.setSecType(SecType.STK.name());
    contractItem.setSymbol(symbol);
    contractItem.setCurrency(currency);
    return contractItem;
  }

  public static ContractItem buildOptionContract(String identifier) throws TigerApiException {
    ContractItem contractItem = new ContractItem();
    contractItem.setSecType(SecType.OPT.name());
    // identifier='AAPL  190118P00160000'
    OptionSymbol optionSymbol = SymbolUtil.convertToOptionSymbolObject(identifier);
    contractItem.setSymbol(optionSymbol.getSymbol());
    contractItem.setExpiry(optionSymbol.getExpiry());
    contractItem.setStrike(Double.parseDouble(optionSymbol.getStrike()));
    contractItem.setRight(optionSymbol.getRight());
    return contractItem;
  }

  public static ContractItem buildOptionContract(String symbol, String expiry, Double strike, String right) {
    ContractItem contractItem = new ContractItem();
    contractItem.setSecType(SecType.OPT.name());
    contractItem.setSymbol(symbol);
    contractItem.setExpiry(expiry);
    contractItem.setStrike(strike);
    contractItem.setRight(right);
    return contractItem;
  }

  public static ContractItem buildWarrantContract(String symbol, String expiry, Double strike, String right) {
    ContractItem contractItem = new ContractItem();
    contractItem.setSecType(SecType.WAR.name());
    contractItem.setSymbol(symbol);
    contractItem.setExpiry(expiry);
    contractItem.setStrike(strike);
    contractItem.setRight(right);
    contractItem.setLocalSymbol(symbol);
    contractItem.setCurrency(Currency.HKD.name());
    contractItem.setMarket(Market.HK.name());
    return contractItem;

  }

  public static ContractItem buildCbbcContract(String symbol, String expiry, Double strike, String right) {
    ContractItem contractItem = new ContractItem();
    contractItem.setSecType(SecType.IOPT.name());
    contractItem.setSymbol(symbol);
    contractItem.setExpiry(expiry);
    contractItem.setStrike(strike);
    contractItem.setRight(right);
    contractItem.setLocalSymbol(symbol);
    contractItem.setCurrency(Currency.HKD.name());
    contractItem.setMarket(Market.HK.name());
    return contractItem;
  }

  public static ContractItem buildFutureContract(String symbol, String currency, String exchange,
      String expiry, Double multiplier) {
    ContractItem contractItem = new ContractItem();
    contractItem.setSecType(SecType.FUT.name());
    contractItem.setSymbol(symbol);
    contractItem.setCurrency(currency);
    contractItem.setExchange(exchange);
    contractItem.setExpiry(expiry);
    contractItem.setMultiplier(multiplier);
    return contractItem;
  }

  public static ContractItem buildFutureContract(String symbol, String currency) {
    ContractItem contractItem = new ContractItem();
    contractItem.setSecType(SecType.FUT.name());
    contractItem.setSymbol(symbol);
    contractItem.setCurrency(currency);
    return contractItem;
  }

  public static ContractItem convertFromJson(String data) {
    if (data.startsWith("{\"items\":")) {
      data = data.substring("{\"items\":".length(), data.length() - 1);
      List<ContractItem> items = JSON.parseObject(data, new TypeReference<List<ContractItem>>() {
      });
      if (items == null || items.size() == 0) {
        return new ContractItem();
      }
      return items.get(0);
    } else {
      return JSON.parseObject(data, ContractItem.class);
    }
  }

  public static List<ContractItem> convertFromJsonV2(String data) {
    if (data.startsWith("{\"items\":")) {
      data = data.substring("{\"items\":" .length(), data.length() - 1);
      List<ContractItem> items = JSON.parseObject(data, new TypeReference<List<ContractItem>>() {
      });
      return items;
    }
    return null;
  }
}
