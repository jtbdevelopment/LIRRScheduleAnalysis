package com.jtbdevelopment.LIRR.dataobjects.analysis

import org.joda.time.LocalTime
import org.joda.time.Minutes

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
        this.rideTime = Minutes.minutesBetween(getOn, getOff).minutes
    }

    @Override
    int compareTo(final TrainRide o) {
        int getOnCompare = this.getOn.compareTo(o.getOn)
        if (getOnCompare != 0) {
            return getOnCompare
        }
        return this.getOff.compareTo(o.getOff);
    }

    boolean equals(final o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        final TrainRide trainRide = (TrainRide) o

        if (rideTime != trainRide.rideTime) return false
        if (getOff != trainRide.getOff) return false
        if (getOn != trainRide.getOn) return false

        return true
    }

    int hashCode() {
        int result
        result = getOn.hashCode()
        result = 31 * result + getOff.hashCode()
        result = 31 * result + rideTime
        return result
    }
}
