package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionTimelinesModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionTimelineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OptionTimelineRequest extends TigerCommonRequest implements
    TigerRequest<OptionTimelineResponse> {

  public OptionTimelineRequest() {
    setApiMethodName(MethodName.OPTION_TIMELINE);
  }

  public OptionTimelineRequest(List<OptionTimelineModel> items) {
    this(items, null);
  }

  public OptionTimelineRequest(List<OptionTimelineModel> items, Market market) {
    this();
    OptionTimelinesModel optionTimelinesModel = new OptionTimelinesModel();
    optionTimelinesModel.setOptionQuery(items);
    optionTimelinesModel.setMarket(market == null ? Market.HK : market);
    setApiModel(optionTimelinesModel);
  }

  public OptionTimelineRequest market(Market market) {
    ((OptionTimelinesModel) getApiModel()).setMarket(market == null ? Market.HK : market);
    return this;
  }

  public static OptionTimelineRequest of(List<OptionTimelineModel> items) {
    return new OptionTimelineRequest(items);
  }

  public static OptionTimelineRequest of(OptionTimelineModel... itemArray) {
    List<OptionTimelineModel> items = new ArrayList<>();
    if (itemArray != null) {
      Collections.addAll(items, itemArray);
    }
    return new OptionTimelineRequest(items);
  }

  @Override
  public Class<OptionTimelineResponse> getResponseClass() {
    return OptionTimelineResponse.class;
  }
}
