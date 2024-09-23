package com.tigerbrokers.stock.openapi.client.struct;

import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import java.util.Objects;

/**
 * Description:
 * Created by lijiawen on 2018/05/24.
 */
public final class TagValue {

  public String tag;
  public String value;

  public TagValue() {

  }

  public TagValue(String tag, String value) {
    this.tag = tag;
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagValue tagValue = (TagValue) o;
    return Objects.equals(tag, tagValue.tag) &&
        Objects.equals(value, tagValue.value);
  }

  @Override
  public int hashCode() {

    return Objects.hash(tag, value);
  }

  public static TagValue buildTagValue(String tag, Object value) {
    if (StringUtils.isEmpty(tag) || value == null) {
      return null;
    }
    return new TagValue(tag, value.toString());
  }
}
