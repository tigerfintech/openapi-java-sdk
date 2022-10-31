package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.tigerbrokers.stock.openapi.client.struct.enums.FilterValType;
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
    private Integer fieldName;
    /** 区间下限（闭区间），不传代表下限为 -∞ 如果为百分位数，不需要加%，例如10%，数值为10即可 */
    private Double filterMin;
    /** 区间上限（闭区间），不传代表上限为 +∞ */
    private Double filterMax;
    /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选 */
    private boolean isNoFilter = false;
    /** FinancialQuarter 财报累积时间 */
    private FinancialPeriod quarter;
    /** filterMin \ filterMax 对应单位，默认为数值型  该字段为过滤字段类型，参考 FilterValType类型 */
    private int filterValType = 0;
    /** SortDir 排序方向，默认不排序。仅仅 filterValType = Rank 此参数生效！ */
    private int sortDir;

    public FinancialFilter(Integer fieldName, Double filterMin, Double filterMax, boolean isNoFilter, FinancialPeriod quarter, int filterValType, int sortDir) {
        this.fieldName = fieldName;
        this.filterMin = filterMin;
        this.filterMax = filterMax;
        this.isNoFilter = isNoFilter;
        this.quarter = quarter;
        this.filterValType = filterValType;
        this.sortDir = sortDir;
    }

    public FinancialFilter() {
    }

    /**
     * 验证参数是否传递ok
     */
    public Boolean checkParamsIsOk() {
        // note 不进行筛选
        if (isNoFilter) {
            return true;
        }
        if (null == fieldName) {
            return false;
        }
        if (FilterValType.Rank.getValue().equals(filterValType)) {
            if (null == this.getFilterMax()
                    || null == this.getFilterMin()
                    || this.getFilterMin() < 0
                    || this.getFilterMax() < 0) {
                return false;
            }
        }
        return true;
    }

    public Integer getFieldName() {
        return fieldName;
    }

    public void setFieldName(Integer fieldName) {
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

    public int getFilterValType() {
        return filterValType;
    }

    public void setFilterValType(int filterValType) {
        this.filterValType = filterValType;
    }

    public int getSortDir() {
        return sortDir;
    }

    public void setSortDir(int sortDir) {
        this.sortDir = sortDir;
    }

    public static FinancialFilter.FinancialFilterBuilder builder() {
        return new FinancialFilter.FinancialFilterBuilder();
    }

    public static class FinancialFilterBuilder {
        private Integer fieldName;
        private Double filterMin;
        private Double filterMax;
        private boolean isNoFilter;
        private FinancialPeriod quarter;
        private int filterValType;
        private int sortDir;

        FinancialFilterBuilder() {
        }

        public FinancialFilter.FinancialFilterBuilder fieldName(Integer fieldName) {
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

        public FinancialFilter.FinancialFilterBuilder filterValType(int filterValType) {
            this.filterValType = filterValType;
            return this;
        }

        public FinancialFilter.FinancialFilterBuilder sortDir(int sortDir) {
            this.sortDir = sortDir;
            return this;
        }

        public FinancialFilter build() {
            return new FinancialFilter(this.fieldName, this.filterMin, this.filterMax, this.isNoFilter, this.quarter, this.filterValType, this.sortDir);
        }

        @Override
        public String toString() {
            return "FinancialFilter.FinancialFilterBuilder(fieldName=" + this.fieldName + ", filterMin=" + this.filterMin + ", filterMax=" + this.filterMax + ", isNoFilter=" + this.isNoFilter + ", quarter=" + this.quarter + ", filterValType=" + this.filterValType + ", sortDir=" + this.sortDir + ")";
        }
    }
}
