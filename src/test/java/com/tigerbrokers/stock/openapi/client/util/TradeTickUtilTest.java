package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author liutongping
 * @date 2022/6/17
 */
@RunWith(MockitoJUnitRunner.class)
public class TradeTickUtilTest {

  @Test
  public void testDecodeData01() {

    String content = "{\"symbol\":\"00999\","
        + "\"times\":[1655436459646, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 41, 0, 0, 0],"
        + "\"type\":\"TradeTick\","
        + "\"tickType\":\"-----------------------------------+\","
        + "\"priceOffset\":1,"
        + "\"volumes\":[200, 109, 2, 3, 31, 348, 19, 118, 500, 177, 367, 52, 50, 514, 250, 299, 50, 3, 50, 240, 136, 10, 1, 1, 101, 661, 188, 141, 159, 653, 78, 27, 29, 1, 1, 2],"
        + "\"priceBase\":192, \"sn\":16693, \"partCode\":[\"t\", \"c\",\"z\"],"
        + "\"cond\":\"DXM *M\","
        + "\"prices\":[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3],"
        + "\"timestamp\":1655436460251}";

    JSONObject jsonObject = JSONObject.parseObject(content);
    jsonObject = TradeTickUtil.decodeData(jsonObject);

    System.out.println(jsonObject);
    JSONArray tickDetailArray = jsonObject.getJSONArray("ticks");
    Assert.assertEquals(36, tickDetailArray.size());
    Assert.assertEquals(1655436459646L, ((JSONObject)tickDetailArray.get(0)).get("time"));
    Assert.assertEquals(1655436459656L, ((JSONObject)tickDetailArray.get(31)).get("time"));
    Assert.assertEquals(1655436459697L, ((JSONObject)tickDetailArray.get(35)).get("time"));

    Assert.assertEquals(19.2D, ((JSONObject)tickDetailArray.get(0)).get("price"));
    Assert.assertEquals(19.2D, ((JSONObject)tickDetailArray.get(34)).get("price"));
    Assert.assertEquals(19.5D, ((JSONObject)tickDetailArray.get(35)).get("price"));

    Assert.assertEquals("NASDAQ Stock Market, LLC (NASDAQ)", ((JSONObject)tickDetailArray.get(0)).get("partName"));

    Assert.assertEquals("HK_ODD_LOT_TRADE", ((JSONObject)tickDetailArray.get(0)).get("cond"));
    Assert.assertEquals("HK_OVERSEAS_TRADE", ((JSONObject)tickDetailArray.get(4)).get("cond"));

    Assert.assertEquals("-", ((JSONObject)tickDetailArray.get(0)).get("tickType"));
    Assert.assertEquals("+", ((JSONObject)tickDetailArray.get(35)).get("tickType"));
  }

  @Test
  public void testDecodeData02() {

    String content = "{\"symbol\":\"XXX\","
        + "\"times\":[1655436459646, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 41, 0, 0, 0],"
        + "\"type\":\"TradeTick\","
        + "\"tickType\":\"-----------------------------------+\","
        + "\"priceOffset\":1,"
        + "\"volumes\":[200, 109, 2, 3, 31, 348, 19, 118, 500, 177, 367, 52, 50, 514, 250, 299, 50, 3, 50, 240, 136, 10, 1, 1, 101, 661, 188, 141, 159, 653, 78, 27, 29, 1, 1, 2],"
        + "\"priceBase\":192, \"sn\":16693, \"partCode\":[\"a\", \"c\",\"z\"],"
        + " \"cond\":\"III RI\","
        + "\"prices\":[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3],"
        + "\"timestamp\":1655436460251}";

    JSONObject jsonObject = JSONObject.parseObject(content);
    jsonObject = TradeTickUtil.decodeData(jsonObject);

    System.out.println(jsonObject);
    JSONArray tickDetailArray = jsonObject.getJSONArray("ticks");
    Assert.assertEquals(36, tickDetailArray.size());
    Assert.assertEquals("US_ODD_LOT_TRADE", ((JSONObject)tickDetailArray.get(0)).get("cond"));
    Assert.assertEquals("US_REGULAR_SALE", ((JSONObject)tickDetailArray.get(3)).get("cond"));
    Assert.assertEquals("US_SELLER", ((JSONObject)tickDetailArray.get(4)).get("cond"));

  }

  /**
   * test futures's trade tick data
   */
  @Test
  public void testDecodeData03() {

    String content = "{\"symbol\":\"ESmain\",\"times\":[1666237140000,0],"
        + "\"serverTimestamp\":1666237076797,\"priceOffset\":2,\"quoteLevel\":\"quote-fut-tick\","
        + "\"volumes\":[5,16],\"priceBase\":368075,\"sn\":18203,\"prices\":[0,0],"
        + "\"mergedVols\":[{\"vols\":[4,1],\"mergeTimes\":2},"
        + "{\"vols\":[16],\"mergeTimes\":1}],\"type\":\"TradeTick\","
        + "\"timestamp\":1666237140468}";

    JSONObject jsonObject = JSONObject.parseObject(content);
    jsonObject = TradeTickUtil.decodeData(jsonObject);

    System.out.println(jsonObject);
    JSONArray tickDetailArray = jsonObject.getJSONArray("ticks");
    Assert.assertEquals(3, tickDetailArray.size());
    Assert.assertEquals(4, ((JSONObject)tickDetailArray.get(0)).get("volume"));
    Assert.assertEquals(1, ((JSONObject)tickDetailArray.get(1)).get("volume"));
    Assert.assertEquals(16, ((JSONObject)tickDetailArray.get(2)).get("volume"));

    Assert.assertEquals(182030, ((JSONObject)tickDetailArray.get(0)).get("sn"));
    Assert.assertEquals(182031, ((JSONObject)tickDetailArray.get(1)).get("sn"));
    Assert.assertEquals(182040, ((JSONObject)tickDetailArray.get(2)).get("sn"));

    Assert.assertEquals(1666237140000L, ((JSONObject)tickDetailArray.get(0)).get("time"));
    Assert.assertEquals(1666237140000L, ((JSONObject)tickDetailArray.get(1)).get("time"));
    Assert.assertEquals(1666237140000L, ((JSONObject)tickDetailArray.get(2)).get("time"));

    Assert.assertEquals(3680.75D, ((JSONObject)tickDetailArray.get(0)).get("price"));
    Assert.assertEquals(3680.75D, ((JSONObject)tickDetailArray.get(1)).get("price"));
    Assert.assertEquals(3680.75D, ((JSONObject)tickDetailArray.get(2)).get("price"));

  }
}
