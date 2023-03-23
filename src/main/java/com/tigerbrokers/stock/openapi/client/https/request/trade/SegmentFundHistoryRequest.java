package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.SegmentFundModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

public class SegmentFundHistoryRequest extends TigerCommonRequest implements TigerRequest<SegmentFundsResponse> {

  private SegmentFundHistoryRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.SEGMENT_FUND_HISTORY);
  }

  public static SegmentFundHistoryRequest newRequest(SegmentFundModel model) {
    SegmentFundHistoryRequest request = new SegmentFundHistoryRequest();
    request.setApiModel(model);
    return request;
  }

  public static SegmentFundHistoryRequest buildRequest(Integer limit) {
    return buildRequest(null, limit);
  }

  public static SegmentFundHistoryRequest buildRequest(
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

  public SegmentFundHistoryRequest limit(Integer limit) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setLimit(limit);
    return this;
  }

  public SegmentFundHistoryRequest account(String account) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public SegmentFundHistoryRequest secretKey(String secretKey) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public SegmentFundHistoryRequest lang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  @Override
  public Class<SegmentFundsResponse> getResponseClass() {
    return SegmentFundsResponse.class;
  }
}
