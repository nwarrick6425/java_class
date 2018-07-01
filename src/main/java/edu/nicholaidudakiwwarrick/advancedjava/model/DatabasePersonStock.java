package edu.nicholaidudakiwwarrick.advancedjava.model;

import javax.persistence.*;

/**
 * This class models a database table containing stock info for
 * each person in the person table
 *
 * @author Nicholai Dudakiw-Warrick
 */
@Entity
@Table(name="person_stocks", catalog="stocks")
public class DatabasePersonStock {

    private int id;
    private DatabasePerson person;
    private DatabaseStockSymbol stockSymbol;

    /**
     * Constructs a {@code DatabasePersonStock} that needs to be initialized
     */
    public DatabasePersonStock() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid {@code DatabasePersonStock} instance
     * @param person the person to assign the stock symbol to
     * @param symbol  the stock symbol to associate the person with
     */
    public DatabasePersonStock(DatabasePerson person, DatabaseStockSymbol symbol) {
        setPerson(person);
        setStockSymbol(symbol);
    }

    /**
     * Gets the unique ID for this {@code DatabasePersonStock} instance
     * @return an integer value representing the unique ID for this PersonStock
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID for this {@code DatabasePersonStock} instance.
     * This method should not be called by client code. The value is managed internally.
     * @param id an integer value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns a defensive copy of the mutable {@code DatabasePerson} object
     * assigned to the corresponding field of this class
     * @return the person associated with this {@code DatabasePersonStock} instance
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public DatabasePerson getPerson() {
        DatabasePerson person = new DatabasePerson(this.person.getFirstName(), this.person.getLastName());
        person.setId(this.person.getId());
        return person;
    }

    /**
     * Sets the first name of this {@code DatabasePersonStock} instance
     * @param person a Person instance
     */
    public void setPerson(DatabasePerson person) {
        this.person = person;
    }

    /**
     * Returns a defensive copy of the mutable {@code DatabaseStockSymbol} object
     * assigned to the corresponding field of this class
     * @return the stock symbol of this {@code DatabasePersonStock}
     */
    @ManyToOne
    @JoinColumn(name = "symbol_id", referencedColumnName = "id", nullable = false)
    public DatabaseStockSymbol getStockSymbol() {

        DatabaseStockSymbol stockSymbol = new DatabaseStockSymbol(this.stockSymbol.getSymbol());
        stockSymbol.setId(this.stockSymbol.getId());
        return stockSymbol;
    }

    /**
     * Sets the stock symbol of this {@code DatabasePersonStock} instance
     * @param stockSymbol a String value
     */
    public void setStockSymbol(DatabaseStockSymbol stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof  DatabasePersonStock)) return false;

        DatabasePersonStock personStock = (DatabasePersonStock) o;

        return this.id == personStock.id
                && this.person.equals(personStock.person)
                && this.stockSymbol.equals(personStock.stockSymbol);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (stockSymbol != null ? stockSymbol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonStock{" +
                "id=" + id +
                ", person='" + person + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                '}';
    }
}