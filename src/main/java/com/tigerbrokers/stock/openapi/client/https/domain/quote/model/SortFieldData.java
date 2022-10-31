package com.tigerbrokers.stock.openapi.client.https.domain.quote.model;

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
  private Integer fieldType;
  /** SortDir 排序方向，默认不排序。 */
  private int sortDir;
}