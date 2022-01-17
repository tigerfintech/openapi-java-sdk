## openapi-java-sdk

老虎开放平台可以为个人开发者和机构客户提供接口服务，投资者可以充分的利用老虎的交易服务、行情服务、账户服务等实现自己的投资应用程序。

#### 主要功能

* 直接管理交易：创建订单，修改或取消订单，以及检查订单状态
* 查看帐户信息：例如余额和当前头寸
* 查询行情变化：股票或期权的价格和其他信息
* 接收实时变动：获取订单变动，持仓变动以及行情变动等

#### 交易类型

* 交易支持：股票(美港股／A股)、美股期权、港股窝轮、港股牛熊证、外汇
* 行情支持：美股、港股、A股
* 订单类型：市价单、限价单、止损单、止损限价单、跟踪止损单、算法订单

#### 系统要求

* jdk >= 1.8

#### Maven地址

```xml

<dependency>
    <groupId>io.github.tigerbrokers</groupId>
    <artifactId>openapi-java-sdk</artifactId>
    <version>{latestVersion}</version>
</dependency>
```
使用maven地址时，可以查看maven仓库中依赖的上传时间，尽量使用最新版本来替换掉上面依赖中的 {latestVersion} 变量。


#### 文档地址

https://openapi.itiger.com/docs
