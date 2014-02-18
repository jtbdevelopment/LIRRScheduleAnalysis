package com.jtbdevelopment.lirr.timetableprocessor.converters

import com.jtbdevelopment.lirr.dataobjects.Station
import com.jtbdevelopment.lirr.timetableprocessor.data.ParsedSchedule
import com.jtbdevelopment.lirr.timetableprocessor.data.RoughParsedSchedule
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat

import java.util.regex.Pattern

/**
 * Date: 2/15/14
 * Time: 6:56 PM
 */
class FinalConverter {

    public static final String FORMAT_STRING = "MMMM dd, yyyy"

    public static final Pattern STATION_NAMES =
            Pattern.compile(
                    "^(" + Station.STATIONS.collect {
                        it.name.toUpperCase()
                    }.join('|') + ")")

    ParsedSchedule convert(final RoughParsedSchedule roughParsedSchedule) {
        ParsedSchedule parsedSchedule = new ParsedSchedule()
        parsedSchedule.title = roughParsedSchedule.title
        parsedSchedule.modified = DateTime.parse(roughParsedSchedule.modified)

        parseEffectiveDates(roughParsedSchedule, parsedSchedule)
        parsedSchedule.westboundWeekdays = parseTimes(roughParsedSchedule.westboundWeekdays)
        parsedSchedule.westboundWeekends = parseTimes(roughParsedSchedule.westboundWeekends)
        parsedSchedule.eastboundWeekends = parseTimes(roughParsedSchedule.eastboundWeekends)
        parsedSchedule.eastboundWeekdays = parseTimes(roughParsedSchedule.eastboundWeekdays)

        return parsedSchedule;
    }

    Map<String, Set<LocalTime>> parseTimes(final List<String> times) {
        if (times.size() < 3) {
            throw new RuntimeException("Unexpected size")
        }
        List<List<String>> stringMatrix = breakIntoStringMatrix(times)
        return convertToTimes(stringMatrix)
    }

    private static Map<String, Set<LocalTime>> convertToTimes(final List<List<String>> matrix) {
        List<List<LocalTime>> matrixOfTimes = matrix.findAll { !it.get(0).contains("AM/PM") }.collect {
            List<String> row ->
                row.subList(1, row.size()).collect {
                    String time ->
                        if (time.contains(":")) {
                            return LocalTime.parse(time, DateTimeFormat.forPattern("hh:mm"))
                        }
                        null
                }
        }
        List<String> startAMPMs = matrix.get(0).subList(1, matrix.get(0).size())
        List<String> endAMPMs = matrix.get(1).subList(1, matrix.get(1).size())
        (1..matrixOfTimes.get(0).size()).each {
            int columnP1 ->
                int column = columnP1 - 1
                String startAMPM = startAMPMs.get(column)
                String endAMPM = endAMPMs.get(column)
                if (startAMPM == "PM") {
                    (1..matrixOfTimes.size()).each {
                        int row ->
                            List<LocalTime> timeRow = matrixOfTimes.get(row - 1)
                            LocalTime time = timeRow.get(column)
                            if (time != null) {
                                timeRow.set(column, time.plusHours(12))
                            }
                    }
                }
                if (startAMPM != endAMPM) {
                    if (startAMPM == "AM") {
                        determineAMPMForCrossoverTrains(matrixOfTimes, column, 12)
                    } else {
                        determineAMPMForCrossoverTrains(matrixOfTimes, column, -12)
                    }
                }
        }

        Map<String, Set<LocalTime>> result = [:]
        (3..matrix.size()).each {
            int row ->
                String station = matrix.get(row - 1).get(0)
                result.put(station, matrixOfTimes.get(row - 3).findAll { it != null } as Set)
        }
        result
    }

    private static void determineAMPMForCrossoverTrains(
            final List<List<LocalTime>> matrixOfTimes, int column, int adjustBy) {
        LocalTime lastTime = null
        boolean startFlipping = false
        (1..matrixOfTimes.size()).each {
            int row ->
                List<LocalTime> timeRow = matrixOfTimes.get(row - 1)
                LocalTime currentTime = timeRow.get(column);
                if (lastTime == null) {
                    lastTime = currentTime
                } else {
                    if (currentTime) {
                        if (lastTime.compareTo(currentTime) > 0) {
                            startFlipping = true
                        }
                        if (startFlipping) {
                            currentTime = currentTime.plusHours(adjustBy)
                            timeRow.set(column, currentTime)
                        }
                    }
                }
        }
    }

    private static List<List<String>> breakIntoStringMatrix(final List<String> times) {
        List<List<String>> matrix = []

        List<String> startAMPMs = times.get(0).tokenize().findAll { it == "AM" || it == "PM" }
        List<String> endAMPMs = times.get(times.size() - 1).tokenize().findAll { it == "AM" || it == "PM" }
        startAMPMs.add(0, "Start AM/PM")
        endAMPMs.add(0, "End AM/PM")
        matrix.add(startAMPMs)
        matrix.add(endAMPMs)


        ArrayList<String> stationRows = times.findAll { !it.contains("PM") }.collect {
            it.toUpperCase()
        }
        String lastName = ""
        List<String> names = stationRows.collect {
            def m = it =~ STATION_NAMES
            if (m) {
                lastName = m[0][1]
            }
            return lastName;
        }
        assert names.size() == stationRows.size()

        stationRows = (1..names.size()).collect {
            int i ->
                stationRows.get(i - 1).replaceAll(names.get(i - 1), "")
        }

        stationRows.findIndexValues { it.contains("(ARRIVE)") }.reverseEach {
            Number index ->
                stationRows.remove(index.intValue())
                names.remove(index.intValue())
        }

        List<List<String>> tokenizedRows = stationRows.collect() {
            it.tokenize().findAll {
                !it.equals("A") && !it.equals("B") && !it.equals("C") && !it.equals("E") &&
                        !it.equals("H") && !it.equals("BE") && !it.equals("AE") && !it.equals("BT") && !it.equals("AT") &&
                        !it.equals("J") && !it.equals("T") && !it.equals("(NOTE)") && !it.equals("(LEAVE)")
            }.collect {
                if (it.contains(":") || it.startsWith("...")) {
                    return it
                }
                "TEXT"
            }
        }
        (1..names.size()).each {
            int row ->
                List<String> tokenizedRow = tokenizedRows.get(row - 1)
                tokenizedRow.add(0, names.get(row - 1))
                matrix.add(tokenizedRow)
        }
        int size = matrix.get(0).size()
        matrix.each { assert size == it.size() }
        return matrix
    }

    private void parseEffectiveDates(RoughParsedSchedule roughParsedSchedule, ParsedSchedule parsedSchedule) {
        String replaced = roughParsedSchedule.subject.replace("Effective ", "")
        List<String> tokens = replaced.tokenize()
        assert tokens.size() == 7
        parsedSchedule.from = LocalDate.parse(tokens.get(0) + " " + tokens.get(1) + " " + tokens.get(2), DateTimeFormat.forPattern(FORMAT_STRING));
        parsedSchedule.to = LocalDate.parse(tokens.get(4) + " " + tokens.get(5) + " " + tokens.get(6), DateTimeFormat.forPattern(FORMAT_STRING));
    }

}
