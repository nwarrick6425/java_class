package edu.nicholaidudakiwwarrick.advancedjava.services;

import edu.nicholaidudakiwwarrick.advancedjava.util.DatabaseInitializationException;
import edu.nicholaidudakiwwarrick.advancedjava.util.DatabaseUtils;
import edu.nicholaidudakiwwarrick.advancedjava.util.IntervalEnum;
import org.joda.time.DateTime;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DatabaseStockServiceTest {

    private DatabaseStockService databaseStockService;
    private DateTime startDate;
    private DateTime endDate;
    private DateTime knownDate;
    private IntervalEnum interval;
    private String symbol;
    private static final int NUMBER_OF_DAYS = 100;

    @Before
    public final void setup() throws DatabaseInitializationException, StockServiceException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        databaseStockService = (DatabaseStockService) ServiceFactory.getStockServiceInstance(ServiceType.DATABASE);
        startDate = new DateTime(2015, 2, 3, 0, 0);
        endDate = DateTime.now();
        knownDate = new DateTime(2016, 6, 9, 17 ,0, 1);
        symbol = "GOOG";
        interval = IntervalEnum.DAILY;
    }

    @Test
    public final void testGetQuoteSingleArgPositive() throws StockServiceException {
        assertTrue("Stock symbol returned from getQuote does not equal test stock symbol string for known date",
                databaseStockService.getQuote(symbol, knownDate).getSymbol().equals(symbol));
    }

    @Test(expected = StockServiceException.class)
    public final void testGetQuoteSingleArgNegative() throws StockServiceException {
        databaseStockService.getQuote(symbol, startDate.minusDays(NUMBER_OF_DAYS));
    }

    @Test
    public final void testGetQuoteTripleArgPositive() throws StockServiceException {
        assertTrue("StockQuotes returned do not equal the passed symbol",
                databaseStockService.getQuote(symbol, startDate, endDate).get(0).getSymbol().equals(symbol));
    }

    @Test(expected = StockServiceException.class)
    public final void testGetQuoteTripleArgNegative() throws StockServiceException {
        databaseStockService.getQuote("BLAH", startDate, endDate);
    }

    @Test(expected = StockServiceException.class)
    public final void testGetQuoteTripleArgTimeNegative() throws StockServiceException {
        databaseStockService.getQuote(symbol, endDate, startDate);
    }
}
