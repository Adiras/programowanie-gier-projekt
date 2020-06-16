package com.noscoope.blazeit;

import java.util.Random;

public class MathUtils {
    private final static Random random = new Random();

    public static int random(int range) {
        return random.nextInt(range + 1);
    }

    public static int random(int start, int end) {
        return start + random.nextInt(end - start + 1);
    }

    public static long random(long range) {
        return (long) (random.nextDouble() * range);
    }

    public static long random(long start, long end) {
        return start + (long)(random.nextDouble() * (end - start));
    }

    public static short clamp(short value, short min, short max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static long clamp(long value, long min, long max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

}
