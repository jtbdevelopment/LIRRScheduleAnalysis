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
        schedule.eastboundWeekends.each {
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
        println schedule.eastboundWeekends
        schedule.eastboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.eastboundWeekdays
        println "[" + schedule.eastboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.eastboundWeekends.each {
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
        schedule.eastboundWeekends.each {
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
        schedule.eastboundWeekends.each {
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
        schedule.eastboundWeekends.each {
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
        println schedule.eastboundWeekends
        schedule.eastboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.eastboundWeekdays
        println "[" + schedule.eastboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.eastboundWeekends.each {
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

    void testPortWashington() {
        ParsedSchedule schedule = processPDF("PortWashingtonBranch2013121620140223.pdf", "Port Washington Branch Timetable", DEC162013, FEB232014, DateTime.parse("2013-12-05T16:44:24.000Z"))
        assert ["PENN STATION", "WOODSIDE", "FLUSHING MAIN STREET", "MURRAY HILL", "BROADWAY", "AUBURNDALE", "BAYSIDE", "DOUGLASTON", "LITTLE NECK", "GREAT NECK", "MANHASSET", "PLANDOME", "PORT WASHINGTON"] as Set == schedule.eastboundWeekends.keySet()
        assert ["PENN STATION", "WOODSIDE", "FLUSHING MAIN STREET", "MURRAY HILL", "BROADWAY", "AUBURNDALE", "BAYSIDE", "DOUGLASTON", "LITTLE NECK", "GREAT NECK", "MANHASSET", "PLANDOME", "PORT WASHINGTON"] as Set == schedule.eastboundWeekdays.keySet()
        assert ["PENN STATION", "WOODSIDE", "FLUSHING MAIN STREET", "MURRAY HILL", "BROADWAY", "AUBURNDALE", "BAYSIDE", "DOUGLASTON", "LITTLE NECK", "GREAT NECK", "MANHASSET", "PLANDOME", "PORT WASHINGTON"] as Set == schedule.westboundWeekdays.keySet()
        assert ["PENN STATION", "WOODSIDE", "FLUSHING MAIN STREET", "MURRAY HILL", "BROADWAY", "AUBURNDALE", "BAYSIDE", "DOUGLASTON", "LITTLE NECK", "GREAT NECK", "MANHASSET", "PLANDOME", "PORT WASHINGTON"] as Set == schedule.westboundWeekends.keySet()

        assert [LT(0, 58), LT(1, 16), LT(1, 57), LT(3, 00), LT(3, 45), LT(5, 0), LT(6, 0), LT(7, 0),
                LT(8, 0), LT(8, 30), LT(9, 0), LT(9, 30), LT(10, 0), LT(10, 30), LT(11, 0), LT(11, 30),
                LT(12, 0), LT(12, 30), LT(13, 0), LT(13, 30), LT(14, 0), LT(14, 30), LT(15, 0), LT(15, 30),
                LT(16, 0), LT(16, 30), LT(17, 0), LT(17, 30), LT(18, 0), LT(18, 30), LT(19, 0), LT(19, 30),
                LT(20, 0), LT(20, 30), LT(21, 0), LT(21, 30), LT(22, 0), LT(22, 30), LT(23, 0), LT(23, 30),
                LT(0, 2)] as Set == schedule.eastboundWeekends.get("PLANDOME")
        println schedule.eastboundWeekdays
        schedule.eastboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekends
        schedule.westboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.westboundWeekdays
        schedule.westboundWeekdays.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        assert false
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
        schedule.eastboundWeekends.each {
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
//        assert ["PENN STATION", "WOODSIDE", "FOREST HILLS", "KEW GARDENS", "HUNTERSPOINT AVE.", "ATLANTIC TERMINAL", "NOSTRAND AVENUE", "EAST NEW YORK", "JAMAICA", "LOCUST MANOR", "LAURELTON", "ROSEDALE", "VALLEY STREAM", "GIBSON", "HEWLETT", "WOODMERE", "CEDARHURST", "LAWRENCE", "INWOOD", "FAR ROCKAWAY"] as Set == schedule.eastboundWeekends.keySet()
        println schedule.eastboundWeekends
        println "[" + schedule.eastboundWeekends.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.eastboundWeekends.each {
            println it.key;
            println "[" + it.value.collect { "LT(" + it.hourOfDay + "," + it.minuteOfHour + ")" }.join(",") + "] as Set"
        }
        println schedule.eastboundWeekdays
        println "[" + schedule.eastboundWeekdays.keySet().collect { "\"" + it + "\"" }.join(",") + "] as Set"
        schedule.eastboundWeekends.each {
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
}
