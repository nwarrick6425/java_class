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
     *               object creation is delegated to static StockServiceFactory
     */
    protected BasicStockService() {
    }

    /**
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return the new stock quote
     */
    public final StockQuote getQuote(String symbol, DateTime date) {
        return new StockQuote(symbol, new BigDecimal(100), date);
    }

    /**
     * @param symbol the stock symbol to search for
     * @param startDate the date of the first stock quote
     * @param endDate the date of the last stock quote
     * @return a List containing the <code>StockQuote</code> instances for each day of the interval
     */
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
}
