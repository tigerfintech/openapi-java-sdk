package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.SegmentFundModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

public class SegmentFundCancelRequest extends TigerCommonRequest implements TigerRequest<SegmentFundResponse> {

  private SegmentFundCancelRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.CANCEL_SEGMENT_FUND);
  }

  public static SegmentFundCancelRequest newRequest(SegmentFundModel model) {
    SegmentFundCancelRequest request = new SegmentFundCancelRequest();
    request.setApiModel(model);
    return request;
  }

  public static SegmentFundCancelRequest buildRequest(Long id) {
    return buildRequest(null, id);
  }

  public static SegmentFundCancelRequest buildRequest(
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

  public SegmentFundCancelRequest id(Long id) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setId(id);
    return this;
  }

  public SegmentFundCancelRequest account(String account) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public SegmentFundCancelRequest secretKey(String secretKey) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public SegmentFundCancelRequest lang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  @Override
  public Class<SegmentFundResponse> getResponseClass() {
    return SegmentFundResponse.class;
  }
}
