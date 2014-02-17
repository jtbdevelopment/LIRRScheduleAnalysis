package com.jtbdevelopment.lirr.timetableprocessor.data

import org.joda.time.DateTime

/**
 * Date: 2/15/14
 * Time: 2:50 PM
 */
class RoughParsedSchedule {
    String title
    String modified
    String subject

    List<String> eastboundWeekdays = []
    List<String> eastboundWeekends = []
    List<String> westboundWeekdays = []
    List<String> westboundWeekends = []
}
