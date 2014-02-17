package com.jtbdevelopment.lirr.dataobjects

import org.joda.time.LocalTime

/**
 * Date: 2/15/14
 * Time: 6:28 PM
 */
class StationSchedule {
    Station station
    Set<LocalTime> eastboundWeekdays = []
    Set<LocalTime> eastboundWeekends = []
    Set<LocalTime> westboundWeekdays = []
    Set<LocalTime> westboundWeekends = []
}

