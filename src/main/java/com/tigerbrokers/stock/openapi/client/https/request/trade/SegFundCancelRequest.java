package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.SegmentFundModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SegFundResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

public class SegFundCancelRequest extends TigerCommonRequest implements TigerRequest<SegFundResponse> {

  private SegFundCancelRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.CANCEL_SEGMENT_FUND);
  }

  public static SegFundCancelRequest newRequest(SegmentFundModel model) {
    SegFundCancelRequest request = new SegFundCancelRequest();
    request.setApiModel(model);
    return request;
  }

  public static SegFundCancelRequest buildRequest(Long id) {
    return buildRequest(null, id);
  }

  public static SegFundCancelRequest buildRequest(
      String account, Long id) {
    SegmentFundModel model = new SegmentFundModel();
    if (StringUtils.isEmpty(account)) {
      model.setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    } else {
      model.setAccount(account);
    }
    model.setId(id);
    return newRequest(model);
  }

  public SegFundCancelRequest id(Long id) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setId(id);
    return this;
  }

  public SegFundCancelRequest account(String account) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public SegFundCancelRequest secretKey(String secretKey) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public SegFundCancelRequest lang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  @Override
  public Class<SegFundResponse> getResponseClass() {
    return SegFundResponse.class;
  }
}
