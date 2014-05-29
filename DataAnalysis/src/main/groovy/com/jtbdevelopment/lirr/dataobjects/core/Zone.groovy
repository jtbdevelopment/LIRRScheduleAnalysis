package com.jtbdevelopment.lirr.dataobjects.core

/**
 * Date: 2/15/14
 * Time: 1:37 PM
 */
public enum Zone {
    Zone1(1),
    Zone3(3),
    Zone4(4),
    Zone7(7),
    Zone9(9),
    Zone10(10),
    Zone12(12),
    Zone14(14);

    private Zone(final int numeric) {
        this.numeric = numeric;
    }

    final int numeric;
}