# CLAUDE.md - TigerOpen Java SDK

## 项目概述

老虎证券 OpenAPI 官方 Java SDK，为个人开发者和机构客户提供交易、行情、账户接口服务。

## 技术栈

- Java 8+
- Maven 构建
- WebSocket（实时推送）
- HTTP REST API

## 目录结构

```
openapi-java-sdk/
├── src/
│   └── main/java/com/tigerbrokers/stock/openapi/
│       └── client/           # 客户端实现
├── pom.xml                   # Maven 配置
└── README.md
```

## 主要功能

| 功能 | 说明 |
|------|------|
| 交易管理 | 创建、修改、取消订单，查询订单状态 |
| 账户信息 | 余额查询、持仓管理 |
| 行情查询 | 股票、期权、期货价格和信息 |
| 实时推送 | 订单变动、持仓变动、行情变动 |

## 支持的交易类型

- **交易**：股票（美港股/A股）、美股期权、港股窝轮、港股牛熊证、外汇
- **行情**：美股、港股、A股
- **订单**：市价单、限价单、止损单、止损限价单、跟踪止损单、算法订单

## 安装

```xml
<dependency>
    <groupId>io.github.tigerbrokers</groupId>
    <artifactId>openapi-java-sdk</artifactId>
    <version>LATEST</version>
</dependency>
```

## 构建

```bash
mvn clean install
```

## 文档

- API 文档：https://quant.itigerup.com/openapi/zh/java/overview/introduction.html
- 开发者平台：https://developer.itigerup.com/
