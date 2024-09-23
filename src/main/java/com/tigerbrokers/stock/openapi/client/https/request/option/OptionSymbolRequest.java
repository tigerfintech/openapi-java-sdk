package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionSymbolResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 * Created by liutongping on 2024/05/24.
 */
public class OptionSymbolRequest extends TigerCommonRequest implements TigerRequest<OptionSymbolResponse> {

  public OptionSymbolRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.ALL_HK_OPTION_SYMBOLS);
  }

  public static OptionSymbolRequest newRequest(Market market) {
    return newRequest(market, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
  }

  public static OptionSymbolRequest newRequest(Market market, Language lang) {
    OptionSymbolRequest request = new OptionSymbolRequest();
    OptionModel model = new OptionModel(market, lang);
    request.setApiModel(model);
    return request;
  }

  public OptionModel getApiModel() {
    if (apiModel == null) {
      apiModel = new OptionModel();
    }
    return (OptionModel)apiModel;
  }

  public OptionSymbolRequest market(Market market) {
    getApiModel().setMarket(market);
    return this;
  }

  public OptionSymbolRequest language(Language language) {
    getApiModel().setLang(language);
    return this;
  }

  @Override
  public Class<OptionSymbolResponse> getResponseClass() {
    return OptionSymbolResponse.class;
  }
}
