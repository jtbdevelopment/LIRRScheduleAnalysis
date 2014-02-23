package com.jtbdevelopment.lirr.analysis

import org.joda.time.LocalTime

/**
 * Date: 2/23/14
 * Time: 1:04 AM
 */
class TrainRide implements Comparable<TrainRide> {
    final LocalTime getOn
    final LocalTime getOff
    final int rideTime

    TrainRide(final LocalTime time1, final LocalTime time2) {
        if (time1.compareTo(time2) < 0) {
            this.getOn = time1
            this.getOff = time2
        } else {
            this.getOn = time2
            this.getOff = time1
        }
        this.rideTime = timeDeltaInMinutes(getOn, getOff)
    }

    @Override
    int compareTo(final TrainRide o) {
        return this.getOn.compareTo(o.getOn)
    }
}
