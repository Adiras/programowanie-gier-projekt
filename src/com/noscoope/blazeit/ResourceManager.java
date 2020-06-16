package com.noscoope.blazeit;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResourceManager {
    private static Map<String, Image> images = new HashMap<>();
    private static Map<String, Music> musics = new HashMap<>();
    private static Map<String, Sound> sounds = new HashMap<>();

    static {
        try {
            loadAllResources();
        } catch (SlickException exception) {
            exception.printStackTrace();
        }
    }

    private static void loadAllResources() throws SlickException {
        addImage("background_menu", ("res/images/background_menu.png"));
        addImage("pickup", ("res/images/pickup.png"));
        addImage("background", ("res/images/background.png"));
        addImage("player", ("res/images/player.png"));
        addImage("alien", ("res/images/alien.png"));
        addImage("bullet", ("res/images/fire_54ca885968311.png"));
        addImage("bullet_alien", ("res/images/zielony.png"));
        addImage("panel_upper_left", ("res/images/panel_upper_left.png"));
        addImage("panel_upper_right", ("res/images/panel_upper_right.png"));
        addImage("panel_lower_left", ("res/images/panel_lower_left.png"));
        addImage("active_shield", ("res/images/active_shield.png"));
        addImage("worn_shield", ("res/images/worn_shield.png"));
        addMusic("main", ("res/musics/hz_dc_whitehouse_endrun_LR_r1.ogg"));
        addSound("laser", ("res/sounds/4529_1329823883.wav"));
        addSound("player_hurt", ("res/sounds/Torpedo Impact-SoundBible.com-765913562.wav"));
        addSound("menu_move", ("res/sounds/2704_1329133088.wav"));
        addSound("menu_select", ("res/sounds/6360_1341227436.wav"));
        addSound("echidna_hurt", ("res/sounds/10317_1369921522.wav"));
    }

    public static void addImage(String key, String reference) throws SlickException {
        if (hasImage(key)) {
            throw new IllegalArgumentException("Image for key " + key + " already exist!");
        } else {
            System.out.println("Loading... " + reference);
            images.put(key, new Image(reference));
        }
    }

    public static void addMusic(String key, String reference) throws SlickException {
        if (hasMusic(key)) {
            throw new IllegalArgumentException("Music for key " + key + " already exist!");
        } else {
            musics.put(key, new Music(reference));
        }
    }

    public static void addSound(String key, String reference) throws SlickException {
        if (hasSound(key)) {
            throw new IllegalArgumentException("Sound for key " + key + " already exist!");
        } else {
            sounds.put(key, new Sound(reference));
        }
    }

    public static Image getImage(String key) {
        Image image = (Image) images.get(key);
        if (image == null) {
            throw new IllegalArgumentException("No image for key " + key + " " + images.keySet());
        } else {
            return image;
        }
    }

    public static Music getMusic(String key) {
        Music music = (Music) musics.get(key);
        if (music == null) {
            throw new IllegalArgumentException("No music for key " + key + " " + musics.keySet());
        } else {
            return music;
        }
    }

    public static Sound getSound(String key) {
        Sound sound = (Sound) sounds.get(key);
        if (sound == null) {
            throw new IllegalArgumentException("No sound for key " + key + " " + sounds.keySet());
        } else {
            return sound;
        }
    }

    public static boolean hasImage(String key) {
        return images.containsKey(key);
    }

    public static boolean hasMusic(String key) {
        return musics.containsKey(key);
    }

    public static boolean hasSound(String key) {
        return sounds.containsKey(key);
    }
}
