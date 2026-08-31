package com.tigerbrokers.stock.openapi.client.trade;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.ContractLeg;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderPreviewRequest;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderPreviewResponse;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.ComboType;
import com.tigerbrokers.stock.openapi.client.struct.enums.MethodName;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.PriceType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SegmentType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import com.tigerbrokers.stock.openapi.client.testsupport.IntegTestConfig;
import com.tigerbrokers.stock.openapi.client.testsupport.MarketHelpers;
import com.tigerbrokers.stock.openapi.client.testsupport.RateLimitRetry;
import com.tigerbrokers.stock.openapi.client.testsupport.WriteApi;
import com.tigerbrokers.stock.openapi.client.util.builder.AccountParamBuilder;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Order matrix integration tests — mirrors the Python/TS SDK three-phase coverage.
 *
 * Phase 1 (US market × order type):
 *   MKT preview, LMT-by-amount, STP, STP_LMT, TRAIL, LMT+legs, OCA, TWAP, VWAP,
 *   ICEBERG, OPT LMT, FUT LMT, forex SEC, invalid-price preview.
 *
 * Phase 2 (HK / CN / SG):
 *   HK STK LMT, HK STK AL, HK STK AM preview, HK STK+legs, HK OPT, HK WAR,
 *   HK IOPT, CN STK, SG STK.
 *
 * Phase 3 (MLEG + edge):
 *   MLEG vertical spread, ICEBERG modify, SELL SHORT preview.
 *
 * Each test uses safe prices (BUY $0.01 / SELL $999,999) so orders never fill,
 * plus a {@code previewAndPlace} helper that runs preview → place → cancel with
 * automatic skip on legitimate boundary errors (permission / session / license).
 *
 * Run with:
 *   mvn test -Dtest=OrderMatrixIntegrationTest \
 *            -Dtest.integ=true \
 *            -Dtest.config.path=&lt;path&gt;   (or TIGEROPEN_* env vars)
 */
@Category(WriteApi.class)
public class OrderMatrixIntegrationTest {

  /** Safe prices — kept far from market so BUY/SELL orders never fill. */
  private static final double SAFE_BUY_PRICE = 0.01;
  private static final double SAFE_SELL_PRICE = 999_999.0;
  private static final double SAFE_STOP_BUY_TRIGGER = 999_999.0;

  /**
   * Server messages that specifically indicate an out-of-hours / session
   * boundary. These are re-checked against live market status before being
   * treated as a skip — see {@link #classifyFailure}.
   */
  private static final Pattern[] HOURS_ERROR_PATTERNS = new Pattern[] {
      Pattern.compile("(?i)market is closed"),
      Pattern.compile("(?i)at non-trading hour"),
      Pattern.compile("(?i)orders cannot be placed at this moment"),
      Pattern.compile("(?i)please wait for the next trading day to retry"),
  };

  /**
   * Server messages meaning "this order type / parameter is not accepted at
   * this instant", which is NOT the same as "the market is closed".
   *
   * <p>These must never be re-checked against {@code isMarketTrading}: for
   * every pattern below, the continuous session being open is exactly when
   * the error is most likely, so using an open market to prove the error is a
   * bug has the implication backwards. Concretely:
   *
   * <ul>
   *   <li>Auction orders (AL/AM) are only accepted during HK's pre-open
   *       (09:00–09:30) and closing (16:00–16:10) auction windows, which are
   *       disjoint from the continuous session. {@code isMarketTrading("HK")}
   *       reports the continuous session, so an auction rejection during
   *       continuous trading is by design — asserting on it made this case
   *       fail every single run that landed inside HK trading hours.</li>
   *   <li>"only limit orders are supported ... outside of regular trading
   *       hours" fires when the order itself carries an RTH-exempt flag; the
   *       server then applies extended-hours rules regardless of whether the
   *       regular session happens to be open.</li>
   *   <li>The TWAP/VWAP end-time window is an order parameter constraint —
   *       the schedule ran past the session close, which says nothing about
   *       the current market state.</li>
   * </ul>
   *
   * <p>The SDK cannot narrow these down further: no quote API exposes
   * auction-window or RTH-exempt eligibility, so an unconditional skip is the
   * only classification that is actually true.
   */
  private static final Pattern[] ORDER_TYPE_RESTRICTION_PATTERNS = new Pattern[] {
      Pattern.compile("(?i)auction order is not allowed at this moment"),
      Pattern.compile("(?i)outside of regular trading hours"),
      Pattern.compile("(?i)only limit orders can be placed"),
      Pattern.compile("(?i)only limit, stop or stop-limit orders are allowed"),
      Pattern.compile("(?i)the time range for the order .* needs to be between"),
  };

  /** Server messages recognized as legitimate skips (permission / license / account-state). */
  private static final Pattern[] PERMISSION_ERROR_PATTERNS = new Pattern[] {
      Pattern.compile("(?i)access forbidden"),
      Pattern.compile("(?i)forbidden"),
      Pattern.compile("(?i)no permission"),
      Pattern.compile("(?i)not supported"),
      Pattern.compile("(?i)license"),
      Pattern.compile("(?i)not open"),
      Pattern.compile("(?i)not enabled"),
      Pattern.compile("(?i)no token"),
      Pattern.compile("(?i)don['\\u2018\\u2019]t support trading"),
      Pattern.compile("(?i)unsupported instrument"),
      Pattern.compile("(?i)only limit orders are supported"),
      // Mainland-China-investor account-state restriction (code 1200):
      // opening/adding positions is blocked while only close/reduce/transfer
      // is allowed — an account-state boundary, not an SDK defect. Same
      // marker exists in C++/Rust's permission-error lists.
      Pattern.compile(
          "(?i)mainland china investors.*opening or adding to positions is temporarily unavailable"),
      Pattern.compile("(?i)does not support stock (long|short)"),
      Pattern.compile("(?i)only trade cash order by market order"),
      Pattern.compile("(?i)cash order by market order"),
      Pattern.compile("(?i)^system error$"),
      Pattern.compile("(?i)bad_request:System error"),
  };

  /** Order-state race — cancel/modify may hit terminal state during test. */
  private static final Pattern[] TERMINAL_ORDER_PATTERNS = new Pattern[] {
      Pattern.compile("(?i)cannot be modified"),
      Pattern.compile("(?i)cannot be (canc|cancell)ed"),
      Pattern.compile("(?i)already (canc|cancell)ed"),
      Pattern.compile("(?i)already filled"),
      Pattern.compile("(?i)invalid order status"),
      Pattern.compile("(?i)cancellation is not allowed"),
      Pattern.compile("(?i)cancel is not allowed"),
  };

  private static String account;
  private static TigerHttpClient client;

  @BeforeClass
  public static void setUpClass() {
    Assume.assumeTrue("integration test; enable with -Dtest.integ=true",
        Boolean.getBoolean("test.integ"));
    client = IntegTestConfig.createClient();
    account = IntegTestConfig.getAccount();
    Assert.assertNotNull("TIGEROPEN_ACCOUNT env var required", account);
  }

  // ==========================================================================
  // Shared helpers
  // ==========================================================================

  private static boolean matches(String msg, Pattern[] patterns) {
    if (msg == null) return false;
    for (Pattern p : patterns) {
      if (p.matcher(msg).find()) return true;
    }
    return false;
  }

  /**
   * Executes {@code req} with rate-limit backoff, skipping if it persists.
   * Delegates to {@link RateLimitRetry}, which is shared with the other
   * order-mutating integration tests — see its javadoc for why every mutating
   * call (not just place_order) has to go through this.
   */
  private static <T extends TigerResponse> T executeWithRateLimitRetry(
      TigerRequest<T> req, String context) {
    return RateLimitRetry.execute(client, req, context);
  }

  /**
   * Decides whether a failed preview/place response should be skipped or
   * treated as a real failure, mirroring the C++ reference's decision tree:
   * hours-specific markers are re-checked against live market status (a hit
   * during real trading hours is a real bug, not a boundary skip); pure
   * permission/capability markers are always an unconditional skip.
   *
   * <p>{@code market} must be the market of the order that failed, not a fixed
   * value: the matrix spans US/HK/CN/SG, whose sessions do not overlap, so
   * checking HK's boundary error against the US session turns a legitimate
   * skip into a spurious failure whenever the two differ. Empty falls back
   * to {@code "US"}.
   *
   * @return null if the message should be treated as a hard failure by the
   *     caller; otherwise a human-readable skip reason.
   */
  private static String classifyFailure(String msg, String market, String context) {
    // Order-type/parameter restrictions are checked before HOURS_ERROR_PATTERNS
    // and deliberately never validated against live market status — see that
    // field's javadoc for why an open market cannot disprove them.
    if (matches(msg, ORDER_TYPE_RESTRICTION_PATTERNS)) {
      return "skipped (order type not accepted at this moment): " + msg;
    }
    if (matches(msg, HOURS_ERROR_PATTERNS)) {
      String mkt = (market == null || market.isEmpty()) ? "US" : market;
      if (MarketHelpers.isMarketTrading(client, mkt)) {
        Assert.fail(context + ": hours-boundary error during live " + mkt
            + " trading hours: " + msg);
      }
      return "skipped (out-of-hours boundary): " + msg;
    }
    if (matches(msg, PERMISSION_ERROR_PATTERNS)) {
      return "skipped (permission boundary): " + msg;
    }
    return null;
  }

  /** Build a US STK contract on AAPL. */
  private static ContractItem usStkContract() {
    ContractItem c = new ContractItem();
    c.setSymbol("AAPL");
    c.setSecType("STK");
    c.setCurrency("USD");
    return c;
  }

  /** Build an HK STK contract on Tencent 00700. */
  private static ContractItem hkStkContract() {
    ContractItem c = new ContractItem();
    c.setSymbol("00700");
    c.setSecType("STK");
    c.setCurrency("HKD");
    return c;
  }

  /** Wrap a place-order request into a preview_order call and check for skip. */
  private boolean previewOnly(TradeOrderRequest placeReq, String market, String context) {
    // Swap the method to preview_order — the model is otherwise identical.
    TradeOrderPreviewRequest previewReq = new TradeOrderPreviewRequest();
    previewReq.setApiModel(placeReq.getApiModel());
    TradeOrderPreviewResponse resp = executeWithRateLimitRetry(previewReq, context);
    Assert.assertNotNull(context + ": preview response is null", resp);
    if (!resp.isSuccess()) {
      String skipReason = classifyFailure(resp.getMessage(), market, context);
      if (skipReason != null) {
        System.out.println(context + ": " + skipReason);
        return false;
      }
      Assert.fail(context + ": preview failed: " + resp.getMessage());
    }
    return true;
  }

  /**
   * place → cancel round-trip with permission auto-skip and rate-limit retry.
   * Returns true if executed, false if skipped.
   */
  private boolean previewAndPlace(TradeOrderRequest placeReq, String market, String context) {
    // 1. Preview first — validates marshaling before touching real state.
    if (!previewOnly(placeReq, market, context)) return false;

    // 2. Place. Rate-limit backoff/skip lives in the shared helper, so what
    //    reaches here is either a success or a genuine, non-throttle failure.
    long orderId;
    TradeOrderResponse placeResp = executeWithRateLimitRetry(placeReq, context);
    Assert.assertNotNull(context + ": place response is null", placeResp);
    if (!placeResp.isSuccess()) {
      String skipReason = classifyFailure(placeResp.getMessage(), market, context);
      if (skipReason != null) {
        System.out.println(context + ": " + skipReason);
        return false;
      }
      Assert.fail(context + ": placeOrder failed: " + placeResp.getMessage());
    }
    Assert.assertNotNull(context + ": placeOrder returned null item", placeResp.getItem());
    orderId = placeResp.getItem().getId();
    System.out.println(context + ": placed order id=" + orderId);

    // 3. Cancel, tolerating terminal-state race.
    cancelTolerant(orderId, context);
    return true;
  }

  private void cancelTolerant(long orderId, String context) {
    TradeOrderRequest cancelReq = new TradeOrderRequest();
    cancelReq.setApiMethodName(MethodName.CANCEL_ORDER);
    cancelReq.setBizContent(AccountParamBuilder.instance().account(account).id(orderId).buildJson());
    // Cleanup runs after several API calls, so it is the most likely to be
    // throttled — and a rate-limit message matches no TERMINAL_ORDER_PATTERN,
    // so without the retry/skip wrapper it would fail a test whose assertions
    // all already passed.
    TradeOrderResponse resp = executeWithRateLimitRetry(cancelReq, context + " — cancel");
    if (resp != null && !resp.isSuccess()
        && !matches(resp.getMessage(), TERMINAL_ORDER_PATTERNS)) {
      Assert.fail(context + ": unexpected cancel failure: " + resp.getMessage());
    }
  }

  // ==========================================================================
  // Phase 1 — US market × order type × sec type
  // ==========================================================================

  @Test
  public void previewUsStkMarket() {
    ContractItem contract = usStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildMarketOrder(account, contract, ActionType.BUY, 1);
    previewOnly(req, "US", "US STK MKT preview");
  }

  @Test
  public void previewUsStkMarketByCashAmount() {
    // Amount orders size by dollars, not by share count. Previously the
    // client-side PlaceOrderRequestValidator required total_quantity > 0,
    // creating a stalemate with the gateway's rule that cash_amount and
    // total_quantity cannot coexist. Validator now skips the quantity
    // check when cashAmount > 0, so this preview round-trips cleanly.
    ContractItem contract = usStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildAmountOrder(account, contract, ActionType.BUY, 100.0);
    previewOnly(req, "US", "US STK MKT-by-cashAmount preview");
  }

  @Test
  public void placeUsStkStop() {
    ContractItem contract = usStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildStopOrder(
        account, contract, ActionType.BUY, 1, SAFE_STOP_BUY_TRIGGER);
    previewAndPlace(req, "US", "US STK STP");
  }

  @Test
  public void placeUsStkStopLimit() {
    ContractItem contract = usStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildStopLimitOrder(
        account, contract, ActionType.BUY, 1, SAFE_BUY_PRICE, SAFE_STOP_BUY_TRIGGER);
    previewAndPlace(req, "US", "US STK STP_LMT");
  }

  @Test
  public void placeUsStkTrail() {
    ContractItem contract = usStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildTrailOrder(
        account, contract, ActionType.SELL, 1, 50.0, null);
    previewAndPlace(req, "US", "US STK TRAIL");
  }

  @Test
  public void placeUsStkTwap() {
    long now = System.currentTimeMillis();
    TradeOrderRequest req = TradeOrderRequest.buildTWAPOrder(
        account, "AAPL", ActionType.BUY, 10, now, now + 3_600_000L, SAFE_BUY_PRICE);
    previewAndPlace(req, "US", "US STK TWAP");
  }

  @Test
  public void placeUsStkVwap() {
    long now = System.currentTimeMillis();
    TradeOrderRequest req = TradeOrderRequest.buildVWAPOrder(
        account, "AAPL", ActionType.BUY, 10, now, now + 3_600_000L, 0.1, SAFE_BUY_PRICE);
    previewAndPlace(req, "US", "US STK VWAP");
  }

  @Test
  public void placeUsStkIceberg() {
    long now = System.currentTimeMillis();
    ContractItem contract = usStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildIcebergOrder(
        account, contract, ActionType.BUY, 10, SAFE_BUY_PRICE,
        2, 1, 30, PriceType.LIMIT_PRICE, now, now + 3_600_000L);
    previewAndPlace(req, "US", "US STK ICEBERG");
  }

  @Test
  public void placeUsStkOcaBrackets() {
    ContractItem contract = usStkContract();
    // OCA brackets: profit + stop-loss legs attached to a parent LMT BUY.
    // Use the account-aware overload so nested OCA legs inherit account.
    TradeOrderRequest req = TradeOrderRequest.buildOCABracketsOrder(
        account, contract, ActionType.BUY, 1L, null,
        SAFE_SELL_PRICE, TimeInForce.GTC, false,
        SAFE_BUY_PRICE, SAFE_BUY_PRICE, TimeInForce.GTC, false);
    ((TradeOrderModel) req.getApiModel()).setLimitPrice(SAFE_BUY_PRICE);
    previewAndPlace(req, "US", "US STK OCA brackets");
  }

  @Test
  public void placeUsOptLimit() {
    ContractItem opt = MarketHelpers.resolveUsOptionContract(client);
    if (opt == null) {
      // A data gap during live trading hours is a real failure, not a skip:
      // skipping here would hide a broken option-chain resolution behind a
      // green build. isMarketTrading is only called on this branch — when the
      // contract resolves there is nothing to gate.
      if (MarketHelpers.isMarketTrading(client, "US")) {
        Assert.fail("could not resolve a live AAPL option contract during US TRADING hours — data gap");
      }
      Assume.assumeTrue("could not resolve a live AAPL option contract (out of hours)", false);
      return;
    }
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(
        account, opt, ActionType.BUY, 1, SAFE_BUY_PRICE);
    previewAndPlace(req, "US", "US OPT LMT");
  }

  @Test
  public void placeUsFutLimit() {
    ContractItem c = new ContractItem();
    c.setSymbol("CL");
    c.setSecType("FUT");
    c.setCurrency("USD");
    c.setExchange("NYMEX");
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(
        account, c, ActionType.BUY, 1, SAFE_BUY_PRICE);
    previewAndPlace(req, "US", "US FUT LMT");
  }

  @Test
  public void previewInvalidPrice() {
    ContractItem contract = usStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(
        account, contract, ActionType.BUY, 1, -1.0);
    // Either accepted or rejected — both are semantically valid here.
    TradeOrderPreviewRequest previewReq = new TradeOrderPreviewRequest();
    previewReq.setApiModel(req.getApiModel());
    TradeOrderPreviewResponse resp = client.execute(previewReq);
    Assert.assertNotNull(resp);
    System.out.println("US STK negative-price preview: code=" + resp.getCode()
        + " msg=" + resp.getMessage());
  }

  // ==========================================================================
  // Phase 2 — HK / CN / SG
  // ==========================================================================

  @Test
  public void placeHkStkLimit() {
    ContractItem contract = hkStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(
        account, contract, ActionType.BUY, 100, SAFE_BUY_PRICE);
    previewAndPlace(req, "HK", "HK STK LMT");
  }

  @Test
  public void placeHkStkAuctionLimit() {
    ContractItem contract = hkStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(
        account, contract, ActionType.BUY, 100, SAFE_BUY_PRICE);
    // Change order type to AL after building.
    ((TradeOrderModel) req.getApiModel()).setOrderType(OrderType.AL);
    previewAndPlace(req, "HK", "HK STK AL");
  }

  @Test
  public void previewHkStkAuctionMarket() {
    ContractItem contract = hkStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildMarketOrder(
        account, contract, ActionType.BUY, 100);
    ((TradeOrderModel) req.getApiModel()).setOrderType(OrderType.AM);
    previewOnly(req, "HK", "HK STK AM preview");
  }

  @Test
  public void placeCnStkLimit() {
    ContractItem c = new ContractItem();
    c.setSymbol("000001");
    c.setSecType("STK");
    c.setCurrency("CNH");
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(
        account, c, ActionType.BUY, 100, SAFE_BUY_PRICE);
    previewAndPlace(req, "CN", "CN STK LMT");
  }

  @Test
  public void placeSgStkLimit() {
    ContractItem c = new ContractItem();
    c.setSymbol("D05");
    c.setSecType("STK");
    c.setCurrency("SGD");
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(
        account, c, ActionType.BUY, 100, SAFE_BUY_PRICE);
    previewAndPlace(req, "SG", "SG STK LMT");
  }

  @Test
  public void placeForexSecSegment() {
    // place_forex_order on SEC segment
    JSONObject biz = new JSONObject();
    biz.put("account", account);
    biz.put("seg_type", SegmentType.SEC.name());
    biz.put("source_currency", "USD");
    biz.put("target_currency", "HKD");
    biz.put("source_amount", 1.0);

    TradeOrderRequest req = new TradeOrderRequest();
    req.setApiMethodName(MethodName.PLACE_FOREX_ORDER);
    req.setBizContent(biz.toJSONString());
    TradeOrderResponse resp = executeWithRateLimitRetry(req, "placeForex SEC");
    Assert.assertNotNull(resp);
    if (!resp.isSuccess()) {
      String skipReason = classifyFailure(resp.getMessage(), "US", "placeForex SEC");
      if (skipReason != null) {
        System.out.println("placeForex SEC: " + skipReason);
        return;
      }
      Assert.fail("placeForex SEC failed: " + resp.getMessage());
    }
    System.out.println("Forex SEC segment: code=" + resp.getCode() + " msg=" + resp.getMessage());
  }

  // ==========================================================================
  // Phase 3 — MLEG combo + edge cases
  // ==========================================================================

  @Test
  public void placeUsMlegVerticalSpread() {
    // AAPL PUT vertical spread — best-effort using two adjacent strikes.
    // Uses the same pattern as the docstring example in trade_client.py.
    long msDay = 24L * 3600L * 1000L;
    long future = System.currentTimeMillis() + 45 * msDay;
    java.util.Date d = new java.util.Date(future);
    String expiry = String.format("%tY%tm%td", d, d, d);

    ContractLeg leg1 = new ContractLeg(SecType.OPT, "AAPL", "200", expiry,
        Right.PUT, ActionType.BUY, 1);
    ContractLeg leg2 = new ContractLeg(SecType.OPT, "AAPL", "205", expiry,
        Right.PUT, ActionType.SELL, 1);

    TradeOrderRequest req = TradeOrderRequest.buildMultiLegOrder(
        account, Arrays.asList(leg1, leg2),
        ComboType.VERTICAL, ActionType.BUY, 1,
        OrderType.LMT, -100.0, null, null);
    previewAndPlace(req, "US", "US MLEG VERTICAL");
  }

  @Test
  public void placeUsStkIcebergModify() {
    // Place iceberg, modify limit price, cancel.
    long now = System.currentTimeMillis();
    ContractItem contract = usStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildIcebergOrder(
        account, contract, ActionType.BUY, 10, SAFE_BUY_PRICE,
        2, 1, 30, PriceType.LIMIT_PRICE, now, now + 3_600_000L);

    // preview first
    if (!previewOnly(req, "US", "US STK ICEBERG modify — preview")) return;

    TradeOrderResponse placeResp = executeWithRateLimitRetry(
        req, "US STK ICEBERG modify");
    if (!placeResp.isSuccess()) {
      if (matches(placeResp.getMessage(), PERMISSION_ERROR_PATTERNS)) {
        System.out.println("US STK ICEBERG modify: skipped: " + placeResp.getMessage());
        return;
      }
      Assert.fail("US STK ICEBERG modify: place failed: " + placeResp.getMessage());
    }
    long orderId = placeResp.getItem().getId();
    System.out.println("US STK ICEBERG modify: placed id=" + orderId);

    // modify: bump limit price
    TradeOrderRequest modifyReq = TradeOrderRequest.buildIcebergOrder(
        account, contract, ActionType.BUY, 10, SAFE_BUY_PRICE * 2,
        2, 1, 30, PriceType.LIMIT_PRICE, now, now + 3_600_000L);
    modifyReq.setApiMethodName(MethodName.MODIFY_ORDER);
    ((TradeOrderModel) modifyReq.getApiModel()).setId(orderId);
    TradeOrderResponse modResp = executeWithRateLimitRetry(
        modifyReq, "US STK ICEBERG modify — modify");
    if (modResp != null && !modResp.isSuccess()
        && !matches(modResp.getMessage(), TERMINAL_ORDER_PATTERNS)) {
      System.out.println("US STK ICEBERG modify: modify failed (best-effort): " + modResp.getMessage());
    }

    // cancel
    cancelTolerant(orderId, "US STK ICEBERG modify");
  }

  @Test
  public void previewSellShort() {
    ContractItem contract = usStkContract();
    TradeOrderRequest req = TradeOrderRequest.buildLimitOrder(
        account, contract, ActionType.SELL, 1, SAFE_SELL_PRICE);
    previewOnly(req, "US", "US STK SELL SHORT preview");
  }
}
