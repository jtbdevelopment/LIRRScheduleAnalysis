package com.jtbdevelopment.lirr.timetableprocessor

import com.jtbdevelopment.lirr.dataobjects.ScheduleForPeriod
import com.jtbdevelopment.lirr.dataobjects.Station
import com.jtbdevelopment.lirr.dataobjects.StationSchedule
import com.jtbdevelopment.lirr.timetableprocessor.data.ParsedSchedule
import org.joda.time.LocalTime

/**
 * Date: 2/18/14
 * Time: 6:43 AM
 */
class ScheduleCreator {
    ScheduleForPeriod createFrom(final Set<ParsedSchedule> schedules) {
        assert schedules.size() > 0

        ParsedSchedule randomParsed = schedules.iterator().next()
        schedules.each {
            ParsedSchedule parsed ->
                assert randomParsed.from == parsed.from
                assert randomParsed.to == parsed.to
        }

        ScheduleForPeriod scheduleForPeriod = new ScheduleForPeriod()
        scheduleForPeriod.start = randomParsed.from
        scheduleForPeriod.end = randomParsed.to
        schedules.each {
            ParsedSchedule parsed ->
                scheduleForPeriod.inputs.put(parsed.title, parsed.modified)

                parsed.eastboundWeekdays.each {
                    String stationName, Set<LocalTime> times ->
                        Station station = Station.STATION_NAME_MAP.get(stationName)
                        StationSchedule stationSchedule = scheduleForPeriod.schedules.get(station, new StationSchedule(station: station))
                        stationSchedule.eastboundWeekdays.addAll(times)
                }
                parsed.eastboundWeekends.each {
                    String stationName, Set<LocalTime> times ->
                        Station station = Station.STATION_NAME_MAP.get(stationName)
                        StationSchedule stationSchedule = scheduleForPeriod.schedules.get(station, new StationSchedule(station: station))
                        stationSchedule.eastboundWeekends.addAll(times)
                }
                parsed.westboundWeekdays.each {
                    String stationName, Set<LocalTime> times ->
                        Station station = Station.STATION_NAME_MAP.get(stationName)
                        StationSchedule stationSchedule = scheduleForPeriod.schedules.get(station, new StationSchedule(station: station))
                        stationSchedule.westboundWeekdays.addAll(times)
                }
                parsed.westboundWeekends.each {
                    String stationName, Set<LocalTime> times ->
                        Station station = Station.STATION_NAME_MAP.get(stationName)
                        StationSchedule stationSchedule = scheduleForPeriod.schedules.get(station, new StationSchedule(station: station))
                        stationSchedule.westboundWeekends.addAll(times)
                }
        }

        return scheduleForPeriod
    }
}
