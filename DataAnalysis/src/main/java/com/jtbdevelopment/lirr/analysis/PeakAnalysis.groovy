package com.jtbdevelopment.lirr.analysis

import com.jtbdevelopment.lirr.dataobjects.*
import groovyx.gpars.GParsPool
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.joda.time.LocalTime

/**
 * Date: 2/18/14
 * Time: 8:08 PM
 */
class PeakAnalysis {
    public static final String FIRST_PEAK = "First Peak"
    public static final String LAST_PEAK = "Last Peak"
    public static final String LAST_PRE_PEAK = "Last Pre Peak"
    public static final String WAIT_FOR_FIRST_PEAK = "Wait for First Peak"
    public static final String NUMBER_OF_PEAK_TRAINS = "# of Peak Trains"
    public static final String AVERAGE_WAIT_BETWEEN_PEAKS = "Avg Wait Between Peaks"
    public static final String AVERAGE_RIDE_TIME = "Avg Ride Time"
    public static final String STD_DEV_WAIT_BETWEEN_PEAKS = "Std Dev Wait Between Peaks"
    public static final String MEDIAN_WAIT_BETWEEN_PEAKS = "Median Wait Between Peaks"
    public static final String LONGEST_WAIT_BETWEEN_PEAKS = "Longest Wait Between Peaks"
    public static final int NO_VALUE = 9999

    public static final String OVERALL = "Overall"

    private class StartAndEndTime implements Comparable<StartAndEndTime> {
        final LocalTime getOn
        final LocalTime getOff
        final int rideTime

        StartAndEndTime(final LocalTime time1, final LocalTime time2) {
            if (time1.compareTo(time2) < 0) {
                this.getOn = time1
                this.getOff = time2
            } else {
                this.getOn = time2
                this.getOff = time2
            }
            this.rideTime = timeDeltaInMinutes(getOn, getOff).abs()
        }

        @Override
        int compareTo(final StartAndEndTime o) {
            return this.getOn.compareTo(o.getOn)
        }
    }

    public Map<Station, Map<String, Object>> analyzeForZone(
            final ScheduleForPeriod scheduleForPeriod, final Zone zone, Direction direction) {
        Set<TrainSchedule> directionSchedules = getSchedulesForDirection(scheduleForPeriod, direction)
        Map<Station, List<StartAndEndTime>> stationTimes = getSchedulesByStation(directionSchedules, zone, Station.PENN_STATION)

        Map<Station, Map<String, Object>> stationStatistics = stationTimes.collectEntries {
            [
                    (it.key): [
                            (OVERALL): [
                                    (FIRST_PEAK): it.value[0].getOn,
                                    (LAST_PEAK): it.value[-1].getOn,
                                    (LAST_PRE_PEAK): LocalTime.MIDNIGHT,
                                    (WAIT_FOR_FIRST_PEAK): NO_VALUE,
                            ],
                    ]
            ]
        }

        getPrePostPeakTimes(directionSchedules, stationTimes, stationStatistics)

        computeDeltaStatistics(stationTimes, stationStatistics, OVERALL)
        Map<Station, LocalTime> lastPrePeak = stationStatistics.collectEntries {
            [(it.key): it.value[OVERALL][LAST_PRE_PEAK]]
        }
        int minPeakHour = stationStatistics.collect { it.value[OVERALL][FIRST_PEAK] }.min().hourOfDay
        int maxPeakHour = stationStatistics.collect { it.value[OVERALL][LAST_PEAK] }.max().hourOfDay
        (minPeakHour..maxPeakHour).each {
            int hour ->
                int hourBase1 = hour - minPeakHour + 1
                String append = "(Hour " + hourBase1 + ")"
                Map<Station, List<StartAndEndTime>> subTimes = stationTimes.collectEntries {
                    Station station, List<StartAndEndTime> times ->
                        [(station): times.findAll { it.getOn.hourOfDay == hour }]
                }
                subTimes.each {
                    Station station, List<StartAndEndTime> times ->
                        computeDeltaStatistics(subTimes, stationStatistics, append)
                        if (times.size() > 0) {
                            stationStatistics[station][(append)][(FIRST_PEAK)] = times[0].getOn
                            stationStatistics[station][(append)][(LAST_PEAK)] = times[-1].getOn
                            stationStatistics[station][(append)][(LAST_PRE_PEAK)] = lastPrePeak[station]
                            stationStatistics[station][(append)][(WAIT_FOR_FIRST_PEAK)] = ((times[0].getOn.millisOfDay - lastPrePeak[(station)].millisOfDay) / 60000).intValue()
                            lastPrePeak[station] = times[-1].getOn
                        }
                }
        }

        return stationStatistics
    }

    private Map<Station, List<StartAndEndTime>> computeDeltaStatistics(
            final Map<Station, List<StartAndEndTime>> stationTimes,
            final Map<Station, Map<String, Object>> stationStatistics,
            final String append) {
        stationTimes.each {
            Station station, List<StartAndEndTime> times ->
                List<Integer> deltas
                if (times.size() >= 2) {
                    deltas = (2..times.size()).collect {
                        int column ->
                            (int) (times[column - 1].getOn.millisOfDay - times[column - 2].getOn.millisOfDay) / 60000
                    }
                } else {
                    deltas = [NO_VALUE]
                }
                Map<String, Object> stats = stationStatistics[station].get(append, [:])
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

    private void getPrePostPeakTimes(final Set<TrainSchedule> directionSchedules,
                                     final Map<Station, List<StartAndEndTime>> stationTimes,
                                     final Map<Station, Map<String, Object>> stationStatistics) {
        Set<TrainSchedule> offPeakSchedules = directionSchedules.findAll {
            !it.peak
        }
        offPeakSchedules.each {
            TrainSchedule schedule ->
                schedule.stops.findAll { stationTimes.containsKey(it.key) }.each {
                    Station station, LocalTime stopTime ->
                        Map<String, Object> stationStats = stationStatistics[station][OVERALL]
                        if (stopTime.compareTo((LocalTime) stationStats.get(FIRST_PEAK)) < 0 &&
                                stopTime.compareTo((LocalTime) stationStats.get(LAST_PRE_PEAK)) > 0) {
                            stationStats.put(LAST_PRE_PEAK, stopTime)
                        }
                }
        }
        GParsPool.withPool {
            stationStatistics.eachParallel {
                Station station, Map<String, Object> statsOverall ->
                    Map<String, Object> stats = statsOverall[(OVERALL)]
                    stats[(WAIT_FOR_FIRST_PEAK)] = timeDeltaInMinutes((LocalTime) stats.get(LAST_PRE_PEAK), (LocalTime) stats.get(FIRST_PEAK))
            }
        }
    }

    private Set<TrainSchedule> getSchedulesForDirection(
            final ScheduleForPeriod scheduleForPeriod, final Direction direction) {
        Set<TrainSchedule> directionSchedules = scheduleForPeriod.schedules.findAll {
            it.direction == direction && !it.ignore
        }
        directionSchedules as Set
    }

    private Map<Station, List<StartAndEndTime>> getSchedulesByStation(
            final Set<TrainSchedule> directionSchedules,
            final Zone zone,
            final Station startOrEndStation) {
        Map<Station, List<StartAndEndTime>> stationTimes = [:]
        Set<TrainSchedule> peakSchedules = directionSchedules.findAll {
            it.peak
        }
        peakSchedules.findAll { it.stops.containsKey(startOrEndStation) }.each {
            TrainSchedule schedule ->
                LocalTime mandatoryTime = schedule.stops[startOrEndStation]
                schedule.stops.findAll {
                    Station key, LocalTime time ->
                        key.zone == zone
                }.each {
                    Station key, LocalTime time ->
                        List<StartAndEndTime> stationTime = stationTimes.get(key, [])
                        stationTime.add(new StartAndEndTime(time, mandatoryTime))
                }
        }
        stationTimes.values().each { it.sort() }
        stationTimes.findAll { !it.key.ignoreForAnalysis }
    }

    private static int timeDeltaInMinutes(final LocalTime start, final LocalTime end) {
        ((end.millisOfDay - start.millisOfDay) / 60000).intValue()
    }

}
