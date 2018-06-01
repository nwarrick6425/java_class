package edu.nicholaidudakiwwarrick.advancedjava;

import java.math.BigDecimal;

public final class BasicStockService implements StockService {

    public static final BasicStockService INSTANCE = new BasicStockService();
    private static BigDecimal startPrice = new BigDecimal(100);

    protected BasicStockService() {
    }

    public final StockQuote getQuote(String symbol) {
        return new StockQuote(symbol, startPrice);
    }
}
