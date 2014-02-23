package com.jtbdevelopment.lirr.dataobjects

import org.joda.time.LocalTime

/**
 * Date: 2/19/14
 * Time: 8:58 PM
 */
class TrainSchedule {
    String trainNumber
    boolean ignore
    boolean peak
    boolean weekday
    Direction direction
    Map<Station, LocalTime> stops = [:]

    boolean equals(final o) {
        if (this.is(o)) return true
        if (!(o instanceof TrainSchedule)) return false

        final TrainSchedule that = (TrainSchedule) o

        if (trainNumber != that.trainNumber) return false

        return true
    }

    int hashCode() {
        return trainNumber.hashCode()
    }

    @Override
    public String toString() {
        return "TrainSchedule{" +
                "trainNumber='" + trainNumber + '\'' +
                ", peak=" + peak +
                ", weekday=" + weekday +
                ", direction=" + direction +
                ", stops=" + stops +
                '}';
    }
}
