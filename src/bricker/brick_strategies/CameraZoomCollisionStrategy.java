package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

public class CameraZoomCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private static  final float ZOOM_RATIO = 1.2f;
    private Camera camera;
    public CameraZoomCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
        this.camera = new Camera(null, Vector2.ZERO, brickerGameManager.getWindowDimensions().mult(ZOOM_RATIO),
                brickerGameManager.getWindowDimensions());
    }

    private void setCameraOn(GameObject ball){
        camera.setToFollow(ball, Vector2.ZERO);
        brickerGameManager.setCamera(camera);
    }

    private void setCameraOff(GameObject ball){
        brickerGameManager.setCamera(null);
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        if (obj2.getTag().equals("Ball")){
            if (brickerGameManager.camera() == null){
                setCameraOn(obj2);
            }
        }
    }
}
