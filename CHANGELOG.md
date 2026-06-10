## 2.5.0 (2026-06-08)
### New
- 新增期权提前行权接口：`OptionExerciseSubmitRequest` / `OptionExerciseCheckRequest` / `OptionExercisePageRequest` / `OptionExercisePositionRequest` / `OptionExerciseCancelRequest`
- 新增 `OptionExerciseType` 枚举（`Exercise` 提前行权 / `Expire` 提前放弃行权）
- `OptionExerciseCheckRequest` 新增 `setItmRate(Integer)` 方法，支持 Expire 类型传入价内率阈值（0-10）
