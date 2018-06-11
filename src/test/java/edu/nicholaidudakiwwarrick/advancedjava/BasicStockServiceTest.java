package edu.nicholaidudakiwwarrick.advancedjava;

import org.joda.time.DateTime;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

/**
 * JUnit tests for the <code>BasicStockService</code> class
 */
public class BasicStockServiceTest {

    private String startSymbol;
    private BigDecimal startPrice;
    private DateTime startDate;
    private DateTime endDate;
    private StockService basicStockService;

    @Before
    public void setup() {
        startSymbol = "GOOG";
        startPrice = new BigDecimal(100);
        startDate = new DateTime().now();
        endDate = startDate.plusDays(5);
        basicStockService = StockServiceFactory.newInstance();
    }

    @Test
    public void testGetQuote() {
        StockQuote stockQuote = basicStockService.getQuote(startSymbol, startDate);
        assertEquals("The StockQuote symbol created by getQuote is correct", startSymbol, stockQuote.getSymbol());
        assertEquals("The StockQuote price created by getQuote is correct", startPrice, stockQuote.getPrice());
    }

    @Test
    public void testGetQuoteListNumberOfDaysPositive() {
        BasicStockService basicStockService = BasicStockService.INSTANCE;
        List<StockQuote> stockList = basicStockService.getQuote(startSymbol, startDate, endDate);

        assertTrue("The number of days is 5", stockList.size() == 5);
    }
}
