package edu.nicholaidudakiwwarrick.advancedjava.model;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

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
}
