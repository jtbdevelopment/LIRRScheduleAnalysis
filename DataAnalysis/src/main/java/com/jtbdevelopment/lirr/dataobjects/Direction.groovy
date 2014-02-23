package com.jtbdevelopment.lirr.dataobjects

import org.joda.time.LocalTime

/**
 * Date: 2/19/14
 * Time: 9:05 PM
 */
public enum Direction {
    East(new LocalTime(16, 10), new LocalTime(19, 59), 16..19),
    West(new LocalTime(6, 0), new LocalTime(9, 59), 5..9);

    final LocalTime startPeak
    final LocalTime endPeak
    final Range<Integer> peakPlus


    private Direction(final LocalTime startPeak, final LocalTime endPeak, final Range<Integer> peakPlus) {
        this.startPeak = startPeak
        this.endPeak = endPeak
        this.peakPlus = peakPlus
    }

}