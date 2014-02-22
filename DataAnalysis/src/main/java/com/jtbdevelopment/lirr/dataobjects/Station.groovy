package com.jtbdevelopment.lirr.dataobjects

/**
 * Date: 2/15/14
 * Time: 1:37 PM
 */
class Station {
    public static final String PENN_STATION_NAME = "PENN STATION"
    public static final String ATLANTIC_AVENUE_NAME = "ATLANTIC TERMINAL"
    public static final String HUNTERSPOINT_AVE_NAME = "HUNTERSPOINT AVE."
    String name
    Zone zone
    boolean ignoreForAnalysis = false  // Primarily for penn, etc

    static final List<Station> STATIONS = [
            //  Names must be as they appear on the schedule
            new Station(name: "Penn Station", zone: Zone.Zone1, ignoreForAnalysis: true),
            new Station(name: "Atlantic Terminal", zone: Zone.Zone1, ignoreForAnalysis: true),
            new Station(name: "Long Island City", zone: Zone.Zone1, ignoreForAnalysis: true),
            new Station(name: "Hunterspoint Ave.", zone: Zone.Zone1, ignoreForAnalysis: true),

            new Station(name: "Nostrand Avenue", zone: Zone.Zone1),
            new Station(name: "East New York", zone: Zone.Zone1),

            new Station(name: "Woodside", zone: Zone.Zone1),
            new Station(name: "Forest Hills", zone: Zone.Zone1),
            new Station(name: "Kew Gardens", zone: Zone.Zone1),

            new Station(name: "Jamaica", zone: Zone.Zone3, ignoreForAnalysis: true),

            new Station(name: "Flushing Main Street", zone: Zone.Zone3),
            new Station(name: "Murray Hill", zone: Zone.Zone3),
            new Station(name: "Broadway", zone: Zone.Zone3),
            new Station(name: "Auburndale", zone: Zone.Zone3),
            new Station(name: "Bayside", zone: Zone.Zone3),
            new Station(name: "Douglaston", zone: Zone.Zone3),
            new Station(name: "Little Neck", zone: Zone.Zone3),
            new Station(name: "Great Neck", zone: Zone.Zone4),
            new Station(name: "Manhasset", zone: Zone.Zone4),
            new Station(name: "Plandome", zone: Zone.Zone4),
            new Station(name: "Port Washington", zone: Zone.Zone4),

            new Station(name: "St. Albans", zone: Zone.Zone3),
            new Station(name: "Westwood", zone: Zone.Zone4),
            new Station(name: "Malverne", zone: Zone.Zone4),
            new Station(name: "Lakeview", zone: Zone.Zone4),
            new Station(name: "Hempstead Gardens", zone: Zone.Zone4),
            new Station(name: "West Hempstead", zone: Zone.Zone4),  //  Before Hempstead

            new Station(name: "New Hyde Park", zone: Zone.Zone4),
            new Station(name: "Merillon Avenue", zone: Zone.Zone4),
            new Station(name: "Mineola", zone: Zone.Zone4),
            new Station(name: "Carle Place", zone: Zone.Zone7),
            new Station(name: "Westbury", zone: Zone.Zone7),
            new Station(name: "Hicksville", zone: Zone.Zone7),
            new Station(name: "Syosset", zone: Zone.Zone7),
            new Station(name: "Cold Spring Harbor", zone: Zone.Zone9),
            new Station(name: "Huntington", zone: Zone.Zone9),
            new Station(name: "Greenlawn", zone: Zone.Zone9),
            new Station(name: "Northport", zone: Zone.Zone9),
            new Station(name: "Kings Park", zone: Zone.Zone10),
            new Station(name: "Smithtown", zone: Zone.Zone10),
            new Station(name: "St. James", zone: Zone.Zone10),
            new Station(name: "Stony Brook", zone: Zone.Zone10),
            new Station(name: "Port Jefferson", zone: Zone.Zone10),

            new Station(name: "Lynbrook", zone: Zone.Zone4),
            new Station(name: "Rockville Centre", zone: Zone.Zone7),
            new Station(name: "Baldwin", zone: Zone.Zone7),
            new Station(name: "Freeport", zone: Zone.Zone7),
            new Station(name: "Merrick", zone: Zone.Zone7),
            new Station(name: "Bellmore", zone: Zone.Zone7),
            new Station(name: "Wantagh", zone: Zone.Zone7),
            new Station(name: "Seaford", zone: Zone.Zone7),
            new Station(name: "Massapequa Park", zone: Zone.Zone7),//  Before Massapequa
            new Station(name: "Massapequa", zone: Zone.Zone7),
            new Station(name: "Amityville", zone: Zone.Zone9),
            new Station(name: "Copiague", zone: Zone.Zone9),
            new Station(name: "Lindenhurst", zone: Zone.Zone9),
            new Station(name: "Babylon", zone: Zone.Zone9),
            new Station(name: "Bay Shore", zone: Zone.Zone10),
            new Station(name: "Islip", zone: Zone.Zone10),
            new Station(name: "Great River", zone: Zone.Zone10),
            new Station(name: "Oakdale", zone: Zone.Zone10),
            new Station(name: "Sayville", zone: Zone.Zone10),
            new Station(name: "Patchogue", zone: Zone.Zone10),
            new Station(name: "Bellport", zone: Zone.Zone12),
            new Station(name: "Mastic-Shirley", zone: Zone.Zone12),
            new Station(name: "Speonk", zone: Zone.Zone12),
            new Station(name: "Westhampton", zone: Zone.Zone14),
            new Station(name: "Hampton Bays", zone: Zone.Zone14),
            new Station(name: "Southampton", zone: Zone.Zone14),
            new Station(name: "Bridgehampton", zone: Zone.Zone14),
            new Station(name: "East Hampton", zone: Zone.Zone14),
            new Station(name: "Amagansett", zone: Zone.Zone14),
            new Station(name: "Montauk", zone: Zone.Zone14),

            new Station(name: "Locust Manor", zone: Zone.Zone3),
            new Station(name: "Laurelton", zone: Zone.Zone3),
            new Station(name: "Rosedale", zone: Zone.Zone3),
            new Station(name: "Valley Stream", zone: Zone.Zone4),
            new Station(name: "Gibson", zone: Zone.Zone4),
            new Station(name: "Hewlett", zone: Zone.Zone4),
            new Station(name: "Woodmere", zone: Zone.Zone4),
            new Station(name: "Cedarhurst", zone: Zone.Zone4),
            new Station(name: "Lawrence", zone: Zone.Zone4),
            new Station(name: "Inwood", zone: Zone.Zone4),
            new Station(name: "Far Rockaway", zone: Zone.Zone4),

            new Station(name: "Hollis", zone: Zone.Zone3),
            new Station(name: "Queens Village", zone: Zone.Zone3),
            new Station(name: "Bellerose", zone: Zone.Zone4),
            new Station(name: "Floral Park", zone: Zone.Zone4),
            new Station(name: "Stewart Manor", zone: Zone.Zone4),
            new Station(name: "Nassau Boulevard", zone: Zone.Zone4),
            new Station(name: "Garden City", zone: Zone.Zone4),
            new Station(name: "Country Life Press", zone: Zone.Zone4),
            new Station(name: "Hempstead", zone: Zone.Zone4),

            new Station(name: "Centre Avenue", zone: Zone.Zone3),
            new Station(name: "East Rockaway", zone: Zone.Zone3),
            new Station(name: "Oceanside", zone: Zone.Zone3),
            new Station(name: "Island Park", zone: Zone.Zone3),
            new Station(name: "Long Beach", zone: Zone.Zone3),

            new Station(name: "Oyster Bay", zone: Zone.Zone7),
            new Station(name: "Locust Valley", zone: Zone.Zone7),
            new Station(name: "Glen Cove", zone: Zone.Zone7),
            new Station(name: "Glen Street", zone: Zone.Zone7),
            new Station(name: "Sea Cliff", zone: Zone.Zone7),
            new Station(name: "Glen Head", zone: Zone.Zone7),
            new Station(name: "Greenvale", zone: Zone.Zone7),
            new Station(name: "Roslyn", zone: Zone.Zone7),
            new Station(name: "Albertson", zone: Zone.Zone7),
            new Station(name: "East Williston", zone: Zone.Zone4),

            new Station(name: "Bethpage", zone: Zone.Zone7),
            new Station(name: "Farmingdale", zone: Zone.Zone7),
            new Station(name: "Pinelawn", zone: Zone.Zone9),
            new Station(name: "Wyandanch", zone: Zone.Zone9),
            new Station(name: "Deer Park", zone: Zone.Zone9),
            new Station(name: "Brentwood", zone: Zone.Zone10),
            new Station(name: "Central Islip", zone: Zone.Zone10),
            new Station(name: "Ronkonkoma", zone: Zone.Zone10),
    ]

    static final Map<String, Station> STATION_NAME_MAP = STATIONS.collectEntries({
        [(it.name.toUpperCase()), it]
    });

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
