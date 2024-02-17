package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * The Ball class represents a game object in the Bricker game.
 * It extends the danogl.GameObject class and defines the behavior of the ball.
 * The class tracks collision events, provides a collision counter, and allows the ball
 * to be recentered with a randomized velocity. It is associated with a specific tag "Ball."
 */
public class Ball extends GameObject {

    /**
     * BALL_TAG constant represent the ball's tag.
     */
    public static final String BALL_TAG = "Ball";

    /**
     * collisionSound field represent the ball's sound.
     */
    protected Sound collisionSound;

    /**
     * collisionCounter field counts the ball's collisions.
     */
    private int collisionCounter;

    /**
     * Constructs a Ball object with the specified parameters.
     * @param topLeftCorner   The top-left corner position of the ball.
     * @param dimensions      The dimensions of the ball.
     * @param renderable      The renderable image for the ball.
     * @param collisionSound  The sound played on collision with other game objects.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
    }

    /**
     * Handles the behavior when a collision occurs with another game object.
     * Reverses the velocity based on the collision normal and plays a collision sound.
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        // counting the collision of the ball
        collisionCounter ++;
        // play the collision sound
        collisionSound.play();
    }

    /**
     * Gets the collision counter associated with the ball.
     * @return The collision counter.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }

    /**
     * Resets the ball to the specified center and gives it a random velocity.
     * @param ballSpeed   The speed of the ball.
     * @param ballCenter  The new center position of the ball.
     */
    public void reCenterBall(float ballSpeed, Vector2 ballCenter) {
        Random rand = new Random();
        // recenter the ball location
        this.setCenter(ballCenter);

        // set the ball's new velocity
        float ballVelY = ballSpeed;
        float ballVelX = ballSpeed;
        if (rand.nextBoolean()) {
            ballVelX *= -1;
            ballVelY *= -1;
        }
        this.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * Gets the tag associated with the ball.
     * @return The tag.
     */
    @Override
    public String getTag() {
        return BALL_TAG;
    }
}
