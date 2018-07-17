package edu.nicholaidudakiwwarrick.advancedjava.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Basic;

/**
 * This class models a database table containing stock symbol info
 *
 * @author Nicholai Dudakiw-Warrick
 */
@Entity
@Table(name="stock_symbols", catalog="stocks")
public class DatabaseStockSymbol {

    private int id;
    private String symbol;

    /**
     * Constructs a {@code StockSymbol} that needs to be initialized
     */
    public DatabaseStockSymbol() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid {@code StockSymbol} instance
     * @param symbol
     */
    public DatabaseStockSymbol(String symbol) {
        setSymbol(symbol);
    }

    /**
     * @return the id field of this {@code StockSymbol} instance
     */
    @Id
    @Column(name = "id",  nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets the id field of this {@code StockSymbol} instance
     * @param id an int value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the symbol field of this {@code StockSymbol} instance
     */
    @Basic
    @Column(name = "symbol", nullable = false, insertable = true, updatable = true, length = 4)
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol field of this {@code StockSymbol} instance
     * @param symbol a String
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatabaseStockSymbol)) return false;

        DatabaseStockSymbol stockSymbol = (DatabaseStockSymbol) o;

        return this.id == stockSymbol.id
                && this.symbol.equals(stockSymbol.symbol);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "symbol=" + symbol;
    }
}
