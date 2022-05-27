package com.tigerbrokers.stock.openapi.client.https.validator;

import com.tigerbrokers.stock.openapi.client.TigerApiException;
import com.tigerbrokers.stock.openapi.client.https.domain.ApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.BatchApiModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.contract.model.ContractsModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.CorporateActionModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialDailyModel;
import com.tigerbrokers.stock.openapi.client.https.domain.financial.model.FinancialReportModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContinuousContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContractByConCodeModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureContractByExchCodeModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureCurrentContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureExchangeModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureKlineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureRealTimeQuoteModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTickModel;
import com.tigerbrokers.stock.openapi.client.https.domain.future.model.FutureTradingDateModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainModel;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionChainV3Model;
import com.tigerbrokers.stock.openapi.client.https.domain.option.model.OptionExpirationModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteContractModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteDepthModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteHistoryTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteKlineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteMarketModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteStockTradeModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteTimelineModel;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.model.QuoteTradeTickModel;
import com.tigerbrokers.stock.openapi.client.https.domain.trade.model.TradeOrderModel;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liutongping
 * @date 2022/5/23 下午7:52
 */
public class ValidatorManager {
  private Map<Class<? extends ApiModel>, RequestValidator> validatorMap = new HashMap<>();

  private ValidatorManager() {
    validatorMap.put(BatchApiModel.class, new BatchRequestValidator());

    ContractRequestValidator contractRequestValidator = new ContractRequestValidator();
    validatorMap.put(ContractModel.class, contractRequestValidator);
    validatorMap.put(ContractsModel.class, contractRequestValidator);

    QuoteRequestValidator quoteRequestValidator = new QuoteRequestValidator();
    validatorMap.put(QuoteDepthModel.class, quoteRequestValidator);
    validatorMap.put(QuoteMarketModel.class, quoteRequestValidator);
    validatorMap.put(QuoteStockTradeModel.class, quoteRequestValidator);
    validatorMap.put(QuoteContractModel.class, quoteRequestValidator);
    validatorMap.put(QuoteTimelineModel.class, quoteRequestValidator);
    validatorMap.put(QuoteHistoryTimelineModel.class, quoteRequestValidator);
    validatorMap.put(QuoteTradeTickModel.class, quoteRequestValidator);

    FutureContractRequestValidator futureContractRequestValidator = new FutureContractRequestValidator();
    validatorMap.put(FutureContinuousContractModel.class, futureContractRequestValidator);
    validatorMap.put(FutureContractByConCodeModel.class, futureContractRequestValidator);
    validatorMap.put(FutureContractByExchCodeModel.class, futureContractRequestValidator);
    validatorMap.put(FutureCurrentContractModel.class, futureContractRequestValidator);

    validatorMap.put(FutureExchangeModel.class, new FutureExchangeRequestValidator());
    FutureQuoteRequestValidator futureQuoteRequestValidator = new FutureQuoteRequestValidator();
    validatorMap.put(FutureRealTimeQuoteModel.class, futureQuoteRequestValidator);
    validatorMap.put(FutureTickModel.class, futureQuoteRequestValidator);
    validatorMap.put(FutureTradingDateModel.class, futureQuoteRequestValidator);

    OptionChainRequestValidator optionChainRequestValidator = new OptionChainRequestValidator();
    validatorMap.put(OptionChainModel.class, optionChainRequestValidator);
    validatorMap.put(OptionChainV3Model.class, optionChainRequestValidator);

    validatorMap.put(OptionExpirationModel.class, new OptionExpirationRequestValidator());

    validatorMap.put(TradeOrderModel.class, new PlaceOrderRequestValidator());
    KlineRequestValidator klineRequestValidator = new KlineRequestValidator();
    validatorMap.put(QuoteKlineModel.class, klineRequestValidator);
    validatorMap.put(FutureKlineModel.class, klineRequestValidator);

    validatorMap.put(CorporateActionModel.class, new CorporateActionRequestValidator());
    validatorMap.put(FinancialDailyModel.class, new FinancialDailyRequestValidator());
    validatorMap.put(FinancialReportModel.class, new FinancialReportRequestValidator());

  }

  private static ValidatorManager single = new ValidatorManager();

  public static ValidatorManager getInstance() {
    return single;
  }

  public void validate(ApiModel apiModel) throws TigerApiException {
    if (apiModel == null) {
      return;
    }
    RequestValidator validator = validatorMap.get(apiModel.getClass());
    if (validator == null) {
      return;
    }
    validator.validate(apiModel);
  }
}
