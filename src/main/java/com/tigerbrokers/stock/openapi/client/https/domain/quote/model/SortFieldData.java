package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

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
  private Integer fieldName;
  /** 排序属性所属类别 参考 FieldBelongType */
  private FieldBelongType fieldType;
  /** SortDir 排序方向，默认不排序。 */
  private SortDir sortDir;

  public static SortFieldData.SortFieldDataBuilder builder() {
    return new SortFieldData.SortFieldDataBuilder();
  }

  public Integer getFieldName() {
    return this.fieldName;
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

  public void setFieldType(FieldBelongType fieldType) {
    this.fieldType = fieldType;
  }

  public void setSortDir(SortDir sortDir) {
    this.sortDir = sortDir;
  }

  public SortFieldData(Integer fieldName, FieldBelongType fieldType, SortDir sortDir) {
    this.fieldName = fieldName;
    this.fieldType = fieldType;
    this.sortDir = sortDir;
  }

  public SortFieldData() {
  }

  public static class SortFieldDataBuilder {
    private Integer fieldName;
    private FieldBelongType fieldType;
    private SortDir sortDir;

    SortFieldDataBuilder() {
    }

    public SortFieldData.SortFieldDataBuilder fieldName(Integer fieldName) {
      this.fieldName = fieldName;
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
      return new SortFieldData(this.fieldName, this.fieldType, this.sortDir);
    }

    @Override
    public String toString() {
      return "SortFieldData.SortFieldDataBuilder(fieldName=" + this.fieldName + ", fieldType=" + this.fieldType + ", sortDir=" + this.sortDir + ")";
    }
  }
}