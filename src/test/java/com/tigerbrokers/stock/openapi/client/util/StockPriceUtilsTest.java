package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSONArray;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.item.TickSizeItem;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author bean
 * @date 2022/6/17
 */
@RunWith(MockitoJUnitRunner.class)
public class StockPriceUtilsTest {

  @Test
  public void testStockPrice() {

    String content = "[{\"begin\":\"0\",\"end\":\"0.25\",\"type\":\"CLOSED\",\"tickSize\":0.001},"
        + "{\"begin\":\"0.25\",\"end\":\"0.5\",\"type\":\"OPEN_CLOSED\",\"tickSize\":0.005},"
        + "{\"begin\":\"0.5\",\"end\":\"10\",\"type\":\"OPEN_CLOSED\",\"tickSize\":0.01},"
        + "{\"begin\":\"10\",\"end\":\"20\",\"type\":\"OPEN_CLOSED\",\"tickSize\":0.02},"
        + "{\"begin\":\"20\",\"end\":\"100\",\"type\":\"OPEN_CLOSED\",\"tickSize\":0.05},{\"begin\":\"100\",\"end\":\"200\",\"type\":\"OPEN_CLOSED\",\"tickSize\":0.1},{\"begin\":\"200\",\"end\":\"500\",\"type\":\"OPEN_CLOSED\",\"tickSize\":0.2},{\"begin\":\"500\",\"end\":\"1000\",\"type\":\"OPEN_CLOSED\",\"tickSize\":0.5},{\"begin\":\"1000\",\"end\":\"2000\",\"type\":\"OPEN_CLOSED\",\"tickSize\":1.0},{\"begin\":\"2000\",\"end\":\"5000\",\"type\":\"OPEN_CLOSED\",\"tickSize\":2.0},{\"begin\":\"5000\",\"end\":\"Infinity\",\"type\":\"OPEN\",\"tickSize\":5.0}]";

    List<TickSizeItem> tickSizeItemList = JSONArray.parseArray(content, TickSizeItem.class);

    Assert.assertEquals(10.34d, StockPriceUtils.fixPriceByTickSize(
        10.34d, tickSizeItemList), 0.0001);
    Assert.assertEquals(10.34d, StockPriceUtils.fixPriceByTickSize(
        10.35d, tickSizeItemList), 0.0001);
    Assert.assertEquals(10.36d, StockPriceUtils.fixPriceByTickSize(
        10.36d, tickSizeItemList), 0.0001);
    Assert.assertEquals(10.36d, StockPriceUtils.fixPriceByTickSize(
        10.35d, tickSizeItemList, true), 0.0001);

    Assert.assertTrue(StockPriceUtils.matchTickSize(10.34d, tickSizeItemList));
    Assert.assertFalse(StockPriceUtils.matchTickSize(10.35d, tickSizeItemList));
    Assert.assertTrue(StockPriceUtils.matchTickSize(10.36d, tickSizeItemList));
  }

}
