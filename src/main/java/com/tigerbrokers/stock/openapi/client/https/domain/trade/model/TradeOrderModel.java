package com.tigerbrokers.stock.openapi.client.https.domain.trade.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.struct.TagValue;
import com.tigerbrokers.stock.openapi.client.struct.enums.ActionType;
import com.tigerbrokers.stock.openapi.client.struct.enums.AttachType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TimeInForce;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class TradeOrderModel extends ApiModel {

  /**
   * 全局唯一的订单ID
   */
  private Long id;

  /**
   * 订单ID(用户账户内唯一)
   */
  @JSONField(name = "order_id")
  private Integer orderId;

  /**
   * 交易账户
   */
  private String account;

  @JSONField(name = "secret_key")
  private String secretKey;
  /**
   * 底层股票代码
   */
  private String symbol;
  /**
   * 合约类型 (STK 股票 OPT 美股期权 WAR 港股涡轮 IOPT 港股牛熊证)
   */
  @JSONField(name = "sec_type")
  private SecType secType;
  /**
   * 交易方向 BUY/SELL
   */
  private ActionType action;
  /**
   * 货币
   */
  private Currency currency;
  /**
   * 订单数量
   */
  @JSONField(name = "total_quantity")
  private Integer totalQuantity;
  /**
   * 有效时间
   * DAY：当日有效
   * GTC：取消前有效
   * GTD：指定日期前有效
   * AUC：集合竞价（ 港股，订单类型为 MTL，需要填 limitPrice）
   */
  @JSONField(name = "time_in_force")
  private TimeInForce timeInForce;
  /**
   * 订单类型
   */
  @JSONField(name = "order_type")
  private OrderType orderType;
  /**
   * 价格元素：
   * 无 : 市价单
   * limitPrice : 限价单价格
   * auxPrice : 止损单价格
   * limitPrice，auxPrice : 止损限价单
   * auxPrice : 跟踪止损单
   */
  @JSONField(name = "limit_price")
  private Double limitPrice;
  /**
   * 股票止损价。当 order_type 为STP,STP_LMT时该参数必需，当 order_type 为 TRAIL时,aux_price为跟踪额
   */
  @JSONField(name = "aux_price")
  private Double auxPrice;

  /**
   * 跟踪止损单 - 百分比。当 order_type 为 TRAIL时,aux_price和trailing_percent两者互斥
   */
  @JSONField(name = "trailing_percent")
  private Double trailingPercent;

  /**
   * true 允许盘前盘后交易(美股专属)
   */
  @JSONField(name = "outside_rth")
  private Boolean outsideRth;
  /**
   * 市场
   */
  private String market;
  /**
   * 交易所
   */
  private String exchange;
  /**
   * 期权、涡轮、牛熊证字段
   */
  private String expiry;
  /**
   * 底层价格(期权、涡轮、牛熊证专属)
   */
  private String strike;
  /**
   * 期权方向 PUT/CALL(期权、涡轮、牛熊证专属)
   */
  private String right;
  /**
   * 1手单位(期权、涡轮、牛熊证专属)
   */
  private Float multiplier;

  @JSONField(name = "local_symbol")
  private String localSymbol;

  @JSONField(name = "alloc_accounts")
  private List<String> allocAccounts;
  @JSONField(name = "alloc_shares")
  private List<Double> allocShares;

  @JSONField(name = "algo_strategy")
  private String algoStrategy;
  @JSONField(name = "algo_params")
  private List<TagValue> algoParams;

  private String source;

  /**
   * 附加订单类型：
   * PROFIT
   * LOSS
   */
  @JSONField(name = "attach_type")
  private AttachType attachType;

  /**
   * 止盈订单
   */
  @JSONField(name = "profit_taker_orderId")
  private Integer profitTakerOrderId;
  @JSONField(name = "profit_taker_price")
  private Double profitTakerPrice;
  @JSONField(name = "profit_taker_tif")
  private TimeInForce profitTakerTif;
  @JSONField(name = "profit_taker_rth")
  private Boolean profitTakerRth;

  /**
   * 止损订单
   */
  @JSONField(name = "stop_loss_orderId")
  private Integer stopLossOrderId;
  @JSONField(name = "stop_loss_price")
  private Double stopLossPrice;
  @JSONField(name = "stop_loss_tif")
  private TimeInForce stopLossTif;
}
