package com.xls.snak.utils;

public class Direction {
    public static final short Up = 0, Right = 1, Down = 2, Left = 3;

    public static short Inverse(short dir) {
        return (short) ((dir + 2) % 4);
    }

    public static short TurnRight(short dir) {
        return (short) ((dir + 1) % 4);
    }
}
