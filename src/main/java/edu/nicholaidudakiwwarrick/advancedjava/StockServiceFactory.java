package edu.nicholaidudakiwwarrick.advancedjava;

/**
 * Factory to create instances of <code>BasicStockService</code>
 *
 * @author Nicholai Dudakiw-Warrick
 */
public final class StockServiceFactory {

    /**
     * Private constructor to prevent instantiation of static factory instances
     */
    private StockServiceFactory() {
    }

    /**
     * @return the <code>BasicStockService</code> instance as a reference to
     *          the <code>StockService</code> interface
     */
    public static final StockService newInstance() {
        return new BasicStockService();
    }
}
