package com.jtbdevelopment.lirr.timetableprocessor.data

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime

/**
 * Date: 2/15/14
 * Time: 6:54 PM
 */
class ParsedSchedule {
    String title
    DateTime modified
    LocalDate from
    LocalDate to

    Map<String, Set<LocalTime>> eastboundWeekdays = [:];
    Map<String, Set<LocalTime>> eastboundWeekends = [:];
    Map<String, Set<LocalTime>> westboundWeekdays = [:];
    Map<String, Set<LocalTime>> westboundWeekends = [:];
}
