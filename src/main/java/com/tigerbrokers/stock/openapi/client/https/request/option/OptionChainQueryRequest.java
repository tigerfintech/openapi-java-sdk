package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/06.
 */
public class OptionChainQueryRequest extends TigerCommonRequest implements TigerRequest<OptionChainResponse> {

  public OptionChainQueryRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.OPTION_CHAIN);
  }

  public OptionChainQueryRequest(List<OptionChainModel> items) {
    this();
    setApiModel(new BatchApiModel(items));
  }

  public static OptionChainQueryRequest of(List<OptionChainModel> items) {
    return new OptionChainQueryRequest(items);
  }

  public static OptionChainQueryRequest of(OptionChainModel item) {
    List<OptionChainModel> items = new ArrayList<>();
    items.add(item);
    return new OptionChainQueryRequest(items);
  }

  public static OptionChainQueryRequest of(OptionChainModel item1, OptionChainModel item2) {
    List<OptionChainModel> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    return new OptionChainQueryRequest(items);
  }

  public static OptionChainQueryRequest of(OptionChainModel item1, OptionChainModel item2, OptionChainModel item3) {
    List<OptionChainModel> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    items.add(item3);
    return new OptionChainQueryRequest(items);
  }

  @Override
  public Class<OptionChainResponse> getResponseClass() {
    return OptionChainResponse.class;
  }
}
