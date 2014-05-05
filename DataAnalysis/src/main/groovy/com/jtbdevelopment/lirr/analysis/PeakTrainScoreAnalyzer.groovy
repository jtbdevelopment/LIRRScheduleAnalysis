package com.jtbdevelopment.LIRR.analysis

import com.jtbdevelopment.LIRR.dataobjects.analysis.Analysis
import com.jtbdevelopment.LIRR.dataobjects.core.Direction
import com.jtbdevelopment.LIRR.dataobjects.core.Station
import groovyx.gpars.GParsPool
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.springframework.stereotype.Component

/**
 * Date: 3/19/14
 * Time: 11:45 PM
 */
@Component
class PeakTrainScoreAnalyzer implements AnalysisAnalyzer {
    public static final String PEAK_TRAIN_SCORE_PENN = "Peak Train Score (Penn)"
    static final String SCORE = "Score"
    public static final List<String> DETAILS_PER_GROUP = [
            SCORE,
            PeakTrainAnalyzer.NUMBER_OF_PEAK_TRAINS,
            PeakTrainAnalyzer.AVERAGE_RIDE_TIME,
            PeakTrainAnalyzer.MPH,
            PeakTrainAnalyzer.AVERAGE_WAIT_BETWEEN_PEAKS,
            PeakTrainAnalyzer.LONGEST_WAIT_BETWEEN_PEAKS,
            PeakTrainAnalyzer.STD_DEV_WAIT_BETWEEN_PEAKS,
            PeakTrainAnalyzer.MEDIAN_WAIT_BETWEEN_PEAKS,
    ]

    public static final Map<String, List<String>> GROUPS_PER_DIRECTION;

    static {
        GROUPS_PER_DIRECTION = [
                (Direction.East.toString()): [Analysis.OVERALL],
                (Direction.West.toString()): [Analysis.OVERALL],
                (Analysis.OVERALL)         : [Analysis.OVERALL]
        ]
    }

    @Override
    Analysis analyze(final Analysis inputAnalysis) {
        Analysis result = new Analysis()
        result.analysisType = PEAK_TRAIN_SCORE_PENN
        result.end = inputAnalysis.end
        result.start = inputAnalysis.start
        result.details = [:]

        GParsPool.withPool {
            inputAnalysis.details.eachParallel {
                String direction, Map<Station, Map<String, Map<String, Object>>> analysis ->
                    result.details[direction] = scoreStations(analysis)
            }
        }

        return result
    }

    Map<Station, Map<String, Map<String, Object>>> scoreStations(
            final Map<Station, Map<String, Map<String, Object>>> input) {
        Map<Station, Map<String, Object>> stationOveralls = input.collectEntries {
            Station station, Map<String, Map<String, Object>> stationDetails ->
                [(station): stationDetails[Analysis.OVERALL].findAll { String key, Object value -> DETAILS_PER_GROUP.contains(key) }]
        }

        Map<String, List<Object>> details = DETAILS_PER_GROUP.collectEntries {
            String detailGroup ->
                [
                        (detailGroup): stationOveralls.collect {
                            Station station, Map<String, Object> details ->
                                details[detailGroup]
                        }
                ]
        }
        details.remove(SCORE)

        Map<String, List<Double>> statsPerGroup = details.collectEntries {
            String group, List<Object> groupDetails ->
                DescriptiveStatistics stats = new DescriptiveStatistics((double[]) groupDetails.toArray())
                [(group): [stats.getPercentile(25.0), stats.getPercentile(50.0), stats.getPercentile(75.0)]]
        }

        Map<Station, Map<String, Map<String, Object>>> stationStats = stationOveralls.collectEntries {
            Station station, Map<String, Object> stationDetails ->
                [
                        (station): [
                                (Analysis.OVERALL): stationDetails.collectEntries {
                                    String detail, Object value ->
                                        List<Double> stats = statsPerGroup[detail];
                                        if (detail == PeakTrainAnalyzer.MPH || detail == PeakTrainAnalyzer.NUMBER_OF_PEAK_TRAINS) {
                                            [(detail): value < stats[0] ? 1 : value < stats[1] ? 2 : value < stats[3] ? 3 : 4]
                                        } else {
                                            [(detail): value < stats[0] ? 4 : value < stats[1] ? 3 : value < stats[3] ? 2 : 1]
                                        }
                                }
                        ]
                ]

        }

        stationStats.each {
            Map.Entry<Station, Map<String, Map<String, Object>>> station ->
                Map<String, Object> map = station.value[Analysis.OVERALL]
                station.value[Analysis.OVERALL][SCORE] = map.values().sum()
        }
        stationStats
    }
}
