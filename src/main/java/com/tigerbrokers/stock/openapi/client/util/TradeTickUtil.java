package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.TradeTick;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.TradeTickData;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: Created by bean on 2022/06/17
 */
public class TradeTickUtil {

  private TradeTickUtil(){}

  private static final Map<String, String> PART_CODE_NAME_MAP = initPartCodeNameMap();

  private static final Map<String, String> PART_CODE_SHORT_NAME_MAP = initPartCodeShortNameMap();

  private static final Map<Character, String> usTradeCondMap = initUsTradeCondMap();

  private static final Map<Character, String> hkTradeCondMap = initHkTradeCondMap();

  private static Map<String, String> initPartCodeNameMap() {
    Map<String, String> map = new HashMap<>();
    map.put("a", "NYSE American, LLC (NYSE American)");
    map.put("b", "NASDAQ OMX BX, Inc. (NASDAQ OMX BX)");
    map.put("c", "NYSE National, Inc. (NYSE National)");
    map.put("d", "FINRA Alternative Display Facility (ADF)");
    map.put("h", "MIAX Pearl Exchange, LLC (MIAX)");
    map.put("i", "International Securities Exchange, LLC (ISE)");
    map.put("j", "Cboe EDGA Exchange, Inc. (Cboe EDGA)");
    map.put("k", "Cboe EDGX Exchange, Inc. (Cboe EDGX)");
    map.put("l", "Long-Term Stock Exchange, Inc. (LTSE)");
    map.put("m", "NYSE Chicago, Inc. (NYSE Chicago)");
    map.put("n", "New York Stock Exchange, LLC (NYSE)");
    map.put("p", "NYSE Arca, Inc. (NYSE Arca)");
    map.put("s", "Consolidated Tape System (CTS)");
    map.put("t", "NASDAQ Stock Market, LLC (NASDAQ)");
    map.put("u", "Members Exchange, LLC (MEMX)");
    map.put("v", "Investors’ Exchange, LLC. (IEX)");
    map.put("w", "CBOE Stock Exchange, Inc. (CBSX)");
    map.put("x", "NASDAQ OMX PSX, Inc. (NASDAQ OMX PSX)");
    map.put("y", "Cboe BYX Exchange, Inc. (Cboe BYX)");
    map.put("z", "Cboe BZX Exchange, Inc. (Cboe BZX)");
    return Collections.unmodifiableMap(map);
  }

  private static Map<String, String> initPartCodeShortNameMap() {
    Map<String, String> map = new HashMap<>();
    map.put("a", "AMEX");
    map.put("b", "BX");
    map.put("c", "NSX");
    map.put("d", "ADF");
    map.put("h", "MIAX");
    map.put("i", "ISE");
    map.put("j", "EDGA");
    map.put("k", "EDGX");
    map.put("l", "LTSE");
    map.put("m", "CHO");
    map.put("n", "NYSE");
    map.put("p", "ARCA");
    map.put("s", "CTS");
    map.put("t", "NSDQ");
    map.put("u", "MEMX");
    map.put("v", "IEX");
    map.put("w", "CBSX");
    map.put("x", "PSX");
    map.put("y", "BYX");
    map.put("z", "BZX");
    return Collections.unmodifiableMap(map);
  }

  private static Map<Character, String> initUsTradeCondMap() {
    Map<Character, String> map = new HashMap<>();
    map.put(' ', "US_REGULAR_SALE"); // 自动对盘
    map.put('B', "US_BUNCHED_TRADE");     // 批量交易
    map.put('C', "US_CASH_TRADE");        // 现金交易
    map.put('F', "US_INTERMARKET_SWEEP"); // 跨市场交易
    map.put('G', "US_BUNCHED_SOLD_TRADE");// 批量卖出
    map.put('H', "US_PRICE_VARIATION_TRADE");// 离价交易
    map.put('I', "US_ODD_LOT_TRADE");        // 碎股交易
    map.put('K', "US_RULE_127_OR_155_TRADE");// 纽交所 第127条交易 或 第155条交易
    map.put('L', "US_SOLD_LAST");            // 延迟交易
    map.put('M', "US_MARKET_CENTER_CLOSE_PRICE");  // 中央收市价
    map.put('N', "US_NEXT_DAY_TRADE");             // 隔日交易
    map.put('O', "US_MARKET_CENTER_OPENING_TRADE");// 中央开盘价交易
    map.put('P', "US_PRIOR_REFERENCE_PRICE");      // 前参考价
    map.put('Q', "US_MARKET_CENTER_OPEN_PRICE");   // 中央开盘价
    map.put('R', "US_SELLER");                // 卖方
    map.put('T', "US_FORM_T");                // 盘前盘后交易
    map.put('U', "US_EXTENDED_TRADING_HOURS");// 延长交易时段
    map.put('V', "US_CONTINGENT_TRADE");      // 合单交易
    map.put('W', "US_AVERAGE_PRICE_TRADE");   // 均价交易
    map.put('X', "US_CROSS_TRADE");           //
    map.put('Z', "US_SOLD_OUT_OF_SEQUENCE");  // 场外售出
    map.put('0', "US_ODD_LOST_CROSS_TRADE");  // 碎股跨市场交易
    map.put('4', "US_DERIVATIVELY_PRICED");   // 衍生工具定价
    map.put('5', "US_MARKET_CENTER_RE_OPENING_TRADE");// 再开盘定价
    map.put('6', "US_MARKET_CENTER_CLOSING_TRADE");   // 收盘定价
    map.put('7', "US_QUALIFIED_CONTINGENT_TRADE");    // 合单交易
    map.put('9', "US_CONSOLIDATED_LAST_PRICE_PER_LISTING_PACKET");// 综合延迟价格

    return Collections.unmodifiableMap(map);
  }

  private static Map<Character, String> initHkTradeCondMap() {
    Map<Character, String> map = new HashMap<>();
    map.put(' ', "HK_AUTOMATCH_NORMAL"); // 自动对盘
    map.put('D', "HK_ODD_LOT_TRADE");    // 碎股交易
    map.put('U', "HK_AUCTION_TRADE");    // 竞价交易
    map.put('*', "HK_OVERSEAS_TRADE");   // 场外交易
    map.put('P', "HK_LATE_TRADE_OFF_EXCHG");       // 开市前成交
    map.put('M', "HK_NON_DIRECT_OFF_EXCHG_TRADE"); // 非自动对盘
    map.put('X', "HK_DIRECT_OFF_EXCHG_TRADE");     // 同券商自动对盘
    map.put('Y', "HK_AUTOMATIC_INTERNALIZED");     // 同券商非自动对盘
    return Collections.unmodifiableMap(map);
  }

  private static String getPartNameByCode(String code) {
    if (StringUtils.isEmpty(code)) {
      return code;
    }
    String name = PART_CODE_NAME_MAP.get(code);
    return name == null ? code : name;
  }

  private static String getPartShortNameByCode(String code) {
    if (StringUtils.isEmpty(code)) {
      return code;
    }
    String name = PART_CODE_SHORT_NAME_MAP.get(code);
    return name == null ? code : name;
  }

  private static String getTradeCondByCode(boolean isUsStockSymbol, Character code) {
    if (code == null) {
      code = ' ';
    }
    String tradeCond = null;
    if (isUsStockSymbol) {
      tradeCond = usTradeCondMap.get(code);
    } else {
      tradeCond = hkTradeCondMap.get(code);
    }

    return tradeCond == null ? String.valueOf(code) : tradeCond;
  }

  public static JSONObject decodeData(JSONObject jsonObject) {
    String symbol = jsonObject.getString("symbol");
    if (SymbolUtil.isFutureSymbol(symbol)) {
      return decodeFutureData(jsonObject);
    } else {
      return decodeStockData(jsonObject);
    }
  }

  public static JSONObject decodeStockData(JSONObject jsonObject) {

    String symbol = jsonObject.getString("symbol");
    JSONArray timeArray = jsonObject.getJSONArray("times");
    JSONArray pricesArray = jsonObject.getJSONArray("prices");
    JSONArray partCodeArray = jsonObject.getJSONArray("partCode");
    JSONArray volumeArray = jsonObject.getJSONArray("volumes");
    String cond = (String)jsonObject.remove("cond");
    String tickType = (String)jsonObject.remove("tickType");
    int startSn = jsonObject.getIntValue("sn");

    boolean isUsStockSymbol = SymbolUtil.isUsStockSymbol(symbol);

    JSONArray tickDetail = new JSONArray(timeArray.size());
    jsonObject.put("ticks", tickDetail);
    if (timeArray != null && timeArray.size() > 0) {
      long previousTime = 0;
      double priceBase = jsonObject.getLongValue("priceBase");
      double denominator = Math.pow(10, jsonObject.getIntValue("priceOffset"));
      for (int i = 0; i < timeArray.size(); i++) {
        JSONObject tickData = new JSONObject();
        tickDetail.set(i, tickData);

        // recover time: time[i] = time[i] + time[i-1]
        previousTime += timeArray.getLongValue(i);
        timeArray.set(i, previousTime);
        // recover price: price[i] = (priceBase + price[i]) / 10^priceOffset
        pricesArray.set(i, (priceBase + pricesArray.getDoubleValue(i)) / denominator);

        tickData.put("sn", startSn++);
        tickData.put("volume", volumeArray.get(i));
        tickData.put("time", timeArray.get(i));
        tickData.put("price", pricesArray.get(i));
        if (i < partCodeArray.size()) {
          tickData.put("partCode", getPartShortNameByCode(partCodeArray.getString(i)));
          tickData.put("partName", getPartNameByCode(partCodeArray.getString(i)));
        }

        Character condChar = null;
        if (cond != null && cond.length() > i) {
          condChar = cond.charAt(i);
        }
        tickData.put("cond", getTradeCondByCode(isUsStockSymbol, condChar));
        if (tickType != null && tickType.length() > i) {
          tickData.put("tickType", tickType.substring(i, i+1));
        }
      }
    }
    jsonObject.remove("times");
    jsonObject.remove("prices");
    jsonObject.remove("partCode");
    jsonObject.remove("volumes");
    jsonObject.remove("priceBase");
    jsonObject.remove("priceOffset");

    return jsonObject;
  }

  public static JSONObject decodeFutureData(JSONObject jsonObject) {
    JSONArray timeArray = jsonObject.getJSONArray("times");
    JSONArray pricesArray = jsonObject.getJSONArray("prices");
    int startSn = jsonObject.getIntValue("sn");
    JSONArray mergedVolsArray = jsonObject.getJSONArray("mergedVols");
    int totalCount = 0;
    for (int i = 0; i < mergedVolsArray.size(); i++) {
      totalCount += mergedVolsArray.getJSONObject(i).getIntValue("mergeTimes");
    }

    JSONArray tickDetail = new JSONArray(totalCount);
    jsonObject.put("ticks", tickDetail);
    if (timeArray != null && timeArray.size() > 0) {
      long previousTime = 0;
      double priceBase = jsonObject.getLongValue("priceBase");
      double denominator = Math.pow(10, jsonObject.getIntValue("priceOffset"));
      int idx = 0;
      for (int i = 0; i < timeArray.size(); i++) {

        // recover time: time[i] = time[i] + time[i-1]
        previousTime += timeArray.getLongValue(i);
        timeArray.set(i, previousTime);
        // recover price: price[i] = (priceBase + price[i]) / 10^priceOffset
        pricesArray.set(i, (priceBase + pricesArray.getDoubleValue(i)) / denominator);

        JSONArray volsArray = mergedVolsArray.getJSONObject(i).getJSONArray("vols");
        int mergeTimes = mergedVolsArray.getJSONObject(i).getIntValue("mergeTimes");
        for (int j = 0; j < mergeTimes; j++) {
          JSONObject tickData = new JSONObject();
          tickDetail.set(idx++, tickData);
          tickData.put("sn", startSn * 10 + j);
          tickData.put("volume", volsArray.get(j));
          tickData.put("time", timeArray.get(i));
          tickData.put("price", pricesArray.get(i));
        }
        startSn++;
      }
    }
    jsonObject.remove("times");
    jsonObject.remove("prices");
    jsonObject.remove("volumes");
    jsonObject.remove("priceBase");
    jsonObject.remove("priceOffset");
    jsonObject.remove("mergedVols");

    return jsonObject;
  }

  public static TradeTick convert(TradeTickData source) {
    String secType = source.getSecType();
    if (SecType.FUT.name().equals(secType)) {
      return convertFutureData(source);
    } else {
      return convertStockData(source);
    }
  }

  public static TradeTick convertStockData(TradeTickData source) {
    TradeTick tradeTick = new TradeTick();

    tradeTick.setSecType(SecType.STK);
    tradeTick.setSymbol(source.getSymbol());
    tradeTick.setQuoteLevel(source.getQuoteLevel());
    tradeTick.setTimestamp(source.getTimestamp());
    List<Long> timeList = source.getTimeList();
    List<Long> pricesList = source.getPriceList();
    List<String> partCodeList = source.getPartCodeList();
    List<Long> volumeList = source.getVolumeList();
    String cond = source.getCond();
    String tickType = source.getType();
    long startSn = source.getSn();
    boolean isUsStockSymbol = SymbolUtil.isUsStockSymbol(source.getSymbol());

    List<TradeTick.Tick> ticks = new ArrayList<>(source.getTimeCount());
    tradeTick.setTicks(ticks);
    if (timeList != null && timeList.size() > 0) {
      long currentTime = 0;
      long priceBase = source.getPriceBase();
      double denominator = Math.pow(10, source.getPriceOffset());
      for (int i = 0; i < timeList.size(); i++) {
        TradeTick.Tick tickData = new TradeTick.Tick();
        ticks.add(tickData);

        // recover time: time[i] = time[i] + time[i-1]
        currentTime += timeList.get(i);
        tickData.setTime(currentTime);
        // recover price: price[i] = (priceBase + price[i]) / 10^priceOffset
        tickData.setPrice((priceBase + pricesList.get(i).doubleValue()) / denominator);

        tickData.setSn(startSn++);
        tickData.setVolume(volumeList.get(i));
        if (i < partCodeList.size()) {
          tickData.setPartCode(getPartShortNameByCode(partCodeList.get(i)));
          tickData.setPartName(getPartNameByCode(partCodeList.get(i)));
        }

        Character condChar = null;
        if (cond != null && cond.length() > i) {
          condChar = cond.charAt(i);
        }
        tickData.setCond(getTradeCondByCode(isUsStockSymbol, condChar));
        if (tickType != null && tickType.length() > i) {
          tickData.setTickType(tickType.substring(i, i+1));
        }
      }
    }

    return tradeTick;
  }

  public static TradeTick convertFutureData(TradeTickData source) {
    TradeTick tradeTick = new TradeTick();

    tradeTick.setSecType(SecType.FUT);
    tradeTick.setSymbol(source.getSymbol());
    tradeTick.setTimestamp(source.getTimestamp());
    List<Long> timeList = source.getTimeList();
    List<Long> pricesList = source.getPriceList();
    long startSn = source.getSn();
    List<TradeTickData.MergedVol> mergedVolsArray = source.getMergedVolsList();
    int totalCount = 0;
    for (TradeTickData.MergedVol item : mergedVolsArray) {
      totalCount += item.getMergeTimes();
    }

    List<TradeTick.Tick> ticks = new ArrayList<>(totalCount);
    tradeTick.setTicks(ticks);
    if (timeList != null && timeList.size() > 0) {
      long currentTime = 0;
      long priceBase = source.getPriceBase();
      double denominator = Math.pow(10, source.getPriceOffset());
      for (int i = 0; i < timeList.size(); i++) {

        // recover time: time[i] = time[i] + time[i-1]
        currentTime += timeList.get(i);
        // recover price: price[i] = (priceBase + price[i]) / 10^priceOffset
        double curPrices = (priceBase + pricesList.get(i).doubleValue()) / denominator;

        List<Long> volsList = mergedVolsArray.get(i).getVolList();
        int mergeTimes = mergedVolsArray.get(i).getMergeTimes();
        for (int j = 0; j < mergeTimes; j++) {
          TradeTick.Tick tickData = new TradeTick.Tick();
          ticks.add(tickData);
          tickData.setSn(startSn * 10 + j);
          tickData.setVolume(volsList.get(j));
          tickData.setTime(currentTime);
          tickData.setPrice(curPrices);
        }
        startSn++;
      }
    }

    return tradeTick;
  }
}
