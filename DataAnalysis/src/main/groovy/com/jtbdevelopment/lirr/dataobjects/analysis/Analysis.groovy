package com.jtbdevelopment.lirr.dataobjects.analysis

import com.jtbdevelopment.lirr.dataobjects.core.Station
import groovy.transform.AutoClone
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Date: 2/23/14
 * Time: 3:56 PM
 */
@Document
@CompoundIndexes(
        @CompoundIndex(
                name = "start_end_type",
                def = "{'start' : 1, 'end' : 1, 'analysisType': 1}",
                unique = true
        )
)
@AutoClone
class Analysis {
    public static final String NO_VALUE = "N/A"

    public static final String OVERALL = "Overall"

    @Id
    String id
    LocalDate start
    LocalDate end
    @Indexed
    String analysisType
    DateTime computed = DateTime.now()
    Map<String, Map<Station, Map<String, Map<String, Object>>>> details
}
