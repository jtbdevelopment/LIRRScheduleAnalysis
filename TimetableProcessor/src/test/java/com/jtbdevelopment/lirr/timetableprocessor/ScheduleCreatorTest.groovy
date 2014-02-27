package com.jtbdevelopment.lirr.timetableprocessor

import com.jtbdevelopment.lirr.dataobjects.parsing.ProcessedPDFSchedule
import com.jtbdevelopment.lirr.dataobjects.schedule.CompleteSchedule
import groovyx.gpars.GParsPool
import org.joda.time.LocalDate
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Date: 2/18/14
 * Time: 6:55 AM
 */
@ContextConfiguration("/spring-context-timetables.xml")
@RunWith(SpringJUnit4ClassRunner.class)
class ScheduleCreatorTest extends GroovyTestCase {
    @Autowired
    PDFProcessor pdfProcessor

    @Autowired
    ScheduleCreator scheduleCreator

    @Test
    void testExceptionOnMismatch() {
        LocalDate from = LocalDate.now().minusDays(20)
        LocalDate to = LocalDate.now()
        Set<ProcessedPDFSchedule> inputs = (1..5).collect {
            def i ->
                def schedule = new ProcessedPDFSchedule(from: from, to: to, title: i.toString())
                if (i == 5) {
                    schedule.to = LocalDate.now().minusDays(1)
                }
                schedule
        } as Set
        shouldFail {
            new ScheduleCreator().createFrom(inputs)
        }
    }

    @Test
    void testCreateFrom() {
        List<ProcessedPDFSchedule> schedules = []
        GParsPool.withPool {
            schedules = [
                    "BabylonBranch2013121620140223.pdf",
                    "FarRockawayBranch2013121620140223.pdf",
                    "HempsteadBranch2013121620140223.pdf",
                    "LongBeachBranch2013121620140223.pdf",
                    "MontaukBranch2013121620140223.pdf",
                    "OysterBayBranch2013121620140223.pdf",
                    "RonkonkomaBranch2013121620140223.pdf",
                    "PortJeffersonBranch2013121620140223.pdf",
                    "PortWashingtonBranch2013121620140223.pdf",
                    "WestHempsteadBranch2013121620140223.pdf"
            ].collectParallel {
                pdfProcessor.parse(ScheduleCreatorTest.classLoader.getResourceAsStream(it))
            }
        }

        CompleteSchedule scheduleForPeriod = scheduleCreator.createFrom(schedules as Set)
        Thread.sleep(100000)
        throw new RuntimeException("")

    }

}
