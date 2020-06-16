package com.noscoope.blazeit;

import org.newdawn.slick.Color;

public class ColorTools {
    public static void visualSeekAlpha(Color currentColor, float targetAlpha, float animationSpeed) {
        currentColor.a += (targetAlpha - currentColor.a) * animationSpeed;
    }
}
