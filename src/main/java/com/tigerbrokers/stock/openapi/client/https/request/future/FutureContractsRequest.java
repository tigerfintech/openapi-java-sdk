package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContinuousContractModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureContractsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/08/10
 */
public class FutureContractsRequest extends TigerCommonRequest implements TigerRequest<FutureContractsResponse> {

    public FutureContractsRequest() {
        setApiVersion(TigerApiConstants.DEFAULT_VERSION);
        setApiMethodName(ApiServiceType.FUTURE_CONTRACTS);
    }

    public static FutureContractsRequest newRequest(String type) {
        return newRequest(type, Language.en_US);
    }

    public static FutureContractsRequest newRequest(String type, Language lang) {
        FutureContractsRequest request = new FutureContractsRequest();
        FutureContinuousContractModel model = new FutureContinuousContractModel(type, lang);
        request.setApiModel(model);
        return request;
    }

    @Override
    public Class<FutureContractsResponse> getResponseClass() {
        return FutureContractsResponse.class;
    }
}
