package com.tigerbrokers.stock.openapi.client.https.domain.quote.item;

import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import java.io.Serializable;
import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2023/04/20
 */
public class MarketScannerTagItem implements Serializable {

  private String market;
  /**
   * multi tag field
   */
  private String multiTagField;
  /**
   * multi tag field's tagList
   */
  private List<TagValue> tagList;

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getMultiTagField() {
    return multiTagField;
  }

  public void setMultiTagField(String multiTagField) {
    this.multiTagField = multiTagField;
  }

  public List<TagValue> getTagList() {
    return tagList;
  }

  public void setTagList(List<TagValue> tagList) {
    this.tagList = tagList;
  }
}
