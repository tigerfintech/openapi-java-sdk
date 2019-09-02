package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.item.CorporateDividendItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionBriefItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.MarketItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.RealTimeQuoteItem;
import com.tigerbrokers.stock.openapi.client.https.request.financial.CorporateDividendRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionBriefQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteMarketRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteRealTimeQuoteRequest;
import com.tigerbrokers.stock.openapi.client.https.response.financial.CorporateDividendResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionBriefResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteMarketResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse;
import com.tigerbrokers.stock.openapi.client.struct.OptionFundamentals;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeZoneId;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 期权指标计算器
 */
public class OptionCalcUtils {

  private static final int TIME_MILLIS_IN_ONE_HOUR = 60 * 60 * 1000;

  private static final int HALF_DAY_MILLS = 12 * TIME_MILLIS_IN_ONE_HOUR;

  private static double ACCURACY = 1.0e-6;

  private static double n(double x) {
    return (1 / Math.sqrt(2 * Math.PI)) * Math.exp(-x * x / 2);
  }

  private static ExecutorService executorService = new ThreadPoolExecutor(4, 4,
      0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), (r) -> {
    Thread t = Executors.defaultThreadFactory().newThread(r);
    t.setDaemon(true);
    return t;
  }, new ThreadPoolExecutor.AbortPolicy());

  private static double N(double z) {
    if (z > 6.0) {
      return 1.0;
    }
    if (z < -6.0) {
      return 0.0;
    }

    double b1 = 0.31938153;
    double b2 = -0.356563782;
    double b3 = 1.781477937;
    double b4 = -1.821255978;
    double b5 = 1.330274429;
    double p = 0.2316419;
    double c2 = 0.3989423;

    double a = Math.abs(z);
    double t = 1.0 / (1.0 + a * p);
    double b = c2 * Math.exp((-z) * (z / 2.0));
    double n = ((((b5 * t + b4) * t + b3) * t + b2) * t + b1) * t;
    n = 1.0 - b * n;
    if (z < 0.0) {
      n = 1.0 - n;
    }
    return n;
  }

  private static double call(double S, double X, double r, double q, double sigma, double time) {
    double sigma_sqr = Math.pow(sigma, 2);
    double time_sqrt = Math.sqrt(time);
    double d1 = (Math.log(S / X) + (r - q + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
    double d2 = d1 - (sigma * time_sqrt);
    return S * Math.exp(-q * time) * N(d1) - X * Math.exp(-r * time) * N(d2);
  }

  private static double put(double S, double K, double r, double q, double sigma, double time) {
    double sigma_sqr = Math.pow(sigma, 2);
    double time_sqrt = Math.sqrt(time);
    double d1 = (Math.log(S / K) + (r - q + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
    double d2 = d1 - (sigma * time_sqrt);
    return K * Math.exp(-r * time) * N(-d2) - S * Math.exp(-q * time) * N(-d1);
  }

  private static double optionPriceAmericanCallApproximatedBaw(double S, double X, double r, double b, double sigma,
      double time) {
    double sigma_sqr = sigma * sigma;
    double time_sqrt = Math.sqrt(time);
    double nn = 2.0 * b / sigma_sqr;
    double m = 2.0 * r / sigma_sqr;
    double K = 1.0 - Math.exp(-r * time);
    double q2 = (-(nn - 1) + Math.sqrt(Math.pow((nn - 1), 2.0) + (4 * m / K))) * 0.5;

    // seed value from paper
    double q2_inf = 0.5 * (-(nn - 1.0) + Math.sqrt(Math.pow((nn - 1), 2.0) + 4.0 * m));
    double S_star_inf = X / (1.0 - 1.0 / q2_inf);
    double h2 = -(b * time + 2.0 * sigma * time_sqrt) * (X / (S_star_inf - X));
    double S_seed = X + (S_star_inf - X) * (1.0 - Math.exp(h2));

    int no_iterations = 0; // iterate on S to find S_star, using Newton steps
    double Si = S_seed;
    double g = 1;
    double gprime = 1.0;
    while ((Math.abs(g) > ACCURACY) && (Math.abs(gprime) > ACCURACY) // to avoid exploding Newton's
        && (no_iterations++ < 500) && (Si > 0.0) && !Double.isNaN(Si)) {
      double c = call(Si, X, r, b, sigma, time);
      double d1 = (Math.log(Si / X) + (b + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
      g = (1.0 - 1.0 / q2) * Si - X - c + (1.0 / q2) * Si * Math.exp((b - r) * time) * N(d1);
      gprime = (1.0 - 1.0 / q2) * (1.0 - Math.exp((b - r) * time) * N(d1)) +
          (1.0 / q2) * Math.exp((b - r) * time) * n(d1) * (1.0 / (sigma * time_sqrt));
      Si = Si - (g / gprime);
    }
    double S_star = 0;
    if (Math.abs(g) > ACCURACY) {
      S_star = S_seed;
    } // did not converge
    else {
      S_star = Si;
    }
    double C = 0;
    double c = call(S, X, r, b, sigma, time);
    if (S >= S_star) {
      C = S - X;
    } else {
      double d1 = (Math.log(S_star / X) + (b + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
      double A2 = (1.0 - Math.exp((b - r) * time) * N(d1)) * (S_star / q2);
      C = c + A2 * Math.pow((S / S_star), q2);
    }
    return Double.isNaN(C) ? c : Math.max(C, c); // know value will never be less than BS value
  }

  private static double optionPriceAmericanPutApproximatedBaw(double S, double X, double r, double b, double sigma,
      double time) {
    double sigma_sqr = sigma * sigma;
    double time_sqrt = Math.sqrt(time);
    double M = 2.0 * r / sigma_sqr;
    double NN = 2.0 * b / sigma_sqr;
    double K = 1.0 - Math.exp(-r * time);
    double q1 = 0.5 * (-(NN - 1) - Math.sqrt(Math.pow((NN - 1), 2.0) + (4.0 * M / K)));

    // now find initial S value
    double q1_inf = 0.5 * (-(NN - 1) - Math.sqrt(Math.pow((NN - 1), 2.0) + 4.0 * M));
    double S_star_star_inf = X / (1.0 - 1.0 / q1_inf);
    double h1 = (b * time - 2 * sigma * time_sqrt) * (X / (X - S_star_star_inf));
    double S_seed = S_star_star_inf + (X - S_star_star_inf) * Math.exp(h1);

    double Si = S_seed;  // now do Newton iterations to solve for S**
    int no_iterations = 0;
    double g = 1;
    double gprime = 1;
    while ((Math.abs(g) > ACCURACY) && (Math.abs(gprime) > ACCURACY) // to avoid non-convergence
        && (no_iterations++ < 500) && Si > 0.0 && !Double.isNaN(Si)) {
      double p = put(Si, X, r, b, sigma, time);
      double d1 = (Math.log(Si / X) + (b + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
      g = X - Si - p + (1 - Math.exp((b - r) * time) * N(-d1)) * Si / q1;
      gprime = (1.0 / q1 - 1.0) * (1.0 - Math.exp((b - r) * time) * N(-d1)) +
          (1.0 / q1) * Math.exp((b - r) * time) * (1.0 / (sigma * time_sqrt)) * n(-d1);
      Si = Si - (g / gprime);
    }
    double S_star_star = Si;
    if (g > ACCURACY) {
      S_star_star = S_seed;
    }
    double P = 0;
    double p = put(S, X, r, b, sigma, time);
    if (S > S_star_star) {
      double d1 = (Math.log(S_star_star / X) + (b + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
      double A1 = -(S_star_star / q1) * (1 - Math.exp((b - r) * time) * N(-d1));
      P = p + A1 * Math.pow((S / S_star_star), q1);
    } else {
      P = X - S;
    }
    return Math.max(P, p);  // should not be lower than Black Scholes value
  }

  /**
   * 买入盈利概率
   */
  private static double optionBuyCallProfitRate(double S, double K, double p, double r, double sigma, double time) {
    double time_sqrt = Math.sqrt(time);
    double d1 = Math.log(S / (K + p)) + r * time;
    double d2 = sigma * time_sqrt;
    return N(d1 / d2);
  }

  /**
   * 买入盈利概率
   */
  private static double optionBuyPutProfitRate(double S, double K, double p, double r, double sigma, double time) {
    double time_sqrt = Math.sqrt(time);
    double d1 = Math.log(S / (K - p)) + r * time;
    double d2 = sigma * time_sqrt;
    double result = N(d1 / d2);
    return 1 - result;
  }

  /**
   * 求call的隐含波动率
   *
   * @param target 目标价
   * @param S 股票 价格
   * @param X 行权价格
   * @param r 国债利率
   * @param b =r
   * @param time 时间，以年为单位半年是0.5
   */
  private static double getVolatilityCall(double target, double S, double X, double r, double b, double time) {
    double min = 0;
    double max = 2.5;
    double sig = min;
    double x = 1000;
    int maxCount = 100;
    while (Math.abs(x - target) > 0.001 && maxCount-- > 0) {
      sig = (min + max) / 2;
      x = optionPriceAmericanCallApproximatedBaw(S, X, r, b, sig, time);
      if (x < target) {
        min = sig;
      } else if (x > target) {
        max = sig;
      }
    }
    return sig;
  }

  private static double getTimeValueCall(double strike, double spot, double price) {
    double res = 0;
    if (strike > spot) {
      res = price;
    } else {
      res = price + strike - spot;
    }
    return res;
  }

  private static double getTimeValuePut(double strike, double spot, double price) {
    double res = 0;
    if (strike < spot) {
      res = price;
    } else {
      res = price - (strike - spot);
    }
    return res;
  }

  /**
   * 求put的隐含波动率
   *
   * @param target 目标价
   * @param S 股票 价格
   * @param X 行权价格
   * @param r 国债利率
   * @param b =r
   * @param time 时间，以年为单位半年是0.5
   */
  private static double getVolatilityPut(double target, double S, double X, double r, double b, double time) {
    double min = 0;
    double max = 2.5;
    double sig = min;
    double x = 1000;
    int maxCount = 100;
    while (Math.abs(x - target) > 0.001 && maxCount-- > 0) {
      sig = (min + max) / 2;
      x = optionPriceAmericanPutApproximatedBaw(S, X, r, b, sig, time);
      if (x < target) {
        min = sig;
      } else if (x > target) {
        max = sig;
      }
    }
    return sig;
  }

  private static OptionResult.OptionIndex optionPricePartialsCallBlackScholes(double S,     // spot price
      double K,     // Strike (exercise) price,
      double r,     // interest rate
      double sigma, // volatility
      double time) {   // partial wrt r
    double time_sqrt = Math.sqrt(time);
    double d1 = (Math.log(S / K) + r * time) / (sigma * time_sqrt) + 0.5 * sigma * time_sqrt;
    double d2 = d1 - (sigma * time_sqrt);
    double Delta = N(d1);
    double Gamma = n(d1) / (S * sigma * time_sqrt);
    double Theta = (-(S * sigma * n(d1)) / (2 * time_sqrt) - r * K * Math.exp(-r * time) * N(d2)) / 365f;
    double Vega = S * time_sqrt * n(d1) / 100f;
    double Rho = K * time * Math.exp(-r * time) * N(d2);
    return new OptionResult.OptionIndex(Delta, Gamma, Theta, Vega, Rho);
  }

  private static OptionResult.OptionIndex optionPricePartialsPutBlackScholes(double S, // spot price
      double K, // Strike (exercise) price,
      double r,  // interest rate
      double sigma, double time) {    // partial wrt r
    double time_sqrt = Math.sqrt(time);
    double d1 = (Math.log(S / K) + r * time) / (sigma * time_sqrt) + 0.5 * sigma * time_sqrt;
    double d2 = d1 - (sigma * time_sqrt);
    double Delta = -N(-d1);
    double Gamma = n(d1) / (S * sigma * time_sqrt);
    double Theta = (-(S * sigma * n(d1)) / (2 * time_sqrt) + r * K * Math.exp(-r * time) * N(-d2)) / 365f;
    double Vega = S * time_sqrt * n(d1) / 100f;
    double Rho = -K * time * Math.exp(-r * time) * N(-d2);
    return new OptionResult.OptionIndex(Delta, Gamma, Theta, Vega, Rho);
  }

  /**
   * 溢价率
   */
  private static double calcPutPremiumRate(double opPrice, double stockPrice, double strike) {
    return (strike - opPrice - stockPrice) / stockPrice;
  }

  /**
   * 溢价率
   */
  private static double calcCallPremiumRate(double opPrice, double stockPrice, double strike) {
    return (strike + opPrice - stockPrice) / stockPrice;
  }

  /**
   * 杠杆率
   */
  private static double getLeverage(double targetPrice, double latestPrice, double delta) {
    return Math.abs(delta * latestPrice) / targetPrice;
  }

  /**
   * 内在价值
   */
  private static double getInsideValue(String right, double latestPrice, double strike) {
    double value = "CALL".equalsIgnoreCase(right) ? latestPrice - strike : strike - latestPrice;
    if (value < 0) {
      value = 0;
    }
    return value;
  }

  /**
   * 计算期权基本面信息
   *
   * @param r 国债利率
   * @param expiryLong 过期日（long类型）
   * @param executeDateLong 除权除息日（long类型）
   * @param latestPrice 最新正股价格
   * @param targetPrice 期权目标价格
   * @param dividendAmount 分红
   * @param strike 行权价
   * @param type CALL or PUT
   * @param currentTime 当前时间（long类型）
   * @param isTrading 是否在正股交易时间
   */
  private static OptionResult calcOptionIndex(double r, long expiryLong, long executeDateLong,
      double latestPrice, double targetPrice, double dividendAmount, double strike, String type, long currentTime,
      boolean isTrading) {

    OptionResult result = new OptionResult();
    double diff = ((expiryLong - currentTime + HALF_DAY_MILLS) /
        (24.0f * TIME_MILLIS_IN_ONE_HOUR) + 1 + (isTrading ? 0 : 1)) / 365.0f;
    final boolean needDividend =
        executeDateLong != 0 && expiryLong > executeDateLong && currentTime < executeDateLong + HALF_DAY_MILLS;
    latestPrice = latestPrice - (needDividend ? dividendAmount : 0);

    if (targetPrice == 0 || strike == 0) {
      return null;
    }
    double sigma = 0;
    if ("PUT".equalsIgnoreCase(type) && targetPrice > strike - latestPrice) {
      result.setTimeValue(getTimeValuePut(strike, latestPrice, targetPrice));
      sigma = getVolatilityPut(targetPrice, latestPrice, strike, r, r, diff);
      result.setIndex(optionPricePartialsPutBlackScholes(latestPrice, strike, r, sigma, diff));
      result.setPremiumRate(calcPutPremiumRate(targetPrice, latestPrice, strike));
      result.setProfitRate(optionBuyPutProfitRate(latestPrice, strike, targetPrice, r, sigma, diff));
    } else if ("CALL".equalsIgnoreCase(type) && targetPrice > latestPrice - strike) {
      result.setTimeValue(getTimeValueCall(strike, latestPrice, targetPrice));
      sigma = getVolatilityCall(targetPrice, latestPrice, strike, r, r, diff);
      result.setPremiumRate(calcCallPremiumRate(targetPrice, latestPrice, strike));
      result.setProfitRate(optionBuyCallProfitRate(latestPrice, strike, targetPrice, r, sigma, diff));
      result.setIndex(optionPricePartialsCallBlackScholes(latestPrice, strike, r, sigma, diff));
    } else {
      result.setIndex(new OptionResult.OptionIndex(Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN));
    }
    result.setVolatility(sigma);
    result.setLeverage(getLeverage(targetPrice, latestPrice, result.getIndex().getDelta()));
    result.setInsideValue(getInsideValue(type, latestPrice, strike));
    return result;
  }

  private static boolean isEmpty(List list) {
    return list == null || list.isEmpty();
  }

  /**
   * 该方法封装了获取期权基本面信息的请求过程
   *
   * @param client TigerHttpClient
   * @param symbol 股票代码
   * @param right CALL or PUT
   * @param strike 行权价
   * @param expiry 过期日（yyyy-MM-dd）
   * @return 期权基本面信息
   * @throws Exception
   */
  public static OptionFundamentals getOptionFundamentals(TigerHttpClient client,
      String symbol,
      String right,
      String strike,
      String expiry) throws Exception {

    DateTimeFormatter dtf = DateUtils.DATE_FORMAT;

    LocalDate expiryDate = LocalDate.parse(expiry, dtf);
    LocalDate now = LocalDate.now(ZoneId.of(TimeZoneId.Shanghai.getZoneId()));
    if(now.compareTo(expiryDate) > 0) {
      throw new RuntimeException("期权过期日无效");
    }

    FutureTask<CorporateDividendItem> corporateDividendTask = new FutureTask<>(() -> {

      // 获取分红数据
      List<String> symbols = new ArrayList<>();
      symbols.add(symbol);
      Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.of(TimeZoneId.Shanghai.getZoneId())).toInstant());
      CorporateDividendRequest request = CorporateDividendRequest.newRequest(symbols, Market.US, date, date);
      CorporateDividendResponse corporateDividendResponse = client.execute(request);
      if (corporateDividendResponse.isSuccess()) {
        List<CorporateDividendItem> corporateDividendItems = corporateDividendResponse.getItems().get(symbol);
        if (!isEmpty(corporateDividendItems)) {
          return corporateDividendItems.get(0);
        }
      }
      return null;
    });
    executorService.execute(corporateDividendTask);

    FutureTask<MarketItem> marketItemTask = new FutureTask<>(() -> {
      // 验证是否美股是盘中状态
      QuoteMarketResponse quoteMarketResponse = client.execute(QuoteMarketRequest.newRequest(Market.US));
      if (quoteMarketResponse.isSuccess()) {
        List<MarketItem> marketItems = quoteMarketResponse.getMarketItems();
        if (!isEmpty(marketItems)) {
          return marketItems.get(0);
        }
      }
      return null;
    });
    executorService.execute(marketItemTask);

    FutureTask<RealTimeQuoteItem> realTimeQuoteItemTask = new FutureTask<>(() -> {
      QuoteRealTimeQuoteResponse quoteRealTimeQuoteResponse =
          client.execute(QuoteRealTimeQuoteRequest.newRequest(Arrays.asList(symbol)));
      if (quoteRealTimeQuoteResponse.isSuccess()) {
        List<RealTimeQuoteItem> realTimeQuoteItems = quoteRealTimeQuoteResponse.getRealTimeQuoteItems();
        if (!isEmpty(realTimeQuoteItems)) {
          return realTimeQuoteItems.get(0);
        }
      }
      throw new RuntimeException("无法获取股票最新价格！");
    });
    executorService.execute(realTimeQuoteItemTask);

    FutureTask<OptionBriefItem> optionBriefItemTask = new FutureTask<>(() -> {
      // 获取期权摘要
      OptionCommonModel model = new OptionCommonModel();
      model.setSymbol(symbol);
      model.setRight(right);
      model.setStrike(strike);
      model.setExpiry(expiry);
      OptionBriefResponse optionBriefResponse = client.execute(OptionBriefQueryRequest.of(model));

      List<OptionBriefItem> res;
      if (optionBriefResponse.isSuccess()) {
        res = optionBriefResponse.getOptionBriefItems();
        if (!isEmpty(res)) {
          return res.get(0);
        }
      }
      throw new RuntimeException("无法期权摘要信息！");
    });
    executorService.execute(optionBriefItemTask);

    double dividendAmount = 0;
    Long executeDateLong = 0L;
    CorporateDividendItem corporateDividendItem = corporateDividendTask.get();
    if (corporateDividendItem != null) {
      dividendAmount = corporateDividendItem.getAmount();
      executeDateLong = corporateDividendItem.getExecuteDate()
          .atStartOfDay(ZoneId.of(TimeZoneId.Shanghai.getZoneId()))
          .toInstant().toEpochMilli();
    }

    boolean isTrading = false;
    MarketItem marketItem = marketItemTask.get();
    if (marketItem != null) {
      isTrading = "交易中".equals(marketItem.getMarketStatus());
    }

    RealTimeQuoteItem realTimeQuoteItem = realTimeQuoteItemTask.get();
    Double latestPrice = realTimeQuoteItem.getLatestPrice();
    if (latestPrice == null) {
      throw new RuntimeException("无法获取股票最新价格！");
    }

    OptionBriefItem op = optionBriefItemTask.get();
    long expiryLong = LocalDate.parse(expiry, dtf)
        .atStartOfDay(ZoneId.of(TimeZoneId.Shanghai.getZoneId()))
        .toInstant().toEpochMilli();
    if(op.getAskPrice() == null || op.getBidPrice() == null || op.getStrike() == null) {
      throw new RuntimeException("无法期权摘要信息！");
    }
    double target = (op.getAskPrice() + op.getBidPrice()) / 2;
    double strikeD = Double.parseDouble(op.getStrike());

    OptionResult result =
        OptionCalcUtils.calcOptionIndex(op.getRatesBonds(), expiryLong,
            executeDateLong, latestPrice, target, dividendAmount, strikeD, op.getRight(),
            LocalDateTime.now().atZone(ZoneId.of(TimeZoneId.Shanghai.getZoneId())).toInstant().toEpochMilli(),
            isTrading);

    OptionFundamentals optionFundamentals = new OptionFundamentals();
    optionFundamentals.setPremiumRate(result.getPremiumRateString());
    optionFundamentals.setOpenInterest(op.getOpenInterest() + "");
    optionFundamentals.setVolatility(result.getVolatilityString());
    optionFundamentals.setHistoryVolatility(op.getVolatility());
    optionFundamentals.setDelta(result.getIndex().getDeltaString());
    optionFundamentals.setTheta(result.getIndex().getThetaString());
    optionFundamentals.setGamma(result.getIndex().getGammaString());
    optionFundamentals.setVega(result.getIndex().getVegaString());
    optionFundamentals.setTimeValue(result.getTimeValueString());
    optionFundamentals.setInsideValue(result.getInsideValueString());
    optionFundamentals.setLeverage(result.getLeverageString());
    optionFundamentals.setProfitRate(result.getProfitRateString());

    return optionFundamentals;
  }
}

@Data
class OptionResult {

  OptionIndex index;
  double timeValue;
  double premiumRate;
  double profitRate;
  double volatility;
  double leverage;
  double insideValue;

  public String getTimeValueString() {
    return formatDoubleString(timeValue, 2);
  }

  public String getPremiumRateString() {
    return doubleToPercentageString(premiumRate, 2);
  }

  public String getProfitRateString() {
    return doubleToPercentageString(profitRate, 2);
  }

  public String getVolatilityString() {
    return doubleToPercentageString(volatility, 2);
  }

  public String getLeverageString() {
    return formatDoubleString(leverage, 2);
  }

  public String getInsideValueString() {
    return formatDoubleString(insideValue, 2);
  }

  @Data
  @AllArgsConstructor
  static class OptionIndex {

    double Delta;
    double Gamma;
    double Theta;
    double Vega;
    double Rho;

    public String getDeltaString() {
      return formatDoubleString(Delta, 3);
    }

    public String getGammaString() {
      return formatDoubleString(Gamma, 3);
    }

    public String getThetaString() {
      return formatDoubleString(Theta, 3);
    }

    public String getVegaString() {
      return formatDoubleString(Vega, 3);
    }

    public String getRhoString() {
      return formatDoubleString(Rho, 3);
    }
  }

  private static String doubleToPercentageString(double src, int fractionDigits) {
    NumberFormat nf = NumberFormat.getPercentInstance();
    nf.setMinimumFractionDigits(fractionDigits);
    nf.setMaximumFractionDigits(fractionDigits);
    String res = nf.format(src);
    return res;
  }

  private static String formatDoubleString(double src, int fractionDigits) {
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMinimumFractionDigits(fractionDigits);
    nf.setMaximumFractionDigits(fractionDigits);
    String res = nf.format(src);
    return res;
  }
}
