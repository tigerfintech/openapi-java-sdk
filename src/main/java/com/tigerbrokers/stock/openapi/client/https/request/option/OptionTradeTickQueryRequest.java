package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionTradeTickResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/06.
 */
public class OptionTradeTickQueryRequest extends TigerCommonRequest implements TigerRequest<OptionTradeTickResponse> {

  public OptionTradeTickQueryRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(ApiServiceType.OPTION_TRADE_TICK);
  }

  public OptionTradeTickQueryRequest(List<OptionCommonModel> items) {
    this();
    setApiModel(new BatchApiModel(items));
  }

  public static OptionTradeTickQueryRequest of(List<OptionCommonModel> items) {
    return new OptionTradeTickQueryRequest(items);
  }

  public static OptionTradeTickQueryRequest of(OptionCommonModel item1) {
    List<OptionCommonModel> items = new ArrayList<>();
    items.add(item1);
    return new OptionTradeTickQueryRequest(items);
  }

  public static OptionTradeTickQueryRequest of(OptionCommonModel item1, OptionCommonModel item2) {
    List<OptionCommonModel> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    return new OptionTradeTickQueryRequest(items);
  }

  public static OptionTradeTickQueryRequest of(OptionCommonModel item1,
      OptionCommonModel item2, OptionCommonModel item3) {
    List<OptionCommonModel> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    items.add(item3);
    return new OptionTradeTickQueryRequest(items);
  }

  @Override
  public Class<OptionTradeTickResponse> getResponseClass() {
    return OptionTradeTickResponse.class;
  }
}
