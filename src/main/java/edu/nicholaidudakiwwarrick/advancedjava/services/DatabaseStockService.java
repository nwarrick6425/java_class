package edu.nicholaidudakiwwarrick.advancedjava.services;

import edu.nicholaidudakiwwarrick.advancedjava.model.DatabaseStockQuote;
import edu.nicholaidudakiwwarrick.advancedjava.model.DatabaseStockSymbol;
import edu.nicholaidudakiwwarrick.advancedjava.model.StockQuote;
import edu.nicholaidudakiwwarrick.advancedjava.util.DatabaseConnectionException;
import edu.nicholaidudakiwwarrick.advancedjava.util.DatabaseUtils;
import edu.nicholaidudakiwwarrick.advancedjava.util.IntervalEnum;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
     * prevents instantiation - object creation delegated to ServiceFactory
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

        // set the start and end of the passed day
        DateTime dayStart = date.withTimeAtStartOfDay();
        DateTime dayEnd = dayStart.plusSeconds(86399);

        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select id from stock_symbols where symbol = '" + symbol + "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            resultSet.next();
            queryString = "select * from quotes where symbol_id = '" + resultSet.getInt("id") + "'"+
                    "AND time between '" + dayStart.toString(StockQuote.DATE_PATTERN) + "' and '" +
                    dayEnd.toString(StockQuote.DATE_PATTERN) + "'";
            resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                Timestamp timeStamp = resultSet.getTimestamp("time");
                DateTime time = new DateTime(timeStamp);
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(symbol, price, time));
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
            String queryString = "select id from stock_symbols where symbol = '" + symbol + "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            resultSet.next();
            queryString = "select * from quotes where symbol_id = '" + resultSet.getInt("id") + "'" +
                    "AND time between '" + startDate.toString(StockQuote.DATE_PATTERN) + "' and '" +
                    endDate.toString(StockQuote.DATE_PATTERN) + "'";

            resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                Timestamp timeStamp = resultSet.getTimestamp("time");
                DateTime time = new DateTime(timeStamp);
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(symbol, price, time));
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
    public final List<StockQuote> getQuote(String symbol, DateTime startDate, DateTime endDate, IntervalEnum interval) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "select id from stock_symbols where symbol = '" + symbol + "'";
            ResultSet resultSet = statement.executeQuery(queryString);
            resultSet.next();
            queryString = "select * from quotes where symbol_id = '" + resultSet.getInt("id") +
                    "' and time between '" + startDate.toString(StockQuote.DATE_PATTERN) + "' and '" +
                    endDate.toString(StockQuote.DATE_PATTERN) + "'";
            resultSet = statement.executeQuery(queryString);
            stockQuotes = new ArrayList<StockQuote>(resultSet.getFetchSize());
            DateTime intervalEnd = new DateTime(startDate);
            while (resultSet.next()) {
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp timestamp = resultSet.getTimestamp("time");
                DateTime time = new DateTime(timestamp);
                if (!time.isBefore(intervalEnd)) {
                    stockQuotes.add(new StockQuote(symbol, price, time));
                    intervalEnd.plusHours(interval.amount());
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("No instances of " + symbol + " found in the selected range");
        }
        return stockQuotes;
    }

    /**
     * Adds a new quote or update an existing one in the database
     * @param time the {@code DateTime} representation of the quote
     * @param price the price of the quote
     * @param stockSymbol the symbol of the quote
     * @throws StockServiceException if a service cannot perform a certain operation
     */
    public final void addOrUpdateQuote(DateTime time, BigDecimal price, DatabaseStockSymbol stockSymbol) throws StockServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of StockQuote if already exists within table
            // or adds as last row of personStock table
            transaction = session.beginTransaction();
            session.saveOrUpdate(stockSymbol);
            DatabaseStockQuote dbQuote = new DatabaseStockQuote(time, price, stockSymbol);
            session.saveOrUpdate(dbQuote);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
                throw new RuntimeException(e.getMessage());
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                session.close();
            }
        }
    }
}