package com.jtbdevelopment.lirr.dataobjects.parsing

import org.joda.time.DateTime
import org.joda.time.LocalDate

/**
 * Date: 2/15/14
 * Time: 6:54 PM
 */
class ProcessedPDFSchedule {
    String title = ""
    DateTime modified
    LocalDate from
    LocalDate to

    Map<String, List> eastbound1 = [:];
    Map<String, List> eastbound2 = [:];
    Map<String, List> westbound1 = [:];
    Map<String, List> westbound2 = [:];

    boolean equals(final o) {
        if (this.is(o)) return true
        if (!(o instanceof ProcessedPDFSchedule)) return false

        final ProcessedPDFSchedule that = (ProcessedPDFSchedule) o

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
