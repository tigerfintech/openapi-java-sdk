package com.tigerbrokers.stock.openapi.client.testsupport;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionChainItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionExpirationItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionRealTimeQuote;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionRealTimeQuoteGroup;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.MarketItem;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.RealTimeQuoteItem;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionChainQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.option.OptionExpirationQueryRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteMarketRequest;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteRealTimeQuoteRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionChainResponse;
import com.tigerbrokers.stock.openapi.client.https.response.option.OptionExpirationResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteMarketResponse;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteRealTimeQuoteResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Shared helpers for integration tests that need to gate assertions on live
 * market state or dynamically resolve tradable identifiers (option contracts,
 * fund symbols, etc.).
 *
 * <p>The core design principle: <b>never skip on missing data during trading
 * hours</b>. Skips are reserved for out-of-hours runs where empty/absent data
 * is expected.
 */
public final class MarketHelpers {

  private static final ConcurrentHashMap<String, MarketItem> MARKET_STATE_CACHE =
      new ConcurrentHashMap<>();

  private MarketHelpers() {}

  /**
   * Returns the cached {@link MarketItem} for {@code market}, fetching it via
   * {@code market_state} on first request. Returns null if the API call fails
   * or returns no data.
   */
  public static MarketItem getMarketItem(TigerHttpClient client, String market) {
    if (client == null || market == null || market.isEmpty()) {
      return null;
    }
    MarketItem cached = MARKET_STATE_CACHE.get(market);
    if (cached != null) {
      return cached;
    }
    Market m;
    try {
      m = Market.valueOf(market.toUpperCase());
    } catch (IllegalArgumentException e) {
      return null;
    }
    TigerResponse resp = client.execute(QuoteMarketRequest.newRequest(m));
    if (resp == null || !resp.isSuccess()) {
      return null;
    }
    QuoteMarketResponse qm = (QuoteMarketResponse) resp;
    if (qm.getMarketItems() == null || qm.getMarketItems().isEmpty()) {
      return null;
    }
    MarketItem item = qm.getMarketItems().get(0);
    if (item != null) {
      MARKET_STATE_CACHE.put(market, item);
    }
    return item;
  }

  /** True when the market status is exactly {@code TRADING} (regular hours). */
  public static boolean isMarketTrading(TigerHttpClient client, String market) {
    MarketItem item = getMarketItem(client, market);
    return item != null && "TRADING".equals(item.getStatus());
  }

  /**
   * True when the market is in a session that can produce live quote data:
   * {@code PRE_HOUR_TRADING}, {@code TRADING}, or {@code POST_HOUR_TRADING}.
   */
  public static boolean isMarketOpenExtended(TigerHttpClient client, String market) {
    MarketItem item = getMarketItem(client, market);
    if (item == null || item.getStatus() == null) {
      return false;
    }
    String s = item.getStatus();
    return "TRADING".equals(s)
        || "PRE_HOUR_TRADING".equals(s)
        || "POST_HOUR_TRADING".equals(s);
  }

  /**
   * Resolves a fresh, valid US option identifier for AAPL by:
   * <ol>
   *   <li>Fetching the earliest expiry via {@code option_expiration}.</li>
   *   <li>Fetching the chain for that expiry via {@code option_chain}.</li>
   *   <li>Pulling AAPL's latest price and picking the strike closest to it
   *       (ATM); prefers a call, falls back to a put.</li>
   * </ol>
   *
   * <p>If any step yields no data, falls back to the first non-empty
   * identifier in the chain. Returns null if the chain itself is empty.
   */
  public static String resolveUsOptionIdentifier(TigerHttpClient client) {
    if (client == null) {
      return null;
    }
    // 1) earliest expiry
    TigerResponse expResp = client.execute(
        OptionExpirationQueryRequest.of(Collections.singletonList("AAPL")));
    if (expResp == null || !expResp.isSuccess()) {
      return null;
    }
    OptionExpirationResponse oe = (OptionExpirationResponse) expResp;
    if (oe.getOptionExpirationItems() == null || oe.getOptionExpirationItems().isEmpty()) {
      return null;
    }
    OptionExpirationItem expItem = oe.getOptionExpirationItems().get(0);
    if (expItem.getDates() == null || expItem.getDates().isEmpty()) {
      return null;
    }
    String expiry = expItem.getDates().get(0);

    // 2) chain
    OptionChainModel chainModel = new OptionChainModel("AAPL", expiry);
    TigerResponse chainResp = client.execute(OptionChainQueryRequest.of(chainModel));
    if (chainResp == null || !chainResp.isSuccess()) {
      return null;
    }
    OptionChainResponse oc = (OptionChainResponse) chainResp;
    if (oc.getOptionChainItems() == null || oc.getOptionChainItems().isEmpty()) {
      return null;
    }
    OptionChainItem chainItem = oc.getOptionChainItems().get(0);
    List<OptionRealTimeQuoteGroup> groups = chainItem.getItems();
    if (groups == null || groups.isEmpty()) {
      return null;
    }

    // 3) pick ATM using latest underlying price when available
    Double latestPrice = getLatestPrice(client, "AAPL");
    if (latestPrice != null && latestPrice > 0) {
      OptionRealTimeQuote atm = pickAtm(groups, latestPrice);
      if (atm != null && atm.getIdentifier() != null && !atm.getIdentifier().isEmpty()) {
        return atm.getIdentifier();
      }
    }

    // fallback: first non-empty identifier
    for (OptionRealTimeQuoteGroup g : groups) {
      OptionRealTimeQuote call = g.getCall();
      if (call != null && call.getIdentifier() != null && !call.getIdentifier().isEmpty()) {
        return call.getIdentifier();
      }
      OptionRealTimeQuote put = g.getPut();
      if (put != null && put.getIdentifier() != null && !put.getIdentifier().isEmpty()) {
        return put.getIdentifier();
      }
    }
    return null;
  }

  /**
   * Resolves an option contract ID for AAPL (US), needed by the exercise
   * check/submit/cancel APIs. Returns null if a suitable option is not
   * available.
   *
   * <p>Callers wanting to actually submit an exercise must additionally
   * verify the account holds a position on the returned contract; this helper
   * does not enforce that.
   */
  public static Long resolveUsOptionContractId(TigerHttpClient client) {
    String identifier = resolveUsOptionIdentifier(client);
    if (identifier == null) {
      return null;
    }
    // Parse the option identifier (OCC-style: e.g. "AAPL  260117C00190000")
    // into symbol/expiry/right/strike, then query the contract API to get its
    // numeric contract_id.
    ParsedOption parsed = parseOccIdentifier(identifier);
    if (parsed == null) {
      return null;
    }
    com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel model =
        com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel
            .getOptionModel(parsed.symbol, parsed.expiry, parsed.strike, parsed.right);
    TigerResponse resp = client.execute(
        com.tigerbrokers.stock.openapi.client.https.request.contract.ContractRequest.newRequest(model));
    if (resp == null || !resp.isSuccess()) {
      return null;
    }
    com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse cr =
        (com.tigerbrokers.stock.openapi.client.https.response.contract.ContractResponse) resp;
    if (cr.getItem() == null || cr.getItem().getContractId() == null) {
      return null;
    }
    return cr.getItem().getContractId().longValue();
  }

  /**
   * Resolves a live, tradable AAPL (US) option contract, needed by order
   * tests that must submit a real option leg instead of a fabricated one.
   * Returns null if a suitable option is not available.
   */
  public static com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem
      resolveUsOptionContract(TigerHttpClient client) {
    String identifier = resolveUsOptionIdentifier(client);
    if (identifier == null) {
      return null;
    }
    ParsedOption parsed = parseOccIdentifier(identifier);
    if (parsed == null) {
      return null;
    }
    com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem item =
        new com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem();
    item.setSymbol(parsed.symbol);
    item.setSecType("OPT");
    item.setCurrency("USD");
    item.setExpiry(parsed.expiry.replace("-", ""));
    item.setStrike(parsed.strike);
    item.setRight(parsed.right);
    return item;
  }

  /**
   * Resolves the first available fund symbol for the given market. Returns
   * null when the fund catalog is empty for that market.
   */
  public static String resolveFundSymbol(TigerHttpClient client, String market) {
    if (client == null) {
      return null;
    }
    com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest request =
        new com.tigerbrokers.stock.openapi.client.https.request.TigerHttpRequest(
            com.tigerbrokers.stock.openapi.client.struct.enums.MethodName.FUND_ALL_SYMBOLS);
    request.setBizContent("{\"market\":\"" + market + "\"}");
    com.tigerbrokers.stock.openapi.client.https.response.TigerHttpResponse resp =
        client.execute(request);
    if (resp == null || !resp.isSuccess() || resp.getData() == null || resp.getData().isEmpty()) {
      return null;
    }
    try {
      com.alibaba.fastjson.JSONArray items = com.alibaba.fastjson.JSON.parseArray(resp.getData());
      if (items == null || items.isEmpty()) {
        return null;
      }
      Object first = items.get(0);
      if (first instanceof String) {
        return (String) first;
      }
      if (first instanceof com.alibaba.fastjson.JSONObject) {
        com.alibaba.fastjson.JSONObject obj = (com.alibaba.fastjson.JSONObject) first;
        String sym = obj.getString("symbol");
        if (sym == null) {
          sym = obj.getString("fund_symbol");
        }
        return sym;
      }
    } catch (Exception ignore) {
      // fall through
    }
    return null;
  }

  // ── internals ──────────────────────────────────────────────────────────────

  private static Double getLatestPrice(TigerHttpClient client, String symbol) {
    TigerResponse resp = client.execute(
        QuoteRealTimeQuoteRequest.newRequest(Arrays.asList(symbol)));
    if (resp == null || !resp.isSuccess()) {
      return null;
    }
    QuoteRealTimeQuoteResponse rt = (QuoteRealTimeQuoteResponse) resp;
    if (rt.getRealTimeQuoteItems() == null || rt.getRealTimeQuoteItems().isEmpty()) {
      return null;
    }
    RealTimeQuoteItem item = rt.getRealTimeQuoteItems().get(0);
    return item.getLatestPrice();
  }

  private static OptionRealTimeQuote pickAtm(
      List<OptionRealTimeQuoteGroup> groups, double underlyingPrice) {
    OptionRealTimeQuote bestCall = null;
    OptionRealTimeQuote bestPut = null;
    double bestCallDist = Double.MAX_VALUE;
    double bestPutDist = Double.MAX_VALUE;
    for (OptionRealTimeQuoteGroup g : groups) {
      OptionRealTimeQuote call = g.getCall();
      if (call != null && call.getIdentifier() != null && !call.getIdentifier().isEmpty()
          && call.getStrike() != null && !call.getStrike().isEmpty()) {
        try {
          double strike = Double.parseDouble(call.getStrike());
          double dist = Math.abs(strike - underlyingPrice);
          if (dist < bestCallDist) {
            bestCallDist = dist;
            bestCall = call;
          }
        } catch (NumberFormatException ignore) {
          // skip malformed strike
        }
      }
      OptionRealTimeQuote put = g.getPut();
      if (put != null && put.getIdentifier() != null && !put.getIdentifier().isEmpty()
          && put.getStrike() != null && !put.getStrike().isEmpty()) {
        try {
          double strike = Double.parseDouble(put.getStrike());
          double dist = Math.abs(strike - underlyingPrice);
          if (dist < bestPutDist) {
            bestPutDist = dist;
            bestPut = put;
          }
        } catch (NumberFormatException ignore) {
          // skip malformed strike
        }
      }
    }
    return bestCall != null ? bestCall : bestPut;
  }

  /** Parses an OCC-style option identifier like "AAPL  260117C00190000". */
  static ParsedOption parseOccIdentifier(String identifier) {
    if (identifier == null || identifier.length() < 15) {
      return null;
    }
    // Symbol is the leading letters (up to 6 chars, right-padded with spaces).
    int i = 0;
    while (i < identifier.length() && Character.isLetter(identifier.charAt(i))) {
      i++;
    }
    if (i == 0) {
      return null;
    }
    String symbol = identifier.substring(0, i);
    // Skip padding whitespace.
    while (i < identifier.length() && identifier.charAt(i) == ' ') {
      i++;
    }
    // Remaining must be 15 chars: YYMMDD[C|P]XXXXXXXX (strike * 1000).
    if (i + 15 > identifier.length()) {
      return null;
    }
    String tail = identifier.substring(i, i + 15);
    String yy = tail.substring(0, 2);
    String mm = tail.substring(2, 4);
    String dd = tail.substring(4, 6);
    char rightChar = tail.charAt(6);
    String strikeStr = tail.substring(7, 15);
    String expiry = "20" + yy + "-" + mm + "-" + dd;
    String right = rightChar == 'C' ? "CALL" : (rightChar == 'P' ? "PUT" : null);
    if (right == null) {
      return null;
    }
    try {
      double strike = Long.parseLong(strikeStr) / 1000.0;
      return new ParsedOption(symbol, expiry, strike, right);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  static final class ParsedOption {
    final String symbol;
    final String expiry;
    final double strike;
    final String right;

    ParsedOption(String symbol, String expiry, double strike, String right) {
      this.symbol = symbol;
      this.expiry = expiry;
      this.strike = strike;
      this.right = right;
    }
  }
}
