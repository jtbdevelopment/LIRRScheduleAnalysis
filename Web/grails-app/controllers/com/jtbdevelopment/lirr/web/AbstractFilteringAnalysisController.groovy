package com.jtbdevelopment.lirr.web

import com.jtbdevelopment.lirr.dataobjects.analysis.Analysis
import com.jtbdevelopment.lirr.dataobjects.core.Line
import com.jtbdevelopment.lirr.dataobjects.core.Station
import com.jtbdevelopment.lirr.dataobjects.core.Zone

import javax.servlet.http.Cookie

abstract class AbstractFilteringAnalysisController {
    public final static List<String> GRAPHED_HEADERS = [
            "# of Peak Trains",
            "Avg Ride Time",
            "MPH",
            "Avg Wait Between Peaks",
            "Longest Wait Between Peaks",
            "Std Dev Wait Between Peaks",
            "Median Wait Between Peaks"
    ];


    public static final String LINE_COOKIE = "com.jtbdevelopment.lirr.Web.lines"
    public static final String ZONE_COOKIE = "com.jtbdevelopment.lirr.Web.zones"
    public static final String MIN_COOKIE = "com.jtbdevelopment.lirr.Web.min"
    public static final String MAX_COOKIE = "com.jtbdevelopment.lirr.Web.max"

    abstract def filterAnalysis()

    private Cookie createCookie(final String key, final String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("/")
        cookie
    }

    protected String getCookie(final String key) {
        request.cookies.find { cookie -> cookie.name == key }?.value
    }

    protected Analysis performFilter() {
        String[] lineCookie = getCookie(LINE_COOKIE)?.replaceAll("\\[", "")?.replaceAll("]", "")?.replace(", ", ",")?.split(",")
        String[] zoneCookie = getCookie(ZONE_COOKIE)?.replaceAll("\\[", "")?.replaceAll("]", "")?.replace(", ", ",")?.split(",")
        def lines = params['lines[]'] as Set ?: Line.values().collect {
            it.name
        } as Set;
        def zones = params['zones[]'] ?
                params['zones[]'].collect { String zone -> Integer.parseInt(zone) } as Set :
                Zone.values().collect { it.numeric } as Set
        def minDistance = Integer.parseInt(((String) params['minDistance']) ?: '0')
        def maxDistance = Integer.parseInt(((String) params['maxDistance']) ?: '117')
        request.cookies.each { println it.name + "/" + it.value + "/" + it.value.class.getName() }
        response.addCookie(createCookie(LINE_COOKIE, lines.toString()))
        response.addCookie(createCookie(ZONE_COOKIE, zones.toString()))
        response.addCookie(createCookie(MIN_COOKIE, minDistance.toString()))
        response.addCookie(createCookie(MAX_COOKIE, maxDistance.toString()))

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
