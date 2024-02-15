package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.gameobjects.CameraTracker;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

public class CameraZoomCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private static final float ZOOM_RATIO = 1.2f;
    private Camera camera;
    private CameraTracker cameraTracker;
    public CameraZoomCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
        this.camera = null;
        this.cameraTracker = null;

    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        if (brickerGameManager.camera() == null){
            camera = new Camera(null, Vector2.ZERO,
                    brickerGameManager.getWindowDimensions().mult(ZOOM_RATIO),
                    brickerGameManager.getWindowDimensions());

            cameraTracker = new CameraTracker(Vector2.ZERO, Vector2.ZERO, null,
                    brickerGameManager,camera);
            brickerGameManager.addGameObject(cameraTracker);
            if (obj2.getTag().equals("Ball")){
                cameraTracker.setCameraOn(obj2);
            }
        }


    }
}
