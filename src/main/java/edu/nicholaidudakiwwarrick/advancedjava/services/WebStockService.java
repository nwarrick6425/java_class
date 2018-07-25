package edu.nicholaidudakiwwarrick.advancedjava.services;

import edu.nicholaidudakiwwarrick.advancedjava.model.StockQuote;
import edu.nicholaidudakiwwarrick.advancedjava.util.IntervalEnum;
import org.joda.time.DateTime;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Implementation of the {@code StockService} interface that obtains stock quotes from a web service
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class WebStockService implements StockService {

    // public fields of this class - returns an instance of the WebStockService
    public static final WebStockService INSTANCE = new WebStockService();

    /**
     * Hidden constructor - delegates instance creation to {@code ServiceFactory}
     */
    protected WebStockService(){
        // empty body
    }

    /**
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @param date the date of the stock quote needed
     * @return a {@code StockQuote} that contains information pulled from the Yahoo Finance API
     * @throws StockServiceException when there is error in connecting to the Yahoo Finance API or
     *                               if the passed symbol is not found
     */
    public final StockQuote getQuote(String symbol, DateTime date) throws StockServiceException {
        StockQuote stockQuote = null;
        Calendar calDate = new DateTime(date).toCalendar(Locale.getDefault());
        try {
            Stock stock = YahooFinance.get(symbol, calDate);
            stockQuote = new StockQuote(stock.getQuote().getSymbol(),
                    stock.getQuote().getPrice(),
                    new DateTime(stock.getQuote().getLastTradeTime()));
        } catch (IOException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuote == null)
            throw new StockServiceException("No instance of " + symbol + " found in the selected range");

        return stockQuote;
    }

    /**
     * Returns a list of {@code StockQuote} objects for the passed symbol between the start and end date.
     * Quotes are returned for each day by default.
     *
     * @param symbol the stock symbol to search for
     * @param start the start date of the historical {@code StockQuote} data
     * @param end the end date of the historical {@code StockQuote} data
     * @return a list of {@code StockQuote} objects
     * @throws StockServiceException when there is an IO connection exception or if the list is empty
     */
    public final List<StockQuote> getQuote(String symbol, DateTime start, DateTime end) throws StockServiceException {
        List<StockQuote> stockQuotes = new ArrayList<StockQuote>();
        try {
            Stock stock = YahooFinance.get(symbol);
            List<HistoricalQuote> historicalQuoteList = stock.getHistory(start.toGregorianCalendar(),
                    end.toGregorianCalendar(),
                    Interval.DAILY);
            stockQuotes = quoteListAdapter(historicalQuoteList);
        } catch (IOException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes == null)
            throw new StockServiceException("No instance of " + symbol + " found in the selected range");

        return stockQuotes;
    }

    /**
     * Returns a list of {@code StockQuote} objects between the specified dates at the specified interval
     *
     * The Yahoo Finance API only allows for DAILY, WEEKLY, OR MONTHLY. All other {@code IntervalEnum} enums
     * will default to DAILY.
     *
     * @param symbol the stock symbol to search for
     * @param start the date of the first stock quote
     * @param end the date of the last stock quote
     * @param interval the interval per day to return a stock quote
     *                 e.g. if IntervalEnum.DAILY is specified, one StockQuote per
     *                 day will be returned
     * @return
     * @throws StockServiceException
     */
    public final List<StockQuote> getQuote(String symbol, DateTime start, DateTime end, IntervalEnum interval) throws StockServiceException {
        List<StockQuote> stockQuotes = new ArrayList<StockQuote>();
        yahoofinance.histquotes.Interval quoteInterval = intervalAdapter(interval);
        try {
            Stock stock = YahooFinance.get(symbol);
            List<HistoricalQuote> historicalQuoteList = stock.getHistory(start.toGregorianCalendar(),
                    end.toGregorianCalendar(),
                    quoteInterval);
            stockQuotes = quoteListAdapter(historicalQuoteList);
        } catch (IOException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes == null)
            throw new StockServiceException("No instance of " + symbol + " found in the selected range");

        return stockQuotes;
    }

    /**
     * Private helper method to convert a {@code HistoricalQuote} list to a {@code StockQuote} list
     *
     * @param quotes {@code HistoricalQuote} list to be converted
     * @return a list of {@code StockQuote} objects
     */
    private final List<StockQuote> quoteListAdapter(List<HistoricalQuote> quotes) {
        List<StockQuote> stockQuotes = new ArrayList<>();
        for (HistoricalQuote quote : quotes) {
            stockQuotes.add(new StockQuote(quote.getSymbol(), quote.getAdjClose(), new DateTime(quote.getDate())));
        }
        return stockQuotes;
    }

    /**
     * Private helper method to convert an {@code IntervalEnum} to a {@code yahoofinance.histquotes.Interval}
     * enumeration
     * @param interval the interval to be converted
     * @return the Yahoo Finance enumeration that matches the {@code IntervalEnum}
     *          returns Interval.DAILY if no match is found
     */
    private static final yahoofinance.histquotes.Interval intervalAdapter(IntervalEnum interval) {
        switch(interval.amount()) {
            case (24): return yahoofinance.histquotes.Interval.DAILY;
            case (168): return yahoofinance.histquotes.Interval.WEEKLY;
            case (672): return yahoofinance.histquotes.Interval.MONTHLY;
            default: return yahoofinance.histquotes.Interval.DAILY;
        }
    }

}
