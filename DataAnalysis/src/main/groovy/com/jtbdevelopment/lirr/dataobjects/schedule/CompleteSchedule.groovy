package com.jtbdevelopment.lirr.dataobjects.schedule

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Date: 2/15/14
 * Time: 6:35 PM
 */
@Document
@CompoundIndexes(
        @CompoundIndex(
                name = "start_end_type",
                def = "{'start' : 1, 'end' : 1}",
                unique = true
        )
)
class CompleteSchedule {
    @Id
    String id
    LocalDate start
    LocalDate end
    DateTime processed = DateTime.now()
    Map<String, DateTime> inputFeeds = [:]
    Map<String, TrainSchedule> schedules = [:]
}
