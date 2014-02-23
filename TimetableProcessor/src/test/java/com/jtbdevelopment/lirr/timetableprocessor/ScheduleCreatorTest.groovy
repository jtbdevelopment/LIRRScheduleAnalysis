package com.jtbdevelopment.lirr.timetableprocessor

import com.jtbdevelopment.lirr.analysis.PeakAnalysis
import com.jtbdevelopment.lirr.dataobjects.Direction
import com.jtbdevelopment.lirr.dataobjects.ScheduleForPeriod
import com.jtbdevelopment.lirr.dataobjects.Station
import com.jtbdevelopment.lirr.dataobjects.Zone
import com.jtbdevelopment.lirr.timetableprocessor.data.ParsedSchedule
import groovyx.gpars.GParsPool
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
        List<ParsedSchedule> schedules = []
        GParsPool.withPool {
            schedules = [
                    "BabylonBranch2013121620140223.pdf",
                    "FarRockawayBranch2013121620140223.pdf",
                    "HempsteadBranch2013121620140223.pdf",
                    "LongBeachBranch2013121620140223.pdf",
                    "MontaukBranch2013121620140223.pdf",
                    "OysterBayBranch2013121620140223.pdf",
                    "PortJeffersonBranch2013121620140223.pdf",
                    "PortWashingtonBranch2013121620140223.pdf",
                    "RonkonkomaBranch2013121620140223.pdf",
                    "WestHempsteadBranch2013121620140223.pdf"].collectParallel {
                pdfProcessor.parse(ScheduleCreatorTest.classLoader.getResourceAsStream(it))
            }
        }

        ScheduleCreator scheduleCreator = new ScheduleCreator()
        ScheduleForPeriod scheduleForPeriod = scheduleCreator.createFrom(schedules as Set)
        printAnalysis(new PeakAnalysis().analyzeForZone(scheduleForPeriod, Zone.Zone7, Direction.West))
        printAnalysis(new PeakAnalysis().analyzeForZone(scheduleForPeriod, Zone.Zone7, Direction.East))
    }

    private void printAnalysis(Map<Station, Map<String, Object>> zoneDirectionAnalysis) {
        println "Analysis-----------"
        zoneDirectionAnalysis.each {
            println it.key
            it.value.each {
                println "   " + it.key + ":" + it.value
            }
        }
        println "Analysis-----------"
    }


}
