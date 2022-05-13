package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureKlineBatchItem;
import com.tigerbrokers.stock.openapi.client.https.domain.future.item.FutureKlineItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlinePoint;
import com.tigerbrokers.stock.openapi.client.https.request.future.FutureKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.response.future.FutureKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.FutureKType;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PageTokenUtil {
  private PageTokenUtil() {
  }

  /**
   * 分页获取K线数据，合并结果返回
   * @param pageSize 每页大小
   * @param total 数据总条数
   * @param symbol
   * @param period 默认为日K线
   * @param right 复权方向 默认前复权
   * @param beginTime 开始时间 "2022-04-25 00:00:00"
   * @param endTime 结束时间 "2022-04-28 00:00:00"
   * @param zoneId 默认纽约时区
   * @throws TigerApiException
   */
  public static List<KlinePoint> klineAggregationByPage(int pageSize, int total,
      String symbol, KType period, RightOption right,
      String beginTime, String endTime, TimeZoneId zoneId) throws TigerApiException {
    if (symbol == null) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbol");
    }
    if (total <= 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_VALUE_ERROR, "total");
    }
    if (pageSize <= 0) {
      pageSize = 1200;
    }
    if (total < pageSize) {
      pageSize = total;
    }
    List<String> symbols = new ArrayList<>();
    symbols.add(symbol);
    QuoteKlineRequest request = QuoteKlineRequest.newRequest(symbols, period == null ? KType.day : period,
        beginTime, endTime, zoneId == null ? TimeZoneId.NewYork : zoneId);
    request.withLimit(pageSize);
    request.withRight(right == null ? RightOption.br : right);

    List<KlinePoint> results = new ArrayList<>(total);
    do {
      QuoteKlineResponse response = TigerHttpClient.getInstance().execute(request);
      if (!response.isSuccess()) {
        throw new TigerApiException(response.getMessage());
      }
      if (response.getKlineItems().size() == 0) {
        break;
      }
      KlineItem klineItem = response.getKlineItems().get(0);
      results.addAll(klineItem.getItems());

      if (klineItem.getNextPageToken() == null) {
        break;
      }
      // 60 times per minute
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException ignoreIE) {
      }
      // set pagination token then query the next page
      request.withPageToken(klineItem.getNextPageToken());
    } while (results.size() < total);
    return results;
  }

  /**
   * 分页获取期货K线数据，合并结果返回
   * @param pageSize 每页大小
   * @param total 数据总条数
   * @param contractCode contract code
   * @param period 默认为日K线
   * @param beginTime 开始时间 "2022-04-25 00:00:00"
   * @param endTime 结束时间 "2022-04-28 00:00:00"
   * @param zoneId 默认纽约时区
   * @throws TigerApiException
   */
  public static List<FutureKlineItem> futureKlineAggregationByPage(int pageSize, int total,
      String contractCode, FutureKType period,
      String beginTime, String endTime, TimeZoneId zoneId) throws TigerApiException {
    if (contractCode == null) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "contractCode");
    }
    if (total <= 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_VALUE_ERROR, "total");
    }
    if (pageSize <= 0) {
      pageSize = 1000;
    }
    if (total < pageSize) {
      pageSize = total;
    }
    if (zoneId == null) {
      zoneId = TimeZoneId.NewYork;
    }
    Date beginDate = DateUtils.getZoneDate(beginTime, zoneId);
    if (beginDate == null) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_VALUE_ERROR, "beginTime");
    }
    Date endDate = DateUtils.getZoneDate(endTime, zoneId);
    if (endDate == null) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_VALUE_ERROR, "endTime");
    }

    List<String> contractCodes = new ArrayList<>();
    contractCodes.add(contractCode);
    FutureKlineRequest request = FutureKlineRequest.newRequest(contractCodes, period == null ? FutureKType.day : period,
        beginDate.getTime(), endDate.getTime(), pageSize);

    List<FutureKlineItem> results = new ArrayList<>(total);
    do {
      FutureKlineResponse response = TigerHttpClient.getInstance().execute(request);
      if (!response.isSuccess()) {
        throw new TigerApiException(response.getMessage());
      }
      if (response.getFutureKlineItems().size() == 0) {
        break;
      }
      FutureKlineBatchItem klineItem = response.getFutureKlineItems().get(0);
      results.addAll(klineItem.getItems());

      if (klineItem.getNextPageToken() == null) {
        break;
      }
      // 60 times per minute
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException ignoreIE) {
      }
      // set nextPageToken and search next page data
      request.withPageToken(klineItem.getNextPageToken());
    } while (results.size() < total);
    return results;
  }

}
