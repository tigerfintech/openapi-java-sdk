package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.AccumulateField;
import com.tigerbrokers.stock.openapi.client.struct.enums.AccumulatePeriod;
import com.tigerbrokers.stock.openapi.client.struct.enums.FilterValType;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public class AccumulateFilter implements Serializable {

    /** AccumulateField 累积属性 */
    @JSONField(name = "field_name")
    private AccumulateField fieldName;
    /** 区间下限（闭区间），不传代表下限为 -∞ 如果为百分位数，不需要加%，例如10%，数值为10即可 */
    @JSONField(name = "filter_min")
    private Double filterMin;
    /** 区间上限（闭区间），不传代表上限为 +∞ */
    @JSONField(name = "filter_max")
    private Double filterMax;
    /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选 */
    @JSONField(name = "is_no_filter")
    private boolean isNoFilter = false;
    /** 时间周期 AccumulatePeriod 非必传项 */
    private AccumulatePeriod period;

    public AccumulateFilter(AccumulateField fieldName, Double filterMin, Double filterMax, boolean isNoFilter, AccumulatePeriod period) {
        this.fieldName = fieldName;
        this.filterMin = filterMin;
        this.filterMax = filterMax;
        this.isNoFilter = isNoFilter;
        this.period = period;
    }

    public AccumulateFilter() {
    }

    public AccumulateField getFieldName() {
        return fieldName;
    }

    public void setFieldName(AccumulateField fieldName) {
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

    public AccumulatePeriod getPeriod() {
        return period;
    }

    public void setPeriod(AccumulatePeriod period) {
        this.period = period;
    }

    public static AccumulateFilter.AccumulateFilterBuilder builder() {
        return new AccumulateFilter.AccumulateFilterBuilder();
    }


    public static class AccumulateFilterBuilder {
        private AccumulateField fieldName;
        private Double filterMin;
        private Double filterMax;
        private boolean isNoFilter;
        private AccumulatePeriod period;

        AccumulateFilterBuilder() {
        }

        public AccumulateFilter.AccumulateFilterBuilder fieldName(AccumulateField fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public AccumulateFilter.AccumulateFilterBuilder filterMin(Double filterMin) {
            this.filterMin = filterMin;
            return this;
        }

        public AccumulateFilter.AccumulateFilterBuilder filterMax(Double filterMax) {
            this.filterMax = filterMax;
            return this;
        }

        public AccumulateFilter.AccumulateFilterBuilder isNoFilter(boolean isNoFilter) {
            this.isNoFilter = isNoFilter;
            return this;
        }

        public AccumulateFilter.AccumulateFilterBuilder period(AccumulatePeriod period) {
            this.period = period;
            return this;
        }

        public AccumulateFilter build() {
            return new AccumulateFilter(this.fieldName, this.filterMin, this.filterMax, this.isNoFilter, this.period);
        }

        @Override
        public String toString() {
            return "AccumulateFilter.AccumulateFilterBuilder(fieldName=" + this.fieldName + ", filterMin=" + this.filterMin + ", filterMax=" + this.filterMax + ", isNoFilter=" + this.isNoFilter + ", period=" + this.period + ")";
        }
    }
}