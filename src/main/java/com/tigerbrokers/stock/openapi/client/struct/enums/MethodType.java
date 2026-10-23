package com.tigerbrokers.stock.openapi.client.struct.enums;

/**
 * method 类型，决定请求路由与默认参数注入（TigerHttpClient）：
 * TRADE → 主网关（模拟账户自动路由 paper 域名），自动补 defaultAccount / secretKey；
 * QUOTE → 行情域名（域名花园按 license 分流，如 TBNZ-QUOTE /hkg-quote）；
 * OTHER → 主网关（user 域）；
 * NEWS  → 主网关（资讯域，不走行情集群分流）。
 * Created by bean on 2022/09/19.
 */
public enum MethodType {
  TRADE, QUOTE, OTHER, NEWS;
}
