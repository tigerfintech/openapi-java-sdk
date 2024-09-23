package com.tigerbrokers.stock.openapi.client.struct.enums;

import lombok.Getter;

/**
 * Description: 选股对应的数据返回类型
 *
 * @author kevin
 * @date 2022/10/27
 */
public enum FilterValType {
    /** 数值类型 **/
    Value(0),
    /** 排名类型 **/
    Rank(2),
    ;

    @Getter
    private Integer value;

    FilterValType(Integer value) {
        this.value = value;
    }
}