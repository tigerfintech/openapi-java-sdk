package com.tigerbrokers.stock.openapi.client.struct.enums;

import lombok.Getter;

/**
 * Description: sort direction
 *
 * @author kevin
 * @date 2022/10/27
 */
public enum SortDir {

    SortDir_No(0), // no sort
    SortDir_Ascend(1), // sort by ascend
    SortDir_Descend(2), // sort by descend
    ;

    @Getter
    private Integer value;

    SortDir(Integer value) {
        this.value = value;
    }

    /**
     * Is it in descending order
     */
    public static Boolean isDescDirection(Integer value) {
        if (0 == value) {
            return null;
        }
        return SortDir_Descend.value.equals(value);
    }
}