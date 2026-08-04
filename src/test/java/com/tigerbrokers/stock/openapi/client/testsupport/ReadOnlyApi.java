package com.tigerbrokers.stock.openapi.client.testsupport;

/**
 * L2 契约测试：只读真实接口。
 *
 * <p>断言字段存在性、类型和错误码，不断言随账户或市场变化的具体数值。行为幂等、可重复，
 * 所以每次 push 都跑，红了阻断。
 *
 * <p>用法：集成测试类上加 {@code @Category(ReadOnlyApi.class)}。
 * CI 用 {@code -Dgroups=...ReadOnlyApi} 选中。
 */
public interface ReadOnlyApi {
}
