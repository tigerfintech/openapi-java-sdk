package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import java.util.regex.Pattern;

/**
 * Description: created by liutongping on 2021/11/5
 */
public interface RequestValidator<T extends ApiModel> {
  Pattern LOCAL_SYMBOL_PATTERN = Pattern.compile("[A-Z0-9]{5}");

  void validate(T model) throws TigerApiException;
}
