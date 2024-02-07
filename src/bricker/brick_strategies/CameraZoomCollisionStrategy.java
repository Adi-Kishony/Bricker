package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.collisions.Layer;

public class CameraZoomCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    public CameraZoomCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        if (!(obj2 instanceof Puck) && obj2 instanceof Ball){

        }
    }
}
