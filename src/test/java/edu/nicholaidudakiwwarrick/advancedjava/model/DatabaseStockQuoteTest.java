package edu.nicholaidudakiwwarrick.advancedjava.model;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit testing for the {@code DatabaseStockQuote} class
 *
 * @author Nicholai Dudakiw-Warrick
 */
@Immutable
public class DatabaseStockQuoteTest {

    private static final int id = 26;
    private static final String symbol = "JAVA";
    private static final BigDecimal price = new BigDecimal(49.26);
    private static final DateTime date = new DateTime(2018, 7, 4, 0, 0, 0);
    private static DatabaseStockSymbol stockSymbol;
    private static DatabaseStockQuote stockQuote;

    @Before
    public final void setup() {
        stockSymbol = new DatabaseStockSymbol(symbol);
        stockQuote = new DatabaseStockQuote(date, price, stockSymbol);
        stockQuote.setId(id);
    }

    @Test
    public final void testGetIdPositive() {
        assertTrue("The id returned does not equal the id passed to the setter",
                id == stockQuote.getId());
    }

    @Test
    public final void testGetIdNegative() {
        assertFalse("The id returned equals a value other than what was passed to the setter",
                (id + id * 2) == stockQuote.getId());
    }

    @Test
    public final void testSetIdPositive() {
        DatabaseStockQuote stockQuote2 = new DatabaseStockQuote(date, price, stockSymbol);
        stockQuote2.setId(stockQuote.getId());
        boolean idMatches = false;
        if (stockQuote.getId() == stockQuote2.getId()) idMatches = true;
        stockQuote2.setId(34);
        assertTrue("setId does not change the Id",
                idMatches && (stockQuote.getId() != stockQuote2.getId()));
    }

    @Test
    public final void testSetIdNegative() {
        DatabaseStockQuote stockQuote2 = new DatabaseStockQuote(date, price, stockSymbol);
        stockQuote2.setId(34);
        assertFalse("setId does not change the Id", (stockQuote.getId() == stockQuote2.getId()));
    }

    @Test
    public final void testGetTimePositive() {
        assertTrue("The returned is different than the time passed by the setter",
                stockQuote.getTime().equals(new Timestamp(date.getMillis())));
    }

    @Test
    public final void testGetTimeNegative() {
        assertFalse("The time returned is something other than what was passed to the setter",
                stockQuote.getTime().equals(DateTime.now()));
    }

    @Test
    public final void testSetTimePositive() {
        DatabaseStockQuote stockQuote2 = new DatabaseStockQuote(date, price, stockSymbol);
        boolean timeMatches = false;
        if (stockQuote.getTime().equals(stockQuote2.getTime()))
            timeMatches = true;
        stockQuote2.setTime(DateTime.now());
        assertTrue("The setTime method did not change the time",
                timeMatches && (!(stockQuote.getTime().equals(stockQuote2.getTime()))));
    }

    @Test
    public final void testSetTimeNegative() {
        DatabaseStockQuote stockQuote2 = new DatabaseStockQuote(date, price, stockSymbol);
        stockQuote2.setTime(DateTime.now());
        assertFalse("setTime does not change the time",
                stockQuote.getTime().equals(stockQuote2.getTime()));
    }

    @Test
    public final void testGetPricePositive() {
        assertTrue("getPrice returned a price different than what was passed to the setter",
                stockQuote.getPrice().equals(price));
    }

    @Test
    public final void testGetPriceNegative() {
        assertFalse("getPrice returned a price different than what was passed to the setter",
                stockQuote.getPrice().equals(new BigDecimal(10.26)));
    }

    @Test
    public final void testSetPricePositive() {
        DatabaseStockQuote stockQuote2 = new DatabaseStockQuote(date, price, stockSymbol);
        boolean priceMatches = false;
        if (stockQuote.getPrice().equals(stockQuote2.getPrice()))
            priceMatches = true;
        stockQuote2.setPrice(new BigDecimal(29.30));
        assertTrue("setPrice does not change the price",
                priceMatches && (!(stockQuote.getPrice().equals(stockQuote2.getPrice()))));
    }

    @Test
    public final void testSetPriceNegative() {
        DatabaseStockQuote stockQuote2 = new DatabaseStockQuote(date, price, stockSymbol);
        stockQuote2.setPrice(new BigDecimal(29.30));
        assertFalse("setPrice does not change the price",
                stockQuote.getPrice().equals(stockQuote2.getPrice()));
    }

    @Test
    public final void testGetStockSymbolPositive() {
        assertTrue("getSymbol returns a symbol other than what was passed to the setter",
                stockQuote.getStockSymbol().equals(stockSymbol));
    }

    @Test
    public final void testGetStockSymbolNegative() {
        DatabaseStockSymbol stockSymbol2 = new DatabaseStockSymbol("AMZN");
        stockQuote.getStockSymbol().setSymbol(stockSymbol2.getSymbol());
        stockQuote.getStockSymbol().setId(stockSymbol2.getId());
        assertFalse("getSymbol returns a symbol other than what was passed to the setter",
                stockSymbol2.equals(stockQuote.getStockSymbol()));
    }

    @Test
    public final void testSetStockSymbolPositive() {
        DatabaseStockQuote stockQuote2 = new DatabaseStockQuote(date, price, stockSymbol);
        boolean symbolMatches = false;
        if (stockQuote.getStockSymbol().equals(stockQuote2.getStockSymbol()))
            symbolMatches = true;
        stockQuote2.setStockSymbol(new DatabaseStockSymbol("AMZN"));
        assertTrue("setSymbol does not change the stock symbol",
                symbolMatches && (!(stockQuote.getStockSymbol().equals(stockQuote2.getStockSymbol()))));
    }

    @Test
    public final void testSetStockSymbolNegative() {
        DatabaseStockQuote stockQuote2 = new DatabaseStockQuote(date, price, stockSymbol);
        stockQuote2.setStockSymbol(new DatabaseStockSymbol("AMZN"));
        assertFalse("setSymbol does not change the stock symbol",
                stockQuote.getStockSymbol().equals(stockQuote2.getStockSymbol()));
    }
}