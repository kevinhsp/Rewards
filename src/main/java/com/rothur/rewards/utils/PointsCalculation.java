package com.rothur.rewards.utils;

public class PointsCalculation {
    public static Long calculatePoints(Double price) {
        long flooredPrice = Math.round(Math.floor(price));
        if(flooredPrice <= 50) return 0L;
        if(flooredPrice <= 100) return flooredPrice - 50L;
        return 50L + (flooredPrice - 100) * 2;
    }
}
