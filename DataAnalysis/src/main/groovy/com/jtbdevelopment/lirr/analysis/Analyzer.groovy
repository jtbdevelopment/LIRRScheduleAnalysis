package com.jtbdevelopment.lirr.analysis

import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis
import com.jtbdevelopment.lirr.dataobjects.schedule.CompleteSchedule

/**
 * Date: 2/23/14
 * Time: 9:07 AM
 */
public interface Analyzer {
    Analysis analyze(final CompleteSchedule scheduleForPeriod)
}