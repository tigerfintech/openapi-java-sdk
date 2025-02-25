package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionBasicModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionBriefResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by liutongping on 2024/05/24.
 */
public class OptionBriefQueryV2Request extends TigerCommonRequest implements TigerRequest<OptionBriefResponse> {

  public OptionBriefQueryV2Request() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.OPTION_BRIEF);
  }

  public OptionBriefQueryV2Request(List<OptionCommonModel> items) {
    this(items, null);
  }

  public OptionBriefQueryV2Request(List<OptionCommonModel> items, Market market) {
    this();
    OptionBasicModel briefModel = new OptionBasicModel();
    briefModel.setOptionBasic(items);
    briefModel.setMarket(market == null ? Market.US : market);
    setApiModel(briefModel);
  }

  public OptionBriefQueryV2Request market(Market market) {
    ((OptionBasicModel)getApiModel()).setMarket(market == null ? Market.US : market);
    return this;
  }

  public static OptionBriefQueryV2Request of(List<OptionCommonModel> items) {
    return new OptionBriefQueryV2Request(items);
  }

  public static OptionBriefQueryV2Request of(OptionCommonModel... itemArray) {
    List<OptionCommonModel> items = new ArrayList<>();
    if (itemArray != null && itemArray.length > 0) {
      for (OptionCommonModel model : itemArray) {
        items.add(model);
      }
    }
    return new OptionBriefQueryV2Request(items);
  }

  @Override
  public Class<OptionBriefResponse> getResponseClass() {
    return OptionBriefResponse.class;
  }
}
