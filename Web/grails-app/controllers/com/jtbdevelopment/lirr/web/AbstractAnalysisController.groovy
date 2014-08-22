package com.jtbdevelopment.lirr.web

import com.jtbdevelopment.lirr.analysis.PeakTrainAnalyzer
import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis

abstract class AbstractAnalysisController {
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

        respond analysis,
                model: [
                        groupsPerDirection: PeakTrainAnalyzer.GROUPS_PER_DIRECTION,
                        detailsPerGroup   : PeakTrainAnalyzer.DETAILS_PER_GROUP,
                ]
    }
}
