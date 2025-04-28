package com.tigerbrokers.stock.openapi.client.https.request.trade;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.FundDetailsModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerCommonRequest;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.FundDetailsResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;

import java.util.List;

/**
 * Created on 2025/4/24
 *
 * @author: sukai
 */
public class FundDetailsRequest extends TigerCommonRequest implements TigerRequest<FundDetailsResponse> {

  public FundDetailsRequest() {
    setApiVersion(TigerApiConstants.DEFAULT_VERSION);
    setApiMethodName(MethodName.FUND_DETAILS);
  }

  public static FundDetailsRequest buildFundDetailsRequest(String account, List<String> segTypes) {
    FundDetailsRequest request = new FundDetailsRequest();
    request.setApiModel(new FundDetailsModel(account, segTypes));
    return request;
  }

  public static FundDetailsRequest buildFundDetailsRequest(String account, List<String> segTypes, String secretKey) {
    FundDetailsRequest request = new FundDetailsRequest();
    request.setApiModel(new FundDetailsModel(account, segTypes, secretKey));
    return request;
  }

  public static FundDetailsRequest buildFundDetailsRequest(String account, List<String> segTypes, String fundType, String secretKey) {
    FundDetailsRequest request = new FundDetailsRequest();
    request.setApiModel(new FundDetailsModel(account, segTypes, fundType, secretKey));
    return request;
  }

  public static FundDetailsRequest buildFundDetailsRequest(String account, List<String> segTypes, Long start, Long limit) {
    FundDetailsRequest request = new FundDetailsRequest();
    request.setApiModel(new FundDetailsModel(account, segTypes, start, limit));
    return request;
  }

  public static FundDetailsRequest buildFundDetailsRequest(String account, List<String> segTypes, Long start, Long limit, String secretKey) {
    FundDetailsRequest request = new FundDetailsRequest();
    request.setApiModel(new FundDetailsModel(account, segTypes, start, limit, secretKey));
    return request;
  }

  public FundDetailsRequest setSegTypes(List<String> segTypes) {
    getApiModel().setSegTypes(segTypes);
    return this;
  }

  public FundDetailsRequest setStart(Long start) {
    getApiModel().setStart(start);
    return this;
  }

  public FundDetailsRequest setLimit(Long limit) {
    getApiModel().setLimit(limit);
    return this;
  }

  public FundDetailsRequest setCurrency(String currency) {
    getApiModel().setCurrency(currency);
    return this;
  }

  public FundDetailsRequest setStartDate(String startDate) {
    getApiModel().setStartDate(startDate);
    return this;
  }

  public FundDetailsRequest setEndDate(String endDate) {
    getApiModel().setEndDate(endDate);
    return this;
  }

  public FundDetailsRequest setFundType(String fundType) {
    getApiModel().setFundType(fundType);
    return this;
  }

  public FundDetailsModel getApiModel() {
    if (apiModel == null) {
      apiModel = new FundDetailsModel(ClientConfig.DEFAULT_CONFIG.defaultAccount);
    }
    return (FundDetailsModel) apiModel;
  }

  @Override
  public Class<FundDetailsResponse> getResponseClass() {
    return FundDetailsResponse.class;
  }
}
