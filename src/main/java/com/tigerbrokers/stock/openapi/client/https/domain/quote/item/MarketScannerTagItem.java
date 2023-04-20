package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.MultiTagField;
import java.io.Serializable;
import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2023/04/20
 */
public class MarketScannerTagItem implements Serializable {

  /**
   * multi tag field
   */
  private MultiTagField multiTagField;
  /**
   * multi tag field's tagList
   */
  private List<TagValue> tagList;

  public MultiTagField getMultiTagField() {
    return multiTagField;
  }

  public void setMultiTagField(MultiTagField multiTagField) {
    this.multiTagField = multiTagField;
  }

  public List<TagValue> getTagList() {
    return tagList;
  }

  public void setTagList(List<TagValue> tagList) {
    this.tagList = tagList;
  }
}
