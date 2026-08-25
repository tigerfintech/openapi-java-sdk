package com.tigerbrokers.stock.openapi.client.testsupport;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;
import com.tigerbrokers.stock.openapi.client.https.request.TigerRequest;
import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.regex.Pattern;
import org.junit.Assume;

/**
 * Executes a request with exponential backoff on an account-level rate limit,
 * skipping the test if the limit persists.
 *
 * <p>Rate limiting is <b>account-level</b>, not per-endpoint: all 7 SDK
 * pipelines exercise the same real trading account, so {@code preview_order},
 * {@code modify_order} and {@code cancel_order} hit
 * {@code error.requestRateExceedLimit} just as readily as {@code place_order}.
 * Wiring backoff into the place path alone leaves every other mutating call to
 * surface the throttle as a hard assertion failure.
 *
 * <p>A sustained throttle is a legitimate CI-capacity boundary, not a product
 * defect, so it ends in {@link Assume} (skip) rather than a failure. Every
 * other response — success or genuine error — is returned untouched so the
 * caller can apply its own classification.
 */
public final class RateLimitRetry {

  private static final Pattern[] RATE_LIMIT_PATTERNS = new Pattern[] {
      Pattern.compile("(?i)too_many_requests"),
      Pattern.compile("(?i)rate limit"),
      Pattern.compile("(?i)requestRateExceedLimit"),
  };

  private static final int MAX_ATTEMPTS = 3;
  private static final long INITIAL_BACKOFF_MS = 1000;

  private RateLimitRetry() {
  }

  /** True if {@code msg} looks like a gateway rate-limit rejection. */
  public static boolean isRateLimited(String msg) {
    if (msg == null) return false;
    for (Pattern p : RATE_LIMIT_PATTERNS) {
      if (p.matcher(msg).find()) return true;
    }
    return false;
  }

  /**
   * Runs {@code client.execute(req)}, retrying while the gateway reports a
   * rate limit and skipping the test if it never clears.
   *
   * @param context human-readable label used in backoff/skip messages
   */
  public static <T extends TigerResponse> T execute(
      TigerHttpClient client, TigerRequest<T> req, String context) {
    long delayMs = INITIAL_BACKOFF_MS;
    T resp = null;
    for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
      resp = client.execute(req);
      if (resp == null || resp.isSuccess() || !isRateLimited(resp.getMessage())) {
        return resp;
      }
      if (attempt < MAX_ATTEMPTS - 1) {
        System.out.println(context + ": rate-limited (attempt " + (attempt + 1)
            + "/" + MAX_ATTEMPTS + "); backing off " + delayMs + "ms");
        try {
          Thread.sleep(delayMs);
        } catch (InterruptedException ignored) {
          Thread.currentThread().interrupt();
        }
        delayMs *= 2;
      }
    }
    Assume.assumeTrue(context + ": gateway rate limit persisted after "
        + MAX_ATTEMPTS + " attempts: "
        + (resp == null ? "null response" : resp.getMessage()), false);
    return resp;   // unreachable; Assume throws.
  }
}
