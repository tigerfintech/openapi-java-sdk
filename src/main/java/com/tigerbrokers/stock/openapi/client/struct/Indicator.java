package com.tigerbrokers.stock.openapi.client.struct;

import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 * Created by bean on 2023/06/15.
 */
public interface Indicator {
  String getValue();

  public static Set<String> getValues(Set<Indicator> indicators) {
    Set<String> values = new HashSet<>();
    if (indicators != null) {
      for (Indicator indicator : indicators) {
        values.add(indicator.getValue());
      }
    }
    return values;
  }
}
