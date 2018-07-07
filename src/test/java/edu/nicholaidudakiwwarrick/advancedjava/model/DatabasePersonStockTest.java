package edu.nicholaidudakiwwarrick.advancedjava.model;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the {@code DatabasePersonStock} class
 *
 * @author Nicholai Dudakiw-Warrick
 */
@Immutable
public class DatabasePersonStockTest {

    private static final String firstName = "Nick";
    private static final String lastName = "Warrick";
    private static final int id = 26;
    private static DatabasePerson person;
    private static DatabaseStockSymbol symbol;
    private static DatabasePersonStock personStock;

    /**
     * Setup common logic for all tests
     */
    @Before
    public final void setup() {
        person = new DatabasePerson();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setId(id);
        symbol = new DatabaseStockSymbol("JAVA");
        personStock = new DatabasePersonStock();
        personStock.setId(id);
        personStock.setPerson(person);
        personStock.setStockSymbol(symbol);
    }

    @Test
    public final void testGetSetPersonPositive() {
        assertTrue("The returned person via getPerson does match the person passed by the setter",
                person.equals(personStock.getPerson()));
    }

    @Test
    public final void testGetSetPersonNegative() {
        DatabasePerson person2 = new DatabasePerson("James", "Smith");
        personStock.getPerson().setFirstName(person2.getFirstName());
        personStock.getPerson().setFirstName(person2.getLastName());
        personStock.getPerson().setId(person2.getId());
        assertFalse("getPerson return value matches a different value other than what was passed into setter",
                person2.equals(personStock.getPerson()));
    }

    @Test
    public final void testGetSetSymbolPositive() {
        assertTrue("The returned stock symbol via getSymbol does match the symbol passed by the setter",
                symbol.equals(personStock.getStockSymbol()));
    }

    @Test
    public final void testGetSetSymbolNegative() {
        DatabaseStockSymbol symbol2 = new DatabaseStockSymbol("CODE");
        personStock.getStockSymbol().setSymbol(symbol2.getSymbol());
        personStock.getStockSymbol().setId(symbol2.getId());
        assertFalse("getSymbol return value matches a different value other than what was passed into setter",
                symbol2.equals(personStock.getStockSymbol()));
    }

    @Test
    public final void testGetSetIdPositive() {
        assertTrue("The getId return value does not match the id passed",
                id == personStock.getId());
    }

    @Test
    public final void testGetSetIdNegative() {
        assertFalse("The getId return value matches a value other than the one that passed into the setter",
                (id * id + 9) == personStock.getId());
    }

    @Test
    public final void testEqualsPositive() {
        DatabasePersonStock personStock2 = new DatabasePersonStock(person, symbol);
        personStock2.setId(id);
        assertTrue("The same object is not considered equal", personStock.equals(personStock2));
    }

    @Test
    public final void testEqualsNegative() {
        DatabasePersonStock personStock2 = new DatabasePersonStock(person, symbol);
        assertFalse("Different objects are considered equal", personStock.equals(personStock2));
    }
}
