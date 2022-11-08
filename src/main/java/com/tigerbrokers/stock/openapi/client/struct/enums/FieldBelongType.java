package com.tigerbrokers.stock.openapi.client.struct.enums;

import lombok.Getter;

/**
 * Description: 选股排序字段对应的类别
 *
 * @author kevin
 * @date 2022/11/08
 */
public enum FieldBelongType {

    StockField_Type(1),
    AccumulateField_Type(2),
    FinancialField_Type(3),
    PatternField_Type(4),
    MultiTagField_Type(5),
    ;

    @Getter
    private Integer value;

    FieldBelongType(Integer value) {
        this.value = value;
    }

    public static FieldBelongType getIndexByValue(Integer value) {
        for (FieldBelongType item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
