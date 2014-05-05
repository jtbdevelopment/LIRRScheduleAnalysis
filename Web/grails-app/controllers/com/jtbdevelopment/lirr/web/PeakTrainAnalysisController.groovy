package com.jtbdevelopment.LIRR.web

import com.jtbdevelopment.LIRR.analysis.PeakTrainAnalyzer
import com.jtbdevelopment.LIRR.dao.AnalysisRepository
import com.jtbdevelopment.LIRR.dataobjects.analysis.Analysis

class PeakTrainAnalysisController extends AbstractFilteringAnalysisController {

    AnalysisRepository analysisRepository

    def index() {
        standardIndex(PeakTrainAnalyzer.PEAK_TRAIN_ANALYSIS_PENN)
    }

    def filterAnalysis() {
        Analysis filtered = performFilter()

        respond filtered,
                model: [
                        groupsPerDirection: PeakTrainAnalyzer.GROUPS_PER_DIRECTION,
                        detailsPerGroup   : PeakTrainAnalyzer.DETAILS_PER_GROUP,
                ]

    }
}
