package com.jtbdevelopment.lirr.analysis

import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis
import com.jtbdevelopment.lirr.dataobjects.analysis.TrainRide
import com.jtbdevelopment.lirr.dataobjects.core.Direction
import com.jtbdevelopment.lirr.dataobjects.core.Station
import com.jtbdevelopment.lirr.dataobjects.schedule.CompleteSchedule
import com.jtbdevelopment.lirr.dataobjects.schedule.TrainSchedule
import groovyx.gpars.GParsPool
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.joda.time.LocalTime
import org.joda.time.Minutes
import org.springframework.stereotype.Component

/**
 * Date: 2/18/14
 * Time: 8:08 PM
 *
 * TODO - off alternate pivot station like Atlantic Ave
 */
@Component
class PeakTrainAnalyzer implements Analyzer {
    static final String FIRST_PEAK = "First Peak"
    static final String LAST_PEAK = "Last Peak"
    static final String LAST_PRE_PEAK = "Last Pre Peak"
    static final String WAIT_FOR_FIRST_PEAK = "Wait for First Peak"
    static final String NUMBER_OF_PEAK_TRAINS = "# of Peak Trains"
    static final String AVERAGE_WAIT_BETWEEN_PEAKS = "Avg Wait Between Peaks"
    static final String AVERAGE_RIDE_TIME = "Avg Ride Time"
    static final String STD_DEV_WAIT_BETWEEN_PEAKS = "Std Dev Wait Between Peaks"
    static final String MEDIAN_WAIT_BETWEEN_PEAKS = "Median Wait Between Peaks"
    static final String LONGEST_WAIT_BETWEEN_PEAKS = "Longest Wait Between Peaks"
    static final int NO_VALUE = 9999

    static final String OVERALL = "Overall"

    public static final String PEAK_TRAIN_ANALYSIS = "Peak Train Analysis"

    public static final Map<Direction, List<String>> GROUPS_PER_DIRECTION;

    public static final List<String> DETAILS_PER_GROUP = [
            NUMBER_OF_PEAK_TRAINS,
            FIRST_PEAK,
            LAST_PEAK,
            AVERAGE_RIDE_TIME,
            AVERAGE_WAIT_BETWEEN_PEAKS,
            LONGEST_WAIT_BETWEEN_PEAKS,
            STD_DEV_WAIT_BETWEEN_PEAKS,
            MEDIAN_WAIT_BETWEEN_PEAKS,
            LAST_PRE_PEAK,
            WAIT_FOR_FIRST_PEAK,
    ]

    /*
    private static Closure<String> GROUP_FOR_HOUR = {
        int hour ->
            "(Departure Hour " + hour + ")"
    }
    */

    static {
        GROUPS_PER_DIRECTION = [
                (Direction.East): [OVERALL],
                (Direction.West): [OVERALL]
        ]
        GROUPS_PER_DIRECTION.each {
            Direction direction, List<String> groups ->
                groups.addAll(direction.peakPlus.collect {
                    int hour ->
                        //GROUP_FOR_HOUR(hour)
                        "(Departure Hour " + hour + ")"
                })
        }
    }

    @Override
    public Analysis analyze(
            final CompleteSchedule scheduleForPeriod) {
        Analysis analysis = new Analysis(start: scheduleForPeriod.start, end: scheduleForPeriod.end, analysisType: PEAK_TRAIN_ANALYSIS)
        analysis.details = Direction.values().collectEntries {
            Direction direction ->
                [(direction): analyzeForDirection(scheduleForPeriod, direction)]
        }
        analysis
    }

    private Map<Station, Map<String, Map<String, Object>>> analyzeForDirection(
            final CompleteSchedule scheduleForPeriod, Direction direction) {
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

            Map<Station, Map<String, Map<String, Object>>> stationStatistics = computeOverallStatistics(peakTimesByStation, prePeakTimesByStation)
            computeHourByHourBreakdowns(stationStatistics, peakTimesByStation, direction)

            return stationStatistics
        }
    }

    private Map<Station, Map<String, Map<String, Object>>> computeOverallStatistics(
            final Map<Station, List<TrainRide>> peakTimesByStation,
            final Map<Station, List<TrainRide>> prePeakTimesByStation) {
        Map<Station, Map<String, Map<String, Object>>> stationStatistics = peakTimesByStation.collectEntries {
            Station station, List<TrainRide> times ->
                [
                        (station): [
                                (OVERALL): [
                                        (FIRST_PEAK): times.first().getOn,
                                        (LAST_PEAK): times.last().getOn,
                                        (LAST_PRE_PEAK): prePeakTimesByStation.get(station).last().getOn,
                                        (WAIT_FOR_FIRST_PEAK): timeDeltaInMinutes(prePeakTimesByStation.get(station).last().getOn, times.last().getOn),
                                ],
                        ]
                ]
        }

        mergeIntoStatistics(OVERALL, stationStatistics, computeWaitForTrainStats(peakTimesByStation))
    }

    private void computeHourByHourBreakdowns(
            final Map<Station, Map<String, Map<String, Object>>> stationStatistics,
            final stationTimes, final Direction direction) {
        Map<Station, LocalTime> lastPrePeak = stationStatistics.collectEntries {
            [(it.key): it.value[OVERALL][LAST_PRE_PEAK]]
        }
        direction.peakPlus.each {
            int hour ->
                String statGroup = "(Departure Hour " + hour + ")"
//                String statGroup = GROUP_FOR_HOUR(hour)
                Map<Station, List<TrainRide>> subTimes = stationTimes.collectEntries {
                    Station station, List<TrainRide> times ->
                        [(station): times.findAll { it.getOn.hourOfDay == hour }]
                }
                subTimes.each {
                    Station station, List<TrainRide> times ->
                        mergeIntoStatistics(statGroup, stationStatistics, computeWaitForTrainStats(subTimes))
                        if (times.size() > 0) {
                            mergeIntoStatistics(statGroup, stationStatistics,
                                    [
                                            (station): [
                                                    (FIRST_PEAK): times.first().getOn,
                                                    (LAST_PEAK): times.last().getOn,
                                                    (LAST_PRE_PEAK): lastPrePeak[station],
                                                    (WAIT_FOR_FIRST_PEAK): timeDeltaInMinutes(times.first().getOn, lastPrePeak[(station)]),
                                            ]
                                    ]
                            )
                            lastPrePeak[station] = times[-1].getOn
                        }
                }
        }
    }

    private Map<Station, Map<String, Map<String, Object>>> mergeIntoStatistics(
            final String subGroup,
            final Map<Station, Map<String, Map<String, Object>>> stationStatistics,
            final Map<Station, Map<String, Object>> itemsToMerge) {
        stationStatistics.each {
            Station station, Map<String, Map<String, Object>> groups ->
                groups.get(subGroup, [:]).putAll(itemsToMerge.get(station, [:]))
        }
        stationStatistics
    }

    private Map<Station, Map<String, Object>> computeWaitForTrainStats(
            final Map<Station, List<TrainRide>> stationTimes) {
        (Map<Station, Map<String, Object>>) stationTimes.collectEntries {
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
                DescriptiveStatistics deltaTimeStats = new DescriptiveStatistics((double[]) deltas.collect {
                    it.doubleValue()
                }.toArray())
                DescriptiveStatistics rideTimeStats = new DescriptiveStatistics((double[]) times.rideTime.collect {
                    it.doubleValue()
                }.toArray())
                int avgRide = NO_VALUE;
                if (times.size() > 0) {
                    avgRide = rideTimeStats.max.intValue()
                }
                [
                        (station): [
                                (NUMBER_OF_PEAK_TRAINS): times.size(),
                                (AVERAGE_WAIT_BETWEEN_PEAKS): deltaTimeStats.mean.intValue(),
                                (LONGEST_WAIT_BETWEEN_PEAKS): deltaTimeStats.max.intValue(),
                                (MEDIAN_WAIT_BETWEEN_PEAKS): deltaTimeStats.getPercentile(50).intValue(),
                                (STD_DEV_WAIT_BETWEEN_PEAKS): deltaTimeStats.standardDeviation.intValue(),
                                (AVERAGE_RIDE_TIME): avgRide,
                        ]
                ]
        }
    }

    private Set<TrainSchedule> getSchedulesForDirection(
            final CompleteSchedule scheduleForPeriod, final Direction direction) {
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
