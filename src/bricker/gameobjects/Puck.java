package bricker.gameobjects;

import bricker.Constants;
import bricker.brick_strategies.AddBallsCollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Map;
import java.util.Random;

public class Puck extends Ball {
    private AddBallsCollisionStrategy myCreator;

    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        Random rand = new Random();
        double angle = rand.nextDouble()* Math.PI;
        float velocityX = (float)Math.cos(angle) * Constants.BALL_SPEED;
        float velocityY = (float)Math.sin(angle) * Constants.BALL_SPEED;
        Vector2 newVel = new Vector2(velocityX, velocityY);
        setVelocity(newVel);
        collisionSound.play();
    }

    @Override
    public void reCenterBall(Vector2 windowDimensions, float ballSpeed, Vector2 ballCenter) {
        super.reCenterBall(windowDimensions, ballSpeed, ballCenter);
    }

//    @Override
//    public void update(float deltaTime) {
//        super.update(deltaTime);
//        // check if this puck ball is out of window scope, if so, remove this puck from games objects.
//        myCreator.removePuckBall(this);
//    }
}
