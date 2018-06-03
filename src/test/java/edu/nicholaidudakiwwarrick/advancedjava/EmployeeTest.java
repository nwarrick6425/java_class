package edu.nicholaidudakiwwarrick.advancedjava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test for the Employee Class
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class EmployeeTest {

    private int id;
    private String firstName;
    private String lastName;
    private String fullName;

    @Before
    public void setup() {
        id = 1234;
        firstName = "John";
        lastName = "Doe";
        fullName = firstName + " " + lastName;
    }

    @Test
    public void testEmployeeConstruction () {
        Employee employee = new Employee(id, firstName, lastName);
        assertEquals("The id is correct", employee.getEmployeeId(), id);
        assertEquals("The first name is correct", employee.getEmployeeFirstName(), firstName);
        assertEquals("The last name is correct", employee.getEmployeeLastName(), lastName);
        assertEquals("The full name is correct", employee.getEmployeeFullName(), fullName);
    }
}
