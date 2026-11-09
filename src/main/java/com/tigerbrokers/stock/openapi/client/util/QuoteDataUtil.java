package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBBOData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;

/**
 * author：bean
 * date：2022/12/02
 */
public class QuoteDataUtil {

  public static QuoteBBOData convertToAskBidData(QuoteData quoteData) {
    if (quoteData == null || quoteData.getType() == null) {
      return null;
    }
    SocketCommon.QuoteType type = quoteData.getType();
    if (SocketCommon.QuoteType.ALL != type && SocketCommon.QuoteType.BBO != type) {
      return null;
    }
    QuoteBBOData.Builder builder = QuoteBBOData.newBuilder();
    builder.setSymbol(quoteData.getSymbol());
    builder.setType(SocketCommon.QuoteType.BBO);
    builder.setTimestamp(quoteData.getTimestamp());
    builder.setAskPrice(quoteData.getAskPrice());
    builder.setAskSize(quoteData.getAskSize());
    builder.setBidPrice(quoteData.getBidPrice());
    builder.setBidSize(quoteData.getBidSize());
    if (quoteData.hasAskTimestamp()) {
      builder.setAskTimestamp(quoteData.getAskTimestamp());
    }
    if (quoteData.hasBidTimestamp()) {
      builder.setBidTimestamp(quoteData.getBidTimestamp());
    }

    return builder.build();
  }

  public static QuoteBasicData convertToBasicData(QuoteData quoteData) {
    if (quoteData == null || quoteData.getType() == null) {
      return null;
    }
    SocketCommon.QuoteType type = quoteData.getType();
    if (SocketCommon.QuoteType.ALL != type && SocketCommon.QuoteType.BASIC != type) {
      return null;
    }
    QuoteBasicData.Builder builder = QuoteBasicData.newBuilder();
    builder.setSymbol(quoteData.getSymbol());
    builder.setType(SocketCommon.QuoteType.BASIC);
    builder.setTimestamp(quoteData.getTimestamp());
    if (quoteData.hasServerTimestamp()) {
      builder.setServerTimestamp(quoteData.getServerTimestamp());
    }
    if (quoteData.hasAvgPrice()) {
      builder.setAvgPrice(quoteData.getAvgPrice());
    }
    if (quoteData.hasLatestPrice()) {
      builder.setLatestPrice(quoteData.getLatestPrice());
    }
    if (quoteData.hasLatestPriceTimestamp()) {
      builder.setLatestPriceTimestamp(quoteData.getLatestPriceTimestamp());
    }
    if (quoteData.hasLatestTime()) {
      builder.setLatestTime(quoteData.getLatestTime());
    }
    if (quoteData.hasPreClose()) {
      builder.setPreClose(quoteData.getPreClose());
    }
    if (quoteData.hasVolume()) {
      builder.setVolume(quoteData.getVolume());
    }
    if (quoteData.hasVolumeDecimal()) {
      builder.setVolumeDecimal(quoteData.getVolumeDecimal());
    }
    if (quoteData.hasAmount()) {
      builder.setAmount(quoteData.getAmount());
    }

    if (quoteData.hasOpen()) {
      builder.setOpen(quoteData.getOpen());
    }
    if (quoteData.hasHigh()) {
      builder.setHigh(quoteData.getHigh());
    }
    if (quoteData.hasLow()) {
      builder.setLow(quoteData.getLow());
    }

    if (quoteData.hasHourTradingTag()) {
      builder.setHourTradingTag(quoteData.getHourTradingTag());
    }
    if (quoteData.hasMarketStatus()) {
      builder.setMarketStatus(quoteData.getMarketStatus());
    }

    // extended-session fields, only Options support
    if (quoteData.hasCumVolume()) {
      builder.setCumVolume(quoteData.getCumVolume());
    }
    if (quoteData.hasCumAmount()) {
      builder.setCumAmount(quoteData.getCumAmount());
    }
    if (quoteData.hasSession()) {
      builder.setSession(quoteData.getSession());
    }

    // only Options support
    if (quoteData.hasIdentifier()) {
      builder.setIdentifier(quoteData.getIdentifier());
    }
    // open interest, only Options support
    if (quoteData.hasOpenInt()) {
      builder.setOpenInt(quoteData.getOpenInt());
    }
    // latest trad time, only Futures support
    if (quoteData.hasTradeTime()) {
      builder.setTradeTime(quoteData.getTradeTime());
    }
    // previous settlement price, only Futures support
    if (quoteData.hasPreSettlement()) {
      builder.setPreSettlement(quoteData.getPreSettlement());
    }
    // min tick, only Futures support
    if (quoteData.hasMinTick()) {
      builder.setMinTick(quoteData.getMinTick());
    }

    if (quoteData.hasMi()) {
      builder.setMi(quoteData.getMi());
    }

    return builder.build();
  }

}
