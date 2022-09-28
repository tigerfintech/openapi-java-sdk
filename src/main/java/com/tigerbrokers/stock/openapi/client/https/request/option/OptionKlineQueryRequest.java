package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionKlineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/06.
 */
public class OptionKlineQueryRequest extends TigerCommonRequest implements TigerRequest<OptionKlineResponse> {

  public OptionKlineQueryRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.OPTION_KLINE);
  }

  public OptionKlineQueryRequest(List<OptionKlineModel> items) {
    this();
    setApiModel(new BatchApiModel(items));
  }

  public static OptionKlineQueryRequest of(List<OptionKlineModel> item1) {
    return new OptionKlineQueryRequest(item1);
  }

  public static OptionKlineQueryRequest of(OptionKlineModel item1) {
    List<OptionKlineModel> items = new ArrayList<>();
    items.add(item1);
    return new OptionKlineQueryRequest(items);
  }

  public static OptionKlineQueryRequest of(OptionKlineModel item1, OptionKlineModel item2) {
    List<OptionKlineModel> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    return new OptionKlineQueryRequest(items);
  }

  public static OptionKlineQueryRequest of(OptionKlineModel item1, OptionKlineModel item2, OptionKlineModel item3) {
    List<OptionKlineModel> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    items.add(item3);
    return new OptionKlineQueryRequest(items);
  }

  @Override
  public Class<OptionKlineResponse> getResponseClass() {
    return OptionKlineResponse.class;
  }
}
