package com.jtbdevelopment.lirr.analysis

import com.jtbdevelopment.lirr.dataobjects.Direction
import com.jtbdevelopment.lirr.dataobjects.ScheduleForPeriod
import com.jtbdevelopment.lirr.dataobjects.Station
import com.jtbdevelopment.lirr.dataobjects.TrainSchedule
import groovyx.gpars.GParsPool
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.joda.time.LocalTime
import org.joda.time.Minutes

/**
 * Date: 2/18/14
 * Time: 8:08 PM
 */
class PeakTrainAnalyzer implements Analyzer {

    @Override
    public Map<Direction, Map<String, Map<Station, Map<String, Object>>>> analyze(
            final ScheduleForPeriod scheduleForPeriod) {
        Direction.values().collectEntries {
            Direction direction ->
                [(direction): analyzeforDirection(scheduleForPeriod, direction)]
        }
    }

    private Map<String, Map<Station, Map<String, Object>>> analyzeforDirection(
            final ScheduleForPeriod scheduleForPeriod, Direction direction) {
        GParsPool.withPool {
            List<Set<TrainSchedule>> schedulesSplitByPeakFlag = getSchedulesForDirection(scheduleForPeriod, direction).splitParallel {
                it.peak
            }
            Map<Station, List<TrainRide>> peakTimesByStation = getSchedulesByStation(schedulesSplitByPeakFlag[0], Station.PENN_STATION)
            Map<Station, List<TrainRide>> prePeakTimesByStation = getSchedulesByStation(schedulesSplitByPeakFlag[1], Station.PENN_STATION)
            prePeakTimesByStation.eachParallel {
                Station station, List<TrainRide> startAndEndTimes ->
                    startAndEndTimes.removeAll {
                        TrainRide startAndEndTime ->
                            startAndEndTime.getOn.compareTo(direction.startPeak) > 0
                    }
                    if (startAndEndTimes.empty) {
                        startAndEndTimes.add(new TrainRide(LocalTime.MIDNIGHT, LocalTime.MIDNIGHT))
                    }
                    startAndEndTimes.sort()
            }

            Map<String, Map<Station, Map<String, Object>>> stationStatistics = computeOverallStatistics([:], peakTimesByStation, prePeakTimesByStation)
            computeHourByHourBreakdowns(stationStatistics, peakTimesByStation, direction)

            return stationStatistics
        }
    }

    private Map<Station, Map<String, Object>> computeOverallStatistics(Map<String, Map<Station, Map<String, Object>>> stationStatistics,
                                                                       final Map<Station, List<TrainRide>> peakTimesByStation,
                                                                       final Map<Station, List<TrainRide>> prePeakTimesByStation) {
        stationStatistics += peakTimesByStation.collectEntries {
            Station station, List<TrainRide> times ->
                [
                        (OVERALL): [
                                (station): [
                                        (FIRST_PEAK): times.first().getOn,
                                        (LAST_PEAK): times.last().getOn,
                                        (LAST_PRE_PEAK): prePeakTimesByStation.get(station).last().getOn,
                                        (WAIT_FOR_FIRST_PEAK): timeDeltaInMinutes(prePeakTimesByStation.get(station).last().getOn, times.last().getOn),
                                ],
                        ]
                ]
        }

        computeWaitForTrainStats(peakTimesByStation, stationStatistics, OVERALL)
        stationStatistics
    }

    private void computeHourByHourBreakdowns(
            final Map<String, Map<Station, Map<String, Object>>> stationStatistics,
            final stationTimes, final Direction direction) {
        Map<Station, LocalTime> lastPrePeak = stationStatistics[OVERALL].collectEntries {
            [(it.key): it.value[LAST_PRE_PEAK]]
        }
        direction.peakPlus.each {
            int hour ->
                String statGroup = "(Departure Hour " + hour + ")"
                Map<Station, List<TrainRide>> subTimes = stationTimes.collectEntries {
                    Station station, List<TrainRide> times ->
                        [(station): times.findAll { it.getOn.hourOfDay == hour }]
                }
                subTimes.each {
                    Station station, List<TrainRide> times ->
                        computeWaitForTrainStats(subTimes, stationStatistics, statGroup)
                        if (times.size() > 0) {
                            stationStatistics[(statGroup)][(station)][(FIRST_PEAK)] = times.first().getOn
                            stationStatistics[(statGroup)][(station)][(LAST_PEAK)] = times.last().getOn
                            stationStatistics[(statGroup)][(station)][(LAST_PRE_PEAK)] = lastPrePeak[station]
                            stationStatistics[(statGroup)][(station)][(WAIT_FOR_FIRST_PEAK)] = timeDeltaInMinutes(times.first().getOn, lastPrePeak[(station)])
                            lastPrePeak[station] = times[-1].getOn
                        }
                }
        }
    }

    private Map<Station, List<TrainRide>> computeWaitForTrainStats(
            final Map<Station, List<TrainRide>> stationTimes,
            final Map<String, Map<Station, Map<String, Object>>> stationStatistics,
            final String append) {
        stationTimes.each {
            Station station, List<TrainRide> times ->
                List<Integer> deltas
                if (times.size() >= 2) {
                    deltas = (2..times.size()).collect {
                        int column ->
                            timeDeltaInMinutes(times[column - 2].getOn, times[column - 1].getOn)
                    }
                } else {
                    deltas = [NO_VALUE]
                }
                Map<String, Object> stats = stationStatistics.get(append, [:]).get(station, [:])
                DescriptiveStatistics deltaTimeStats = new DescriptiveStatistics((double[]) deltas.collect {
                    it.doubleValue()
                }.toArray())
                DescriptiveStatistics rideTimeStats = new DescriptiveStatistics((double[]) times.rideTime.collect {
                    it.doubleValue()
                }.toArray())
                stats[(NUMBER_OF_PEAK_TRAINS)] = times.size()
                stats[(AVERAGE_WAIT_BETWEEN_PEAKS)] = deltaTimeStats.mean.intValue()
                stats[(LONGEST_WAIT_BETWEEN_PEAKS)] = deltaTimeStats.max.intValue()
                stats[(MEDIAN_WAIT_BETWEEN_PEAKS)] = deltaTimeStats.getPercentile(50).intValue()
                stats[(STD_DEV_WAIT_BETWEEN_PEAKS)] = deltaTimeStats.standardDeviation.intValue()
                int avgRide = NO_VALUE;
                if (times.size() > 0) {
                    avgRide = rideTimeStats.max.intValue()
                }
                stats[AVERAGE_RIDE_TIME] = avgRide
        }
    }

    private Set<TrainSchedule> getSchedulesForDirection(
            final ScheduleForPeriod scheduleForPeriod, final Direction direction) {
        Set<TrainSchedule> directionSchedules = scheduleForPeriod.schedules.values().findAllParallel {
            it.direction == direction && (!it.ignore) && it.weekday
        }
        directionSchedules
    }

    private Map<Station, List<TrainRide>> getSchedulesByStation(
            final Collection<TrainSchedule> schedules,
            final Station startOrEndStation) {
        Map<Station, List<TrainRide>> stationTimes = Station.STATIONS.collectEntries {
            Station station ->
                [(station): []]
        }
        schedules.findAllParallel { it.stops.containsKey(startOrEndStation) }.each {
            TrainSchedule schedule ->
                LocalTime mandatoryTime = schedule.stops[startOrEndStation]
                schedule.stops.findAll {
                    Station station, LocalTime time ->
                        !station.ignoreForAnalysis
                }.each {
                    Station station, LocalTime time ->
                        stationTimes[station].add(new TrainRide(time, mandatoryTime))
                }
        }
        stationTimes.values().eachParallel { it.sort() }
        stationTimes.findAll { (!it.key.ignoreForAnalysis) && (!it.value.empty) }
    }

    private static int timeDeltaInMinutes(final LocalTime start, final LocalTime end) {
        if (start && end) {
            return Minutes.minutesBetween(start, end).minutes
        }
        NO_VALUE
    }

}
