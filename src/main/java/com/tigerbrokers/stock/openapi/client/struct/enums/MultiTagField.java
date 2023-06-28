package com.tigerbrokers.stock.openapi.client.struct.enums;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Description: 多标签筛选字段
 *
 * @author kevin
 * @date 2022/10/27
 */
public enum MultiTagField {
    /** Industry */
    MultiTagField_Industry(1, "industry"),
    /** Concept */
    MultiTagField_Concept(2, "concept"),
    /** OTC stock. 1=yes, 0=no */
    MultiTagField_isOTC(3, "isOTC"),
    MultiTagField_StockCode(4, "symbol"),
    /** Stock type: stock or etf; non-zero value indicates ETF, 1 indicates non-leveraged ETF, 2 indicates 2x leveraged ETF, 3 indicates 3x leveraged ETF, negative value indicates inverse ETF */
    MultiTagField_Type(5, "type"),
    /** Volume spike. 1=yes, 0=no; real-time volume > 5 * average volume of the past year */
    MultiTagField_Volume_Spike(6, "volSpike"),
    /** Net broken stock; PB ratio < 1 */
    MultiTagField_Net_Broken(7, "netBroken"),
    /** Broken issue stock; latest price < issue price */
    MultiTagField_Issue_Price_Broken(8, "issuePriceBroken"),
    /** Tracking index/asset - ETF */
    MultiTagField_PrimaryBenchmark(9, "primaryBenchmark"),
    /** Issuer - ETF */
    MultiTagField_Issuer(10, "issuer"),
    /** Custodian - ETF */
    MultiTagField_Custodian(11, "custodian"),
    /** Distribution frequency - ETF */
    MultiTagField_DistributionFrequency(12, "distributionFrequency"),
    /** Options available - ETF; 1=yes, 0=no */
    MultiTagField_OptionsAvailable(13, "optionsAvailable"),
    /** Today's historical high - ETF; 1=yes, 0=no */
    MultiTagField_Today_HistoryHigh(14, "todayHistoryHigh"),
    /** Today's historical low - ETF; 1=yes, 0=no */
    MultiTagField_Today_HistoryLow(15, "todayHistoryLow"),
    /** Stock package */
    MultiTagField_Stock_Package(16, "StockPkg"),
    /** 52-week high flag; 0=no, 1=yes */
    MultiTagField_Week52HighFlag(17, "week52HighFlag"),
    /** 52-week low flag; 0=no, 1=yes */
    MultiTagField_Week52LowFlag(18, "week52LowFlag"),
    /** Trading currency; specific currency required */
    MultiTagField_TradeCurrency(19, "tradeCurrency"),
    /** ETF type; specific type required */
    MultiTagField_ETF_TYPE(20, "etfType"),
    /** Stock market; multiple markets supported; specific type required QotMarket stock market, pass the value inside */
    MultiTagField_Market_Name(21, "marketName"),
    /** First-level industry level; specific sectorId required */
    MultiTagField_One_Sectors_Level(22, "oneSectorsLevel"),
    ;

    @Getter
    private String value;
    @Getter
    private Integer index;

    MultiTagField(Integer index, String value) {
        this.index = index;
        this.value = value;
    }

    public static MultiTagField getTypeByValue(String value) {
        for (MultiTagField item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    public static String getValueByIndex(Integer index) {
        for (MultiTagField item : values()) {
            if (item.getIndex().equals(index)) {
                return item.getValue();
            }
        }
        return null;
    }

    /**
     * 获取所有的value
     */
    public static Set<String> getAllValues() {
        Set<String> retSet = new HashSet<>();
        for (MultiTagField item : values()) {
            retSet.add(item.getValue());
        }
        return retSet;
    }

    public static Integer getIndexByValue(String value) {
        for (MultiTagField item : values()) {
            if (item.getValue().equals(value)) {
                return item.getIndex();
            }
        }
        return null;
    }

    public static MultiTagField getTypeByIndex(Integer index) {
        for (MultiTagField item : values()) {
            if (item.getIndex().equals(index)) {
                return item;
            }
        }
        return null;
    }
}
