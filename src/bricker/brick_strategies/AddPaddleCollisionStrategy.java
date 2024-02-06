package bricker.brick_strategies;

import bricker.BrickerGameManager;
import danogl.GameObject;

public class AddPaddleCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    public AddPaddleCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        brickerGameManager.createPaddle(brickerGameManager.getWindowDimensions().mult(0.5f));
        System.out.println("add paddle");
    }
}
