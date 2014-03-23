package com.jtbdevelopment.lirr.web

import com.jtbdevelopment.lirr.analysis.PeakTrainAnalyzer
import com.jtbdevelopment.lirr.dao.AnalysisRepository
import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis

class PeakTrainAnalysisController {

    AnalysisRepository analysisRepository

    def setup() {
        redirect(action: "index");
    }

    def index() {
        List<Analysis> analysisList = analysisRepository.findByAnalysisTypeOrderByStartDesc(PeakTrainAnalyzer.PEAK_TRAIN_ANALYSIS_PENN) as List
        Map<String, String> options = analysisList.collectEntries() {
            Analysis analysis ->
                [(analysis.id): analysis.start.toString("MMM dd, YYYY") + " to " + analysis.end.toString("MMM dd, YYYY")]
        }
        respond options
    }

    def show(final String id) {
        Analysis analysis = analysisRepository.findOne(id)
        respond analysis,
                model: [
                        groupsPerDirection: PeakTrainAnalyzer.GROUPS_PER_DIRECTION,
                        detailsPerGroup: PeakTrainAnalyzer.DETAILS_PER_GROUP,
                ]
    }
}
