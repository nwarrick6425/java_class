package edu.nicholaidudakiwwarrick.advancedjava;

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
        stockService = StockServiceFactory.newInstance();
    }

    @Test
    public void testStockServiceFactoryNonNull(){
        assertFalse("The StockService instance was not null", stockService == null);
    }
}
