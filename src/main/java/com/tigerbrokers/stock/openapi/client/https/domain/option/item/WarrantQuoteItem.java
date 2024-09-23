package com.tigerbrokers.stock.openapi.client.https.domain.option.item;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Created on 2023/02/02.
 */
public class WarrantQuoteItem implements Serializable {
  private static final long serialVersionUID = 1L;

  private List<WarrantQuote> items;

  public List<WarrantQuote> getItems() {
    return items;
  }

  public void setItems(List<WarrantQuote> items) {
    this.items = items;
  }
}
