package com.jtbdevelopment.lirr.timetableprocessor.converters

import com.jtbdevelopment.lirr.dataobjects.core.Station
import com.jtbdevelopment.lirr.dataobjects.parsing.ParsedPDFSchedule
import com.jtbdevelopment.lirr.dataobjects.parsing.ProcessedPDFSchedule
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.springframework.stereotype.Component

import java.util.regex.Pattern

/**
 * Date: 2/15/14
 * Time: 6:56 PM
 */
@Component
class FinalConverter {

    private static
    final Set<String> EXCLUDE_CELLS = ["A", "B", "C", "E", "H", "BE", "AE", "BT", "AT", "J", "T", "(NOTE)", "(LEAVE)"] as Set
    public static final String FORMAT_STRING = "MMMM dd, yyyy"

    public static final Pattern STATION_NAMES =
            Pattern.compile(
                    "^(" + Station.STATIONS.collect {
                        it.name.toUpperCase()
                    }.join('|') + ")")

    ProcessedPDFSchedule convert(final ParsedPDFSchedule roughParsedSchedule) {
        ProcessedPDFSchedule parsedSchedule = new ProcessedPDFSchedule()
        parsedSchedule.title = roughParsedSchedule.title
        parsedSchedule.modified = DateTime.parse(roughParsedSchedule.modified)

        parseEffectiveDates(roughParsedSchedule, parsedSchedule)
        parsedSchedule.eastboundWeekdays = parseTimes(roughParsedSchedule.eastboundWeekdays)
        parsedSchedule.westboundWeekdays = parseTimes(roughParsedSchedule.westboundWeekdays)
        parsedSchedule.westboundWeekends = parseTimes(roughParsedSchedule.westboundWeekends)
        parsedSchedule.eastboundWeekends = parseTimes(roughParsedSchedule.eastboundWeekends)

        return parsedSchedule;
    }

    Map<String, List<LocalTime>> parseTimes(final List<String> times) {
        if (times.size() < 3) {
            throw new RuntimeException("Unexpected size")
        }
        List<List<String>> stringMatrix = breakIntoStringMatrix(times)
        return convertToTimes(stringMatrix)
    }

    private static Map<String, List<LocalTime>> convertToTimes(final List<List<String>> matrix) {
        List<List<LocalTime>> matrixOfTimes = matrix.findAll {
            String first = it[0]
            !first.contains("AM/PM") && !first.equals("#")
        }.collect {
            List<String> row ->
                row.subList(1, row.size()).collect {
                    String time ->
                        if (time.contains(":")) {
                            return LocalTime.parse(time, DateTimeFormat.forPattern("hh:mm"))
                        }
                        null
                }
        }
        List<String> trainIDs = matrix[0].subList(1, matrix[0].size())
        List<String> startAMPMs = matrix[1].subList(1, matrix[1].size())
        List<String> endAMPMs = matrix[2].subList(1, matrix[2].size())

        (1..matrixOfTimes[0].size()).each {
            int columnP1 ->
                int column = columnP1 - 1
                String startAMPM = startAMPMs[column]
                String endAMPM = endAMPMs[column]
                if (startAMPM == "PM") {
                    (1..matrixOfTimes.size()).each {
                        int row ->
                            List<LocalTime> timeRow = matrixOfTimes[row - 1]
                            LocalTime time = timeRow[column]
                            if (time != null) {
                                timeRow.set(column, time.plusHours(12))
                            }
                    }
                }

                if (startAMPM != endAMPM) {
                    matrixOfTimes.each {
                        List<LocalTime> times ->
                            LocalTime checking = times[column]
                            if (checking &&
                                    column > 0) {
                                int delta = (times.subList(0, column).findAll {
                                    it
                                }.last().hourOfDay - checking.hourOfDay)
                                if (delta > 4 || delta < -8) {
                                    times[column] = checking.plusHours(12)
                                }
                            }
                    }
                }
        }
        Map<String, List> result = [:]
        (4..matrix.size()).each {
            int row ->
                String station = matrix[row - 1][0]
                result[(station)] = matrixOfTimes[row - 4]
        }
        result["#"] = trainIDs
        result
    }

    private static List<List<String>> breakIntoStringMatrix(final List<String> times) {

        List<String> trainIDs = times[0].replace("Train #", "").tokenize()
        List<String> startAMPMs = times[1].tokenize().findAll { it == "AM" || it == "PM" }
        List<String> endAMPMs = times[-1].tokenize().findAll { it == "AM" || it == "PM" }
        startAMPMs.add(0, "Start AM/PM")
        endAMPMs.add(0, "End AM/PM")
        trainIDs.add(0, "#")

        ArrayList<String> stationRows = times.findAll { !it.contains("PM") && !it.contains("Train #") }.collect {
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
                stationRows[i - 1].replaceAll(names[i - 1], "")
        }

        stationRows.findIndexValues { it.contains("(ARRIVE)") }.reverseEach {
            Number index ->
                stationRows.remove(index.intValue())
                names.remove(index.intValue())
        }

        List<List<String>> tokenizedRows = stationRows.collect() {
            it.tokenize().findAll {
                !EXCLUDE_CELLS.contains(it)
            }.collect {
                if (it.contains(":") || it.startsWith("...")) {
                    return it
                }
                "TEXT"
            }
        }
        List<List<String>> matrix = []
        matrix.add(trainIDs)
        matrix.add(startAMPMs)
        matrix.add(endAMPMs)
        tokenizedRows.eachWithIndex {
            List<String> entry, int i ->
                entry.add(0, names[i])
                matrix.add(entry)
        }
        int size = matrix[0].size()
        matrix.each { assert size == it.size() }
        return matrix
    }

    private void parseEffectiveDates(ParsedPDFSchedule roughParsedSchedule, ProcessedPDFSchedule parsedSchedule) {
        String replaced = roughParsedSchedule.subject.replace("Effective ", "")
        List<String> tokens = replaced.tokenize()
        assert tokens.size() == 7
        parsedSchedule.from = LocalDate.parse(tokens[0] + " " + tokens[1] + " " + tokens[2], DateTimeFormat.forPattern(FORMAT_STRING));
        parsedSchedule.to = LocalDate.parse(tokens[4] + " " + tokens[5] + " " + tokens[6], DateTimeFormat.forPattern(FORMAT_STRING));
    }

}
