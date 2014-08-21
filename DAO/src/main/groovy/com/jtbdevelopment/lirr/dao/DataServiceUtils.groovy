package com.jtbdevelopment.lirr.dao

import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis
import com.jtbdevelopment.lirr.dataobjects.schedule.CompleteSchedule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Component

/**
 * Date: 2/23/14
 * Time: 6:10 PM
 */
@Component
class DataServiceUtils {
    @Autowired
    private AnalysisRepository analysisRepository

    @Autowired
    private CompleteScheduleRepository completeScheduleRepository

    @CacheEvict(value = "schedules", allEntries = true)
    public CompleteSchedule saveOrUpdateSchedule(final CompleteSchedule schedule) {
        List<CompleteSchedule> loaded = completeScheduleRepository.findByStartAndEnd(schedule.start, schedule.end)

        if (loaded) {
            if (loaded.size() > 1) {
                new RuntimeException("Too many somehow")
            }
            schedule.id = loaded[0].id
        }

        completeScheduleRepository.save(schedule)
    }

    //  TODO - verify this value
    @CacheEvict(value = "analysis,analysisTypes", allEntries = true)
    public Analysis saveOrUpdateAnalysis(final Analysis analysis) {
        List<Analysis> loaded = analysisRepository.findByStartAndEndAndAnalysisType(analysis.start, analysis.end, analysis.analysisType)

        if (loaded) {
            if (loaded.size() > 1) {
                new RuntimeException("Too many somehow")
            }
            analysis.id = loaded[0].id
        }

        analysisRepository.save(analysis)
    }
}
