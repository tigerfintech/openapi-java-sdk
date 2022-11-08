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
}