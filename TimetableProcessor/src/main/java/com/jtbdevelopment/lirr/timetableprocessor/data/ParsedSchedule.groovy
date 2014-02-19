package com.jtbdevelopment.lirr.timetableprocessor.data

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime

/**
 * Date: 2/15/14
 * Time: 6:54 PM
 */
class ParsedSchedule {
    String title = ""
    DateTime modified
    LocalDate from
    LocalDate to

    Map<String, List<LocalTime>> eastboundWeekdays = [:];
    Map<String, List<LocalTime>> eastboundWeekends = [:];
    Map<String, List<LocalTime>> westboundWeekdays = [:];
    Map<String, List<LocalTime>> westboundWeekends = [:];

    boolean equals(final o) {
        if (this.is(o)) return true
        if (!(o instanceof ParsedSchedule)) return false

        final ParsedSchedule that = (ParsedSchedule) o

        if (from != that.from) return false
        if (modified != that.modified) return false
        if (title != that.title) return false
        if (to != that.to) return false

        return true
    }

    int hashCode() {
        int result
        result = (title != null ? title.hashCode() : 0)
        result = 31 * result + (modified != null ? modified.hashCode() : 0)
        result = 31 * result + (from != null ? from.hashCode() : 0)
        result = 31 * result + (to != null ? to.hashCode() : 0)
        return result
    }
}
