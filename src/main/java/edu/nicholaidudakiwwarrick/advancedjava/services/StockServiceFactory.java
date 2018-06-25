package edu.nicholaidudakiwwarrick.advancedjava.services;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Factory pattern class to instantiate service objects
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
     * @param type {@code ServiceType} enum that specifies the type of {@code StockService} to return
     * @return the {@code BasicStockService} instance as a reference to
     * the {@code StockService} interface
     */
    public static final StockService getInstance(ServiceType type) throws StockServiceException {
        if (type.equals(ServiceType.BASIC)) {
            return new BasicStockService();
        } else if (type.equals(ServiceType.DATABASE)) {
            return new DatabaseStockService();
        } else {
            throw new StockServiceException("Error: Invalid ServiceType");
        }

    }
}
