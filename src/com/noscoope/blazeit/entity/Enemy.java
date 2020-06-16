package com.noscoope.blazeit.entity;

import com.noscoope.blazeit.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

public class Enemy extends Mob {

    private Image image = ResourceManager.getImage("alien");
    private int health;
    private int hurtTimer;
    private int reloadTimer;
    private float incomingSpeed = 0.2f;
    private float targetX;

    public Enemy(World world, float x, float y) {
        super(world, 1024.0F + 1.5F * x, y, 65.0F, 40.0F);
        this.targetX = x;
        this.health = this.getMaxHealth();
        this.reloadTimer = MathUtils.random(7000);
    }

    public void collide(Mob mob) {
        if (mob instanceof Bullet) {
            var bullet = (Bullet) mob;
            bullet.remove();
            var damage = (float) bullet.getDamage();
            this.hurt(damage);
        }
    }

    public void hurt(float damage) {
        health = (int) (this.health - damage);
        if (health <= 0) {
            die();
        }
        hurtTimer = 100;
    }

    private void die() {
        world.addEntity(new Explosion(world, ExplosionType.MOON, this.x - 15.0f, this.y - 30.0f));
        world.addScore(50);
        world.addDeathAlien();
        if (MathUtils.random(10) <= 2) {
            world.addEntity(new Pickup(world, x + 16.0f, y));
        }
        remove();
    }

    public void render(Graphics graphics) {
        image.draw(this.x, this.y, this.getHurtColor());
    }

    private boolean isReachedPosition() {
        return x <= targetX;
    }

    public void update(int delta) {
        int accuracy = 10;
        for (int i = 0; i < accuracy; i++) {
            float speed = incomingSpeed / (float) accuracy;
            if (isReachedPosition()) break;
            x -= speed * (float) delta;
        }

        hurtTimer -= delta;
        if (hurtTimer < 0) {
            hurtTimer = 0;
        }

        reloadTimer -= delta;
        if (reloadTimer < 0) {
            EnemyBullet bullet = new EnemyBullet(world, x + 12.0f, y + 40.0f);
            world.addEntity(bullet);
            reloadTimer = 3000 + MathUtils.random(4000);
        }

    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return 70;
    }

    private boolean isRecentlyHurt() {
        return hurtTimer > 0;
    }

    private Color getHurtColor() {
        return isRecentlyHurt() ? Color.red : Color.white;
    }
}
