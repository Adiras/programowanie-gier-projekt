package com.noscoope.blazeit;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public enum ExplosionType {
    MOON("res/images/moon.png", 100, 100, 45);

    private int duration;
    private SpriteSheet frames;

    ExplosionType(String reference, int width, int height, int duration) {
        try {
            frames = new SpriteSheet(reference, width, height);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        this.duration = duration;
    }

    public SpriteSheet getFrames() {
        return frames;
    }

    public int getDuration() {
        return duration;
    }
}
