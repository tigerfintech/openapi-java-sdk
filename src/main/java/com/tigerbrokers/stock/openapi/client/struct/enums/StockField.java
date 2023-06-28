package com.tigerbrokers.stock.openapi.client.struct.enums;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Description: scanner base stock field
 *
 * @author kevin
 * @date 2022/10/27
 */
public enum StockField {
    /** Latest price* (accurate to 3 decimal places, the excess will be discarded) For example, fill in the value range of [10,20] */
    StockField_CurPrice(2, "latestPrice"),
    /** Purchase price (accurate to 3 decimal places, the excess will be discarded) For example, fill in the value range [10,20] */
    StockField_BidPrice(3, "bidPrice"),
    /** Selling price (accurate to 3 decimal places, the excess will be discarded) For example, fill in the value range [10,20] */
    StockField_AskPrice(4, "askPrice"),
    /** Today's offer price (accurate to 3 decimal places, the excess will be discarded) For example, fill in the value range [10,20] */
    StockField_OpenPrice(5, "open"),
    /** Yesterday's closing price (accurate to 3 decimal places, the excess will be discarded) For example, fill in the value range [10,20] */
    StockField_PreClosePrice(6, "preClose"),
    /** Highest price */
    StockField_HighPrice(7, "high"),
    /** Lowest price */
    StockField_LowPrice(8, "low"),
    /** Pre-market price* (accurate to 3 decimal places, the excess will be discarded) For example, fill in the [10,20] value range */
    StockField_HourTradingPrePrice(9, "hourTradingPrePrice"),
    /** After-hours price* (accurate to 3 decimal places, the excess will be discarded) For example, fill in the [10,20] value range */
    StockField_HourTradingAfterPrice(10, "hourTradingAfterPrice"),
    /** Volume* */
    StockField_Volume(11, "volume"),
    /** trading amount* */
    StockField_Amount(12, "amount"),
    /** Circulating share capital* */
    StockField_FloatShare(13, "floatShares"),
    /** 52 week maximum price* */
    StockField_Week52High(14, "week52High"),
    /** Lowest price for 52 weeks* */
    StockField_Week52Low(15, "week52Low"),
    /** Market value* FloatMarketVal calculates FloatShare* current price */
    StockField_FloatMarketVal(16, "floatMarketCap"),
    /** total market value * MarketVal shares * current price */
    StockField_MarketValue(17, "marketValue"),
    /** Pre-market price rise and fall (curPrice-pre-market left close) self-calculated latest price-close / close */
    StockField_preHourTradingChangeRate(18, "preHourTradingChangeRate"),
    /** Calculate the after-hours rise and fall by yourself */
    StockField_postHourTradingChangeRate(19, "postHourTradingChangeRate"),
    /** Earnings per share rolling price-earnings ratio TTM=last 12 months Last Twelve Month Get eps through hermes */
    StockField_ttm_Eps(20, "ttmEps"),
    /** Quantity ratio* (accurate to 3 digits after the decimal point, the excess will be discarded) For example, fill in the value range [0.005,0.01] */
    StockField_VolumeRatio(21, "volumeRatio"),
    /** Commission ratio* (accurate to 3 digits after the decimal point, the excess will be discarded) For example, fill in the value range [0.005,0.01] */
    StockField_BidAskRatio(22, "committee"),
    /** Next financial report date * */
    StockField_EarningDate(23, "earningDate"),
    /** Price-earnings ratio* TTM (accurate to 3 decimal places, the excess will be discarded) For example, fill in the value range [0.005,0.01] */
    StockField_PeTTM(24, "peRate"),
    /** Dividend hermes $ */
    StockField_DividePrice(26, "dividePriceVal"),
    /** Dividend yield is calculated by the stock selection service itself */
    StockField_DivideRate(27, "divideRateVal"),
    /** stock market */
    StockField_Exchange(29, "exchange"),
    /** Turnover rate* (accurate to 3 decimal places, the excess will be discarded) For example, fill in the value range [0.005,0.01] */
    StockField_TurnoverRate(30, "turnoverRate"),
    /** Time to market */
    StockField_ListingDate(31, "listingDate"),
    /** Total share capital* */
    StockField_Share(33, "shares"),
    /** listing price* */
    StockField_ListingPrice(34, "listingPrice"),
    /** Latest price - issue price* */
    StockField_DiffBetweenLastPriceAndListPrice(36, "DiffBetweenLastPriceAndListPrice"),
    /** Earnings per share lyr=Last Year Ratio static price-earnings ratio */
    StockField_lyr_Eps(37, "lyrEps"),
    /** Open short position */
    StockField_Open_Short_Interest(38, "OpenShortInterestVal"),
    /** Open short position ratio = open short position volume / total equity */
    StockField_Open_Short_Interest_Ratio(39, "OpenShortInterestRatio"),
    /** Equity Ratio = Liability/Equity Total Liabilities/Shareholders */
    StockField_Equity_Ratio(40, "totalDebtToEquity"),
    /** Equity multiplier = Asset/Equity */
    StockField_Equity_Multiplier(41, "totalLiabilitiesToTotalAssets"),
    /** The latest number of shareholders */
    StockField_Holder_Nums(42, "holderNums"),
    /** The latest growth rate of the number of shareholders */
    StockField_Holder_Nums_Ratio(43, "holderRatio"),
    /** The average number of shares held by each account */
    StockField_Per_Hold_Nums(44, "perHolderNums"),
    /** The average amount of shares held by each account */
    StockField_Per_Hold_Money(45, "perHolderMoney"),
    /** Semi-annual growth rate of the average number of shares held by each account */
    StockField_HalfYear_Holder_Nums_Ratio(46, "HalfYearholderRatio"),
    /** Issue time - ETF */
    StockField_InceptionDate(47, "inceptionDate"),
    /** Purchase fee - ETF */
    StockField_CreationFee(48, "creationFee"),
    /** Management Fee - ETF */
    StockField_ManagementFee(49, "managementFee"),
    /** Proportion of top 10 constituent stocks - ETF */
    StockField_Top10_Composition_Rate(50, "Top10CompoRate"),
    /** Proportion of Top 15 Constituent Stocks - ETF */
    StockField_Top15_Composition_Rate(51, "Top15CompoRate"),
    /** Proportion of Top 20 Constituent Stocks - ETF */
    StockField_Top20_Composition_Rate(52, "Top20CompoRate"),
    /** Premium rate (discount rate) - ETF */
    StockField_DiscountPremium(53, "discountPremium"),
    /** asset size - net worth - ETF */
    StockField_Net_Worth_Aum(55, "aum"),
    /** Asset size - current price - ETF */
    StockField_assetSize(56, "assetSize"),
    /** Amplitude */
    StockField_Amplitude(57, "Amplitude"),
    /** Pre-market change rate */
    StockField_Pre_ChangeRate(58, "preChangeRate"),
    /** Intraday change rate */
    StockField_current_ChangeRate(59, "curChangeRate"),
    /** After-market change rate */
    StockField_Post_ChangeRate(60, "postChangeRate"),
    /** Component change - ETF */
    StockField_ETF_LastHoldingChangeDay(61, "LastHoldingChangeDay"),
    /** Holding count - ETF */
    StockField_ETF_HoldingCount(62, "etfHoldingCount"),
    /** Net income without cycle */
    StockField_Net_Income(63, "netIncomeVal"),
    ;

    @Getter
    private String value;
    @Getter
    private Integer index;

    StockField(Integer index, String value) {
        this.index = index;
        this.value = value;
    }

    public static StockField getTypeByValue(String value) {
        for (StockField item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    public static String getValueByIndex(Integer index) {
        for (StockField item : values()) {
            if (item.getIndex().equals(index)) {
                return item.getValue();
            }
        }
        return null;
    }

    public static Integer getIndexByValue(String value) {
        for (StockField item : values()) {
            if (item.getValue().equals(value)) {
                return item.getIndex();
            }
        }
        return null;
    }

    public static StockField getTypeByIndex(Integer index) {
        for (StockField item : values()) {
            if (item.getIndex().equals(index)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 获取所有的value
     */
    public static Set<String> getAllValues() {
        Set<String> retSet = new HashSet<>();
        for (StockField item : values()) {
            retSet.add(item.getValue());
        }
        return retSet;
    }
}