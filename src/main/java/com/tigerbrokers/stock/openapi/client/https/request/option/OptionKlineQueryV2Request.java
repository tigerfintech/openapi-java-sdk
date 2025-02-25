package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineV2Model;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionKlineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by liutongping on 2024/05/24.
 */
public class OptionKlineQueryV2Request extends TigerCommonRequest implements TigerRequest<OptionKlineResponse> {

  public OptionKlineQueryV2Request() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.OPTION_KLINE);
  }

  public OptionKlineQueryV2Request(List<OptionKlineModel> items) {
    this(items, null);
  }

  public OptionKlineQueryV2Request(List<OptionKlineModel> items, Market market) {
    this();
    OptionKlineV2Model klineV2Model = new OptionKlineV2Model();
    klineV2Model.setOptionQuery(items);
    klineV2Model.setMarket(market == null ? Market.US : market);
    setApiModel(klineV2Model);
  }

  public OptionKlineQueryV2Request market(Market market) {
    ((OptionKlineV2Model)getApiModel()).setMarket(market == null ? Market.US : market);
    return this;
  }

  public static OptionKlineQueryV2Request of(List<OptionKlineModel> items) {
    return new OptionKlineQueryV2Request(items);
  }

  public static OptionKlineQueryV2Request of(OptionKlineModel... itemArray) {
    List<OptionKlineModel> items = new ArrayList<>();
    if (itemArray != null && itemArray.length > 0) {
      for (OptionKlineModel model : itemArray) {
        items.add(model);
      }
    }
    return new OptionKlineQueryV2Request(items);
  }

  @Override
  public Class<OptionKlineResponse> getResponseClass() {
    return OptionKlineResponse.class;
  }
}
