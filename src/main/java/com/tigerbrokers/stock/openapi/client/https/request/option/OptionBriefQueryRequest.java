package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionBriefResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by lijiawen on 2018/12/06.
 */
public class OptionBriefQueryRequest extends TigerCommonRequest implements TigerRequest<OptionBriefResponse> {

  private List<OptionCommonModel> items;

  public OptionBriefQueryRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(ApiServiceType.OPTION_BRIEF);
  }

  public OptionBriefQueryRequest(List<OptionCommonModel> items) {
    this();
    setApiModel(new BatchApiModel(items));
  }

  public static OptionBriefQueryRequest of(List<OptionCommonModel> items) {
    return new OptionBriefQueryRequest(items);
  }

  public static OptionBriefQueryRequest of(OptionCommonModel item) {
    List<OptionCommonModel> items = new ArrayList<>();
    items.add(item);
    return new OptionBriefQueryRequest(items);
  }

  public static OptionBriefQueryRequest of(OptionCommonModel item1, OptionCommonModel item2) {
    List<OptionCommonModel> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    return new OptionBriefQueryRequest(items);
  }

  public static OptionBriefQueryRequest of(OptionCommonModel item1, OptionCommonModel item2, OptionCommonModel item3) {
    List<OptionCommonModel> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    items.add(item3);
    return new OptionBriefQueryRequest(items);
  }

  public static OptionBriefQueryRequest of(OptionCommonModel item1, OptionCommonModel item2, OptionCommonModel item3,
      OptionCommonModel item4) {
    List<OptionCommonModel> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);
    items.add(item3);
    items.add(item4);
    return new OptionBriefQueryRequest(items);
  }

  @Override
  public Class<OptionBriefResponse> getResponseClass() {
    return OptionBriefResponse.class;
  }
}
