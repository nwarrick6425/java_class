package edu.nicholaidudakiwwarrick.advancedjava;

import edu.nicholaidudakiwwarrick.advancedjava.services.ServiceType;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockService;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockServiceException;
import edu.nicholaidudakiwwarrick.advancedjava.services.StockServiceFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * JUnit to test the <code>StockServiceFactory</code> class
 */
public class StockServiceFactoryTest {

    private StockService stockService;

    @Before
    public void setup() {
        try {
            stockService = StockServiceFactory.getInstance(ServiceType.BASIC);
        } catch(StockServiceException e) {
            e.getMessage();
        }
    }

    @Test
    public void testStockServiceFactoryNonNull(){
        assertFalse("The StockService instance was not null", stockService == null);
    }
}
