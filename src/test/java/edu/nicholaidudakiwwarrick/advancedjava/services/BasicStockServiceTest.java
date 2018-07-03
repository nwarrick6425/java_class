package edu.nicholaidudakiwwarrick.advancedjava.services;

import edu.nicholaidudakiwwarrick.advancedjava.model.StockQuote;
import edu.nicholaidudakiwwarrick.advancedjava.util.IntervalEnum;
import org.joda.time.DateTime;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        try {
            basicStockService = ServiceFactory.getStockServiceInstance(ServiceType.BASIC);
        } catch (StockServiceException e) {
            e.getMessage();
        }
    }

    @Test
    public void testGetQuote() throws StockServiceException {
        StockQuote stockQuote = basicStockService.getQuote(startSymbol, startDate);
        assertEquals("The StockQuote symbol created by getQuote is correct", startSymbol, stockQuote.getSymbol());
        assertEquals("The StockQuote price created by getQuote is correct", startPrice, stockQuote.getPrice());
    }

    @Test
    public void testGetQuoteListNumberOfDaysPositive() throws StockServiceException {
        List<StockQuote> stockList = basicStockService.getQuote(startSymbol, startDate, endDate);

        assertTrue("The number of days is 5", stockList.size() == 5);
    }

    @Test
    public void testGetQuoteListNumberOfDaysNegative() throws StockServiceException {
        List<StockQuote> stockList = basicStockService.getQuote(startSymbol, startDate, endDate);

        assertFalse("The number of days is not greater than or less than 5",
                stockList.size() < 5 || stockList.size() > 5);
    }

    @Test
    public void testGetQuoteIntervalPositive() {
        List<StockQuote> stockList = basicStockService.getQuote(startSymbol, startDate, endDate, IntervalEnum.HOURLY);
        assertTrue("The number of stock quotes generated is 120 for 5 days", stockList.size() == 120);
    }

    @Test
    public void testGetQuoteIntervalNegative() {
        List<StockQuote> stockList = basicStockService.getQuote(startSymbol, startDate, endDate, IntervalEnum.HOURLY);
        assertFalse("The number of stock quotes generated is not greater than or less than 120 for 5 days",
                stockList.size() < 120 || stockList.size() > 120);
    }
}
