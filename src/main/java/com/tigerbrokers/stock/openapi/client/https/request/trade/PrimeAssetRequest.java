package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PrimeAssetModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAssetResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.AssetQuoteType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 *
 * @author kevin
 * @date 2021/11/30
 */
public class PrimeAssetRequest extends TigerCommonRequest implements TigerRequest<PrimeAssetResponse> {
    public PrimeAssetRequest() {
        setApiVersion(TigerApiConstants.DEFAULT_VERSION);
        setApiMethodName(MethodName.PRIME_ASSETS);
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
        PrimeAssetModel primeAssetModel = new PrimeAssetModel(account);
        primeAssetModel.setBaseCurrency(baseCurrency.name());
        primeAssetRequest.setApiModel(primeAssetModel);
        return primeAssetRequest;
    }

    public static PrimeAssetRequest buildPrimeAssetRequest(String account, Currency baseCurrency, String secretKey) {
        PrimeAssetRequest primeAssetRequest = new PrimeAssetRequest();
        primeAssetRequest.setApiModel(new PrimeAssetModel(account, baseCurrency.name(), secretKey));
        return primeAssetRequest;
    }

    public static PrimeAssetRequest buildPrimeAssetRequest(String account, AssetQuoteType assetQuoteType) {
        PrimeAssetRequest primeAssetRequest = new PrimeAssetRequest();
        PrimeAssetModel primeAssetModel = new PrimeAssetModel(account);
        if (assetQuoteType != null) {
            primeAssetModel.setAssetQuoteType(assetQuoteType.name());
        }
        primeAssetRequest.setApiModel(primeAssetModel);
        return primeAssetRequest;
    }

    public static PrimeAssetRequest buildPrimeAssetRequest(String account, Currency baseCurrency,
                                                           AssetQuoteType assetQuoteType) {
        PrimeAssetRequest primeAssetRequest = new PrimeAssetRequest();
        PrimeAssetModel primeAssetModel = new PrimeAssetModel(account);
        primeAssetModel.setBaseCurrency(baseCurrency.name());
        if (assetQuoteType != null) {
            primeAssetModel.setAssetQuoteType(assetQuoteType.name());
        }
        primeAssetRequest.setApiModel(primeAssetModel);
        return primeAssetRequest;
    }

    public static PrimeAssetRequest buildPrimeAssetRequest(String account, Currency baseCurrency, String secretKey,
                                                           AssetQuoteType assetQuoteType) {
        PrimeAssetRequest primeAssetRequest = new PrimeAssetRequest();
        PrimeAssetModel primeAssetModel = new PrimeAssetModel(account, baseCurrency.name(), secretKey);
        if (assetQuoteType != null) {
            primeAssetModel.setAssetQuoteType(assetQuoteType.name());
        }
        primeAssetRequest.setApiModel(primeAssetModel);
        return primeAssetRequest;
    }

    public PrimeAssetModel getApiModel() {
        if (apiModel == null) {
            apiModel = new PrimeAssetModel(ClientConfig.DEFAULT_CONFIG.defaultAccount);
        }
        return (PrimeAssetModel)apiModel;
    }

    public void setConsolidated(Boolean consolidated) {
        getApiModel().setConsolidated(consolidated);
    }

    public void setAssetQuoteType(AssetQuoteType assetQuoteType) {
        getApiModel().setAssetQuoteType(assetQuoteType == null ? null : assetQuoteType.name());
    }

    @Override
    public Class<PrimeAssetResponse> getResponseClass() {
        return PrimeAssetResponse.class;
    }
}
