package com.jtbdevelopment.LIRR.dataobjects.core

/**
 * Date: 3/12/14
 * Time: 9:56 PM
 */
public enum Line {
    Babylon,
    CityBrooklyn("City - Brooklyn"),
    CityPenn("City - Penn"),
    CityLIC("City - LIC"),
    FarRockaway("Far Rockaway"),
    Hempstead,
    LongBeach,
    Montauk,
    OysterBay("Oyster Bay"),
    PortJefferson("Port Jefferson"),
    PortWashington("Port Washington"),
    Ronkonkoma,
    WestHempstead("West Hempstead")

    String name;

    Line() {
        name = this.toString()
    }

    Line(final String name) {
        this.name = name;
    }
}