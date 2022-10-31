package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public class MultiTagsRelationFilter implements Serializable {

    /** 属性id 针对 XXXMultiTagField 获取数值 */
    private Integer fieldName;
    /** 多个tag列表 */
    private List<String> tagList;
    /** 该字段是否不需要筛选，True：不筛选，False：筛选。不传默认筛选 */
    private boolean isNoFilter = false;

    public MultiTagsRelationFilter(Integer fieldName, List<String> tagList, boolean isNoFilter) {
        this.fieldName = fieldName;
        this.tagList = tagList;
        this.isNoFilter = isNoFilter;
    }

    public MultiTagsRelationFilter() {
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
        return true;
    }

    public Integer getFieldName() {
        return fieldName;
    }

    public void setFieldName(Integer fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public boolean isNoFilter() {
        return isNoFilter;
    }

    public void setNoFilter(boolean noFilter) {
        isNoFilter = noFilter;
    }

    public static MultiTagsRelationFilter.MultiTagsRelationFilterBuilder builder() {
        return new MultiTagsRelationFilter.MultiTagsRelationFilterBuilder();
    }

    public static class MultiTagsRelationFilterBuilder {
        private Integer fieldName;
        private List<String> tagList;
        private boolean isNoFilter;

        MultiTagsRelationFilterBuilder() {
        }

        public MultiTagsRelationFilter.MultiTagsRelationFilterBuilder fieldName(Integer fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public MultiTagsRelationFilter.MultiTagsRelationFilterBuilder tagList(List<String> tagList) {
            this.tagList = tagList;
            return this;
        }

        public MultiTagsRelationFilter.MultiTagsRelationFilterBuilder isNoFilter(boolean isNoFilter) {
            this.isNoFilter = isNoFilter;
            return this;
        }

        public MultiTagsRelationFilter build() {
            return new MultiTagsRelationFilter(this.fieldName, this.tagList, this.isNoFilter);
        }

        @Override
        public String toString() {
            return "MultiTagsRelationFilter.MultiTagsRelationFilterBuilder(fieldName=" + this.fieldName + ", tagList=" + this.tagList + ", isNoFilter=" + this.isNoFilter + ")";
        }
    }


}