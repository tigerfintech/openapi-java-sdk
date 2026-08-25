package com.tigerbrokers.stock.openapi.client.https.domain;

import com.alibaba.fastjson.JSON;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.item.PrimeAnalyticsAssetItem.HistoryItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionTimelineItem;
import com.tigerbrokers.stock.openapi.client.https.domain.option.item.OptionTimelineItem.OptionTimelinePoint;
import org.junit.Assert;
import org.junit.Test;

public class MoreInnerClassTest {

  @Test
  public void testHistoryItem_jsonRoundTrip() {
    HistoryItem item = new HistoryItem();
    item.setDate(1234567890L);
    item.setAsset(10000.0);
    item.setPnl(500.0);
    item.setPnlPercentage(5.0);

    String json = JSON.toJSONString(item);
    HistoryItem parsed = JSON.parseObject(json, HistoryItem.class);
    Assert.assertNotNull(parsed);
    Assert.assertEquals(1234567890L, (long) parsed.getDate());
    Assert.assertEquals(10000.0, parsed.getAsset(), 0.001);
  }

  @Test
  public void testOptionTimelinePoint() {
    OptionTimelinePoint p = new OptionTimelinePoint();
    p.setPrice(150.0);
    p.setAvgPrice(145.0);
    p.setTime(1234567890L);
    p.setVolume(100L);
    Assert.assertEquals(150.0, p.getPrice(), 0.001);
    Assert.assertEquals(145.0, p.getAvgPrice(), 0.001);

    String json = JSON.toJSONString(p);
    OptionTimelinePoint parsed = JSON.parseObject(json, OptionTimelinePoint.class);
    Assert.assertNotNull(parsed);
  }

  @Test
  public void testOptionTimelineItem() {
    OptionTimelineItem item = new OptionTimelineItem();
    item.setSymbol("AAPL");
    item.setExpiry(1705708800000L);
    item.setRight("CALL");
    Assert.assertEquals("AAPL", item.getSymbol());
  }
}
