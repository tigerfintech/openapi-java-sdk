package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.socket.data.TradeTick;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.TradeTickData;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.util.List;
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

  /* ---------- convert(TradeTickData) — stock path ---------- */

  @Test
  public void testConvertStockData() {
    TradeTickData data = TradeTickData.newBuilder()
        .setSymbol("AAPL")
        .setSecType(SecType.STK.name())
        .setQuoteLevel("quote-tick")
        .setTimestamp(1655436460251L)
        .setSn(100L)
        .setPriceBase(192L)
        .setPriceOffset(1)
        .setType("TradeTick")
        .setCond("IXM")
        .addTime(1655436459646L)
        .addTime(0L)
        .addPrice(0L)
        .addPrice(0L)
        .addVolume(200L)
        .addVolume(109L)
        .addPartCode("t")
        .addPartCode("c")
        .build();

    TradeTick tick = TradeTickUtil.convert(data);
    Assert.assertNotNull(tick);
    Assert.assertEquals(SecType.STK, tick.getSecType());
    Assert.assertEquals("AAPL", tick.getSymbol());
    Assert.assertEquals("quote-tick", tick.getQuoteLevel());
    Assert.assertEquals(1655436460251L, tick.getTimestamp());

    List<TradeTick.Tick> ticks = tick.getTicks();
    Assert.assertEquals(2, ticks.size());
    Assert.assertEquals(100L, ticks.get(0).getSn());
    Assert.assertEquals(101L, ticks.get(1).getSn());
    Assert.assertEquals(19.2D, ticks.get(0).getPrice(), 0.0001);
    Assert.assertEquals(200L, ticks.get(0).getVolume());
    Assert.assertEquals("NASDAQ Stock Market, LLC (NASDAQ)", ticks.get(0).getPartName());
    Assert.assertEquals("NSDQ", ticks.get(0).getPartCode());
    Assert.assertEquals("US_ODD_LOT_TRADE", ticks.get(0).getCond());
    Assert.assertEquals("US_CROSS_TRADE", ticks.get(1).getCond());
  }

  @Test
  public void testConvertStockData_emptyTimeList() {
    TradeTickData data = TradeTickData.newBuilder()
        .setSymbol("AAPL")
        .setSecType(SecType.STK.name())
        .setSn(1L)
        .setPriceBase(0L)
        .setPriceOffset(0)
        .build();
    TradeTick tick = TradeTickUtil.convert(data);
    Assert.assertNotNull(tick);
    Assert.assertEquals(0, tick.getTicks().size());
  }

  @Test
  public void testConvertStockData_hkSymbol() {
    TradeTickData data = TradeTickData.newBuilder()
        .setSymbol("00999")
        .setSecType(SecType.STK.name())
        .setSn(5L)
        .setPriceBase(192L)
        .setPriceOffset(1)
        .setCond("DX*")
        .setType("---+")
        .addTime(100L)
        .addTime(0L)
        .addTime(0L)
        .addPrice(0L)
        .addPrice(0L)
        .addPrice(3L)
        .addVolume(200L)
        .addVolume(2L)
        .addVolume(5L)
        .addPartCode("t")
        .addPartCode("z")
        .addPartCode("a")
        .build();
    TradeTick tick = TradeTickUtil.convert(data);
    List<TradeTick.Tick> ticks = tick.getTicks();
    Assert.assertEquals(3, ticks.size());
    Assert.assertEquals("HK_ODD_LOT_TRADE", ticks.get(0).getCond());
    Assert.assertEquals("HK_DIRECT_OFF_EXCHG_TRADE", ticks.get(1).getCond());
    Assert.assertEquals("HK_OVERSEAS_TRADE", ticks.get(2).getCond());
    Assert.assertEquals("-", ticks.get(0).getTickType());
    Assert.assertEquals("-", ticks.get(2).getTickType());
  }

  /* ---------- convert(TradeTickData) — future path ---------- */

  @Test
  public void testConvertFutureData() {
    TradeTickData.MergedVol mv1 = TradeTickData.MergedVol.newBuilder()
        .setMergeTimes(2)
        .addVol(4L)
        .addVol(1L)
        .build();
    TradeTickData.MergedVol mv2 = TradeTickData.MergedVol.newBuilder()
        .setMergeTimes(1)
        .addVol(16L)
        .build();

    TradeTickData data = TradeTickData.newBuilder()
        .setSymbol("ESmain")
        .setSecType(SecType.FUT.name())
        .setTimestamp(1666237140468L)
        .setSn(18203L)
        .setPriceBase(368075L)
        .setPriceOffset(2)
        .addTime(1666237140000L)
        .addTime(0L)
        .addPrice(0L)
        .addPrice(0L)
        .addMergedVols(mv1)
        .addMergedVols(mv2)
        .build();

    TradeTick tick = TradeTickUtil.convert(data);
    Assert.assertNotNull(tick);
    Assert.assertEquals(SecType.FUT, tick.getSecType());
    Assert.assertEquals("ESmain", tick.getSymbol());
    Assert.assertEquals(1666237140468L, tick.getTimestamp());

    List<TradeTick.Tick> ticks = tick.getTicks();
    Assert.assertEquals(3, ticks.size());
    Assert.assertEquals(4L, ticks.get(0).getVolume());
    Assert.assertEquals(1L, ticks.get(1).getVolume());
    Assert.assertEquals(16L, ticks.get(2).getVolume());
    Assert.assertEquals(182030L, ticks.get(0).getSn());
    Assert.assertEquals(182031L, ticks.get(1).getSn());
    Assert.assertEquals(182040L, ticks.get(2).getSn());
    Assert.assertEquals(3680.75D, ticks.get(0).getPrice(), 0.0001);
  }

  @Test
  public void testConvertFutureData_emptyTimeList() {
    TradeTickData data = TradeTickData.newBuilder()
        .setSymbol("ESmain")
        .setSecType(SecType.FUT.name())
        .setSn(1L)
        .setPriceBase(0L)
        .setPriceOffset(0)
        .build();
    TradeTick tick = TradeTickUtil.convert(data);
    Assert.assertNotNull(tick);
    Assert.assertEquals(0, tick.getTicks().size());
  }

  /* ---------- decodeStockData edge cases ---------- */

  @Test
  public void testDecodeStockData_emptyTimeArray() {
    String content = "{\"symbol\":\"AAPL\",\"times\":[],\"prices\":[],\"partCode\":[],"
        + "\"volumes\":[],\"priceBase\":0,\"priceOffset\":0,\"sn\":1,\"timestamp\":1}";
    JSONObject json = JSONObject.parseObject(content);
    json = TradeTickUtil.decodeStockData(json);
    JSONArray ticks = json.getJSONArray("ticks");
    Assert.assertEquals(0, ticks.size());
  }

  @Test
  public void testDecodeStockData_nullPartCodeAndCond() {
    String content = "{\"symbol\":\"AAPL\",\"times\":[100],\"prices\":[5],"
        + "\"partCode\":[],\"volumes\":[10],\"priceBase\":100,\"priceOffset\":2,\"sn\":1,\"timestamp\":1}";
    JSONObject json = JSONObject.parseObject(content);
    json = TradeTickUtil.decodeStockData(json);
    JSONArray ticks = json.getJSONArray("ticks");
    Assert.assertEquals(1, ticks.size());
    JSONObject t0 = ticks.getJSONObject(0);
    Assert.assertEquals(1, t0.getIntValue("sn"));
    Assert.assertEquals(1.05D, t0.getDoubleValue("price"), 0.0001);
    Assert.assertEquals("US_REGULAR_SALE", t0.getString("cond"));
  }

  @Test
  public void testDecodeFutureData_emptyTimeArray() {
    String content = "{\"symbol\":\"ESmain\",\"times\":[],\"prices\":[],"
        + "\"mergedVols\":[],\"priceBase\":0,\"priceOffset\":0,\"sn\":1,\"timestamp\":1}";
    JSONObject json = JSONObject.parseObject(content);
    json = TradeTickUtil.decodeFutureData(json);
    JSONArray ticks = json.getJSONArray("ticks");
    Assert.assertEquals(0, ticks.size());
  }

  @Test
  public void testDecodeStockData_unknownPartCode() {
    String content = "{\"symbol\":\"AAPL\",\"times\":[100],\"prices\":[0],"
        + "\"partCode\":[\"Q\"],\"volumes\":[5],\"priceBase\":100,\"priceOffset\":0,"
        + "\"sn\":1,\"timestamp\":1}";
    JSONObject json = JSONObject.parseObject(content);
    json = TradeTickUtil.decodeStockData(json);
    JSONArray ticks = json.getJSONArray("ticks");
    JSONObject t0 = ticks.getJSONObject(0);
    // unknown part code "Q" falls back to the code itself
    Assert.assertEquals("Q", t0.getString("partCode"));
    Assert.assertEquals("Q", t0.getString("partName"));
  }
}
