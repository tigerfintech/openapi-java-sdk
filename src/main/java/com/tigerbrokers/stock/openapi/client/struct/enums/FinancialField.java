package com.tigerbrokers.stock.openapi.client.struct.enums;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Description: 财务相关指标
 *
 * @author kevin
 * @date 2022/10/27
 */
public enum FinancialField {
    /** 毛利率*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    FinancialField_GrossProfitRate(1, "grossMargin"),
    /** 净利率*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    FinancialField_NetProfitRate(2, "netIncomeMargin"),
    /** 扣非净利润率  *（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    FinancialField_EarningsFromContOpsMargin(3, "earningsFromContOpsMargin"),
    /** 总负债/股东权益* (单位：元) */
    FinancialField_TotalDebtToEquity(4, "totalDebtToEquity"),
    /** 长期负债/股东权益 **/
    FinancialField_LongTermDebtToEquity(5, "ltDebtToEquity"),
    /** EBIT/利息支出 **/
    FinancialField_EbitToInterestExp(6, "ebitToInterestExp"),
    /** 总负债/总资产 **/
    FinancialField_TotalLiabilitiesToTotalAssets(7, "totalLiabilitiesToTotalAssets"),
    /** 总资产周转率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    FinancialField_TotalAssetTurnover(8, "totalAssetTurnover"),
    /** 应收帐款周转率 */
    FinancialField_AccountsReceivableTurnover(9, "accountsReceivableTurnover"),
    /** 存货周转率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    FinancialField_InventoryTurnover(10, "inventoryTurnover"),
    /** 流动比率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    FinancialField_CurrentRatio(11, "currentRatio"),
    /** 速动比率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    FinancialField_QuickRatio(12, "quickRatio"),
    /** 资产回报率 总资产收益率 *$ TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    FinancialField_ROATTM(13, "roa"),
    /** 净资产收益率 $（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    FinancialField_ReturnOnEquityRate(14, "roe"),
    /** 营业收入一年增长率 或者 营收增长率 */
    FinancialField_TotalRevenues1YrGrowth(15, "totalRevenues1YrGrowth"),
    /** 毛利润率一年增长率  营业利润增长率 */
    FinancialField_GrossProfit1YrGrowth(16, "grossProfit1YrGrowth"),
    /** 净利润一年增长率 */
    FinancialField_NetIncome1YrGrowth(17, "netIncome1YrGrowth"),
    /** 应收帐款一年增长率 */
    FinancialField_AccountsReceivable1YrGrowth(18, "accountsReceivable1YrGrowth"),
    /** 存货一年增长率 */
    FinancialField_Inventory1YrGrowth(19, "inventory1YrGrowth"),
    /** 总资产一年增长率 */
    FinancialField_TotalAssets1YrGrowth(20, "totalAssets1YrGrowth"),
    /** 有形资产一年增长率 */
    FinancialField_TangibleBookValue1YrGrowth(21, "tangibleBookValue1YrGrowth"),
    /** 经营现金流一年增长率 */
    FinancialField_CashFromOperations1YrGrowth(22, "cashFromOperations1YrGrowth"),
    /** 资本开支一年增长率 */
    FinancialField_CapitalExpenditures1YrGrowth(23, "capitalExpenditures1YrGrowth"),
    /** 营业收入三年增长率 或者叫 营收3年复合增长率 */
    FinancialField_TotalRevenues3YrCagr(24, "totalRevenues3YrCagr"),
    /** 毛利润率三年增长率 */
    FinancialField_GrossProfit3YrCagr(25, "grossProfit3YrCagr"),
    /** 净利润三年增长率 */
    FinancialField_NetIncome3YrCagr(26, "netIncome3YrCagr"),
    /** 应收帐款三年增长率 */
    FinancialField_AccountsReceivable3YrCagr(27, "accountsReceivable3YrCagr"),
    /** 存货三年增长率 */
    FinancialField_Inventory3YrCagr(28, "inventory3YrCagr"),
    /** 总资产三年增长率 */
    FinancialField_TotalAssets3YrCagr(29, "totalAssets3YrCagr"),
    /** 有形资产三年增长率 */
    FinancialField_TangibleBookValue3YrCagr(30, "tangibleBookValue3YrCagr"),
    /** 经营现金流三年增长率 */
    FinancialField_CashFromOps3YrCagr(31, "cashFromOps3YrCagr"),
    /** 资本开支三年增长率 */
    FinancialField_CapitalExpenditures3YrCagr(32, "capitalExpenditures3YrCagr"),
    /** 净利润 */
    FinancialField_NetIncomeToCompany(33, "netIncomeToCompany"),
    /** 经营现金流 */
    FinancialField_CashFromOperations(34, "cashFromOps"),
    /** 投资现金流 */
    FinancialField_CashFromInvesting(35, "cashFromInvesting"),
    /** 筹资现金流 */
    FinancialField_CashFromFinancing(36, "cashFromFinancing"),
    /** 净利润2年复合增长率 */
    FinancialField_NormalizedNetIncome2YrCagr(37, "normalizedNetIncome2YrCagr"),
    /** 营收2年复合增长率 */
    FinancialField_TotalRevenues2YrCagr(38, "totalRevenues2YrCagr"),
    /** 净利润5年复合增长率 */
    FinancialField_NetIncome5YrCagr(39, "netIncome5YrCagr"),
    /** 营收5年复合增长率 */
    FinancialField_TotalRevenues5YrCagr(40, "totalRevenues5YrCagr"),
    /** 总资产 */
    FinancialField_TotalAssets(41, "totalAssets"),
    /** 固定资产周转率（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    FinancialField_FixedAssetTurnover(42, "fixedAssetTurnover"),
    /** 营业利润 */
    FinancialField_OperatingIncome(43, "operatingIncome"),
    /** 营业总收入 */
    FinancialField_TotalRevenue(44, "totalRevenue"),
    /** 市盈率LYR PE =price-to-earnings ratio */
    FinancialField_LYR_PE(45, "LyrPE"),
    /** 市盈率TTM PE =price-to-earnings ratio */
    FinancialField_TTM_PE(46, "ttmPE"),
    /** 市销率LYR PS =Price-to-sales Ratio */
    FinancialField_LYR_PS(47, "LyrPS"),
    /** 市销率TTM PS =Price-to-sales Ratio */
    FinancialField_TTM_PS(48, "ttmPS"),
    /** 市净率LYR PB =price/book value ratio */
    FinancialField_LYR_PB(47, "LyrPB"),
    /** 市净率TTM PB =price/book value ratio */
    FinancialField_TTM_PB(48, "ttmPB"),
    /** 当日主力净流入额 */
    FinancialField_LargeInflowAmountToday(49, "largeInflowAmountToday"),
    /** 当日主力增仓占比 */
    FinancialField_LargeInflowAmountTodayPre(50, "largeInflowAmountTodayPre"),
    /** 未平仓做空量 */
    FinancialField_ShortInterest(51, "shortInterest"),
    /** 未平仓做空比例 */
    FinancialField_ShortInterestPre(52, "shortInterestPre"),
    /** 港股通持股比例=港股通(深)持股比例=港股通(沪)持股比例 */
    FinancialField_HK_StockConnectRate(53, "hkStockConnectRate"),
    /** 沪股通持股比例 */
    FinancialField_SH_StockConnectRate(54, "shStockConnectRate"),
    /** 深股通持股比例 */
    FinancialField_SZ_StockConnectRate(55, "szStockConnectRate"),
    /** 营业利润占比 */
    FinancialField_Operating_Profits_Rate(56, "operatingProfitsRate"),
    /** 港股通(沪)净买入额 */
    FinancialField_HK_StockShConnectInflow(57, "hkStockShConnectInflow"),
    /** 港股通(深)净买入额 */
    FinancialField_HK_StockSzConnectInflow(58, "hkStockSzConnectInflow"),
    /** 沪股通净买入额 */
    FinancialField_SH_StockConnectInflow(59, "shStockConnectInflow"),
    /** 深股通净买入额 */
    FinancialField_SZ_StockConnectInflow(60, "szStockConnectInflow"),
    /** 上市以来年化收益率 ETF */
    FinancialField_ListingAnnualReturn(61, "listingAnnualReturn"),
    /** 近1年年化收益率  ETF */
    FinancialField_LstYearAnnualReturn(62, "lstYearAnnualReturn"),
    /** 近2年年化收益率  ETF */
    FinancialField_Lst2YearAnnualReturn(63, "lst2YearAnnualReturn"),
    /** 近5年年化收益率  ETF */
    FinancialField_Lst5YearAnnualReturn(64, "lst5YearAnnualReturn"),
    /** 上市以来年化波动率  ETF */
    FinancialField_ListingAnnualVolatility(65, "listingAnnualVolatility"),
    /** 近1年年化波动率  ETF */
    FinancialField_LstYearAnnualVolatility(66, "lstYearAnnualVolatility"),
    /** 近2年年化波动率  ETF */
    FinancialField_Lst2YearAnnualVolatility(67, "lst2YearAnnualVolatility"),
    /** 近5年年化波动率  ETF */
    FinancialField_Lst5YearAnnualVolatility(68, "lst5YearAnnualVolatility"),

    ;

    @Getter
    private String value;
    @Getter
    private Integer index;

    FinancialField(Integer index, String value) {
        this.index = index;
        this.value = value;
    }

    public static FinancialField getTypeByValue(String value) {
        for (FinancialField item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    public static String getValueByIndex(Integer index) {
        for (FinancialField item : values()) {
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
        for (FinancialField item : values()) {
            retSet.add(item.getValue());
        }
        return retSet;
    }

    public static Integer getIndexByValue(String value) {
        for (FinancialField item : values()) {
            if (item.getValue().equals(value)) {
                return item.getIndex();
            }
        }
        return null;
    }

    public static FinancialField getTypeByIndex(Integer index) {
        for (FinancialField item : values()) {
            if (item.getIndex().equals(index)) {
                return item;
            }
        }
        return null;
    }
}