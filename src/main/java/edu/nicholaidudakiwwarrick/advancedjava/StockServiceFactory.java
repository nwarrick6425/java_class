package edu.nicholaidudakiwwarrick.advancedjava;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Factory to create instances of <code>BasicStockService</code>
 *
 * @author Nicholai Dudakiw-Warrick
 */
@Immutable
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
