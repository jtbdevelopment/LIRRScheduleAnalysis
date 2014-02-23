package com.jtbdevelopment.lirr.dataobjects

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Date: 2/15/14
 * Time: 6:35 PM
 */
@Document
class ScheduleForPeriod {
    LocalDate start
    LocalDate end
    Map<String, DateTime> inputFeeds = [:]
    Map<String, TrainSchedule> schedules = [:]

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
