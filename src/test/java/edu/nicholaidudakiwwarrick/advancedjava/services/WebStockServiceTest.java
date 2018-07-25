package edu.nicholaidudakiwwarrick.advancedjava.services;

import edu.nicholaidudakiwwarrick.advancedjava.model.StockQuote;
import edu.nicholaidudakiwwarrick.advancedjava.util.IntervalEnum;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the {@code WebStockService} class
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class WebStockServiceTest {

    private StockService service;
    private static final String symbol = "AAPL";
    private static DateTime start;
    private static DateTime end;
    private static final int NUMBER_OF_DAYS = 100;
    private static IntervalEnum interval;

    /**
     * Sets up common logic for all tests
     *
     * @throws StockServiceException if an incorrect {@code ServiceType} is passed to the {@code ServiceFactory}
     */
    @Before
    public void setup() throws StockServiceException {
        service = ServiceFactory.getStockServiceInstance(ServiceType.WEB);
        start = DateTime.now().minusDays(NUMBER_OF_DAYS).withTimeAtStartOfDay();
        end = DateTime.now().withTimeAtStartOfDay();
        interval = IntervalEnum.DAILY;
    }

    /**
     * Tests that the stock symbol retrieved from getQuote is the same as what was passed
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public void testGetQuoteDoubleArgStockSymbolPositive() throws StockServiceException {
        assertTrue("Symbol returned from getQuote is not the same that was passed",
                service.getQuote(symbol, end).getSymbol().equals(symbol));
    }

    /**
     * Tests that the stock symbol returned is not something other than what was passed
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public void testGetQuoteDoubleArgStockSymbolNegative() throws StockServiceException {
        assertFalse("Symbol returned is the same as the invalid symbol",
                service.getQuote(symbol, end).getSymbol().equals("BURG"));
    }

    /**
     * Tests that the {@code DateTime} returned is not after the date passed
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public void testGetQuoteDoubleArgDatePositive() throws StockServiceException {
        assertTrue("The date returned is after the passed date",
                !service.getQuote(symbol, end).getDate().isAfterNow());
    }

    /**
     * Tests that the {@code DateTime} returned is not the same as a future date
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public void testGetQuoteDoubleArgDateNegative() throws StockServiceException {
        assertFalse("The date returned is the same as a future time",
                service.getQuote(symbol, end).getDate().getMillis() == (DateTime.now().getMillis() + 2600));
    }

    /**
     * Tests that the stock symbol retrieved from getQuote is the same as what was passed
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolPositive() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, start, end);
        for (StockQuote q : qList) {
            assertTrue("Symbol returned from getQuote is not same as symbol passed as argument", q.getSymbol().equals(symbol));
        }
    }

    /**
     * Tests that the stock symbol returned is not something other than what was passed
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public final void testGetQuoteTripleArgStockSymbolNegative() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, start, end);
        for (StockQuote q : qList) {
            assertFalse("Symbol returned from getQuote is the same as an invalid symbol",
                    q.getSymbol().equals("BERG"));
        }
    }

    /**
     * Tests that the {@code DateTime} returned is not after the date passed
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public final void testGetQuoteTripleArgDatePositive() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, start, end);
        for (StockQuote q : qList) {
            assertTrue("Date returned from getQuote is not within parameter range",
                    !q.getDate().isBefore(start) || !q.getDate().isAfter(end));
        }
    }

    /**
     * Tests that the {@code DateTime} returned is not the same as a future date
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public final void testGetQuoteTripleArgDateNegative() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, start, end);
        for (StockQuote q : qList) {
            assertFalse("Date returned from getQuote is not within parameter range",
                    q.getDate().isBefore(start) || q.getDate().isAfter(end));
        }
    }

    /**
     * Tests that the stock symbol retrieved from getQuote is the same as what was passed
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolPositive() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, start, end, interval);
        System.out.println(interval.toString());
        for (StockQuote q : qList) {
            assertTrue("Symbol returned from getQuote is not same as symbol passed as argument",
                    q.getSymbol().equals(symbol));
        }
    }

    /**
     * Tests that the stock symbol returned is not something other than what was passed
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public final void testGetQuoteQuadArgStockSymbolNegative() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, start, end, interval);
        for (StockQuote q : qList) {
            assertFalse("Symbol returned from getQuote is the same as an invalid symbol",
                    q.getSymbol().equals(""));
        }
    }

    /**
     * Tests that the {@code DateTime} returned is not after the date passed
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public final void testGetQuoteQuadArgDatePositive() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, start, end, interval);
        for (StockQuote q : qList) {
            assertTrue("Time returned from getQuote is not within parameter range",
                    !q.getDate().isBefore(start) && !q.getDate().isAfter(end));
        }
    }

    /**
     * Tests that the {@code DateTime} returned is not the same as a future date
     * @throws StockServiceException if there is an IO exception retrieving the {@code StockQuote} or if the
     *                               the {@code StockQuote} is null
     */
    @Test
    public final void testGetQuoteQuadArgDateNegative() throws StockServiceException {
        List<StockQuote> qList = service.getQuote(symbol, start, end, interval);
        for (StockQuote q : qList) {
            assertFalse("Time returned from getQuote is not within parameter range",
                    q.getDate().isBefore(start) || q.getDate().isAfter(end));
        }
    }
}