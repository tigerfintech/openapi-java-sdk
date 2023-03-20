package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.SegmentFundModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SegFundResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

public class SegFundTransferRequest extends TigerCommonRequest implements TigerRequest<SegFundResponse> {

  private SegFundTransferRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.TRANSFER_SEGMENT_FUND);
  }

  public static SegFundTransferRequest newRequest(SegmentFundModel model) {
    SegFundTransferRequest request = new SegFundTransferRequest();
    request.setApiModel(model);
    return request;
  }

  public static SegFundTransferRequest buildRequest(
      SegmentType fromSegType, SegmentType toSegType,
      Currency currency,
      Double amount) {
    return buildRequest(null, fromSegType, toSegType, currency, amount);
  }

  public static SegFundTransferRequest buildRequest(
      String account,
      SegmentType fromSegType, SegmentType toSegType,
      Currency currency,
      Double amount) {
    SegmentFundModel model = new SegmentFundModel();
    if (StringUtils.isEmpty(account)) {
      model.setAccount(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    } else {
      model.setAccount(account);
    }
    if (fromSegType != null && fromSegType != SegmentType.ALL) {
      model.setFromSegment(fromSegType.toString());
    }
    if (toSegType != null && toSegType != SegmentType.ALL) {
      model.setToSegment(toSegType.toString());
    }
    if (currency != null) {
      model.setCurrency(currency.toString());
    }
    model.setAmount(amount);
    return newRequest(model);
  }

  public SegFundTransferRequest fromSegmentType(SegmentType segmentType) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    if (segmentType != null && segmentType != SegmentType.ALL) {
      model.setFromSegment(segmentType.toString());
    } else {
      model.setFromSegment(null);
    }
    return this;
  }

  public SegFundTransferRequest toSegmentType(SegmentType segmentType) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    if (segmentType != null && segmentType != SegmentType.ALL) {
      model.setToSegment(segmentType.toString());
    } else {
      model.setToSegment(null);
    }
    return this;
  }

  public SegFundTransferRequest currency(Currency currency) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setCurrency(currency == null ? null : currency.toString());
    return this;
  }

  public SegFundTransferRequest amount(Double amount) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setAmount(amount);
    return this;
  }

  public SegFundTransferRequest account(String account) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public SegFundTransferRequest secretKey(String secretKey) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public SegFundTransferRequest lang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  @Override
  public Class<SegFundResponse> getResponseClass() {
    return SegFundResponse.class;
  }
}
