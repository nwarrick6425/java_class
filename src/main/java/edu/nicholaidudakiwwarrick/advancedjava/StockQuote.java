package edu.nicholaidudakiwwarrick.advancedjava;

import java.math.BigDecimal;
import java.text.NumberFormat;

public final class StockQuote {

    private final String symbol;
    private final BigDecimal price;

    public StockQuote (String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }

    public final String getSymbol() { return symbol; }

    public final BigDecimal getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof StockQuote))
            return false;
        StockQuote sq = (StockQuote)o;

        return sq.symbol.equals(symbol) && sq.price.equals(price);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + symbol.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return " [ " + getSymbol() + " " + NumberFormat.getInstance().format(getPrice()) + " ] ";
    }
}
