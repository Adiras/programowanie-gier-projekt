package com.noscoope.blazeit.entity;

import com.noscoope.blazeit.World;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class Mob extends Entity {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public Mob(World world, float x, float y, float width, float height) {
        super(world);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Shape getAABB() {
        return new Rectangle(x, y, width, height);
    }

    public boolean checkCollision(Mob other) {
        return getAABB().intersects(other.getAABB());
    }

    public void collide(Mob mob) {}
}
