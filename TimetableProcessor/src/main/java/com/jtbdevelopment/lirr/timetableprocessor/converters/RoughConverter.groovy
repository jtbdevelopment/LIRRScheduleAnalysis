package com.jtbdevelopment.lirr.timetableprocessor.converters

import com.jtbdevelopment.lirr.timetableprocessor.data.RoughParsedSchedule

/**
 * Date: 2/15/14
 * Time: 6:42 PM
 */
class RoughConverter {
    private final static Map<String, String> FIX_IT_PHRASES = [
            "Nostrand Avenue.": "Nostrand Avenue",
            "Train runs": "Trainruns",
            "Dec. ": "Dec.",
            "NOT run": "NOTrun",
            "Sat. & Sun.": "Sat.&Sun.",
            "thru Jan ": "thruJan",
            "Will also": "Willalso",
            "Jan ": "Jan",
            "Jan. ": "Jan."
    ]

    RoughParsedSchedule convert(final String input) {
        RoughParsedSchedule roughParsedSchedule = new RoughParsedSchedule()

        assignSchedulesToDetails(roughOutSchedules(input), roughParsedSchedule)

        return roughParsedSchedule
    }

    private static void assignSchedulesToDetails(
            final List<List<String>> rawSchedules, final RoughParsedSchedule roughParsedSchedule) {
        assert rawSchedules.size() == 4
        List<Boolean> isEast = []

        rawSchedules.each {
            List<String> schedule ->
                assert schedule.size() > 2
                String upperCase = schedule.get(2).toUpperCase()
                isEast.add((boolean) (upperCase =~ /PENN STATION/))
        }
        List<Integer> eastIndices = isEast.findIndexValues { it }.collect { it.intValue() }
        List<Integer> westIndices = isEast.findIndexValues { !it }.collect { it.intValue() }
        assert eastIndices.size() == 2
        assert westIndices.size() == 2

        Closure<Boolean> isTimeColumn = { String part -> part =~ /:/ || part =~ /\.\./ }
        int eastSize1 = rawSchedules.get(eastIndices.get(0)).get(2).tokenize().findAll(isTimeColumn).size()
        int eastSize2 = rawSchedules.get(eastIndices.get(1)).get(2).tokenize().findAll(isTimeColumn).size()
        if (eastSize1 > eastSize2) {
            roughParsedSchedule.eastboundWeekdays = rawSchedules.get(eastIndices.get(0))
            roughParsedSchedule.eastboundWeekends = rawSchedules.get(eastIndices.get(1))
        } else {
            roughParsedSchedule.eastboundWeekdays = rawSchedules.get(eastIndices.get(1))
            roughParsedSchedule.eastboundWeekends = rawSchedules.get(eastIndices.get(0))
        }
        int westSize1 = rawSchedules.get(westIndices.get(0)).get(2).tokenize().findAll(isTimeColumn).size()
        int westSize2 = rawSchedules.get(westIndices.get(1)).get(2).tokenize().findAll(isTimeColumn).size()
        if (westSize1 > westSize2) {
            roughParsedSchedule.westboundWeekdays = rawSchedules.get(westIndices.get(0))
            roughParsedSchedule.westboundWeekends = rawSchedules.get(westIndices.get(1))
        } else {
            roughParsedSchedule.westboundWeekdays = rawSchedules.get(westIndices.get(1))
            roughParsedSchedule.westboundWeekends = rawSchedules.get(westIndices.get(0))
        }
    }

    private static List<List<String>> roughOutSchedules(final String input) {
        String fixed = input;
        FIX_IT_PHRASES.each {
            String oldPhase, String newPhase ->
                fixed = fixed.replaceAll(oldPhase, newPhase)
        }

        boolean insideSchedule = false
        boolean grabTrainIDs = false
        List<List<String>> rawSchedules = [];
        List<String> currentSchedule = [];
        List<String> lastSchedule = currentSchedule
        for (String line : fixed.tokenize("\n")) {
            if (line.startsWith("AM AM AM") || line.startsWith("AM AM PM PM PM PM PM PM")) {
                if (insideSchedule) {
                    insideSchedule = false
                    currentSchedule.add(line)
                    rawSchedules.add(currentSchedule)
                    lastSchedule = currentSchedule
                    currentSchedule = []
                    grabTrainIDs = true
                    continue
                }
                insideSchedule = true
                currentSchedule.add(line)
                continue
            }
            if (insideSchedule) {
                currentSchedule.add(line)
            } else if (grabTrainIDs && line.contains("Train #")) {
                grabTrainIDs = false
                lastSchedule.add(0, line)
            }

        }
        rawSchedules
    }

}
