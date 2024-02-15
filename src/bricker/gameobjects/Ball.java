package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class Ball extends GameObject {
    public static final String BALL_TAG = "Ball";
    protected Sound collisionSound;
    private final Counter collisionCounter;

    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = new Counter(0);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionCounter.increment();
        collisionSound.play();
    }

    public Counter getCollisionCounter() {
        return collisionCounter;
    }

    public void reCenterBall(float ballSpeed, Vector2 ballCenter) {
        this.setCenter(ballCenter);
        float ballVelY = ballSpeed;
        float ballVelX = ballSpeed;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
            ballVelY *= -1;
        }
        this.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    @Override
    public String getTag() {
        return BALL_TAG;
    }
}
