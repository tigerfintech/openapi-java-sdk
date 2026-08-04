package com.tigerbrokers.stock.openapi.client.testsupport;

/**
 * L3 集成测试：涉及写操作的真实接口。
 *
 * <p>下单、撤单、行权、划转这类会改变账户状态的操作。只手动或定时触发，不进每次 push 的
 * 门禁，已知业务限制（账户权限、地区合规、市场状态）转为受控断言而不是让流水线长期红灯。
 *
 * <p>用法：集成测试类上加 {@code @Category(WriteApi.class)}。
 * CI 用 {@code -Dgroups=...WriteApi} 选中。
 */
public interface WriteApi {
}
