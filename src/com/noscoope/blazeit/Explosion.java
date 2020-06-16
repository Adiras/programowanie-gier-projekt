package com.noscoope.blazeit;

import com.noscoope.blazeit.entity.Entity;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Explosion extends Entity {
    private Animation animation;
    private float x;
    private float y;

    public Explosion(World world, ExplosionType type, float x, float y) {
        super(world);
        this.x = x;
        this.y = y;
        this.animation = new Animation(type.getFrames(), type.getDuration());
        this.animation.setLooping(false);
    }

    public void render(Graphics graphics) throws SlickException {
        animation.draw(x, y);
    }

    public void update(int delta) throws SlickException {
        animation.update(delta);
        if (animation.isStopped()) {
            remove();
        }
    }
}
