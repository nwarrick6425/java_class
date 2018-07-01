package edu.nicholaidudakiwwarrick.advancedjava.services;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Factory pattern class to instantiate service objects
 *
 * @author Nicholai Dudakiw-Warrick
 */
@Immutable
public final class ServiceFactory {

    /**
     * Private constructor to prevent instantiation of static factory instances
     */
    private ServiceFactory() {
    }

    /**
     * @param type {@code ServiceType} enum that specifies the type of {@code StockService} to return
     * @return the {@code BasicStockService} or {@code DatabaseStockService} instance as a reference to
     * the {@code StockService} interface
     */
    public static final StockService getStockServiceInstance(ServiceType type) throws StockServiceException {
        if (type.equals(ServiceType.BASIC)) {
            return new BasicStockService();
        } else if (type.equals(ServiceType.DATABASE)) {
            return new DatabaseStockService();
        } else {
            throw new StockServiceException("Error: Invalid ServiceType");
        }

    }

    /**
     * @return a {@code DatabasePersonService} instance as a reference to a {@code PersonService} interface
     */
    public static final PersonService getPersonServiceInstance() {
        return new DatabasePersonService();
    }
}
