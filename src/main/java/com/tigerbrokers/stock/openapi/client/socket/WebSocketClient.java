package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.config.ClientConfig;
import com.tigerbrokers.stock.openapi.client.constant.ReqProtocolType;
import com.tigerbrokers.stock.openapi.client.constant.TigerApiConstants;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Request;
import com.tigerbrokers.stock.openapi.client.socket.data.pb.Response;
import com.tigerbrokers.stock.openapi.client.struct.ClientHeartBeatData;
import com.tigerbrokers.stock.openapi.client.struct.Indicator;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.QuoteSubject;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.client.util.ApiLogger;
import com.tigerbrokers.stock.openapi.client.util.ConfigFileUtil;
import com.tigerbrokers.stock.openapi.client.util.NetworkUtil;
import com.tigerbrokers.stock.openapi.client.util.ProtoMessageUtil;
import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.handler.codec.stomp.StompHeaders;
import io.netty.handler.codec.stomp.StompSubframeAggregator;
import io.netty.handler.codec.stomp.StompSubframeDecoder;
import io.netty.handler.codec.stomp.StompSubframeEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.SSLException;

/**
 * Description:
 * Created by lijiawen on 2018/05/16.
 */
public class WebSocketClient implements SubscribeAsyncApi {

  public final static String SOCKET_ENCODER = "socketEncoder";
  public final static String SOCKET_DECODER = "socketDecoder";
  private static final String[] PROTOCOLS = new String[]{"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3"};

  private ClientConfig clientConfig;
  private SslProvider sslProvider = null;
  private String url;

  private boolean isProtobuf = true;
  private ApiAuthentication authentication;
  private ApiComposeCallback apiComposeCallback;
  private final Set<Subject> subscribeList = new CopyOnWriteArraySet<>();
  private volatile CountDownLatch connectCountDown = new CountDownLatch(1);

  private EventLoopGroup group = null;
  private Bootstrap bootstrap = null;
  private volatile Channel channel = null;
  private ChannelFuture future = null;
  private SslContext sslCtx;

  private volatile boolean isInitial = false;
  private volatile ScheduledFuture<?> reconnectExecutorFuture = null;
  private ScheduledThreadPoolExecutor reconnectExecutorService = new ScheduledThreadPoolExecutor(1);

  private long lastConnectedTime = System.currentTimeMillis();
  private AtomicInteger reconnectCount = new AtomicInteger(0);
  private AtomicBoolean reconnectErrorLogFlag = new AtomicBoolean(false);

  private static final int CONNECT_TIMEOUT = 5000;
  private static final int OP_TIMEOUT = 5000;
  private static final long SHUTDOWN_TIMEOUT = 1000 * 60 * 15;
  private static final int RECONNECT_WARNING_PERIOD = 1800;
  private static final long RECONNECT_DELAY_TIME = 3 * 1000;
  private static final long RECONNECT_INTERVAL_TIME = 10 * 1000;

  private int clientSendInterval = 30000;
  private int clientReceiveInterval = 30000;
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

  /**
   * set SslProvider
   * @param sslProvider
   * @return WebSocketClient
   */
  public WebSocketClient sslProvider(SslProvider sslProvider) {
    this.sslProvider = sslProvider;
    return this;
  }

  public WebSocketClient clientConfig(ClientConfig clientConfig) {
    ConfigFileUtil.loadConfigFile(clientConfig);
    this.clientConfig = clientConfig;
    this.url = NetworkUtil.getServerAddress(null);
    if (this.sslProvider == null && clientConfig.getSslProvider() != null) {
      this.sslProvider = clientConfig.getSslProvider();
    }
    if (this.authentication == null) {
      ApiAuthentication authentication = ApiAuthentication.build(clientConfig.tigerId, clientConfig.privateKey);
      if (!StringUtils.isEmpty(clientConfig.version)) {
        authentication.setVersion(clientConfig.version);
      }
      this.authentication = authentication;
    }
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
    if (apiComposeCallback instanceof ApiComposeCallback4Stomp) {
      this.isProtobuf = false;
    } else if (apiComposeCallback instanceof ApiComposeCallback) {
      this.isProtobuf = true;
    } else {
      throw new IllegalArgumentException("please use ApiComposeCallback's instance.");
    }
    if (connectCountDown.getCount() == 0) {
      connectCountDown = new CountDownLatch(1);
    }
  }

  private synchronized void init() throws SSLException {
    if (isInitial) {
      return;
    }
    group = new NioEventLoopGroup(1);
    bootstrap = new Bootstrap();

    if (clientConfig.isSslSocket) {
      SslProvider provider = this.sslProvider == null ? SslProvider.OPENSSL : this.sslProvider;
      final String[] protocols = NetworkUtil.getSupportedProtocolsSet(PROTOCOLS, provider);
      if (protocols == null || protocols.length == 0) {
        throw new RuntimeException("supported protocols is empty.");
      }
      sslCtx = SslContextBuilder.forClient()
          .protocols(protocols)
          .trustManager(InsecureTrustManagerFactory.INSTANCE)
          .sslProvider(provider)
          .build();
    }

    bootstrap.group(group).option(ChannelOption.TCP_NODELAY, true)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) {
            ChannelPipeline p = ch.pipeline();
            InetSocketAddress address = getNewServerAddress();
            if (address == null) {
              throw new RuntimeException("get connect address error.");
            }
            if (clientConfig.isSslSocket) {
              p.addLast(TigerApiConstants.SSL_HANDLER_NAME,
                  sslCtx.newHandler(ch.alloc(), address.getHostName(), address.getPort()));
            }
            if (isProtobuf) {
              final ProtoSocketHandler handler =
                  new ProtoSocketHandler(authentication, apiComposeCallback, clientSendInterval, clientReceiveInterval);
              p.addLast(SOCKET_DECODER, new ProtobufVarint32FrameDecoder());
              p.addLast(new ProtobufDecoder(Response.getDefaultInstance()));
              p.addLast(new ProtobufVarint32LengthFieldPrepender());
              p.addLast(SOCKET_ENCODER, new ProtobufEncoder());
              p.addLast("webSocketHandler", handler);
            } else {
              final WebSocketHandler handler =
                  new WebSocketHandler(authentication, apiComposeCallback, clientSendInterval, clientReceiveInterval);
              p.addLast(SOCKET_ENCODER, new StompSubframeEncoder());
              p.addLast(SOCKET_DECODER, new StompSubframeDecoder());
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
      InetSocketAddress address = getNewServerAddress();
      if (address == null) {
        throw new RuntimeException("get connect address error.");
      }

      future = bootstrap.connect(address).sync();
      boolean completed = future.awaitUninterruptibly(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);

      if (completed && future.isSuccess()) {
        Channel newChannel = future.channel();
        try {
          Channel oldChannel = this.channel;
          if (oldChannel != null && oldChannel.isActive()) {
            ApiLogger.info("close old netty channel:{} , create new netty channel:{} ", oldChannel, newChannel);
            oldChannel.close();
          }
        } finally {
          this.channel = newChannel;
          connectCountDown.await(OP_TIMEOUT, TimeUnit.MILLISECONDS);
          if (connectCountDown.getCount() > 0) {
            this.channel.close();
          }
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
      if (future != null && !isConnected()) {
        future.cancel(true);
      }
    }
  }

  private InetSocketAddress getNewServerAddress() {
    if (clientConfig != null) {
      String newUrl = NetworkUtil.getServerAddress(this.url);
      if (!this.url.equals(newUrl)) {
        InetSocketAddress address = getSocketAddress(newUrl);
        if (address != null) {
          ApiLogger.info("socket url changed. {}-->{}", this.url, newUrl);
          if (channel != null && channel.pipeline().get(TigerApiConstants.SSL_HANDLER_NAME) != null) {
            ChannelHandler oldHandler = channel.pipeline().get(TigerApiConstants.SSL_HANDLER_NAME);
            channel.pipeline().replace(oldHandler, TigerApiConstants.SSL_HANDLER_NAME,
                sslCtx.newHandler(channel.alloc(), address.getHostName(), address.getPort()));
            ApiLogger.info("socket url changed. {}-->{}. replace sslHandler", this.url, newUrl);
          }
          this.url = newUrl;
          return address;
        }
      }
    }
    return getSocketAddress();
  }

  private InetSocketAddress getSocketAddress() {
    return getSocketAddress(this.url);
  }

  private InetSocketAddress getSocketAddress(String urlString) {
    if (StringUtils.isEmpty(urlString)) {
      ApiLogger.error("url is empty.");
      return null;
    }

    URI uri;
    try {
      uri = new URI(urlString);
    } catch (URISyntaxException e) {
      ApiLogger.error("uri syntax exception:{}", urlString, e);
      return null;
    }
    return new InetSocketAddress(uri.getHost(), uri.getPort());
  }

  public String getUrl() {
    return this.url;
  }
  public boolean isUseProtobuf() {
    return this.isProtobuf;
  }

  /**
   * destroy the reconnect thread, then send disconnect command and close the connection
   * <p>Note: Sending the disconnect command will cancel all subscription data</p>
   */
  public void disconnect() {
    closeConnect(true);
  }

  /**
   * close the connection
   * @sendDisconnectCommand true:send disconnect command
   */
  public void closeConnect(boolean sendDisconnectCommand) {
    destroyConnectCommand();
    if (sendDisconnectCommand) {
      sendDisconnectData();
    }
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

  private synchronized void sendDisconnectData() {
    if (!isConnected()) {
      notConnect();
      return;
    }
    Object disconnectData = this.isProtobuf
        ? ProtoMessageUtil.buildDisconnectMessage()
        : StompMessageUtil.buildDisconnectMessage(authentication.getTigerId());
    ChannelFuture channelFuture = channel.writeAndFlush(disconnectData);
    try {
      channelFuture.sync();
      ApiLogger.info("sendDisconnect finished, tiger id:{}", authentication.getTigerId());
    } catch (InterruptedException e) {
      ApiLogger.error("sendDisconnect error, tiger id:{}", authentication.getTigerId(), e);
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
    if (channel == null || connectCountDown.getCount() > 0) {
      return false;
    }
    return channel.isActive();
  }

  private void initReconnectCommand() {
    synchronized (SingletonInner.singleton) {
      if (reconnectExecutorFuture == null || reconnectExecutorFuture.isCancelled()) {
        Runnable reconnectCommand = new Runnable() {
          @Override
          public void run() {
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
          }
        };
        if (reconnectExecutorService.isShutdown()) {
          reconnectExecutorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
              Thread t = Executors.defaultThreadFactory().newThread(r);
              t.setDaemon(true);
              return t;
            }
          });
        }
        reconnectExecutorFuture =
            reconnectExecutorService.scheduleWithFixedDelay(reconnectCommand, RECONNECT_DELAY_TIME,
                RECONNECT_INTERVAL_TIME, TimeUnit.MILLISECONDS);
      }
    }
  }

  @Override
  public String subscribe(Subject subject) {
    return subscribe(null, subject);
  }

  @Override
  public String subscribe(String account, Subject subject) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    String returnStr;
    Object subscribeData;
    if (this.isProtobuf) {
      subscribeData = ProtoMessageUtil.buildSubscribeMessage(account, subject);
      returnStr = String.valueOf(((Request)subscribeData).getId());
    } else {
      subscribeData = StompMessageUtil.buildSubscribeMessage(account, subject, null);
      returnStr = ((StompFrame)subscribeData).headers().getAsString(StompHeaders.ID);
    }
    channel.writeAndFlush(subscribeData);
    subscribeList.add(subject);

    return returnStr;
  }

  @Override
  public String cancelSubscribe(Subject subject) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    String returnStr;
    Object unsubscribeData;
    if (this.isProtobuf) {
      unsubscribeData = ProtoMessageUtil.buildUnSubscribeMessage(subject);
      returnStr = String.valueOf(((Request)unsubscribeData).getId());
    } else {
      unsubscribeData = StompMessageUtil.buildUnSubscribeMessage(subject);
      returnStr = ((StompFrame)unsubscribeData).headers().getAsString(StompHeaders.ID);
    }
    channel.writeAndFlush(unsubscribeData);
    subscribeList.remove(subject);

    return returnStr;
  }

  @Override
  public String subscribeQuote(Set<String> symbols) {
    return subscribeQuote(symbols, QuoteSubject.Quote);
  }

  @Override
  public String cancelSubscribeQuote(Set<String> symbols) {
    return cancelSubscribeQuote(symbols, QuoteSubject.Quote);
  }

  @Override
  public String subscribeTradeTick(Set<String> symbols) {
    return subscribeQuote(symbols, QuoteSubject.TradeTick);
  }

  @Override
  public String cancelSubscribeTradeTick(Set<String> symbols) {
    return cancelSubscribeQuote(symbols, QuoteSubject.TradeTick);
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
    if (!isConnected()) {
      notConnect();
      return null;
    }
    String returnStr;
    Object subscribeData;
    if (this.isProtobuf) {
      subscribeData = ProtoMessageUtil.buildSubscribeMessage(symbols, subject);
      returnStr = String.valueOf(((Request)subscribeData).getId());
    } else {
      subscribeData = StompMessageUtil.buildSubscribeMessage(symbols, subject, null);
      returnStr = ((StompFrame)subscribeData).headers().getAsString(StompHeaders.ID);
    }
    channel.writeAndFlush(subscribeData);
    ApiLogger.info("send subscribe [{}] message, symbols:{}", subject, symbols);

    return returnStr;
  }

  private String cancelSubscribeQuote(Set<String> symbols, QuoteSubject subject) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    String returnStr;
    Object unsubscribeData;
    if (this.isProtobuf) {
      unsubscribeData = ProtoMessageUtil.buildUnSubscribeMessage(symbols, subject);
      returnStr = String.valueOf(((Request)unsubscribeData).getId());
    } else {
      unsubscribeData = StompMessageUtil.buildUnSubscribeMessage(symbols, subject);
      returnStr = ((StompFrame)unsubscribeData).headers().getAsString(StompHeaders.ID);
    }
    channel.writeAndFlush(unsubscribeData);
    ApiLogger.info("send cancel subscribe [{}] message, symbols:{}.", subject, symbols);

    return returnStr;
  }

  @Override
  public String subscribeMarketQuote(Market market, QuoteSubject subject) {
    return subscribeMarketData(market, subject, null);
  }

  @Override
  public String cancelSubscribeMarketQuote(Market market, QuoteSubject subject) {
    return cancelSubscribeMarketData(market, subject, null);
  }

  @Override
  public String subscribeStockTop(Market market, Set<Indicator> indicators) {
    return subscribeMarketData(market, QuoteSubject.StockTop, Indicator.getValues(indicators));
  }

  @Override
  public String cancelSubscribeStockTop(Market market, Set<Indicator> indicators) {
    return cancelSubscribeMarketData(market, QuoteSubject.StockTop, Indicator.getValues(indicators));
  }

  @Override
  public String subscribeOptionTop(Market market, Set<Indicator> indicators) {
    return subscribeMarketData(market, QuoteSubject.OptionTop, Indicator.getValues(indicators));
  }

  @Override
  public String cancelSubscribeOptionTop(Market market, Set<Indicator> indicators) {
    return cancelSubscribeMarketData(market, QuoteSubject.OptionTop, Indicator.getValues(indicators));
  }

  private String subscribeMarketData(Market market, QuoteSubject subject, Set<String> indicatorNames) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    Request subscribeData = ProtoMessageUtil.buildSubscribeMessage(market, subject, indicatorNames);
    channel.writeAndFlush(subscribeData);
    ApiLogger.info("send subscribe [{}] message, market:{}", subject, market);
    return String.valueOf(subscribeData.getId());
  }

  private String cancelSubscribeMarketData(Market market, QuoteSubject subject, Set<String> indicatorNames) {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    Request subscribeData = ProtoMessageUtil.buildUnSubscribeMessage(market, subject, indicatorNames);
    channel.writeAndFlush(subscribeData);
    ApiLogger.info("send cancel subscribe [{}] message, market:{}", subject, market);
    return String.valueOf(subscribeData.getId());
  }

  @Override
  public String getSubscribedSymbols() {
    if (!isConnected()) {
      notConnect();
      return null;
    }
    ApiLogger.info("send getSubscribedSymbols message");
    String returnStr;
    Object msgData;
    if (this.isProtobuf) {
      msgData = ProtoMessageUtil.buildSendMessage();
      returnStr = String.valueOf(((Request)msgData).getId());
    } else {
      msgData = StompMessageUtil.buildSendMessage(ReqProtocolType.REQ_SUB_SYMBOLS, null);
      returnStr = ((StompFrame)msgData).headers().getAsString(StompHeaders.ID);
    }
    channel.writeAndFlush(msgData);

    return returnStr;
  }

  private void notConnect() {
    ApiLogger.info("connection is closed.");
  }
}
