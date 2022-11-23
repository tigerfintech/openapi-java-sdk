package com.tigerbrokers.stock.openapi.client.util.builder;

import com.tigerbrokers.stock.openapi.client.constant.ReqProtocolType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.client.util.SdkVersionUtils;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import io.netty.handler.codec.stomp.DefaultStompHeaders;
import io.netty.handler.codec.stomp.StompHeaders;
import io.netty.util.AsciiString;
import java.util.Collection;
import java.util.Set;

import static com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants.SEPARATOR;
import static io.netty.handler.codec.stomp.StompHeaders.HEART_BEAT;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class HeaderBuilder {

  public static final String REQ_TYPE = ReqProtocolType.REQ_HEADER;
  public static final String FOCUS_KEYS = "keys";
  public static final String STOMP_VERSION_10 = "1.0";
  public static final String STOMP_VERSION_11 = "1.1";
  public static final String STOMP_VERSION_12 = "1.2";
  public static final String PROTOBUF_VERSION_3 = "3";
  public static final String DEFAULT_VERSION = PROTOBUF_VERSION_3;
  public static final String HOST = "localhost";
  public static final String ACCOUNT = "account";
  public static final String SYMBOLS = "symbols";
  private static AsciiString SDK_VERSION_HEADER = AsciiString.cached(TigerApiConstants.SDK_VERSION);

  private static String USE_VERSION = PROTOBUF_VERSION_3;
  private StompHeaders stompHeaders;

  private HeaderBuilder() {
    stompHeaders = new DefaultStompHeaders();
  }

  public static HeaderBuilder instance() {
    return new HeaderBuilder();
  }

  public static String getUseVersion() {
    return USE_VERSION;
  }

  public static void setUseVersion(String version) {
    if (PROTOBUF_VERSION_3.equals(version)
        || STOMP_VERSION_12.equals(version)
        || STOMP_VERSION_11.equals(version)
        || STOMP_VERSION_10.equals(version)) {
      USE_VERSION = version;
    }
  }

  public static boolean isUseProtobuf() {
    return isUseProtobuf(USE_VERSION);
  }

  public static boolean isUseProtobuf(String acceptVersion) {
    if (StringUtils.isEmpty(acceptVersion)) {
      return PROTOBUF_VERSION_3.equals(DEFAULT_VERSION);
    }
    String[] versions = acceptVersion.split(SEPARATOR);
    for (String item : versions) {
      if (PROTOBUF_VERSION_3.equals(item)) {
        return true;
      }
    }
    return false;
  }

  public HeaderBuilder version(String version) {
    this.stompHeaders.set(StompHeaders.ACCEPT_VERSION, version);
    return this;
  }

  public HeaderBuilder sdkVersion() {
    this.stompHeaders.set(SDK_VERSION_HEADER, SdkVersionUtils.getSdkVersion());
    return this;
  }

  public HeaderBuilder host() {
    this.stompHeaders.set(StompHeaders.HOST, HOST);
    return this;
  }

  public HeaderBuilder login(String login) {
    this.stompHeaders.set(StompHeaders.LOGIN, login);
    return this;
  }

  public HeaderBuilder passcode(String passcode) {
    this.stompHeaders.set(StompHeaders.PASSCODE, passcode);
    return this;
  }

  public HeaderBuilder id(int id) {
    this.stompHeaders.set(StompHeaders.ID, String.valueOf(id));
    return this;
  }

  public HeaderBuilder reqType(int reqType) {
    this.stompHeaders.set(REQ_TYPE, String.valueOf(reqType));
    return this;
  }

  public HeaderBuilder subject(Subject subject) {
    this.stompHeaders.set(StompHeaders.SUBSCRIPTION, subject.name());
    return this;
  }

  public HeaderBuilder subject(String subject) {
    this.stompHeaders.set(StompHeaders.SUBSCRIPTION, subject);
    return this;
  }

  public HeaderBuilder focusKeys(Set<String> focusKeys) {
    if (focusKeys != null && !focusKeys.isEmpty()) {
      this.stompHeaders.set(FOCUS_KEYS, join(focusKeys));
    }
    return this;
  }

  public HeaderBuilder account(String account) {
    if (null != account) {
      this.stompHeaders.set(ACCOUNT, account);
    }
    return this;
  }

  public HeaderBuilder heartBeat(int cx, int cy) {
    String value = String.format("%d,%d", cx, cy);
    this.stompHeaders.set(HEART_BEAT, value);
    return this;
  }

  public static String join(Collection<String> collection) {
    StringBuilder builder = new StringBuilder();
    boolean isFirst = true;
    for (String symbol : collection) {
      if (!isFirst) {
        builder.append(",").append(symbol);
      } else {
        builder.append(symbol);
        isFirst = false;
      }
    }

    return builder.toString();
  }

  public HeaderBuilder symbols(Set<String> symbols) {
    if (symbols != null && !symbols.isEmpty()) {
      this.stompHeaders.set(SYMBOLS, join(symbols));
    }
    return this;
  }

  public StompHeaders build() {
    return this.stompHeaders;
  }
}
