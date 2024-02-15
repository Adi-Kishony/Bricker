package bricker.gameobjects;

import bricker.brick_strategies.AddPaddleCollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The SecondPaddle class represents an additional game object in the form of a paddle
 * in the Bricker game. It extends the Paddle class and introduces addition behavior related to
 * a collision strategy for adding paddles. The second paddle has a specific collision tag, and
 * its collision counter is used to determine when to remove the paddle based on its collisions with
 * balls.
 */
public class SecondPaddle extends Paddle {

    // Constants
    private static final String SECOND_PADDLE_TAG = "extra paddle";
    private static final int MAX_COLLISIONS = 4;

    // Instance variables
    private final AddPaddleCollisionStrategy myCreator;
    private int collisionCounter;

    /**
     * Constructs a SecondPaddle object with the specified parameters.
     * @param topLeftCorner      The top-left corner position of the second paddle.
     * @param dimensions         The dimensions of the second paddle.
     * @param renderable         The renderable image for the second paddle.
     * @param inputListener      The user input listener for handling paddle movement.
     * @param windowDimensions   The dimensions of the game window.
     * @param myCreator          The collision strategy responsible for creating the paddle.
     */
    public SecondPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener
            inputListener, Vector2 windowDimensions, AddPaddleCollisionStrategy myCreator) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.myCreator = myCreator;
    }

    /**
     * Handles the collision event with another game object, updating the collision counter
     * and removing the paddle if the maximum collision threshold is reached with a ball.
     * @param other      The other game object involved in the collision.
     * @param collision  The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball){
            collisionCounter ++;
            // check if the collisions of the second paddle exceeded the maximum collisions amount
            if (collisionCounter >= MAX_COLLISIONS){
                myCreator.removePaddle();
            }
        }
    }

    /**
     * Returns the tag associated with the second paddle.
     * @return The tag string representing the second paddle.
     */
    @Override
    public String getTag() {
        return SECOND_PADDLE_TAG;
    }
}
