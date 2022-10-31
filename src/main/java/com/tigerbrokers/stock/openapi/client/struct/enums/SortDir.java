package com.tigerbrokers.stock.openapi.client.struct.enums;

import lombok.Getter;

/**
 * Description: 排序方向
 *
 * @author kevin
 * @date 2022/10/27
 */
public enum SortDir {

    SortDir_No(0), // 不排序
    SortDir_Ascend(1), // 升序
    SortDir_Descend(2), // 降序
    ;

    @Getter
    private Integer value;

    SortDir(Integer value) {
        this.value = value;
    }

    /**
     * 是否降序
     */
    public static Boolean isDescDirection(Integer value) {
        if (0 == value) {
            return null;
        }
        return SortDir_Descend.value.equals(value);
    }
}