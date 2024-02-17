package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;

/**
 * A basic collision strategy for bricks in the Bricker game.
 * This strategy removes the collided brick from the game when a collision occurs.
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    /**
     * The BrickerGameManager associated with this collision strategy.
     */
    protected BrickerGameManager brickerGameManager;

    /**
     * Constructs a BasicCollisionStrategy with the specified BrickerGameManager.
     *
     * @param brickerGameManager The BrickerGameManager to associate with this collision strategy.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Executes the basic collision strategy, removing the first game object from the STATIC_OBJECTS layer
     * when a collision occurs with the second game object.
     *
     * @param obj1 The first game object involved in the collision (to be removed).
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        // Remove the collided brick from the STATIC_OBJECTS layer
        brickerGameManager.removeGameObject(obj1, Layer.STATIC_OBJECTS);
    }
}