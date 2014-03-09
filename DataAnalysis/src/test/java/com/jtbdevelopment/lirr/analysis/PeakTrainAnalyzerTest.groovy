package com.jtbdevelopment.lirr.analysis

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Date: 3/8/14
 * Time: 9:58 PM
 */
@ContextConfiguration("/spring-context-timetables.xml")
@RunWith(SpringJUnit4ClassRunner.class)
class PeakTrainAnalyzerTest extends GroovyTestCase {
    @Autowired
    PeakTrainAnalyzer peakTrainAnalyzer

    @Test
    void testAnalyze() {
        //        Analysis analysis = peakTrainAnalyzer.analyze(scheduleForPeriod)
//        assert analysis != null
//        dataServiceUtils.saveOrUpdateAnalysis(analysis)

    }
}
