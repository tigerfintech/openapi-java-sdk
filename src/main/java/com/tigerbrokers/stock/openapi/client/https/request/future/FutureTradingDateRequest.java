package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.ApiServiceType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTradingDateModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureTradingDateResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;

/**
 * Description:
 * Created by lijiawen on 2018/12/20.
 */
public class FutureTradingDateRequest extends TigerCommonRequest implements TigerRequest<FutureTradingDateResponse> {

  public FutureTradingDateRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(ApiServiceType.FUTURE_TRADING_DATE);
  }

  public static FutureTradingDateRequest newRequest(String contractCode) {
    return newRequest(contractCode, System.currentTimeMillis());
  }

  public static FutureTradingDateRequest newRequest(String contractCode, Long tradingDate) {
    FutureTradingDateRequest request = new FutureTradingDateRequest();
    FutureTradingDateModel model = new FutureTradingDateModel(contractCode, tradingDate);
    request.setApiModel(model);
    return request;
  }

  /**
   * construct FutureTradingDateRequest
   * @param contractCode
   * @param tradingDate yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static FutureTradingDateRequest newRequest(String contractCode, String tradingDate) {
    return newRequest(contractCode, tradingDate, ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }

  /**
   * construct FutureTradingDateRequest
   * @param contractCode
   * @param tradingDate yyyy-MM-dd HH:mm:ss
   * @param zoneId
   * @return
   */
  public static FutureTradingDateRequest newRequest(String contractCode, String tradingDate,
      TimeZoneId zoneId) {
    FutureTradingDateRequest request = new FutureTradingDateRequest();
    Long tradingDateTime = DateUtils.getTimestamp(tradingDate, zoneId);
    FutureTradingDateModel model = new FutureTradingDateModel(contractCode, tradingDateTime);
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureTradingDateResponse> getResponseClass() {
    return FutureTradingDateResponse.class;
  }
}
