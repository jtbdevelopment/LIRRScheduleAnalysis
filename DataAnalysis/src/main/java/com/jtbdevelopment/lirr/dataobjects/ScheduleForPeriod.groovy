package com.jtbdevelopment.lirr.dataobjects

import org.joda.time.DateTime
import org.joda.time.LocalDate

/**
 * Date: 2/15/14
 * Time: 6:35 PM
 */
class ScheduleForPeriod {
    LocalDate start
    LocalDate end
    Map<String, DateTime> inputFeeds = [:]
    Set<TrainSchedule> schedules = []

    @Override
    public String toString() {
        return "ScheduleForPeriod{" +
                "start=" + start +
                ", end=" + end +
                ", inputFeeds=" + inputFeeds +
                ", schedules=" + schedules +
                '}';
    }
}
