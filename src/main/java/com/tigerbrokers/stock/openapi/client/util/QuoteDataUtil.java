package com.tigerbrokers.stock.openapi.client.util;

import java.util.Optional;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBBOData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteBasicData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.QuoteData;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.SocketCommon;

/**
 * author：bean
 * date：2022/12/02
 */
public class QuoteDataUtil {

  public static Optional<QuoteBBOData> convertToAskBidData(QuoteData quoteData) {
    if (quoteData == null || quoteData.getType() == null) {
      return Optional.empty();
    }
    SocketCommon.QuoteType type = quoteData.getType();
    if (SocketCommon.QuoteType.ALL != type && SocketCommon.QuoteType.BBO != type) {
      return Optional.empty();
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

    return Optional.ofNullable(builder.build());
  }

  public static Optional<QuoteBasicData> convertToBasicData(QuoteData quoteData) {
    if (quoteData == null || quoteData.getType() == null) {
      return Optional.empty();
    }
    SocketCommon.QuoteType type = quoteData.getType();
    if (SocketCommon.QuoteType.ALL != type && SocketCommon.QuoteType.BASIC != type) {
      return Optional.empty();
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
    builder.setLatestPrice(quoteData.getLatestPrice());
    if (quoteData.hasLatestPriceTimestamp()) {
      builder.setLatestPriceTimestamp(quoteData.getLatestPriceTimestamp());
    }
    builder.setLatestTime(quoteData.getLatestTime());
    builder.setPreClose(quoteData.getPreClose());
    builder.setVolume(quoteData.getVolume());
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

    return Optional.ofNullable(builder.build());
  }
}
