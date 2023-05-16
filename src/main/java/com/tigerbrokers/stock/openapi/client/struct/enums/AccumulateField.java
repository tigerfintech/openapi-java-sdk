package com.tigerbrokers.stock.openapi.client.struct.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public enum AccumulateField {
    /** 涨跌幅*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间  */
    AccumulateField_ChangeRate(1, "changeRate"),
    /** 涨跌额*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    AccumulateField_ChangeValue(2, "changeVal"),
    /** 总负债增长率 */
    AccumulateField_TotalLiabilities_Ratio_Annual(3, "totalLiabilitiesRatio"),
    /** 净资产增长率 */
    AccumulateField_TotalCommonEquity_Ratio_Annual(4, "totalCommonEquityRatio"),
    /** 每股收益同比增长率 */
    AccumulateField_BasicEps_Ratio_Annual(5, "basicEpsRatio"),
    /** 净利润同比增长率 */
    AccumulateField_NetIncome_Ratio_Annual(6, "netIncomeRatio"),
    /** 营业利润同比增长率 */
    AccumulateField_OperatingIncome_Ratio_Annual(7, "opeIncomeratio"),
    /** 每股收益 */
    AccumulateField_Eps(8, "eps"),
    /** 每股净资产 */
    AccumulateField_NetAsset_PerShare(9, "bookValueshare"),
    /** 净利润 */
    AccumulateField_Net_Income(10, "netIncome"),
    /** 营业利润 */
    AccumulateField_Operating_Income(11, "operatingIncome"),
    /** 营业收入 */
    AccumulateField_Total_Revenue(12, "total_revenue"),
    /** ROE = 资产回报率 */
    AccumulateField_ROE(13, "ROE"),
    /** ROA =净资产收益率 */
    AccumulateField_ROA(14, "ROA"),
    /** 毛利率 */
    AccumulateField_GrossProfitRate(17, "grossMargin"),
    /** 净利率* */
    AccumulateField_NetProfitRate(18, "netIncomeMargin"),
    /** 总资产* */
    AccumulateField_TotalAssets(19, "totalAssets"),
    /** 流动比率 */
    AccumulateField_CurrentRatio(20, "currentRatio"),
    /** 速动比率 */
    AccumulateField_QuickRatio(21, "quickRatio"),
    /** 经营现金流同比率 */
    AccumulateField_CashFromOpsRatio(22, "cash4OpsRatio"),
    /** 投资现金流 */
    AccumulateField_CashFromInvesting(23, "cash4Invest"),
    /** 筹资现金流 */
    AccumulateField_CashFromFinancing(24, "cash4Finance"),
    /** 资产负债率 */
    AccumulateField_TotalLiabilitiesToTotalAssets(25, "allLiabAndAssets"),
    /** 净资产收益率ROE同比增长率  （T期ROE-T-1期ROE）/T-1期ROE *100%*/
    AccumulateField_ROE_yearOnYear_Ratio(27, "netIncomeYearOnYearRatio"),
    /** 营业利润占比 */
    AccumulateField_Operating_Profits_Ratio(28, "OperatingProfitsRatio"),
    /** 经营现金流  */
    AccumulateField_CashFromOpsVal(29, "cash4OpsVal"),
    ;

    private String value;
    private Integer index;
    private String combineSign;

    AccumulateField(Integer index, String value) {
        this.index = index;
        this.value = value;
        //this.combineSign = combineSign;
    }

    public static AccumulateField getTypeByValue(String value) {
        for (AccumulateField item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    public static String getValueByIndex(Integer index) {
        for (AccumulateField item : values()) {
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
        for (AccumulateField item : values()) {
            retSet.add(item.getValue());
        }
        return retSet;
    }

    public static Integer getIndexByValue(String value) {
        for (AccumulateField item : values()) {
            if (item.getValue().equals(value)) {
                return item.getIndex();
            }
        }
        return null;
    }

    public static AccumulateField getTypeByIndex(Integer index) {
        for (AccumulateField item : values()) {
            if (item.getIndex().equals(index)) {
                return item;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getCombineSign() {
        return combineSign;
    }

    public void setCombineSign(String combineSign) {
        this.combineSign = combineSign;
    }
}