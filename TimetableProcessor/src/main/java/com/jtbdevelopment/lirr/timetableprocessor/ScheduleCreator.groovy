package com.jtbdevelopment.lirr.timetableprocessor

import com.jtbdevelopment.lirr.dataobjects.Direction
import com.jtbdevelopment.lirr.dataobjects.ScheduleForPeriod
import com.jtbdevelopment.lirr.dataobjects.Station
import com.jtbdevelopment.lirr.dataobjects.TrainSchedule
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
                scheduleForPeriod.inputFeeds.put(parsed.title, parsed.modified)

                Closure<Boolean> neverPeak = { TrainSchedule s, Direction direction -> Boolean.FALSE }
                Closure<Boolean> isPeak = { TrainSchedule s, Direction direction ->
                    LocalTime peakTime = getPeakTimeDeterminant(s)
                    assert peakTime: s.toString()
                    peakTime.compareTo(direction.startPeak) >= 0 && peakTime.compareTo(direction.endPeak) <= 0
                }
                scheduleForPeriod.schedules.addAll(processParsedSubSchedule(parsed.eastboundWeekends, Direction.East, false, neverPeak))
                scheduleForPeriod.schedules.addAll(processParsedSubSchedule(parsed.westboundWeekends, Direction.West, false, neverPeak))
                scheduleForPeriod.schedules.addAll(processParsedSubSchedule(parsed.eastboundWeekdays, Direction.East, true, isPeak))
                scheduleForPeriod.schedules.addAll(processParsedSubSchedule(parsed.westboundWeekdays, Direction.West, true, isPeak))
        }

        return scheduleForPeriod
    }

    public LocalTime getPeakTimeDeterminant(TrainSchedule s) {
        LocalTime time
        time = s.stops[Station.PENN_STATION]
        if (!time) {
            time = s.stops[Station.ATLANTIC_AVENUE]
        }
        if (!time) {
            time = s.stops[Station.HUNTERSPOINT_AVENUE]
        }
        if (!time) {
            if (s.direction == Direction.East) {
                time = s.stops.values().min()
            } else {
                time = s.stops.values().max()
            }
        }
        time
    }

    public List<TrainSchedule> processParsedSubSchedule(LinkedHashMap<String, List> schedule, Direction direction, boolean weekday, Closure<Boolean> determineIfPeak) {
        Map<String, Station> stationMap = schedule.keySet().collectEntries { String station -> [(station): Station.STATION_NAME_MAP.get(station)] }
        List<TrainSchedule> trains = schedule.get("#").collect { String trainNumber ->
            new TrainSchedule(trainNumber: trainNumber, direction: direction, peak: false, weekday: weekday)
        }
        (1..trains.size()).each {
            int columnPlus1 ->
                int column = columnPlus1 - 1
                schedule.findAll { it.key != "#" }.each {
                    String stationName, List<LocalTime> times ->
                        if (times[column]) {
                            Station station = stationMap[stationName]
                            assert station
                            trains[column].stops[station] = times[column]
                        }
                }
        }
        trains.each { it.peak = determineIfPeak(it, direction) }
        trains.findAll {
            Station.TRAINS_TO_IGNORE.contains(it.trainNumber)
        }.each {
            println "Skipping Train #${it.trainNumber}"
        }
        trains.findAll {
            !Station.TRAINS_TO_IGNORE.contains(it.trainNumber)
        }
    }
}
