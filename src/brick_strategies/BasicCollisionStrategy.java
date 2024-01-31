package brick_strategies;

import danogl.GameManager;
import danogl.GameObject;

public class BasicCollisionStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;

    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        System.out.println("Collision with brick detected");
    }
}
