package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.FinancialField;
import com.tigerbrokers.stock.openapi.client.struct.enums.FinancialPeriod;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public class FinancialFilter implements Serializable {

    /** FinancialField 财务属性 */
    @JSONField(name = "field_name")
    private FinancialField fieldName;
    /** 区间下限（闭区间），不传代表下限为 -∞ 如果为百分位数，不需要加%，例如10%，数值为10即可 */
    @JSONField(name = "filter_min")
    private Double filterMin;
    /** 区间上限（闭区间），不传代表上限为 +∞ */
    @JSONField(name = "filter_max")
    private Double filterMax;
    /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选 */
    @JSONField(name = "is_no_filter")
    private boolean isNoFilter = false;
    /** FinancialQuarter 财报累积时间 */
    private FinancialPeriod quarter;

    public FinancialFilter(FinancialField fieldName, Double filterMin, Double filterMax, boolean isNoFilter, FinancialPeriod quarter) {
        this.fieldName = fieldName;
        this.filterMin = filterMin;
        this.filterMax = filterMax;
        this.isNoFilter = isNoFilter;
        this.quarter = quarter;
    }

    public FinancialFilter() {
    }

    public FinancialField getFieldName() {
        return fieldName;
    }

    public void setFieldName(FinancialField fieldName) {
        this.fieldName = fieldName;
    }

    public Double getFilterMin() {
        return filterMin;
    }

    public void setFilterMin(Double filterMin) {
        this.filterMin = filterMin;
    }

    public Double getFilterMax() {
        return filterMax;
    }

    public void setFilterMax(Double filterMax) {
        this.filterMax = filterMax;
    }

    public boolean isNoFilter() {
        return isNoFilter;
    }

    public void setNoFilter(boolean noFilter) {
        isNoFilter = noFilter;
    }

    public FinancialPeriod getQuarter() {
        return quarter;
    }

    public void setQuarter(FinancialPeriod quarter) {
        this.quarter = quarter;
    }

    public static FinancialFilter.FinancialFilterBuilder builder() {
        return new FinancialFilter.FinancialFilterBuilder();
    }

    public static class FinancialFilterBuilder {
        private FinancialField fieldName;
        private Double filterMin;
        private Double filterMax;
        private boolean isNoFilter;
        private FinancialPeriod quarter;

        FinancialFilterBuilder() {
        }

        public FinancialFilter.FinancialFilterBuilder fieldName(FinancialField fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public FinancialFilter.FinancialFilterBuilder filterMin(Double filterMin) {
            this.filterMin = filterMin;
            return this;
        }

        public FinancialFilter.FinancialFilterBuilder filterMax(Double filterMax) {
            this.filterMax = filterMax;
            return this;
        }

        public FinancialFilter.FinancialFilterBuilder isNoFilter(boolean isNoFilter) {
            this.isNoFilter = isNoFilter;
            return this;
        }

        public FinancialFilter.FinancialFilterBuilder quarter(FinancialPeriod quarter) {
            this.quarter = quarter;
            return this;
        }

        public FinancialFilter build() {
            return new FinancialFilter(this.fieldName, this.filterMin, this.filterMax, this.isNoFilter, this.quarter);
        }

        @Override
        public String toString() {
            return "FinancialFilter.FinancialFilterBuilder(fieldName=" + this.fieldName + ", filterMin=" + this.filterMin + ", filterMax=" + this.filterMax + ", isNoFilter=" + this.isNoFilter + ", quarter=" + this.quarter + ")";
        }
    }
}
