package com.jtbdevelopment.lirr.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Date: 2/23/14
 * Time: 6:10 PM
 */
@Component
class DataService {
    @Autowired
    private AnalysisRepository analysisRepository

    @Autowired
    private CompleteScheduleRepository completeScheduleRepository


}
