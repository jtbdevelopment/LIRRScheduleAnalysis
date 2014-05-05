package com.jtbdevelopment.LIRR.dao.converters

import com.jtbdevelopment.LIRR.dataobjects.analysis.Analysis
import com.jtbdevelopment.LIRR.dataobjects.core.Station
import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import org.bson.types.ObjectId
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.core.convert.converter.Converter

/**
 * Date: 2/24/14
 * Time: 7:03 AM
 */
class AnalysisReader implements Converter<DBObject, Analysis> {
    private static final DateTimeFormatter HHMM = DateTimeFormat.forPattern("HH:mm")

    @Override
    Analysis convert(final DBObject source) {
        Analysis analysis = new Analysis()
        source.keySet().each {
            String fieldName ->
                switch (fieldName) {
                    case "_id":
                        analysis.id = ((ObjectId) source.get(fieldName)).toString()
                        break
                    case "start":
                    case "end":
                        analysis[fieldName] = new LocalDate((Date) source.get(fieldName))
                        break;
                    case "computed":
                        analysis[fieldName] = new DateTime((Date) source.get(fieldName))
                        break;
                    case "details":
                        BasicDBObject dbDirectionMap = source.get(fieldName)
                        analysis[fieldName] = dbDirectionMap.keySet().collectEntries() {
                            String directionString ->
                                BasicDBObject dbDirectionLists = dbDirectionMap.get(directionString)
                                List<Station> stations = dbDirectionLists.get("STATIONS").collect {
                                    String stationName ->
                                        Station.STATION_NAME_MAP.get(stationName)
                                }
                                BasicDBList dbDetailsList = dbDirectionLists.get("DETAILS")
                                Map<Station, Map<String, Map<String, Object>>> stationDetails = [:]
                                stations.eachWithIndex {
                                    Station station, int i ->
                                        BasicDBObject dbStationDetails = (BasicDBObject) dbDetailsList.get(i)
                                        stationDetails[(station)] = dbStationDetails.keySet().collectEntries() {
                                            String type ->
                                                BasicDBObject dbStationDetail = dbStationDetails.get(type)
                                                [
                                                        (type): dbStationDetail.keySet().collectEntries() {
                                                            String key ->
                                                                Object value = dbStationDetail.get(key)
                                                                if (value in String && value =~ /\d\d:\d\d/) {
                                                                    [(key): HHMM.parseLocalTime(value)]
                                                                } else {
                                                                    [(key): value]
                                                                }
                                                        }
                                                ]
                                        }
                                }
                                [(directionString): stationDetails]
                        }
                        break;
                    default:
                        analysis[fieldName] = source.get(fieldName)
                        break;
                }
        }
        return analysis
    }
}
