## 2.8.0 (2026-10-22)
### New
- 新增个股新闻列表接口 `news_stock_list`：`NewsStockListRequest.newRequest(List<String> symbols, Language lang, Integer pageCount, Integer pageSize)` 查询指定股票（1-20 个代码）的资讯列表；`page_count` / `page_size` 可选（传 `null` = 服务端默认 1 / 20，`page_count` >= 1，`page_size` 1-50）
- 新增市场要闻列表接口 `news_highlight_list`：`NewsHighlightListRequest.newRequest(Language lang, String lastId)` 查询市场要闻，无必填参数；翻页只走 `lastId` 游标（取上一页最后一条的 `id`，传 `null` 从最新开始）
- `lang` 为两接口共用可选参数（`zh_CN` / `zh_TW` / `en_US`，传 `null` = 中英混合内容；服务端对非法值回退 `en_US`）
- 两接口响应共用 `NewsPageItem`（`items` + `pageCount` / `pageSize` / `totalPage` / `totalSize`），条目 `NewsItem` 含 `id` / `title` / `summary` / `pubTimestamp` / `url`
### 兼容性
- 纯新增接口，不影响既有方法
- 出入参与平台惯例对齐：请求 snake_case、响应 camelCase，无特殊映射

## 2.7.1 (2026-09-23)
### New
- 资产查询新增 `AssetQuoteType` 参数（`ETH` / `RTH` / `OVERNIGHT`），可按夜盘口径估值：`PrimeAssetRequest.buildPrimeAssetRequest(...)` 与 `AggregateAssetRequest.buildAggregateAssetRequest(...)` 各新增带该参数的重载，`PrimeAssetModel` / `AggregateAssetModel` 新增对应字段
- 推送订阅新增资产行情口径：`SubscribeAsyncApi.subscribe(String account, Subject subject, AssetQuoteType assetQuoteType)`，仅对 `Subject.Asset` / `Subject.Position` 生效
### 兼容性
- 原有方法签名不变，不传 `AssetQuoteType`（或传 `null`）时行为与此前完全一致
- 订阅侧的口径参数只随 protobuf 协议上线；使用 `ApiComposeCallback4Stomp`（STOMP 协议）时该参数会被忽略并打印 warn 日志

## 2.7.0 (2026-09-17)
### New
- 新增 OAuth2 授权方式，可替代 tigerId + 私钥签名：`OAuth2SessionManager.builder()` 完成授权并管理令牌，`clientConfig.authentication = new OAuth2Authentication(sessions)` 接入客户端。令牌保存在 `~/.tiger/openapi/`，过期自动续期，代码与配置中都不再需要私钥
- 推送支持 OAuth2，令牌临近过期时在连接上直接续期，不断开重连
### 兼容性
- 已有的 tigerId + 私钥签名方式不受影响；不设置 `ClientConfig.authentication` 即沿用签名方式

## 2.6.3 (2026-08-27)
### New
- `RealTimeQuoteItem` 新增 `amount` 字段，支持股票和数字货币实时行情成交额。

## 2.6.2 (2026-08-19)
### New
- `OptionRealTimeQuote` 新增 `markPrice`、`preMarkPrice`、`markTimestamp`、`midPrice`、`preMidPrice`、`midTimestamp` 字段
- `QuoteOvernight` 新增 `tradingStatus` 字段

## 2.6.1 (2026-08-03)

### Fixed

- 修复期权分析接口 `requireVolatilityList` 参数未生效的问题，此前请求该参数会被服务端忽略，无法返回波动率列表数据
- 修复期权分时接口 `beginTime` 参数未生效的问题，此前设置起始时间不会过滤返回结果

## 2.6.0 (2026-07-23)
### New
- `CorporateActionType` 新增：`SYMBOL_CHANGE`、`DELISTING`、`IPO`
- 新增公司行为 / 股票代码变更查询接口

## 2.5.1 (2026-06-24)
### New
- 新增冰山单构建方法 `TradeOrderModel.buildIcebergOrder`，支持设置最小展示数量、检查间隔和价格类型
- 新增 `IcebergPriceType` 枚举（`LIMIT_PRICE`、`OPPONENT_PRICE`）

## 2.5.0 (2026-06-08)
### New
- 新增期权提前行权相关接口，支持检查可行权持仓、提交行权、分页查询记录和取消行权
- 新增 `OptionExerciseType` 枚举（`Exercise` 提前行权 / `Expire` 提前放弃行权）
- `OptionExerciseCheckRequest` 新增 `setItmRate(Integer)` 方法，支持 Expire 类型传入价内率阈值（0-10）
