package com.noscoope.blazeit.entity;


import com.noscoope.blazeit.*;
import com.noscoope.blazeit.screen.GameState;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import java.util.function.Consumer;

import static java.lang.Math.sqrt;

public class Player extends Mob {
    private Image texture = ResourceManager.getImage("player");
    private Sound laser = ResourceManager.getSound("laser");
    private Sound hurt = ResourceManager.getSound("player_hurt");
    private ParticleSystem particle;
    private int reloadTimer;
    private int reloadDelay = 110;
    private int hurtTimer;
    private int health;
    private int shelds = 5;
    private float overheated;
    private float overheatingSpeed = 4.9f;
    private float cooling = 0.028f;

    public Player(World world, float x, float y) {
        super(world, x, y, 97.0f, 87.0f);
        health = getMaxHealth();
        try {
            particle = ParticleIO.loadConfiguredSystem("res/streak.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fire() {
        overheated += overheatingSpeed;
        laser.play();
        world.addEntity(new Bullet(world, x + width / 2.0f + 10.0f, y));
        world.addEntity(new Bullet(world, x + width / 2.0f - 23.0f, y));
        reloadTimer = reloadDelay;
    }

    public void collide(Mob mob) {
        if (mob instanceof EnemyBullet) {
            EnemyBullet bullet = (EnemyBullet) mob;
            bullet.remove();
            hurt(bullet.getDamage());
        }
    }

    public void render(Graphics graphics) throws SlickException {
        texture.draw(x, y, getHurtColor());
        particle.render(x + 21.0f, y + 80.0f);
        particle.render(x + 73.0f, y + 80.0f);
    }

    public void update(int delta) throws SlickException {
        particle.update(delta);
        overheated -= cooling * (float) delta;
        if (overheated < 0.0f) {
            overheated = 0.0f;
        }

        hurtTimer -= delta;
        if (hurtTimer < 0) {
            hurtTimer = 0;
        }

        var mouse = new Vector2f(GameState.input.getMouseX(), GameState.input.getMouseY());
        Vector2f gyroscope = new Vector2f(GameState.input.getMouseX(), Math.max(GameState.input.getMouseY(), 450));

        Vector2f distance = new Vector2f(
                (float) sqrt(((gyroscope.x - x - width / 2.0f) * (gyroscope.x - x - width / 2.0f))),
                (float) sqrt(((gyroscope.y - y - height / 2.0f) * (gyroscope.y - y - height / 2.0f)))
        );


        Vector2f speed = new Vector2f(distance.x / 250.0f, distance.y / 250.0f);

        x += speed.x * (float) delta * gyroscopeIndication(gyroscope, Equity.HORIZONTAL);
        y += speed.y * (float) delta * gyroscopeIndication(gyroscope, Equity.VERTICAL);

        reloadTimer -= delta;
        ifFireButtonIsPressed(Player::fire);
    }

    private enum Equity {
        HORIZONTAL, VERTICAL;
    }

    private int gyroscopeIndication(Vector2f vector, Equity equity) {
        switch (equity) {
            case HORIZONTAL -> {
                return vector.x > x + width / 2.0f ? +1 : -1;
            }
            case VERTICAL -> {
                return vector.y > y + height / 2.0f ? +1 : -1;
            }
        }
        throw new IllegalArgumentException();
    }

    private void ifFireButtonIsPressed(Consumer<Player> consumer) {
        var canShoot = reloadTimer < 0 && !isOverheated();
        if (canShoot && (GameState.input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) || GameState.input.isKeyDown(Input.KEY_SPACE))) {
            consumer.accept(this);
        }
    }

    public void addShield(int amount) {
        shelds += amount;
        if (shelds > getMaxShields()) {
            shelds = getMaxShields();
        }
    }

    public Shape getAABB() {
        return new Circle(
                x + width / 2.0f,
                y + height / 2.0f,
                35.0f
        );
    }

    public void hurt(int damage) {
        world.addEntity(new Explosion(world, ExplosionType.MOON, x, y));
        if (hasShield()) {
            shelds--;
        } else {
            health -= damage;
            if (health <= 0) {
                die();
            }
            hurtTimer = 200;
            hurt.play();
        }
    }

    public void heal(float amount) {
        health = (int) (health + amount);
        if (health > getMaxHealth()) {
            health = getMaxHealth();
        }

    }

    public boolean isOverheated() {
        return overheated > 100.0F;
    }

    public float getOverheating() {
        return overheated;
    }

    private void die() {
        this.remove();
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return 100;
    }

    private boolean isRecentlyHurt() {
        return hurtTimer > 0;
    }

    private Color getHurtColor() {
        return isRecentlyHurt() ? Color.red : Color.white;
    }

    public int getShields() {
        return shelds;
    }

    public int getMaxShields() {
        return 5;
    }

    public boolean hasShield() {
        return shelds > 0;
    }
}
