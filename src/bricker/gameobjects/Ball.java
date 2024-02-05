package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Ball extends GameObject {
    private Sound collisionSound;

    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
    }

    private int collisionCounter;
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionCounter++;
        collisionSound.play();
    }

    public int getCollisionCounter() {
        return collisionCounter;
    }

    public void reCenterBall(Vector2 windowDimensions, float ballSpeed){
        this.setCenter(windowDimensions.mult(0.5f));
        float ballVelY = ballSpeed;
        float ballVelX = ballSpeed;
        Random rand = new Random();
        if(rand.nextBoolean()){
            ballVelX *= -1;
            ballVelY *= -1;
        }
        this.setVelocity(new Vector2(ballVelX,ballVelY));
    }
}
