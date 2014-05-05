package com.jtbdevelopment.LIRR.analysis

import com.jtbdevelopment.LIRR.dataobjects.analysis.Analysis
import com.jtbdevelopment.LIRR.dataobjects.schedule.CompleteSchedule

/**
 * Date: 2/23/14
 * Time: 9:07 AM
 */
public interface Analyzer {
    Analysis analyze(final CompleteSchedule scheduleForPeriod)
}