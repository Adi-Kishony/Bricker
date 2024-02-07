package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.Constants;
import bricker.gameobjects.Paddle;
import bricker.gameobjects.SecondPaddle;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class AddPaddleCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private SecondPaddle secondPaddle;

    public AddPaddleCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
        this.secondPaddle = null;
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        Renderable paddleImage = brickerGameManager.getImageReader().readImage("assets/paddle.png",true);
        this.secondPaddle = new SecondPaddle(
                brickerGameManager.getWindowDimensions().mult(0.5f),
                new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                paddleImage,
                brickerGameManager.getInputListener(),
                brickerGameManager.getWindowDimensions(), this);
        brickerGameManager.addGameObject(this.secondPaddle);
    }

    public void removePaddle(){
        brickerGameManager.removeGameObject(secondPaddle);
    }
}
