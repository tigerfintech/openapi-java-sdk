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
    /** Gross profit margin * (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the value range [0.005, 0.01] */
    FinancialField_GrossProfitRate(1, "grossMarginVal"),
    /** Net profit margin * (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the value range [0.005, 0.01] */
    FinancialField_NetProfitRate(2, "netIncomeMarginVal"),
    /** Non-recurring net profit margin * (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the value range [0.005, 0.01] */
    FinancialField_EarningsFromContOpsMargin(3, "earningsFromContOpsMargin"),
    /** Long-term debt to equity ratio */
    FinancialField_LongTermDebtToEquity(5, "ltDebtToEquity"),
    /** EBIT to interest expense ratio */
    FinancialField_EbitToInterestExp(6, "ebitToInterestExp"),
    /** Total asset turnover ratio * (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the value range [0.005, 0.01] */
    FinancialField_TotalAssetTurnover(8, "totalAssetTurnover"),
    /** Accounts receivable turnover ratio */
    FinancialField_AccountsReceivableTurnover(9, "accountsReceivableTurnover"),
    /** Inventory turnover ratio * (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the value range [0.005, 0.01] */
    FinancialField_InventoryTurnover(10, "inventoryTurnover"),
    /** Current ratio * (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the value range [0.005, 0.01] */
    FinancialField_CurrentRatio(11, "currentRatioVal"),
    /** Quick ratio * (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the value range [0.005, 0.01] */
    FinancialField_QuickRatio(12, "quickRatioVal"),
    /** Return on total assets * TTM (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the value range [0.005, 0.01] */
    FinancialField_ROATTM(13, "roa"),
    /** Return on equity * (accurate to 3 decimal places, excess parts will be discarded) For example, fill in the value range [0.005, 0.01] */
    FinancialField_ReturnOnEquityRate(14, "roe"),
    /** Year-on-year growth rate of total revenues or sales */
    FinancialField_TotalRevenues1YrGrowth(15, "totalRevenues1YrGrowth"),
    /** Year-on-year growth rate of gross profit margin */
    FinancialField_GrossProfit1YrGrowth(16, "grossProfit1YrGrowth"),
    /** Year-on-year growth rate of net profit */
    FinancialField_NetIncome1YrGrowth(17, "netIncome1YrGrowth"),
    /** Year-on-year growth rate of accounts receivable */
    FinancialField_AccountsReceivable1YrGrowth(18, "accountsReceivable1YrGrowth"),
    /** Year-on-year growth rate of inventory */
    FinancialField_Inventory1YrGrowth(19, "inventory1YrGrowth"),
    /** Year-on-year growth rate of total assets */
    FinancialField_TotalAssets1YrGrowth(20, "totalAssets1YrGrowth"),
    /** Year-on-year growth rate of tangible assets */
    FinancialField_TangibleBookValue1YrGrowth(21, "tangibleBookValue1YrGrowth"),
    /** Year-on-year growth rate of cash flow from operations */
    FinancialField_CashFromOperations1YrGrowth(22, "cashFromOperations1YrGrowth"),
    /** Year-on-year growth rate of capital expenditures */
    FinancialField_CapitalExpenditures1YrGrowth(23, "capitalExpenditures1YrGrowth"),
    /** Three-year compound annual growth rate of total revenues */
    FinancialField_TotalRevenues3YrCagr(24, "totalRevenues3YrCagr"),
    /** Three-year compound annual growth rate of gross profit margin */
    FinancialField_GrossProfit3YrCagr(25, "grossProfit3YrCagr"),
    /** Three-year compound annual growth rate of net profit */
    FinancialField_NetIncome3YrCagr(26, "netIncome3YrCagr"),
    /** Three-year compound annual growth rate of accounts receivable */
    FinancialField_AccountsReceivable3YrCagr(27, "accountsReceivable3YrCagr"),
    /** Three-year compound annual growth rate of inventory */
    FinancialField_Inventory3YrCagr(28, "inventory3YrCagr"),
    /** Three-year compound annual growth rate of total assets */
    FinancialField_TotalAssets3YrCagr(29, "totalAssets3YrCagr"),
    /** Three-year compound annual growth rate of tangible assets */
    FinancialField_TangibleBookValue3YrCagr(30, "tangibleBookValue3YrCagr"),
    /** Three-year compound annual growth rate of cash flow from operations */
    FinancialField_CashFromOps3YrCagr(31, "cashFromOps3YrCagr"),
    /** Three-year compound annual growth rate of capital expenditures */
    FinancialField_CapitalExpenditures3YrCagr(32, "capitalExpenditures3YrCagr"),
    /** Net income */
    FinancialField_NetIncomeToCompany(33, "netIncomeToCompany"),
    /** Cash flow from operations */
    FinancialField_CashFromOperations(34, "cashFromOps"),
    /** Cash flow from investing activities */
    FinancialField_CashFromInvesting(35, "cashFromInvesting"),
    /** Cash flow from financing activities */
    FinancialField_CashFromFinancing(36, "cashFromFinancing"),
    /** Two-year compound annual growth rate of net income */
    FinancialField_NormalizedNetIncome2YrCagr(37, "netIncome2YrCagr"),
    /** Two-year compound annual growth rate of total revenues */
    FinancialField_TotalRevenues2YrCagr(38, "totalRevenues2YrCagr"),
    /** Five-year compound annual growth rate of net income */
    FinancialField_NetIncome5YrCagr(39, "netIncome5YrCagr"),
    /** Five-year compound annual growth rate of total revenues */
    FinancialField_TotalRevenues5YrCagr(40, "totalRevenues5YrCagr"),
    /** Total assets */
    FinancialField_TotalAssets(41, "totalAssetsVal"),
    /** Fixed asset turnover (accurate to 3 decimal places, any excess will be discarded) for example, fill in the value range [0.005, 0.01] */
    FinancialField_FixedAssetTurnover(42, "fixedAssetTurnover"),
    /** Operating income */
    FinancialField_OperatingIncome(43, "operatingIncomeVal"),
    /** Total revenue */
    FinancialField_TotalRevenue(44, "totalRevenue"),
    /** Price-to-earnings ratio LYR PE = price-to-earnings ratio */
    FinancialField_LYR_PE(45, "LyrPE"),
    /** Price-to-earnings ratio TTM PE = price-to-earnings ratio */
    FinancialField_TTM_PE(46, "ttmPE"),
    /** Price-to-sales ratio LYR PS = Price-to-sales Ratio */
    FinancialField_LYR_PS(47, "LyrPS"),
    /** Price-to-sales ratio TTM PS = Price-to-sales Ratio */
    FinancialField_TTM_PS(48, "ttmPS"),
    /** Net inflow amount from major investors today */
    FinancialField_LargeInflowAmountToday(49, "largeInflowAmountToday"),
    /** Percentage of increase in position from major investors today */
    FinancialField_LargeInflowAmountTodayPre(50, "largeInflowAmountTodayPre"),
    /** Outstanding short interest */
    FinancialField_ShortInterest(51, "shortInterest"),
    /** Percentage of outstanding short interest */
    FinancialField_ShortInterestPre(52, "shortInterestPre"),
    /** Hong Kong Stock Connect holding ratio = Hong Kong Stock Connect (Shenzhen) holding ratio = Hong Kong Stock Connect (Shanghai) holding ratio */
    FinancialField_HK_StockConnectRate(53, "hkStockConnectRate"),
    /** Shanghai Stock Connect holding ratio */
    FinancialField_SH_StockConnectRate(54, "shStockConnectRate"),
    /** Shenzhen Stock Connect holding ratio */
    FinancialField_SZ_StockConnectRate(55, "szStockConnectRate"),
    /** Operating profit rate */
    FinancialField_Operating_Profits_Rate(56, "operatingProfitsRate"),
    /** Net inflow amount from Hong Kong Stock Connect (Shanghai) */
    FinancialField_HK_StockShConnectInflow(57, "hkStockShConnectInflow"),
    /** Net inflow amount from Hong Kong Stock Connect (Shenzhen) */
    FinancialField_HK_StockSzConnectInflow(58, "hkStockSzConnectInflow"),
    /** Net inflow amount from Shanghai Stock Connect */
    FinancialField_SH_StockConnectInflow(59, "shStockConnectInflow"),
    /** Net inflow amount from Shenzhen Stock Connect */
    FinancialField_SZ_StockConnectInflow(60, "szStockConnectInflow"),
    /** Annualized return on ETF since its listing */
    FinancialField_ListingAnnualReturn(61, "listingAnnualReturn"),
    /** Annualized return in the past year on ETF */
    FinancialField_LstYearAnnualReturn(62, "lstYearAnnualReturn"),
    /** Annualized return in the past 2 years on ETF */
    FinancialField_Lst2YearAnnualReturn(63, "lst2YearAnnualReturn"),
    /** Annualized return in the past 5 years on ETF */
    FinancialField_Lst5YearAnnualReturn(64, "lst5YearAnnualReturn"),
    /** Annualized volatility since listing on ETF */
    FinancialField_ListingAnnualVolatility(65, "listingAnnualVolatility"),
    /** Annualized volatility in the past year on ETF */
    FinancialField_LstYearAnnualVolatility(66, "lstYearAnnualVolatility"),
    /** Annualized volatility in the past 2 years on ETF */
    FinancialField_Lst2YearAnnualVolatility(67, "lst2YearAnnualVolatility"),
    /** Annualized volatility in the past 5 years on ETF */
    FinancialField_Lst5YearAnnualVolatility(68, "lst5YearAnnualVolatility"),
    /** Price-to-book ratio LYR PB = price/book value ratio */
    FinancialField_LYR_PB(69, "LyrPB"),
    /** Price-to-book ratio TTM PB = price/book value ratio */
    FinancialField_TTM_PB(70, "ttmPB"),
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