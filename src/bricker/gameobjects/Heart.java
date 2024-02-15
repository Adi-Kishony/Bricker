package bricker.gameobjects;

import bricker.brick_strategies.AddLifeCollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Heart class represents a game object in the form of a heart in the Bricker game.
 * It extends the danogl.GameObject class and defines the behavior of the heart,
 * including collision handling and interactions with an AddLifeCollisionStrategy.
 * The class has two constructors, allowing for the creation of a heart with or without
 * an associated collision strategy. It provides methods for collision checks and actions
 * to be taken upon collision, such as notifying the creator of the heart and checking
 * if the heart is out of bounds.
 */
public class Heart extends GameObject {

    // Instance variable
    private final AddLifeCollisionStrategy myCreator;

    /**
     * Constructs a Heart object without an associated collision strategy.
     * @param topLeftCorner The top-left corner position of the heart.
     * @param dimensions    The dimensions of the heart.
     * @param renderable    The renderable image for the heart.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.myCreator = null;
    }

    /**
     * Constructs a Heart object with an associated collision strategy.
     * @param topLeftCorner The top-left corner position of the heart.
     * @param dimensions    The dimensions of the heart.
     * @param renderable    The renderable image for the heart.
     * @param myCreator     The collision strategy which responsible for creating the heart.
     *                      (by calling this constructor)
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 AddLifeCollisionStrategy myCreator) {
        super(topLeftCorner, dimensions, renderable);
        this.myCreator = myCreator;
    }

    /**
     * Determines if the heart should collide with the specified game object.
     * Hearts should only collide with paddles.
     * @param other The other game object involved in the collision.
     * @return True if the heart should collide with the specified object; otherwise, false.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Paddle.PADDLE_TAG);
    }

    /**
     * Handles the behavior when a collision occurs with another game object.
     * Notifies the creator (if available) than,
     * caught this Heart in order to add life to the player's lives (if available).
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (myCreator != null){
            // caught this Heart, and update left lives accordingly
            myCreator.caughtHeart(this);
        }
    }

    /**
     * Updates the heart's state, checking if it is out of the screen bounds.
     * Notifies the creator (if available) to handle out-of-bounds situations.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (myCreator != null){
            // check if the heart is out of the game board bounds, and remove it if it does.
            myCreator.checkOutOfBounds(this);
        }
    }
}
