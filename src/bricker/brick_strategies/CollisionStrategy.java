package bricker.brick_strategies;

import danogl.GameObject;

/**
 * An interface representing a collision strategy between two game objects.
 * Implementing classes define the behavior to be executed when a collision occurs.
 */
public interface CollisionStrategy {

    /**
     * Defines the actions to be taken when a collision occurs between two game objects.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */
    void onCollision(GameObject obj1, GameObject obj2);
}