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
