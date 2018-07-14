package edu.nicholaidudakiwwarrick.advancedjava.services;

import edu.nicholaidudakiwwarrick.advancedjava.util.IntervalEnum;
import edu.nicholaidudakiwwarrick.advancedjava.model.StockQuote;
import org.joda.time.DateTime;

import java.util.List;

public interface StockService {
    /**
     * Return the current price for a share of stock for the given symbol
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @param date the date of the stock quote needed
     * @return a <CODE>StockQuote</CODE> instance
    */
    StockQuote getQuote(String symbol, DateTime date) throws StockServiceException;


    /**
     * Get a historical list of stock quotes for the provide symbol
     * @param symbol the stock symbol to search for
     * @param startDate the date of the first stock quote
     * @param endDate the date of the last stock quote
     * @return a list of StockQuote instances. One for each day in the range specified.
     *
    */
    List<StockQuote> getQuote(String symbol, DateTime startDate, DateTime endDate) throws StockServiceException;

    /**
     * Get a historical list of stock quotes for the provide symbol
     * @param symbol the stock symbol to search for
     * @param startDate the date of the first stock quote
     * @param endDate the date of the last stock quote
     * @param interval the interval per day to return a stock quote
     *                 e.g. if IntervalEnum.DAILY is specified, one StockQuote per
     *                 day will be returned
     * @return a list of StockQuote instances. One for each day in the range specified.
    */
    List<StockQuote> getQuote(String symbol, DateTime startDate, DateTime endDate, IntervalEnum interval);
}
