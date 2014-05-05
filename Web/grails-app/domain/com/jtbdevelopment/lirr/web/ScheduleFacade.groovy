package com.jtbdevelopment.LIRR.web

import com.jtbdevelopment.LIRR.dataobjects.schedule.CompleteSchedule
import com.jtbdevelopment.LIRR.dataobjects.schedule.TrainSchedule
import org.joda.time.DateTime
import org.joda.time.LocalDate

class ScheduleFacade {
    private CompleteSchedule data = new CompleteSchedule()

    /*
    String id
    LocalDate start
    LocalDate end
    DateTime processed = DateTime.now()
    Map<String, DateTime> inputFeeds = [:]
    Map<String, TrainSchedule> schedules = [:]
    */

    String getId() {
        return data.id
    }

    void setId(final String id) {
        data.id = id
    }

    LocalDate getStart() {
        return data.start
    }

    void setStart(final LocalDate start) {
        data.start = start
    }

    LocalDate getEnd() {
        return data.end
    }

    void setEnd(final LocalDate end) {
        data.end = end
    }

    DateTime getProcessed() {
        return data.processed
    }

    void setProcessed(final DateTime processed) {
        data.processed = processed
    }

    Map<String, DateTime> getInputFeeds() {
        return data.inputFeeds
    }

    void setInputFeeds(final Map<String, DateTime> inputFeeds) {
        data.inputFeeds = inputFeeds
    }

    Map<String, TrainSchedule> getSchedules() {
        return data.schedules
    }

    void setSchedules(final Map<String, TrainSchedule> schedules) {
        data.schedules = schedules
    }
    static mapWith = "none"

    static constraints = {
    }

    static transients = ['"data']

    boolean equals(final o) {
        data.equals(o)
    }

    int hashCode() {
        data.hashCode()
    }
}
