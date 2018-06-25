package edu.nicholaidudakiwwarrick.advancedjava;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Basic stock service class that retrieves stock quotes.
 *
 * @author Nicholai Dudakiw-Warrick
 */
public final class BasicStockService implements StockService {

    public static final BasicStockService INSTANCE = new BasicStockService();


    /**
     * Constructor - prevents instantiation of object
     *               object creation is delegated to static {@code StockServiceFactory}
     */
    protected BasicStockService() {
    }

    /**
     * Returns a {@code StockQuote} instance with the specified symbol and date
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @param date   the date for the specific stock quote for the given symbol
     * @return the new stock quote
     */
    @Override
    public final StockQuote getQuote(String symbol, DateTime date) {
        return new StockQuote(symbol, new BigDecimal(100), new DateTime(date));
    }

    /**
     * Returns a {@code List<StockQuote>} instances for the specified symbol for the period of time
     *
     * @param symbol the stock symbol to search for
     * @param startDate the date of the first stock quote
     * @param endDate the date of the last stock quote
     * @return a List containing the <code>StockQuote</code> instances for each day of the interval
     */
    @Override
    public final List<StockQuote> getQuote(String symbol, DateTime startDate, DateTime endDate) {
        DateTime date = new DateTime(startDate);
        List<StockQuote> result = new ArrayList<StockQuote>();
        BigDecimal startPrice = new BigDecimal(100);

        while (date.isBefore(endDate)) {
            result.add(new StockQuote(symbol, startPrice, date));
            startPrice = startPrice.add(new BigDecimal(new Random().nextInt(15) - 3.18));
            date = date.plusDays(1);
        }
        return result;
    }

    /**
     * Returns a {@code List<StockQuote>} instances for the specified symbol during the period of time
     * at a set interval (e.g., Hourly, Half Day, or Daily)
     *
     * @param symbol the stock symbol to search for
     * @param startDate the date of the first stock quote
     * @param endDate the date of the last stock quote
     * @param interval the interval per day to return a stock quote
     *                 e.g. if IntervalEnum.DAILY is specified, one StockQuote per
     *                 day will be returned
     * @return a List containing the <code>StockQuote</code> instances for each day of the interval
     */
    @Override
    public final List<StockQuote> getQuote(String symbol, DateTime startDate, DateTime endDate, IntervalEnum interval) {
        DateTime date = new DateTime(startDate);
        List<StockQuote> result = new ArrayList<StockQuote>();
        BigDecimal startPrice = new BigDecimal(100);

        while (date.isBefore(endDate)) {
            result.add(new StockQuote(symbol, startPrice, date));
            startPrice = startPrice.add(new BigDecimal(new Random().nextInt(15) - 3.18));
            date = date.plusHours(interval.amount());
        }
        return result;
    }
}
