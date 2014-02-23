package com.jtbdevelopment.lirr.analysis

import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis
import com.jtbdevelopment.lirr.dataobjects.schedule.CompleteSchedule

/**
 * Date: 2/23/14
 * Time: 9:07 AM
 */
public interface Analyzer {
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

    Analysis analyze(final CompleteSchedule scheduleForPeriod)
}