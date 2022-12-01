package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.StockField;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */

public class BaseFilter implements Serializable {

    /** StockField 简单属性 */
    @JSONField(name = "field_name")
    private StockField fieldName;
    /** 区间下限（闭区间），不传代表下限为 -∞ */
    @JSONField(name = "filter_min")
    private Double filterMin;
    /** 区间上限（闭区间），不传代表上限为 +∞ */
    @JSONField(name = "filter_max")
    private Double filterMax;
    /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选 */
    @JSONField(name = "is_no_filter")
    private boolean isNoFilter = false;

    public static BaseFilter.BaseFilterBuilder builder() {
        return new BaseFilter.BaseFilterBuilder();
    }

    public StockField getFieldName() {
        return this.fieldName;
    }

    public Double getFilterMin() {
        return this.filterMin;
    }

    public Double getFilterMax() {
        return this.filterMax;
    }

    public boolean isNoFilter() {
        return this.isNoFilter;
    }

    public void setFieldName(StockField fieldName) {
        this.fieldName = fieldName;
    }

    public void setFilterMin(Double filterMin) {
        this.filterMin = filterMin;
    }

    public void setFilterMax(Double filterMax) {
        this.filterMax = filterMax;
    }

    public void setNoFilter(boolean isNoFilter) {
        this.isNoFilter = isNoFilter;
    }

    @Override
    public String toString() {
        return "BaseFilter(fieldName=" + this.getFieldName() + ", filterMin=" + this.getFilterMin() + ", filterMax=" + this.getFilterMax() + ", isNoFilter=" + this.isNoFilter() + ")";
    }

    public BaseFilter(StockField fieldName, Double filterMin, Double filterMax, boolean isNoFilter) {
        this.fieldName = fieldName;
        this.filterMin = filterMin;
        this.filterMax = filterMax;
        this.isNoFilter = isNoFilter;
    }

    public BaseFilter() {
    }

    public static class BaseFilterBuilder {
        private StockField fieldName;
        private Double filterMin;
        private Double filterMax;
        private boolean isNoFilter;

        BaseFilterBuilder() {
        }

        public BaseFilter.BaseFilterBuilder fieldName(StockField fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public BaseFilter.BaseFilterBuilder filterMin(Double filterMin) {
            this.filterMin = filterMin;
            return this;
        }

        public BaseFilter.BaseFilterBuilder filterMax(Double filterMax) {
            this.filterMax = filterMax;
            return this;
        }

        public BaseFilter.BaseFilterBuilder isNoFilter(boolean isNoFilter) {
            this.isNoFilter = isNoFilter;
            return this;
        }

        public BaseFilter build() {
            return new BaseFilter(this.fieldName, this.filterMin, this.filterMax, this.isNoFilter);
        }

        @Override
        public String toString() {
            return "BaseFilter.BaseFilterBuilder(fieldName=" + this.fieldName + ", filterMin=" + this.filterMin + ", filterMax=" + this.filterMax + ", isNoFilter=" + this.isNoFilter + ")";
        }
    }
}