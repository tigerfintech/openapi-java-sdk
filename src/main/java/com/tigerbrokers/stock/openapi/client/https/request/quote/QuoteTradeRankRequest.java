package com.tigerbrokers.stock.openapi.client.https.request.quote;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteTradeRankModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteTradeRankResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

public class QuoteTradeRankRequest extends TigerCommonRequest implements TigerRequest<QuoteTradeRankResponse> {

    public QuoteTradeRankRequest() {
        setApiVersion(V2_0);
        setApiMethodName(MethodName.QUOTE_TRADE_RANK);
    }

    public static QuoteTradeRankRequest newRequest(Market market) {
        return newRequest(market, ClientConfig.DEFAULT_CONFIG.getDefaultLanguage());
    }

    public static QuoteTradeRankRequest newRequest(Market market, Language lang) {
        QuoteTradeRankRequest request = new QuoteTradeRankRequest();
        QuoteTradeRankModel model = new QuoteTradeRankModel(market, lang);
        request.setApiModel(model);
        return request;
    }

    @Override
    public Class<QuoteTradeRankResponse> getResponseClass() {
        return QuoteTradeRankResponse.class;
    }
} 