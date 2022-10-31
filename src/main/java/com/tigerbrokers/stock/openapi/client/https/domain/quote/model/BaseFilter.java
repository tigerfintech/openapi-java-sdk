package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */

public class BaseFilter implements Serializable {

    /** StockField 简单属性 */
    private Integer fieldName;
    /** 区间下限（闭区间），不传代表下限为 -∞ */
    private Double filterMin;
    /** 区间上限（闭区间），不传代表上限为 +∞ */
    private Double filterMax;
    /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选 */
    private boolean isNoFilter = false;
    /** filterMin \ filterMax 对应单位，默认为数值型  该字段为过滤字段类型，参考 FilterValType类型 */
    private int filterValType = 0;
    /** SortDir 排序方向，默认不排序。仅仅 filterValType = Rank 此参数生效！ */
    private int sortDir;

    public static BaseFilter.BaseFilterBuilder builder() {
        return new BaseFilter.BaseFilterBuilder();
    }

    public Integer getFieldName() {
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

    public int getFilterValType() {
        return this.filterValType;
    }

    public int getSortDir() {
        return this.sortDir;
    }

    public void setFieldName(Integer fieldName) {
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

    public void setFilterValType(int filterValType) {
        this.filterValType = filterValType;
    }

    public void setSortDir(int sortDir) {
        this.sortDir = sortDir;
    }


    @Override
    public String toString() {
        return "BaseFilter(fieldName=" + this.getFieldName() + ", filterMin=" + this.getFilterMin() + ", filterMax=" + this.getFilterMax() + ", isNoFilter=" + this.isNoFilter() + ", filterValType=" + this.getFilterValType() + ", sortDir=" + this.getSortDir() + ")";
    }

    public BaseFilter(Integer fieldName, Double filterMin, Double filterMax, boolean isNoFilter, int filterValType, int sortDir) {
        this.fieldName = fieldName;
        this.filterMin = filterMin;
        this.filterMax = filterMax;
        this.isNoFilter = isNoFilter;
        this.filterValType = filterValType;
        this.sortDir = sortDir;
    }

    public BaseFilter() {
    }

    public static class BaseFilterBuilder {
        private Integer fieldName;
        private Double filterMin;
        private Double filterMax;
        private boolean isNoFilter;
        private int filterValType;
        private int sortDir;

        BaseFilterBuilder() {
        }

        public BaseFilter.BaseFilterBuilder fieldName(Integer fieldName) {
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

        public BaseFilter.BaseFilterBuilder filterValType(int filterValType) {
            this.filterValType = filterValType;
            return this;
        }

        public BaseFilter.BaseFilterBuilder sortDir(int sortDir) {
            this.sortDir = sortDir;
            return this;
        }

        public BaseFilter build() {
            return new BaseFilter(this.fieldName, this.filterMin, this.filterMax, this.isNoFilter, this.filterValType, this.sortDir);
        }

        @Override
        public String toString() {
            return "BaseFilter.BaseFilterBuilder(fieldName=" + this.fieldName + ", filterMin=" + this.filterMin + ", filterMax=" + this.filterMax + ", isNoFilter=" + this.isNoFilter + ", filterValType=" + this.filterValType + ", sortDir=" + this.sortDir + ")";
        }
    }
}