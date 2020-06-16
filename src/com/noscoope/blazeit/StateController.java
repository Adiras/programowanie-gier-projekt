package com.noscoope.blazeit;

import com.noscoope.blazeit.screen.GameOverState;
import com.noscoope.blazeit.screen.GameState;
import com.noscoope.blazeit.screen.MenuState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class StateController extends StateBasedGame {

    public StateController(String name) {
        super(name);
    }

    public void initStatesList(GameContainer container) {
        addState(new MenuState());
        addState(new GameState());
        addState(new GameOverState());
        enterState(1);
    }
}
