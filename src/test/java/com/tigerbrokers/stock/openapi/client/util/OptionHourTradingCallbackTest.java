package com.tigerbrokers.stock.openapi.client.util;

import com.google.protobuf.Descriptors;
import com.google.protobuf.util.JsonFormat;
import com.tigerbrokers.stock.openapi.client.socket.ApiCallbackDecoder;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.socket.executor.MessageCallbackExecutor;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;

public class OptionHourTradingCallbackTest {
  private QuoteData.Builder flat(String session) {
    return QuoteData.newBuilder().setSymbol("SPXW 20260723 7380.0 PUT")
        .setType(SocketCommon.QuoteType.BASIC).setSession(session)
        .setHourTradingTag("GTH".equals(session) ? "PreMarket" : "AfterHours")
        .setLatestPrice(1.5).setLatestPriceTimestamp(1784796380425L)
        .setPreClose(1.1).setVolume(7).setVolumeDecimal(7).setAmount(12.5)
        .setCumVolume(1000).setCumAmount(9999);
  }

  @Test
  public void flatWireQuotePreservesSessionFieldsAndCumulativeTotals() throws Exception {
    QuoteData source = QuoteData.parseFrom(flat("GTH").build().toByteArray());
    QuoteBasicData data = QuoteDataUtil.convertToBasicData(source);
    Assert.assertEquals("GTH", data.getSession());
    Assert.assertEquals("PreMarket", data.getHourTradingTag());
    Assert.assertEquals(1.5, data.getLatestPrice(), 0);
    Assert.assertEquals(1784796380425L, data.getLatestPriceTimestamp());
    Assert.assertEquals(7L, data.getVolume());
    Assert.assertEquals(12.5, data.getAmount(), 0);
    Assert.assertEquals(1000L, data.getCumVolume());
    Assert.assertEquals(9999.0, data.getCumAmount(), 0);
    Assert.assertFalse(JsonFormat.printer().print(data).contains("\"hourTrading\""));
  }

  @Test
  public void curbZeroValuesKeepTheirPresence() {
    QuoteBasicData data = QuoteDataUtil.convertToBasicData(flat("CURB").setLatestPrice(0)
        .setLatestPriceTimestamp(0).setVolume(0).setAmount(0).setPreClose(0).build());
    Assert.assertEquals("AfterHours", data.getHourTradingTag());
    Assert.assertTrue(data.hasLatestPrice());
    Assert.assertEquals(0.0, data.getLatestPrice(), 0);
    Assert.assertTrue(data.hasLatestPriceTimestamp());
    Assert.assertEquals(0L, data.getLatestPriceTimestamp());
    Assert.assertTrue(data.hasVolume());
    Assert.assertEquals(0L, data.getVolume());
    Assert.assertTrue(data.hasAmount());
    Assert.assertEquals(0.0, data.getAmount(), 0);
    Assert.assertEquals(1000L, data.getCumVolume());
  }

  @Test
  public void missingFlatFieldsAreNotFabricatedAsZeroOrReceiveTime() {
    QuoteBasicData data = QuoteDataUtil.convertToBasicData(QuoteData.newBuilder()
        .setSymbol("SPXW").setType(SocketCommon.QuoteType.BASIC)
        .setSession("GTH").setHourTradingTag("PreMarket").setTimestamp(1793667586000L).build());
    Assert.assertFalse(data.hasLatestPrice());
    Assert.assertFalse(data.hasLatestPriceTimestamp());
    Assert.assertFalse(data.hasPreClose());
    Assert.assertFalse(data.hasVolume());
    Assert.assertFalse(data.hasAmount());
  }

  @Test
  public void rthKeepsMissingSessionAndOriginalFields() {
    QuoteBasicData data = QuoteDataUtil.convertToBasicData(QuoteData.newBuilder()
        .setSymbol("SPXW").setType(SocketCommon.QuoteType.BASIC)
        .setLatestPrice(2).setLatestPriceTimestamp(123).setVolume(100).setOpen(1).build());
    Assert.assertFalse(data.hasSession());
    Assert.assertFalse(data.hasHourTradingTag());
    Assert.assertEquals(2.0, data.getLatestPrice(), 0);
    Assert.assertEquals(100L, data.getVolume());
    Assert.assertTrue(data.hasOpen());
  }

  @Test
  public void bboStaysOnAskBidCallbackOnly() {
    QuoteData source = QuoteData.newBuilder().setSymbol("SPXW")
        .setType(SocketCommon.QuoteType.BBO).setAskPrice(1.6).setBidPrice(1.4).build();
    Assert.assertNull(QuoteDataUtil.convertToBasicData(source));
    ApiComposeCallback callback = mock(ApiComposeCallback.class);
    decoder(callback).handle(response(source));
    verify(callback).optionAskBidChange(any(QuoteBBOData.class));
    verify(callback, never()).optionChange(any(QuoteBasicData.class));
  }

  @Test
  public void optionChangeReceivesFlattenedGthQuote() {
    ApiComposeCallback callback = mock(ApiComposeCallback.class);
    decoder(callback).handle(response(flat("GTH").build()));
    ArgumentCaptor<QuoteBasicData> captured = ArgumentCaptor.forClass(QuoteBasicData.class);
    verify(callback).optionChange(captured.capture());
    Assert.assertEquals("GTH", captured.getValue().getSession());
    Assert.assertEquals("PreMarket", captured.getValue().getHourTradingTag());
    Assert.assertEquals(1.5, captured.getValue().getLatestPrice(), 0);
    Assert.assertEquals(7L, captured.getValue().getVolume());
    Assert.assertEquals(1000L, captured.getValue().getCumVolume());
    verify(callback, never()).optionAskBidChange(any(QuoteBBOData.class));
  }

  @Test
  public void hkFlatQuoteKeepsOriginalFields() {
    QuoteBasicData data = QuoteDataUtil.convertToBasicData(QuoteData.newBuilder()
        .setSymbol("HOS.HK 20270629 205.00 PUT").setType(SocketCommon.QuoteType.BASIC)
        .setLatestPrice(142.69).setVolume(100).build());
    Assert.assertEquals(142.69, data.getLatestPrice(), 0);
    Assert.assertEquals(100L, data.getVolume());
    Assert.assertFalse(data.hasHourTradingTag());
  }

  @Test
  public void unpublishedNestedFieldAndTypeAreRemoved() {
    for (Descriptors.Descriptor descriptor : new Descriptors.Descriptor[]{
        QuoteData.getDescriptor(), QuoteBasicData.getDescriptor()}) {
      Assert.assertNull(descriptor.findFieldByName("hourTrading"));
      Assert.assertNull(descriptor.findNestedTypeByName("HourTrading"));
      Assert.assertNull(descriptor.findFieldByNumber(33));
    }
  }

  private Response response(QuoteData quote) {
    return Response.newBuilder().setCommand(SocketCommon.Command.MESSAGE)
        .setBody(PushData.newBuilder().setDataType(SocketCommon.DataType.Option).setQuoteData(quote)).build();
  }

  private ApiCallbackDecoder decoder(ApiComposeCallback callback) {
    return new ApiCallbackDecoder(callback, new MessageCallbackExecutor() {
      @Override public void execute(Runnable task, SocketCommon.DataType type, String symbol) { task.run(); }
      @Override public void shutdown() { }
      @Override public void shutdownNow() { }
    });
  }
}
