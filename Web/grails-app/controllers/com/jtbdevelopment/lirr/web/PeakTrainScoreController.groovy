package com.jtbdevelopment.LIRR.web

import com.jtbdevelopment.LIRR.analysis.PeakTrainAnalyzer
import com.jtbdevelopment.LIRR.analysis.PeakTrainScoreAnalyzer
import com.jtbdevelopment.LIRR.dao.AnalysisRepository
import com.jtbdevelopment.LIRR.dataobjects.analysis.Analysis

class PeakTrainScoreController extends AbstractFilteringAnalysisController {

    AnalysisRepository analysisRepository

    PeakTrainScoreAnalyzer peakTrainScoreAnalyzer

    def index() {
        standardIndex(PeakTrainAnalyzer.PEAK_TRAIN_ANALYSIS_PENN)
    }


    def filterAnalysis() {
        Analysis filtered = performFilter()

        Analysis scored = peakTrainScoreAnalyzer.analyze(filtered)
        respond scored,
                model: [
                        groupsPerDirection: PeakTrainScoreAnalyzer.GROUPS_PER_DIRECTION,
                        detailsPerGroup   : PeakTrainScoreAnalyzer.DETAILS_PER_GROUP,
                ]

    }

}
