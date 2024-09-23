package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.MarketScannerTagsModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.MarketScannerTagsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.MultiTagField;
import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2023/04/19
 */
public class MarketScannerTagsRequest extends TigerCommonRequest implements TigerRequest<MarketScannerTagsResponse> {

  public MarketScannerTagsRequest() {
    setApiMethodName(MethodName.MARKET_SCANNER_TAGS);
  }

  public static MarketScannerTagsRequest newRequest(Market market, List<MultiTagField> multiTagFieldList) {
    MarketScannerTagsRequest marketScannerTagsRequest = new MarketScannerTagsRequest();
    MarketScannerTagsModel marketScannerTagsModel = new MarketScannerTagsModel(market, multiTagFieldList);
    marketScannerTagsRequest.setApiModel(marketScannerTagsModel);
    return marketScannerTagsRequest;
  }

  @Override
  public Class<MarketScannerTagsResponse> getResponseClass() {
    return MarketScannerTagsResponse.class;
  }
}
