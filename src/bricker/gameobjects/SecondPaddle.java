package bricker.gameobjects;

import bricker.brick_strategies.AddPaddleCollisionStrategy;
import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class SecondPaddle extends Paddle {
    private AddPaddleCollisionStrategy myCreator;
    private int collisionCounter;
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Puck || other instanceof Ball){
            collisionCounter ++;
            if (collisionCounter >= 4){
                myCreator.removePaddle();
            }
        }
    }
    public SecondPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener
            inputListener, Vector2 windowDimensions, AddPaddleCollisionStrategy myCreator) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.myCreator = myCreator;
    }
}
