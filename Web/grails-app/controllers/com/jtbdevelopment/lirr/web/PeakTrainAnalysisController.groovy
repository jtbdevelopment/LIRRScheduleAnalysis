package com.jtbdevelopment.lirr.web

import com.jtbdevelopment.lirr.analysis.PeakTrainAnalyzer
import com.jtbdevelopment.lirr.dao.AnalysisRepository
import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis
import com.jtbdevelopment.lirr.dataobjects.core.Direction
import com.jtbdevelopment.lirr.dataobjects.core.Station

class PeakTrainAnalysisController {

    AnalysisRepository analysisRepository

    def index() {
        List<Analysis> analysisList = analysisRepository.findByAnalysisTypeOrderByStartDesc(PeakTrainAnalyzer.PEAK_TRAIN_ANALYSIS) as List
        Map<String, String> options = analysisList.collectEntries() {
            Analysis analysis ->
                [(analysis.id): analysis.start.toString("MMM dd, YYYY") + " to " + analysis.end.toString("MMM dd, YYYY")]
        }
        respond options
    }

    def show(final String id, String zone) {
        Analysis analysis = analysisRepository.findOne(id)
        if (!(zone.toUpperCase() == "ALL")) {
            int zoneNumeric = zone.toInteger()
            analysis.details =
                    analysis.details.collectEntries {
                        Direction direction, Map<Station, Map<String, Map<String, Object>>> details ->
                            [(direction):
                                    details.findAll {
                                        it.key.zone.numeric == zoneNumeric
                                    }.collectEntries {
                                        it
                                    }
                            ]
                    }
        }
        respond analysis,
                model: [
                        groupsPerDirection: PeakTrainAnalyzer.GROUPS_PER_DIRECTION,
                        detailsPerGroup: PeakTrainAnalyzer.DETAILS_PER_GROUP,
                ]
    }
}
