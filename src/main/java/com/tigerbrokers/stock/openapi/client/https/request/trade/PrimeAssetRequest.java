package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PrimeAssetModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAssetResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;


/**
 * Description:
 *
 * @author kevin
 * @date 2021/11/30
 */
public class PrimeAssetRequest extends TigerCommonRequest implements TigerRequest<PrimeAssetResponse> {
    public PrimeAssetRequest() {
        setApiVersion(TigerApiConstants.DEFAULT_VERSION);
        setApiMethodName(ApiServiceType.PRIME_ASSETS);
    }

    public static PrimeAssetRequest buildPrimeAssetRequest(String account) {
        PrimeAssetRequest primeAssetRequest = new PrimeAssetRequest();
        primeAssetRequest.setApiModel(new PrimeAssetModel(account));
        return primeAssetRequest;
    }

    public static PrimeAssetRequest buildPrimeAssetRequest(String account, String secretKey) {
        PrimeAssetRequest primeAssetRequest = new PrimeAssetRequest();
        primeAssetRequest.setApiModel(new PrimeAssetModel(account, secretKey));
        return primeAssetRequest;
    }

    public static PrimeAssetRequest buildPrimeAssetRequest(String account, Currency baseCurrency) {
        PrimeAssetRequest primeAssetRequest = new PrimeAssetRequest();
        primeAssetRequest.setApiModel(new PrimeAssetModel(account, baseCurrency.name()));
        return primeAssetRequest;
    }

    public static PrimeAssetRequest buildPrimeAssetRequest(String account, Currency baseCurrency, String secretKey) {
        PrimeAssetRequest primeAssetRequest = new PrimeAssetRequest();
        primeAssetRequest.setApiModel(new PrimeAssetModel(account, baseCurrency.name(), secretKey));
        return primeAssetRequest;
    }

    @Override
    public Class<PrimeAssetResponse> getResponseClass() {
        return PrimeAssetResponse.class;
    }
}
