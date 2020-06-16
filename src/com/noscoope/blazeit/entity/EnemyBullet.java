package com.noscoope.blazeit.entity;

import com.noscoope.blazeit.MathUtils;
import com.noscoope.blazeit.ResourceManager;
import com.noscoope.blazeit.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EnemyBullet extends Mob {
    private Image image = ResourceManager.getImage("bullet_alien");
    private int damage = 10;
    private float speed = 0.25f + (MathUtils.random(6) / 100.0f);

    public EnemyBullet(World world, float x, float y) {
        super(world, x, y, 27.0f, 26.0f);
    }

    public void render(Graphics graphics) throws SlickException {
        image.draw(x, y);
    }

    public void update(int delta) throws SlickException {
        y += speed * (float) delta;
        if (y > 768.0f) {
            remove();
        }
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
