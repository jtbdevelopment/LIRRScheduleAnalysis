package com.jtbdevelopment.LIRR.dao.converters

import com.jtbdevelopment.LIRR.dataobjects.analysis.Analysis
import com.jtbdevelopment.LIRR.dataobjects.core.Station
import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import org.bson.types.ObjectId
import org.joda.time.LocalTime
import org.springframework.core.convert.converter.Converter

import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * Date: 2/23/14
 * Time: 10:27 PM
 */
class AnalysisWriter implements Converter<Analysis, DBObject> {
    @Override
    DBObject convert(final Analysis source) {
        BasicDBObject dbObject = new BasicDBObject()
        dbObject.putAll(source.class.declaredFields.findAll {
            !it.synthetic && !Modifier.isStatic(it.modifiers)
        }.collectEntries() {
            Field field ->
                switch (field.name) {
                    case "id":
                        String id = (String) source[field.name]
                        if (id)
                            return ["_id", new ObjectId(id)]
                        else
                            return ["_id", new ObjectId()]
                    case "start":
                    case "end":
                    case "computed":
                        return [(field.name): source[field.name].toDate()]
                    case "details":
                        Map<String, Map<Station, Map<String, Map<String, Object>>>> details = source[field.name]
                        return [
                                (field.name): details.collectEntries {
                                    String direction, Map<Station, Map<String, Map<String, Object>>> stations ->
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
                                                (direction): [
                                                        "STATIONS": stationList,
                                                        "DETAILS" : stationDetailList
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
