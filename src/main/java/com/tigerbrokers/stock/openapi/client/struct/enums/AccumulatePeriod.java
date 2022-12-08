package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public enum AccumulatePeriod {
    /** 近五分钟 */
    Five_Minutes(0, "_5_min", "changeRate"),
    /** 近五天 */
    Five_Days(1, "_5_days", "changeRate"),
    /** 近10日 */
    Ten_Days(2, "_10_days", "changeRate"),
    /** 近20日 */
    Twenty_Days(3, "_20_days", "changeRate"),
    /** 年初至今 */
    Beginning_Of_The_Year_To_Now(4, "_1_year", "changeRate"),
    /** 近半年 */
    Half_Year(5, "_half_year", "changeRate"),
    /** 近一年 */
    Last_Year(6, "_last_year", "changeRate"),
    /** 近两年 */
    Last_Two_Year(7, "_last_two_year", "changeRate"),
    /** 近五年 */
    Last_Five_Year(8, "_last_five_year", "changeRate"),
    /** 上市至今 */
    Listing_Date_To_Now(9, "_ListDateToNow", "changeRate"),
    /** 年度范围 */
    ANNUAL(10, "_annu",
            "totalLiabilitiesRatio,totalCommonEquityRatio,basicEpsRatio,netIncomeRatio,opeIncomeratio,eps,bookValueshare,netIncome,operatingIncome,total_revenue,ROE,ROA,dividePrice,divideRate,grossMargin,netIncomeMargin,totalAssets,currentRatio,quickRatio,cash4Ops,cash4Invest,cash4Finance,allLiabAndAssets,cash4Ops,netIncomeYearOnYearRatio,cash4OpsYearOnYearRatio"),
    /** 一季度报 */
    QUARTERLY(11, "_quart",
            "totalLiabilitiesRatio,totalCommonEquityRatio,basicEpsRatio,netIncomeRatio,opeIncomeratio,eps,bookValueshare,netIncome,operatingIncome,total_revenue,ROE,ROA,dividePrice,divideRate,grossMargin,netIncomeMargin,totalAssets,currentRatio,quickRatio,cash4Ops,cash4Invest,cash4Finance,allLiabAndAssets,cash4Ops,netIncomeYearOnYearRatio,cash4OpsYearOnYearRatio"),
    /** 三季度报 */
    QUARTERLY_Recent_Third(12, "_3_ytd",
            "totalLiabilitiesRatio,totalCommonEquityRatio,basicEpsRatio,netIncomeRatio,opeIncomeratio,eps,bookValueshare,netIncome,operatingIncome,total_revenue,ROE,ROA,dividePrice,divideRate,grossMargin,netIncomeMargin,totalAssets,currentRatio,quickRatio,cash4Ops,cash4Invest,cash4Finance,allLiabAndAssets,cash4Ops,netIncomeYearOnYearRatio,cash4OpsYearOnYearRatio"),
    /** 中报 */
    SEMIANNUAL(13, "_semiAnnu",
            "totalLiabilitiesRatio,totalCommonEquityRatio,basicEpsRatio,netIncomeRatio,opeIncomeratio,eps,bookValueshare,netIncome,operatingIncome,total_revenue,ROE,ROA,dividePrice,divideRate,grossMargin,netIncomeMargin,totalAssets,currentRatio,quickRatio,cash4Ops,cash4Invest,cash4Finance,allLiabAndAssets,cash4Ops,netIncomeYearOnYearRatio,cash4OpsYearOnYearRatio"),

    ;

    private Integer value;
    private String suffix;
    /**
     * 可使用的范围，多个字段逗号分隔 针对AccumulateField的value属性值，这里防止后续维护出现不一致
     */
    private String range;

    AccumulatePeriod(Integer value, String suffix, String range) {
        this.value = value;
        this.suffix = suffix;
        this.range = range;
    }

    public static String getSuffixByIndex(Integer index) {
        for (AccumulatePeriod item : values()) {
            if (item.getValue().equals(index)) {
                return item.getSuffix();
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}