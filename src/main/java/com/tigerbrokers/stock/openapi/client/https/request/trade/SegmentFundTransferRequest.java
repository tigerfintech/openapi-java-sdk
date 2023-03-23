package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.SegmentFundModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.SegmentFundResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

public class SegmentFundTransferRequest extends TigerCommonRequest implements TigerRequest<SegmentFundResponse> {

  private SegmentFundTransferRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.TRANSFER_SEGMENT_FUND);
  }

  public static SegmentFundTransferRequest newRequest(SegmentFundModel model) {
    SegmentFundTransferRequest request = new SegmentFundTransferRequest();
    request.setApiModel(model);
    return request;
  }

  public static SegmentFundTransferRequest buildRequest(
      SegmentType fromSegType, SegmentType toSegType,
      Currency currency,
      Double amount) {
    return buildRequest(null, fromSegType, toSegType, currency, amount);
  }

  public static SegmentFundTransferRequest buildRequest(
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

  public SegmentFundTransferRequest fromSegmentType(SegmentType segmentType) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    if (segmentType != null && segmentType != SegmentType.ALL) {
      model.setFromSegment(segmentType.toString());
    } else {
      model.setFromSegment(null);
    }
    return this;
  }

  public SegmentFundTransferRequest toSegmentType(SegmentType segmentType) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    if (segmentType != null && segmentType != SegmentType.ALL) {
      model.setToSegment(segmentType.toString());
    } else {
      model.setToSegment(null);
    }
    return this;
  }

  public SegmentFundTransferRequest currency(Currency currency) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setCurrency(currency == null ? null : currency.toString());
    return this;
  }

  public SegmentFundTransferRequest amount(Double amount) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setAmount(amount);
    return this;
  }

  public SegmentFundTransferRequest account(String account) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setAccount(account);
    return this;
  }

  public SegmentFundTransferRequest secretKey(String secretKey) {
    SegmentFundModel model = (SegmentFundModel) getApiModel();
    model.setSecretKey(secretKey);
    return this;
  }

  public SegmentFundTransferRequest lang(Language lang) {
    ApiModel model = getApiModel();
    model.setLang(lang);
    return this;
  }

  @Override
  public Class<SegmentFundResponse> getResponseClass() {
    return SegmentFundResponse.class;
  }
}
