## 2.6.0 (2026-07-23)
### New
- `CorporateActionType` 新增：`SYMBOL_CHANGE`、`DELISTING`、`IPO`
- 新增 `CorporateSymbolChangeRequest/Response/Item`、`CorporateDelistingRequest/Response/Item`、`CorporateIpoRequest/Response/Item`

## 2.5.1 (2026-06-24)
### New
- 新增冰山单构建方法 `TradeOrderModel.buildIcebergOrder(account, contract, action, quantity, price, displaySize)` 及完整参数重载，支持 `minDisplaySize`、`checkIntervals`、`priceType`（`LIMIT_PRICE`/`OPPONENT_PRICE`）、`startTime`/`endTime`（epoch ms）
- 新增 `IcebergPriceType` 枚举（`LIMIT_PRICE`、`OPPONENT_PRICE`）
- 新增冰山单集成测试类 `IcebergOrderIntegrationTest`（`@Ignore`，需真实账户配置，覆盖下单/预览/查询/撤单全流程）

## 2.5.0 (2026-06-08)
### New
- 新增期权提前行权接口：`OptionExerciseSubmitRequest` / `OptionExerciseCheckRequest` / `OptionExercisePageRequest` / `OptionExercisePositionRequest` / `OptionExerciseCancelRequest`
- 新增 `OptionExerciseType` 枚举（`Exercise` 提前行权 / `Expire` 提前放弃行权）
- `OptionExerciseCheckRequest` 新增 `setItmRate(Integer)` 方法，支持 Expire 类型传入价内率阈值（0-10）
