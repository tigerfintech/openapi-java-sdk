package com.tigerbrokers.stock.openapi.client.util;

import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.client.util.builder.HeaderBuilder;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.stomp.DefaultStompFrame;
import io.netty.handler.codec.stomp.StompCommand;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.handler.codec.stomp.StompHeaders;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 * Created by lijiawen on 2018/05/23.
 */
public class StompMessageUtil {

  private static AtomicInteger increment = new AtomicInteger(0);

  public static StompFrame buildConnectMessage(String login, String passcode, String version) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.CONNECT);
    stompFrame.headers()
        .set(HeaderBuilder.instance().version(version).sdkVersion()
            .id(increment.addAndGet(1))
            .host().login(login).passcode(passcode).build());
    return stompFrame;
  }

  /**
   * @param login login
   * @param passcode passcode
   * @param version version
   * @param sendInterval The client can guarantee the minimum interval for sending heartbeats, 0 means the client does not send heartbeats
   * @param receiveInterval The client wants to receive the heartbeat interval of the server, 0 means the client does not want to receive the server's heartbeat
   * @return StompFrame
   */
  public static StompFrame buildConnectMessage(String login, String passcode, String version, int sendInterval,
      int receiveInterval) {
    if (sendInterval < 0 || receiveInterval < 0) {
      throw new RuntimeException("sendInterval < 0 or receiveInterval < 0");
    }
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.CONNECT);
    stompFrame.headers()
        .set(HeaderBuilder.instance()
            .version(version)
            .sdkVersion()
            .id(increment.addAndGet(1))
            .host()
            .login(login)
            .passcode(passcode)
            .heartBeat(sendInterval, receiveInterval)
            .build());
    return stompFrame;
  }

  public static StompFrame buildSendMessage(int reqType, String message) {
    if (reqType <= 0) {
      throw new RuntimeException("reqType不能为空");
    }
    StompFrame stompFrame;
    int id = increment.addAndGet(1);
    if (message != null) {
      stompFrame =
          new DefaultStompFrame(StompCommand.SEND, ByteBufAllocator.DEFAULT.buffer().writeBytes(
              message.getBytes(Charset.defaultCharset())));
    } else {
      stompFrame = new DefaultStompFrame(StompCommand.SEND);
    }
    StompHeaders headers = HeaderBuilder.instance().id(id).host().reqType(reqType).build();
    stompFrame.headers().set(headers);

    return stompFrame;
  }

  public static StompFrame buildCommonSendMessage(String message) {
    StompFrame stompFrame;
    int id = increment.addAndGet(1);
    if (message != null) {
      stompFrame =
          new DefaultStompFrame(StompCommand.SEND, ByteBufAllocator.DEFAULT.buffer().writeBytes(
              message.getBytes(Charset.defaultCharset())));
    } else {
      stompFrame = new DefaultStompFrame(StompCommand.SEND);
    }
    StompHeaders headers = HeaderBuilder.instance().id(id).host().build();
    stompFrame.headers().set(headers);

    return stompFrame;
  }

  public static StompFrame buildSubscribeMessage(Subject subject) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.SUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers = HeaderBuilder.instance().id(id).host().subject(subject).build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildSubscribeMessage(String account, Subject subject, Set<String> focusKeys) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.SUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers = HeaderBuilder.instance()
        .id(id)
        .account(account)
        .host()
        .subject(subject)
        .focusKeys(focusKeys)
        .build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildSubscribeMessage(Set<String> symbols, QuoteSubject subject) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.SUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers =
        HeaderBuilder.instance()
            .id(id)
            .host()
            .subject(subject.name())
            .symbols(symbols)
            .build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildSubscribeMessage(Set<String> symbols, QuoteSubject subject, Set<String> focusKeys) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.SUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers =
        HeaderBuilder.instance()
            .id(id)
            .host()
            .subject(subject.name())
            .symbols(symbols)
            .focusKeys(focusKeys)
            .build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildUnSubscribeMessage(Subject subject) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.UNSUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers = HeaderBuilder.instance().id(id).host().subject(subject).build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildUnSubscribeMessage(Set<String> symbols, QuoteSubject subject) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.UNSUBSCRIBE);
    int id = increment.addAndGet(1);
    StompHeaders headers =
        HeaderBuilder.instance().id(id).host().subject(subject.name()).symbols(symbols).build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }

  public static StompFrame buildDisconnectMessage(String login) {
    StompFrame stompFrame = new DefaultStompFrame(StompCommand.DISCONNECT);
    StompHeaders headers = HeaderBuilder.instance().login(login).host().build();
    stompFrame.headers().set(headers);
    return stompFrame;
  }
}
