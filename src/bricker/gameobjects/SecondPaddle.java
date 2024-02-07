package bricker.gameobjects;

import bricker.brick_strategies.AddPaddleCollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class SecondPaddle extends Paddle {
    private final AddPaddleCollisionStrategy myCreator;
    private int collisionCounter;
    public SecondPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener
            inputListener, Vector2 windowDimensions, AddPaddleCollisionStrategy myCreator) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.myCreator = myCreator;
    }
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball){
            collisionCounter ++;
            if (collisionCounter >= 4){
                myCreator.removePaddle();
            }
        }
    }
}
