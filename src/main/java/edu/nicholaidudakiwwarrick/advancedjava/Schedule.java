package edu.nicholaidudakiwwarrick.advancedjava;

import java.util.Calendar;
import java.util.Date;

/**
 * Simple class to model an employee schedule
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class Schedule {

    private Employee employee;
    private Date startDate;
    private Date endDate;

    /**
     * Creates a new Schedule instance.
     *
     * @param employee      employee that is being scheduled
     * @param startDate     job start date
     * @param endDate       job end date
     */
    public Schedule (Employee employee, Date startDate, Date endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return  the employee that is scheduled
     */
    public Employee getEmployee() { return employee; }

    /**
     * @return  the starting date of the job
     */
    public Date getStartDate() { return startDate; }

    /**
     * @return  the ending date of the job
     */
    public Date getEndDate() { return endDate; }

    /**
     * @param calendar is the employee scheduled at this time?
     * @return true if the employee is scheduled at the time provided
     */
    public boolean isScheduled(Calendar calendar) {
        Date startDate = this.getStartDate();
        Date endDate = this.getEndDate();

        Date time = calendar.getTime();
        boolean notBefore = time.before(startDate);
        boolean notAfter = time.after(endDate);

        return (notAfter == false && notBefore == false);
    }
}
