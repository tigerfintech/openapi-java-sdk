package com.tigerbrokers.stock.openapi.client.socket;

import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 * Created by lijiawen on 2018/06/15.
 */
public interface TradeApiCallback {

  void orderNoEnd(JSONObject jsonObject);

  void previewOrderEnd(JSONObject jsonObject);

  void placeOrderEnd(JSONObject jsonObject);

  void cancelOrderEnd(JSONObject jsonObject);

  void modifyOrderEnd(JSONObject jsonObject);

  void getAssetEnd(JSONObject jsonObject);

  void getPositionEnd(JSONObject jsonObject);

  void getAccountEnd(JSONObject jsonObject);

}
