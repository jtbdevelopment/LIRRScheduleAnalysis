package com.jtbdevelopment.lirr.timetableprocessor

import com.jtbdevelopment.lirr.dataobjects.ScheduleForPeriod
import com.jtbdevelopment.lirr.timetableprocessor.data.ParsedSchedule
import org.joda.time.LocalDate

/**
 * Date: 2/18/14
 * Time: 6:55 AM
 */
class ScheduleCreatorTest extends GroovyTestCase {
    void testExceptionOnMismatch() {
        LocalDate from = LocalDate.now().minusDays(20)
        LocalDate to = LocalDate.now()
        Set<ParsedSchedule> inputs = (1..5).collect {
            def i ->
                def schedule = new ParsedSchedule(from: from, to: to, title: i.toString())
                if (i == 5) {
                    schedule.to = LocalDate.now().minusDays(1)
                }
                schedule
        } as Set
        shouldFail {
            new ScheduleCreator().createFrom(inputs)
        }
    }

    void testCreateFrom() {
        PDFProcessor pdfProcessor = new PDFProcessor()
        List<ParsedSchedule> schedules = [
                "BabylonBranch2013121620140223.pdf",
                "FarRockawayBranch2013121620140223.pdf",
                "HempsteadBranch2013121620140223.pdf",
                "LongBeachBranch2013121620140223.pdf",
                "MontaukBranch2013121620140223.pdf",
                "OysterBayBranch2013121620140223.pdf",
                "PortJeffersonBranch2013121620140223.pdf",
                "PortWashingtonBranch2013121620140223.pdf",
                "RonkonkomaBranch2013121620140223.pdf",
                "WestHempsteadBranch2013121620140223.pdf"].collect {
            pdfProcessor.parse(ScheduleCreatorTest.classLoader.getResourceAsStream(it))
        }

        ScheduleCreator scheduleCreator = new ScheduleCreator()
        ScheduleForPeriod scheduleForPeriod = scheduleCreator.createFrom(schedules as Set)
        println scheduleForPeriod
    }
}
