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
    /** Change rate* (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the [0.005, 0.01] value range */
    AccumulateField_ChangeRate(1, "changeRate"),
    /** Change value* (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the [0.005, 0.01] value range */
    AccumulateField_ChangeValue(2, "changeVal"),
    /** Total liabilities growth rate */
    AccumulateField_TotalLiabilities_Ratio_Annual(3, "totalLiabilitiesRatio"),
    /** Net asset growth rate */
    AccumulateField_TotalCommonEquity_Ratio_Annual(4, "totalCommonEquityRatio"),
    /** Year-on-year growth rate of earnings per share */
    AccumulateField_BasicEps_Ratio_Annual(5, "basicEpsRatio"),
    /** Year-on-year growth rate of net profit */
    AccumulateField_NetIncome_Ratio_Annual(6, "netIncomeRatio"),
    /** Year-on-year growth rate of operating profit */
    AccumulateField_OperatingIncome_Ratio_Annual(7, "opeIncomeratio"),
    /** Earnings per share */
    AccumulateField_Eps(8, "eps"),
    /** Net asset per share */
    AccumulateField_NetAsset_PerShare(9, "bookValueshare"),
    /** Net profit */
    AccumulateField_Net_Income(10, "netIncome"),
    /** Operating profit */
    AccumulateField_Operating_Income(11, "operatingIncome"),
    /** Operating revenue */
    AccumulateField_Total_Revenue(12, "total_revenue"),
    /** ROE = Return on equity */
    AccumulateField_ROE(13, "ROE"),
    /** ROA = Return on assets */
    AccumulateField_ROA(14, "ROA"),
    /** Gross profit rate */
    AccumulateField_GrossProfitRate(17, "grossMargin"),
    /** Net profit margin */
    AccumulateField_NetProfitRate(18, "netIncomeMargin"),
    /** Total assets */
    AccumulateField_TotalAssets(19, "totalAssets"),
    /** Current ratio */
    AccumulateField_CurrentRatio(20, "currentRatio"),
    /** Quick ratio */
    AccumulateField_QuickRatio(21, "quickRatio"),
    /** Year-on-year growth rate of operating cash flow */
    AccumulateField_CashFromOpsRatio(22, "cash4OpsRatio"),
    /** Cash flow from investing */
    AccumulateField_CashFromInvesting(23, "cash4Invest"),
    /** Cash flow from financing */
    AccumulateField_CashFromFinancing(24, "cash4Finance"),
    /** Debt to asset ratio */
    AccumulateField_TotalLiabilitiesToTotalAssets(25, "allLiabAndAssets"),
    /** Year-on-year growth rate of net income return on equity (T period ROE-T-1 period ROE) / T-1 period ROE * 100% */
    AccumulateField_ROE_yearOnYear_Ratio(27, "netIncomeYearOnYearRatio"),
    /** Operating profit ratio */
    AccumulateField_Operating_Profits_Ratio(28, "OperatingProfitsRatio"),
    /** Operating cash flow */
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