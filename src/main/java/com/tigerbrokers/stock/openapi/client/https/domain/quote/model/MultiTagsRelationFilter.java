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
}