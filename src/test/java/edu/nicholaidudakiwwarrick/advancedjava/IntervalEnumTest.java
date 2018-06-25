package edu.nicholaidudakiwwarrick.advancedjava;

import org.junit.Before;
import org.junit.Test;

import edu.nicholaidudakiwwarrick.advancedjava.IntervalEnum;

import static org.junit.Assert.assertEquals;

/**
 * JUnit testing for the {@code IntervalEnum} enumeration class
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class IntervalEnumTest {

    @Test
    public void testHourly() {
        assertEquals("The Hourly amount is 1", IntervalEnum.HOURLY.amount(), 1 );
        assertEquals("The Hourly symbol is \"HOURLY\"", IntervalEnum.HOURLY.toString(), "HOURLY");
    }

    @Test
    public void testHalfDay() {
        assertEquals("The Half Day amount is 12", IntervalEnum.HALF_DAY.amount(), 12 );
        assertEquals("The Half Day symbol is \"HALF_DAY\"", IntervalEnum.HALF_DAY.toString(), "HALF_DAY");
    }

    @Test
    public void testDaily() {
        assertEquals("The Daily amount is 24", IntervalEnum.DAILY.amount(), 24 );
        assertEquals("The Daily symbol is \"DAILY\"", IntervalEnum.DAILY.toString(), "DAILY");
    }
}
