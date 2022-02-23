package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.constant.ReqProtocolType;
import com.tigerbrokers.stock.openapi.client.struct.ClientHeartBeatData;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteKeyType;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.NetworkUtil;
import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import com.tigerbrokers.stock.openapi.client.websocket.WebSocketHandshakerHandler;
import com.tigerbrokers.stock.openapi.client.websocket.WebSocketStompFrameDecoder;
import com.tigerbrokers.stock.openapi.client.websocket.WebSocketStompFrameEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.handler.codec.stomp.StompHeaders;
import io.netty.handler.codec.stomp.StompSubframeAggregator;
import io.netty.handler.codec.stomp.StompSubframeDecoder;
import io.netty.handler.codec.stomp.StompSubframeEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.internal.ConcurrentSet;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.SSLException;

/**
 * Description:
 * Created by lijiawen on 2018/05/16.
 */
public class WebSocketClient implements SubscribeAsyncApi {

  public final static String STOMP_ENCODER = "stompEncoder";
  public final static String STOMP_DECODER = "stompDecoder";
  private static final String[] PROTOCOLS = new String[]{"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3"};

  private String url;
  private ApiAuthentication authentication;
  private ApiComposeCallback apiComposeCallback;
  private final Set<Subject> subscribeList = new CopyOnWriteArraySet<>();
  private final Set<String> subscribeSymbols = new ConcurrentSet<>();
  private CountDownLatch connectCountDown = new CountDownLatch(1);

  private EventLoopGroup group = null;
  private Bootstrap bootstrap = null;
  private volatile Channel channel = null;
  private ChannelFuture future = null;

  private volatile boolean isInitial = false;
  private volatile ScheduledFuture<?> reconnectExecutorFuture = null;
  private ScheduledThreadPoolExecutor reconnectExecutorService = new ScheduledThreadPoolExecutor(1);

  private long lastConnectedTime = System.currentTimeMillis();
  private AtomicInteger reconnectCount = new AtomicInteger(0);
  private AtomicBoolean reconnectErrorLogFlag = new AtomicBoolean(false);

  private static final int CONNECT_TIMEOUT = 5000;
  private static final long SHUTDOWN_TIMEOUT = 1000 * 60 * 15;
  private static final int RECONNECT_WARNING_PERIOD = 1800;
  private static final long RECONNECT_DELAY_TIME = 3 * 1000;
  private static final long RECONNECT_INTERVAL_TIME = 10 * 1000;

  private int clientSendInterval = 0;
  private int clientReceiveInterval = 0;
  private static final int CLIENT_SEND_INTERVAL_MIN = 10000;
  private static final int CLIENT_RECEIVE_INTERVAL_MIN = 10000;

  private WebSocketClient() {
  }

  private static class SingletonInner {
    private static WebSocketClient singleton = new WebSocketClient();
  }

  /**
   * get WebSocketClient instance
   * @return WebSocketClient
   */
  public static WebSocketClient getInstance() {
    return SingletonInner.singleton;
  }

  public WebSocketClient url(String url) {
    this.url = url;
    return this;
  }

  public WebSocketClient authentication(final ApiAuthentication authentication) {
    this.authentication = authentication;
    return this;
  }

  public WebSocketClient apiComposeCallback(final ApiComposeCallback apiComposeCallback) {
    this.apiComposeCallback = apiComposeCallback;
    return this;
  }

  public WebSocketClient clientHeartBeatData(final ClientHeartBeatData clientHeartBeatData) {
    if (clientHeartBeatData != null) {
      if (clientHeartBeatData.getSendInterval() >= 0) {
        this.clientSendInterval =
            clientHeartBeatData.getSendInterval() >= CLIENT_SEND_INTERVAL_MIN ? clientHeartBeatData.getSendInterval()
                : CLIENT_SEND_INTERVAL_MIN;
      }
      if (clientHeartBeatData.getReceiveInterval() >= 0) {
        this.clientReceiveInterval = clientHeartBeatData.getReceiveInterval() >= CLIENT_RECEIVE_INTERVAL_MIN
            ? clientHeartBeatData.getReceiveInterval() : CLIENT_RECEIVE_INTERVAL_MIN;
      }
    }
    return this;
  }

  private void checkArgument() {
    if (this.url == null || this.url.isEmpty()) {
      throw new IllegalArgumentException("url is empty.");
    }
    if (this.authentication == null) {
      throw new IllegalArgumentException("authentication info is missing.");
    }
    if (this.apiComposeCallback == null) {
      throw new IllegalArgumentException("apiComposeCallback is missing.");
    }
    if (connectCountDown.getCount() == 0) {
      connectCountDown = new CountDownLatch(1);
    }
  }

  private synchronized void init() {
    if (isInitial) {
      return;
    }
    InetSocketAddress address = getServerAddress();
    if (address == null) {
      throw new RuntimeException("get connect address error.");
    }
    group = new NioEventLoopGroup(1);
    bootstrap = new Bootstrap();
    final String[] protocols = NetworkUtil.getOpenSslSupportedProtocolsSet(PROTOCOLS, SslProvider.OPENSSL);
    if (protocols == null || protocols.length == 0) {
      throw new RuntimeException("supported protocols is empty.");
    }

    final int port = address.getPort();
    bootstrap.group(group).option(ChannelOption.TCP_NODELAY, true)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws SSLException {
            ChannelPipeline p = ch.pipeline();
            SslContext sslCtx =
                SslContextBuilder.forClient()
                    .protocols(protocols)
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .sslProvider(SslProvider.OPENSSL)
                    .build();
            p.addLast(sslCtx.newHandler(ch.alloc(), address.getHostName(), address.getPort()));
            if (port == 8887 || port == 8889) {
              p.addLast("websocketCodec", new HttpClientCodec());
              p.addLast("websocketAggregator", new HttpObjectAggregator(65535));
              p.addLast(STOMP_ENCODER, new WebSocketStompFrameDecoder());
              p.addLast(STOMP_DECODER, new WebSocketStompFrameEncoder());
              p.addLast("stompAggregator", new StompSubframeAggregator(65535));
            } else {
              final WebSocketHandler handler =
                  new WebSocketHandler(authentication, apiComposeCallback, clientSendInterval, clientReceiveInterval);
              p.addLast(STOMP_ENCODER, new StompSubframeEncoder());
              p.addLast(STOMP_DECODER, new StompSubframeDecoder());
              p.addLast("aggregator", new StompSubframeAggregator(65535));
              p.addLast("webSocketHandler", handler);
            }
          }
        });

    isInitial = true;
  }

  public void connectCountDown() {
    connectCountDown.countDown();
  }

  /**
   * create the connection (The same tigerId has only one active connection)
   */
  public synchronized void connect() {
    try {
      if (isConnected()) {
        return;
      }
      checkArgument();
      initReconnectCommand();
      doConnect();
      if (!isConnected()) {
        throw new Exception("Failed connect to server.");
      }
      ApiLogger.info("Success connect to server, channel is: {}", channel);

      reconnectCount.set(0);
      reconnectErrorLogFlag.set(false);
    } catch (Throwable e) {
      ApiLogger.error("Failed connect to server, cause: ", e);
    }
  }

  private void doConnect() {
    try {
      long start = System.currentTimeMillis();
      init();
      InetSocketAddress address = getServerAddress();
      if (address == null) {
        throw new RuntimeException("get connect address error.");
      }

      future = bootstrap.connect(address).sync();
      boolean completed = future.awaitUninterruptibly(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);

      if (completed && future.isSuccess()) {
        Channel newChannel = future.channel();
        URI uri = null;
        try {
          uri = new URI(url);
          Channel oldChannel = this.channel;
          if (oldChannel != null && oldChannel.isActive()) {
            ApiLogger.info("close old netty channel:{} , create new netty channel:{} ", oldChannel, newChannel);
            oldChannel.close();
          }
        } finally {
          this.channel = newChannel;
          if (address.getPort() == 8887 || address.getPort() == 8889) {
            synchronized (this.channel) {
              WebSocketHandshakerHandler webSocketHandshakerHandler =
                  new WebSocketHandshakerHandler(authentication, apiComposeCallback, clientSendInterval,
                      clientReceiveInterval);
              HttpHeaders httpHeaders = new DefaultHttpHeaders();
              WebSocketClientHandshaker handshaker =
                  WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, true, httpHeaders);
              webSocketHandshakerHandler.setHandshaker(handshaker);
              channel.pipeline().addLast("handshakeHandler", webSocketHandshakerHandler);
              webSocketHandshakerHandler.setHandshaker(handshaker);
              ChannelPromise channelFuture = (ChannelPromise) handshaker.handshake(this.channel);
              channelFuture.sync();
            }
          }
          connectCountDown.await(5000, TimeUnit.MILLISECONDS);
        }
      } else if (future.cause() != null) {
        throw new Exception("client failed to connect to server, error message is:" + future.cause().getMessage(),
            future.cause());
      } else {
        throw new Exception(
            "client failed to connect to server, client-side timeout: " + (System.currentTimeMillis() - start) + "ms ");
      }
    } catch (Exception e) {
      ApiLogger.error("client failed to connect to server: ", e);
    } finally {
      if (!isConnected()) {
        future.cancel(true);
      }
    }
  }

  private InetSocketAddress getServerAddress() {
    if (StringUtils.isEmpty(url)) {
      ApiLogger.error("url is empty.");
      return null;
    }
    URI uri;
    try {
      uri = new URI(url);
    } catch (URISyntaxException e) {
      ApiLogger.error("uri syntax exception:", e);
      return null;
    }
    return new InetSocketAddress(uri.getHost(), uri.getPort());
  }

  /**
   * destroy the reconnect thread and close the connection
   */
  public void disconnect() {
    destroyConnectCommand();
    sendDisconnectFrame();
    try {
      if (channel != null) {
        channel.close();
      }
      channel = null;
    } catch (Throwable e) {
      ApiLogger.error(e.getMessage(), e);
    }

    try {
      group.shutdownGracefully();
    } catch (Throwable t) {
      ApiLogger.error(t.getMessage());
    } finally {
      isInitial = false;
    }
  }

  private synchronized void sendDisconnectFrame() {
    if (!isConnected()) {
      notConnect();
      return;
    }
    StompFrame disconnectFrame = StompMessageUtil.buildDisconnectMessage(authentication.getTigerId());
    ChannelFuture channelFuture = channel.writeAndFlush(disconnectFrame);
    try {
      channelFuture.sync();
      ApiLogger.info("sendDisconnectFrame finished, tiger id:{}", authentication.getTigerId());
    } catch (InterruptedException e) {
      ApiLogger.error("sendDisconnectFrame error, tiger id:{}", authentication.getTigerId(), e);
    }
  }

  private void destroyConnectCommand() {
    synchronized (SingletonInner.singleton) {
      try {
        if (reconnectExecutorFuture != null && !reconnectExecutorFuture.isDone()) {
          reconnectExecutorFuture.cancel(true);
          reconnectExecutorService.purge();
          reconnectExecutorService.shutdownNow();
        }
      } catch (Throwable e) {
        ApiLogger.error(e.getMessage(), e);
      }
      reconnectCount.set(0);
      reconnectErrorLogFlag.set(false);
    }
  }

  public boolean isConnected() {
    if (channel == null) {
      return false;
    }
    return channel.isActive();
  }

  private void initReconnectCommand() {
    synchronized (SingletonInner.singleton) {
      if (reconnectExecutorFuture == null || reconnectExecutorFuture.isCancelled()) {
        Runnable reconnectCommand = () -> {
          try {
            if (!isConnected()) {
              connect();
            } else {
              lastConnectedTime = System.currentTimeMillis();
            }
          } catch (Throwable t) {
            if (System.currentTimeMillis() - lastConnectedTime > SHUTDOWN_TIMEOUT) {
              if (!reconnectErrorLogFlag.get()) {
                reconnectErrorLogFlag.set(true);
                ApiLogger.error("client reconnect to server error, lastConnectedTime:{}, currentTime:{}",
                    lastConnectedTime,
                    System.currentTimeMillis(), t);
                return;
              }
            }
            if (reconnectCount.getAndIncrement() % RECONNECT_WARNING_PERIOD == 0) {
              ApiLogger.error("client reconnect to server error, lastConnectedTime:{}, currentTime:{}",
                  lastConnectedTime,
                  System.currentTimeMillis(), t);
            }
          }
        };
        if (reconnectExecutorService.isShutdown()) {
          reconnectExecutorService = new ScheduledThreadPoolExecutor(1);
        }
        reconnectExecutorFuture =
            reconnectExecutorService.scheduleWithFixedDelay(reconnectCommand, RECONNECT_DELAY_TIME,
                RECONNECT_INTERVAL_TIME, TimeUnit.MILLISECONDS);
      }
    }
  }

  @Override
  public String subscribe(Subject subject, List<String> focusKeys) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    StompFrame frame = StompMessageUtil.buildSubscribeMessage(subject, new HashSet<>(focusKeys));
    channel.writeAndFlush(frame);
    subscribeList.add(subject);

    return frame.headers().getAsString(StompHeaders.ID);
  }

  @Override
  public String subscribe(Subject subject) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    StompFrame frame = StompMessageUtil.buildSubscribeMessage(subject);
    channel.writeAndFlush(frame);
    subscribeList.add(subject);

    return frame.headers().getAsString(StompHeaders.ID);
  }

  @Override
  public String subscribe(String account, Subject subject) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    StompFrame frame = StompMessageUtil.buildSubscribeMessage(account, subject, null);
    channel.writeAndFlush(frame);
    subscribeList.add(subject);

    return frame.headers().getAsString(StompHeaders.ID);
  }

  @Override
  public String subscribe(String account, Subject subject, List<String> focusKeys) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    StompFrame frame = StompMessageUtil.buildSubscribeMessage(account, subject, new HashSet<>(focusKeys));
    channel.writeAndFlush(frame);
    subscribeList.add(subject);

    return frame.headers().getAsString(StompHeaders.ID);
  }

  @Override
  public String cancelSubscribe(Subject subject) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    StompFrame frame = StompMessageUtil.buildUnSubscribeMessage(subject);
    channel.writeAndFlush(frame);
    subscribeList.remove(subject);

    return frame.headers().getAsString(StompHeaders.ID);
  }

  @Override
  public String subscribeQuote(Set<String> symbols) {
    return subscribeQuote(symbols, QuoteSubject.Quote);
  }

  @Override
  public String subscribeQuote(Set<String> symbols, QuoteKeyType quoteKeyType) {
    List<String> focusKeys = (quoteKeyType == null ? null : quoteKeyType.getFocusKeys());
    return subscribeQuote(symbols, QuoteSubject.Quote, focusKeys);
  }

  @Override
  public String subscribeQuote(Set<String> symbols, List<String> focusKeys) {
    return subscribeQuote(symbols, QuoteSubject.Quote, focusKeys);
  }

  @Override
  public String cancelSubscribeQuote(Set<String> symbols) {
    return cancelSubscribeQuote(symbols, QuoteSubject.Quote);
  }

  @Override
  public String subscribeOption(Set<String> symbols) {
    return subscribeQuote(symbols, QuoteSubject.Option);
  }

  @Override
  public String cancelSubscribeOption(Set<String> symbols) {
    return cancelSubscribeQuote(symbols, QuoteSubject.Option);
  }

  @Override
  public String subscribeFuture(Set<String> symbols) {
    return subscribeQuote(symbols, QuoteSubject.Future);
  }

  @Override
  public String cancelSubscribeFuture(Set<String> symbols) {
    return cancelSubscribeQuote(symbols, QuoteSubject.Future);
  }

  @Override
  public String subscribeDepthQuote(Set<String> symbols) {
    return subscribeQuote(symbols, QuoteSubject.QuoteDepth);
  }

  @Override
  public String cancelSubscribeDepthQuote(Set<String> symbols) {
    return cancelSubscribeQuote(symbols, QuoteSubject.QuoteDepth);
  }

  private String subscribeQuote(Set<String> symbols, QuoteSubject subject) {
    return subscribeQuote(symbols, subject, null);
  }

  private String subscribeQuote(Set<String> symbols, QuoteSubject subject, List<String> focusKeys) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    StompFrame frame;
    if (focusKeys == null) {
      frame = StompMessageUtil.buildSubscribeMessage(symbols, subject);
    } else {
      frame = StompMessageUtil.buildSubscribeMessage(symbols, subject, new HashSet<>(focusKeys));
    }
    channel.writeAndFlush(frame);
    subscribeSymbols.addAll(symbols);
    ApiLogger.info("send subscribe [{}] message, symbols:{},focusKeys:{}", subject, symbols, focusKeys);

    return frame.headers().getAsString(StompHeaders.ID);
  }

  private String cancelSubscribeQuote(Set<String> symbols, QuoteSubject subject) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    StompFrame frame = StompMessageUtil.buildUnSubscribeMessage(symbols, subject);
    channel.writeAndFlush(frame);
    subscribeSymbols.removeAll(symbols);
    ApiLogger.info("send cancel subscribe [{}] message, symbols:{}.", subject, symbols);

    return frame.headers().getAsString(StompHeaders.ID);
  }

  @Override
  public String getSubscribedSymbols() {
    return sendMessage(ReqProtocolType.REQ_SUB_SYMBOLS, null);
  }

  private String sendMessage(int reqType, String message) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    ApiLogger.info("reqType:{},send message:{}", reqType, message);
    StompFrame frame = StompMessageUtil.buildSendMessage(reqType, message);
    channel.writeAndFlush(frame);

    return frame.headers().getAsString(StompHeaders.ID);
  }

  private void notConnect() {
    ApiLogger.info("connection is closed.");
  }
}
