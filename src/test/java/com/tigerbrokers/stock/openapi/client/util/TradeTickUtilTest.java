package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author liutongping
 * @version 1.0
 * @date 2022/6/17 下午5:15
 */
@RunWith(MockitoJUnitRunner.class)
public class TradeTickUtilTest {

  @Test
  public void testDecodeData01() {

    String content = "{\"symbol\":\"00999\","
        + "\"times\":[1655436459646, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 41, 0, 0, 0],"
        + "\"type\":\"-----------------------------------+\","
        + "\"priceOffset\":1,"
        + "\"volumes\":[200, 109, 2, 3, 31, 348, 19, 118, 500, 177, 367, 52, 50, 514, 250, 299, 50, 3, 50, 240, 136, 10, 1, 1, 101, 661, 188, 141, 159, 653, 78, 27, 29, 1, 1, 2],"
        + "\"priceBase\":192, \"sn\":16693, \"partCode\":[\"t\", \"c\",\"z\"],"
        + "\"cond\":\"DXM *M\","
        + "\"prices\":[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3],"
        + "\"timestamp\":1655436460251}";

    JSONObject jsonObject = JSONObject.parseObject(content);
    jsonObject = TradeTickUtil.decodeData(jsonObject);

    System.out.println(jsonObject);
    JSONArray timeArray = jsonObject.getJSONArray("times");
    JSONArray pricesArray = jsonObject.getJSONArray("prices");
    JSONArray partCodeArray = jsonObject.getJSONArray("partCode");
    JSONArray tadeCondArray = jsonObject.getJSONArray("tradeCond");
    Assert.assertEquals(36, timeArray.size());
    Assert.assertEquals(1655436459646L, timeArray.getLongValue(0));
    Assert.assertEquals(1655436459656L, timeArray.getLongValue(31));
    Assert.assertEquals(1655436459697L, timeArray.getLongValue(35));

    Assert.assertEquals(36, pricesArray.size());
    Assert.assertEquals(19.2D, pricesArray.getDoubleValue(0), 0.0001d);
    Assert.assertEquals(19.2D, pricesArray.getDoubleValue(34), 0.0001d);
    Assert.assertEquals(19.5D, pricesArray.getDoubleValue(35), 0.0001d);

    Assert.assertEquals("NASDAQ Stock Market, LLC (NASDAQ)", partCodeArray.getString(0));

    Assert.assertEquals("HK_ODD_LOT_TRADE", tadeCondArray.getString(0));
    Assert.assertEquals("HK_OVERSEAS_TRADE", tadeCondArray.getString(4));
  }
  @Test
  public void testDecodeData02() {

    String content = "{\"symbol\":\"XXX\","
        + "\"times\":[1655436459646, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 41, 0, 0, 0],"
        + "\"type\":\"-----------------------------------+\","
        + "\"priceOffset\":1,"
        + "\"volumes\":[200, 109, 2, 3, 31, 348, 19, 118, 500, 177, 367, 52, 50, 514, 250, 299, 50, 3, 50, 240, 136, 10, 1, 1, 101, 661, 188, 141, 159, 653, 78, 27, 29, 1, 1, 2],"
        + "\"priceBase\":192, \"sn\":16693, \"partCode\":[\"a\", \"c\",\"z\"],"
        + " \"cond\":\"III RI\","
        + "\"prices\":[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3],"
        + "\"timestamp\":1655436460251}";

    JSONObject jsonObject = JSONObject.parseObject(content);
    jsonObject = TradeTickUtil.decodeData(jsonObject);

    System.out.println(jsonObject);
    JSONArray tadeCondArray = jsonObject.getJSONArray("tradeCond");
    Assert.assertEquals(6, tadeCondArray.size());
    Assert.assertEquals("US_ODD_LOT_TRADE", tadeCondArray.getString(0));
    Assert.assertEquals("US_REGULAR_SALE", tadeCondArray.getString(3));
    Assert.assertEquals("US_SELLER", tadeCondArray.getString(4));

  }
}
