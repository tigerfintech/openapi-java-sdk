package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.AttachType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Currency;
import com.tigerbrokers.stock.openapi.client.struct.enums.OrderType;
import com.tigerbrokers.stock.openapi.client.struct.enums.Right;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

/**
 * Description: created by liutongping on 2021/11/5
 */
public class PlaceOrderRequestValidator implements RequestValidator<TradeOrderModel> {

  @Override
  public void validate(TradeOrderModel model) throws TigerApiException {
    if (StringUtils.isEmpty(model.getAccount())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "account");
    }
    if (StringUtils.isEmpty(model.getSymbol())) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbol");
    }
    if (model.getSecType() == null) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "sec_type");
    }
    if (model.getAction() == null) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "action");
    }
    if (model.getOrderType() == null) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "order_type");
    }
    if (model.getTotalQuantity() == null) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "total_quantity");
    } else if (model.getTotalQuantity() <= 0) {
      throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_VALUE_ERROR, "total_quantity");
    }

    if (model.getOrderType() == OrderType.LMT || model.getOrderType() == OrderType.STP_LMT) {
      if (model.getLimitPrice() == null) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "limit_price");
      }
    }

    if (model.getOrderType() == OrderType.STP || model.getOrderType() == OrderType.STP_LMT) {
      if (model.getAuxPrice() == null) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "aux_price");
      }
    }

    if (model.getSecType() == SecType.CASH) {
      if (!model.getSymbol().contains(".") && model.getCurrency() == null) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "currency");
      }
    } else if (model.getCurrency() == Currency.HKD
        && (model.getSecType() == SecType.WAR || model.getSecType() == SecType.IOPT)) {
      if (!StringUtils.isEmpty(model.getLocalSymbol()) && !LOCAL_SYMBOL_PATTERN.matcher(
          model.getLocalSymbol()).matches()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_VALUE_ERROR, "local_symbol");
      }
    }

    if (model.getAttachType() == AttachType.BRACKETS || model.getAttachType() == AttachType.PROFIT) {
      if (model.getProfitTakerPrice() == null) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "profit_taker_price");
      }
      if (model.getProfitTakerTif() == null) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "profit_taker_tif");
      }
      if (model.getProfitTakerRth() == null) {
        model.setProfitTakerRth(model.getOutsideRth() == null ? Boolean.TRUE : model.getOutsideRth());
      }
    }

    if (model.getAttachType() == AttachType.BRACKETS || model.getAttachType() == AttachType.LOSS) {
      if (model.getStopLossPrice() == null) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "stop_loss_price");
      }
      if (model.getStopLossTif() == null) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "stop_loss_tif");
      }
    }
  }
}
