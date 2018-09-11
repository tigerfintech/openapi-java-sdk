package com.tigerbrokers.stock.openapi.client.https.request;



import com.tigerbrokers.stock.openapi.client.https.response.TigerResponse;
import java.util.Map;

/**
 * 请求接口。
 * Created by lijiawen on 2018/04/25.
 */
public interface TigerRequest<T extends TigerResponse> {

  /**
   * 获取API名称。
   *
   * @return API名称
   */
  String getApiMethodName();

  /**
   * 获取所有的Key-Value形式的文本请求参数集合。其中：
   * <ul>
   * <li>Key: 请求参数名</li>
   * <li>Value: 请求参数值</li>
   * </ul>
   *
   * @return 文本请求参数集合
   */
  Map<String, String> getTextParams();

  /**
   * 得到当前接口的版本
   *
   * @return API版本
   */
  String getApiVersion();

  /**
   * 设置当前API的版本信息
   *
   * @param apiVersion API版本
   */
  void setApiVersion(String apiVersion);

  /**
   * 返回通知地址
   */
  String getNotifyUrl();

  /**
   * 设置通知地址
   */
  void setNotifyUrl(String notifyUrl);
}
