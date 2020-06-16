package com.noscoope.blazeit;


import com.noscoope.blazeit.entity.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.RoundedRectangle;

import java.util.stream.IntStream;

public class Gui {
    private World world;
    private UnicodeFont font;

    public Gui(World world) throws SlickException {
        this.world = world;
        this.font = new UnicodeFont("res/fonts/franchise-Bold.ttf", 23, false, false);
        this.font.addAsciiGlyphs();
        this.font.getEffects().add(new ColorEffect());
        this.font.loadGlyphs();
    }

    public void render(GameContainer container, Graphics graphics) throws SlickException {
        Player player = this.world.getPlayer();
        ResourceManager.getImage("panel_upper_left").draw();
        font.drawString(15.0f, 7.0f, "Score: " + this.world.getScore());
        float overheatX = (float)(container.getWidth() - 209);
        float overheatY = 8.0f;
        float overheatWidth = 200.0f;
        float overheatHeight = 17.0f;
        float overheat = MathUtils.clamp(player.getOverheating(), 0f, 100f);

        ResourceManager.getImage("panel_upper_right").draw((float)(container.getWidth() - 220), 0.0f);
        graphics.setColor(new Color(60, 70, 129, 100));
        graphics.fillRoundRect(overheatX, overheatY, overheatWidth, overheatHeight, 10);
        GradientFill fill = new GradientFill(overheatX, overheatY, new Color(255, 210, 73), overheatX, overheatHeight, new Color(238, 37, 53));
        graphics.fill(new RoundedRectangle(overheatX, overheatY, overheatWidth * overheat / 100.0f, overheatHeight, 10.0f), fill);
        ResourceManager.getImage("panel_lower_left").draw(0.0f, (float)(container.getHeight() - 40));
        Image activeShield = ResourceManager.getImage("active_shield");
        Image wornShield = ResourceManager.getImage("worn_shield");
        float shieldX = 65.0f;
        float shieldY = (float) (container.getHeight() - 28);

        for (int i = 0; i < player.getMaxShields(); i++) {
            if (i >= player.getShields()) {
                wornShield.draw(shieldX + (float)(i * 17), shieldY);
            } else {
                activeShield.draw(shieldX + (float)(i * 17), shieldY);
            }
        }

        String health = "hp " + player.getHealth();
        this.font.drawString(10.0f, (float)(container.getHeight() - 30), health);
    }

    public void update(GameContainer container, int delta) throws SlickException {
    }
}
