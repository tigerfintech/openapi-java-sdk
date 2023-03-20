package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.SegmentFundModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SegFundsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

public class SegFundHistoryRequest extends TigerCommonRequest implements TigerRequest<SegFundsResponse> {

  private SegFundHistoryRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.SEGMENT_FUND_HISTORY);
  }

  public static SegFundHistoryRequest newRequest(SegmentFundModel model) {
    SegFundHistoryRequest request = new SegFundHistoryRequest();
    request.setApiModel(model);
    return request;
  }

  public static SegFundHistoryRequest buildRequest(Integer limit) {
    return buildRequest(null, limit);
  }

  public static SegFundHistoryRequest buildRequest(
      String account, Integer limit) {
    SegmentFundModel model = new SegmentFundModel();
    if (StringUtils.isEmpty(account)) {
      model.setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    } else {
      model.setAccount(account);
    }
    model.setLimit(limit);
    return newRequest(model);
  }

  public SegFundHistoryRequest limit(Integer limit) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setLimit(limit);
    return this;
  }

  public SegFundHistoryRequest account(String account) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public SegFundHistoryRequest secretKey(String secretKey) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public SegFundHistoryRequest lang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  @Override
  public Class<SegFundsResponse> getResponseClass() {
    return SegFundsResponse.class;
  }
}
