package com.noscoope.blazeit.entity;

import com.noscoope.blazeit.ResourceManager;
import com.noscoope.blazeit.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Bullet extends Mob {
    private static Image image = ResourceManager.getImage("bullet");
    private float speed = 0.6f;

    public Bullet(World world, float x, float y) {
        super(world, x, y, 11.0f, 38.0f);
    }

    public void render(Graphics graphics)  {
        image.draw(x, y);
    }

    public void update(int delta) {
        y -= speed * (float) delta;
        if (y + height < 0.0f) {
            remove();
        }
    }

    public int getDamage() {
        return 5;
    }
}
