# Changelog

## 2.7.4 (2026-11-18)

### New

- Contract responses expose supported combinations of order types, time in force, and trading sessions through order capabilities.

## 2.7.3 (2026-11-09)

### New

- US option snapshots and option chains provide GTH/CURB extended-session quotes and independent cumulative volume and turnover.
- Protobuf option callbacks provide current-session prices, trade timestamps, reference previous closes, volume, and turnover, with `PreMarket` / `AfterHours` session tags and independent cumulative totals.
- Option minute-bar requests support `withHourTrading` to include or exclude GTH/CURB data.

### Changed

- Existing method signatures and server defaults remain unchanged when no extended-session option is set.
- Quote callbacks preserve missing optional values and explicit zeros, including fractional volume.
- Extended-session option callbacks require Protobuf; HTTP snapshots and option chains expose extended-session data through `hourTrading`.

## 2.7.2 (2026-11-02)

### New

- `NewsStockListRequest` queries news for one stock with page-number pagination.
- `NewsHighlightListRequest` queries market highlights with cursor pagination.
- News queries support simplified Chinese, traditional Chinese, and English. Omitting the language returns mixed-language content; unsupported language values fall back to English.
- News responses provide pagination details, article titles, summaries, publication timestamps, and URLs.

## 2.7.1 (2026-09-23)

### New

- Asset queries support `AssetQuoteType` values `ETH`, `RTH`, and `OVERNIGHT` for session-specific valuations.
- Asset and position subscriptions support the same quote basis through a new `SubscribeAsyncApi.subscribe` overload.

### Changed

- Existing method signatures and behavior remain unchanged when `AssetQuoteType` is omitted.
- Subscription quote-basis selection requires Protobuf. STOMP callbacks ignore the parameter and log a warning.

## 2.7.0 (2026-09-17)

### New

- OAuth2 authentication supports automatic token storage and renewal through `OAuth2SessionManager` and `OAuth2Authentication`.
- Push connections renew OAuth2 tokens without reconnecting.

### Changed

- Existing tigerId and private-key authentication remains available when no alternative authentication is configured.

## 2.6.3 (2026-08-27)

### New

- Real-time stock and cryptocurrency quotes provide turnover through `RealTimeQuoteItem.amount`.

## 2.6.2 (2026-08-19)

### New

- Option quotes provide current and previous mark prices, midpoint prices, and their timestamps.
- Overnight quotes provide trading status.

## 2.6.1 (2026-08-03)

### Fixed

- Option analysis now honors `requireVolatilityList` and returns the requested volatility data.
- Option timelines now honor `beginTime` when filtering results.

## 2.6.0 (2026-07-23)

### New

- Corporate-action queries support symbol changes, delistings, and IPOs.
- `CorporateActionType` includes `SYMBOL_CHANGE`, `DELISTING`, and `IPO`.

## 2.5.1 (2026-06-24)

### New

- `TradeOrderModel.buildIcebergOrder` creates iceberg orders with minimum display size, check intervals, and price type.
- `IcebergPriceType` supports `LIMIT_PRICE` and `OPPONENT_PRICE`.

## 2.5.0 (2026-06-08)

### New

- Option-exercise APIs support checking eligible positions, submitting requests, querying records, and cancelling requests.
- `OptionExerciseType` supports early exercise and lapse requests.
- `OptionExerciseCheckRequest.setItmRate` accepts in-the-money thresholds from 0 to 10 for lapse requests.
