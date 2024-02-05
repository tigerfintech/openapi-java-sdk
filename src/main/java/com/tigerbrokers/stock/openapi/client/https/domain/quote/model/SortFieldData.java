package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.struct.enums.FieldBelongType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SortDir;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public class SortFieldData implements Serializable {

  /** 排序属性 */
  @JSONField(name = "field_name")
  private Integer fieldName;
  /** 时间周期 AccumulatePeriod, 非必传项-只有排序为Accumulate相关字段，需要此字段 */
  private Integer period;
  /** 排序属性所属类别 参考 FieldBelongType */
  @JSONField(name = "field_type")
  private FieldBelongType fieldType;
  /** SortDir 排序方向，默认不排序。 */
  @JSONField(name = "sort_dir")
  private SortDir sortDir;

  public static SortFieldData.SortFieldDataBuilder builder() {
    return new SortFieldData.SortFieldDataBuilder();
  }

  public Integer getFieldName() {
    return this.fieldName;
  }

  public Integer getPeriod() {
    return period;
  }

  public FieldBelongType getFieldType() {
    return this.fieldType;
  }

  public SortDir getSortDir() {
    return this.sortDir;
  }

  public void setFieldName(Integer fieldName) {
    this.fieldName = fieldName;
  }

  public void setPeriod(Integer period) {
    this.period = period;
  }

  public void setFieldType(FieldBelongType fieldType) {
    this.fieldType = fieldType;
  }

  public void setSortDir(SortDir sortDir) {
    this.sortDir = sortDir;
  }

  public SortFieldData(Integer fieldName, Integer period, FieldBelongType fieldType, SortDir sortDir) {
    this.fieldName = fieldName;
    this.period = period;
    this.fieldType = fieldType;
    this.sortDir = sortDir;
  }

  public SortFieldData() {
  }

  public static class SortFieldDataBuilder {
    private Integer fieldName;
    private Integer period;
    private FieldBelongType fieldType;
    private SortDir sortDir;

    SortFieldDataBuilder() {
    }

    public SortFieldData.SortFieldDataBuilder fieldName(Integer fieldName) {
      this.fieldName = fieldName;
      return this;
    }

    public SortFieldData.SortFieldDataBuilder period(Integer period) {
      this.period = period;
      return this;
    }

    public SortFieldData.SortFieldDataBuilder fieldType(FieldBelongType fieldType) {
      this.fieldType = fieldType;
      return this;
    }

    public SortFieldData.SortFieldDataBuilder sortDir(SortDir sortDir) {
      this.sortDir = sortDir;
      return this;
    }

    public SortFieldData build() {
      return new SortFieldData(this.fieldName, this.period, this.fieldType, this.sortDir);
    }

    @Override
    public String toString() {
      return "SortFieldDataBuilder{" +
              "fieldName=" + fieldName +
              ", period=" + period +
              ", fieldType=" + fieldType +
              ", sortDir=" + sortDir +
              '}';
    }
  }
}