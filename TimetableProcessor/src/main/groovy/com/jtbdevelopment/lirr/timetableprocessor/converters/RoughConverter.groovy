package com.jtbdevelopment.lirr.timetableprocessor.converters

import com.jtbdevelopment.lirr.dataobjects.core.Station
import com.jtbdevelopment.lirr.dataobjects.parsing.ParsedPDFSchedule
import org.springframework.stereotype.Component

/**
 * Date: 2/15/14
 * Time: 6:42 PM
 */
@Component
class RoughConverter {
    //  TODO - this needs to go in DB, would also be good if it was more dynamic
    //  such as able to determine a column was station names by mostly matching entries
    //  and not just exact match and determining text phrases
    private final static Map<String, String> FIX_IT_PHRASES = [
            "Nostrand Avenue."      : "Nostrand Avenue",
            "ATLANTIC TERMINA"      : "ATLANTIC TERMINAL",
            "ATLANTIC TERMINALL"    : "ATLANTIC TERMINAL",  // Correct for above
            "HUNTERSPOINT"          : "HUNTERSPOINT AVE.",
            "HUNTERSPOINT AVE. AVE.": "HUNTERSPOINT AVE.",  // Correct for above
            "Train runs"            : "Trainruns",
            "Dec. "                 : "Dec.",
            "NOT run"               : "NOTrun",
            "Sat. & Sun."           : "Sat.&Sun.",
            "thru Jan "             : "thruJan",
            "Will also"             : "Willalso",
            "Jan "                  : "Jan",
            "Jan. "                 : "Jan.",
            "Sun. &"                : "Sun.&",
            "WILL NOT"              : "WILLNOT",
            "run July 4"            : "runJuly4",
            "or Aug. 31."           : "orAug.31.",
            "May 26 -"              : "May26-",
            "Sept. 1."              : "Sept.1.",
            "Oct. 12."              : "Oct.12.",
            "Oct. 10."              : "Oct.10.",
            "May 22 -"              : "May22-",
            "Aug. 28."              : "Aug.28.",
            "\\(except will"        : "(exceptwill",
            "July 3.\\)"            : "July 3.)",
            "run July 2."           : "runJuly2.",
            "July 4"                : "July4",
            "May 23 -"              : "May23-",
            "Aug. 29."              : "Aug.29.",
            "July 3."               : "July3.",
    ]

    ParsedPDFSchedule convert(final String input) {
        ParsedPDFSchedule roughParsedSchedule = new ParsedPDFSchedule()

        roughParsedSchedule.subject = findEffectiveDate(input);

        assignSchedulesToDetails(roughOutSchedules(input), roughParsedSchedule)

        return roughParsedSchedule
    }

    private static String findEffectiveDate(final String input) {
        input.tokenize("\n").find { String line -> line.startsWith("Effective") }
    }

    private static void assignSchedulesToDetails(
            final List<List<String>> rawSchedules,
            final ParsedPDFSchedule roughParsedSchedule) {
        assert rawSchedules.size() == 4
        List<Boolean> isEast = []

        rawSchedules.each {
            List<String> schedule ->
                assert schedule.size() > 2
                String upperCase = schedule.get(2).toUpperCase()
                isEast.add((boolean) (upperCase =~ Station.PENN_STATION_NAME))
        }
        List<Integer> eastIndices = isEast.findIndexValues { it }.collect { it.intValue() }
        List<Integer> westIndices = isEast.findIndexValues { !it }.collect { it.intValue() }
        assert eastIndices.size() == 2
        assert westIndices.size() == 2

        roughParsedSchedule.eastbound1 = rawSchedules[eastIndices.first()]
        roughParsedSchedule.eastbound2 = rawSchedules[eastIndices.last()]
        roughParsedSchedule.westbound1 = rawSchedules[westIndices.first()]
        roughParsedSchedule.westbound2 = rawSchedules[westIndices.last()]
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
