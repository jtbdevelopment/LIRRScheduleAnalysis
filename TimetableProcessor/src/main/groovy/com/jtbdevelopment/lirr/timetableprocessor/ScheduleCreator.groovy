package com.jtbdevelopment.lirr.timetableprocessor

import com.jtbdevelopment.lirr.dataobjects.core.Direction
import com.jtbdevelopment.lirr.dataobjects.core.Station
import com.jtbdevelopment.lirr.dataobjects.parsing.ProcessedPDFSchedule
import com.jtbdevelopment.lirr.dataobjects.schedule.CompleteSchedule
import com.jtbdevelopment.lirr.dataobjects.schedule.TrainSchedule
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

                Closure<Boolean> isPeak = { TrainSchedule s, Direction direction ->
                    LocalTime peakTime = getPeakTimeDeterminant(s)
                    assert peakTime: s.toString()
                    peakTime.compareTo(direction.startPeak) >= 0 && peakTime.compareTo(direction.endPeak) <= 0
                }


                List<TrainSchedule> east1 = processParsedSubSchedule(parsed.eastbound1, Direction.East)
                List<TrainSchedule> east2 = processParsedSubSchedule(parsed.eastbound2, Direction.East)
                markPeakTrains(east1, east2, isPeak)
                addOrMergeTrainSchedules(completeSchedule, east1)
                addOrMergeTrainSchedules(completeSchedule, east2)

                List<TrainSchedule> west1 = processParsedSubSchedule(parsed.westbound1, Direction.West)
                List<TrainSchedule> west2 = processParsedSubSchedule(parsed.westbound2, Direction.West)
                markPeakTrains(west1, west2, isPeak)
                addOrMergeTrainSchedules(completeSchedule, west1)
                addOrMergeTrainSchedules(completeSchedule, west2)
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

    private LocalTime getPeakTimeDeterminant(TrainSchedule s) {
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

    private List<TrainSchedule> processParsedSubSchedule(
            final Map<String, List> schedule,
            Direction direction) {
        GParsPool.withPool {
            Map<String, Station> stationMap = schedule.keySet().collectEntries { String station -> [(station): Station.STATION_NAME_MAP.get(station)] }
            List<TrainSchedule> trains = schedule.get("#").collectParallel { String trainNumber ->
                new TrainSchedule(trainNumber: trainNumber, direction: direction, peak: false, weekday: false)
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
                it.ignore = Station.TRAINS_TO_IGNORE.contains(it.trainNumber)
            }
            trains
        }
    }

    private markPeakTrains(
            List<TrainSchedule> trains1,
            List<TrainSchedule> trains2,
            final Closure<Boolean> determineIfPeak) {

        GParsPool.withPool {
            trains1.findAll { !it.ignore }.size()
            if (trains1.findAll { !it.ignore }.size() > trains2.findAll { !it.ignore }.size()) {
                trains1.each { it.weekday = true }
                trains1.eachParallel {
                    it.peak = determineIfPeak(it, it.direction)
                }
                trains2.each { it.weekday = false }
            } else {
                trains2.each { it.weekday = true }
                trains2.eachParallel {
                    it.peak = determineIfPeak(it, it.direction)
                }
                trains1.each { it.weekday = false }
            }
        }
    }
}
