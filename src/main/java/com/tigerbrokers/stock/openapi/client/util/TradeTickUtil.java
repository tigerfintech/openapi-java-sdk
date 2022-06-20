package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.stock.openapi.client.struct.enums.ParticipantCode;
import com.tigerbrokers.stock.openapi.client.struct.enums.USTradeCond;
import java.util.ArrayList;
import java.util.List;

/**
 * description: Created by bean on 2022/06/17
 */
public class TradeTickUtil {

  private TradeTickUtil(){}

  public static JSONObject decodeData(JSONObject jsonObject ) {

    JSONArray timeArray = jsonObject.getJSONArray("times");
    JSONArray pricesArray = jsonObject.getJSONArray("prices");
    JSONArray partCodeArray = jsonObject.getJSONArray("partCode");
    if (timeArray != null && timeArray.size() > 0) {
      long previousTime = 0;
      double priceBase = jsonObject.getLongValue("priceBase");
      double denominator = Math.pow(10, jsonObject.getIntValue("priceOffset"));
      for (int i = 0; i < timeArray.size(); i++) {
        // recover time: time[i] = time[i] + time[i-1]
        previousTime += timeArray.getLongValue(i);
        timeArray.set(i, previousTime);
        // recover price: price[i] = (priceBase + price[i]) / 10^priceOffset
        pricesArray.set(i, (priceBase + pricesArray.getDoubleValue(i)) / denominator);
      }
    }
    if (partCodeArray != null && partCodeArray.size() > 0) {
      for (int i = 0; i < partCodeArray.size(); i++) {
        partCodeArray.set(i, ParticipantCode.getParticipantByCode(partCodeArray.getString(i)));
      }
    }
    String cond = (String)jsonObject.remove("cond");
    if (cond != null || cond.length() > 0) {
      List<USTradeCond> tradeCondList = new ArrayList<>(cond.length());
      for (int i = 0; i < cond.length(); i++) {
        tradeCondList.add(i, USTradeCond.getTradeCondByCode(cond.charAt(i)));
      }
      jsonObject.put("tradeCond", tradeCondList);
    }
    return jsonObject;
  }
}
