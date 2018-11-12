package com.tigerbrokers.stock.openapi.client.socket;

import com.tigerbrokers.stock.openapi.client.struct.param.AssetParameter;
import com.tigerbrokers.stock.openapi.client.struct.param.OrderParameter;
import com.tigerbrokers.stock.openapi.client.struct.param.PositionParameter;

/**
 * Description:
 * Created by lijiawen on 2018/06/15.
 */
public interface TradeAsyncApi {

  /**
   * 获取订单号
   *
   * @param account 账户
   */
  int getOrderNo(String account);

  /**
   * 异步获取订单号
   *
   * @param account 账户
   */
  String getOrderNoAsync(String account);

  /**
   * 预览订单
   *
   * @param orderParameter 订单参数
   */
  String previewOrder(OrderParameter orderParameter);

  /**
   * 创建订单
   *
   * @param orderParameter 订单参数
   */
  String placeOrder(OrderParameter orderParameter);

  /**
   * 取消订单
   *
   * @param account 账户
   * @param orderId 订单号
   */
  String cancelOrder(String account, int orderId);

  /**
   * 修改订单
   *
   * @param orderParameter 订单参数
   */
  String modifyOrder(OrderParameter orderParameter);

  /**
   * 获取未完成订单
   */
  String getOpenOrders();

  /**
   * 获取持仓
   *
   * @param position 持仓参数
   */
  String getPosition(PositionParameter position);

  /**
   * 获取资产
   *
   * @param asset 资产参数
   */
  String getAsset(AssetParameter asset);

  /**
   * 获取账户信息
   *
   * @param account 账户
   */
  String getAccount(String account);
}
