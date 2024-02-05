package com.tigerbrokers.stock.openapi.client.struct.enums;

import lombok.Getter;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public enum StockType {

    STOCK(0), // stock
    ETF(1), // ETF
    ;

    @Getter
    private Integer value;

    StockType(Integer value) {
        this.value = value;
    }
}
