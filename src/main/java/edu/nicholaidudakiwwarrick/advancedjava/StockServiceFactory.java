package edu.nicholaidudakiwwarrick.advancedjava;

public final class StockServiceFactory {

    private StockServiceFactory() {
    }

    public static final StockService newInstance(String symbol) {
        return new BasicStockService();
    }
}
