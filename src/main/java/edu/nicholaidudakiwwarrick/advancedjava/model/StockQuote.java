package edu.nicholaidudakiwwarrick.advancedjava.model;

import jdk.nashorn.internal.ir.annotations.Immutable;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * This class describes a stock quote at a given point in time.
 * The class is immutable.
 *
 * @author Nicholai Dudakiw-Warrick
 */
@Immutable
public final class StockQuote {

    public static final String DATE_PATTERN = "YYYY-MM-dd HH:mm:ss";
    private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern(DATE_PATTERN);

    private final String symbol;
    private final BigDecimal price;
    private final DateTime date;

    /**
     * Constructor - Creates a <CODE>StockQuote</CODE> instance
     * @param symbol    string representation of the stock symbol
     * @param price     current price for given date
     * @param date      date at which the stock quote is requested
     */
    public StockQuote (@NotNull String symbol, @NotNull BigDecimal price, @NotNull DateTime date) {
        this.symbol = symbol;
        this.price = price;
        this.date = date;
    }

    /**
     * @return the stock's symbol
     */
    public final String getSymbol() { return symbol; }

    /**
     * @return the stock's price for the give date
     */
    public final BigDecimal getPrice() { return price; }

    /**
     * @return the stock's date
     */
    public final DateTime getDate() { return date; }

    /**
     * @return the date formatter for the specified date format
     */
    public static final DateTimeFormatter getDateFormatter() { return dateFormatter; }

    /**
     * @param o object to check equality against
     * @return whether this object is equal to the passed <CODE>StockQuote</CODE> object
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof StockQuote))
            return false;
        StockQuote stockQuote = (StockQuote)o;

        return stockQuote.symbol.equals(this.symbol)
                && stockQuote.price.equals(this.price)
                && stockQuote.date.equals(this.date);
    }

    /**
     * @return the hash code for the <code>StockQuote</code> instance
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    /**
     * @return the string representation of a <code>StockQuote</code> instance
     */
    @Override
    public String toString() {
        return "[ " + getSymbol() + " " + NumberFormat.getInstance().format(getPrice()) +
                " " + getDate().toString(dateFormatter) + " ]";
    }
}
