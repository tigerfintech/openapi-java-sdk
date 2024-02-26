package com.tigerbrokers.stock.openapi.client.https.request.future;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureHistoryMainContractModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureHistoryMainContractResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import com.tigerbrokers.stock.openapi.client.util.DateUtils;
import java.util.List;

/**
 * Description:
 * Created by bean on 2023/08/29.
 */
public class FutureHistoryMainContractRequest extends TigerCommonRequest implements TigerRequest<FutureHistoryMainContractResponse> {

  public FutureHistoryMainContractRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.FUTURE_HISTORY_MAIN_CONTRACT);
  }

  /**
   * build FutureHistoryMainContractRequest
   * @param contractCodes
   * @param beginTime begin time, excluded
   * @param endTime end time, included
   * @return
   */
  public static FutureHistoryMainContractRequest newRequest(List<String> contractCodes,
      Long beginTime, Long endTime) {
    FutureHistoryMainContractRequest request = new FutureHistoryMainContractRequest();
    FutureHistoryMainContractModel model = new FutureHistoryMainContractModel(
        contractCodes, beginTime, endTime);
    request.setApiModel(model);
    return request;
  }

  public static FutureHistoryMainContractRequest newRequest(List<String> contractCodes,
      String beginTime, String endTime) {
    return newRequest(contractCodes, beginTime, endTime,
        ClientConfig.DEFAULT_CONFIG.getDefaultTimeZone());
  }

  /**
   * build FutureHistoryMainContractRequest
   * @param contractCodes
   * @param beginTime begin time, excluded, yyyy-MM-dd
   * @param endTime end time, included, yyyy-MM-dd
   * @param zoneId time zone
   * @return
   */
  public static FutureHistoryMainContractRequest newRequest(List<String> contractCodes,
      String beginTime, String endTime, TimeZoneId zoneId) {
    FutureHistoryMainContractRequest request = new FutureHistoryMainContractRequest();

    FutureHistoryMainContractModel model =
        new FutureHistoryMainContractModel(contractCodes,
            DateUtils.getTimestamp(beginTime, zoneId),
            DateUtils.getTimestamp(endTime, zoneId));
    request.setApiModel(model);
    return request;
  }

  @Override
  public Class<FutureHistoryMainContractResponse> getResponseClass() {
    return FutureHistoryMainContractResponse.class;
  }
}
