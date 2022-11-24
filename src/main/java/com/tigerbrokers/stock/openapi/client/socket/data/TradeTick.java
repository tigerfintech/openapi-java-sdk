package com.tigerbrokers.stock.openapi.client.socket.data;

import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import java.io.Serializable;
import java.util.List;

/**
 * @author bean
 * @date 2022/11/24 7:31 PM
 */
public class TradeTick implements Serializable {
  private String symbol;
  private SecType secType;
  private String quoteLevel;
  private long timestamp;
  private List<Tick> ticks;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public SecType getSecType() {
    return secType;
  }

  public void setSecType(SecType secType) {
    this.secType = secType;
  }

  public String getQuoteLevel() {
    return quoteLevel;
  }

  public void setQuoteLevel(String quoteLevel) {
    this.quoteLevel = quoteLevel;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public List<Tick> getTicks() {
    return ticks;
  }

  public void setTicks(List<Tick> ticks) {
    this.ticks = ticks;
  }

  public static class Tick implements Serializable {
    private long sn;
    private long volume;
    private String tickType;
    private double price;
    private long time;
    private String cond;
    private String partCode;
    private String partName;

    public long getSn() {
      return sn;
    }

    public void setSn(long sn) {
      this.sn = sn;
    }

    public long getVolume() {
      return volume;
    }

    public void setVolume(long volume) {
      this.volume = volume;
    }

    public String getTickType() {
      return tickType;
    }

    public void setTickType(String tickType) {
      this.tickType = tickType;
    }

    public double getPrice() {
      return price;
    }

    public void setPrice(double price) {
      this.price = price;
    }

    public long getTime() {
      return time;
    }

    public void setTime(long time) {
      this.time = time;
    }

    public String getCond() {
      return cond;
    }

    public void setCond(String cond) {
      this.cond = cond;
    }

    public String getPartCode() {
      return partCode;
    }

    public void setPartCode(String partCode) {
      this.partCode = partCode;
    }

    public String getPartName() {
      return partName;
    }

    public void setPartName(String partName) {
      this.partName = partName;
    }
  }

}
