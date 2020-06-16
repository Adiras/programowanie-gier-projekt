package com.noscoope.blazeit;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Announcer {
    private final Color baseColor;
    private final float fadeInDuration = 0.2f;
    private final float fadeOutDuration = 0.2f;
    private final long duration = 3500L;
    private final int yPosition;
    private long startTime = 0L;
    private Color textColor;
    private UnicodeFont announcementFont;
    private String message = ""; // initialize as empty message

    public Announcer(GameContainer container, int yPosition, Color baseColor) {
        this.textColor = new Color(Color.white);
        this.yPosition = yPosition;
        this.baseColor = baseColor;

        try {
            announcementFont = new UnicodeFont("res/fonts/Franchise-Bold.ttf", 70, false, false);
            announcementFont.addAsciiGlyphs();
            announcementFont.getEffects().add(new ColorEffect());
            announcementFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMessage(GameContainer container, String message) {
        if (message.equals(message)) {
            long currentTime = container.getTime();
            if (currentTime > startTime + 3500 + 1000) {
                postMessage(container, message);
            }
        } else {
            postMessage(container, message);
        }
    }

    public void postMessage(GameContainer container, String message) {
        this.message = message;
        this.startTime = container.getTime();
        this.textColor = new Color(baseColor.r, baseColor.g, baseColor.b, 0.0f);
    }

    public void update(GameContainer container, int delta) {
        float deltaTime = (float) delta / 1000.0f;
        updateTextColor(container, deltaTime);
    }

    public void render(GameContainer container, Graphics graphics) {
        var currentTime = container.getTime();
        if (shouldDraw(currentTime)) {
            int width  = announcementFont.getWidth(message);
            int height = announcementFont.getHeight(message);
            int x = container.getWidth() / 2 - width / 2;
            int y = yPosition;
            drawMessage(graphics, x, y);
        }
    }

    private void drawMessage(Graphics graphics,
                             float x,
                             float y) {
        graphics.setColor(textColor);
        graphics.setFont(announcementFont);
        graphics.drawString(message, x, y);
        graphics.resetFont();
    }

    private boolean shouldDraw(long currentTime) {
        return currentTime > startTime && currentTime < startTime + 3500;
    }

    private void updateTextColor(GameContainer container, float delta) {
        if (container.getTime() < startTime + 200) {
            ColorTools.visualSeekAlpha(textColor, 1.0f, 0.020000001f);
        } else if (container.getTime() > startTime + 3500 - 200) {
            ColorTools.visualSeekAlpha(textColor, 0.0f, 0.020000001f);
        } else {
            this.textColor = new Color(baseColor);
        }

    }
}
