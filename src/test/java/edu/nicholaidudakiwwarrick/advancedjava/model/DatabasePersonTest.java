package edu.nicholaidudakiwwarrick.advancedjava.model;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test class to test {@code DatabasePerson} class
 */
@Immutable
public class DatabasePersonTest {

    private static final String firstName = "Nick";
    private static final String lastName = "Warrick";
    private static final int id = 26;
    private static DatabasePerson person;

    /**
     * Sets up common logic for all tests
     */
    @Before
    public final void setup() {
        person = new DatabasePerson();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setId(id);
    }

    /**
     * Tests the get/set method for the first name
     */
    @Test
    public final void testGetSetFirstNamePositive() {
        assertTrue("The name does not match the value set by the set method",
                firstName.equals(person.getFirstName()));
    }

    /**
     * Test to make sure the passed first name does not equal it's inverse
     */
    @Test
    public final void testGetSetFirstNameNegative() {
        assertFalse("The name matches the inverse of the passed first name",
                new StringBuilder(firstName).reverse().toString().equals(person.getFirstName()));
    }

    /**
     * Tests the get/set method for the last name
     */
    @Test
    public final void testGetSetLastNamePositive() {
        assertTrue("The name does not match the value set by the set method",
                lastName.equals(person.getLastName()));
    }

    /**
     * Test to make sure the passed last name does not equal it's inverse
     */
    @Test
    public final void testGetSetLastNameNegative() {
        assertFalse("The name matches the inverse of the passed last name",
                new StringBuilder(lastName).reverse().toString().equals(person.getLastName()));
    }

    /**
     * Verifies the id matches the id set by the set method
     */
    @Test
    public final void testGetSetIdPositive() {
        assertTrue("The id does not equal the id passed",
                id == person.getId());
    }

    /**
     * Verifies the set id does not match a different value than what was passed
     */
    @Test
    public final void testGetSetIdNegative() {
        assertFalse("The id matches a different value than what was passed",
                (id * id + 9) == person.getId());
    }

    /**
     * Test to make sure the equals method is working properly
     */
    @Test
    public final void testEqualsPositive() {
        DatabasePerson person2 = new DatabasePerson(firstName, lastName);
        person2.setId(id);
        assertTrue("The same object is not considered equal",
                person.equals(person2));
    }

    /**
     * Test to make sure the equals method is working properly
     */
    @Test
    public final void testEqualsNegative() {
        DatabasePerson person2 = new DatabasePerson(firstName, lastName);
        assertFalse("A different object is considered equal",
                person.equals(person2));
    }
}
