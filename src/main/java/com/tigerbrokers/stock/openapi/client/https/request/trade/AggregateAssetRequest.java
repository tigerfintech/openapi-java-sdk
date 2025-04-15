package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.AggregateAssetModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PrimeAssetModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.AggregateAssetResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAssetResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

/**
 * Description:
 *
 * @author kevin
 * @date 2021/11/30
 */
public class AggregateAssetRequest extends TigerCommonRequest implements TigerRequest<AggregateAssetResponse> {
    public AggregateAssetRequest() {
        setApiVersion(TigerApiConstants.DEFAULT_VERSION);
        setApiMethodName(MethodName.AGGREGATE_ASSETS);
    }

    public static AggregateAssetRequest buildAggregateAssetRequest(String account, String segType) {
        AggregateAssetRequest request = new AggregateAssetRequest();
        request.setApiModel(new AggregateAssetModel(account, segType));
        return request;
    }

    public static AggregateAssetRequest buildAggregateAssetRequest(String account, String segType, String secretKey) {
        AggregateAssetRequest request = new AggregateAssetRequest();
        request.setApiModel(new AggregateAssetModel(account, segType, secretKey));
        return request;
    }

    public static AggregateAssetRequest buildAggregateAssetRequest(String account, String segType, String secretKey, Currency baseCurrency) {
        AggregateAssetRequest request = new AggregateAssetRequest();
        AggregateAssetModel model = new AggregateAssetModel(account, segType, baseCurrency.name(), secretKey);
        model.setBaseCurrency(baseCurrency.name());
        request.setApiModel(model);
        return request;
    }

    public AggregateAssetModel getApiModel() {
        if (apiModel == null) {
            apiModel = new PrimeAssetModel(ClientConfig.DEFAULT_CONFIG.defaultAccount);
        }
        return (AggregateAssetModel)apiModel;
    }

    @Override
    public Class<AggregateAssetResponse> getResponseClass() {
        return AggregateAssetResponse.class;
    }
}
