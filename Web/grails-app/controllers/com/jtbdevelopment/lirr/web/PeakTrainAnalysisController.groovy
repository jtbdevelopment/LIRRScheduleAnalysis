package com.jtbdevelopment.lirr.web

import com.jtbdevelopment.lirr.analysis.PeakTrainAnalyzer
import com.jtbdevelopment.lirr.dao.AnalysisRepository

class PeakTrainAnalysisController extends AbstractAnalysisController {

    AnalysisRepository analysisRepository

    def index() {
        standardIndex(PeakTrainAnalyzer.PEAK_TRAIN_ANALYSIS_PENN)
    }

}
