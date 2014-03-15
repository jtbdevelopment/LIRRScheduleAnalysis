package com.jtbdevelopment.lirr.dataobjects.core

/**
 * Date: 3/12/14
 * Time: 9:56 PM
 */
public enum Line {
    FarRockaway("Far Rockaway"),
    OysterBay("Oyster Bay"),
    Hempstead,
    LongBeach,
    WestHempstead("West Hempstead"),
    PortJefferson("Port Jefferson"),
    Montauk,
    PortWashington("Port Washington"),
    Ronkonkoma,
    Babylon,
    CityBrooklyn("City - Brooklyn"),
    CityPenn("City - Penn"),
    CityLIC("City - LIC")

    String name;

    Line() {
        name = this.toString()
    }

    Line(final String name) {
        this.name = name;
    }
}