package com.noscoope.blazeit.entity;

import com.noscoope.blazeit.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class Entity {
    public static long nextId = 0L;
    protected final World world;
    private final long id;
    private boolean removed;

    public Entity(World world) {
        this.world = world;
        this.removed = false;
        this.id = nextId++;
    }

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public abstract void render(Graphics graphics) throws SlickException;

    public abstract void update(int delta) throws SlickException;
}
