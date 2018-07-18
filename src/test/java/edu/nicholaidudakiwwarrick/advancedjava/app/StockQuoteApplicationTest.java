package edu.nicholaidudakiwwarrick.advancedjava.app;

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
        args = new String[]{"-symbol",
                "GOOG",
                "-start",
                "2016-06-09 00:00:00",
                "-end",
                "2016-06-23 00:00:00",
                "-interval",
                "DAILY"};
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
