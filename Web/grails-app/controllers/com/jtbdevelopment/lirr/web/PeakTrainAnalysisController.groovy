package com.jtbdevelopment.lirr.web

import com.jtbdevelopment.lirr.analysis.PeakTrainAnalyzer
import com.jtbdevelopment.lirr.dao.AnalysisRepository
import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis

class PeakTrainAnalysisController extends AbstractFilteringAnalysisController {

    AnalysisRepository analysisRepository

    def index() {
        standardIndex(PeakTrainAnalyzer.PEAK_TRAIN_ANALYSIS_PENN)
    }

    def filterAnalysis() {
        Analysis filtered = performFilter()

        respond filtered,
                model: [
                        graphedHeaders: GRAPHED_HEADERS,
                        groupsPerDirection: PeakTrainAnalyzer.GROUPS_PER_DIRECTION,
                        detailsPerGroup   : PeakTrainAnalyzer.DETAILS_PER_GROUP,
                ]

    }
}
