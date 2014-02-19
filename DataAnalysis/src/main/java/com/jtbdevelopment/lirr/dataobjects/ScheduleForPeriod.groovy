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
    Map<String, DateTime> inputs = [:]
    Map<Station, StationSchedule> schedules = [:]
}
