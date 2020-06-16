package com.noscoope.blazeit.screen;

import com.noscoope.blazeit.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameState extends BasicGameState {
    public static final int STATE_IDENTIFIER = 2;
    private Music mainMusic = ResourceManager.getMusic("main");
    private Image background = ResourceManager.getImage("background");
    public static Input input;
    private World world;
    private Gui gui;
    public static Announcer announcer;
    private Background firstBackground;
    private Background secondBackground;

    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        this.mainMusic.loop();
        this.firstBackground = new Background(0.0F, 0.0F);
        this.secondBackground = new Background(0.0F, -800.0F);
        this.world = new World();
        this.gui = new Gui(this.world);
        announcer = new Announcer(container, 200, Color.white);
    }

    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        mainMusic.stop();
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        processInput(container.getInput());
        background.setFilter(Image.FILTER_NEAREST);
    }

    private static void processInput(Input input) {
        GameState.input = input;
    }

    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
        background.draw(firstBackground.x, firstBackground.y);
        background.draw(secondBackground.x, secondBackground.y);
        world.render(container, graphics);
        gui.render(container, graphics);
        announcer.render(container, graphics);
    }

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        processInput(container.getInput());
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            game.enterState(MenuState.STATE_IDENTIFIER,
                    new FadeOutTransition(Color.black, 400),
                    new FadeInTransition());
        }

        if (world.isGameOver()) {
            GameOverState state = (GameOverState)game.getState(3);
            state.setScore(world.getScore());
            state.setWaves(world.getCurrentWave());
            state.setDeathAliens(world.getDeathAliens());
            game.enterState(GameOverState.STATE_IDENTIFIER,
                    new FadeOutTransition(Color.black, 400),
                    new FadeInTransition());
        }

        firstBackground.udate(delta);
        secondBackground.udate(delta);
        world.update(container, delta);
        gui.update(container, delta);
        announcer.update(container, delta);
    }

    public int getID() {
        return STATE_IDENTIFIER;
    }
}
