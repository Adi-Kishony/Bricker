package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The Brick class represents a game object in the form of a brick in the Bricker game.
 * It extends the danogl.GameObject class and defines the behavior of the game brick,
 * including collision handling and the associated collision strategy.
 * The class tracks the number of bricks left in the game and delegates collision handling
 * to a specified CollisionStrategy. When a collision occurs, the brick's count is decremented,
 * and the collision strategy's onCollision method is called.
 */
public class Brick extends GameObject {

    /**
     * bricksLeft field counts the bricks that left in each delta time in the game.
     */
    private final Counter bricksLeft;

    /**
     * collisionStrategy field represents the collision strategy of this brick.
     */
    private final CollisionStrategy collisionStrategy;

    /**
     * Constructs a Brick object with the specified parameters.
     * @param topLeftCorner     The top-left corner position of the brick.
     * @param dimensions        The dimensions of the brick.
     * @param renderable        The renderable image for the brick.
     * @param collisionStrategy The collision strategy associated with the brick.
     * @param bricksLeft        The counter for tracking the number of bricks left in the game.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy
            collisionStrategy, Counter bricksLeft){
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.bricksLeft = bricksLeft;
    }

    /**
     * Handles the behavior when a collision occurs with another game object.
     * Decrements the number of bricks left and delegates the collision handling
     * to the associated CollisionStrategy.
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        // decrease the left bricks counter
        bricksLeft.decrement();
        // activate this brick collision strategy
        collisionStrategy.onCollision(this, other);
    }
}
