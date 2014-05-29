package com.jtbdevelopment.lirr.web

import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis
import com.jtbdevelopment.lirr.dataobjects.core.Line
import com.jtbdevelopment.lirr.dataobjects.core.Station
import com.jtbdevelopment.lirr.dataobjects.core.Zone

abstract class AbstractFilteringAnalysisController {

    abstract def filterAnalysis()

    protected Analysis performFilter() {
        def lines = params['lines[]'] as Set ?: Line.values().collect { it.name } as Set;
        def zones = params['zones[]'] ?
                params['zones[]'].collect { String zone -> Integer.parseInt(zone) } as Set :
                Zone.values().collect { it.numeric } as Set
        def minDistance = Integer.parseInt(((String) params['minDistance']) ?: '0')
        def maxDistance = Integer.parseInt(((String) params['maxDistance']) ?: '117')

        Analysis analysis = session["analysis"]
        Set<String> linesAsSet = lines as Set
        Set<Integer> zonesAsSet = zones as Set
        Analysis filtered = analysis.clone()
        filtered.details = filtered.details.collectEntries {
            String direction, Map<Station, Map<String, Map<String, Object>>> stations ->
                [
                        (direction): stations.findAll {
                            Station station, Map<String, Map<String, Object>> details ->
                                linesAsSet.contains(station.line.name) &&
                                        zonesAsSet.contains(station.zone.numeric) &&
                                        station.milesToPenn >= minDistance &&
                                        station.milesToPenn <= maxDistance
                        }
                ]
        }
        filtered
    }

    protected void standardIndex(String peak_train_analysis_penn) {
        List<Analysis> analysisList = analysisRepository.findByAnalysisTypeOrderByStartDesc(peak_train_analysis_penn) as List
        Map<String, String> options = analysisList.collectEntries() {
            Analysis analysis ->
                [(analysis.id): analysis.start.toString("MMM dd, YYYY") + " to " + analysis.end.toString("MMM dd, YYYY")]
        }
        respond options
    }

    def show(final String id) {
        Analysis analysis = analysisRepository.findOne(id)
        session["analysis"] = analysis;
        filterAnalysis()
    }
}
