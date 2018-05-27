package edu.nicholaidudakiwwarrick.advancedjava;

/**
 * Simple class to model an employee
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class Employee {

    private int employeeId;
    private String employeeFirstName;
    private String employeeLastName;

    /**
     * Creates a new employee instance
     *
     * @param employeeId            employee identification number
     * @param employeeFirstName     employee first name
     * @param employeeLastName      employee last name
     */
    public Employee (int employeeId, String employeeFirstName, String employeeLastName) {
        this.employeeId = employeeId;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
    }

    /**
     * @return  the employee id
     */
    public int getEmployeeId() { return employeeId; }

    /**
     * @return  the employee's first name
     */
    public String getEmployeeFirstName() { return employeeFirstName; }

    /**
     * @return the employee's last name
     */
    public String getEmployeeLastName() { return employeeLastName; }

    /**
     * @return the employee's full name
     */
    public String getEmployeeFullName() {
        return employeeFirstName + " " + employeeLastName;
    }
}
