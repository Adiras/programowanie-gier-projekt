package com.noscoope.blazeit.screen;


import com.noscoope.blazeit.ResourceManager;
import com.noscoope.blazeit.screen.GameState;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.List;

public class MenuState extends BasicGameState {

    public static final int STATE_IDENTIFIER = 1;
    private List<MenuEntry> menu = new ArrayList<>();
    private Image background = ResourceManager.getImage("background_menu");
    private Sound select = ResourceManager.getSound("menu_select");
    private Sound move = ResourceManager.getSound("menu_move");
    private UnicodeFont font;
    private int menuIndex = 0;

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        initMenuFont(container, game);
        initMenu(container, game);
    }

    private void initMenuFont(GameContainer container, StateBasedGame game) throws SlickException {
        font = new UnicodeFont("res/fonts/Franchise-Bold.ttf", 33, false, false);
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect());
        font.loadGlyphs();
    }

    private void initMenu(GameContainer container, StateBasedGame game) throws SlickException {
        addMenuEntry(new MenuEntry("Start", () -> {
            game.enterState(GameState.STATE_IDENTIFIER,
                    new FadeOutTransition(Color.black, 400),
                    new FadeInTransition());
        }));

        addMenuEntry(new MenuEntry("Exit", container::exit));
    }

    private void addMenuEntry(MenuEntry entry) {
        menu.add(entry);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException {
        background.draw();

        for (int i = 0; i < menu.size(); i++) {
            var message = menu.get(i).message;
            var color = menuIndex == i ? new Color(246, 97, 41) : Color.white;
            var x = container.getWidth() / 2f - font.getWidth(message) / 2f;
            var y = 138f + i * (font.getHeight(message) + 30f);
            font.drawString(x, y, message, color);
        }
    }

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        var input = container.getInput();

        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            container.exit();
        }

        var shouldGotoNextEntry =
                (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_S));

        var shouldGotoPreviousEntry =
                (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W));

        var shouldGotoCurrentEntry =
                (input.isKeyDown(Input.KEY_ENTER) || input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON));

        var shouldRotateUp   = (menuIndex >= menu.size() - 1);
        var shouldRotateDown = (menuIndex <= 0);

        if (shouldGotoNextEntry) {
            menuIndex++;
            move.play();
            if (shouldRotateUp) {
                menuIndex = 0;
            }
        } else {
            if (shouldGotoPreviousEntry) {
                menuIndex--;
                move.play();
                if (shouldRotateDown) {
                    menuIndex = menu.size() - 1;
                }
            } else {
                if (shouldGotoCurrentEntry) {
                    menu.get(menuIndex).action();
                }
            }
        }
    }

    public int getID() {
        return STATE_IDENTIFIER;
    }

    private class MenuEntry {
        private final String message;
        private final MenuEntryAction action;

        public MenuEntry(String message, MenuEntryAction action) {
            this.message = message;
            this.action = action;
        }

        public void action() {
            action.onClick();
        }
    }

    private interface MenuEntryAction {
        void onClick();
    }
}
