package edu.nicholaidudakiwwarrick.advancedjava.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * JUnit to test the <code>ServiceFactory</code> class
 */
public class ServiceFactoryTest {

    private StockService stockService;

    @Before
    public void setup() {
        try {
            stockService = ServiceFactory.getStockServiceInstance(ServiceType.BASIC);
        } catch(StockServiceException e) {
            e.getMessage();
        }
    }

    @Test
    public void testStockServiceFactoryNonNull(){
        assertFalse("The StockService instance was not null", stockService == null);
    }
}
