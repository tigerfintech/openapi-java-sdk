package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.constant.ReqProtocolType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.param.AssetParameter;
import com.tigerbrokers.stock.openapi.client.struct.param.OrderParameter;
import com.tigerbrokers.stock.openapi.client.struct.param.PositionParameter;
import com.tigerbrokers.stock.openapi.client.struct.enums.Subject;
import com.tigerbrokers.stock.openapi.client.struct.param.QuoteParameter;
import com.tigerbrokers.stock.openapi.client.util.FastJsonPropertyFilter;
import com.tigerbrokers.stock.openapi.client.util.StompMessageUtil;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.stomp.StompSubframeAggregator;
import io.netty.handler.codec.stomp.StompSubframeDecoder;
import io.netty.handler.codec.stomp.StompSubframeEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.internal.ConcurrentSet;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.SSLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Created by lijiawen on 2018/05/16.
 */
public class WebSocketClient implements TradeAsyncApi, QuoteAsyncApi, SubscribeAsyncApi {

  private static Logger logger = LoggerFactory.getLogger(WebSocketClient.class);

  private String url;
  private boolean async;
  private ApiAuthentication authentication;
  private ApiComposeCallback apiComposeCallback;
  private Set<Subject> subscribeList = new CopyOnWriteArraySet<>();
  private Set<String> subscribeSymbols = new ConcurrentSet<>();
  private CyclicBarrier orderNoBarrier = new CyclicBarrier(2);
  public OrderIdPassport orderIdPassport = new OrderIdPassport();

  private EventLoopGroup group = null;
  private Bootstrap bootstrap = null;
  private Channel channel = null;
  private ChannelFuture future = null;

  private volatile ScheduledFuture<?> reconnectExecutorFuture = null;
  private static final ScheduledThreadPoolExecutor reconnectExecutorService = new ScheduledThreadPoolExecutor(2);

  private long lastConnectedTime = System.currentTimeMillis();
  private AtomicInteger reconnectCount = new AtomicInteger(0);
  private AtomicBoolean reconnectErrorLogFlag = new AtomicBoolean(false);

  private static final int CONNECT_TIMEOUT = 3000;
  private static final long SHUTDOWN_TIMEOUT = 1000 * 60 * 15;
  private static final int RECONNECT_WARNING_PERIOD = 1800;

  public WebSocketClient(String url, ApiAuthentication authentication, ApiComposeCallback apiComposeCallback) {
    this.url = url;
    this.authentication = authentication;
    this.apiComposeCallback = apiComposeCallback;
    async = true;
    init();
  }

  public WebSocketClient(String url, ApiAuthentication authentication, ApiComposeCallback apiComposeCallback,
      boolean async) {
    this.url = url;
    this.authentication = authentication;
    this.apiComposeCallback = apiComposeCallback;
    this.async = async;
    init();
  }

  private void init() {
    InetSocketAddress address = getServerAddress();
    if (address == null) {
      throw new RuntimeException("get connect address error.");
    }
    group = new NioEventLoopGroup(1);
    bootstrap = new Bootstrap();
    final WebSocketHandler handler =
        new WebSocketHandler(authentication, apiComposeCallback, async, orderNoBarrier, orderIdPassport);

    bootstrap.group(group).option(ChannelOption.TCP_NODELAY, true)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          protected void initChannel(SocketChannel ch) throws SSLException {
            ChannelPipeline p = ch.pipeline();
            SslContext sslCtx =
                SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            p.addLast(sslCtx.newHandler(ch.alloc(), address.getHostName(), address.getPort()));
            p.addLast("stompEncoder", new StompSubframeEncoder());
            p.addLast("stompDecoder", new StompSubframeDecoder());
            p.addLast("aggregator", new StompSubframeAggregator(65535));
            p.addLast("webSocketHandler", handler);
          }
        });
    apiComposeCallback.client(this);
  }

  public void connect() {
    try {
      if (isConnected()) {
        return;
      }
      initReconnectCommand();
      doConnect();
      if (!isConnected()) {
        throw new Exception("Failed connect to server.");
      }
      logger.info("Success connect to server, channel is: {}", channel);

      reconnectCount.set(0);
      reconnectErrorLogFlag.set(false);
    } catch (Throwable e) {
      logger.error("Failed connect to server, cause: ", e);
    }
  }

  private void doConnect() {
    try {
      long start = System.currentTimeMillis();
      InetSocketAddress address = getServerAddress();
      if (address == null) {
        throw new RuntimeException("get connect address error.");
      }

      future = bootstrap.connect(address).sync();
      boolean completed = future.awaitUninterruptibly(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);

      if (completed && future.isSuccess()) {
        Channel newChannel = future.channel();
        try {
          Channel oldChannel = this.channel;
          if (oldChannel != null) {
            logger.info("close old netty channel:{} , create new netty channel:{} ", oldChannel, newChannel);
            oldChannel.close();
          }
        } finally {
          this.channel = newChannel;
        }
      } else if (future.cause() != null) {
        throw new Exception("client failed to connect to server, error message is:" + future.cause().getMessage(),
            future.cause());
      } else {
        throw new Exception(
            "client failed to connect to server, client-side timeout: " + (System.currentTimeMillis() - start) + "ms ");
      }
    } catch (Exception e) {
      logger.error("client failed to connect to server: ", e);
    } finally {
      if (!isConnected()) {
        future.cancel(true);
      }
    }
  }

  private InetSocketAddress getServerAddress() {
    if (StringUtils.isEmpty(url)) {
      logger.error("url is empty.");
      return null;
    }
    URI uri;
    try {
      uri = new URI(url);
    } catch (URISyntaxException e) {
      logger.error("uri syntax exception:", e);
      return null;
    }
    return new InetSocketAddress(uri.getHost(), uri.getPort());
  }

  public void disconnect() {
    destroyConnectCommand();
    try {
      if (channel != null) {
        channel.close();
      }
    } catch (Throwable e) {
      logger.warn(e.getMessage(), e);
    }

    try {
      group.shutdownGracefully();
    } catch (Throwable t) {
      logger.warn(t.getMessage());
    }
  }

  private synchronized void destroyConnectCommand() {
    try {
      if (reconnectExecutorFuture != null && !reconnectExecutorFuture.isDone()) {
        reconnectExecutorFuture.cancel(true);
        reconnectExecutorService.purge();
      }
    } catch (Throwable e) {
      logger.warn(e.getMessage(), e);
    }
  }

  public boolean isConnected() {
    if (channel == null) {
      return false;
    }
    return channel.isActive();
  }

  private synchronized void initReconnectCommand() {
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
              logger.error("client reconnect to server error, lastConnectedTime:{}, currentTime:{}", lastConnectedTime,
                  System.currentTimeMillis(), t);
              return;
            }
          }
          if (reconnectCount.getAndIncrement() % RECONNECT_WARNING_PERIOD == 0) {
            logger.warn("client reconnect to server error, lastConnectedTime:{}, currentTime:{}", lastConnectedTime,
                System.currentTimeMillis(), t);
          }
        }
      };
      reconnectExecutorFuture =
          reconnectExecutorService.scheduleWithFixedDelay(reconnectCommand, 2 * 1000, 10 * 1000,
              TimeUnit.MILLISECONDS);
    }
  }

  @Override
  public int getOrderNo(String account) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("account", account);
    sendMessage(ReqProtocolType.ORDER_NO, jsonObject.toJSONString());
    nonAsyncWait();
    return orderIdPassport.getOrderId();
  }

  private void nonAsyncWait() {
    if (!async) {
      try {
        orderNoBarrier.await();
      } catch (InterruptedException e) {
        logger.error("connect interrupted exception:", e);
      } catch (BrokenBarrierException e) {
        logger.error("connect broken barrier exception:", e);
      }
    }
  }

  @Override
  public void previewOrder(OrderParameter orderParameter) {
    sendMessage(ReqProtocolType.PREVIEW_ORDER,
        JSONObject.toJSONString(orderParameter, FastJsonPropertyFilter.getPropertyFilter()));
  }

  @Override
  public void placeOrder(OrderParameter orderParameter) {
    sendMessage(ReqProtocolType.PLACE_ORDER,
        JSONObject.toJSONString(orderParameter, FastJsonPropertyFilter.getPropertyFilter()));
  }

  @Override
  public void cancelOrder(String account, int orderId) {
    Map<String, String> params = new HashMap<>();
    params.put("order_id", orderId + "");
    params.put("account", account);
    sendMessage(ReqProtocolType.CANCEL_ORDER, JSONObject.toJSONString(params));
  }

  @Override
  public void modifyOrder(OrderParameter orderParameter) {
    sendMessage(ReqProtocolType.MODIFY_ORDER,
        JSONObject.toJSONString(orderParameter, FastJsonPropertyFilter.getPropertyFilter()));
  }

  @Override
  public void subscribe(Subject subject, List<String> focusKeys) {
    if (!isConnected()) {
      notConnect();
      return;
    }
    channel.writeAndFlush(StompMessageUtil.buildSubscribeMessage(subject, new HashSet<>(focusKeys)));
    subscribeList.add(subject);
  }

  @Override
  public void subscribe(Subject subject) {
    if (!isConnected()) {
      notConnect();
      return;
    }
    channel.writeAndFlush(StompMessageUtil.buildSubscribeMessage(subject));
    subscribeList.add(subject);
  }

  @Override
  public void cancelSubscribe(Subject subject) {
    if (!isConnected()) {
      notConnect();
      return;
    }
    channel.writeAndFlush(StompMessageUtil.buildUnSubscribeMessage(subject));
    subscribeList.remove(subject);
  }

  @Override
  public void getOpenOrders() {
    sendMessage(ReqProtocolType.REQ_OPEN_ORDERS, null);
  }

  @Override
  public void getPosition(PositionParameter position) {
    sendMessage(ReqProtocolType.REQ_POSITIONS, null);
  }

  @Override
  public void getAsset(AssetParameter asset) {
    sendMessage(ReqProtocolType.REQ_ASSETS, null);
  }

  @Override
  public void getAccount(String account) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("account", account);
    sendMessage(ReqProtocolType.REQ_ACCOUNT, jsonObject.toJSONString());
  }

  @Override
  public void getMarketState(QuoteParameter parameter) {
    if (parameter == null || parameter.getMarket() == null) {
      logger.info("param error:{}", parameter);
      return;
    }
    sendMessage(ReqProtocolType.REQ_MARKET_STATE, JSONObject.toJSONString(parameter));
  }

  @Override
  public void getAllSymbols(QuoteParameter parameter) {
    if (parameter == null || parameter.getMarket() == null) {
      logger.info("param error:{}", parameter);
      return;
    }
    sendMessage(ReqProtocolType.REQ_ALL_SYMBOLS, JSONObject.toJSONString(parameter));
  }

  @Override
  public void getAllSymbolNames(QuoteParameter parameter) {
    if (parameter == null || parameter.getMarket() == null) {
      logger.info("param error:{}", parameter);
      return;
    }
    sendMessage(ReqProtocolType.REQ_ALL_SYMBOL_NAMES, JSONObject.toJSONString(parameter));
  }

  @Override
  public void getBriefInfo(QuoteParameter parameter) {
    if (parameter.getSymbols() == null
        || parameter.getSymbols().isEmpty()
        || parameter.getMarket() == null
        || parameter.getMarket() == Market.ALL) {
      logger.info("param error:{}", parameter);
      return;
    }
    sendMessage(ReqProtocolType.REQ_BRIEF_INFO, JSONObject.toJSONString(parameter));
  }

  @Override
  public void getStockDetail(QuoteParameter parameter) {
    if (parameter.getSymbols() == null
        || parameter.getSymbols().isEmpty()
        || parameter.getMarket() == null
        || parameter.getMarket() == Market.ALL) {
      logger.info("param error:{}", parameter);
      return;
    }
    sendMessage(ReqProtocolType.REQ_STOCK_DETAIL, JSONObject.toJSONString(parameter));
  }

  @Override
  public void getTimeline(QuoteParameter parameter) {
    if (parameter.getSymbols() == null
        || parameter.getSymbols().isEmpty()
        || parameter.getPeriod() == null
        || parameter.getMarket() == null
        || parameter.getMarket() == Market.ALL) {
      logger.info("param error:{}", parameter);
      return;
    }
    sendMessage(ReqProtocolType.REQ_TIME_LINE, JSONObject.toJSONString(parameter));
  }

  @Override
  public void getHourTradingTimeline(QuoteParameter parameter) {
    if (parameter.getSymbols() == null
        || parameter.getSymbols().isEmpty()
        || parameter.getMarket() == null
        || parameter.getMarket() == Market.ALL) {
      logger.info("param error:{}", parameter);
      return;
    }
    sendMessage(ReqProtocolType.REQ_HOUR_TRADING_TIME_LINE, JSONObject.toJSONString(parameter));
  }

  @Override
  public void getKline(QuoteParameter parameter) {
    if (parameter.getSymbols() == null
        || parameter.getSymbols().isEmpty()
        || parameter.getMarket() == null
        || parameter.getMarket() == Market.ALL) {
      logger.info("param error:{}", parameter);
      return;
    }
    sendMessage(ReqProtocolType.REQ_KLINE, JSONObject.toJSONString(parameter));
  }

  @Override
  public void getTradeTick(QuoteParameter parameter) {
    if (parameter.getSymbols() == null
        || parameter.getSymbols().isEmpty()
        || parameter.getMarket() == null
        || parameter.getMarket() == Market.ALL) {
      logger.info("param error:{}", parameter);
      return;
    }
    sendMessage(ReqProtocolType.REQ_TRADE_TICK, JSONObject.toJSONString(parameter));
  }

  @Override
  public void subscribeQuote(Set<String> symbols) {
    if (!isConnected()) {
      notConnect();
      return;
    }
    channel.writeAndFlush(StompMessageUtil.buildSubscribeMessage(symbols));
    subscribeSymbols.addAll(symbols);
    logger.info("send subscribe quote message, symbols:{}", symbols);
  }

  @Override
  public void subscribeQuote(Set<String> symbols, List<String> focusKeys) {
    if (!isConnected()) {
      notConnect();
      return;
    }
    channel.writeAndFlush(StompMessageUtil.buildSubscribeMessage(symbols, new HashSet<>(focusKeys)));
    subscribeSymbols.addAll(symbols);
    logger.info("send subscribe quote message, symbols:{},focusKeys:{}", symbols, focusKeys);
  }

  @Override
  public void cancelSubscribeQuote(Set<String> symbols) {
    if (!isConnected()) {
      notConnect();
      return;
    }
    channel.writeAndFlush(StompMessageUtil.buildUnSubscribeMessage(symbols));
    subscribeSymbols.removeAll(symbols);
    logger.info("send cancel subscribe quote message, symbols:{}.", symbols);
  }

  @Override
  public void getSubscribedSymbols() {
    sendMessage(ReqProtocolType.REQ_SUB_SYMBOLS, null);
  }

  private void sendMessage(int reqType, String message) {
    if (!isConnected()) {
      notConnect();
      return;
    }
    logger.info("reqType:{},send message:{}", reqType, message);
    channel.writeAndFlush(StompMessageUtil.buildSendMessage(reqType, message));
  }

  private void notConnect() {
    logger.info("connection is closed.");
  }
}
