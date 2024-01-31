package brick_strategies;

import danogl.GameObject;

public class BasicCollisionStrategy implements CollisionStrategy {
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        System.out.println("Collision with brick detected");
    }
}
