<h1 align="center">TigerOpen Java SDK</h1>

<p align="center">
  <a href="https://search.maven.org/artifact/io.github.tigerbrokers/openapi-java-sdk"><img src="https://img.shields.io/maven-central/v/io.github.tigerbrokers/openapi-java-sdk.svg" alt="Maven Central"></a>
  <a href="https://github.com/tigerfintech/openapi-java-sdk/blob/master/LICENSE"><img src="https://img.shields.io/badge/license-MIT-blue.svg" alt="License"></a>
  <img src="https://img.shields.io/badge/Java-8%2B-orange.svg" alt="Java">
</p>

<p align="center">
  老虎证券 OpenAPI Java SDK — 行情、资讯、交易、账户、推送一站式接入
  <br>
  Tiger Brokers OpenAPI Java SDK — Market data, news, trading, account & push in one package
</p>

<p align="center">
  <a href="#中文文档">中文</a> | <a href="#english-documentation">English</a>
</p>

---

# 中文文档

## 目录

- [简介](#简介)
- [支持市场](#支持市场)
- [安装](#安装)
- [快速开始](#快速开始)
- [示例代码](#示例代码)
- [文档与支持](#文档与支持)

## 简介

TigerOpen Java SDK 是老虎证券开放平台的官方 Java SDK，为个人开发者和机构客户提供完整的证券交易接口服务：

- **行情数据** — 股票/期权/期货实时行情、K 线、逐笔成交、盘口深度
- **资讯数据** — 个股新闻、市场要闻列表，支持分页与游标翻页
- **交易服务** — 下单、改单、撤单，支持市价/限价/止损/跟踪止损/算法订单 (TWAP/VWAP)
- **账户管理** — 资产查询、持仓管理、成交记录
- **实时推送** — WebSocket 行情推送、订单状态、持仓与资产变动

> 开通老虎证券账户并入金后即可免费使用 OpenAPI。

## 支持市场

| 市场 | 股票/ETF | 期权 | 期货 | 窝轮/牛熊证 |
|------|:--------:|:----:|:----:|:----------:|
| 美国 | ✅ | ✅ | ✅ | — |
| 香港 | ✅ | ✅ | ✅ | ✅ |
| 新加坡 | ✅ | — | — | — |
| 澳大利亚 | ✅ | ✅ | — | — |

## 安装

**Java 8+**

### Maven（推荐）

在 `pom.xml` 中添加依赖：

```xml
<dependency>
    <groupId>io.github.tigerbrokers</groupId>
    <artifactId>openapi-java-sdk</artifactId>
    <version>2.5.0</version>
</dependency>
```

如无法下载，可添加仓库源：

```xml
<repositories>
  <repository>
    <id>sonatype-public</id>
    <name>sonatype-public</name>
    <url>https://oss.sonatype.org/content/groups/public/</url>
  </repository>
</repositories>
```

### Gradle

```groovy
dependencies {
    implementation 'io.github.tigerbrokers:openapi-java-sdk:2.5.0'
}
```

### 从源码构建

```bash
git clone https://github.com/tigerfintech/openapi-java-sdk.git
cd openapi-java-sdk
mvn clean install
```

## 快速开始

### 1. 注册开发者

前往 [开发者信息页](https://developer.itigerup.com/profile) 注册并获取：
- `tiger_id` — 开发者 ID
- `private_key` — RSA 私钥（**Java SDK 仅支持 PKCS#8 格式**，即 `BEGIN PRIVATE KEY`）
- `account` — 交易账户号

机构用户请访问 [机构账户中心](https://docs.itigerup.com/docs/contact#institution_link) 完成开通流程，还需获取 `secret_key`。

### 2. 配置

下载 `tiger_openapi_config.properties` 并放到本地目录：

```properties
private_key_pk8=your_pkcs8_private_key
tiger_id=your_tiger_id
account=your_account
license=TBHK
env=PROD
# 机构用户额外配置
# secret_key=your_secret_key
```

**代码初始化：**

```java
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;

public class TigerClientConfig {
    public static ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
    public static TigerHttpClient client;

    static {
        // 配置文件所在目录
        clientConfig.configFilePath = "/path/to/config/dir";
        // clientConfig.secretKey = "your_secret_key"; // 机构账号必填
        client = TigerHttpClient.getInstance().clientConfig(clientConfig);
    }
}
```

### 3. 查询行情

```java
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import java.util.Arrays;

QuoteKlineRequest request = QuoteKlineRequest
    .newRequest(Arrays.asList("AAPL"), KType.day, "2024-01-01", "2024-12-31")
    .withLimit(100);

QuoteKlineResponse response = client.execute(request);
if (response.isSuccess()) {
    System.out.println(response.getKlineItems());
} else {
    System.out.println("Error: " + response.getMessage());
}
```

### 逐笔成交 `cond` 字段说明

`trade_tick` 接口和 push 层返回的 `cond` 字段已由 SDK 转换为可读字符串，含义如下：

**美股（US）**

| 值 | 含义 |
|----|------|
| `US_REGULAR_SALE` | 常规交易（Regular Sale） |
| `US_BUNCHED_TRADE` | 批量交易（Bunched Trade） |
| `US_CASH_TRADE` | 现金交易（Cash Trade） |
| `US_INTERMARKET_SWEEP` | 跨市场交易（Intermarket Sweep） |
| `US_BUNCHED_SOLD_TRADE` | 批量卖出（Bunched Sold Trade） |
| `US_PRICE_VARIATION_TRADE` | 离价交易（Price Variation Trade） |
| `US_ODD_LOT_TRADE` | 碎股交易（Odd Lot Trade） |
| `US_RULE_127_OR_155_TRADE` | 纽交所第 127/155 条交易 |
| `US_SOLD_LAST` | 延迟交易（Sold Last） |
| `US_MARKET_CENTER_CLOSE_PRICE` | 中央收市价（Market Center Close Price） |
| `US_NEXT_DAY_TRADE` | 隔日交易（Next Day Trade） |
| `US_MARKET_CENTER_OPENING_TRADE` | 中央开盘价交易（Market Center Opening Trade） |
| `US_PRIOR_REFERENCE_PRICE` | 前参考价（Prior Reference Price） |
| `US_MARKET_CENTER_OPEN_PRICE` | 中央开盘价（Market Center Open Price） |
| `US_SELLER` | 卖方（Seller） |
| `US_FORM_T` | 盘前盘后交易（Form T） |
| `US_EXTENDED_TRADING_HOURS` | 延长交易时段（Extended Trading Hours） |
| `US_CONTINGENT_TRADE` | 合单交易（Contingent Trade） |
| `US_AVERAGE_PRICE_TRADE` | 均价交易（Average Price Trade） |
| `US_CROSS_TRADE` | 跨市场交易（Cross Trade） |
| `US_SOLD_OUT_OF_SEQUENCE` | 场外售出（Sold Out of Sequence） |
| `US_DERIVATIVELY_PRICED` | 衍生工具定价（Derivatively Priced） |
| `US_QUALIFIED_CONTINGENT_TRADE` | 合单交易（Qualified Contingent Trade） |

**港股（HK）**

| 值 | 含义 |
|----|------|
| `HK_AUTOMATCH_NORMAL` | 自动对盘（Automatch Normal） |
| `HK_ODD_LOT_TRADE` | 碎股交易（Odd Lot Trade） |
| `HK_AUCTION_TRADE` | 竞价交易（Auction Trade） |
| `HK_OVERSEAS_TRADE` | 场外交易（Overseas Trade） |
| `HK_LATE_TRADE_OFF_EXCHG` | 开市前成交（Late Trade Off Exchange） |
| `HK_NON_DIRECT_OFF_EXCHG_TRADE` | 非自动对盘（Non-Direct Off Exchange Trade） |
| `HK_DIRECT_OFF_EXCHG_TRADE` | 同券商自动对盘（Direct Off Exchange Trade） |
| `HK_AUTOMATIC_INTERNALIZED` | 同券商非自动对盘（Automatic Internalized） |

### 4. 下单交易

```java
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;

ContractItem contract = ContractItem.buildStockContract("AAPL", "USD");
TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, ActionType.BUY, 1, 150.0);

TradeOrderResponse response = client.execute(request);
System.out.println(response.isSuccess() ? "下单成功" : "下单失败: " + response.getMessage());
```

### 5. 实时推送

实现 `ApiComposeCallback` 接口定义回调：

```java
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketClient;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import java.util.HashSet;

// 实现回调接口
ApiComposeCallback callback = new DefaultApiComposeCallback() {
    @Override
    public void quoteChange(QuoteBasicData data) {
        System.out.println("行情变动: " + data.getSymbol() + " " + data.getLatestPrice());
    }
};

ClientConfig config = ClientConfig.DEFAULT_CONFIG;
config.configFilePath = "/path/to/config/dir";

WebSocketClient wsClient = WebSocketClient.getInstance()
    .clientConfig(config)
    .apiComposeCallback(callback);

wsClient.connect();

// 订阅股票行情
HashSet<String> symbols = new HashSet<>();
symbols.add("AAPL");
symbols.add("TSLA");
wsClient.subscribeQuote(symbols);
```

### 6. 查询资讯

SDK 提供两个资讯查询接口，均为 HTTP 请求：

| 接口 | method | 说明 |
|------|--------|------|
| 个股新闻 | `news_stock_list` | 查询单只股票的资讯列表，按 `pageCount` / `pageSize` 翻页 |
| 市场要闻 | `news_highlight_list` | 查询市场要闻列表，按 `lastId` 游标翻页 |

#### 个股新闻

```java
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsItem;
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsPageItem;
import com.tigerbrokers.stock.openapi.client.https.request.news.NewsStockListRequest;
import com.tigerbrokers.stock.openapi.client.https.response.news.NewsStockListResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

NewsStockListRequest request =
    NewsStockListRequest.newRequest("AAPL", Language.zh_CN, 1, 20);

NewsStockListResponse response = client.execute(request);
if (response.isSuccess()) {
    NewsPageItem page = response.getNewsPageItem();
    for (NewsItem item : page.getItems()) {
        System.out.println(item.getTitle() + " — " + item.getUrl());
    }
} else {
    System.out.println("Error: " + response.getMessage());
}
```

参数说明：

| 参数 | 必填 | 说明 |
|------|:----:|------|
| `symbol` | 是 | 股票代码，**仅支持单只**（该接口不支持批量查询） |
| `lang` | 否 | `zh_CN` / `zh_TW` / `en_US`；传 `null` 返回中英混合内容，非法值回退 `en_US` |
| `pageCount` | 否 | 页码，默认 1，取值 >= 1 |
| `pageSize` | 否 | 每页条数，默认 20，取值 1–50 |

#### 市场要闻

```java
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsItem;
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsPageItem;
import com.tigerbrokers.stock.openapi.client.https.request.news.NewsHighlightListRequest;
import com.tigerbrokers.stock.openapi.client.https.response.news.NewsHighlightListResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

NewsHighlightListRequest request = NewsHighlightListRequest.newRequest(Language.zh_CN, null);

NewsHighlightListResponse response = client.execute(request);
if (response.isSuccess()) {
    NewsPageItem page = response.getNewsPageItem();
    // 下一页：把本页最后一条的 id 作为 lastId 传回
    String nextLastId = page.getItems().isEmpty()
        ? null
        : page.getItems().get(page.getItems().size() - 1).getId();
}
```

参数说明：

| 参数 | 必填 | 说明 |
|------|:----:|------|
| `lang` | 否 | 同上 |
| `lastId` | 否 | 游标。传 `null` 从最新开始；下一页传本页 `items` 最后一条的 `id` |

#### 响应结构

两个接口的响应都通过 `getNewsPageItem()` 取回 `NewsPageItem`：

| 字段 | 类型 | 说明 |
|------|------|------|
| `items` | `List<NewsItem>` | 当前页资讯列表 |
| `pageCount` | Integer | 当前页码 |
| `pageSize` | Integer | 每页条数 |
| `totalPage` | Integer | 总页数 |
| `totalSize` | Integer | 总条数 |

`NewsItem`：

| 字段 | 类型 | 说明 |
|------|------|------|
| `id` | String | 资讯 ID |
| `title` | String | 标题 |
| `summary` | String | 摘要 |
| `pubTimestamp` | Long | 发布时间戳（秒级） |
| `url` | String | 原文链接 |

## 示例代码

更多示例代码请参考 [src/test/java/](src/test/java/) 目录。

## 文档与支持

- [官方 API 文档](https://docs.itigerup.com/docs/prepare-java)
- [开发者信息页](https://developer.itigerup.com/profile)
- [GitHub Issues](https://github.com/tigerfintech/openapi-java-sdk/issues)
- 老虎量化 QQ 群：869893807（团队或公司客户请联系群主）

## License

[MIT License](LICENSE)

---

# English Documentation

## Table of Contents

- [Introduction](#introduction)
- [Supported Markets](#supported-markets)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Examples](#examples)
- [Documentation & Support](#documentation--support)

## Introduction

TigerOpen Java SDK is the official Java SDK for Tiger Brokers' Open Platform, providing developers and institutional clients with comprehensive securities trading interfaces:

- **Market Data** — Real-time quotes, candlesticks, tick data, order book depth for stocks, options & futures
- **News** — Per-symbol news and market highlights with page and cursor pagination
- **Trading** — Place, modify, cancel orders; supports market/limit/stop/trailing-stop/algo orders (TWAP/VWAP)
- **Account Management** — Asset queries, position tracking, transaction history
- **Real-time Push** — WebSocket streaming for quotes, order status, position & asset changes

> OpenAPI is free to use after opening and funding a Tiger Brokers account.

## Supported Markets

| Market | Stocks/ETFs | Options | Futures | Warrants/CBBCs |
|--------|:-----------:|:-------:|:-------:|:--------------:|
| US | ✅ | ✅ | ✅ | — |
| Hong Kong | ✅ | ✅ | ✅ | ✅ |
| Singapore | ✅ | — | — | — |
| Australia | ✅ | ✅ | — | — |

## Installation

**Java 8+**

### Maven (Recommended)

Add to `pom.xml`:

```xml
<dependency>
    <groupId>io.github.tigerbrokers</groupId>
    <artifactId>openapi-java-sdk</artifactId>
    <version>2.5.0</version>
</dependency>
```

### Gradle

```groovy
dependencies {
    implementation 'io.github.tigerbrokers:openapi-java-sdk:2.5.0'
}
```

### Build from Source

```bash
git clone https://github.com/tigerfintech/openapi-java-sdk.git
cd openapi-java-sdk
mvn clean install
```

## Quick Start

### 1. Register as a Developer

Go to the [Developer Portal](https://developer.itigerup.com/profile) to obtain:
- `tiger_id` — Developer ID
- `private_key` — RSA private key (**Java SDK requires PKCS#8 format**, i.e. `BEGIN PRIVATE KEY`)
- `account` — Trading account number

Institutional users: visit the [Institution Center](https://docs.itigerup.com/docs/contact#institution_link) to complete onboarding and obtain a `secret_key`.

### 2. Configuration

Download `tiger_openapi_config.properties` and place it in a local directory:

```properties
private_key_pk8=your_pkcs8_private_key
tiger_id=your_tiger_id
account=your_account
license=TBHK
env=PROD
# Institutional users only
# secret_key=your_secret_key
```

**Initialize the client:**

```java
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;

public class TigerClientConfig {
    public static ClientConfig clientConfig = ClientConfig.DEFAULT_CONFIG;
    public static TigerHttpClient client;

    static {
        clientConfig.configFilePath = "/path/to/config/dir";
        // clientConfig.secretKey = "your_secret_key"; // required for institutional accounts
        client = TigerHttpClient.getInstance().clientConfig(clientConfig);
    }
}
```

### 3. Query Market Data

```java
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import java.util.Arrays;

QuoteKlineRequest request = QuoteKlineRequest
    .newRequest(Arrays.asList("AAPL"), KType.day, "2024-01-01", "2024-12-31")
    .withLimit(100);

QuoteKlineResponse response = client.execute(request);
if (response.isSuccess()) {
    System.out.println(response.getKlineItems());
} else {
    System.out.println("Error: " + response.getMessage());
}
```

### 4. Place Orders

```java
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.ContractItem;
import com.tigerbrokers.stock.openapi.client.https.request.trade.TradeOrderRequest;
import com.tigerbrokers.stock.openapi.client.https.response.trade.TradeOrderResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;

ContractItem contract = ContractItem.buildStockContract("AAPL", "USD");
TradeOrderRequest request = TradeOrderRequest.buildLimitOrder(contract, ActionType.BUY, 1, 150.0);

TradeOrderResponse response = client.execute(request);
System.out.println(response.isSuccess() ? "Order placed" : "Failed: " + response.getMessage());
```

### 5. Real-time Push

Implement the `ApiComposeCallback` interface to handle events:

```java
import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.socket.WebSocketClient;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
import java.util.HashSet;

ApiComposeCallback callback = new DefaultApiComposeCallback() {
    @Override
    public void quoteChange(QuoteBasicData data) {
        System.out.println("Quote: " + data.getSymbol() + " @ " + data.getLatestPrice());
    }
};

ClientConfig config = ClientConfig.DEFAULT_CONFIG;
config.configFilePath = "/path/to/config/dir";

WebSocketClient wsClient = WebSocketClient.getInstance()
    .clientConfig(config)
    .apiComposeCallback(callback);

wsClient.connect();

HashSet<String> symbols = new HashSet<>();
symbols.add("AAPL");
symbols.add("TSLA");
wsClient.subscribeQuote(symbols);
```

### 6. Query News

The SDK exposes two news query APIs over HTTP:

| API | method | Description |
|-----|--------|-------------|
| Stock news | `news_stock_list` | News list for a single symbol, paged by `pageCount` / `pageSize` |
| Market highlights | `news_highlight_list` | Market highlights, paged by the `lastId` cursor |

#### Stock News

```java
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsItem;
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsPageItem;
import com.tigerbrokers.stock.openapi.client.https.request.news.NewsStockListRequest;
import com.tigerbrokers.stock.openapi.client.https.response.news.NewsStockListResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

NewsStockListRequest request =
    NewsStockListRequest.newRequest("AAPL", Language.en_US, 1, 20);

NewsStockListResponse response = client.execute(request);
if (response.isSuccess()) {
    NewsPageItem page = response.getNewsPageItem();
    for (NewsItem item : page.getItems()) {
        System.out.println(item.getTitle() + " — " + item.getUrl());
    }
} else {
    System.out.println("Error: " + response.getMessage());
}
```

Parameters:

| Parameter | Required | Description |
|-----------|:--------:|-------------|
| `symbol` | Yes | Stock symbol. **Single symbol only** (batch queries are not supported) |
| `lang` | No | `zh_CN` / `zh_TW` / `en_US`; `null` returns mixed Chinese/English content, invalid values fall back to `en_US` |
| `pageCount` | No | Page number, default 1, must be >= 1 |
| `pageSize` | No | Page size, default 20, range 1–50 |

#### Market Highlights

```java
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsItem;
import com.tigerbrokers.stock.openapi.client.https.domain.news.item.NewsPageItem;
import com.tigerbrokers.stock.openapi.client.https.request.news.NewsHighlightListRequest;
import com.tigerbrokers.stock.openapi.client.https.response.news.NewsHighlightListResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.Language;

NewsHighlightListRequest request = NewsHighlightListRequest.newRequest(Language.en_US, null);

NewsHighlightListResponse response = client.execute(request);
if (response.isSuccess()) {
    NewsPageItem page = response.getNewsPageItem();
    // Next page: pass the id of the last item as lastId
    String nextLastId = page.getItems().isEmpty()
        ? null
        : page.getItems().get(page.getItems().size() - 1).getId();
}
```

Parameters:

| Parameter | Required | Description |
|-----------|:--------:|-------------|
| `lang` | No | Same as above |
| `lastId` | No | Cursor. `null` starts from the newest; pass the `id` of the last item of the current page to get the next page |

#### Response Structure

Both APIs return a `NewsPageItem` via `getNewsPageItem()`:

| Field | Type | Description |
|-------|------|-------------|
| `items` | `List<NewsItem>` | News items on the current page |
| `pageCount` | Integer | Current page number |
| `pageSize` | Integer | Page size |
| `totalPage` | Integer | Total number of pages |
| `totalSize` | Integer | Total number of items |

`NewsItem`:

| Field | Type | Description |
|-------|------|-------------|
| `id` | String | News ID |
| `title` | String | Title |
| `summary` | String | Summary |
| `pubTimestamp` | Long | Publish timestamp in seconds |
| `url` | String | Link to the original article |

## Examples

More examples are available in the [src/test/java/](src/test/java/) directory.

## Documentation & Support

- [Official API Documentation](https://docs.itigerup.com/docs/prepare-java)
- [Developer Portal](https://developer.itigerup.com/profile)
- [GitHub Issues](https://github.com/tigerfintech/openapi-java-sdk/issues)
- Tiger Quant QQ Group: 869893807

## License

[MIT License](LICENSE)
