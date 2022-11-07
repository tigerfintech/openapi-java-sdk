package com.tigerbrokers.stock.openapi.client.struct;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/31
 */
public class MarketIndicatorValue implements Serializable {

    /** 指标索引 **/
    private Integer index;
    /** 指标名称 **/
    private String name;
    /** 指标值 **/
    private Object value;

    public MarketIndicatorValue() {
    }

    public MarketIndicatorValue(Integer index, String name, Object value) {
        this.index = index;
        this.name = name;
        this.value = value;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Double doubleValue() {
        if (this.value == null) {
            return null;
        }
        return Double.valueOf(this.value.toString());
    }

    @Override
    public String toString() {
        return "MarketIndicatorValue{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}