package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.Constants;
import bricker.gameobjects.SecondPaddle;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class AddPaddleCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private SecondPaddle secondPaddle;

    private static boolean existsPaddle = false;

    public AddPaddleCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        if (!existsPaddle) {
            existsPaddle = true;
            Renderable paddleImage = brickerGameManager.getImageReader().readImage(Constants.PADDLE_IMG_PATH,
                    true);
            this.secondPaddle = new SecondPaddle(
                    brickerGameManager.getWindowDimensions().mult(Constants.MULTIPLY_FACTOR_HALF),
                    new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                    paddleImage,
                    brickerGameManager.getInputListener(),
                    brickerGameManager.getWindowDimensions(), this);
            brickerGameManager.addGameObject(this.secondPaddle);
        }
    }

    public void removePaddle(){
        brickerGameManager.removeGameObject(secondPaddle);
        existsPaddle = false;
    }
}
