package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionDepthResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by liutongping on 2024/05/23.
 */
public class OptionDepthQueryRequest extends TigerCommonRequest implements TigerRequest<OptionDepthResponse> {

  public OptionDepthQueryRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.OPTION_DEPTH);
  }

  public OptionDepthQueryRequest(List<OptionCommonModel> items) {
    this(items, null);
  }

  public OptionDepthQueryRequest(List<OptionCommonModel> items, Market market) {
    this();
    BatchApiModel batchApiModel = new BatchApiModel(items);
    setApiModel(batchApiModel);
    batchApiModel.setMarket(market == null ? Market.US : market);
  }

  public OptionDepthQueryRequest of(Market market) {
    ((BatchApiModel)getApiModel()).setMarket(market);
    return this;
  }

  public OptionDepthQueryRequest of(List<OptionCommonModel> items) {
    ((BatchApiModel)getApiModel()).setItems(items);
    return this;
  }

  public OptionDepthQueryRequest of(OptionCommonModel... itemArray) {
    List<OptionCommonModel> items = new ArrayList<>();
    if (itemArray != null && itemArray.length > 0) {
      for (OptionCommonModel model : itemArray) {
        items.add(model);
      }
    }
    return of(items);
  }

  @Override
  public Class<OptionDepthResponse> getResponseClass() {
    return OptionDepthResponse.class;
  }
}
