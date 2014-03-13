package com.jtbdevelopment.lirr.dataobjects.core
/**
 * Date: 2/15/14
 * Time: 1:37 PM
 *
 * TODO - miles from penn
 * TODO - gps coordinates
 */
class Station {
    public static final String PENN_STATION_NAME = "PENN STATION"
    public static final String ATLANTIC_AVENUE_NAME = "ATLANTIC TERMINAL"
    public static final String HUNTERSPOINT_AVE_NAME = "HUNTERSPOINT AVE."
    String name
    Zone zone
    Line line
    float milesToPenn
    boolean ignoreForAnalysis = false  // Primarily for penn, etc
    boolean penn = true
    boolean brooklyn = true
    boolean lic = true

    static final List<Station> STATIONS = [
            //  Names must be as they appear on the schedule
            new Station(name: "Penn Station", zone: Zone.Zone1, ignoreForAnalysis: true, brooklyn: false, lic: false, line: Line.CityPenn, milesToPenn: 0),
            new Station(name: "Atlantic Terminal", zone: Zone.Zone1, ignoreForAnalysis: true, brooklyn: true, penn: false, line: Line.CityBrooklyn),
            new Station(name: "Long Island City", zone: Zone.Zone1, ignoreForAnalysis: true, lic: true, penn: false, line: Line.CityLIC),
            new Station(name: "Hunterspoint Ave.", zone: Zone.Zone1, ignoreForAnalysis: true, lic: true, penn: false, line: Line.CityLIC),

            new Station(name: "Nostrand Avenue", zone: Zone.Zone1, brooklyn: true, penn: false, line: Line.CityBrooklyn),
            new Station(name: "East New York", zone: Zone.Zone1, brooklyn: true, penn: false, line: Line.CityBrooklyn),

            new Station(name: "Woodside", zone: Zone.Zone1, brooklyn: false, lic: false, milesToPenn: 4.9, line: Line.CityPenn),
            new Station(name: "Forest Hills", zone: Zone.Zone1, brooklyn: false, lic: false, milesToPenn: 8.5, line: Line.CityPenn),
            new Station(name: "Kew Gardens", zone: Zone.Zone1, brooklyn: false, lic: false, milesToPenn: 8.8, line: Line.CityPenn),

            new Station(name: "Jamaica", zone: Zone.Zone3, ignoreForAnalysis: true, milesToPenn: 10.8, line: Line.CityPenn),

            new Station(name: "Flushing Main Street", zone: Zone.Zone3, milesToPenn: 9.1, line: Line.PortWashington),
            new Station(name: "Murray Hill", zone: Zone.Zone3, milesToPenn: 10.2, line: Line.PortWashington),
            new Station(name: "Broadway", zone: Zone.Zone3, milesToPenn: 11, line: Line.PortWashington),
            new Station(name: "Auburndale", zone: Zone.Zone3, milesToPenn: 11.7, line: Line.PortWashington),
            new Station(name: "Bayside", zone: Zone.Zone3, milesToPenn: 12.6, line: Line.PortWashington),
            new Station(name: "Douglaston", zone: Zone.Zone3, milesToPenn: 13.9, line: Line.PortWashington),
            new Station(name: "Little Neck", zone: Zone.Zone3, milesToPenn: 14.5, line: Line.PortWashington),
            new Station(name: "Great Neck", zone: Zone.Zone4, milesToPenn: 15.7, line: Line.PortWashington),
            new Station(name: "Manhasset", zone: Zone.Zone4, milesToPenn: 17.2, line: Line.PortWashington),
            new Station(name: "Plandome", zone: Zone.Zone4, milesToPenn: 18.3, line: Line.PortWashington),
            new Station(name: "Port Washington", zone: Zone.Zone4, milesToPenn: 19.9, line: Line.PortWashington),

            new Station(name: "St. Albans", zone: Zone.Zone3, milesToPenn: 13.6, line: Line.WestHempstead),
            new Station(name: "Westwood", zone: Zone.Zone4, milesToPenn: 19.3, line: Line.WestHempstead),
            new Station(name: "Malverne", zone: Zone.Zone4, milesToPenn: 20.1, line: Line.WestHempstead),
            new Station(name: "Lakeview", zone: Zone.Zone4, milesToPenn: 21.2, line: Line.WestHempstead),
            new Station(name: "Hempstead Gardens", zone: Zone.Zone4, milesToPenn: 21.9, line: Line.WestHempstead),
            new Station(name: "West Hempstead", zone: Zone.Zone4, milesToPenn: 22.5, line: Line.WestHempstead),  //  Before Hempstead

            new Station(name: "New Hyde Park", zone: Zone.Zone4, milesToPenn: 18, line: Line.PortJefferson),
            new Station(name: "Merillon Avenue", zone: Zone.Zone4, milesToPenn: 19.1, line: Line.PortJefferson),
            new Station(name: "Mineola", zone: Zone.Zone4, milesToPenn: 20.3, line: Line.PortJefferson),
            new Station(name: "Carle Place", zone: Zone.Zone7, milesToPenn: 22.2, line: Line.PortJefferson),
            new Station(name: "Westbury", zone: Zone.Zone7, milesToPenn: 23.3, line: Line.PortJefferson),
            new Station(name: "Hicksville", zone: Zone.Zone7, milesToPenn: 26.6, line: Line.PortJefferson),
            new Station(name: "Syosset", zone: Zone.Zone7, milesToPenn: 30.9, line: Line.PortJefferson),
            new Station(name: "Cold Spring Harbor", zone: Zone.Zone9, milesToPenn: 33.7, line: Line.PortJefferson),
            new Station(name: "Huntington", zone: Zone.Zone9, milesToPenn: 36.5, line: Line.PortJefferson),
            new Station(name: "Greenlawn", zone: Zone.Zone9, milesToPenn: 39.2, line: Line.PortJefferson),
            new Station(name: "Northport", zone: Zone.Zone9, milesToPenn: 41.4, line: Line.PortJefferson),
            new Station(name: "Kings Park", zone: Zone.Zone10, milesToPenn: 45.2, line: Line.PortJefferson),
            new Station(name: "Smithtown", zone: Zone.Zone10, milesToPenn: 48.9, line: Line.PortJefferson),
            new Station(name: "St. James", zone: Zone.Zone10, milesToPenn: 51.7, line: Line.PortJefferson),
            new Station(name: "Stony Brook", zone: Zone.Zone10, milesToPenn: 54.9, line: Line.PortJefferson),
            new Station(name: "Port Jefferson", zone: Zone.Zone10, milesToPenn: 59.3, line: Line.PortJefferson),

            new Station(name: "Lynbrook", zone: Zone.Zone4, milesToPenn: 19.5, line: Line.LongBeach),
            new Station(name: "Rockville Centre", zone: Zone.Zone7, milesToPenn: 21.1, line: Line.Babylon),
            new Station(name: "Baldwin", zone: Zone.Zone7, milesToPenn: 23, line: Line.Babylon),
            new Station(name: "Freeport", zone: Zone.Zone7, milesToPenn: 24.5, line: Line.Babylon),
            new Station(name: "Merrick", zone: Zone.Zone7, milesToPenn: 25.9, line: Line.Babylon),
            new Station(name: "Bellmore", zone: Zone.Zone7, milesToPenn: 27.4, line: Line.Babylon),
            new Station(name: "Wantagh", zone: Zone.Zone7, milesToPenn: 27.8, line: Line.Babylon),
            new Station(name: "Seaford", zone: Zone.Zone7, milesToPenn: 29.5, line: Line.Babylon),
            new Station(name: "Massapequa Park", zone: Zone.Zone7, milesToPenn: 30.8, line: Line.Babylon),//  Before Massapequa
            new Station(name: "Massapequa", zone: Zone.Zone7, milesToPenn: 30.5, line: Line.Babylon),
            new Station(name: "Amityville", zone: Zone.Zone9, milesToPenn: 33.1, line: Line.Babylon),
            new Station(name: "Copiague", zone: Zone.Zone9, milesToPenn: 34.2, line: Line.Babylon),
            new Station(name: "Lindenhurst", zone: Zone.Zone9, milesToPenn: 35.5, line: Line.Babylon),
            new Station(name: "Babylon", zone: Zone.Zone9, milesToPenn: 38.4, line: Line.Babylon),
            new Station(name: "Bay Shore", zone: Zone.Zone10, milesToPenn: 42.5, line: Line.Montauk),
            new Station(name: "Islip", zone: Zone.Zone10, milesToPenn: 44.9, line: Line.Montauk),
            new Station(name: "Great River", zone: Zone.Zone10, milesToPenn: 47, line: Line.Montauk),
            new Station(name: "Oakdale", zone: Zone.Zone10, milesToPenn: 49.2, line: Line.Montauk),
            new Station(name: "Sayville", zone: Zone.Zone10, milesToPenn: 51.6, line: Line.Montauk),
            new Station(name: "Patchogue", zone: Zone.Zone10, milesToPenn: 55.7, line: Line.Montauk),
            new Station(name: "Bellport", zone: Zone.Zone12, milesToPenn: 59.6, line: Line.Montauk),
            new Station(name: "Mastic-Shirley", zone: Zone.Zone12, milesToPenn: 64.1, line: Line.Montauk),
            new Station(name: "Speonk", zone: Zone.Zone12, milesToPenn: 73.4, line: Line.Montauk),
            new Station(name: "Westhampton", zone: Zone.Zone14, milesToPenn: 76.1, line: Line.Montauk),
            new Station(name: "Hampton Bays", zone: Zone.Zone14, milesToPenn: 83, line: Line.Montauk),
            new Station(name: "Southampton", zone: Zone.Zone14, milesToPenn: 91.1, line: Line.Montauk),
            new Station(name: "Bridgehampton", zone: Zone.Zone14, milesToPenn: 95.8, line: Line.Montauk),
            new Station(name: "East Hampton", zone: Zone.Zone14, milesToPenn: 103, line: Line.Montauk),
            new Station(name: "Amagansett", zone: Zone.Zone14, milesToPenn: 106, line: Line.Montauk),
            new Station(name: "Montauk", zone: Zone.Zone14, milesToPenn: 117, line: Line.Montauk),

            new Station(name: "Locust Manor", zone: Zone.Zone3, milesToPenn: 14, line: Line.FarRockaway),
            new Station(name: "Laurelton", zone: Zone.Zone3, milesToPenn: 14.9, line: Line.FarRockaway),
            new Station(name: "Rosedale", zone: Zone.Zone3, milesToPenn: 15.8, line: Line.FarRockaway),
            new Station(name: "Valley Stream", zone: Zone.Zone4, milesToPenn: 17.9, line: Line.FarRockaway),
            new Station(name: "Gibson", zone: Zone.Zone4, milesToPenn: 18.8, line: Line.FarRockaway),
            new Station(name: "Hewlett", zone: Zone.Zone4, milesToPenn: 19.7, line: Line.FarRockaway),
            new Station(name: "Woodmere", zone: Zone.Zone4, milesToPenn: 20.3, line: Line.FarRockaway),
            new Station(name: "Cedarhurst", zone: Zone.Zone4, milesToPenn: 21.2, line: Line.FarRockaway),
            new Station(name: "Lawrence", zone: Zone.Zone4, milesToPenn: 22, line: Line.FarRockaway),
            new Station(name: "Inwood", zone: Zone.Zone4, milesToPenn: 22.4, line: Line.FarRockaway),
            new Station(name: "Far Rockaway", zone: Zone.Zone4, milesToPenn: 23, line: Line.FarRockaway),

            new Station(name: "Hollis", zone: Zone.Zone3, milesToPenn: 13.4, line: Line.Hempstead),
            new Station(name: "Queens Village", zone: Zone.Zone3, milesToPenn: 15, line: Line.Hempstead),
            new Station(name: "Bellerose", zone: Zone.Zone4, milesToPenn: 16.1, line: Line.Hempstead),
            new Station(name: "Floral Park", zone: Zone.Zone4, milesToPenn: 16.7, line: Line.Hempstead),
            new Station(name: "Stewart Manor", zone: Zone.Zone4, milesToPenn: 18.1, line: Line.Hempstead),
            new Station(name: "Nassau Boulevard", zone: Zone.Zone4, milesToPenn: 19.1, line: Line.Hempstead),
            new Station(name: "Garden City", zone: Zone.Zone4, milesToPenn: 20.2, line: Line.Hempstead),
            new Station(name: "Country Life Press", zone: Zone.Zone4, milesToPenn: 20.8, line: Line.Hempstead),
            new Station(name: "Hempstead", zone: Zone.Zone4, milesToPenn: 22.6, line: Line.Hempstead),

            new Station(name: "Centre Avenue", zone: Zone.Zone7, milesToPenn: 20.4, line: Line.LongBeach),
            new Station(name: "East Rockaway", zone: Zone.Zone7, milesToPenn: 20.9, line: Line.LongBeach),
            new Station(name: "Oceanside", zone: Zone.Zone7, milesToPenn: 21.4, line: Line.LongBeach),
            new Station(name: "Island Park", zone: Zone.Zone7, milesToPenn: 23.9, line: Line.LongBeach),
            new Station(name: "Long Beach", zone: Zone.Zone7, milesToPenn: 24.9, line: Line.LongBeach),

            new Station(name: "Oyster Bay", zone: Zone.Zone7, milesToPenn: 34.7, line: Line.OysterBay),
            new Station(name: "Locust Valley", zone: Zone.Zone7, milesToPenn: 30.8, line: Line.OysterBay),
            new Station(name: "Glen Cove", zone: Zone.Zone7, milesToPenn: 29.7, line: Line.OysterBay),
            new Station(name: "Glen Street", zone: Zone.Zone7, milesToPenn: 29.1, line: Line.OysterBay),
            new Station(name: "Sea Cliff", zone: Zone.Zone7, milesToPenn: 28.5, line: Line.OysterBay),
            new Station(name: "Glen Head", zone: Zone.Zone7, milesToPenn: 27.2, line: Line.OysterBay),
            new Station(name: "Greenvale", zone: Zone.Zone7, milesToPenn: 26, line: Line.OysterBay),
            new Station(name: "Roslyn", zone: Zone.Zone7, milesToPenn: 24, line: Line.OysterBay),
            new Station(name: "Albertson", zone: Zone.Zone7, milesToPenn: 22.6, line: Line.OysterBay),
            new Station(name: "East Williston", zone: Zone.Zone4, milesToPenn: 21.6, line: Line.OysterBay),

            new Station(name: "Bethpage", zone: Zone.Zone7, milesToPenn: 29.7, line: Line.Ronkonkoma),
            new Station(name: "Farmingdale", zone: Zone.Zone7, milesToPenn: 32, line: Line.Ronkonkoma),
            new Station(name: "Pinelawn", zone: Zone.Zone9, milesToPenn: 34.2, line: Line.Ronkonkoma),
            new Station(name: "Wyandanch", zone: Zone.Zone9, milesToPenn: 36.5, line: Line.Ronkonkoma),
            new Station(name: "Deer Park", zone: Zone.Zone9, milesToPenn: 40.2, line: Line.Ronkonkoma),
            new Station(name: "Brentwood", zone: Zone.Zone10, milesToPenn: 42.9, line: Line.Ronkonkoma),
            new Station(name: "Central Islip", zone: Zone.Zone10, milesToPenn: 45.4, line: Line.Ronkonkoma),
            new Station(name: "Ronkonkoma", zone: Zone.Zone10, milesToPenn: 50.3, line: Line.Ronkonkoma),
    ]

    static final Map<String, Station> STATION_NAME_MAP = STATIONS.collectEntries {
        [(it.name.toUpperCase()), it]
    };
    static final Map<Zone, Set<Station>> ZONE_STATION_MAP = Zone.values().collectEntries {
        Zone zone ->
            [(zone): STATIONS.findAll { Station station -> station.zone == zone }]
    }

    static final Station PENN_STATION = STATION_NAME_MAP[PENN_STATION_NAME]
    static final Station ATLANTIC_AVENUE = STATION_NAME_MAP[ATLANTIC_AVENUE_NAME]
    static final Station HUNTERSPOINT_AVENUE = STATION_NAME_MAP[HUNTERSPOINT_AVE_NAME]

    //  Holiday/Special Trains
    static final Set<String> TRAINS_TO_IGNORE = [
            //  Babylon
            "6092", "8792", "6094", "6096", "6098", "112", "116", "122", "124", "128", "136", "160", "1064", "3129", "4159", "3199", "2793", "197",
            //  Far Rockaway
            "6890", "6892", "6894", "6896", "6898", "6891", "6893", "6895", "6897", "2898", "2858", "3853", "3899",
            //  Hempstead
            "3753",
            //  Long Beach
            "6094", "6890", "6096", "6098", "6892", "6894", "6896", "6898", "6891", "6893", "6895", "6897", "124", "3853", "3899",
            //  Montauk
            "8792", "2710", "2793",
            //  Port Jeff
            "8102", "8104", "7690", "7698", "6690", "8106", "8085", "2100", "2102", "1708", "1250", "1710", "1252",
            //  Port Wash
            "6436", "6438", "6440", "354", "3361", "499",
            //  Ronkonkoma
            "8102", "8104", "8106", "8085", "200", "2100", "2102", "2095", "2097",
            //  W. Hempstead

    ] as Set;

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", zone=" + zone +
                ", ignoreForAnalysis=" + ignoreForAnalysis +
                '}';
    }
}
