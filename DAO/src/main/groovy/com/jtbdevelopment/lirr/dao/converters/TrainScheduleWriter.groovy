package com.jtbdevelopment.lirr.dao.converters

import com.jtbdevelopment.lirr.dataobjects.schedule.TrainSchedule
import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import org.springframework.core.convert.converter.Converter

import java.lang.reflect.Field

/**
 * Date: 2/23/14
 * Time: 7:50 PM
 */
class TrainScheduleWriter implements Converter<TrainSchedule, DBObject> {
    @Override
    DBObject convert(final TrainSchedule source) {
        BasicDBObject dbObject = new BasicDBObject()
        dbObject.putAll(source.class.declaredFields.findAll { !it.synthetic }.collectEntries() {
            Field field ->
                switch (field.name) {
                    case "direction":
                        return [(field.name): source[field.name].toString()]
                    case "stops":
                        BasicDBList stations = new BasicDBList()
                        BasicDBList times = new BasicDBList()
                        source.stops.each {
                            stations.add(it.key.name.toUpperCase())
                            times.add(it.value.toString("HH:mm"))
                        }
                        return [
                                (field.name): [
                                        "STATIONS": stations,
                                        "TIMES"   : times
                                ]
                        ]
                    default:
                        return [(field.name): source[field.name]]
                }
        })
        return dbObject
    }
}
