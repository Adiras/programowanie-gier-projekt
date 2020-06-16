package com.noscoope.blazeit.screen;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GameOverState extends BasicGameState {
    public static final int STATE_IDENTIFIER = 3;
    private UnicodeFont font;
    private int score;
    private int waves;
    private int deathAliens;

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.font = new UnicodeFont("res/fonts/Franchise-Bold.ttf", 52, false, false);
        this.font.addAsciiGlyphs();
        this.font.getEffects().add(new ColorEffect());
        this.font.loadGlyphs();
    }

    public void enter(GameContainer container, StateBasedGame game) {
    }

    public void render(GameContainer container, StateBasedGame game, Graphics graphics) {
        String gov = "Game Over";
        font.drawString((float) (container.getWidth() / 2 - font.getWidth(gov) / 2), (float) (container.getHeight() / 2 - font.getHeight(gov) / 2 - 20), gov);
        String scr = "Your score: " + this.score;
        font.drawString((float) (container.getWidth() / 2 - font.getWidth(scr) / 2), (float) (container.getHeight() / 2 - font.getHeight(scr) / 2 + 20), scr);
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_ENTER) ||
            input.isKeyDown(Input.KEY_SPACE) ||
            input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ||
            input.isKeyDown(Input.MOUSE_RIGHT_BUTTON)) {
            game.enterState(1, new FadeOutTransition(Color.black, 400), new FadeInTransition());
        }
    }

    public void submitScore(String login, int score, int kill, int level) {
        URL url = null;
        HttpURLConnection connection = null;

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("http://blazeit.720noscoope.xaa.pl/sqlsend.php?sLogin=");
            sb.append(login);
            sb.append("&iScore=");
            sb.append(score);
            sb.append("&iKill=");
            sb.append(kill);
            sb.append("&iLevel=");
            sb.append(level);
            url = new URL(sb.toString());
            connection = (HttpURLConnection)url.openConnection();
            connection.getContent();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setWaves(int waves) {
        this.waves = waves;
    }

    public void setDeathAliens(int deathAliens) {
        this.deathAliens = deathAliens;
    }

    public int getID() {
        return STATE_IDENTIFIER;
    }
}
