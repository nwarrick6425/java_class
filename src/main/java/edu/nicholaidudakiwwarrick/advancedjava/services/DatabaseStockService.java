package edu.nicholaidudakiwwarrick.advancedjava.services;

import edu.nicholaidudakiwwarrick.advancedjava.model.StockQuote;
import edu.nicholaidudakiwwarrick.advancedjava.util.DatabaseConnectionException;
import edu.nicholaidudakiwwarrick.advancedjava.util.DatabaseUtils;
import edu.nicholaidudakiwwarrick.advancedjava.util.IntervalEnum;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the StockService interface that gets
 * stock data from a database.
 */
public class DatabaseStockService implements StockService {

    /**
     * prevents instantiation - object creation delegated to StockServiceFactory
     */
    protected DatabaseStockService() {

    }

    /**
     * Returns a {@code StockQuote} instance with the specified symbol and date
     * The symbol returned is the first quote from that day
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @param date   the date for the specific stock quote for the given symbol
     * @throws StockServiceException if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    @Override
    public final StockQuote getQuote(String symbol, DateTime date) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        DateTime dayStart = date.withTimeAtStartOfDay();
        DateTime dayEnd = dayStart.plusSeconds(86399);
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + symbol + "'" +
                    "AND time between '" + dayStart.toString(StockQuote.DATE_PATTERN) + "' and '" +
                    dayEnd.toString(StockQuote.DATE_PATTERN) + "'";

            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                Timestamp timeStamp = resultSet.getTimestamp("time");
                DateTime time = new DateTime(timeStamp);
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(symbolValue, price, time));
            }
        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }
        return stockQuotes.get(0);
    }

    /**
     * Get a historical list of stock quotes for the provide symbol
     *
     * @param symbol the stock symbol to search for
     * @param startDate   the date of the first stock quote
     * @param endDate  the date of the last stock quote
     * @return a list of StockQuote instances
     * @throws   StockServiceException if using the service generates an exception.
     * If this happens, trying the service may work, depending on the actual cause of the
     * error.
     */
    @Override
    public final List<StockQuote> getQuote(String symbol, DateTime startDate, DateTime endDate) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select * from quotes where symbol = '" + symbol + "'" +
                    "AND time between '" + startDate.toString(StockQuote.DATE_PATTERN) + "' and '" +
                    endDate.toString(StockQuote.DATE_PATTERN) + "'";

            ResultSet resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                String symbolValue = resultSet.getString("symbol");
                DateTime time = new DateTime(resultSet.getDate("time"));
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(symbolValue, price, time));
            }
        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for:" + symbol);
        }
        return stockQuotes;
    }

    @Override
    public final List<StockQuote> getQuote(String symbol, DateTime startDate, DateTime endDate, IntervalEnum interval) {
        return new ArrayList<StockQuote>();
    }
}
