package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteDepthModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteHistoryTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteMarketModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteStockTradeModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteSymbolModel;
import com.tigerbrokers.stock.openapi.client.struct.enums.Market;
import com.tigerbrokers.stock.openapi.client.struct.enums.SecType;
import com.tigerbrokers.stock.openapi.client.struct.enums.TigerApiCode;
import com.tigerbrokers.stock.openapi.client.util.StringUtils;

/**
 * Description: created by liutongping on 2022/5/24
 */
public class QuoteRequestValidator implements RequestValidator<ApiModel> {

  @Override
  public void validate(ApiModel model) throws TigerApiException {

    if (model instanceof QuoteStockTradeModel) {
      QuoteStockTradeModel quoteStockTradeModel = (QuoteStockTradeModel) model;
      if (quoteStockTradeModel.getSymbols() == null || quoteStockTradeModel.getSymbols().isEmpty()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbols");
      }
    } else if (model instanceof QuoteDepthModel) {
      QuoteDepthModel quoteDepthModel = (QuoteDepthModel) model;
      if (quoteDepthModel.getSymbols() == null || quoteDepthModel.getSymbols().isEmpty()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbols");
      }
      if (StringUtils.isEmpty(quoteDepthModel.getMarket())) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "market");
      }
      try {
        Market market = Market.valueOf(quoteDepthModel.getMarket().toUpperCase());
        quoteDepthModel.setMarket(market.name());
      } catch (Exception e) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_VALUE_ERROR, "market");
      }
    } else if (model instanceof QuoteMarketModel) {
      // interface: ALL_SYMBOLS(need market or package_name) ALL_SYMBOL_NAMES(must have market)  MARKET_STATE(must have market)
      // in the server side Market Enume is StockMarket
      QuoteMarketModel quoteMarketModel = (QuoteMarketModel) model;
      if (null == quoteMarketModel.getPackageName() && null == quoteMarketModel.getMarket()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "market");
      }
    } else if (model instanceof QuoteSymbolModel) {
      QuoteSymbolModel quoteSymbolModel = (QuoteSymbolModel) model;
      if (quoteSymbolModel.getSymbols() == null || quoteSymbolModel.getSymbols().isEmpty()) {
        throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "symbols");
      }
      if (model instanceof QuoteContractModel) {
        QuoteContractModel quoteContractModel = (QuoteContractModel)model;
        SecType secType = quoteContractModel.getSecType();
        if (secType == null) {
          throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "sec_type");
        }
        if (SecType.OPT != secType && SecType.WAR != secType && SecType.IOPT != secType) {
          throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_CONCTRACT_SECTYPE_ERROR, "sec_type");
        }
        if (SecType.OPT == secType && StringUtils.isEmpty(quoteContractModel.getExpiry())) {
          throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "expiry");
        }
      } else if (model instanceof QuoteHistoryTimelineModel) {
        if (StringUtils.isEmpty(((QuoteHistoryTimelineModel)model).getDate())) {
          throw new TigerApiException(TigerApiCode.HTTP_BIZ_PARAM_EMPTY_ERROR, "date");
        }
      }
    }
  }
}
