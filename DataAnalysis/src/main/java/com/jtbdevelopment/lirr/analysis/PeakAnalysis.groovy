package com.jtbdevelopment.lirr.analysis

import com.jtbdevelopment.lirr.dataobjects.ScheduleForPeriod
import com.jtbdevelopment.lirr.dataobjects.Station
import com.jtbdevelopment.lirr.dataobjects.StationSchedule
import com.jtbdevelopment.lirr.dataobjects.Zone

/**
 * Date: 2/18/14
 * Time: 8:08 PM
 */
class PeakAnalysis {
    public void analyzeForZone(final ScheduleForPeriod scheduleForPeriod, final Zone zone) {

        List<Station> stations = Station.ZONE_STATION_MAP.get(zone)

        stations.each {
            Station station ->
                StationSchedule schedule = scheduleForPeriod.schedules.get(station)

                schedule.eastboundWeekdays.findAll {

                }
        }
    }
}
