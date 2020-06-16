package com.noscoope.blazeit;

public class Background {
    private static float scrollingSpeed = 0.07f;
    public float x;
    public float y;

    public Background(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void udate(int delta) {
        y += scrollingSpeed * (float) delta;
        if (y > 800.0f) {
            y -= 1600.0f;
        }
    }
}
