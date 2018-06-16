package edu.nicholaidudakiwwarrick.advancedjava;

import org.joda.time.DateTime;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test for <code>StockQuote</code> class
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class StockQuoteTest {

    private String symbol;
    private BigDecimal price;
    private DateTime date;

    @Before
    public void setup() {
        symbol = "GOOG";
        price = new BigDecimal(100);
        date = new DateTime().now();
    }

    @Test
    public void testStockQuoteConstruction() {
        StockQuote stockQuote = new StockQuote(symbol, price, date);

        assertEquals("The symbol is correct", stockQuote.getSymbol(), symbol);
        assertEquals("The price is correct", stockQuote.getPrice(), price);
        assertEquals("The date is correct", stockQuote.getDate(), date);
    }

    @Test
    public void testEqualsPositive() {
        StockQuote stockQuote_one = new StockQuote(symbol, price, date);
        StockQuote stockQuote_two = new StockQuote(symbol, price, date);
        StockQuote stockQuote_three = stockQuote_two;

        assertTrue("The StockQuote instance is equal to itself - Reflexivity",
                stockQuote_one.equals(stockQuote_one));
        assertTrue("The two StockQuote instances are equal - Symmetric",
                stockQuote_one.equals(stockQuote_two) && stockQuote_two.equals(stockQuote_one));
        assertTrue("The StockQuote instance are equal - Transitivity",
                stockQuote_one.equals(stockQuote_three));
        assertTrue("The hash codes are equal", stockQuote_one.hashCode() == stockQuote_two.hashCode());
    }

    @Test
    public void testEqualsNegative() {
        StockQuote stockQuote_one = new StockQuote(symbol, price, date);
        StockQuote stockQuote_two = new StockQuote("AAPL", price, date);

        assertFalse("The two StockQuote instances are not equal", stockQuote_one.equals(stockQuote_two));
        assertFalse("The passed object is not an instance of StockQuote",
                stockQuote_one.equals(new BigDecimal(100)));
        assertFalse("The hash codes are not equal",
                stockQuote_one.hashCode() == stockQuote_two.hashCode());
    }
}
