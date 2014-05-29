package com.jtbdevelopment.lirr.dao.converters

import com.jtbdevelopment.lirr.dataobjects.core.Direction
import com.jtbdevelopment.lirr.dataobjects.core.Station
import com.jtbdevelopment.lirr.dataobjects.schedule.TrainSchedule
import com.mongodb.BasicDBList
import com.mongodb.DBObject
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.core.convert.converter.Converter

/**
 * Date: 2/23/14
 * Time: 8:06 PM
 */
class TrainScheduleReader implements Converter<DBObject, TrainSchedule> {
    private static final DateTimeFormatter HHMM = DateTimeFormat.forPattern("HH:mm")

    @Override
    TrainSchedule convert(final DBObject source) {
        TrainSchedule schedule = new TrainSchedule()
        source.keySet().each {
            String fieldName ->
                switch (fieldName) {
                    case "stops":
                        DBObject lists = (DBObject) source.get(fieldName)
                        BasicDBList stations = (BasicDBList) lists.get("STATIONS")
                        BasicDBList times = (BasicDBList) lists.get("TIMES")
                        stations.eachWithIndex {
                            String stationName, int i ->
                                schedule.stops[Station.STATION_NAME_MAP[stationName]] =
                                        LocalTime.parse((String) times[i], HHMM)
                        }
                        break;
                    case "direction":
                        schedule[fieldName] = Enum.valueOf(Direction.class, (String) source.get(fieldName))
                        break;
                    default:
                        schedule[fieldName] = source.get(fieldName)
                        break;
                }
        }
        return schedule
    }
}
