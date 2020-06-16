package com.noscoope.blazeit;

import com.noscoope.blazeit.entity.Enemy;
import com.noscoope.blazeit.entity.Entity;
import com.noscoope.blazeit.entity.Mob;
import com.noscoope.blazeit.entity.Player;
import com.noscoope.blazeit.screen.GameState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

public class World {
    private Player player = new Player(this, 464.0f, 650.0f);
    private ArrayList<Entity> entities = new ArrayList();
    private int score;
    private int deathAliens;
    private ArrayList<Wave> waves;
    private ArrayList<String> waveNames;
    private int currentWave = 0;

    public World() {
        addEntity(player);
        waves = new ArrayList();
        waveNames = new ArrayList();
        bootstrap();
    }

    private int getRemainingAliens() {
        return (int) entities.stream()
                .filter(entity -> entity instanceof Enemy)
                .count();
//        int remainingAliens = 0;
//        var iterator = entities.iterator();
//        while (iterator.hasNext()) {
//            Entity current = iterator.next();
//            if (current instanceof Enemy) {
//                remainingAliens++;
//            }
//        }
//        return remainingAliens;
    }

    public void createEnemyWave(String name, Wave wave) {
        waveNames.add(name);
        waves.add(wave);
    }

    private boolean isWaveComplete() {
        return getRemainingAliens() == 0;
    }

    private void spawnAlienGroup(int aliens) {
        spawnAlienGroup(aliens, 100.0f);
    }

    private void spawnAlienGroup(int aliens, float y) {
        float a = 1024.0f;
        float n = (float) aliens;
        float w = 65.0f;
        float b = a / (n + 1.0f);

        for (int i = 1; i < aliens + 1; ++i) {
            this.addEntity(new Enemy(this, (float)i * b - w / 2.0F, y));
        }

    }

    public void createNewWave() {
        currentWave++;
        if (currentWave > waves.size()) {
            player.remove();
        } else {
            var previousWave = currentWave - 1;
            waves.get(previousWave).execute(this);
        }
    }

    private String getCurrentWaveName() {
        return currentWave < waveNames.size() ? waveNames.get(currentWave) : "";
    }

    public void render(GameContainer container, Graphics graphics) throws SlickException {
        var iterator = this.entities.iterator();
        while (iterator.hasNext()) {
            Entity current = iterator.next();
            current.render(graphics);
        }
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (isWaveComplete()) {
            GameState.announcer.updateMessage(container, getCurrentWaveName());
            createNewWave();
        }

        for (int i = 0; i < entities.size(); ++i) {
            Entity current = entities.get(i);
            if (current.isRemoved()) {
                entities.remove(i);
            } else {
                current.update(delta);

                for (int j = 0; j < this.entities.size(); ++j) {
                    Entity other = entities.get(j);
                    if (other.isRemoved()) {
                        entities.remove(j);
                    } else if (i != j && current instanceof Mob && other instanceof Mob) {
                        Mob a = (Mob) current;
                        Mob b = (Mob) other;
                        if (a.checkCollision(b)) {
                            a.collide(b);
                            b.collide(a);
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Mob> getMobs() {
        ArrayList<Mob> mobs = new ArrayList();
        var iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity current = iterator.next();
            if (current instanceof Mob) {
                Mob mob = (Mob) current;
                mobs.add(mob);
            }
        }
        return mobs;
    }

    public void addDeathAlien() {
        deathAliens++;
    }

    public int getDeathAliens() {
        return deathAliens;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public Player getPlayer() {
        return player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public boolean isGameOver() {
        return player.isRemoved();
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int getScore() {
        return score;
    }

    public void bootstrap() {
        createEnemyWave("Wave 1 - beginning of the invasion", world -> {
            spawnAlienGroup(3);
            spawnAlienGroup(2, 150.0F);
            spawnAlienGroup(1, 200.0F);
        });

        createEnemyWave("Wave 2", world -> {
            spawnAlienGroup(5);
            spawnAlienGroup(3, 150.0F);
            spawnAlienGroup(5, 200.0F);
        });

        createEnemyWave("Wave 3", world -> {
            spawnAlienGroup(8);
            spawnAlienGroup(5, 150.0F);
            spawnAlienGroup(3, 200.0F);
        });

        createEnemyWave("Wave 4 - aliens rush", world -> {
            spawnAlienGroup(6);
            spawnAlienGroup(5, 150.0F);
            spawnAlienGroup(4, 200.0F);
            spawnAlienGroup(3, 250.0F);
            spawnAlienGroup(2, 300.0F);
        });

        createEnemyWave("Wave 5 - rain of asteroids", world -> {
            spawnAlienGroup(6);
            spawnAlienGroup(4, 150.0f);
            spawnAlienGroup(8, 200.0f);
            spawnAlienGroup(5, 250.0f);
            spawnAlienGroup(8, 300.0f);
        });

        createEnemyWave("Wave 6", world -> {
            spawnAlienGroup(7);
            spawnAlienGroup(5, 150.0f);
            spawnAlienGroup(3, 200.0f);
            spawnAlienGroup(7, 250.0f);
            spawnAlienGroup(4, 300.0f);
        });

        createEnemyWave("Wave 7", world -> {
            spawnAlienGroup(8);
            spawnAlienGroup(7, 150.0f);
            spawnAlienGroup(6, 200.0f);
            spawnAlienGroup(5, 250.0f);
            spawnAlienGroup(4, 300.0f);
            spawnAlienGroup(3, 350.0f);
        });

        createEnemyWave("Wave 8 - the end", world -> {
            spawnAlienGroup(6);
            spawnAlienGroup(4, 150.0f);
            spawnAlienGroup(8, 200.0f);
            spawnAlienGroup(5, 250.0f);
            spawnAlienGroup(8, 300.0f);
        });
    }
}
