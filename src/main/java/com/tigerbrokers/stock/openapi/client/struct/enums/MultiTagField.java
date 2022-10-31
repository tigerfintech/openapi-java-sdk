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
    /** 所属行业 */
    MultiTagField_Industry(1, "industry"),
    /** 所属概念 */
    MultiTagField_Concept(2, "concept"),
    /** 是否为otc股票.1=是，0=否 */
    MultiTagField_isOTC(3, "isOTC"),
    MultiTagField_StockCode(4, "symbol"),
    /** 股票类型 stock or etf ;股票类型,非0表示该股票是ETF,1表示不带杠杆的etf,2表示2倍杠杆etf,3表示3倍etf杠杆,负值表示反向的ETF */
    MultiTagField_Type(5, "type"),
    /** 成交量异常.1=是，0=否 ;当日实时成交量> 5* 最近一年的平均成交量 */
    MultiTagField_Volume_Spike(6, "volSpike"),
    /** 破净股票；市净率PB<1 */
    MultiTagField_Net_Broken(7, "netBroken"),
    /** 破发股票 ； 最新价<发行价 */
    MultiTagField_Issue_Price_Broken(8, "issuePriceBroken"),
    /** 跟踪指数/资产 - ETF */
    MultiTagField_PrimaryBenchmark(9, "primaryBenchmark"),
    /** 发行人 - ETF */
    MultiTagField_Issuer(10, "issuer"),
    /** 托管人 - ETF */
    MultiTagField_Custodian(11, "custodian"),
    /** 分红频率 - ETF */
    MultiTagField_DistributionFrequency(12, "distributionFrequency"),
    /** 是否支持期权 - ETF ; 1=是，0=否 */
    MultiTagField_OptionsAvailable(13, "optionsAvailable"),
    /** 今日创历史新高 - ETF 1=是，0=否 */
    MultiTagField_Today_HistoryHigh(14, "todayHistoryHigh"),
    /** 今日创历史新低 - ETF 1=是，0=否 */
    MultiTagField_Today_HistoryLow(15, "todayHistoryLow"),
    /** 股票包 */
    MultiTagField_Stock_Package(16, "StockPkg"),
    /** 52周最高 0 否 1是* */
    MultiTagField_Week52HighFlag(17, "week52HighFlag"),
    /** 52周最低 0 否 1是 */
    MultiTagField_Week52LowFlag(18, "week52LowFlag"),

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
