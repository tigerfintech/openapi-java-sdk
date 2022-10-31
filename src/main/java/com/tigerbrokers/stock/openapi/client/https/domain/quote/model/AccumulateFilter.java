package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

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
    private Integer fieldName;
    /** 区间下限（闭区间），不传代表下限为 -∞ 如果为百分位数，不需要加%，例如10%，数值为10即可 */
    private Double filterMin;
    /** 区间上限（闭区间），不传代表上限为 +∞ */
    private Double filterMax;
    /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选 */
    private boolean isNoFilter = false;
    /** 时间周期 AccumulatePeriod 非必传项 */
    private Integer period;
    /** filterMin \ filterMax 对应单位，默认为数值型  该字段为过滤字段类型，参考 FilterValType类型 */
    private int filterValType = 0;
    /** SortDir 排序方向，默认不排序。仅仅 filterValType = Rank 此参数生效！ */
    private int sortDir;

    public AccumulateFilter(Integer fieldName, Double filterMin, Double filterMax, boolean isNoFilter, Integer period, int filterValType, int sortDir) {
        this.fieldName = fieldName;
        this.filterMin = filterMin;
        this.filterMax = filterMax;
        this.isNoFilter = isNoFilter;
        this.period = period;
        this.filterValType = filterValType;
        this.sortDir = sortDir;
    }

    public AccumulateFilter() {
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

    /**
     * 获取累计属性 对应的时间周期字段，需要进行拼接 时间周期
     * 例如：changeRate_20_days
     * @return
     */
    public String getQueryFieldName() {
        String retFieldName;
        if (null != this.getPeriod()) {
            retFieldName =
                    new StringBuilder().append(AccumulateField.getValueByIndex(this.getFieldName()))
                            .append(AccumulatePeriod.getSuffixByIndex(this.getPeriod()))
                            .toString();
        } else {
            retFieldName = AccumulateField.getValueByIndex(this.getFieldName());
        }
        return retFieldName;
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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
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


    public static AccumulateFilter.AccumulateFilterBuilder builder() {
        return new AccumulateFilter.AccumulateFilterBuilder();
    }


    public static class AccumulateFilterBuilder {
        private Integer fieldName;
        private Double filterMin;
        private Double filterMax;
        private boolean isNoFilter;
        private Integer period;
        private int filterValType;
        private int sortDir;

        AccumulateFilterBuilder() {
        }

        public AccumulateFilter.AccumulateFilterBuilder fieldName(Integer fieldName) {
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

        public AccumulateFilter.AccumulateFilterBuilder period(Integer period) {
            this.period = period;
            return this;
        }

        public AccumulateFilter.AccumulateFilterBuilder filterValType(int filterValType) {
            this.filterValType = filterValType;
            return this;
        }

        public AccumulateFilter.AccumulateFilterBuilder sortDir(int sortDir) {
            this.sortDir = sortDir;
            return this;
        }

        public AccumulateFilter build() {
            return new AccumulateFilter(this.fieldName, this.filterMin, this.filterMax, this.isNoFilter, this.period, this.filterValType, this.sortDir);
        }

        @Override
        public String toString() {
            return "AccumulateFilter.AccumulateFilterBuilder(fieldName=" + this.fieldName + ", filterMin=" + this.filterMin + ", filterMax=" + this.filterMax + ", isNoFilter=" + this.isNoFilter + ", period=" + this.period + ", filterValType=" + this.filterValType + ", sortDir=" + this.sortDir + ")";
        }
    }
}