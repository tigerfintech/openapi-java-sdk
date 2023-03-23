package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.SegmentFundModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundAvailableResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

public class SegmentFundAvailableRequest extends TigerCommonRequest implements TigerRequest<SegmentFundAvailableResponse> {

  private SegmentFundAvailableRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.SEGMENT_FUND_AVAILABLE);
  }

  public static SegmentFundAvailableRequest newRequest(SegmentFundModel model) {
    SegmentFundAvailableRequest request = new SegmentFundAvailableRequest();
    request.setApiModel(model);
    return request;
  }

  public static SegmentFundAvailableRequest buildRequest(
      SegmentType segmentType, Currency currency) {
    return buildRequest(null, segmentType, currency);
  }

  public static SegmentFundAvailableRequest buildRequest(
      String account,
      SegmentType segmentType, Currency currency) {
    SegmentFundModel model = new SegmentFundModel();
    if (StringUtils.isEmpty(account)) {
      model.setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    } else {
      model.setAccount(account);
    }
    if (segmentType != null && segmentType != SegmentType.ALL) {
      model.setFromSegment(segmentType.toString());
    }
    if (currency != null) {
      model.setCurrency(currency.toString());
    }
    return newRequest(model);
  }

  public SegmentFundAvailableRequest segmentType(SegmentType segmentType) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    if (segmentType != null && segmentType != SegmentType.ALL) {
      model.setFromSegment(segmentType.toString());
    } else {
      model.setFromSegment(null);
    }
    return this;
  }

  public SegmentFundAvailableRequest currency(Currency currency) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setCurrency(currency == null ? null : currency.toString());
    return this;
  }

  public SegmentFundAvailableRequest account(String account) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public SegmentFundAvailableRequest secretKey(String secretKey) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public SegmentFundAvailableRequest lang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  @Override
  public Class<SegmentFundAvailableResponse> getResponseClass() {
    return SegmentFundAvailableResponse.class;
  }
}
