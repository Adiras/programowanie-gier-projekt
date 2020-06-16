package com.noscoope.blazeit;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tests.PedigreeTest;

public class Main {
    public static void main(String[] args) {
        try {
            AppGameContainer application = new AppGameContainer(new StateController("BlazeIT"));
            application.setIcon("res/images/icon.png");
            application.setDisplayMode(1024, 768, false);
            application.setMouseGrabbed(true);
            application.setShowFPS(false);
            application.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}