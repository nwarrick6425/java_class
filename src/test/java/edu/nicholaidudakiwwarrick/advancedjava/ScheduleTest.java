package edu.nicholaidudakiwwarrick.advancedjava;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test for the Schedule class
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class ScheduleTest {

    private Date currentDate;
    private Calendar endOfJob;
    private Employee employee;

    @Before
    public void setup() {
        currentDate = Calendar.getInstance().getTime();
        endOfJob = Calendar.getInstance();
        endOfJob.add(Calendar.MONTH, 1);
        employee = new Employee(1234, "John", "Doe");
    }

    @Test
    public void testScheduleConstruction() {
        Schedule schedule = new Schedule(employee, currentDate, endOfJob.getTime());

        assertEquals("The employee is correct", schedule.getEmployee(), employee);
        assertEquals("The start date is correct", schedule.getStartDate(), currentDate);
        assertEquals("The end date is correct", schedule.getEndDate(), endOfJob.getTime());
    }

    @Test
    public void testIsScheduledPositive() {
        Schedule schedule = new Schedule(employee, currentDate, endOfJob.getTime());
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        assertTrue("The employee should be scheduled tomorrow", schedule.isScheduled(tomorrow));
    }

    @Test
    public void testIsScheduledNegative() {
        Schedule schedule = new Schedule(employee, currentDate, endOfJob.getTime());
        Calendar twoYearsAgo = Calendar.getInstance();
        twoYearsAgo.add(Calendar.YEAR, -2);
        assertFalse("The employee should not be scheduled two years ago", schedule.isScheduled(twoYearsAgo));
    }
}
