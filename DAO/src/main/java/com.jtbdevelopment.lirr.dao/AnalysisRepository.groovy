package com.jtbdevelopment.lirr.dao

import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis
import org.joda.time.LocalDate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Date: 2/23/14
 * Time: 4:33 PM
 */
@Repository
interface AnalysisRepository extends CrudRepository<Analysis, String> {
    List<Analysis> findByStartAndEndAndAnalysisType(
            final LocalDate start, final LocalDate end, final String analysisType)
}
