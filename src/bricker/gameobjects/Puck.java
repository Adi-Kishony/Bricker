package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Puck extends Ball {


    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
    }

    @Override
    public void reCenterBall(Vector2 windowDimensions, float ballSpeed, Vector2 ballCenter) {
        super.reCenterBall(windowDimensions, ballSpeed, ballCenter);
    }

}
