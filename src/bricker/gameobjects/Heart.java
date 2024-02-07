package bricker.gameobjects;

import bricker.brick_strategies.AddBallsCollisionStrategy;
import bricker.brick_strategies.AddLifeCollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {

    private AddLifeCollisionStrategy myCreator;
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.myCreator = null;
    }
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 AddLifeCollisionStrategy myCreator) {
        super(topLeftCorner, dimensions, renderable);
        this.myCreator = myCreator;
    }


    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals("Paddle");
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (myCreator != null){
            myCreator.caughtHeart(this);
        }

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (myCreator != null){
            myCreator.checkOutOfBounds(this);
        }
    }
}
