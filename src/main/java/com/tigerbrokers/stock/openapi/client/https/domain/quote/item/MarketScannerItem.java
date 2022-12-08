package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.struct.MarketIndicatorValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public class MarketScannerItem implements Serializable {
    /** 市场 */
    private Market market;
    private String symbol;
    /** 筛选后的:简单指标属性数据 */
    private List<MarketIndicatorValue> baseDataList;
    /** 筛选后的:累积指标属性数据 */
    private List<MarketIndicatorValue> accumulateDataList;
    /** 筛选后的:财务指标属性数据 */
    private List<MarketIndicatorValue> financialDataList;
    /** 筛选后的:多标签层面过滤器 */
    private List<MarketIndicatorValue> multiTagDataList;

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<MarketIndicatorValue> getBaseDataList() {
        return baseDataList;
    }

    public void setBaseDataList(List<MarketIndicatorValue> baseDataList) {
        this.baseDataList = baseDataList;
    }

    public List<MarketIndicatorValue> getAccumulateDataList() {
        return accumulateDataList;
    }

    public void setAccumulateDataList(List<MarketIndicatorValue> accumulateDataList) {
        this.accumulateDataList = accumulateDataList;
    }

    public List<MarketIndicatorValue> getFinancialDataList() {
        return financialDataList;
    }

    public void setFinancialDataList(List<MarketIndicatorValue> financialDataList) {
        this.financialDataList = financialDataList;
    }

    public List<MarketIndicatorValue> getMultiTagDataList() {
        return multiTagDataList;
    }

    public void setMultiTagDataList(List<MarketIndicatorValue> multiTagDataList) {
        this.multiTagDataList = multiTagDataList;
    }
}
