package com.jtbdevelopment.lirr.web

import com.jtbdevelopment.lirr.analysis.PeakTrainAnalyzer
import com.jtbdevelopment.lirr.analysis.PeakTrainScoreAnalyzer
import com.jtbdevelopment.lirr.dao.AnalysisRepository
import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis

class PeakTrainAnalysisController extends AbstractFilteringAnalysisController {

    AnalysisRepository analysisRepository

    def setup() {
        redirect(action: "index");
    }

    def index() {
        standardIndex(PeakTrainAnalyzer.PEAK_TRAIN_ANALYSIS_PENN)
    }

    def filterAnalysis() {
        Analysis filtered = performFilter()

        respond filtered,
                model: [
                        groupsPerDirection: PeakTrainScoreAnalyzer.GROUPS_PER_DIRECTION,
                        detailsPerGroup   : PeakTrainScoreAnalyzer.DETAILS_PER_GROUP,
                ]

    }
}
