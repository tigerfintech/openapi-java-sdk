package com.tigerbrokers.stock.openapi.client.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;

/**
 * Description:
 * Created by liutongping on 2021/11/18
 */
public class FastJsonBooleanDeserializer implements ObjectDeserializer {
  @Override
  public Boolean deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
    JSONLexer lexer = parser.lexer;

    Boolean boolObj;
    try {
      if (lexer.token() == 6) {
        lexer.nextToken(16);
        boolObj = Boolean.TRUE;
      } else if (lexer.token() == 7) {
        lexer.nextToken(16);
        boolObj = Boolean.FALSE;
      } else if (lexer.token() == 2) {
        int intValue = lexer.intValue();
        lexer.nextToken(16);
        if (intValue == 0) {
          boolObj = Boolean.FALSE;
        } else {
          boolObj = Boolean.TRUE;
        }
      } else {
        Object value = parser.parse();
        if (value == null) {
          return null;
        }

        boolObj = TypeUtils.castToBoolean(value);
      }
    } catch (Exception var7) {
      throw new JSONException("parseBoolean error, field : " + fieldName, var7);
    }
    return boolObj;
  }

  @Override
  public int getFastMatchToken() {
    return JSONToken.TRUE;
  }
}
