package com.noscoope.blazeit.entity;

import com.noscoope.blazeit.ResourceManager;
import com.noscoope.blazeit.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.Random;
import java.util.function.Consumer;

public class Pickup extends Mob {
    private float fallingSpeed = 0.2F;
    private PickupType type = this.getRandomType();
    private Image image = ResourceManager.getImage("pickup");

    public Pickup(World world, float x, float y) {
        super(world, x, y, 32.0F, 32.0F);
    }

    private PickupType getRandomType() {
        PickupType[] types = PickupType.values();
        Random generator = new Random();
        return types[generator.nextInt(types.length)];
    }

    public void collide(Mob mob) {
        if (mob instanceof Player) {
            Player player = (Player)mob;
            switch(type) {
                case SCORE:
                    this.world.addScore(20);
                    break;
                case HEALTH:
                    player.heal(15.0F);
                    break;
                case SHIELD:
                    player.addShield(1);
            }
            remove();
        }
    }

    public void render(Graphics g) throws SlickException {
        image.draw(x, y, width, height);
    }

    private void ifOutOfWorld(Consumer<Pickup> consumer) {
        if (y > 768.0f) consumer.accept(this);
    }

    public void update(int delta) throws SlickException {
        y += fallingSpeed * (float) delta;
        ifOutOfWorld(Entity::remove);
    }

    private enum PickupType {
        SCORE, HEALTH, SHIELD;
    }
}