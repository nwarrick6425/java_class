package edu.nicholaidudakiwwarrick.advancedjava.services;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit to test the {@code ServiceFactory} class
 */
public class ServiceFactoryTest {

    private StockService stockService;
    private StockService stockService2;
    private StockService stockService3;
    private PersonService personService;

    @Before
    public void setup() throws StockServiceException {
        stockService = ServiceFactory.getStockServiceInstance(ServiceType.BASIC);
        stockService2 = ServiceFactory.getStockServiceInstance(ServiceType.DATABASE);
        stockService3 = ServiceFactory.getStockServiceInstance(ServiceType.WEB);
        personService = ServiceFactory.getPersonServiceInstance();
    }

    @Test
    public void testServiceFactoryNonNull(){
        assertFalse("The BasicStockService instance is null", stockService == null);
        assertFalse("The DatabaseStockService instance is null", stockService2 == null);
        assertFalse("The WebStockService instance is null", stockService3 == null);
        assertFalse("The PersonService instance is null", personService == null);
    }

    @Test
    public void testServiceFactoryInstancePositive() {
        assertTrue("The object returned by getStockServiceInstance is not an instance of StockService",
                stockService instanceof StockService);
        assertTrue("The object returned by getStockServiceInstance is not an instance of StockService",
                stockService2 instanceof StockService);
        assertTrue("The object returned by getStockServiceInstance is not an instance of StockService",
                stockService3 instanceof StockService);
        assertTrue("The object returned by getPersonServiceInstance is not an instance of PersonService",
                personService instanceof PersonService);
    }

    @Test
    public void testServiceFactoryInstanceNegative() {
        assertFalse("The object returned by getStockServiceInstance is an instance of Calendar",
                stockService instanceof Calendar);
        assertFalse("The object returned by getStockServiceInstance is an instance of Calendar",
                stockService2 instanceof Calendar);
        assertFalse("The object returned by getStockServiceInstance is an instance of Calendar",
                stockService3 instanceof Calendar);
        assertFalse("The object returned by getPersonServiceInstance is an instance of Calendar",
                personService instanceof Calendar);
    }
}
