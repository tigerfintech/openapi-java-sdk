package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.*;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.MarketScannerResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

import java.util.List;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/10/27
 */
public class MarketScannerRequest  extends TigerCommonRequest implements TigerRequest<MarketScannerResponse> {
    public MarketScannerRequest() {
        setApiMethodName(MethodName.MARKET_SCANNER);
    }

    public static MarketScannerRequest newRequest(Market market, List<BaseFilter> baseFilterList,
                                                  List<AccumulateFilter> accumulateFilterList,
                                                  List<FinancialFilter> financialFilterList,
                                                  List<MultiTagsRelationFilter> multiTagsRelationFilterList, SortFieldData sortFieldData,
                                                  int page, int pageSize) {
        MarketScannerRequest marketScannerRequest = new MarketScannerRequest();
        MarketScannerModel marketScannerModel = new MarketScannerModel(market, baseFilterList, accumulateFilterList,
                financialFilterList, multiTagsRelationFilterList, sortFieldData, page, pageSize);
        marketScannerRequest.setApiModel(marketScannerModel);
        return marketScannerRequest;
    }

    @Override
    public Class<MarketScannerResponse> getResponseClass() {
        return MarketScannerResponse.class;
    }
}
