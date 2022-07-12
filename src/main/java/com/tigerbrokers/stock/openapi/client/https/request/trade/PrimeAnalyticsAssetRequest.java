package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.PrimeAnalyticsAssetModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.PrimeAnalyticsAssetResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;

/**
 * Description:
 *
 * @author bean
 * @date 2022/07/12
 */
public class PrimeAnalyticsAssetRequest extends TigerCommonRequest implements TigerRequest<PrimeAnalyticsAssetResponse> {
    public PrimeAnalyticsAssetRequest() {
        setApiVersion(TigerApiConstants.DEFAULT_VERSION);
        setApiMethodName(ApiServiceType.ANALYTICS_ASSET);
    }

    public static PrimeAnalyticsAssetRequest buildPrimeAnalyticsAssetRequest() {
        return buildPrimeAnalyticsAssetRequest(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    }

    public static PrimeAnalyticsAssetRequest buildPrimeAnalyticsAssetRequest(String account) {
        return buildPrimeAnalyticsAssetRequest(account, ClientConfig.DEFAULT_CONFIG.secretKey);
    }

    public static PrimeAnalyticsAssetRequest buildPrimeAnalyticsAssetRequest(String account, String secretKey) {
        PrimeAnalyticsAssetRequest primeAssetRequest = new PrimeAnalyticsAssetRequest();
        primeAssetRequest.setApiModel(new PrimeAnalyticsAssetModel(account, secretKey));
        return primeAssetRequest;
    }

    /**
     * set start date
     * @param startDate yyyy-MM-dd
     * @return
     */
    public PrimeAnalyticsAssetRequest startDate(String startDate) {
        getPrimeAnalyticsAssetModel().setStartDate(startDate);
        return this;
    }

    /**
     * set end date
     * @param endDate yyyy-MM-dd
     * @return
     */
    public PrimeAnalyticsAssetRequest endDate(String endDate) {
        getPrimeAnalyticsAssetModel().setEndDate(endDate);
        return this;
    }

    public PrimeAnalyticsAssetRequest segType(SegmentType segmentType) {
        getPrimeAnalyticsAssetModel().setSegType(segmentType);
        return this;
    }

    public PrimeAnalyticsAssetRequest currency(Currency currency) {
        getPrimeAnalyticsAssetModel().setCurrency(currency);
        return this;
    }

    public PrimeAnalyticsAssetRequest subAccount(String subAccount) {
        getPrimeAnalyticsAssetModel().setSubAccount(subAccount);
        return this;
    }

    private PrimeAnalyticsAssetModel getPrimeAnalyticsAssetModel() {
        if (this.apiModel == null) {
            this.apiModel = new PrimeAnalyticsAssetModel(ClientConfig.DEFAULT_CONFIG.defaultAccount,
                ClientConfig.DEFAULT_CONFIG.secretKey);
        }
        return (PrimeAnalyticsAssetModel)this.apiModel;
    }

    @Override
    public Class<PrimeAnalyticsAssetResponse> getResponseClass() {
        return PrimeAnalyticsAssetResponse.class;
    }
}
