package com.jtbdevelopment.LIRR.timetableprocessor

import com.jtbdevelopment.LIRR.dataobjects.core.Direction
import com.jtbdevelopment.LIRR.dataobjects.core.Station
import com.jtbdevelopment.LIRR.dataobjects.parsing.ProcessedPDFSchedule
import com.jtbdevelopment.LIRR.dataobjects.schedule.CompleteSchedule
import com.jtbdevelopment.LIRR.dataobjects.schedule.TrainSchedule
import groovyx.gpars.GParsPool
import org.joda.time.LocalTime
import org.springframework.stereotype.Component

/**
 * Date: 2/18/14
 * Time: 6:43 AM
 */
@Component
class ScheduleCreator {
    CompleteSchedule createFrom(final Set<ProcessedPDFSchedule> schedules) {
        assert schedules.size() > 0

        ProcessedPDFSchedule randomParsed = schedules.iterator().next()
        schedules.each {
            ProcessedPDFSchedule parsed ->
                assert randomParsed.from == parsed.from
                assert randomParsed.to == parsed.to
        }

        CompleteSchedule completeSchedule = new CompleteSchedule()
        completeSchedule.start = randomParsed.from
        completeSchedule.end = randomParsed.to
        //  Do not parallelize due to merging
        schedules.each {
            ProcessedPDFSchedule parsed ->
                completeSchedule.inputFeeds.put(parsed.title, parsed.modified)

                Closure<Boolean> neverPeak = { TrainSchedule s, Direction direction -> Boolean.FALSE }
                Closure<Boolean> isPeak = { TrainSchedule s, Direction direction ->
                    LocalTime peakTime = getPeakTimeDeterminant(s)
                    assert peakTime: s.toString()
                    peakTime.compareTo(direction.startPeak) >= 0 && peakTime.compareTo(direction.endPeak) <= 0
                }

                addOrMergeTrainSchedules(completeSchedule, processParsedSubSchedule(parsed.eastboundWeekdays, Direction.East, true, isPeak))
                addOrMergeTrainSchedules(completeSchedule, processParsedSubSchedule(parsed.westboundWeekdays, Direction.West, true, isPeak))
                addOrMergeTrainSchedules(completeSchedule, processParsedSubSchedule(parsed.eastboundWeekends, Direction.East, false, neverPeak))
                addOrMergeTrainSchedules(completeSchedule, processParsedSubSchedule(parsed.westboundWeekends, Direction.West, false, neverPeak))
        }

        return completeSchedule
    }

    private void addOrMergeTrainSchedules(
            final CompleteSchedule scheduleForPeriod, final List<TrainSchedule> trainSchedules) {
        trainSchedules.each {
            TrainSchedule schedule ->
                if (scheduleForPeriod.schedules.containsKey(schedule.trainNumber)) {
                    scheduleForPeriod.schedules[(schedule.trainNumber)].merge(schedule)
                } else {
                    scheduleForPeriod.schedules[(schedule.trainNumber)] = schedule
                }
        }
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

    public List<TrainSchedule> processParsedSubSchedule(
            final Map<String, List> schedule,
            Direction direction,
            boolean weekday,
            final Closure<Boolean> determineIfPeak) {
        GParsPool.withPool {
            Map<String, Station> stationMap = schedule.keySet().collectEntries { String station -> [(station): Station.STATION_NAME_MAP.get(station)] }
            List<TrainSchedule> trains = schedule.get("#").collectParallel { String trainNumber ->
                new TrainSchedule(trainNumber: trainNumber, direction: direction, peak: false, weekday: weekday)
            }
            Map<String, List> stopsOnly = schedule.findAll { it.key != "#" }
            (1..trains.size()).eachParallel {
                int columnPlus1 ->
                    int column = columnPlus1 - 1
                    stopsOnly.each {
                        String stationName, List<LocalTime> times ->
                            if (times[column]) {
                                Station station = stationMap[stationName]
                                assert station
                                trains[column].stops[station] = times[column]
                            }
                    }
            }
            trains.eachParallel {
                it.peak = determineIfPeak(it, direction);
                it.ignore = Station.TRAINS_TO_IGNORE.contains(it.trainNumber)
            }
            trains
        }
    }
}
