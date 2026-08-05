package com.tigerbrokers.stock.openapi.client.https.request.option;

import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionAnalysisModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainFilterModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionCommonModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionKlineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionAnalysisResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionBriefResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionDepthResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionExpirationResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionKlineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionSymbolResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionTimelineResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionTradeTickResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.WarrantFilterResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.WarrantQuoteResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionAnalysisPeriod;
import com.tigerbrokers.stock.openapi.client.struct.enums.SortDir;
import com.tigerbrokers.stock.openapi.client.struct.enums.WarrantState;
import com.tigerbrokers.stock.openapi.client.struct.enums.WarrantType;
import com.tigerbrokers.stock.openapi.client.struct.enums.OptionPrice;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class OptionRequestTest {
  private static final OptionCommonModel COMMON = new OptionCommonModel("AAPL", "CALL", "160", 20240119L);
  private static final OptionChainModel CHAIN = new OptionChainModel("AAPL", 20240119L);

  @Test public void testOptionAnalysisRequest() {
    Assert.assertEquals(MethodName.OPTION_ANALYSIS, new OptionAnalysisRequest().getApiMethodName());
    Assert.assertEquals(OptionAnalysisResponse.class, new OptionAnalysisRequest().getResponseClass());
    OptionAnalysisRequest req = OptionAnalysisRequest.newRequest(Arrays.asList(new OptionAnalysisModel("AAPL", "3year")), Market.US);
    Assert.assertNotNull(req.getApiModel());
    Assert.assertEquals(Market.US, req.getApiModel().getMarket());
    Assert.assertNotNull(OptionAnalysisRequest.of("AAPL", "3year").getApiModel());
    Assert.assertNotNull(OptionAnalysisRequest.of("AAPL", "3year", Market.HK).getApiModel());
    Assert.assertNotNull(OptionAnalysisRequest.of("AAPL", OptionAnalysisPeriod.THREE_YEAR).getApiModel());
    Assert.assertNotNull(OptionAnalysisRequest.of("AAPL", OptionAnalysisPeriod.THREE_YEAR, Market.HK).getApiModel());
    Assert.assertNotNull(OptionAnalysisRequest.of("AAPL", "3year", true).getApiModel());
    Assert.assertNotNull(OptionAnalysisRequest.of("AAPL", "3year", true, Market.HK).getApiModel());
    Assert.assertNotNull(OptionAnalysisRequest.of("AAPL", OptionAnalysisPeriod.THREE_YEAR, true).getApiModel());
    Assert.assertNotNull(OptionAnalysisRequest.of("AAPL", OptionAnalysisPeriod.THREE_YEAR, true, Market.HK).getApiModel());
    OptionAnalysisRequest chained = OptionAnalysisRequest.of("AAPL", "3year").market(Market.SG);
    Assert.assertEquals(Market.SG, chained.getApiModel().getMarket());
  }
  @Test public void testOptionBriefQueryRequest() {
    Assert.assertEquals(MethodName.OPTION_BRIEF, new OptionBriefQueryRequest().getApiMethodName());
    Assert.assertEquals(OptionBriefResponse.class, new OptionBriefQueryRequest().getResponseClass());
    Assert.assertNotNull(OptionBriefQueryRequest.of(Arrays.asList(COMMON)).getApiModel());
    Assert.assertNotNull(OptionBriefQueryRequest.of(COMMON).getApiModel());
    Assert.assertNotNull(OptionBriefQueryRequest.of(COMMON, COMMON).getApiModel());
    Assert.assertNotNull(OptionBriefQueryRequest.of(COMMON, COMMON, COMMON).getApiModel());
    Assert.assertNotNull(OptionBriefQueryRequest.of(COMMON, COMMON, COMMON, COMMON).getApiModel());
  }
  @Test public void testOptionBriefQueryV2Request() {
    Assert.assertEquals(MethodName.OPTION_BRIEF, new OptionBriefQueryV2Request().getApiMethodName());
    Assert.assertEquals(OptionBriefResponse.class, new OptionBriefQueryV2Request().getResponseClass());
    Assert.assertNotNull(OptionBriefQueryV2Request.of(Arrays.asList(COMMON)).getApiModel());
    Assert.assertNotNull(OptionBriefQueryV2Request.of(COMMON).getApiModel());
    Assert.assertNotNull(OptionBriefQueryV2Request.of(COMMON, COMMON).getApiModel());
    OptionBriefQueryV2Request chained = OptionBriefQueryV2Request.of(COMMON).market(Market.HK);
    Assert.assertNotNull(chained.getApiModel());
  }
  @Test public void testOptionChainQueryRequest() {
    Assert.assertEquals(MethodName.OPTION_CHAIN, new OptionChainQueryRequest().getApiMethodName());
    Assert.assertEquals(OptionChainResponse.class, new OptionChainQueryRequest().getResponseClass());
    Assert.assertNotNull(OptionChainQueryRequest.of(Arrays.asList(CHAIN)).getApiModel());
    Assert.assertNotNull(OptionChainQueryRequest.of(CHAIN).getApiModel());
    Assert.assertNotNull(OptionChainQueryRequest.of(CHAIN, CHAIN).getApiModel());
    Assert.assertNotNull(OptionChainQueryRequest.of(CHAIN, CHAIN, CHAIN).getApiModel());
  }
  @Test public void testOptionChainQueryV3Request() {
    Assert.assertEquals(MethodName.OPTION_CHAIN, new OptionChainQueryV3Request().getApiMethodName());
    Assert.assertEquals(OptionChainResponse.class, new OptionChainQueryV3Request().getResponseClass());
    OptionChainFilterModel filter = new OptionChainFilterModel();
    Assert.assertNotNull(OptionChainQueryV3Request.of(CHAIN, filter).getApiModel());
    Assert.assertNotNull(OptionChainQueryV3Request.of(CHAIN, filter, Market.US).getApiModel());
    Assert.assertNotNull(OptionChainQueryV3Request.of(Arrays.asList(CHAIN), filter).getApiModel());
    Assert.assertNotNull(OptionChainQueryV3Request.of(Arrays.asList(CHAIN), filter, Market.US).getApiModel());
    OptionChainQueryV3Request chained = new OptionChainQueryV3Request();
    chained.setMarket(Market.HK);
    chained.setOptionBasic(Arrays.asList(CHAIN));
    chained.setOptionFilter(filter);
    chained.setReturnGreekValue(true);
    Assert.assertNotNull(chained.getApiModel());
  }
  @Test public void testOptionDepthQueryRequest() {
    Assert.assertEquals(MethodName.OPTION_DEPTH, new OptionDepthQueryRequest().getApiMethodName());
    Assert.assertEquals(OptionDepthResponse.class, new OptionDepthQueryRequest().getResponseClass());
    Assert.assertNotNull(OptionDepthQueryRequest.of(Arrays.asList(COMMON)).getApiModel());
    Assert.assertNotNull(OptionDepthQueryRequest.of(COMMON).getApiModel());
    Assert.assertNotNull(OptionDepthQueryRequest.of(COMMON, COMMON).getApiModel());
    OptionDepthQueryRequest chained = OptionDepthQueryRequest.of(COMMON).market(Market.HK);
    Assert.assertNotNull(chained.getApiModel());
  }
  @Test public void testOptionExpirationQueryRequest() {
    Assert.assertEquals(MethodName.OPTION_EXPIRATION, new OptionExpirationQueryRequest().getApiMethodName());
    Assert.assertEquals(OptionExpirationResponse.class, new OptionExpirationQueryRequest().getResponseClass());
    Assert.assertNotNull(OptionExpirationQueryRequest.of(Arrays.asList("AAPL")).getApiModel());
    OptionExpirationQueryRequest chained = new OptionExpirationQueryRequest();
    chained.market(Market.HK);
    Assert.assertNotNull(chained.getApiModel());
    Assert.assertNotNull(new OptionExpirationQueryRequest(Arrays.asList("AAPL"), Market.US).getApiModel());
  }
  @Test public void testOptionKlineQueryRequest() {
    Assert.assertEquals(MethodName.OPTION_KLINE, new OptionKlineQueryRequest().getApiMethodName());
    Assert.assertEquals(OptionKlineResponse.class, new OptionKlineQueryRequest().getResponseClass());
    OptionKlineModel m = new OptionKlineModel();
    Assert.assertNotNull(OptionKlineQueryRequest.of(Arrays.asList(m)).getApiModel());
    Assert.assertNotNull(OptionKlineQueryRequest.of(m).getApiModel());
    Assert.assertNotNull(OptionKlineQueryRequest.of(m, m).getApiModel());
    Assert.assertNotNull(OptionKlineQueryRequest.of(m, m, m).getApiModel());
  }
  @Test public void testOptionKlineQueryV2Request() {
    Assert.assertEquals(MethodName.OPTION_KLINE, new OptionKlineQueryV2Request().getApiMethodName());
    Assert.assertEquals(OptionKlineResponse.class, new OptionKlineQueryV2Request().getResponseClass());
    OptionKlineModel m = new OptionKlineModel();
    Assert.assertNotNull(OptionKlineQueryV2Request.of(Arrays.asList(m)).getApiModel());
    Assert.assertNotNull(OptionKlineQueryV2Request.of(m).getApiModel());
    OptionKlineQueryV2Request chained = OptionKlineQueryV2Request.of(m).market(Market.HK);
    Assert.assertNotNull(chained.getApiModel());
  }
  @Test public void testOptionSymbolRequest() {
    Assert.assertEquals(MethodName.ALL_HK_OPTION_SYMBOLS, new OptionSymbolRequest().getApiMethodName());
    Assert.assertEquals(OptionSymbolResponse.class, new OptionSymbolRequest().getResponseClass());
    Assert.assertNotNull(OptionSymbolRequest.newRequest(Market.HK).getApiModel());
    Assert.assertNotNull(OptionSymbolRequest.newRequest(Market.HK, Language.zh_CN).getApiModel());
    OptionSymbolRequest chained = new OptionSymbolRequest();
    chained.market(Market.HK);
    chained.language(Language.zh_CN);
    Assert.assertNotNull(chained.getApiModel());
  }
  @Test public void testOptionTimelineRequest() {
    Assert.assertEquals(MethodName.OPTION_TIMELINE, new OptionTimelineRequest().getApiMethodName());
    Assert.assertEquals(OptionTimelineResponse.class, new OptionTimelineRequest().getResponseClass());
    OptionTimelineModel m = new OptionTimelineModel();
    Assert.assertNotNull(OptionTimelineRequest.of(Arrays.asList(m)).getApiModel());
    Assert.assertNotNull(OptionTimelineRequest.of(m).getApiModel());
    Assert.assertNotNull(OptionTimelineRequest.of(m, m).getApiModel());
    OptionTimelineRequest chained = OptionTimelineRequest.of(m).market(Market.HK);
    Assert.assertNotNull(chained.getApiModel());
  }
  @Test public void testOptionTradeTickQueryRequest() {
    Assert.assertEquals(MethodName.OPTION_TRADE_TICK, new OptionTradeTickQueryRequest().getApiMethodName());
    Assert.assertEquals(OptionTradeTickResponse.class, new OptionTradeTickQueryRequest().getResponseClass());
    Assert.assertNotNull(OptionTradeTickQueryRequest.of(Arrays.asList(COMMON)).getApiModel());
    Assert.assertNotNull(OptionTradeTickQueryRequest.of(COMMON).getApiModel());
    Assert.assertNotNull(OptionTradeTickQueryRequest.of(COMMON, COMMON).getApiModel());
    Assert.assertNotNull(OptionTradeTickQueryRequest.of(COMMON, COMMON, COMMON).getApiModel());
  }
  @Test public void testWarrantFilterRequest() {
    Assert.assertEquals(MethodName.WARRANT_FILTER, new WarrantFilterRequest().getApiMethodName());
    Assert.assertEquals(WarrantFilterResponse.class, new WarrantFilterRequest().getResponseClass());
    Assert.assertNotNull(WarrantFilterRequest.newRequest("AAPL").getApiModel());
    WarrantFilterRequest chained = WarrantFilterRequest.newRequest("AAPL").lang(Language.zh_CN).page(1).pageSize(10).sortFieldName("volume").sortDir(SortDir.SortDir_Ascend);
    Assert.assertNotNull(chained.getApiModel());
  }
  @Test public void testWarrantQuoteRequest() {
    Assert.assertEquals(MethodName.WARRANT_REAL_TIME_QUOTE, new WarrantQuoteRequest().getApiMethodName());
    Assert.assertEquals(WarrantQuoteResponse.class, new WarrantQuoteRequest().getResponseClass());
    Assert.assertNotNull(WarrantQuoteRequest.newRequest(Arrays.asList("AAPL")).getApiModel());
    WarrantQuoteRequest chained = WarrantQuoteRequest.newRequest(Arrays.asList("AAPL")).lang(Language.zh_CN);
    Assert.assertNotNull(chained.getApiModel());
  }
  @Test public void testOptionCommonModelFourArgConstructor() {
    OptionCommonModel m = new OptionCommonModel("AAPL", "PUT", "150", 20240315L);
    Assert.assertEquals("AAPL", m.getSymbol());
    Assert.assertEquals("PUT", m.getRight());
    Assert.assertEquals("150", m.getStrike());
    Assert.assertEquals(Long.valueOf(20240315L), m.getExpiry());
  }
  @Test public void testOptionChainModelConstructors() {
    OptionChainModel m1 = new OptionChainModel("AAPL", 20240119L);
    Assert.assertEquals("AAPL", m1.getSymbol());
    Assert.assertEquals(Long.valueOf(20240119L), m1.getExpiry());
    OptionChainModel m2 = new OptionChainModel("AAPL", "2024-01-19");
    Assert.assertEquals("AAPL", m2.getSymbol());
  }
  @Test public void testEdgeCasesNullMarket() {
    OptionAnalysisRequest req = OptionAnalysisRequest.newRequest(Arrays.asList(new OptionAnalysisModel("AAPL", "3year")), null);
    Assert.assertEquals(Market.US, req.getApiModel().getMarket());
    OptionExpirationQueryRequest expReq = new OptionExpirationQueryRequest(Arrays.asList("AAPL"), null);
    Assert.assertNotNull(expReq.getApiModel());
  }

  @Test public void testWarrantFilterRequest_allBuilders() {
    WarrantFilterRequest req = WarrantFilterRequest.newRequest("AAPL");
    req.lang(Language.zh_CN).page(1).pageSize(10).sortFieldName("volume").sortDir(SortDir.SortDir_Ascend)
       .warrantType(WarrantType.Call, WarrantType.Put)
       .issuerName("BrokerA").expireYM("2024-06")
       .state(WarrantState.Normal)
       .inOutPrice(OptionPrice.ITM, OptionPrice.OTM)
       .lotSize(new HashSet<>(Arrays.asList(100, 1000)))
       .entitlementRatio(new HashSet<>(Arrays.asList(0.1)))
       .strike(100.0, 200.0)
       .effectiveLeverage(1.0, 5.0)
       .leverageRatio(2.0, 10.0)
       .callPrice(0.5, 1.5)
       .volume(1000L, 5000L)
       .premium(0.1, 0.5)
       .outstandingRatio(0.01, 0.5)
       .impliedVolatility(0.2, 0.8);
    Assert.assertNotNull(req.getApiModel());
    Assert.assertNotNull(req.getApiModel().getImpliedVolatility());
  }
  @Test public void testWarrantFilterRequest_emptyVarargs_noOp() {
    // empty varargs should not set warrantType/inOutPrice
    WarrantFilterRequest req = WarrantFilterRequest.newRequest("AAPL");
    req.warrantType();
    req.inOutPrice();
    Assert.assertNull(req.getApiModel().getWarrantType());
    Assert.assertNull(req.getApiModel().getInOutPrice());
  }
  @Test public void testWarrantFilterRequest_nullState_noOp() {
    WarrantFilterRequest req = WarrantFilterRequest.newRequest("AAPL");
    req.state(null);
    Assert.assertNull(req.getApiModel().getState());
  }
  @Test public void testWarrantFilterRequest_nullVarargsInArray() {
    // null entries inside the array are skipped
    WarrantFilterRequest req = WarrantFilterRequest.newRequest("AAPL");
    req.warrantType(WarrantType.Call, null);
    Assert.assertEquals(1, req.getApiModel().getWarrantType().size());
    req.inOutPrice(OptionPrice.ITM, null);
    Assert.assertEquals(1, req.getApiModel().getInOutPrice().size());
  }
  @Test public void testWarrantFilterRequest_getApiModelLazyInit() {
    WarrantFilterRequest req = new WarrantFilterRequest();
    Assert.assertNotNull(req.getApiModel());
  }
}
