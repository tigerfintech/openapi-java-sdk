package com.tigerbrokers.stock.openapi.client.util.builder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.enums.AssetQuoteType;
import org.junit.Assert;
import org.junit.Test;

/**
 * AccountParamBuilder 的参数名契约测试。
 * positions / assets 两个接口都通过本 builder 传 asset_quote_type，参数名和取值必须和服务端一致。
 */
public class AccountParamBuilderTest {

  @Test
  public void assetQuoteTypeUsesSnakeCaseKeyAndEnumName() {
    JSONObject json = JSON.parseObject(AccountParamBuilder.instance()
        .account("testAccount")
        .assetQuoteType(AssetQuoteType.OVERNIGHT)
        .buildJsonWithoutDefaultAccount());
    // 下划线参数名，值是枚举名而不是 ordinal
    Assert.assertEquals("OVERNIGHT", json.getString("asset_quote_type"));

    Assert.assertEquals("ETH", JSON.parseObject(AccountParamBuilder.instance()
        .assetQuoteType(AssetQuoteType.ETH)
        .buildJsonWithoutDefaultAccount()).getString("asset_quote_type"));
    Assert.assertEquals("RTH", JSON.parseObject(AccountParamBuilder.instance()
        .assetQuoteType(AssetQuoteType.RTH)
        .buildJsonWithoutDefaultAccount()).getString("asset_quote_type"));
  }

  @Test
  public void assetQuoteTypeNullLeavesParamAbsent() {
    JSONObject json = JSON.parseObject(AccountParamBuilder.instance()
        .account("testAccount")
        .assetQuoteType(null)
        .buildJsonWithoutDefaultAccount());
    Assert.assertFalse(json.containsKey("asset_quote_type"));
  }
}
