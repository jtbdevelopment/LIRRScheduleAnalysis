package com.jtbdevelopment.lirr.timetableprocessor

import com.jtbdevelopment.lirr.analysis.PeakTrainAnalyzer
import com.jtbdevelopment.lirr.analysis.PeakTrainScoreAnalyzer
import com.jtbdevelopment.lirr.dao.AnalysisRepository
import com.jtbdevelopment.lirr.dao.CompleteScheduleRepository
import com.jtbdevelopment.lirr.dao.DataServiceUtils
import com.jtbdevelopment.lirr.dataobjects.core.Direction
import com.jtbdevelopment.lirr.dataobjects.core.Station
import com.jtbdevelopment.lirr.dataobjects.parsing.ProcessedPDFSchedule
import com.jtbdevelopment.lirr.dataobjects.schedule.CompleteSchedule
import com.jtbdevelopment.lirr.dataobjects.schedule.TrainSchedule
import groovyx.gpars.GParsPool
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
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

    @Autowired
    CompleteScheduleRepository completeScheduleRepository

    @Autowired
    DataServiceUtils dataServiceUtils

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
                    "BabylonBranch2014022420140518.pdf",
                    "FarRockawayBranch2014022420140518.pdf",
                    "HempsteadBranch2014022420140518.pdf",
                    "LongBeachBranch2014022420140518.pdf",
                    "MontaukBranch2014022420140518.pdf",
                    "OysterBayBranch2014022420140518.pdf",
                    "RonkonkomaBranch2014022420140518.pdf",
                    "PortJeffersonBranch2014022420140518.pdf",
                    "PortWashingtonBranch2014022420140518.pdf",
                    "WestHempsteadBranch2014022420140518.pdf"
            ].collectParallel {
                pdfProcessor.parse(ScheduleCreatorTest.classLoader.getResourceAsStream(it), it)
            }
        }

        DateTime before = DateTime.now()
        CompleteSchedule scheduleForPeriod = scheduleCreator.createFrom(schedules as Set)

        assert scheduleForPeriod != null
        assert new LocalDate(2014, 5, 18) == scheduleForPeriod.end
        assert new LocalDate(2014, 2, 24) == scheduleForPeriod.start
        assert before.compareTo(scheduleForPeriod.processed) < 0
        assert [
                "Babylon Branch Timetable"        : DateTime.parse("2014-02-03T18:47:18.000Z"),
                "Far Rockaway Branch Timetable"   : DateTime.parse("2014-02-03T18:47:51.000Z"),
                "Hempstead Branch Timetable"      : DateTime.parse("2014-02-03T18:48:12.000Z"),
                "Long Beach Branch Timetable"     : DateTime.parse("2014-02-03T18:48:25.000Z"),
                "Montauk Branch Timetable"        : DateTime.parse("2014-02-03T18:48:46.000Z"),
                "Oyster Bay Branch Timetable"     : DateTime.parse("2014-02-03T18:49:02.000Z"),
                "Ronkonkoma Branch Timetable"     : DateTime.parse("2014-02-03T18:49:55.000Z"),
                "Port Jefferson Branch Timetable" : DateTime.parse("2014-02-03T18:49:21.000Z"),
                "Port Washington Branch Timetable": DateTime.parse("2014-02-03T18:49:45.000Z"),
                "West Hempstead Branch Timetable" : DateTime.parse("2014-02-06T19:34:57.000Z")] == scheduleForPeriod.inputFeeds
        //  Select schedules
        TrainSchedule train124 = scheduleForPeriod.schedules.get("124")
        assert train124.ignore

        TrainSchedule train1054 = scheduleForPeriod.schedules["1054"]
        assert Direction.East == train1054.direction
        assert train1054.peak
        assert train1054.weekday
        assert "1054" == train1054.trainNumber
        assert [
                (Station.PENN_STATION)                        : new LocalTime(16, 37),
                (Station.STATION_NAME_MAP["ROCKVILLE CENTRE"]): new LocalTime(17, 12),
                (Station.STATION_NAME_MAP["BALDWIN"])         : new LocalTime(17, 15),
                (Station.STATION_NAME_MAP["FREEPORT"])        : new LocalTime(17, 18),
                (Station.STATION_NAME_MAP["MERRICK"])         : new LocalTime(17, 21),
                (Station.STATION_NAME_MAP["BELLMORE"])        : new LocalTime(17, 24),
                (Station.STATION_NAME_MAP["WANTAGH"])         : new LocalTime(17, 27),
        ] == train1054.stops

        assert !scheduleForPeriod.id
        dataServiceUtils.saveOrUpdateSchedule(scheduleForPeriod)
        assert scheduleForPeriod.id
    }

    @Autowired
    PeakTrainAnalyzer analyzer

    @Test
    void testAnalysis() {
        completeScheduleRepository.findAll().each {
            CompleteSchedule schedule ->
                dataServiceUtils.saveOrUpdateAnalysis(analyzer.analyze(schedule))
        }
    }

    @Autowired
    PeakTrainScoreAnalyzer peakTrainScoreAnalyzer

    @Autowired
    AnalysisRepository analysisRepository

    @Test
    void testScoreAnalysis() {
        analysisRepository.findAll().find { it.analysisType == PeakTrainAnalyzer.PEAK_TRAIN_ANALYSIS_PENN }.each {
            peakTrainScoreAnalyzer.analyze(it)
        }
    }

}
