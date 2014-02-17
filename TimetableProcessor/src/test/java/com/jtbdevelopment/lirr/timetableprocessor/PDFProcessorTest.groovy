package com.jtbdevelopment.lirr.timetableprocessor

import com.jtbdevelopment.lirr.timetableprocessor.data.ParsedSchedule
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime

/**
 * Date: 2/15/14
 * Time: 2:23 PM
 */
class PDFProcessorTest extends GroovyTestCase {

    public static final LocalDate FEB232014 = new LocalDate(2014, 2, 23)
    public static final LocalDate DEC162013 = new LocalDate(2013, 12, 16)

    private LocalTime LT(int hour, int minute) {
        return new LocalTime(hour, minute)
    }

    void testParseWestHempstead() {
        ParsedSchedule schedule = processPDF("WestHempsteadBranch2013121620140223.pdf", "West Hempstead Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:44:50.000Z"))
        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "ST. ALBANS"] as Set == schedule.eastboundWeekends.keySet()
        println schedule.eastboundWeekends
        schedule.eastboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.eastboundWeekdays
        println "[" + schedule.eastboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.eastboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekends
        println "[" + schedule.westboundWeekends.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.westboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekdays
        println "[" + schedule.westboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.westboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        assert false
    }

    void testLongBeach() {
        ParsedSchedule schedule = processPDF("LongBeachBranch2013121620140223.pdf", "Long Beach Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:43:45.000Z"))
        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "VALLEY STREAM", "LYNBROOK", "CENTRE AVENUE", "EAST ROCKAWAY", "OCEANSIDE", "ISLAND PARK", "LONG BEACH"] as Set == schedule.eastboundWeekends.keySet()
        assert [LT(0, 57), LT(1, 25), LT(2, 58), LT(4, 24), LT(6, 43), LT(7, 33), LT(8, 43), LT(9, 43), LT(10, 43), LT(23, 43), LT(12, 43), LT(13, 43), LT(14, 43), LT(15, 33), LT(16, 33), LT(17, 43), LT(18, 43), LT(19, 43), LT(20, 33), LT(21, 43), LT(22, 33), LT(22, 53), LT(23, 15), LT(11, 46)] as Set == schedule.eastboundWeekends.get("KEW GARDENS")
        assert [LT(1, 17), LT(1, 57), LT(2, 20), LT(2, 47), LT(3, 22), LT(3, 32), LT(3, 34), LT(4, 4), LT(4, 45), LT(5, 30), LT(7, 3), LT(7, 25), LT(7, 54), LT(8, 25), LT(9, 3), LT(9, 25), LT(10, 3), LT(10, 25), LT(11, 3), LT(11, 25), LT(12, 3), LT(12, 25), LT(13, 3), LT(13, 25), LT(14, 3), LT(14, 25), LT(15, 3), LT(15, 25), LT(15, 54), LT(16, 25), LT(16, 54), LT(17, 1), LT(17, 25), LT(17, 52), LT(18, 3), LT(18, 25), LT(18, 52), LT(19, 3), LT(19, 25), LT(19, 52), LT(20, 3), LT(20, 25), LT(20, 54), LT(21, 25), LT(22, 3), LT(22, 25), LT(22, 54), LT(23, 25), LT(23, 36), LT(0, 35)] as Set == schedule.eastboundWeekends.get("LYNBROOK")
        assert [LT(1, 54), LT(2, 17), LT(3, 19), LT(3, 29), LT(4, 41), LT(7, 22), LT(8, 22), LT(9, 22), LT(10, 22), LT(11, 22), LT(12, 22), LT(13, 22), LT(14, 22), LT(15, 22), LT(16, 22), LT(16, 58), LT(17, 22), LT(17, 49), LT(18, 22), LT(18, 49), LT(19, 22), LT(19, 49), LT(20, 22), LT(21, 22), LT(22, 22), LT(23, 22), LT(0, 32)] as Set == schedule.eastboundWeekends.get("VALLEY STREAM")

        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "VALLEY STREAM", "LYNBROOK", "CENTRE AVENUE", "EAST ROCKAWAY", "OCEANSIDE", "ISLAND PARK", "LONG BEACH"] as Set == schedule.eastboundWeekdays.keySet()
        assert [LT(1, 34), LT(3, 45), LT(6, 12), LT(7, 29), LT(8, 15), LT(8, 49), LT(9, 29), LT(10, 21), LT(11, 21), LT(12, 21), LT(13, 21), LT(14, 21), LT(15, 41), LT(16, 7), LT(16, 36), LT(17, 1), LT(17, 30), LT(17, 48), LT(18, 3), LT(18, 11), LT(18, 25), LT(18, 42), LT(19, 3), LT(19, 32), LT(19, 55), LT(20, 19), LT(20, 56), LT(21, 15), LT(21, 43), LT(22, 39), LT(23, 39), LT(0, 45)] as Set == schedule.eastboundWeekdays.get("ISLAND PARK")
        assert [LT(1, 17), LT(1, 24), LT(2, 13), LT(3, 35), LT(5, 29), LT(6, 2), LT(7, 14), LT(7, 19), LT(8, 5), LT(8, 39), LT(9, 19), LT(9, 41), LT(10, 11), LT(10, 41), LT(11, 11), LT(11, 41), LT(12, 11), LT(12, 41), LT(13, 11), LT(13, 41), LT(14, 11), LT(14, 41), LT(15, 6), LT(15, 12), LT(15, 31), LT(15, 57), LT(16, 9), LT(16, 26), LT(16, 51), LT(17, 14), LT(17, 19), LT(17, 36), LT(17, 52), LT(18, 1), LT(18, 14), LT(18, 41), LT(18, 29), LT(18, 52), LT(19, 20), LT(19, 22), LT(19, 44), LT(20, 9), LT(20, 16), LT(20, 46), LT(21, 5), LT(21, 15), LT(21, 33), LT(21, 46), LT(22, 29), LT(22, 46), LT(23, 29), LT(0, 16), LT(0, 35)] as Set == schedule.eastboundWeekdays.get("LYNBROOK")
        assert [LT(1, 40), LT(3, 51), LT(6, 22), LT(7, 36), LT(8, 22), LT(8, 55), LT(9, 36), LT(10, 28), LT(11, 28), LT(12, 28), LT(13, 28), LT(14, 28), LT(15, 48), LT(16, 15), LT(16, 44), LT(17, 8), LT(17, 37), LT(17, 55), LT(18, 11), LT(18, 18), LT(18, 32), LT(18, 49), LT(19, 10), LT(19, 38), LT(20, 2), LT(20, 26), LT(21, 3), LT(21, 22), LT(21, 50), LT(22, 46), LT(23, 46), LT(0, 52)] as Set == schedule.eastboundWeekdays.get("LONG BEACH")

        assert ["LONG BEACH", "ISLAND PARK", "OCEANSIDE", "EAST ROCKAWAY", "CENTRE AVENUE", "LYNBROOK", "VALLEY STREAM", "JAMAICA", "EAST NEW YORK", "NOSTRAND AVENUE", "ATLANTIC TERMINAL", "HUNTERSPOINT AVE.", "KEW GARDENS", "FOREST HILLS", "WOODSIDE", "PENN STATION"] as Set == schedule.westboundWeekends.keySet()
        assert [LT(1, 23), LT(2, 26), LT(4, 33), LT(5, 35), LT(6, 19), LT(7, 26), LT(8, 26), LT(9, 26), LT(10, 26), LT(11, 26), LT(12, 26), LT(13, 26), LT(14, 26), LT(15, 26), LT(16, 26), LT(17, 26), LT(18, 26), LT(19, 26), LT(20, 26), LT(21, 26), LT(22, 26), LT(23, 26), LT(0, 26)] as Set == schedule.westboundWeekends.get("KEW GARDENS")
        assert [LT(0, 45), LT(1, 32), LT(4, 10), LT(4, 48), LT(5, 23), LT(6, 32), LT(7, 6), LT(7, 42), LT(8, 6), LT(8, 42), LT(9, 6), LT(9, 13), LT(9, 42), LT(10, 6), LT(10, 13), LT(10, 42), LT(11, 6), LT(11, 13), LT(11, 42), LT(12, 6), LT(12, 13), LT(12, 42), LT(13, 6), LT(13, 42), LT(14, 6), LT(14, 42), LT(15, 6), LT(15, 42), LT(16, 6), LT(16, 42), LT(17, 6), LT(17, 42), LT(18, 6), LT(18, 42), LT(19, 6), LT(19, 42), LT(20, 6), LT(20, 42), LT(21, 6), LT(21, 42), LT(22, 6), LT(22, 42), LT(23, 6), LT(23, 39)] as Set == schedule.westboundWeekends.get("LYNBROOK")
        assert [LT(0, 28), LT(3, 53), LT(5, 6), LT(6, 15), LT(7, 25), LT(8, 25), LT(8, 56), LT(9, 25), LT(9, 56), LT(10, 25), LT(10, 56), LT(11, 25), LT(11, 56), LT(12, 25), LT(13, 25), LT(14, 25), LT(15, 25), LT(16, 25), LT(17, 25), LT(18, 25), LT(19, 25), LT(20, 25), LT(21, 25), LT(22, 25), LT(23, 22)] as Set == schedule.westboundWeekends.get("LONG BEACH")

        assert ["LONG BEACH", "ISLAND PARK", "OCEANSIDE", "EAST ROCKAWAY", "CENTRE AVENUE", "LYNBROOK", "VALLEY STREAM", "JAMAICA", "EAST NEW YORK", "NOSTRAND AVENUE", "ATLANTIC TERMINAL", "HUNTERSPOINT AVE.", "KEW GARDENS", "FOREST HILLS", "WOODSIDE", "PENN STATION"] as Set == schedule.westboundWeekdays.keySet()
        assert [LT(0, 40), LT(3, 25), LT(5, 47), LT(6, 22), LT(6, 49), LT(7, 6), LT(7, 16), LT(7, 35), LT(7, 51), LT(8, 8), LT(8, 15), LT(8, 20), LT(8, 50), LT(9, 10), LT(9, 33), LT(10, 23), LT(11, 23), LT(12, 23), LT(13, 23), LT(14, 23), LT(15, 23), LT(16, 15), LT(16, 42), LT(17, 26), LT(17, 55), LT(18, 50), LT(19, 9), LT(20, 1), LT(20, 31), LT(21, 0), LT(22, 5), LT(23, 9)] as Set == schedule.westboundWeekdays.get("EAST ROCKAWAY")
        assert [LT(1, 0), LT(1, 49), LT(2, 32), LT(3, 58), LT(4, 31), LT(5, 33), LT(5, 50), LT(6, 11), LT(6, 13), LT(6, 47), LT(7, 4), LT(7, 9), LT(7, 10), LT(7, 31), LT(8, 12), LT(8, 33), LT(8, 41), LT(9, 10), LT(9, 33), LT(9, 53), LT(10, 43), LT(11, 3), LT(11, 43), LT(12, 3), LT(12, 43), LT(13, 3), LT(13, 43), LT(14, 3), LT(14, 43), LT(15, 3), LT(15, 43), LT(16, 3), LT(16, 39), LT(17, 2), LT(17, 46), LT(18, 17), LT(18, 32), LT(19, 15), LT(19, 29), LT(19, 33), LT(20, 3), LT(20, 21), LT(20, 33), LT(20, 55), LT(21, 6), LT(21, 22), LT(22, 6), LT(22, 25), LT(23, 7), LT(23, 30), LT(0, 6)] as Set == schedule.westboundWeekdays.get("JAMAICA")
        assert [LT(6, 37), LT(7, 26), LT(7, 41), LT(7, 49), LT(8, 36), LT(8, 55), LT(9, 11), LT(9, 28)] as Set == schedule.westboundWeekdays.get("HUNTERSPOINT AVE.")
    }

    void testHempstead() {
        ParsedSchedule schedule = processPDF("HempsteadBranch2013121620140223.pdf", "Hempstead Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:43:36.000Z"))
//        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "LOCUST MANOR", "LAURELTON", "ROSEDALE", "VALLEY STREAM", "GIBSON", "HEWLETT", "WOODMERE", "CEDARHURST", "LAWRENCE", "INWOOD", "FAR ROCKAWAY"] as Set == schedule.eastboundWeekends.keySet()
        println schedule.eastboundWeekends
        println "[" + schedule.eastboundWeekends.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.eastboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.eastboundWeekdays
        println "[" + schedule.eastboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.eastboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekends
        println "[" + schedule.westboundWeekends.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.westboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekdays
        println "[" + schedule.westboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.westboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        assert false
    }

    void testFarRockaway() {
        ParsedSchedule schedule = processPDF("FarRockawayBranch2013121620140223.pdf", "Far Rockaway Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:43:13.000Z"))
        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "LOCUST MANOR", "LAURELTON", "ROSEDALE", "VALLEY STREAM", "GIBSON", "HEWLETT", "WOODMERE", "CEDARHURST", "LAWRENCE", "INWOOD", "FAR ROCKAWAY"] as Set == schedule.eastboundWeekends.keySet()

        println schedule.eastboundWeekends
        schedule.eastboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.eastboundWeekdays
        println "[" + schedule.eastboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.eastboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekends
        println "[" + schedule.westboundWeekends.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.westboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekdays
        println "[" + schedule.westboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.westboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        assert false
    }

    void testBabylon() {
        ParsedSchedule schedule = processPDF("BabylonBranch2013121620140223.pdf", "Babylon Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:42:51.000Z"))
//        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "LOCUST MANOR", "LAURELTON", "ROSEDALE", "VALLEY STREAM", "GIBSON", "HEWLETT", "WOODMERE", "CEDARHURST", "LAWRENCE", "INWOOD", "FAR ROCKAWAY"] as Set == schedule.eastboundWeekends.keySet()
        println schedule.eastboundWeekends
        println "[" + schedule.eastboundWeekends.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.eastboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.eastboundWeekdays
        println "[" + schedule.eastboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.eastboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekends
        println "[" + schedule.westboundWeekends.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.westboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekdays
        println "[" + schedule.westboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.westboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        assert false
    }

    void testMontauk() {
        ParsedSchedule schedule = processPDF("MontaukBranch2013121620140223.pdf", "Montauk Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:43:54.000Z"))
        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "MINEOLA", "HICKSVILLE", "BABYLON", "BAY SHORE", "ISLIP", "GREAT RIVER", "OAKDALE", "SAYVILLE", "PATCHOGUE", "BELLPORT", "MASTIC-SHIRLEY", "SPEONK", "WESTHAMPTON", "HAMPTON BAYS", "SOUTHAMPTON", "BRIDGEHAMPTON", "EAST HAMPTON", "AMAGANSETT", "MONTAUK"] as Set == schedule.eastboundWeekends.keySet()
        assert [LT(0, 41), LT(1, 18), LT(6, 45), LT(7, 45), LT(8, 45), LT(21, 45), LT(22, 45), LT(23, 45), LT(12, 45), LT(14, 22), LT(14, 45), LT(15, 45), LT(16, 45), LT(18, 45), LT(19, 45), LT(9, 45), LT(10, 50)] as Set == schedule.eastboundWeekends.get("ATLANTIC TERMINAL")
        assert [LT(1, 29)] as Set == schedule.eastboundWeekends.get("HICKSVILLE")
        assert [LT(1, 46), LT(2, 40), LT(8, 26), LT(8, 46), LT(10, 19), LT(22, 46), LT(12, 19), LT(12, 51), LT(14, 19), LT(15, 35), LT(16, 26), LT(17, 26), LT(18, 19), LT(20, 17), LT(21, 26), LT(11, 26), LT(0, 24)] as Set == schedule.eastboundWeekends.get("BABYLON")
        assert [LT(3, 32), LT(10, 21), LT(12, 28), LT(14, 35), LT(11, 55)] as Set == schedule.eastboundWeekends.get("EAST HAMPTON")

        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "MINEOLA", "HICKSVILLE", "BABYLON", "BAY SHORE", "ISLIP", "GREAT RIVER", "OAKDALE", "SAYVILLE", "PATCHOGUE", "BELLPORT", "MASTIC-SHIRLEY", "SPEONK", "WESTHAMPTON", "HAMPTON BAYS", "SOUTHAMPTON", "BRIDGEHAMPTON", "EAST HAMPTON", "AMAGANSETT", "MONTAUK"] as Set == schedule.eastboundWeekdays.keySet()
        assert [LT(0, 41), LT(6, 37), LT(7, 50), LT(8, 30), LT(9, 5), LT(22, 5), LT(23, 5), LT(13, 5), LT(14, 35), LT(15, 23), LT(15, 56), LT(16, 20), LT(16, 34), LT(17, 11), LT(17, 23), LT(17, 49), LT(18, 27), LT(19, 23), LT(20, 30), LT(21, 13), LT(10, 36), LT(11, 10)] as Set == schedule.eastboundWeekdays.get("ATLANTIC TERMINAL")
        assert [LT(1, 46), LT(7, 55), LT(8, 52), LT(9, 26), LT(10, 27), LT(23, 27), LT(12, 10), LT(12, 27), LT(14, 27), LT(15, 40), LT(16, 28), LT(17, 10), LT(17, 44), LT(18, 13), LT(18, 44), LT(19, 27), LT(20, 36), LT(21, 26), LT(22, 35), LT(11, 35), LT(0, 37)] as Set == schedule.eastboundWeekdays.get("BABYLON")
        assert [LT(3, 32), LT(10, 27), LT(13, 50), LT(18, 17), LT(19, 9), LT(20, 41), LT(23, 32)] as Set == schedule.eastboundWeekdays.get("EAST HAMPTON")

        assert ["MONTAUK", "AMAGANSETT", "EAST HAMPTON", "BRIDGEHAMPTON", "SOUTHAMPTON", "HAMPTON BAYS", "WESTHAMPTON", "SPEONK", "MASTIC-SHIRLEY", "BELLPORT", "PATCHOGUE", "SAYVILLE", "OAKDALE", "GREAT RIVER", "ISLIP", "BAY SHORE", "BABYLON", "HICKSVILLE", "MINEOLA", "JAMAICA", "EAST NEW YORK", "NOSTRAND AVENUE", "ATLANTIC TERMINAL", "HUNTERSPOINT AVE.", "KEW GARDENS", "FOREST HILLS", "WOODSIDE", "PENN STATION"] as Set == schedule.westboundWeekends.keySet()
        assert [LT(6, 4), LT(7, 15), LT(7, 35), LT(8, 43), LT(10, 35), LT(12, 35), LT(14, 35), LT(15, 35), LT(17, 10), LT(17, 23), LT(18, 21), LT(19, 13), LT(19, 24), LT(20, 23), LT(21, 13), LT(21, 24), LT(22, 23), LT(0, 30)] as Set == schedule.westboundWeekends.get("PATCHOGUE")
        assert [LT(6, 34), LT(7, 45), LT(8, 10), LT(9, 13), LT(11, 10), LT(13, 10), LT(15, 10), LT(16, 10), LT(17, 53), LT(18, 53), LT(19, 34), LT(19, 59), LT(20, 58), LT(21, 34), LT(21, 59), LT(22, 53), LT(1, 0)] as Set == schedule.westboundWeekends.get("BABYLON")
        assert [] as Set == schedule.westboundWeekends.get("HICKSVILLE")
        assert [LT(7, 39), LT(8, 47), LT(9, 12), LT(10, 21), LT(12, 12), LT(14, 12), LT(16, 12), LT(17, 12), LT(18, 39), LT(18, 59), LT(19, 59), LT(20, 39), LT(21, 12), LT(22, 12), LT(22, 39), LT(23, 14), LT(23, 59), LT(2, 2)] as Set == schedule.westboundWeekends.get("PENN STATION")

        assert ["MONTAUK", "AMAGANSETT", "EAST HAMPTON", "BRIDGEHAMPTON", "SOUTHAMPTON", "HAMPTON BAYS", "WESTHAMPTON", "SPEONK", "MASTIC-SHIRLEY", "BELLPORT", "PATCHOGUE", "SAYVILLE", "OAKDALE", "GREAT RIVER", "ISLIP", "BAY SHORE", "BABYLON", "HICKSVILLE", "MINEOLA", "JAMAICA", "EAST NEW YORK", "NOSTRAND AVENUE", "ATLANTIC TERMINAL", "HUNTERSPOINT AVE.", "KEW GARDENS", "FOREST HILLS", "WOODSIDE", "PENN STATION"] as Set == schedule.westboundWeekdays.keySet()
        assert [LT(1, 0), LT(5, 39), LT(11, 18), LT(14, 51), LT(22, 38)] as Set == schedule.westboundWeekdays.get("MONTAUK")
        assert [LT(2, 13), LT(4, 37), LT(5, 8), LT(5, 30), LT(6, 21), LT(6, 49), LT(7, 12), LT(7, 31), LT(12, 37), LT(14, 0), LT(16, 14), LT(20, 0), LT(21, 7), LT(21, 59), LT(0, 5)] as Set == schedule.westboundWeekdays.get("SPEONK")
        assert [LT(6, 38), LT(7, 14), LT(7, 41), LT(8, 5), LT(8, 47), LT(8, 23), LT(9, 14), LT(9, 48), LT(10, 55), LT(23, 55), LT(12, 55), LT(14, 55), LT(15, 53), LT(17, 20), LT(18, 1), LT(18, 46), LT(20, 8), LT(21, 10), LT(22, 16), LT(0, 48), LT(2, 25)] as Set == schedule.westboundWeekdays.get("KEW GARDENS")
    }

    void testPortWashington() {
        ParsedSchedule schedule = processPDF("PortWashingtonBranch2013121620140223.pdf", "Port Washington Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:44:24.000Z"))
        assert ["PENN STATION", "WOODSIDE", "FLUSHING MAIN STREET", "MURRAY HILL", "BROADWAY", "AUBURNDALE", "BAYSIDE", "DOUGLASTON", "LITTLE NECK", "GREAT NECK", "MANHASSET", "PLANDOME", "PORT WASHINGTON"] as Set == schedule.eastboundWeekends.keySet()
        assert [LT(0, 58), LT(1, 16), LT(1, 57), LT(3, 00), LT(3, 45), LT(5, 0), LT(6, 0), LT(7, 0), LT(8, 0), LT(8, 30), LT(9, 0), LT(9, 30), LT(10, 0), LT(10, 30), LT(11, 0), LT(11, 30), LT(12, 0), LT(12, 30), LT(13, 0), LT(13, 30), LT(14, 0), LT(14, 30), LT(15, 0), LT(15, 30), LT(16, 0), LT(16, 30), LT(17, 0), LT(17, 30), LT(18, 0), LT(18, 30), LT(19, 0), LT(19, 30), LT(20, 0), LT(20, 30), LT(21, 0), LT(21, 30), LT(22, 0), LT(22, 30), LT(23, 0), LT(23, 30), LT(0, 2)] as Set == schedule.eastboundWeekends.get("PLANDOME")

        assert ["PENN STATION", "WOODSIDE", "FLUSHING MAIN STREET", "MURRAY HILL", "BROADWAY", "AUBURNDALE", "BAYSIDE", "DOUGLASTON", "LITTLE NECK", "GREAT NECK", "MANHASSET", "PLANDOME", "PORT WASHINGTON"] as Set == schedule.eastboundWeekdays.keySet()
        assert [LT(0, 31), LT(1, 30), LT(3, 30), LT(5, 40), LT(6, 42), LT(7, 12), LT(8, 7), LT(8, 32), LT(9, 2), LT(9, 30), LT(10, 0), LT(10, 30), LT(11, 0), LT(11, 30), LT(12, 0), LT(12, 30), LT(13, 0), LT(13, 30), LT(14, 0), LT(14, 30), LT(15, 0), LT(15, 29), LT(15, 51), LT(16, 0), LT(16, 36), LT(16, 57), LT(17, 40), LT(18, 7), LT(18, 25), LT(18, 53), LT(19, 25), LT(20, 0), LT(20, 30), LT(21, 0), LT(21, 30), LT(22, 0), LT(22, 30), LT(23, 0), LT(23, 30), LT(0, 0)] as Set == schedule.eastboundWeekdays.get("WOODSIDE")

        assert ["PENN STATION", "WOODSIDE", "FLUSHING MAIN STREET", "MURRAY HILL", "BROADWAY", "AUBURNDALE", "BAYSIDE", "DOUGLASTON", "LITTLE NECK", "GREAT NECK", "MANHASSET", "PLANDOME", "PORT WASHINGTON"] as Set == schedule.westboundWeekdays.keySet()
        assert [LT(0, 39), LT(2, 10), LT(4, 10), LT(5, 39), LT(6, 39), LT(7, 9), LT(7, 39), LT(8, 9), LT(8, 39), LT(9, 9), LT(9, 39), LT(10, 9), LT(10, 39), LT(11, 9), LT(11, 39), LT(12, 9), LT(12, 39), LT(13, 9), LT(13, 39), LT(14, 9), LT(14, 39), LT(15, 9), LT(15, 39), LT(16, 9), LT(16, 39), LT(17, 9), LT(17, 39), LT(18, 9), LT(18, 39), LT(19, 9), LT(19, 39), LT(20, 9), LT(20, 39), LT(21, 9), LT(21, 39), LT(22, 9), LT(22, 39), LT(23, 39)] as Set == schedule.westboundWeekends.get("PORT WASHINGTON")
        assert [LT(1, 5), LT(2, 36), LT(4, 36), LT(6, 5), LT(7, 5), LT(7, 35), LT(7, 59), LT(8, 5), LT(8, 35), LT(8, 42), LT(9, 5), LT(9, 35), LT(10, 5), LT(10, 35), LT(11, 5), LT(11, 35), LT(12, 5), LT(12, 35), LT(13, 5), LT(13, 35), LT(14, 5), LT(14, 35), LT(15, 5), LT(15, 35), LT(16, 5), LT(16, 35), LT(17, 5), LT(17, 35), LT(18, 5), LT(18, 35), LT(19, 5), LT(19, 35), LT(20, 5), LT(20, 35), LT(21, 5), LT(21, 35), LT(22, 5), LT(22, 35), LT(23, 5), LT(0, 5)] as Set == schedule.westboundWeekends.get("FLUSHING MAIN STREET")

        assert ["PENN STATION", "WOODSIDE", "FLUSHING MAIN STREET", "MURRAY HILL", "BROADWAY", "AUBURNDALE", "BAYSIDE", "DOUGLASTON", "LITTLE NECK", "GREAT NECK", "MANHASSET", "PLANDOME", "PORT WASHINGTON"] as Set == schedule.westboundWeekends.keySet()
        assert [LT(1, 3), LT(2, 34), LT(3, 47), LT(5, 33), LT(6, 0), LT(6, 42), LT(6, 53), LT(7, 23), LT(8, 9), LT(8, 39), LT(8, 53), LT(9, 13), LT(9, 47), LT(10, 33), LT(11, 33), LT(12, 33), LT(13, 33), LT(14, 33), LT(15, 33), LT(16, 30), LT(17, 0), LT(17, 24), LT(18, 37), LT(19, 21), LT(19, 33), LT(20, 3), LT(20, 33), LT(21, 3), LT(21, 15), LT(21, 33), LT(22, 3), LT(22, 33), LT(23, 3), LT(0, 3)] as Set == schedule.westboundWeekdays.get("MURRAY HILL")
        assert [LT(0, 51), LT(2, 22), LT(3, 35), LT(5, 21), LT(5, 48), LT(6, 30), LT(6, 40), LT(7, 9), LT(7, 22), LT(7, 55), LT(8, 7), LT(8, 32), LT(8, 41), LT(9, 1), LT(9, 35), LT(9, 52), LT(10, 22), LT(10, 52), LT(11, 22), LT(11, 52), LT(12, 22), LT(12, 52), LT(13, 22), LT(13, 52), LT(14, 22), LT(14, 52), LT(15, 22), LT(15, 52), LT(16, 19), LT(16, 49), LT(17, 12), LT(17, 47), LT(18, 26), LT(18, 47), LT(19, 9), LT(19, 21), LT(19, 51), LT(20, 21), LT(20, 51), LT(20, 57), LT(21, 21), LT(21, 51), LT(22, 21), LT(22, 51), LT(23, 51)] as Set == schedule.westboundWeekdays.get("LITTLE NECK")
    }

    void testRonkonkoma() {
        ParsedSchedule schedule = processPDF("RonkonkomaBranch2013121620140223.pdf", "Ronkonkoma Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:44:39.000Z"))
        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "MINEOLA", "HICKSVILLE", "BETHPAGE", "FARMINGDALE", "PINELAWN", "WYANDANCH", "DEER PARK", "BRENTWOOD", "CENTRAL ISLIP", "RONKONKOMA"] as Set == schedule.eastboundWeekends.keySet()
        assert [LT(0, 50), LT(1, 48), LT(2, 38), LT(3, 28), LT(4, 29), LT(5, 19), LT(6, 27), LT(7, 28), LT(8, 27), LT(9, 27), LT(10, 27), LT(23, 27), LT(12, 27), LT(13, 27), LT(14, 27), LT(15, 27), LT(16, 9), LT(16, 27), LT(17, 9), LT(17, 27), LT(18, 9), LT(18, 27), LT(19, 9), LT(19, 27), LT(20, 27), LT(21, 27), LT(22, 27), LT(11, 54)] as Set == schedule.eastboundWeekends.get("MINEOLA")
        assert [LT(1, 29), LT(2, 1), LT(2, 34), LT(3, 18), LT(4, 8), LT(5, 9), LT(5, 58), LT(7, 25), LT(8, 25), LT(9, 25), LT(10, 25), LT(11, 25), LT(12, 25), LT(13, 25), LT(14, 25), LT(15, 25), LT(16, 25), LT(16, 47), LT(17, 25), LT(17, 47), LT(18, 25), LT(18, 47), LT(19, 25), LT(19, 47), LT(20, 24), LT(21, 25), LT(22, 25), LT(23, 25), LT(0, 32)] as Set == schedule.eastboundWeekends.get("CENTRAL ISLIP")
        assert [LT(1, 36), LT(2, 7), LT(2, 41), LT(3, 25), LT(4, 14), LT(5, 16), LT(6, 5), LT(7, 32), LT(8, 32), LT(9, 32), LT(10, 32), LT(11, 32), LT(12, 32), LT(13, 32), LT(14, 32), LT(15, 32), LT(16, 32), LT(16, 55), LT(17, 32), LT(17, 54), LT(18, 32), LT(18, 54), LT(19, 32), LT(19, 54), LT(20, 31), LT(21, 32), LT(22, 32), LT(23, 32), LT(0, 39)] as Set == schedule.eastboundWeekends.get("RONKONKOMA")

        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "MINEOLA", "HICKSVILLE", "BETHPAGE", "FARMINGDALE", "PINELAWN", "WYANDANCH", "DEER PARK", "BRENTWOOD", "CENTRAL ISLIP", "RONKONKOMA"] as Set == schedule.eastboundWeekdays.keySet()
        assert [LT(0, 50), LT(1, 58), LT(3, 52), LT(5, 47), LT(6, 39), LT(8, 15), LT(8, 51), LT(9, 51), LT(10, 51), LT(23, 51), LT(12, 51), LT(13, 23), LT(13, 51), LT(14, 16), LT(14, 51), LT(15, 30), LT(16, 21), LT(16, 44), LT(17, 9), LT(17, 18), LT(17, 32), LT(18, 0), LT(18, 47), LT(19, 10), LT(19, 48), LT(20, 17), LT(20, 50), LT(21, 49), LT(22, 49), LT(11, 17), LT(11, 50)] as Set == schedule.eastboundWeekdays.get("MINEOLA")
        assert [LT(1, 5), LT(2, 15), LT(4, 5), LT(6, 0), LT(6, 54), LT(9, 5), LT(10, 5), LT(11, 5), LT(12, 4), LT(13, 5), LT(13, 36), LT(14, 5), LT(14, 32), LT(15, 5), LT(15, 53), LT(16, 1), LT(16, 44), LT(16, 59), LT(17, 11), LT(17, 25), LT(17, 51), LT(18, 13), LT(18, 53), LT(18, 39), LT(19, 7), LT(19, 40), LT(20, 1), LT(20, 30), LT(21, 5), LT(22, 5), LT(23, 5), LT(11, 34), LT(0, 5)] as Set == schedule.eastboundWeekdays.get("BETHPAGE")
        assert [LT(1, 29), LT(2, 40), LT(4, 31), LT(6, 26), LT(8, 3), LT(8, 51), LT(9, 32), LT(10, 30), LT(11, 30), LT(12, 30), LT(13, 30), LT(14, 0), LT(14, 30), LT(15, 1), LT(15, 30), LT(16, 18), LT(16, 27), LT(17, 9), LT(17, 37), LT(17, 50), LT(17, 58), LT(18, 14), LT(18, 31), LT(18, 38), LT(18, 42), LT(19, 20), LT(19, 4), LT(19, 32), LT(20, 5), LT(20, 26), LT(20, 55), LT(21, 30), LT(22, 30), LT(23, 31), LT(11, 58), LT(0, 30)] as Set == schedule.eastboundWeekdays.get("CENTRAL ISLIP")
        assert [LT(1, 36), LT(2, 46), LT(4, 37), LT(6, 37), LT(8, 10), LT(8, 59), LT(9, 39), LT(10, 37), LT(11, 37), LT(12, 37), LT(13, 37), LT(14, 7), LT(14, 37), LT(15, 8), LT(15, 37), LT(16, 25), LT(16, 34), LT(17, 16), LT(17, 44), LT(17, 57), LT(18, 4), LT(18, 21), LT(18, 38), LT(18, 45), LT(18, 49), LT(19, 27), LT(19, 11), LT(19, 39), LT(20, 12), LT(20, 33), LT(21, 2), LT(21, 37), LT(22, 37), LT(23, 38), LT(0, 5), LT(0, 37)] as Set == schedule.eastboundWeekdays.get("RONKONKOMA")

        assert ["RONKONKOMA", "CENTRAL ISLIP", "BRENTWOOD", "DEER PARK", "WYANDANCH", "PINELAWN", "FARMINGDALE", "BETHPAGE", "HICKSVILLE", "MINEOLA", "JAMAICA", "EAST NEW YORK", "NOSTRAND AVENUE", "ATLANTIC TERMINAL", "HUNTERSPOINT AVE.", "KEW GARDENS", "FOREST HILLS", "WOODSIDE", "PENN STATION"] as Set == schedule.westboundWeekends.keySet()
        assert [LT(0, 44), LT(2, 13), LT(3, 45), LT(5, 10), LT(5, 37), LT(6, 10), LT(6, 40), LT(7, 10), LT(7, 40), LT(8, 10), LT(8, 40), LT(9, 40), LT(10, 5), LT(10, 40), LT(11, 5), LT(11, 40), LT(12, 5), LT(12, 40), LT(13, 40), LT(14, 40), LT(15, 40), LT(16, 40), LT(17, 40), LT(18, 40), LT(19, 40), LT(20, 40), LT(21, 40), LT(22, 40), LT(23, 18)] as Set == schedule.westboundWeekends.get("RONKONKOMA")
        assert [LT(2, 2), LT(3, 28), LT(5, 2), LT(6, 28), LT(6, 59), LT(7, 28), LT(7, 59), LT(8, 15), LT(8, 28), LT(8, 59), LT(9, 28), LT(9, 59), LT(10, 59), LT(11, 28), LT(11, 59), LT(12, 28), LT(12, 59), LT(13, 28), LT(13, 59), LT(14, 59), LT(15, 59), LT(16, 59), LT(17, 59), LT(18, 59), LT(19, 59), LT(20, 59), LT(21, 59), LT(22, 59), LT(23, 59), LT(0, 44)] as Set == schedule.westboundWeekends.get("PENN STATION")

        assert ["RONKONKOMA", "CENTRAL ISLIP", "BRENTWOOD", "DEER PARK", "WYANDANCH", "PINELAWN", "FARMINGDALE", "BETHPAGE", "HICKSVILLE", "MINEOLA", "JAMAICA", "EAST NEW YORK", "NOSTRAND AVENUE", "ATLANTIC TERMINAL", "HUNTERSPOINT AVE.", "KEW GARDENS", "FOREST HILLS", "WOODSIDE", "PENN STATION"] as Set == schedule.westboundWeekdays.keySet()
        assert [LT(0, 44), LT(1, 46), LT(4, 6), LT(4, 58), LT(5, 29), LT(5, 42), LT(6, 8), LT(6, 24), LT(6, 39), LT(6, 56), LT(7, 4), LT(7, 19), LT(7, 32), LT(7, 40), LT(7, 57), LT(8, 31), LT(9, 11), LT(10, 11), LT(11, 11), LT(12, 11), LT(13, 11), LT(14, 11), LT(15, 11), LT(16, 11), LT(16, 48), LT(19, 13), LT(19, 42), LT(19, 58), LT(20, 39), LT(21, 11), LT(22, 11), LT(23, 8)] as Set == schedule.westboundWeekdays.get("RONKONKOMA")
        assert [LT(1, 20), LT(2, 23), LT(4, 43), LT(5, 35), LT(6, 11), LT(6, 19), LT(6, 50), LT(7, 6), LT(7, 33), LT(8, 7), LT(8, 19), LT(8, 33), LT(9, 7), LT(9, 49), LT(10, 10), LT(10, 49), LT(11, 10), LT(11, 49), LT(12, 10), LT(12, 49), LT(13, 49), LT(14, 49), LT(15, 49), LT(16, 48), LT(17, 24), LT(18, 35), LT(19, 11), LT(19, 50), LT(20, 19), LT(20, 38), LT(21, 20), LT(21, 51), LT(22, 49), LT(23, 47)] as Set == schedule.westboundWeekdays.get("HICKSVILLE")
    }

    void testPortJefferson() {
        ParsedSchedule schedule = processPDF("PortJeffersonBranch2013121620140223.pdf", "Port Jefferson Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:44:14.000Z"))
//        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "LOCUST MANOR", "LAURELTON", "ROSEDALE", "VALLEY STREAM", "GIBSON", "HEWLETT", "WOODMERE", "CEDARHURST", "LAWRENCE", "INWOOD", "FAR ROCKAWAY"] as Set == schedule.eastboundWeekends.keySet()
        println schedule.eastboundWeekends
        println schedule.eastboundWeekends.keySet()
        schedule.eastboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.eastboundWeekdays
        println schedule.eastboundWeekdays.keySet()
        schedule.eastboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekends
        println schedule.westboundWeekends.keySet()
        schedule.westboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekdays
        println schedule.westboundWeekdays.keySet()
        schedule.westboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        assert false
    }

    void testOysterBay() {
        ParsedSchedule schedule = processPDF("OysterBayBranch2013121620140223.pdf", "Oyster Bay Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:44:03.000Z"))
        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "MINEOLA", "EAST WILLISTON", "ALBERTSON", "ROSLYN", "GREENVALE", "GLEN HEAD", "SEA CLIFF", "GLEN STREET", "GLEN COVE", "LOCUST VALLEY", "OYSTER BAY"] as Set == schedule.eastboundWeekends.keySet()
        assert [LT(9, 46), LT(11, 46), LT(13, 46), LT(15, 46), LT(17, 46), LT(18, 46), LT(19, 46), LT(20, 46), LT(22, 46), LT(0, 43)] as Set == schedule.eastboundWeekends.get("OYSTER BAY")
        assert [LT(9, 16), LT(11, 16), LT(13, 16), LT(15, 16), LT(17, 16), LT(18, 16), LT(19, 16), LT(20, 16), LT(22, 16), LT(0, 13)] as Set == schedule.eastboundWeekends.get("ROSLYN")

        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "MINEOLA", "EAST WILLISTON", "ALBERTSON", "ROSLYN", "GREENVALE", "GLEN HEAD", "SEA CLIFF", "GLEN STREET", "GLEN COVE", "LOCUST VALLEY", "OYSTER BAY"] as Set == schedule.eastboundWeekdays.keySet()
        assert [LT(2, 27), LT(8, 48), LT(9, 45), LT(23, 45), LT(12, 45), LT(13, 45), LT(15, 46), LT(16, 32), LT(17, 15), LT(17, 59), LT(18, 43), LT(19, 15), LT(19, 39), LT(20, 46), LT(21, 46), LT(22, 49), LT(0, 21)] as Set == schedule.eastboundWeekdays.get("GLEN COVE")
        assert [LT(1, 21), LT(7, 39), LT(8, 33), LT(10, 41), LT(11, 41), LT(12, 41), LT(14, 36), LT(15, 27), LT(16, 15), LT(16, 54), LT(17, 33), LT(18, 16), LT(18, 36), LT(19, 44), LT(20, 42), LT(21, 42), LT(23, 14)] as Set == schedule.eastboundWeekdays.get("PENN STATION")

        assert ["OYSTER BAY", "LOCUST VALLEY", "GLEN COVE", "GLEN STREET", "SEA CLIFF", "GLEN HEAD", "GREENVALE", "ROSLYN", "ALBERTSON", "EAST WILLISTON", "MINEOLA", "JAMAICA", "EAST NEW YORK", "NOSTRAND AVENUE", "ATLANTIC TERMINAL", "HUNTERSPOINT AVE.", "KEW GARDENS", "FOREST HILLS", "WOODSIDE", "PENN STATION"] as Set == schedule.westboundWeekends.keySet()
        assert [LT(6, 42), LT(8, 42), LT(10, 42), LT(12, 42), LT(14, 42), LT(16, 42), LT(17, 42), LT(18, 42), LT(20, 42), LT(22, 42)] as Set == schedule.westboundWeekends.get("GLEN HEAD")
        assert [LT(6, 34), LT(8, 34), LT(10, 34), LT(12, 34), LT(14, 34), LT(16, 34), LT(17, 34), LT(18, 34), LT(20, 34), LT(22, 34)] as Set == schedule.westboundWeekends.get("GLEN COVE")

        assert ["OYSTER BAY", "LOCUST VALLEY", "GLEN COVE", "GLEN STREET", "SEA CLIFF", "GLEN HEAD", "GREENVALE", "ROSLYN", "ALBERTSON", "EAST WILLISTON", "MINEOLA", "JAMAICA", "EAST NEW YORK", "NOSTRAND AVENUE", "ATLANTIC TERMINAL", "HUNTERSPOINT AVE.", "KEW GARDENS", "FOREST HILLS", "WOODSIDE", "PENN STATION"] as Set == schedule.westboundWeekdays.keySet()
        assert [LT(5, 44), LT(6, 22), LT(7, 1), LT(7, 45), LT(8, 13), LT(8, 48), LT(10, 2), LT(11, 2), LT(13, 2), LT(14, 2), LT(15, 2), LT(16, 52), LT(18, 25), LT(19, 6), LT(20, 53), LT(21, 55), LT(23, 49)] as Set == schedule.westboundWeekdays.get("ALBERTSON")
        assert [LT(5, 37), LT(6, 15), LT(6, 54), LT(7, 38), LT(8, 6), LT(8, 41), LT(9, 55), LT(10, 55), LT(12, 55), LT(13, 55), LT(14, 55), LT(16, 45), LT(18, 18), LT(18, 59), LT(20, 46), LT(21, 48), LT(23, 42)] as Set == schedule.westboundWeekdays.get("GREENVALE")
    }

    private ParsedSchedule processPDF(
            final String file,
            final String expectedTitle,
            final LocalDate expectedFrom, final LocalDate expectedTo, final DateTime expectedModified) {
        InputStream is = new BufferedInputStream(PDFProcessorTest.classLoader.getResourceAsStream(file))
        ParsedSchedule parsedSchedule = new PDFProcessor().parse(is)
        is.close()
        assert expectedTitle == parsedSchedule.title
        assert expectedFrom == parsedSchedule.from
        assert expectedTo == parsedSchedule.to
        assert expectedModified == parsedSchedule.modified
        parsedSchedule
    }

    private void printSchedule(final ParsedSchedule schedule) {
        println schedule.eastboundWeekends
        println schedule.eastboundWeekends.keySet()
        schedule.eastboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.eastboundWeekdays
        println schedule.eastboundWeekdays.keySet()
        schedule.eastboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekends
        println schedule.westboundWeekends.keySet()
        schedule.westboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekdays
        println schedule.westboundWeekdays.keySet()
        schedule.westboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
    }
}
