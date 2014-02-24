package com.jtbdevelopment.lirr.dataobjects.schedule

import com.jtbdevelopment.lirr.dataobjects.core.Direction
import com.jtbdevelopment.lirr.dataobjects.core.Station
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

    void merge(final TrainSchedule other) {
        assert trainNumber == other.trainNumber
        assert ignore == other.ignore
        assert peak == other.peak
        assert weekday == other.weekday
        assert direction == other.direction
        stops += other.stops
    }

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
}
