package com.jtbdevelopment.lirr.dao.converters

import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis
import com.jtbdevelopment.lirr.dataobjects.core.Direction
import com.jtbdevelopment.lirr.dataobjects.core.Station
import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import org.joda.time.LocalTime
import org.springframework.core.convert.converter.Converter

import java.lang.reflect.Field

/**
 * Date: 2/23/14
 * Time: 10:27 PM
 */
class AnalysisWriter implements Converter<Analysis, DBObject> {
    @Override
    DBObject convert(final Analysis source) {
        BasicDBObject dbObject = new BasicDBObject()
        dbObject.putAll(source.class.declaredFields.findAll { !it.synthetic }.collectEntries() {
            Field field ->
                switch (field.name) {
                    case "id":
                        return ["_id", source[field.name]]
                    case "start":
                    case "end":
                    case "computed":
                        return [(field.name): source[field.name].toDate()]
                    case "details":
                        Map<Direction, Map<Station, Map<String, Map<String, Object>>>> details = source[field.name]
                        return [
                                (field.name): details.collectEntries {
                                    Direction direction, Map<Station, Map<String, Map<String, Object>>> stations ->
                                        BasicDBList stationList = new BasicDBList();
                                        BasicDBList stationDetailList = new BasicDBList()
                                        stations.each {
                                            Station station, Map<String, Map<String, Object>> stationDetails ->
                                                stationList.add(station.name.toUpperCase())
                                                stationDetailList.add(
                                                        stationDetails.collectEntries {
                                                            String type, Map<String, Object> results ->
                                                                [(type): results.collectEntries {
                                                                    String key, Object value ->
                                                                        [(key): value in LocalTime ? value.toString("HH:mm") : value]
                                                                }]
                                                        }
                                                )
                                        }
                                        [
                                                (direction.toString()): [
                                                        "STATIONS": stationList,
                                                        "DETAILS": stationDetailList
                                                ]
                                        ]
                                }
                        ]
                    default:
                        return [(field.name): source[field.name]]
                }
        })
        return dbObject
    }
}
