package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {

    private final Counter bricksLeft;

    private CollisionStrategy collisionStrategy;
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy
            collisionStrategy, Counter bricksLeft){
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.bricksLeft = bricksLeft;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        bricksLeft.decrement();
        collisionStrategy.onCollision(this, other);
    }
}
