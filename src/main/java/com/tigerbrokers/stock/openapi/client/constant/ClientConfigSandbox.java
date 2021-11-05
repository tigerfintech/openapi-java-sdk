package com.tigerbrokers.stock.openapi.client.constant;

/**
 * @author liutongping
 * @version 1.0
 * @description:
 * @date 2021/11/5 下午4:11
 */
public final class ClientConfigSandbox {

  public static String env = "sandbox";

  /**
   * http接口地址
   */
  public static String serverUrl = "https://openapi-sandbox.itiger.com/gateway";

  /**
   * 消息推送接口地址
   */
  public static String socketServerUrl = "wss://openapi-sandbox.itiger.com:8885";

  /**
   * 参数必填,tigerId : 形如2015xxxx,查询地址：https://www.itiger.com/openapi/info
   */
  public static String tigerId = null;

  /**
   * 参数必填,yourPrivateKey : 开发者注册时生成的私钥 生成方式 : https://quant.itiger.com/openapi/java-docs/zh-cn/docs/intro/quickstart.html
   * 如果报错,可以比对下下面的私钥实例 testPrivateKey 或者 testPrivateKey1(去除了头尾、换行和空格)
   */
  public static String privateKey = null;

  /**
   * institutional trader private key 机构交易员专有密钥
   */
  public static String secretKey = null;
}
