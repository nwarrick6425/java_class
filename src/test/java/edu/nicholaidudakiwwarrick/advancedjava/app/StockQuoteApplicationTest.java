package edu.nicholaidudakiwwarrick.advancedjava;

import edu.nicholaidudakiwwarrick.advancedjava.app.StockQuoteApplication;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for the Main method
 */
public class StockQuoteApplicationTest {
    private String[] args;

    @Before
    public void setup() {
        args = new String[]{"GOOG", "2018-06-08 00:00:00", "2018-06-15 00:00:00", "HALF_DAY"};
    }


    @Test(expected = NullPointerException.class)
    public void testMainNegative() {
        StockQuoteApplication.main(null);
    }

    @Test
    public void testMainPositive() {
        StockQuoteApplication.main(args);
    }
}
