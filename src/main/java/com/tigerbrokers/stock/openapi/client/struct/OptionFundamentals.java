package com.tigerbrokers.stock.openapi.client.struct;

import lombok.Data;

/**
 * 期权基本面信息
 * 作者：ltc
 * 时间：2019/08/27
 */
@Data
public class OptionFundamentals {

  /**
   * delta
   */
  String Delta;
  /**
   * gamma
   */
  String Gamma;
  /**
   * theta
   */
  String Theta;
  /**
   * vega
   */
  String Vega;
  /**
   * 时间价值
   */
  String timeValue;
  /**
   * 溢价率
   */
  String premiumRate;
  /**
   * 买入盈利率
   */
  String profitRate;
  /**
   * 隐含波动率
   */
  String volatility;
  /**
   * 杠杆率
   */
  String leverage;
  /**
   * 内在价值
   */
  String insideValue;
  /**
   * 历史波动率
   */
  String historyVolatility;
  /**
   * 未平合约数
   */
  String openInterest;
}
