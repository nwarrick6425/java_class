package edu.nicholaidudakiwwarrick.advancedjava.util;

import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Enum to represent intervals in a day
 * Current representations are Hourly, every 12 Hours, and Daily
 *
 * @author Nicholai Dudakiw-Warrick
 */
@Immutable
public enum IntervalEnum {

    /** An enumeration for one hour of a day */
    HOURLY("HOURLY", 1),

    /** An enumeration for one half of one day or 12 hours */
    HALF_DAY("HALF_DAY", 12),

    /** An enumeration for one day of one year or 24 hours */
    DAILY("DAILY", 24);

    private final String symbol;
    private final int hours;

    /**
     * Constructs a new {@code IntervalEnum} instance
     *
     * @param symbol a {@code String} representation of an {@code enum} element
     */
    IntervalEnum(String symbol, int hours) {
        this.symbol = symbol;
        this.hours = hours;
    }

    /**
     * @return the amount of hours for the specified {@code enum} instance
     */
    public int amount() {
        return hours;
    }

    /**
     * @return a String representation of this {@code enum} instance
     */
    @Override
    public String toString() { return this.symbol; }
}

