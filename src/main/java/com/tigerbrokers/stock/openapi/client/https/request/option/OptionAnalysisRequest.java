package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionAnalysisModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionAnalysisRequestModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionAnalysisResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionAnalysisPeriod;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: Option analysis request
 */
public class OptionAnalysisRequest extends TigerCommonRequest implements TigerRequest<OptionAnalysisResponse> {

  public OptionAnalysisRequest() {
    setApiVersion(V2_0);
    setApiMethodName(MethodName.OPTION_ANALYSIS);
  }

  public OptionAnalysisRequest(List<OptionAnalysisModel> symbols, Market market) {
    this();
    OptionAnalysisRequestModel model = new OptionAnalysisRequestModel(symbols);
    model.setMarket(market == null ? Market.US : market);
    setApiModel(model);
  }

  public static OptionAnalysisRequest newRequest(List<OptionAnalysisModel> symbols, Market market) {
    return new OptionAnalysisRequest(symbols, market);
  }

  public static OptionAnalysisRequest of(String symbol, String period) {
    return of(symbol, period, Market.US);
  }

  public static OptionAnalysisRequest of(String symbol, String period, Market market) {
    List<OptionAnalysisModel> items = new ArrayList<>();
    items.add(new OptionAnalysisModel(symbol, period));
    return new OptionAnalysisRequest(items, market);
  }

  public static OptionAnalysisRequest of(String symbol, OptionAnalysisPeriod period) {
    return of(symbol, period, Market.US);
  }

  public static OptionAnalysisRequest of(String symbol, OptionAnalysisPeriod period, Market market) {
    List<OptionAnalysisModel> items = new ArrayList<>();
    items.add(new OptionAnalysisModel(symbol, period));
    return new OptionAnalysisRequest(items, market);
  }

  public static OptionAnalysisRequest of(String symbol, String period, Boolean requireVolatilityList) {
    return of(symbol, period, requireVolatilityList, Market.US);
  }

  public static OptionAnalysisRequest of(String symbol, String period, Boolean requireVolatilityList, Market market) {
    List<OptionAnalysisModel> items = new ArrayList<>();
    items.add(new OptionAnalysisModel(symbol, period, requireVolatilityList));
    return new OptionAnalysisRequest(items, market);
  }

  public static OptionAnalysisRequest of(String symbol, OptionAnalysisPeriod period, Boolean requireVolatilityList) {
    return of(symbol, period, requireVolatilityList, Market.US);
  }

  public static OptionAnalysisRequest of(String symbol, OptionAnalysisPeriod period, Boolean requireVolatilityList,
      Market market) {
    List<OptionAnalysisModel> items = new ArrayList<>();
    items.add(new OptionAnalysisModel(symbol, period, requireVolatilityList));
    return new OptionAnalysisRequest(items, market);
  }

  public OptionAnalysisRequest market(Market market) {
    OptionAnalysisRequestModel model = (OptionAnalysisRequestModel) getApiModel();
    if (model != null) {
      model.setMarket(market == null ? Market.US : market);
    }
    return this;
  }

  @Override
  public OptionAnalysisRequestModel getApiModel() {
    return (OptionAnalysisRequestModel) super.getApiModel();
  }

  @Override
  public Class<OptionAnalysisResponse> getResponseClass() {
    return OptionAnalysisResponse.class;
  }
}
