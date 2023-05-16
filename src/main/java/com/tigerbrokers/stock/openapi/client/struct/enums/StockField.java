package com.tigerbrokers.stock.openapi.client.struct.enums;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Description: 简单属性
 *
 * @author kevin
 * @date 2022/10/27
 */
public enum StockField {
    /** 股票代码*，不能填区间上下限值。 */
    /** 最新价*（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
    StockField_CurPrice(2, "latestPrice"),
    /** 买入价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
    StockField_BidPrice(3, "bidPrice"),
    /** 卖出价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
    StockField_AskPrice(4, "askPrice"),
    /** 今开价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
    StockField_OpenPrice(5, "open"),
    /** 昨收价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
    StockField_PreClosePrice(6, "preClose"),
    /** 最高价 */
    StockField_HighPrice(7, "high"),
    /** 最低价 */
    StockField_LowPrice(8, "low"),
    /** 盘前价*（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
    StockField_HourTradingPrePrice(9, "hourTradingPrePrice"),
    /** 盘后价*（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间 */
    StockField_HourTradingAfterPrice(10, "hourTradingAfterPrice"),
    /** 成交量* */
    StockField_Volume(11, "volume"),
    /** 成交额* */
    StockField_Amount(12, "amount"),
    /** 流通股本* */
    StockField_FloatShare(13, "floatShares"),
    /** 52周最高价格* */
    StockField_Week52High(14, "week52High"),
    /** 52周最低价格* */
    StockField_Week52Low(15, "week52Low"),
    /** 通市值* FloatMarketVal  自己计算 FloatShare* 当前价格 */
    StockField_FloatMarketVal(16, "floatMarketCap"),
    /** 总市值*  MarketVal  shares * 当前价格 */
    StockField_MarketValue(17, "marketValue"),
    /** 盘前涨跌幅   (curPrice-盘前左收）自己计算 最新价-close / close */
    StockField_preHourTradingChangeRate(18, "preHourTradingChangeRate"),
    /** 盘后涨跌幅 自己计算 */
    StockField_postHourTradingChangeRate(19, "postHourTradingChangeRate"),
    /** 每股收益 滚动市盈率 TTM=过去12个月  Last Twelve Month  通过hermes获取 eps */
    StockField_ttm_Eps(20, "ttmEps"),
    /** 量比*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    StockField_VolumeRatio(21, "volumeRatio"),
    /** 委比*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    StockField_BidAskRatio(22, "committee"),
    /** 下次财报日期 * */
    StockField_EarningDate(23, "earningDate"),
    /** 市盈率* TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    StockField_PeTTM(24, "peRate"),
    /** 股息   hermes $ */
    StockField_DividePrice(26, "dividePriceVal"),
    /** 股息收益率 选股服务自身计算 */
    StockField_DivideRate(27, "divideRateVal"),
    /** 股票交易市场 */
    StockField_Exchange(29, "exchange"),
    /** 换手率*（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.005,0.01] 值区间 */
    StockField_TurnoverRate(30, "turnoverRate"),
    /** 上市时间 */
    StockField_ListingDate(31, "listingDate"),
    /** 总股本* */
    StockField_Share(33, "shares"),
    /** 上市价格* */
    StockField_ListingPrice(34, "listingPrice"),
    /** 最新价-发行价* */
    StockField_DiffBetweenLastPriceAndListPrice(36, "DiffBetweenLastPriceAndListPrice"),
    /** 每股收益 lyr=Last Year Ratio 静态市盈率 */
    StockField_lyr_Eps(37, "lyrEps"),
    /** 未平仓做空量 */
    StockField_Open_Short_Interest(38, "OpenShortInterestVal"),
    /** 未平仓做空比例 = 未平仓做空量/总股本 */
    StockField_Open_Short_Interest_Ratio(39, "OpenShortInterestRatio"),
    /** 产权比率 = Liability/Equity 总负债/股东 */
    StockField_Equity_Ratio(40, "totalDebtToEquity"),
    /** 权益乘数 = Asset/Equity */
    StockField_Equity_Multiplier(41, "totalLiabilitiesToTotalAssets"),
    /** 最新股东数 */
    StockField_Holder_Nums(42, "holderNums"),
    /** 最新股东户数增长率 */
    StockField_Holder_Nums_Ratio(43, "holderRatio"),
    /** 户均持股数量 */
    StockField_Per_Hold_Nums(44, "perHolderNums"),
    /** 户均持股金额 */
    StockField_Per_Hold_Money(45, "perHolderMoney"),
    /** 户均持股数半年增长率 */
    StockField_HalfYear_Holder_Nums_Ratio(46, "HalfYearholderRatio"),
    /** 发行时间 - ETF */
    StockField_InceptionDate(47, "inceptionDate"),
    /** 申购费用 - ETF */
    StockField_CreationFee(48, "creationFee"),
    /** 管理费用 - ETF */
    StockField_ManagementFee(49, "managementFee"),
    /** 成分股Top10 占比 - ETF */
    StockField_Top10_Composition_Rate(50, "Top10CompoRate"),
    /** 成分股Top15 占比 - ETF */
    StockField_Top15_Composition_Rate(51, "Top15CompoRate"),
    /** 成分股Top20 占比 - ETF */
    StockField_Top20_Composition_Rate(52, "Top20CompoRate"),
    /** 溢价率(折扣率) - ETF */
    StockField_DiscountPremium(53, "discountPremium"),
    /** 资产规模-净值 - ETF */
    StockField_Net_Worth_Aum(55, "aum"),
    /** 资产规模-现价 - ETF */
    StockField_assetSize(56, "assetSize"),
    /** 振幅 */
    StockField_Amplitude(57, "Amplitude"),
    /** 盘前涨跌幅 */
    StockField_Pre_ChangeRate(58, "preChangeRate"),
    /** 盘中涨跌幅 */
    StockField_current_ChangeRate(59, "curChangeRate"),
    /** 盘后涨跌幅 */
    StockField_Post_ChangeRate(60, "postChangeRate"),
    /** 成分变动 - etf */
    StockField_ETF_LastHoldingChangeDay(61, "LastHoldingChangeDay"),
    /** 持仓数量 - etf */
    StockField_ETF_HoldingCount(62, "etfHoldingCount"),
    /** 净利润 不带周期 */
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