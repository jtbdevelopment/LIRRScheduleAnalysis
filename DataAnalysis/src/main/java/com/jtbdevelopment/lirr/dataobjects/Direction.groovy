package com.jtbdevelopment.lirr.dataobjects

import org.joda.time.LocalTime

/**
 * Date: 2/19/14
 * Time: 9:05 PM
 */
public enum Direction {
    East(new LocalTime(16, 1), new LocalTime(19, 59)),
    West(new LocalTime(6, 0), new LocalTime(9, 59));

    final LocalTime startPeak
    final LocalTime endPeak


    private Direction(final LocalTime startPeak, final LocalTime endPeak) {
        this.startPeak = startPeak
        this.endPeak = endPeak
    }

}